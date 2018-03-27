

package org.elasticsearch.transport.netty4;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.common.lease.Releasable;
import org.elasticsearch.common.metrics.CounterMetric;

import java.io.IOException;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ChannelHandler.Sharable
public class Netty4OpenChannelsHandler extends ChannelInboundHandlerAdapter implements Releasable {

    final Set<Channel> openChannels = Collections.newSetFromMap(new ConcurrentHashMap<>());
    final CounterMetric openChannelsMetric = new CounterMetric();
    final CounterMetric totalChannelsMetric = new CounterMetric();

    final Logger logger;

    public Netty4OpenChannelsHandler(Logger logger) {
        this.logger = logger;
    }

    final ChannelFutureListener remover = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            boolean removed = openChannels.remove(future.channel());
            if (removed) {
                openChannelsMetric.dec();
            }
            if (logger.isTraceEnabled()) {
                logger.trace("channel closed: {}", future.channel());
            }
        }
    };

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (logger.isTraceEnabled()) {
            logger.trace("channel opened: {}", ctx.channel());
        }
        final boolean added = openChannels.add(ctx.channel());
        if (added) {
            openChannelsMetric.inc();
            totalChannelsMetric.inc();
            ctx.channel().closeFuture().addListener(remover);
        }

        super.channelActive(ctx);
    }

    public long numberOfOpenChannels() {
        return openChannelsMetric.count();
    }

    public long totalChannels() {
        return totalChannelsMetric.count();
    }

    @Override
    public void close() {
        try {
            Netty4Utils.closeChannels(openChannels);
        } catch (IOException e) {
            logger.trace("exception while closing channels", e);
        }
        openChannels.clear();
    }

}