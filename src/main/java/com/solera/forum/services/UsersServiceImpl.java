package com.solera.forum.services;

import com.solera.forum.daos.ForumUsersDAO;
import com.solera.forum.models.ForumUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Component
public class UsersServiceImpl implements UsersService {

    @Autowired
    private ForumUsersDAO forumUsersDAO;


    @Override
    public ResponseEntity<List<ForumUsers>> getUsers() {
        return new ResponseEntity<>(forumUsersDAO.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Integer> addUser(@RequestBody ForumUsers user) {

        String name = user.getName();
        String email = user.getEmail();

        //0 if email is bad, 1 if name is bad, 2 if everything is ok

        if (forumUsersDAO.existsForumUsersByEmail(email)) {
            return new ResponseEntity<>(0, HttpStatus.BAD_REQUEST);

        } else if (forumUsersDAO.existsForumUsersByName(name)) {
            return new ResponseEntity<>(1, HttpStatus.BAD_REQUEST);
        } else {
            forumUsersDAO.save(user);
            return new ResponseEntity<>(2, HttpStatus.OK);

        }
    }

    @Override
    public ResponseEntity<List<ForumUsers>> deleteUser(@RequestBody ForumUsers user) {
        forumUsersDAO.deleteById(user.getId());
        return new ResponseEntity<>(forumUsersDAO.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> updateUser(@RequestBody ForumUsers user) {

        String email = user.getEmail();
        String name = user.getName();

        Optional<ForumUsers> updateUser = forumUsersDAO.findById(user.getId());

        if (updateUser.isPresent()) {
            ForumUsers updatedUser = updateUser.get();
            updatedUser.setName(user.getName());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());

            forumUsersDAO.save(updatedUser);
            return new ResponseEntity<>(true, HttpStatus.OK); //FOUND AND UPDATED

        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);//NOT FOUND
        }
    }
}
