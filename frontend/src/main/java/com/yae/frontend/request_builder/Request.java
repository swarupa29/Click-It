package com.yae.frontend.request_builder;

import java.net.http.HttpHeaders;

import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Request {
    String url;
    String method;
    Object body;
    Class<?> template;
    HttpHeaders httpHeaders;
    RestTemplate restTemplate;

    public Request(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.body="";
        this.template = Object.class;
    }

    public Object send() {
        if (method.toLowerCase().equals("get")) {
            return restTemplate.getForObject(url, template);
        }

        else {
            return restTemplate.postForObject(url, body, template);
        }
    }
}
