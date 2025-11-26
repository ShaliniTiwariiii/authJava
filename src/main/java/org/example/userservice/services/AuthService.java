package org.example.userservice.services;

import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.models.User;
import org.example.userservice.reposetories.UserReposetories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private UserReposetories userReposetories;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public AuthService(UserReposetories userReposetories, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userReposetories = userReposetories;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public  Boolean SignUp(String email, String password) {

        if(userReposetories.findByEmail(email).isPresent()){
//            throw new UserAlreadyExistsException("Email already exist");

        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userReposetories.save(user);
        return true;
    }
    public  String Login(String email, String password) {
        return null;
    }
}
