package com.mophn.web.api;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.mophn.web.response.HttpHeroResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class DefaultServlet extends HeroServlet{
    private final Logger log = LoggerFactory.getLogger(getClass());
    private static final String STATIC = "static";
    @Override
    public void doGet(HeroRequest request, HeroResponse response) throws Exception {
        String uri = request.getUri();

        if (isStaticResource(uri)) {
            URL resource = this.getClass().getClassLoader().getResource(STATIC + "/" + uri);
            if (resource == null) {
                response.write("404 - no found: " + uri);
                return;
            }

            byte[] bytes = FileUtil.readBytes(resource.getFile());
            HttpHeroResponse heroResponse = (HttpHeroResponse)response;
            Map<CharSequence, CharSequence> headerMap = new HashMap<>();
            headerMap.put(HttpHeaderNames.CONTENT_TYPE, getContentType(uri.substring(uri.lastIndexOf(".")+1)));
            heroResponse.setHeaderMap(headerMap);
            heroResponse.write(bytes);

        } else {
            if (uri.indexOf("?") > 0) {
                uri = uri.substring(0, uri.indexOf("?"));
            }
            response.write("404 - no this servlet : " + uri);
        }





    }

    @Override
    public void doPost(HeroRequest request, HeroResponse response) throws Exception {
        doGet(request, response);
    }

    private boolean isStaticResource(String uri) {
        return StrUtil.endWithAny(uri, ".js|.html|.css|.jpg".split("\\|"));
    }

    private String getContentType(String type) {
        String contentType = "";
        switch (type) {
            case "js":
            case "css":
                contentType = "text/plain";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "jpg":
            case "jpeg":
                contentType = "image/jpeg";
                break;
            default:
                contentType = "text/html";
        }
        return contentType;
    }
}
