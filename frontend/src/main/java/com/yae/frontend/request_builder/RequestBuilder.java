package com.yae.frontend.request_builder;

import java.net.http.HttpHeaders;

import org.springframework.web.client.RestTemplate;

public class RequestBuilder {
    private Request request;

    public RequestBuilder(RestTemplate restTemplate) {
        this.request = new Request(restTemplate);
    }

    public RequestBuilder setUrl(String url) {
        this.request.setUrl(url);
        return this;
    }

    public RequestBuilder setHeaders(HttpHeaders httpHeaders) {
        this.request.setHttpHeaders(httpHeaders);;
        return this;
    }

    public RequestBuilder forTemplate(Class<?> template) {
        this.request.setTemplate(template);
        return this;
    }

    public RequestBuilder setBody(Object body) {
        this.request.setBody(body);
        return this;
    }

    public RequestBuilder setMethod(String method) {
        this.request.setMethod(method);
        return this;
    }

    public Request build() {
        return this.request;
    }
}
