package com.cct.util;

import com.cct.model.*;
import com.cct.model.dto.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Component
public class ModelMapper {

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setCars(userDTO
                .getCars()
                .stream()
                .map(this::convertToEntity)
                .collect(Collectors.toSet())
        );
        user.setRatings(userDTO
                .getRatings()
                .stream()
                .map(this::convertToEntity)
                .collect(Collectors.toSet())
        );

        return user;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setCars(user
                .getCars()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet())
        );
        userDTO.setRatings(user
                .getRatings()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toSet())
        );

        return userDTO;
    }

    public Car convertToEntity(CarDTO carDTO) {
        Car car = new Car();

        car.setId(carDTO.getId());
        car.setName(carDTO.getName());

        Version version = new Version();
        version.setId(carDTO.getVersionId());
        car.setVersion(version);

        User user = new User();
        user.setId(carDTO.getUserId());
        car.setUser(user);

        return car;
    }

    public CarDTO convertToDTO(Car car) {
        CarDTO carDTO = new CarDTO();

        carDTO.setId(car.getId());
        carDTO.setUserId(car.getUser().getId());
        carDTO.setVersionId(car.getVersion().getId());
        carDTO.setName(car.getName());

        return carDTO;
    }

    public MakeDTO convertToDTO(Make make) {
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

    public ModelDTO convertToDTO(Model model) {
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

    public VersionDTO convertToDTO(Version version) {
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

    public FuelRefill convertToEntity(FuelRefillDTO fuelRefillDTO) {
        FuelRefill fuelRefill = new FuelRefill();

        fuelRefill.setId(fuelRefillDTO.getId());
        fuelRefill.setDistance(fuelRefillDTO.getDistance());
        fuelRefill.setLiters(fuelRefillDTO.getLiters());
        fuelRefill.setDate(convertMillisToLocalDateTime(fuelRefillDTO.getDate()));

        Car car = new Car();
        car.setId(fuelRefillDTO.getCarId());
        fuelRefill.setCar(car);

        return fuelRefill;
    }

    public FuelRefillDTO convertToDTO(FuelRefill fuelRefill) {
        FuelRefillDTO fuelRefillDTO = new FuelRefillDTO();

        fuelRefillDTO.setId(fuelRefill.getId());
        fuelRefillDTO.setDistance(fuelRefill.getDistance());
        fuelRefillDTO.setLiters(fuelRefill.getLiters());
        fuelRefillDTO.setDate(convertLocalDateTimeToMillis(fuelRefill.getDate()));
        fuelRefillDTO.setCarId(fuelRefill.getCar().getId());

        return fuelRefillDTO;
    }

    public Rating convertToEntity(RatingDTO ratingDTO) {
        Rating rating = new Rating();

        rating.setId(ratingDTO.getId());
        rating.setComment(ratingDTO.getComment());
        rating.setPoints(ratingDTO.getPoints());
        rating.setDate(convertMillisToLocalDateTime(ratingDTO.getDate()));

        Version version = new Version();
        version.setId(ratingDTO.getVersionId());
        rating.setVersion(version);

        User user = new User();
        user.setId(ratingDTO.getUserId());
        rating.setUser(user);

        return rating;
    }

    public RatingDTO convertToDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();

        ratingDTO.setId(rating.getId());
        ratingDTO.setPoints(rating.getPoints());
        ratingDTO.setComment(rating.getComment());
        ratingDTO.setDate(convertLocalDateTimeToMillis(rating.getDate()));
        ratingDTO.setVersionId(rating.getVersion().getId());
        ratingDTO.setUserId(rating.getUser().getId());

        return ratingDTO;
    }

    public FuelPrice convertToEntity(FuelPriceDTO fuelPriceDTO) {
        FuelPrice fuelPrice = new FuelPrice();

        fuelPrice.setId(fuelPriceDTO.getId());
        fuelPrice.setDate(convertMillisToLocalDateTime(fuelPriceDTO.getDate()));
        fuelPrice.setFuel(Fuel.valueOf(fuelPriceDTO.getFuel()));
        fuelPrice.setFuelStationId(fuelPriceDTO.getFuelStationId());
        fuelPrice.setPrice(fuelPriceDTO.getPrice());

        return fuelPrice;
    }

    public FuelPriceDTO convertToDTO(FuelPrice fuelPrice) {
        FuelPriceDTO fuelPriceDTO = new FuelPriceDTO();

        fuelPriceDTO.setId(fuelPrice.getId());
        fuelPriceDTO.setDate(convertLocalDateTimeToMillis(fuelPrice.getDate()));
        fuelPriceDTO.setFuel(fuelPrice.getFuel().name());
        fuelPriceDTO.setFuelStationId(fuelPrice.getFuelStationId());
        fuelPriceDTO.setPrice(fuelPrice.getPrice());

        return fuelPriceDTO;
    }

    private Long convertLocalDateTimeToMillis(LocalDateTime date) {
        return date.toInstant(ZoneOffset.ofTotalSeconds(0)).toEpochMilli();
    }

    private LocalDateTime convertMillisToLocalDateTime(Long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
    }
}
