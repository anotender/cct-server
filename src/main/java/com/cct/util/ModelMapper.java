package com.cct.util;

import com.cct.model.Make;
import com.cct.model.Model;
import com.cct.model.User;
import com.cct.model.dto.MakeDTO;
import com.cct.model.dto.ModelDTO;
import com.cct.model.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class ModelMapper {

    public User convertToEntity(UserDTO userDTO) {
        User user = new User();

        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        return user;
    }

    public UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());

        return userDTO;
    }

    public Make convertToEntity(MakeDTO makeDTO) {
        Make make = new Make();

        make.setId(makeDTO.getId());
        make.setName(makeDTO.getName());
        make.setCountry(makeDTO.getCountry());

        return make;
    }

    public MakeDTO convertToDTO(Make make) {
        MakeDTO makeDTO = new MakeDTO();

        makeDTO.setId(make.getId());
        makeDTO.setName(make.getName());
        makeDTO.setCountry(make.getCountry());

        return makeDTO;
    }

    public ModelDTO convertToDTO(Model model) {
        ModelDTO modelDTO = new ModelDTO();

        modelDTO.setId(model.getId());
        modelDTO.setMakeId(model.getMakeId());
        modelDTO.setName(model.getName());
        modelDTO.setVersion(model.getVersion());
        modelDTO.setYear(model.getYear());
        modelDTO.setHighwayFuelConsumption(model.getHighwayFuelConsumption());
        modelDTO.setCityFuelConsumption(model.getCityFuelConsumption());
        modelDTO.setMixedFuelConsumption(model.getMixedFuelConsumption());

        return modelDTO;
    }

    public Model converToEntity(ModelDTO modelDTO) {
        Model model = new Model();

        model.setId(modelDTO.getId());
        model.setMakeId(modelDTO.getMakeId());
        model.setName(modelDTO.getName());
        model.setVersion(modelDTO.getVersion());
        model.setYear(modelDTO.getYear());
        model.setHighwayFuelConsumption(modelDTO.getHighwayFuelConsumption());
        model.setCityFuelConsumption(modelDTO.getCityFuelConsumption());
        model.setMixedFuelConsumption(modelDTO.getMixedFuelConsumption());

        return model;
    }
}
