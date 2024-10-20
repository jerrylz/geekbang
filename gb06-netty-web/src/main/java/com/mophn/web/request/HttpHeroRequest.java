package com.mophn.web.request;

import cn.hutool.core.collection.CollectionUtil;
import com.mophn.web.api.HeroRequest;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

public class HttpHeroRequest implements HeroRequest {

    private final HttpRequest httpRequest;

    public HttpHeroRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public String getUri() {
        return httpRequest.uri();
    }

    @Override
    public String getPath() {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.uri());
        return queryStringDecoder.path();
    }

    @Override
    public String getMethod() {
        return httpRequest.method().toString();
    }

    @Override
    public Map<String, List<String>> getParameters() {
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.uri());
        return queryStringDecoder.parameters();
    }

    @Override
    public List<String> getParameters(String name) {
        return getParameters().get(name);
    }

    @Override
    public String getParameter(String name) {
        List<String> parameters = getParameters(name);
        if (CollectionUtil.isEmpty(parameters)) {
            return null;
        }
        return parameters.get(0);
    }
}
