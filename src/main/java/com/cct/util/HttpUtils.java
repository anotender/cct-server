package com.cct.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class HttpUtils {

    public Optional<Element> getWebsiteBody(String url) {
        try {
            return Optional.of(Jsoup.connect(url).get().body());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
