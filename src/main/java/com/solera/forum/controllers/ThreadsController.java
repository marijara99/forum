package com.solera.forum.controllers;

import com.solera.forum.daos.ThreadsDAO;
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
    private ThreadsDAO threadsDAO; //Inyeccion de dependencias

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

    @GetMapping("/showThreads")
    public ResponseEntity<List<Threads>> showAllThreads(){
        if (!threadsDAO.findAll().isEmpty()) {
            return new ResponseEntity<>(threadsDAO.findAll(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(threadsDAO.findAll(), HttpStatus.NOT_FOUND);

        }
    }

    @DeleteMapping("/deleteThread")
    public ResponseEntity<Boolean> deleteThread(@RequestBody Threads thread){
        Optional<Threads> threadToDelete = threadsDAO.findById(thread.getId());

        if (threadToDelete.isPresent()){
            threadsDAO.deleteById(thread.getId());
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

}
