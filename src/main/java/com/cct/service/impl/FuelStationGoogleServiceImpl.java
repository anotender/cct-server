package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.dto.FuelStationDTO;
import com.cct.service.api.FuelStationGoogleService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.cct.exception.ErrorInfo.FUEL_STATION_NOT_FOUND;

@Service
public class FuelStationGoogleServiceImpl implements FuelStationGoogleService {

    private final RestTemplate restTemplate;
    private final String googleApiKey;
    private final String googleApiNearbySearchUrl;
    private final String googleApiDetailsUrl;

    public FuelStationGoogleServiceImpl(
            RestTemplate restTemplate,
            @Value("${google.api.key}") String googleApiKey,
            @Value("${google.api.nearbysearch.url}") String googleApiNearbySearchUrl,
            @Value("${google.api.details.url}") String googleApiDetailsUrl
    ) {
        this.restTemplate = restTemplate;
        this.googleApiKey = googleApiKey;
        this.googleApiNearbySearchUrl = googleApiNearbySearchUrl;
        this.googleApiDetailsUrl = googleApiDetailsUrl;
    }

    @Override
    public Collection<FuelStationDTO> getFuelStationsInArea(int radius, double latitude, double longitude) {
        URI uri = UriComponentsBuilder.fromHttpUrl(googleApiNearbySearchUrl)
                .queryParam("location", latitude + "," + longitude)
                .queryParam("radius", radius)
                .queryParam("type", "gas_station")
                .queryParam("key", googleApiKey)
                .build()
                .toUri();
        return getResults(uri);
    }

    @Override
    public FuelStationDTO getFuelStation(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(googleApiDetailsUrl)
                .queryParam("placeid", id)
                .queryParam("key", googleApiKey)
                .build()
                .toUri();
        return getResult(uri).orElseThrow(() -> new BadRequestException(FUEL_STATION_NOT_FOUND));
    }

    private Collection<FuelStationDTO> getResults(URI uri) {
        return new JSONObject(restTemplate.getForObject(uri, String.class))
                .getJSONArray("results")
                .toList()
                .stream()
                .filter(o -> o instanceof Map)
                .map(this::mapToJSONObject)
                .map(this::mapToFuelStation)
                .collect(Collectors.toSet());
    }

    private Optional<FuelStationDTO> getResult(URI uri) {
        JSONObject result = new JSONObject(restTemplate.getForObject(uri, String.class)).getJSONObject("result");
        return Optional.ofNullable(result).map(this::mapToFuelStation);
    }

    private JSONObject mapToJSONObject(Object o) {
        return new JSONObject((Map) o);
    }

    private FuelStationDTO mapToFuelStation(JSONObject o) {
        String name = o.getString("name");
        String address = o.getString("vicinity");
        String id = o.getString("id");
        Double latitude = o.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        Double longitude = o.getJSONObject("geometry").getJSONObject("location").getDouble("lng");
        return new FuelStationDTO(id, latitude, longitude, name, address, Collections.emptySet());
    }
}
