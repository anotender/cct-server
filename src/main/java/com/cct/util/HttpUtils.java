package com.cct.util;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.springframework.http.HttpMethod.GET;

@Component
public class HttpUtils {

    private final RestTemplate rt;
    private final HttpEntity<String> httpEntity;

    public HttpUtils(RestTemplateBuilder restTemplateBuilder) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.90 Safari/537.36");

        this.httpEntity = new HttpEntity<>("parameters", headers);
        this.rt = restTemplateBuilder.build();
    }

    public JSONObject getJSONObject(String url) {
        return new JSONObject(doGet(url));
    }

    public JSONArray getJSONArray(String url) {
        return new JSONArray(doGet(url));
    }

    private String doGet(String url) {
        return rt
                .exchange(url, GET, httpEntity, String.class)
                .getBody()
                .replace("?(", "")
                .replace(");", "");
    }
}
