package com.niit.services;

import com.niit.domain.User;

import java.util.Map;

public interface ServiceTokenGenerator {
    Map <String,String> generateToken(User user);
}
