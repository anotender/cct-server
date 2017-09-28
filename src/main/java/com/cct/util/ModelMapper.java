package com.cct.util;

import com.cct.model.*;
import com.cct.model.dto.*;
import org.springframework.stereotype.Component;

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

        return userDTO;
    }

    public Car convertToEntity(CarDTO carDTO) {
        Car car = new Car();

        car.setId(carDTO.getId());

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

        return carDTO;
    }

    public Make convertToEntity(MakeDTO makeDTO) {
        Make make = new Make();

        make.setId(makeDTO.getId());
        make.setName(makeDTO.getName());
        make.setLogoUrl(makeDTO.getLogoUrl());
        make.setModels(makeDTO
                .getModels()
                .stream()
                .map(id -> {
                    Model m = new Model();
                    m.setId(id);
                    return m;
                })
                .collect(Collectors.toSet())
        );

        return make;
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

    public Model convertToEntity(ModelDTO modelDTO) {
        Model model = new Model();

        model.setId(modelDTO.getId());
        model.setName(modelDTO.getName());
        model.setBody(modelDTO.getBody());

        Make make = new Make();
        make.setId(modelDTO.getMakeId());
        model.setMake(make);

        model.setVersions(modelDTO
                .getVersions()
                .stream()
                .map(id -> {
                    Version v = new Version();
                    v.setId(id);
                    return v;
                })
                .collect(Collectors.toSet())
        );

        return model;
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

    public Version convertToEntity(VersionDTO versionDTO) {
        Version version = new Version();

        version.setId(versionDTO.getId());
        version.setName(versionDTO.getName());

        Model model = new Model();
        model.setId(versionDTO.getModelId());
        version.setModel(model);

        version.setFuel(versionDTO.getFuel());
        version.setYears(versionDTO.getYears());
        version.setCityFuelConsumption(versionDTO.getCityFuelConsumption());
        version.setHighwayFuelConsumption(versionDTO.getHighwayFuelConsumption());
        version.setMixedFuelConsumption(versionDTO.getMixedFuelConsumption());
        version.setCars(versionDTO
                .getCars()
                .stream()
                .map(id -> {
                    Car c = new Car();
                    c.setId(id);
                    return c;
                })
                .collect(Collectors.toSet())
        );

        return version;
    }

    public VersionDTO convertToDTO(Version version) {
        VersionDTO versionDTO = new VersionDTO();

        versionDTO.setId(version.getId());
        versionDTO.setName(version.getName());
        versionDTO.setModelId(version.getModel().getId());
        versionDTO.setFuel(version.getFuel());
        versionDTO.setYears(version.getYears());
        versionDTO.setCityFuelConsumption(version.getCityFuelConsumption());
        versionDTO.setHighwayFuelConsumption(version.getHighwayFuelConsumption());
        versionDTO.setMixedFuelConsumption(version.getMixedFuelConsumption());
        versionDTO.setCars(version
                .getCars()
                .stream()
                .map(Car::getId)
                .collect(Collectors.toSet())
        );

        return versionDTO;
    }

    public FuelRefill convertToEntity(FuelRefillDTO fuelRefillDTO) {
        FuelRefill fuelRefill = new FuelRefill();

        fuelRefill.setId(fuelRefillDTO.getId());
        fuelRefill.setDistance(fuelRefillDTO.getDistance());
        fuelRefill.setLiters(fuelRefillDTO.getLiters());
        fuelRefill.setDate(fuelRefillDTO.getDate());

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
        fuelRefillDTO.setDate(fuelRefill.getDate());
        fuelRefillDTO.setCarId(fuelRefill.getCar().getId());

        return fuelRefillDTO;
    }
}
