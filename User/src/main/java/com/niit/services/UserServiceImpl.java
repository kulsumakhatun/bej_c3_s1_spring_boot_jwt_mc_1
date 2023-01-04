package com.niit.services;

import com.niit.domain.User;
import com.niit.exception.UserNotFoundException;
import com.niit.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) throws UserNotFoundException {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteUser(int userid) throws UserNotFoundException {
        boolean flag = false;
        if (userRepository.findById(userid).isEmpty()) {
            throw new UserNotFoundException();
        } else {
            userRepository.deleteById(userid);
            flag = true;
        }
        return flag;
    }

    @Override
    public User loginCheck(int userId, String password) throws UserNotFoundException {
        if (userRepository.findById(userId).isEmpty()) {
            User user = userRepository.findById(userId).get();
            if (user.getPassword().equals(password)) {
                return user;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }
}
