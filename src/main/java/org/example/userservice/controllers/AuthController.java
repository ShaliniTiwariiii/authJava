package org.example.userservice.controllers;

import org.example.userservice.dtos.*;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.exceptions.WrongPasswordException;
import org.example.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import io.jsonwebtoken.Jwts;

import java.util.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/sign_up")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) {
        SignUpResponseDto response = new SignUpResponseDto();
        try {
            if (authService.signUp(requestDto.getEmail(), requestDto.getPassword())) {
                response.setStatus(RequestStatus.SUCCESS);
                return ResponseEntity.ok(response);
            } else {
                response.setStatus(RequestStatus.FAILURE);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }
        } catch (Exception ex) {
            response.setStatus(RequestStatus.FAILURE);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }

@PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto) throws UserAlreadyExistsException, UserNotFoundException, WrongPasswordException {
    try{
        String token=authService.login(requestDto.getEmail(), requestDto.getPassword());
        LoginResponseDto  loginDto=new LoginResponseDto();
        loginDto.setStatus(RequestStatus.SUCCESS);
        MultiValueMap<String, String> headers=new LinkedMultiValueMap<>();
        headers.add("AUTH_TOKEN",token);
        ResponseEntity<LoginResponseDto> response = ResponseEntity
                .status(HttpStatus.OK)
                .body(loginDto);
        return response;
    }catch (Exception ex){
        LoginResponseDto  loginDto=new LoginResponseDto();
        loginDto.setStatus(RequestStatus.FAILURE);
        ResponseEntity<LoginResponseDto> response = ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(loginDto);
        return response;
    }
}
public boolean validate(@RequestParam("token")String token){
        System.out.println("Here i am");
        return authService.validateTokn(token);
}
}
