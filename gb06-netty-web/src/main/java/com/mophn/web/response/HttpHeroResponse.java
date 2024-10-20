package com.mophn.web.response;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.mophn.web.api.HeroResponse;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpHeroResponse implements HeroResponse {
    private final HttpRequest httpRequest;
    private final ChannelHandlerContext ctx;

    private Map<CharSequence, CharSequence> headerMap;

    public void setHeaderMap(Map<CharSequence, CharSequence> headerMap) {
        this.headerMap = headerMap;
    }

    public HttpHeroResponse(HttpRequest httpRequest, ChannelHandlerContext ctx) {
        this.httpRequest = httpRequest;
        this.ctx = ctx;
    }


    @Override
    public void write(String content) throws Exception {
        if (StrUtil.isBlank(content)) {
            return;
        }

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(content.getBytes(StandardCharsets.UTF_8)));


        HttpHeaders headers = response.headers();
        headers.set(HttpHeaderNames.CONTENT_TYPE, "text/json");
        headers.set(HttpHeaderNames.CONTENT_LENGTH, String.valueOf(response.content().readableBytes()));
        headers.set(HttpHeaderNames.EXPIRES, "0");
        if (HttpHeaderUtil.isKeepAlive(httpRequest)) {
            headers.set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        }

        if (CollectionUtil.isNotEmpty(headerMap)) {
            headerMap.forEach(headers::set);
        }


        ctx.writeAndFlush(response);

    }

}
