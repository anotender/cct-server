package com.cct.util;

import com.cct.model.*;
import com.cct.model.dto.*;
import com.cct.model.dto.google.PlaceDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

public final class ModelMapper {

    private ModelMapper() {
    }

    public static User convertToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCars(userDTO
                .getCars()
                .stream()
                .map(ModelMapper::convertToEntity)
                .collect(Collectors.toSet())
        );
        user.setRatings(userDTO
                .getRatings()
                .stream()
                .map(ModelMapper::convertToEntity)
                .collect(Collectors.toSet())
        );

        return user;
    }

    public static UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setCars(user
                .getCars()
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toSet())
        );
        userDTO.setRatings(user
                .getRatings()
                .stream()
                .map(ModelMapper::convertToDTO)
                .collect(Collectors.toSet())
        );

        return userDTO;
    }

    public static Car convertToEntity(CarDTO carDTO) {
        Car car = new Car();

        car.setId(carDTO.getId());
        car.setName(carDTO.getName());
        car.setVersion(new Version(carDTO.getVersionId()));
        car.setUser(new User(carDTO.getUserId()));

        return car;
    }

    public static CarDTO convertToDTO(Car car) {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(car.getId());
        carDTO.setUserId(car.getUser().getId());
        carDTO.setVersionId(car.getVersion().getId());
        carDTO.setName(car.getName());

        return carDTO;
    }

    public static MakeDTO convertToDTO(Make make) {
        MakeDTO makeDTO = new MakeDTO();

        makeDTO.setId(make.getId());
        makeDTO.setName(make.getName());
        makeDTO.setLogoUrl(make.getLogoUrl());
        makeDTO.setModels(make
                .getModels()
                .stream()
                .map(Model::getId)
                .collect(Collectors.toSet())
        );

        return makeDTO;
    }

    public static ModelDTO convertToDTO(Model model) {
        ModelDTO modelDTO = new ModelDTO();

        modelDTO.setId(model.getId());
        modelDTO.setMakeId(model.getMake().getId());
        modelDTO.setName(model.getName());
        modelDTO.setBody(model.getBody());
        modelDTO.setVersions(model
                .getVersions()
                .stream()
                .map(Version::getId)
                .collect(Collectors.toSet())
        );

        return modelDTO;
    }

    public static Version convertToEntity(VersionDTO versionDTO) {
        Version version = new Version();

        version.setId(versionDTO.getId());
        version.setName(versionDTO.getName());
        version.setModel(new Model(versionDTO.getModelId()));
        version.setFuel(Fuel.valueOf(versionDTO.getFuel()));
        version.setYears(versionDTO.getYears());
        version.setCityFuelConsumption(versionDTO.getCityFuelConsumption());
        version.setHighwayFuelConsumption(versionDTO.getHighwayFuelConsumption());
        version.setMixedFuelConsumption(versionDTO.getMixedFuelConsumption());
        version.setAverageFuelConsumption(versionDTO.getAverageFuelConsumption());
        version.setCars(versionDTO
                .getCars()
                .stream()
                .map(Car::new)
                .collect(Collectors.toSet())
        );
        version.setRatings(versionDTO
                .getRatings()
                .stream()
                .map(Rating::new)
                .collect(Collectors.toSet())
        );

        return version;
    }

    public static VersionDTO convertToDTO(Version version) {
        VersionDTO versionDTO = new VersionDTO();

        versionDTO.setId(version.getId());
        versionDTO.setName(version.getName());
        versionDTO.setModelId(version.getModel().getId());
        versionDTO.setFuel(version.getFuel().name());
        versionDTO.setYears(version.getYears());
        versionDTO.setCityFuelConsumption(version.getCityFuelConsumption());
        versionDTO.setHighwayFuelConsumption(version.getHighwayFuelConsumption());
        versionDTO.setMixedFuelConsumption(version.getMixedFuelConsumption());
        versionDTO.setAverageFuelConsumption(version.getAverageFuelConsumption());
        versionDTO.setCars(version
                .getCars()
                .stream()
                .map(Car::getId)
                .collect(Collectors.toSet())
        );
        versionDTO.setRatings(version
                .getRatings()
                .stream()
                .map(Rating::getId)
                .collect(Collectors.toSet())
        );

        return versionDTO;
    }

    public static FuelRefill convertToEntity(FuelRefillDTO fuelRefillDTO) {
        FuelRefill fuelRefill = new FuelRefill();

        fuelRefill.setId(fuelRefillDTO.getId());
        fuelRefill.setDistance(fuelRefillDTO.getDistance());
        fuelRefill.setLiters(fuelRefillDTO.getLiters());
        fuelRefill.setAverageFuelConsumption(fuelRefillDTO.getAverageFuelConsumption());
        fuelRefill.setDate(convertMillisToLocalDateTime(fuelRefillDTO.getDate()));
        fuelRefill.setCar(new Car(fuelRefillDTO.getCarId()));

        if (fuelRefillDTO.getFuelPriceId() != null) {
            fuelRefill.setFuelPrice(new FuelPrice(fuelRefillDTO.getFuelPriceId()));
        }

        return fuelRefill;
    }

    public static FuelRefillDTO convertToDTO(FuelRefill fuelRefill) {
        FuelRefillDTO fuelRefillDTO = new FuelRefillDTO();

        fuelRefillDTO.setId(fuelRefill.getId());
        fuelRefillDTO.setDistance(fuelRefill.getDistance());
        fuelRefillDTO.setLiters(fuelRefill.getLiters());
        fuelRefillDTO.setAverageFuelConsumption(fuelRefill.getAverageFuelConsumption());
        fuelRefillDTO.setDate(convertLocalDateTimeToMillis(fuelRefill.getDate()));
        fuelRefillDTO.setCarId(fuelRefill.getCar().getId());
        if (fuelRefill.getFuelPrice() != null) {
            fuelRefillDTO.setFuelPriceId(fuelRefill.getFuelPrice().getId());
        }

        return fuelRefillDTO;
    }

    public static Rating convertToEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();

        rating.setId(ratingDTO.getId());
        rating.setComment(ratingDTO.getComment());
        rating.setPoints(ratingDTO.getPoints());
        rating.setDate(convertMillisToLocalDateTime(ratingDTO.getDate()));
        rating.setVersion(new Version(ratingDTO.getVersionId()));
        rating.setUser(new User(ratingDTO.getUserId()));

        return rating;
    }

    public static RatingDTO convertToDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();

        ratingDTO.setId(rating.getId());
        ratingDTO.setPoints(rating.getPoints());
        ratingDTO.setComment(rating.getComment());
        ratingDTO.setDate(convertLocalDateTimeToMillis(rating.getDate()));
        ratingDTO.setVersionId(rating.getVersion().getId());
        ratingDTO.setUserId(rating.getUser().getId());

        return ratingDTO;
    }

    public static FuelPrice convertToEntity(FuelPriceDTO fuelPriceDTO) {
        FuelPrice fuelPrice = new FuelPrice();

        fuelPrice.setId(fuelPriceDTO.getId());
        fuelPrice.setDate(convertMillisToLocalDateTime(fuelPriceDTO.getDate()));
        fuelPrice.setFuel(Fuel.valueOf(fuelPriceDTO.getFuel()));
        fuelPrice.setFuelStationId(fuelPriceDTO.getFuelStationId());
        fuelPrice.setPrice(fuelPriceDTO.getPrice());

        return fuelPrice;
    }

    public static FuelPriceDTO convertToDTO(FuelPrice fuelPrice) {
        FuelPriceDTO fuelPriceDTO = new FuelPriceDTO();

        fuelPriceDTO.setId(fuelPrice.getId());
        fuelPriceDTO.setDate(convertLocalDateTimeToMillis(fuelPrice.getDate()));
        fuelPriceDTO.setFuel(fuelPrice.getFuel().name());
        fuelPriceDTO.setFuelStationId(fuelPrice.getFuelStationId());
        fuelPriceDTO.setPrice(fuelPrice.getPrice());

        return fuelPriceDTO;
    }

    public static FuelStationDTO convertToDTO(PlaceDTO placeDTO) {
        FuelStationDTO fuelStationDTO = new FuelStationDTO();

        fuelStationDTO.setId(placeDTO.getPlaceId());
        fuelStationDTO.setAddress(placeDTO.getVicinity());
        fuelStationDTO.setName(placeDTO.getName());
        fuelStationDTO.setLatitude(placeDTO.getGeometryDTO().getLocationDTO().getLat());
        fuelStationDTO.setLongitude(placeDTO.getGeometryDTO().getLocationDTO().getLng());

        return fuelStationDTO;
    }

    private static Long convertLocalDateTimeToMillis(LocalDateTime date) {
        return date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    private static LocalDateTime convertMillisToLocalDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
