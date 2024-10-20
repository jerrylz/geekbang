package com.mophn.web.handler;

import com.mophn.web.api.DefaultServlet;
import com.mophn.web.api.HeroServlet;
import com.mophn.web.request.HttpHeroRequest;
import com.mophn.web.response.HttpHeroResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.AsciiString;
import io.netty.handler.codec.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class HttpHeroHandler extends SimpleChannelInboundHandler<HttpRequest> {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<String, String> nameToClassNameMap;
    private final Map<String, HeroServlet> nameToServletMap;

    public HttpHeroHandler(Map<String, String> nameToClassNameMap, Map<String, HeroServlet> nameToServletMap) {
        this.nameToClassNameMap = nameToClassNameMap;
        this.nameToServletMap = nameToServletMap;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, HttpRequest request) throws Exception {
        HttpHeroRequest httpHeroRequest = new HttpHeroRequest(request);
        HttpHeroResponse httpHeroResponse = new HttpHeroResponse(request, ctx);
        String uri = request.uri();



        String servletName = "";
        if (uri.indexOf("?") > 0) {
            servletName = uri.substring(uri.lastIndexOf("/") + 1, uri.indexOf("?"));
        } else {
            servletName = uri.substring(uri.lastIndexOf("/") + 1);
        }

        HeroServlet heroServlet = new DefaultServlet();
        if (nameToServletMap.containsKey(servletName)) {
            heroServlet = nameToServletMap.get(servletName);
        } else if (nameToClassNameMap.containsKey(servletName)){
            synchronized (this) {
                if (!nameToServletMap.containsKey(servletName)) {
                    String className = nameToClassNameMap.get(servletName);
                    heroServlet = (HeroServlet)Class.forName(className).newInstance();
                    nameToServletMap.put(servletName, heroServlet);
                }
            }
        }



        AsciiString methodName = request.method().name();

        if (methodName.equalsIgnoreCase("GET")) {
            heroServlet.doGet(httpHeroRequest, httpHeroResponse);
        } else if (methodName.equalsIgnoreCase("POST")) {
            heroServlet.doPost(httpHeroRequest, httpHeroResponse);
        }

        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("异常: {}", cause.getMessage(), cause);
        ctx.close();
    }


}
