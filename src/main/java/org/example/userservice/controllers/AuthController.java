package org.example.userservice.controllers;

import org.example.userservice.dtos.SignUpRequestDto;
import org.example.userservice.dtos.SignUpResponseDto;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
@PostMapping("/sign_up")
public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
return null;
}
@PostMapping("/login")
    public String login( ) {

}
}
