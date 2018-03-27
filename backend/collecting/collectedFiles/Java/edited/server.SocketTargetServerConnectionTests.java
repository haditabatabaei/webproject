

package org.springframework.boot.devtools.tunnel.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;


public class SocketTargetServerConnectionTests {

	private static final int DEFAULT_TIMEOUT = 1000;

	private MockServer server;

	private SocketTargetServerConnection connection;

	@Before
	public void setup() throws IOException {
		this.server = new MockServer();
		this.connection = new SocketTargetServerConnection(() -> this.server.getPort());
	}

	@Test
	public void readData() throws Exception {
		this.server.willSend("hello".getBytes());
		this.server.start();
		ByteChannel channel = this.connection.open(DEFAULT_TIMEOUT);
		ByteBuffer buffer = ByteBuffer.allocate(5);
		channel.read(buffer);
		assertThat(buffer.array()).isEqualTo("hello".getBytes());
	}

	@Test
	public void writeData() throws Exception {
		this.server.expect("hello".getBytes());
		this.server.start();
		ByteChannel channel = this.connection.open(DEFAULT_TIMEOUT);
		ByteBuffer buffer = ByteBuffer.wrap("hello".getBytes());
		channel.write(buffer);
		this.server.closeAndVerify();
	}

	@Test
	public void timeout() throws Exception {
		this.server.delay(1000);
		this.server.start();
		ByteChannel channel = this.connection.open(10);
		long startTime = System.currentTimeMillis();
		try {
			channel.read(ByteBuffer.allocate(5));
			fail("No socket timeout thrown");
		}
		catch (SocketTimeoutException ex) {
						long runTime = System.currentTimeMillis() - startTime;
			assertThat(runTime).isGreaterThanOrEqualTo(10L);
			assertThat(runTime).isLessThan(10000L);
		}
	}

	private static class MockServer {

		private ServerSocketChannel serverSocket;

		private byte[] send;

		private byte[] expect;

		private int delay;

		private ByteBuffer actualRead;

		private ServerThread thread;

		MockServer() throws IOException {
			this.serverSocket = ServerSocketChannel.open();
			this.serverSocket.bind(new InetSocketAddress(0));
		}

		int getPort() {
			return this.serverSocket.socket().getLocalPort();
		}

		public void delay(int delay) {
			this.delay = delay;
		}

		public void willSend(byte[] send) {
			this.send = send;
		}

		public void expect(byte[] expect) {
			this.expect = expect;
		}

		public void start() {
			this.thread = new ServerThread();
			this.thread.start();
		}

		public void closeAndVerify() throws InterruptedException {
			close();
			assertThat(this.actualRead.array()).isEqualTo(this.expect);
		}

		public void close() throws InterruptedException {
			while (this.thread.isAlive()) {
				Thread.sleep(10);
			}
		}

		private class ServerThread extends Thread {

			@Override
			public void run() {
				try {
					SocketChannel channel = MockServer.this.serverSocket.accept();
					Thread.sleep(MockServer.this.delay);
					if (MockServer.this.send != null) {
						ByteBuffer buffer = ByteBuffer.wrap(MockServer.this.send);
						while (buffer.hasRemaining()) {
							channel.write(buffer);
						}
					}
					if (MockServer.this.expect != null) {
						ByteBuffer buffer = ByteBuffer
								.allocate(MockServer.this.expect.length);
						while (buffer.hasRemaining()) {
							channel.read(buffer);
						}
						MockServer.this.actualRead = buffer;
					}
					channel.close();
				}
				catch (Exception ex) {
					ex.printStackTrace();
					fail();
				}
			}

		}

	}

}
