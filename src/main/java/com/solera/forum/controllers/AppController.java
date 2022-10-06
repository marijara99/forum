package com.solera.forum.controllers;


import com.solera.forum.daos.ForumUsersDAO;
import com.solera.forum.models.ForumUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
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
    public ResponseEntity<Integer> addUser(@RequestBody ForumUsers user) throws SQLException {

        String name = user.getName();
        String email = user.getEmail();

        //0 if email is bad, 1 if name is bad, 2 if everything is ok

        if (forumUsersDAO.existsForumUsersByEmail(email)){

            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);

        }else if(forumUsersDAO.existsForumUsersByName(name)){
            return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
        }else {
            ForumUsers newUser = forumUsersDAO.save(user);
            return new ResponseEntity<>(2, HttpStatus.OK);

        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<ForumUsers>> deleteUser(@RequestBody ForumUsers user){
        forumUsersDAO.deleteById(user.getId());
        return new ResponseEntity<>(forumUsersDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping("/updateUser")
    public ResponseEntity<Integer> updateUser(@RequestBody ForumUsers user){

        String email = user.getEmail();
        String name = user.getName();

        if (forumUsersDAO.existsForumUsersByEmail(email)){
            //MIRAR VIDEO PARA UPDATE EL USER
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);

        }else if(forumUsersDAO.existsForumUsersByName(name)){
            return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
        }else {
            ForumUsers newUser = forumUsersDAO.save(user);
            return new ResponseEntity<>(2, HttpStatus.OK);

        }

    }



}
