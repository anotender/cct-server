package com.cct.configuration.security.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
public class SecurityController {

    @PostMapping(value = "/api/login")
    public ResponseEntity<?> login() {
        return new ResponseEntity<>(OK);
    }

}