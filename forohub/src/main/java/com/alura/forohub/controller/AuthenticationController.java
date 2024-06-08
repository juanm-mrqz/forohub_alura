package com.alura.forohub.controller;

import com.alura.forohub.infra.security.TokenService;
import com.alura.forohub.infra.security.JWTTokenData;
import com.alura.forohub.users.User;
import com.alura.forohub.users.UserAuthenticationData;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity authenticateUser(@RequestBody @Valid UserAuthenticationData data){
        Authentication authToken =
                new UsernamePasswordAuthenticationToken(
                        data.login(),
                        data.password());
        var authUser= authManager.authenticate(authToken);
        var jwtToken = tokenService.generateToken((User) authUser.getPrincipal());
        return ResponseEntity.ok(new JWTTokenData(jwtToken));
    }
}
