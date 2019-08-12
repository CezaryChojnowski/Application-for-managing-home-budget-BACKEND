package com.rest;

import com.dao.UserDAO;
import com.entity.User;
import com.error.ErrorResponse;
import com.error.Exception;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private UserDAO userDAO;

    @Autowired
    public UserRestController(UserDAO theUserDAO){
        userDAO = theUserDAO;
    }

    @GetMapping("/getAllUsers")
    public List<User> getAllUsers(){
        return userDAO.findAllUsers();
    }

    @PostMapping(value="/register")
    public ResponseEntity registerUserAccount(@Valid @RequestBody User user){
        if(!userDAO.checkTheUniqueEmail(user.getEmail())){
            throw new Exception("User with the given email address exists " + user.getEmail());
        }
        else{
            return ResponseEntity.ok(userDAO.createUser(user));
        }
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(Exception existEx){
        ErrorResponse error = new ErrorResponse();
        error.setStatus(HttpStatus.INSUFFICIENT_STORAGE.value());
        error.setMessage(existEx.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.INSUFFICIENT_STORAGE);
    }
}
