package org.example.userservice.controllers;

import org.example.userservice.dtos.*;
import org.example.userservice.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
@PostMapping("/sign_up")
public ResponseEntity< SignUpResponseDto> signUp(SignUpRequestDto requestDto) {
        SignUpResponseDto response = new SignUpResponseDto();
        try{
            if(authService.SignUp(requestDto.getEmail(), requestDto.getPassword())) {
                response.setStatus(RequestStatus.SUCCESS);
            }else{
                response.setStatus(RequestStatus.FAILURE);
            }
            return new  ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch(Exception ex){
            response.setStatus(RequestStatus.FAILURE);
            return new  ResponseEntity<>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }

}
@PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto requestDto) {
    String token=authService.Login(requestDto.getEmail(), requestDto.getPassword());
    LoginResponseDto  loginDto=new LoginResponseDto();
    loginDto.setStatus(RequestStatus.SUCCESS);
    MultiValueMap<String, String> headers=new LinkedMultiValueMap<>();
    headers.add("AUTH_TOKEN",token);
    ResponseEntity<LoginResponseDto> response = ResponseEntity
            .status(HttpStatus.OK)
            .body(loginDto);
 return response;
}
}
