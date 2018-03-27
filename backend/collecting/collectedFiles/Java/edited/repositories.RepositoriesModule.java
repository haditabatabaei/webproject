

package org.elasticsearch.repositories;

import org.elasticsearch.action.admin.cluster.snapshots.status.TransportNodesSnapshotsStatus;
import org.elasticsearch.common.inject.AbstractModule;
import org.elasticsearch.common.inject.multibindings.MapBinder;
import org.elasticsearch.common.xcontent.NamedXContentRegistry;
import org.elasticsearch.env.Environment;
import org.elasticsearch.plugins.RepositoryPlugin;
import org.elasticsearch.repositories.fs.FsRepository;
import org.elasticsearch.snapshots.RestoreService;
import org.elasticsearch.snapshots.SnapshotShardsService;
import org.elasticsearch.snapshots.SnapshotsService;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RepositoriesModule extends AbstractModule {

    private final Map<String, Repository.Factory> repositoryTypes;

    public RepositoriesModule(Environment env, List<RepositoryPlugin> repoPlugins, NamedXContentRegistry namedXContentRegistry) {
        Map<String, Repository.Factory> factories = new HashMap<>();
        factories.put(FsRepository.TYPE, (metadata) -> new FsRepository(metadata, env, namedXContentRegistry));

        for (RepositoryPlugin repoPlugin : repoPlugins) {
            Map<String, Repository.Factory> newRepoTypes = repoPlugin.getRepositories(env, namedXContentRegistry);
            for (Map.Entry<String, Repository.Factory> entry : newRepoTypes.entrySet()) {
                if (factories.put(entry.getKey(), entry.getValue()) != null) {
                    throw new IllegalArgumentException("Repository type [" + entry.getKey() + "] is already registered");
                }
            }
        }
        repositoryTypes = Collections.unmodifiableMap(factories);
    }

    @Override
    protected void configure() {
        bind(RepositoriesService.class).asEagerSingleton();
        bind(SnapshotsService.class).asEagerSingleton();
        bind(SnapshotShardsService.class).asEagerSingleton();
        bind(TransportNodesSnapshotsStatus.class).asEagerSingleton();
        bind(RestoreService.class).asEagerSingleton();
        MapBinder<String, Repository.Factory> typesBinder = MapBinder.newMapBinder(binder(), String.class, Repository.Factory.class);
        repositoryTypes.forEach((k, v) -> typesBinder.addBinding(k).toInstance(v));
    }
}
