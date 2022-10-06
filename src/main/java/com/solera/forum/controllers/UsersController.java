package com.solera.forum.controllers;


import com.solera.forum.daos.ForumUsersDAO;
import com.solera.forum.models.ForumUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UsersController {
    @Autowired
    private ForumUsersDAO forumUsersDAO;

    @GetMapping("/getUsers")
    public ResponseEntity<List<ForumUsers>> getUsers() {

        return new ResponseEntity<>(forumUsersDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Integer> addUser(@RequestBody ForumUsers user) {

        String name = user.getName();
        String email = user.getEmail();

        //0 if email is bad, 1 if name is bad, 2 if everything is ok

        if (forumUsersDAO.existsForumUsersByEmail(email)) {
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);

        } else if (forumUsersDAO.existsForumUsersByName(name)) {
            return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
        } else {
            ForumUsers newUser = forumUsersDAO.save(user);
            return new ResponseEntity<>(2, HttpStatus.OK);

        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<ForumUsers>> deleteUser(@RequestBody ForumUsers user) {
        forumUsersDAO.deleteById(user.getId());
        return new ResponseEntity<>(forumUsersDAO.findAll(), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody ForumUsers user) {

        String email = user.getEmail();
        String name = user.getName();

        Optional<ForumUsers> updateUser = forumUsersDAO.findById(user.getId());

        if (updateUser.isPresent()){
            ForumUsers updatedUser = updateUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());

            forumUsersDAO.save(updatedUser);
            return new ResponseEntity<>(true, HttpStatus.OK); //FOUND AND UPDATED

        } else{
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);//NOT FOUND
        }

    }

}
