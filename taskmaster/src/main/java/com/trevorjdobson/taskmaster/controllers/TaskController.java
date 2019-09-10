package com.trevorjdobson.taskmaster.controllers;



import com.trevorjdobson.taskmaster.models.Task;
import com.trevorjdobson.taskmaster.repository.TheTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    TheTaskRepository theTaskRepository;

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) theTaskRepository.findAll();
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task) {
        Task t = new Task(task.getTitle(),task.getDescription());

        theTaskRepository.save(t);
        return t;
    }
    @PutMapping("/tasks/{id}/state")
    public Task updateStatus(@PathVariable String id){
        Task task = theTaskRepository.findById(id).get();
        String status = task.getStatus();
        if(status.equals("available")){
            task.setStatus("assigned");
        }else if(status.equals("assigned")){
            task.setStatus("accepted");
        }else if(status.equals("accepted")){
            task.setStatus("finished");
        }
        theTaskRepository.save(task);
        return task;
    }
}