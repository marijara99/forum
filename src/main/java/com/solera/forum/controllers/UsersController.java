package com.solera.forum.controllers;


import com.solera.forum.models.ForumUsers;
import com.solera.forum.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")

public class UsersController {

    @Autowired //It looks for the class that IMPLEMENTS UsersService --> UsersServiceImpl
    private UsersService usersService;

    @GetMapping("/getUsers")
    public ResponseEntity<List<ForumUsers>> getUsers() {
        return usersService.getUsers();
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> addUser(@RequestBody ForumUsers user) {
        return usersService.addUser(user);
    }


    @DeleteMapping("/delete")
    public ResponseEntity<List<ForumUsers>> deleteUser(@RequestBody ForumUsers user) {
        return usersService.deleteUser(user);
    }


    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody ForumUsers user) {
        return usersService.updateUser(user);
    }
}



