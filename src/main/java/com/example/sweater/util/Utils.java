package com.example.sweater.util;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Utils {

    private static String URL = "http://localhost:9000/rest/test";

    public String getApiText() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(URL, String.class).getBody();
    }
}
