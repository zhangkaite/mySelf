package org.jam.dataflow.web;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.jam.dataflow.Dataflow;

import java.util.List;
import java.util.Map;

/**
 * Created by James on 16/5/13.
 */
public class DataflowManageHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri());
        Map<String, List<String>> params = queryStringDecoder.parameters();

        FullHttpResponse fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK);

        try{
            if(!params.get("auth").get(0).equals("123456")){
                fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer("Invalid authority.".getBytes()));
            }else{
                String cmd = params.get("cmd").get(0);
                if(cmd.equals("recovering")){
                    Dataflow.setModeRecovering();
                    fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer("Set mode recovering ok!".getBytes()));
                }else if(cmd.equals("high")){
                    Dataflow.setModeHigh();
                    fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer("Set mode high ok".getBytes()));
                }else if(cmd.equals("mode")){
                    fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer(("The mode of dataflow is: " + Dataflow.getDataMode().getDescription()).getBytes()));
                }
            }
        }catch (Exception e){
            fullHttpResponse.content().writeBytes(Unpooled.copiedBuffer(e.getMessage().getBytes()));
        }

        ctx.writeAndFlush(fullHttpResponse).addListener(ChannelFutureListener.CLOSE);

    }

}
