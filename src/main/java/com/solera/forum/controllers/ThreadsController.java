package com.solera.forum.controllers;

import com.solera.forum.models.SubThreads;
import com.solera.forum.models.Threads;
import com.solera.forum.services.ThreadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/threads")
@CrossOrigin(origins = {"*"}, maxAge = 4800, allowCredentials = "false")

public class ThreadsController {
    @Autowired
    private ThreadsService threadsService; //Inyección de dependencias

    @PostMapping("/newMainThread")
    public ResponseEntity<Boolean> addThread(@RequestBody Threads thread) {
       return threadsService.addThread(thread);
    }

    @GetMapping
    public ResponseEntity<List<Threads>> showAllThreads(){
        return threadsService.showAllThreads();
    }

    @DeleteMapping("/{mainId}/delete")
    public ResponseEntity<Boolean> deleteThread(@PathVariable Long mainId){
        return threadsService.deleteThread(mainId);
    }
    @GetMapping("/{mainId}")
    public ResponseEntity<List<SubThreads>> getSubThreads(@PathVariable Long mainId){ //id of the main thread
        return threadsService.getSubThreads(mainId);
    }
    @PostMapping("/{mainId}/newSubThread")
    public ResponseEntity<Boolean> newSubThread(@PathVariable Long mainId, @RequestBody SubThreads subThread){
        return threadsService.newSubThread(mainId, subThread);
    }
    @DeleteMapping("/{mainId}/{id}/delete")
    public ResponseEntity<Boolean> deleteSubThread(@PathVariable Long mainId, @PathVariable Long id, @RequestBody SubThreads subThread){
        return threadsService.deleteSubThread(mainId, id, subThread);
    }

}
