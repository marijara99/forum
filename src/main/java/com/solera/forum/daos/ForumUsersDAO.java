package com.solera.forum.daos;

import com.solera.forum.models.ForumUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumUsersDAO extends JpaRepository<ForumUsers, Long> {
    boolean existsForumUsersByEmail(String email); //QUERY METHODS
    boolean existsForumUsersByName(String name); //QUERY METHODS


}
