

package org.springframework.boot.devtools.filewatch;

import java.io.File;
import java.io.FileFilter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.Assert;


public class FileSystemWatcher {

	private static final Duration DEFAULT_POLL_INTERVAL = Duration.ofMillis(1000);

	private static final Duration DEFAULT_QUIET_PERIOD = Duration.ofMillis(400);

	private final List<FileChangeListener> listeners = new ArrayList<>();

	private final boolean daemon;

	private final long pollInterval;

	private final long quietPeriod;

	private final AtomicInteger remainingScans = new AtomicInteger(-1);

	private final Map<File, FolderSnapshot> folders = new HashMap<>();

	private Thread watchThread;

	private FileFilter triggerFilter;

	private final Object monitor = new Object();

	
	public FileSystemWatcher() {
		this(true, DEFAULT_POLL_INTERVAL, DEFAULT_QUIET_PERIOD);
	}

	
	public FileSystemWatcher(boolean daemon, Duration pollInterval,
			Duration quietPeriod) {
		Assert.notNull(pollInterval, "PollInterval must not be null");
		Assert.notNull(quietPeriod, "QuietPeriod must not be null");
		Assert.isTrue(pollInterval.toMillis() > 0, "PollInterval must be positive");
		Assert.isTrue(quietPeriod.toMillis() > 0, "QuietPeriod must be positive");
		Assert.isTrue(pollInterval.toMillis() > quietPeriod.toMillis(),
				"PollInterval must be greater than QuietPeriod");
		this.daemon = daemon;
		this.pollInterval = pollInterval.toMillis();
		this.quietPeriod = quietPeriod.toMillis();
	}

	
	public void addListener(FileChangeListener fileChangeListener) {
		Assert.notNull(fileChangeListener, "FileChangeListener must not be null");
		synchronized (this.monitor) {
			checkNotStarted();
			this.listeners.add(fileChangeListener);
		}
	}

	
	public void addSourceFolders(Iterable<File> folders) {
		Assert.notNull(folders, "Folders must not be null");
		synchronized (this.monitor) {
			for (File folder : folders) {
				addSourceFolder(folder);
			}
		}
	}

	
	public void addSourceFolder(File folder) {
		Assert.notNull(folder, "Folder must not be null");
		Assert.isTrue(folder.isDirectory(),
				"Folder '" + folder + "' must exist and must" + " be a directory");
		synchronized (this.monitor) {
			checkNotStarted();
			this.folders.put(folder, null);
		}
	}

	
	public void setTriggerFilter(FileFilter triggerFilter) {
		synchronized (this.monitor) {
			this.triggerFilter = triggerFilter;
		}
	}

	private void checkNotStarted() {
		synchronized (this.monitor) {
			Assert.state(this.watchThread == null, "FileSystemWatcher already started");
		}
	}

	
	public void start() {
		synchronized (this.monitor) {
			saveInitialSnapshots();
			if (this.watchThread == null) {
				Map<File, FolderSnapshot> localFolders = new HashMap<>();
				localFolders.putAll(this.folders);
				this.watchThread = new Thread(new Watcher(this.remainingScans,
						new ArrayList<>(this.listeners), this.triggerFilter,
						this.pollInterval, this.quietPeriod, localFolders));
				this.watchThread.setName("File Watcher");
				this.watchThread.setDaemon(this.daemon);
				this.watchThread.start();
			}
		}
	}

	private void saveInitialSnapshots() {
		for (File folder : this.folders.keySet()) {
			this.folders.put(folder, new FolderSnapshot(folder));
		}
	}

	
	public void stop() {
		stopAfter(0);
	}

	
	void stopAfter(int remainingScans) {
		Thread thread;
		synchronized (this.monitor) {
			thread = this.watchThread;
			if (thread != null) {
				this.remainingScans.set(remainingScans);
				if (remainingScans <= 0) {
					thread.interrupt();
				}
			}
			this.watchThread = null;
		}
		if (thread != null && Thread.currentThread() != thread) {
			try {
				thread.join();
			}
			catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	private static final class Watcher implements Runnable {

		private final AtomicInteger remainingScans;

		private final List<FileChangeListener> listeners;

		private final FileFilter triggerFilter;

		private final long pollInterval;

		private final long quietPeriod;

		private Map<File, FolderSnapshot> folders;

		private Watcher(AtomicInteger remainingScans, List<FileChangeListener> listeners,
				FileFilter triggerFilter, long pollInterval, long quietPeriod,
				Map<File, FolderSnapshot> folders) {
			this.remainingScans = remainingScans;
			this.listeners = listeners;
			this.triggerFilter = triggerFilter;
			this.pollInterval = pollInterval;
			this.quietPeriod = quietPeriod;
			this.folders = folders;
		}

		@Override
		public void run() {
			int remainingScans = this.remainingScans.get();
			while (remainingScans > 0 || remainingScans == -1) {
				try {
					if (remainingScans > 0) {
						this.remainingScans.decrementAndGet();
					}
					scan();
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
				remainingScans = this.remainingScans.get();
			}
		}

		private void scan() throws InterruptedException {
			Thread.sleep(this.pollInterval - this.quietPeriod);
			Map<File, FolderSnapshot> previous;
			Map<File, FolderSnapshot> current = this.folders;
			do {
				previous = current;
				current = getCurrentSnapshots();
				Thread.sleep(this.quietPeriod);
			}
			while (isDifferent(previous, current));
			if (isDifferent(this.folders, current)) {
				updateSnapshots(current.values());
			}
		}

		private boolean isDifferent(Map<File, FolderSnapshot> previous,
				Map<File, FolderSnapshot> current) {
			if (!previous.keySet().equals(current.keySet())) {
				return true;
			}
			for (Map.Entry<File, FolderSnapshot> entry : previous.entrySet()) {
				FolderSnapshot previousFolder = entry.getValue();
				FolderSnapshot currentFolder = current.get(entry.getKey());
				if (!previousFolder.equals(currentFolder, this.triggerFilter)) {
					return true;
				}
			}
			return false;
		}

		private Map<File, FolderSnapshot> getCurrentSnapshots() {
			Map<File, FolderSnapshot> snapshots = new LinkedHashMap<>();
			for (File folder : this.folders.keySet()) {
				snapshots.put(folder, new FolderSnapshot(folder));
			}
			return snapshots;
		}

		private void updateSnapshots(Collection<FolderSnapshot> snapshots) {
			Map<File, FolderSnapshot> updated = new LinkedHashMap<>();
			Set<ChangedFiles> changeSet = new LinkedHashSet<>();
			for (FolderSnapshot snapshot : snapshots) {
				FolderSnapshot previous = this.folders.get(snapshot.getFolder());
				updated.put(snapshot.getFolder(), snapshot);
				ChangedFiles changedFiles = previous.getChangedFiles(snapshot,
						this.triggerFilter);
				if (!changedFiles.getFiles().isEmpty()) {
					changeSet.add(changedFiles);
				}
			}
			if (!changeSet.isEmpty()) {
				fireListeners(Collections.unmodifiableSet(changeSet));
			}
			this.folders = updated;
		}

		private void fireListeners(Set<ChangedFiles> changeSet) {
			for (FileChangeListener listener : this.listeners) {
				listener.onChange(changeSet);
			}
		}

	}

}