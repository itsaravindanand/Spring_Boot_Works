package com.in28minutes.rest.webservices.restful_web_services.user;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
class UserResource {
    private UserDAOService userDAOService;

    public UserResource(UserDAOService userDAOService){
        this.userDAOService = userDAOService;
    }

    //Get All Users
    //GET /users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUser(){
        return userDAOService.findAll();
    }

    //Get one specific user
    //GET /users/{id}
    @GetMapping(path = "/users/{userId}")
    public User retrieveUser(@PathVariable Integer userId){
        User user = userDAOService.findOne(userId);
        if(user==null){
            throw new UserNotFoundException("id: "+ userId);
        }
        return user;
    }

    //Create new User
    //POST /users
    @PostMapping(path = "/users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        User savedUser = userDAOService.save(user);
        //Returning the location of created user
        // /users/4 -> /users/{id}, user.getID()
        //Take the current URL, add a path variable, get the value of the path variable and build the URI
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
