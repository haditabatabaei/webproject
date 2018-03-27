

package org.elasticsearch.discovery.file;

import org.apache.logging.log4j.message.ParameterizedMessage;
import org.apache.logging.log4j.util.Supplier;
import org.elasticsearch.cluster.node.DiscoveryNode;
import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.discovery.zen.UnicastHostsProvider;
import org.elasticsearch.env.Environment;
import org.elasticsearch.transport.TransportService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.elasticsearch.discovery.zen.UnicastZenPing.DISCOVERY_ZEN_PING_UNICAST_HOSTS_RESOLVE_TIMEOUT;
import static org.elasticsearch.discovery.zen.UnicastZenPing.resolveHostsLists;


class FileBasedUnicastHostsProvider extends AbstractComponent implements UnicastHostsProvider {

    static final String UNICAST_HOSTS_FILE = "unicast_hosts.txt";
    static final String UNICAST_HOST_PREFIX = "#zen_file_unicast_host_";

    private final TransportService transportService;
    private final ExecutorService executorService;

    private final Path unicastHostsFilePath;

    private final TimeValue resolveTimeout;

    FileBasedUnicastHostsProvider(Environment environment, TransportService transportService, ExecutorService executorService) {
        super(environment.settings());
        this.transportService = transportService;
        this.executorService = executorService;
        this.unicastHostsFilePath = environment.configFile().resolve("discovery-file").resolve(UNICAST_HOSTS_FILE);
        this.resolveTimeout = DISCOVERY_ZEN_PING_UNICAST_HOSTS_RESOLVE_TIMEOUT.get(settings);
    }

    @Override
    public List<DiscoveryNode> buildDynamicNodes() {
        List<String> hostsList;
        try (Stream<String> lines = Files.lines(unicastHostsFilePath)) {
            hostsList = lines.filter(line -> line.startsWith("#") == false)                              .collect(Collectors.toList());
        } catch (FileNotFoundException | NoSuchFileException e) {
            logger.warn((Supplier<?>) () -> new ParameterizedMessage("[discovery-file] Failed to find unicast hosts file [{}]",
                                                                        unicastHostsFilePath), e);
            hostsList = Collections.emptyList();
        } catch (IOException e) {
            logger.warn((Supplier<?>) () -> new ParameterizedMessage("[discovery-file] Error reading unicast hosts file [{}]",
                                                                        unicastHostsFilePath), e);
            hostsList = Collections.emptyList();
        }

        final List<DiscoveryNode> discoNodes = new ArrayList<>();
        try {
            discoNodes.addAll(resolveHostsLists(
                executorService,
                logger,
                hostsList,
                1,
                transportService,
                UNICAST_HOST_PREFIX,
                resolveTimeout));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        logger.debug("[discovery-file] Using dynamic discovery nodes {}", discoNodes);

        return discoNodes;
    }

}
