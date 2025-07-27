package com.sample.trade.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.trade.security.JWTTokenGenService;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class LoginController {

    @Autowired
    private JWTTokenGenService jwtTokenGenService;

    @PostMapping("/login")
    public ResponseEntity<loginDTO> login(@RequestBody User user) throws Exception {
        // This method can be used to handle login requests.
        // You can implement your login logic here.
        System.out.println("Login request received for user: " + user.username());
        String token = jwtTokenGenService.generateToken(user.username());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return ResponseEntity.ok().headers(headers).body(new loginDTO(token));
    }
}
