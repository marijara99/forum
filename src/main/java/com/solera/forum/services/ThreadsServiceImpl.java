package com.solera.forum.services;

import com.solera.forum.daos.SubThreadsDAO;
import com.solera.forum.daos.ThreadsDAO;
import com.solera.forum.models.SubThreads;
import com.solera.forum.models.Threads;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ThreadsServiceImpl implements ThreadsService {

    @Autowired
    private SubThreadsDAO subThreadsDAO;

    @Autowired
    private ThreadsDAO threadsDAO;

    @Override
    public ResponseEntity<Boolean> deleteSubThread(Long mainId, Long id, @NotNull SubThreads subThread ) {
        if (subThread.getId().equals(id) && subThread.getMainThreadId().equals(mainId)) {
            subThreadsDAO.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<Boolean> newSubThread(Long mainId, SubThreads subThread) {
        if (threadsDAO.findById(mainId).isPresent() && !subThreadsDAO.existsSubThreadsByTitle(subThread.getTitle())) {

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

    @Override
    public ResponseEntity<List<SubThreads>> getSubThreads(Long mainId) {
        //It asks for the id of the main thread
        List<SubThreads> subThreads = subThreadsDAO.findSubThreadsByMainThreadId(mainId);

        return new ResponseEntity<>(subThreads, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> addThread(Threads thread) {
        String title = thread.getTitle();

        if (threadsDAO.existsThreadsByTitle(title)) { //Already exists
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        } else {
            threadsDAO.save(thread); //Correctly added to the database
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
    }

    @Override
    public ResponseEntity<List<Threads>> showAllThreads() {
        return new ResponseEntity<>(threadsDAO.findAll(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Boolean> deleteThread(Long mainId) {
        Optional<Threads> threadToDelete = threadsDAO.findById(mainId);

        if (threadToDelete.isPresent()) {
            threadsDAO.deleteById(mainId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
    }
}
