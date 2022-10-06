package com.solera.forum.daos;

import com.solera.forum.models.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadsDAO extends JpaRepository<Threads, Long> {
    boolean existsThreadsByTitle (String title);


}
