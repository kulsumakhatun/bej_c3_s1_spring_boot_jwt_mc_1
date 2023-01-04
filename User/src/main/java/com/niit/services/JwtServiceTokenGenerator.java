package com.niit.services;

import com.niit.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtServiceTokenGenerator implements ServiceTokenGenerator{
    @Override
    public Map<String, String> generateToken(User user) {
        String jwToken = null;
      jwToken=  Jwts.builder().setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "securitykey").compact();
                Map <String, String> map = new HashMap<>();
                map.put("token",jwToken);
                map.put("message","User logged in");
                 return map;
    }
}
