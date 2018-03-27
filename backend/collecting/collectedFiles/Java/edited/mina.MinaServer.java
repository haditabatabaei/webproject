
package com.alibaba.dubbo.remoting.transport.mina;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.alibaba.dubbo.common.utils.ExecutorUtil;
import com.alibaba.dubbo.common.utils.NamedThreadFactory;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.RemotingException;
import com.alibaba.dubbo.remoting.transport.AbstractServer;
import com.alibaba.dubbo.remoting.transport.dispatcher.ChannelHandlers;

import org.apache.mina.common.IoSession;
import org.apache.mina.common.ThreadModel;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import java.net.InetSocketAddress;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;


public class MinaServer extends AbstractServer {

    private static final Logger logger = LoggerFactory.getLogger(MinaServer.class);

    private SocketAcceptor acceptor;

    public MinaServer(URL url, ChannelHandler handler) throws RemotingException {
        super(url, ChannelHandlers.wrap(handler, ExecutorUtil.setThreadName(url, SERVER_THREAD_POOL_NAME)));
    }

    @Override
    protected void doOpen() throws Throwable {
                acceptor = new SocketAcceptor(getUrl().getPositiveParameter(Constants.IO_THREADS_KEY, Constants.DEFAULT_IO_THREADS),
                Executors.newCachedThreadPool(new NamedThreadFactory("MinaServerWorker",
                        true)));
                SocketAcceptorConfig cfg = (SocketAcceptorConfig) acceptor.getDefaultConfig();
        cfg.setThreadModel(ThreadModel.MANUAL);
                acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MinaCodecAdapter(getCodec(), getUrl(), this)));

        acceptor.bind(getBindAddress(), new MinaHandler(getUrl(), this));
    }

    @Override
    protected void doClose() throws Throwable {
        try {
            if (acceptor != null) {
                acceptor.unbind(getBindAddress());
            }
        } catch (Throwable e) {
            logger.warn(e.getMessage(), e);
        }
    }

    public Collection<Channel> getChannels() {
        Set<IoSession> sessions = acceptor.getManagedSessions(getBindAddress());
        Collection<Channel> channels = new HashSet<Channel>();
        for (IoSession session : sessions) {
            if (session.isConnected()) {
                channels.add(MinaChannel.getOrAddChannel(session, getUrl(), this));
            }
        }
        return channels;
    }

    public Channel getChannel(InetSocketAddress remoteAddress) {
        Set<IoSession> sessions = acceptor.getManagedSessions(getBindAddress());
        for (IoSession session : sessions) {
            if (session.getRemoteAddress().equals(remoteAddress)) {
                return MinaChannel.getOrAddChannel(session, getUrl(), this);
            }
        }
        return null;
    }

    public boolean isBound() {
        return acceptor.isManaged(getBindAddress());
    }

}