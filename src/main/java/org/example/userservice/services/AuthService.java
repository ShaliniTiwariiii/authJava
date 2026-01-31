package org.example.userservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.example.userservice.exceptions.UserAlreadyExistsException;
import org.example.userservice.exceptions.UserNotFoundException;
import org.example.userservice.exceptions.WrongPasswordException;
import org.example.userservice.models.User;
import org.example.userservice.reposetories.UserReposetories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.*;

@Service
public class AuthService {
    private UserReposetories userReposetories;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private SecretKey key=Jwts.SIG.HS256.key().build();
    public AuthService(UserReposetories userReposetories, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userReposetories = userReposetories;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public  Boolean signUp(String email, String password) {

        if(userReposetories.findByEmail(email).isPresent()){
//            throw new UserAlreadyExistsException("Email already exist");

        }
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        userReposetories.save(user);
        return true;
    }
    public  String login(String email, String password) throws UserNotFoundException, WrongPasswordException {
        Optional<User>userOptional=userReposetories.findByEmail(email);
        if(userOptional.isPresent()){
            throw new UserNotFoundException("User with email"+email+"not found");
        }
        boolean matches=   bCryptPasswordEncoder.matches(
                password,
                userOptional.get().getPassword()
        );
        if(matches){
            return "token";
        }else{
           throw new WrongPasswordException("Wrong password");
        }

    }
public boolean validateTokn(String token){
        try{
            Jws< Claims> claims=Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            Date expiration=claims.getPayload().getExpiration();
            Long userId=claims.getPayload().get("user_id",Long.class);
        }catch (Exception e){
            return false;
        }
        return true;
}
    private String createJwtToken(Long userId, List<String> roles, String email){
        Map<String,Object> dataInJwt=new HashMap<>();
        dataInJwt.put("userId",userId);
        dataInJwt.put("roles",roles);
        dataInJwt.put("email",email);
        Calendar calendar= Calendar.getInstance();
        Date currentDate=calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH ,30);
        Date datePlus30Days=calendar.getTime();
        String token = Jwts.builder()
                .claims(dataInJwt)
                .expiration(datePlus30Days)
                .issuedAt(new Date())
                .signWith(key)
                .compact();
        return token;
    }
}
