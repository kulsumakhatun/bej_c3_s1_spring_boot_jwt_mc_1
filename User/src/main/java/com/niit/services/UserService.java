package com.niit.services;

import com.niit.domain.User;
import com.niit.exception.UserNotFoundException;

import java.util.List;

public interface UserService {
    public User addUser(User user);
    public User findByUsernameAndPassword(String username , String password) throws UserNotFoundException;
    List<User> getAllUsers();
    boolean deleteUser(int userid) throws  UserNotFoundException;
    public User loginCheck(int userId, String password) throws UserNotFoundException;



}
