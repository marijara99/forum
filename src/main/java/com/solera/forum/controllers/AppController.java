package com.solera.forum.controllers;


import com.solera.forum.daos.ForumUsersDAO;
import com.solera.forum.models.ForumUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class AppController {


    @Autowired
    private ForumUsersDAO forumUsersDAO;

    @GetMapping("/getUsers")
    public ResponseEntity<List<ForumUsers>> getUsers(){

        return new ResponseEntity<>(forumUsersDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping("/addUser")
    public ResponseEntity<ForumUsers> addUser(@RequestBody ForumUsers user){
        ForumUsers newUser = forumUsersDAO.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

}
