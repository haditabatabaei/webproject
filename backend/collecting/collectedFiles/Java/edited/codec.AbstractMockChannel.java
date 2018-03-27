
package com.alibaba.dubbo.remoting.codec;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.remoting.Channel;
import com.alibaba.dubbo.remoting.ChannelHandler;
import com.alibaba.dubbo.remoting.RemotingException;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

public class AbstractMockChannel implements Channel {
    public static final String LOCAL_ADDRESS = "local";
    public static final String REMOTE_ADDRESS = "remote";
    public static final String ERROR_WHEN_SEND = "error_when_send";
    InetSocketAddress localAddress;
    InetSocketAddress remoteAddress;
    private URL remoteUrl;
    private ChannelHandler handler;
    private boolean isClosed;
    private volatile boolean closing;
    private Map<String, Object> attributes = new HashMap<String, Object>(1);
    private volatile Object receivedMessage = null;

    public AbstractMockChannel() {

    }

    public AbstractMockChannel(URL remoteUrl) {
        this.remoteUrl = remoteUrl;
        this.remoteAddress = NetUtils.toAddress(remoteUrl.getParameter(REMOTE_ADDRESS));
        this.localAddress = NetUtils.toAddress(remoteUrl.getParameter(LOCAL_ADDRESS));
    }

    public AbstractMockChannel(ChannelHandler handler) {
        this.handler = handler;
    }

    public URL getUrl() {
        return remoteUrl;
    }

    public ChannelHandler getChannelHandler() {
        return handler;
    }

    public InetSocketAddress getLocalAddress() {
        return localAddress;
    }

    public void send(Object message) throws RemotingException {
        if (remoteUrl.getParameter(ERROR_WHEN_SEND, Boolean.FALSE)) {
            receivedMessage = null;
            throw new RemotingException(localAddress, remoteAddress, "mock error");
        } else {
            receivedMessage = message;
        }
    }

    public void send(Object message, boolean sent) throws RemotingException {
        send(message);
    }

    public void close() {
        close(0);
    }

    public void close(int timeout) {
        isClosed = true;
    }

    @Override
    public void startClose() {
        closing = true;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public InetSocketAddress getRemoteAddress() {
        return remoteAddress;
    }

    public boolean isConnected() {
        return isClosed;
    }

    public boolean hasAttribute(String key) {
        return attributes.containsKey(key);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }

    public void removeAttribute(String key) {
        attributes.remove(key);
    }

    public Object getReceivedMessage() {
        return receivedMessage;
    }
}
