package com.cct.service.impl;

import com.cct.exception.BadRequestException;
import com.cct.model.dto.FuelStationDTO;
import com.cct.model.dto.google.PlaceDTO;
import com.cct.model.dto.google.PlaceDetailsResponseDTO;
import com.cct.model.dto.google.PlacesSearchResponseDTO;
import com.cct.service.api.FuelStationGoogleService;
import com.cct.util.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
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

        return restTemplate
                .getForObject(uri, PlacesSearchResponseDTO.class)
                .getPlaceDTOs()
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toSet());
    }

    @Override
    public FuelStationDTO getFuelStation(String id) {
        URI uri = UriComponentsBuilder.fromHttpUrl(googleApiDetailsUrl)
                .queryParam("placeid", id)
                .queryParam("key", googleApiKey)
                .build()
                .toUri();

        PlaceDTO placeDTO = restTemplate
                .getForObject(uri, PlaceDetailsResponseDTO.class)
                .getPlaceDTO();

        if (placeDTO == null) {
            throw new BadRequestException(FUEL_STATION_NOT_FOUND);
        }

        return ModelMapper.convertToDTO(placeDTO);
    }
}
