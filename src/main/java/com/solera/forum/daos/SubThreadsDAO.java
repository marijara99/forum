package com.solera.forum.daos;

import com.solera.forum.models.SubThreads;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubThreadsDAO extends JpaRepository<SubThreads, Long> {

    List<SubThreads> findSubThreadsByMainThreadId(Long mainThreadId);
    boolean existsSubThreadsByTitle (String title);
}
