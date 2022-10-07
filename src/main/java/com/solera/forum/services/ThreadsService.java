package com.solera.forum.services;

import com.solera.forum.models.SubThreads;
import com.solera.forum.models.Threads;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface ThreadsService {

    ResponseEntity<Boolean> deleteSubThread(@PathVariable Long mainId, @PathVariable Long id, @RequestBody SubThreads subThread);

    ResponseEntity<Boolean> newSubThread(@PathVariable Long mainId, @RequestBody SubThreads subThread);

    ResponseEntity<List<SubThreads>> getSubThreads(@PathVariable Long mainId); //id of the main thread

    ResponseEntity<Boolean> addThread(@RequestBody Threads thread);

    ResponseEntity<List<Threads>> showAllThreads();

    ResponseEntity<Boolean> deleteThread(@PathVariable Long mainId);


}


