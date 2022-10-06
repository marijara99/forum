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
@RequestMapping("/threads")
public class ThreadsController {

    @Autowired
    private ThreadsDAO threadsDAO; //Inyección de dependencias
    @Autowired
    private SubThreadsDAO subThreadsDAO;

    @PostMapping("/newMainThread")
    public ResponseEntity<Boolean> addThread(@RequestBody Threads thread){

        String title = thread.getTitle();

        if (threadsDAO.existsThreadsByTitle(title)) { //Already exists
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }else{
            threadsDAO.save(thread); //Correctly added to the database
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }
    @GetMapping
    public ResponseEntity<List<Threads>> showAllThreads(){
        return new ResponseEntity<>(threadsDAO.findAll(), HttpStatus.OK);

    }
    @DeleteMapping("/{mainId}/delete")
    public ResponseEntity<Boolean> deleteThread(@PathVariable Long mainId){
        Optional<Threads> threadToDelete = threadsDAO.findById(mainId);

        if (threadToDelete.isPresent()){
            threadsDAO.deleteById(mainId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/{mainId}")
    public ResponseEntity<List<SubThreads>> getSubThreads(@PathVariable Long mainId){ //id of the main thread
        //It asks for the id of the main thread
        List <SubThreads> subThreads = subThreadsDAO.findSubThreadsByMainThreadId(mainId);
        return new ResponseEntity<>(subThreads, HttpStatus.OK);
    }
    @PostMapping("/{mainId}/newSubThread")
    public ResponseEntity<Boolean> newSubThread(@PathVariable Long mainId, @RequestBody SubThreads subThread){

        //The title mustn't exist and the main thread must be available
        if (threadsDAO.findById(mainId).isPresent() && !subThreadsDAO.existsSubThreadsByTitle(subThread.getTitle())){

            SubThreads newSubThread = new SubThreads();
            newSubThread.setMainThreadId(mainId);
            newSubThread.setContent(subThread.getContent());
            newSubThread.setAuthor(subThread.getAuthor());
            newSubThread.setDate(subThread.getDate());
            newSubThread.setTitle(subThread.getTitle());

            subThreadsDAO.save(newSubThread);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/{mainId}/{id}/delete")
    public ResponseEntity<Boolean> deleteSubThread(@PathVariable Long mainId, @PathVariable Long id, @RequestBody SubThreads subThread){

        //If the sub thread is going to be deleted coincides with the URL specs
        if (subThread.getId().equals(id) && subThread.getMainThreadId().equals(mainId)){
            subThreadsDAO.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }



    }

}
