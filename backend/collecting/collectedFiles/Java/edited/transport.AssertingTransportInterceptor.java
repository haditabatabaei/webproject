
package org.elasticsearch.transport;

import org.elasticsearch.Version;
import org.elasticsearch.common.io.stream.NamedWriteableRegistry;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.Streamable;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.util.concurrent.ThreadContext;
import org.elasticsearch.plugins.NetworkPlugin;
import org.elasticsearch.plugins.Plugin;
import org.elasticsearch.tasks.Task;
import org.elasticsearch.test.ESIntegTestCase;
import org.elasticsearch.test.VersionUtils;
import org.elasticsearch.test.hamcrest.ElasticsearchAssertions;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;


public final class AssertingTransportInterceptor implements TransportInterceptor {

    private final Random random;
    private final NamedWriteableRegistry namedWriteableRegistry;

    public static final class TestPlugin extends Plugin implements NetworkPlugin {

        private final Settings settings;

        public TestPlugin(Settings settings) {
            this.settings = settings;
        }

        @Override
        public List<TransportInterceptor> getTransportInterceptors(NamedWriteableRegistry namedWriteableRegistry,
                                                                   ThreadContext threadContext) {
            return Collections.singletonList(new AssertingTransportInterceptor(settings, namedWriteableRegistry));
        }
    }

    public AssertingTransportInterceptor(Settings settings, NamedWriteableRegistry namedWriteableRegistry) {
        final long seed = ESIntegTestCase.INDEX_TEST_SEED_SETTING.get(settings);
        random = new Random(seed);
        this.namedWriteableRegistry = namedWriteableRegistry;
    }

    @Override
    public <T extends TransportRequest> TransportRequestHandler<T> interceptHandler(String action, String executor,
                                                                                    boolean forceExecution,
                                                                                    TransportRequestHandler<T> actualHandler) {
        return new TransportRequestHandler<T>() {

            @Override
            public void messageReceived(T request, TransportChannel channel, Task task) throws Exception {
                assertVersionSerializable(request);
                actualHandler.messageReceived(request, channel, task);
            }

            @Override
            public void messageReceived(T request, TransportChannel channel) throws Exception {
                assertVersionSerializable(request);
                actualHandler.messageReceived(request, channel);
            }
        };
    }

    private void assertVersionSerializable(Streamable streamable) {
        Version version = VersionUtils.randomVersionBetween(random, Version.CURRENT.minimumCompatibilityVersion(), Version.CURRENT);
        ElasticsearchAssertions.assertVersionSerializable(version, streamable, namedWriteableRegistry);

    }

    @Override
    public AsyncSender interceptSender(final AsyncSender sender) {
        return new AsyncSender() {
            @Override
            public <T extends TransportResponse> void sendRequest(Transport.Connection connection, String action, TransportRequest request,
                                                                  TransportRequestOptions options,
                                                                  final TransportResponseHandler<T> handler) {
                assertVersionSerializable(request);
                sender.sendRequest(connection, action, request, options, new TransportResponseHandler<T>() {
                    @Override
                    public T read(StreamInput in) throws IOException {
                        return handler.read(in);
                    }

                    @Override
                    public void handleResponse(T response) {
                        assertVersionSerializable(response);
                        handler.handleResponse(response);
                    }

                    @Override
                    public void handleException(TransportException exp) {
                        handler.handleException(exp);
                    }

                    @Override
                    public String executor() {
                        return handler.executor();
                    }
                });
            }
        };
    }


}
