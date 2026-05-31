package com.cinematchai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class IntegrationService {

    private final RestTemplate restTemplate;

    public IntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map getWeather(double lat, double lon, String apiKey) {
        if (apiKey == null || apiKey.isBlank()) return Map.of();
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=metric", lat, lon, apiKey);
        return restTemplate.getForObject(url, Map.class);
    }

    public Map getReverseGeocode(double lat, double lon) {
        String url = String.format("https://nominatim.openstreetmap.org/reverse?format=json&lat=%s&lon=%s", lat, lon);
        return restTemplate.getForObject(url, Map.class);
    }

    public Map[] getCountryInfo(String name) {
        String url = String.format("https://restcountries.com/v3.1/name/%s", name);
        return restTemplate.getForObject(url, Map[].class);
    }

    public Map getWikipediaExtract(String title) {
        try {
            String url = String.format("https://en.wikipedia.org/api/rest_v1/page/summary/%s", java.net.URLEncoder.encode(title, java.nio.charset.StandardCharsets.UTF_8));
            return restTemplate.getForObject(url, Map.class);
        } catch (Exception e) {
            return Map.of();
        }
    }
}
