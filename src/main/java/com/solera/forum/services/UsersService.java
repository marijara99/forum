package com.solera.forum.services;

import com.solera.forum.models.ForumUsers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

public interface UsersService {

    ResponseEntity<List<ForumUsers>> getUsers();

    ResponseEntity<Integer> addUser(@RequestBody ForumUsers user);

    ResponseEntity<List<ForumUsers>> deleteUser(@RequestBody ForumUsers user);

    ResponseEntity<Boolean> updateUser(@RequestBody ForumUsers user);


}
