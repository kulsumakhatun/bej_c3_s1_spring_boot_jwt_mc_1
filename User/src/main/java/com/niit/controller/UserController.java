package com.niit.controller;

import com.niit.domain.User;
import com.niit.exception.UserNotFoundException;
import com.niit.services.ServiceTokenGenerator;
import com.niit.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user/api")
public class UserController {
    private UserService userServices;
    private ServiceTokenGenerator securityTokenGenerator;

    @Autowired
    public UserController(UserService userServices,ServiceTokenGenerator securityTokenGenerator ) {
        this.userServices = userServices;
        this.securityTokenGenerator=securityTokenGenerator;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) throws UserNotFoundException {
        Map<String,String> map =null;
        User user1 = userServices.findByUsernameAndPassword(user.getUsername(),user.getPassword());
        if(user1.getUsername().equals(user.getUsername())){
            map =securityTokenGenerator.generateToken(user);
            return  new ResponseEntity<>(map,HttpStatus.OK);
        }
        return new ResponseEntity<>("Please try later",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody User user){
        User createdUser = userServices.addUser(user);
        return new ResponseEntity(createdUser, HttpStatus.CREATED);
    }





    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(){
        List<User> userList=userServices.getAllUsers();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @DeleteMapping("/user/{userid}")
    public ResponseEntity<?> deleteUser(@PathVariable int userid) throws UserNotFoundException {
        ResponseEntity responseEntity = null;
        try{
            userServices.deleteUser(userid);
            responseEntity = new ResponseEntity<>("successfully deleted one record",HttpStatus.OK);
        }catch (UserNotFoundException e){
            throw new UserNotFoundException();
        }catch (Exception e){
            responseEntity=new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }

}
