

package org.elasticsearch.nio;

import org.elasticsearch.test.ESTestCase;
import org.junit.Before;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.function.Consumer;

import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AcceptorEventHandlerTests extends ESTestCase {

    private AcceptorEventHandler handler;
    private ChannelFactory<NioServerSocketChannel, NioSocketChannel> channelFactory;
    private NioServerSocketChannel channel;
    private DoNotRegisterContext context;
    private RoundRobinSupplier<SocketSelector> selectorSupplier;

    @Before
    @SuppressWarnings("unchecked")
    public void setUpHandler() throws IOException {
        channelFactory = mock(ChannelFactory.class);
        ArrayList<SocketSelector> selectors = new ArrayList<>();
        selectors.add(mock(SocketSelector.class));
        selectorSupplier = new RoundRobinSupplier<>(selectors.toArray(new SocketSelector[selectors.size()]));
        handler = new AcceptorEventHandler(logger, selectorSupplier);

        channel = new NioServerSocketChannel(mock(ServerSocketChannel.class));
        context = new DoNotRegisterContext(channel, mock(AcceptingSelector.class), mock(Consumer.class));
        channel.setContext(context);
    }

    public void testHandleRegisterSetsOP_ACCEPTInterest() throws IOException {
        assertNull(context.getSelectionKey());

        handler.handleRegistration(context);

        assertEquals(SelectionKey.OP_ACCEPT, channel.getContext().getSelectionKey().interestOps());
    }

    public void testRegisterAddsAttachment() throws IOException {
        assertNull(context.getSelectionKey());

        handler.handleRegistration(context);

        assertEquals(context, context.getSelectionKey().attachment());
    }

    public void testHandleAcceptCallsChannelFactory() throws IOException {
        NioSocketChannel childChannel = new NioSocketChannel(mock(SocketChannel.class));
        NioSocketChannel nullChannel = null;
        when(channelFactory.acceptNioChannel(same(context), same(selectorSupplier))).thenReturn(childChannel, nullChannel);

        handler.acceptChannel(context);

        verify(channelFactory, times(2)).acceptNioChannel(same(context), same(selectorSupplier));
    }

    @SuppressWarnings("unchecked")
    public void testHandleAcceptCallsServerAcceptCallback() throws IOException {
        NioSocketChannel childChannel = new NioSocketChannel(mock(SocketChannel.class));
        SocketChannelContext childContext = mock(SocketChannelContext.class);
        childChannel.setContext(childContext);
        ServerChannelContext serverChannelContext = mock(ServerChannelContext.class);
        channel = new NioServerSocketChannel(mock(ServerSocketChannel.class));
        channel.setContext(serverChannelContext);
        when(serverChannelContext.getChannel()).thenReturn(channel);
        when(channelFactory.acceptNioChannel(same(context), same(selectorSupplier))).thenReturn(childChannel);

        handler.acceptChannel(serverChannelContext);

        verify(serverChannelContext).acceptChannels(selectorSupplier);
    }

    private class DoNotRegisterContext extends ServerChannelContext {


        @SuppressWarnings("unchecked")
        DoNotRegisterContext(NioServerSocketChannel channel, AcceptingSelector selector, Consumer<NioSocketChannel> acceptor) {
            super(channel, channelFactory, selector, acceptor, mock(Consumer.class));
        }

        @Override
        public void register() {
            setSelectionKey(new TestSelectionKey(0));
        }
    }
}
