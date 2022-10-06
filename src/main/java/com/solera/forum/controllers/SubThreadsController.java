package com.solera.forum.controllers;

import com.solera.forum.daos.SubThreadsDAO;
import com.solera.forum.daos.ThreadsDAO;
import com.solera.forum.models.SubThreads;
import com.solera.forum.models.Threads;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/accessThread")
public class SubThreadsController {

    @Autowired
    private SubThreadsDAO subThreadsDAO;
    private ThreadsDAO threadsDAO;

    @GetMapping("/{mainId}")
    public ResponseEntity<List<SubThreads>> getSubThreads(@RequestParam Long mainId){ //id of the main thread
        //It asks for the id of the main thread
        List <SubThreads> subThreads = subThreadsDAO.FindSubThreadsByMainThreadId(mainId);
        return new ResponseEntity<>(subThreads, HttpStatus.OK);
    }

    @PostMapping("/{mainId}/newThread")
    public ResponseEntity<SubThreads> newThread(@PathVariable Long mainId, @RequestBody SubThreads subThread){

        SubThreads newSubThread = new SubThreads();
        newSubThread.setMainThreadId(mainId);
        newSubThread.setContent(subThread.getContent());
        newSubThread.setAuthor(subThread.getAuthor());
        newSubThread.setDate(subThread.getDate());
        newSubThread.setTitle(subThread.getTitle());

        subThreadsDAO.save(newSubThread);
        return new ResponseEntity<>(newSubThread, HttpStatus.OK);

    }

    @DeleteMapping("/{mainId}/delete")
    public ResponseEntity<Boolean> deleteSubThread(@PathVariable Long mainId, @RequestBody Long id){
        subThreadsDAO.deleteById(id);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }


}
