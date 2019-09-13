package com.trevorjdobson.taskmaster.controllers;



import com.amazonaws.services.dynamodbv2.datamodeling.ItemConverter;
import com.trevorjdobson.taskmaster.models.HistoryItem;
import com.trevorjdobson.taskmaster.models.Task;
import com.trevorjdobson.taskmaster.repository.S3Client;
import com.trevorjdobson.taskmaster.repository.TheTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1")
public class TaskController {

    @Autowired
    TheTaskRepository theTaskRepository;

    private S3Client s3Client;

    @Autowired
    TaskController(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @GetMapping("/tasks")
    public List<Task> getTasks() {
        return (List) theTaskRepository.findAll();
    }

    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksForUser(@PathVariable String name) {
        return (List) theTaskRepository.findAllByAssignee(name);
    }
    @GetMapping("/tasks/{id}")
    public Task getOneTask(@PathVariable String id){
        Task task = theTaskRepository.findById(id).get();
        return task;
    }

    @PostMapping("tasks/{id}/images")
    public Task addTaskImage(@PathVariable String id,@RequestPart(value = "file") MultipartFile file){

        String pic = this.s3Client.uploadFile(file);
        Task task = theTaskRepository.findById(id).get();
        task.setImgUrl(pic);
        theTaskRepository.save(task);
        return task;
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task) {
        Task t = new Task(task.getTitle(),task.getDescription(),task.getAssignee());
        HistoryItem historyItem = new HistoryItem("Task was created and assigned to " + task.getAssignee());

        t.addToHistory(historyItem);
        theTaskRepository.save(t);
        return t;
    }
    @PutMapping("/tasks/{id}/state")
    public Task updateStatus(@PathVariable String id){
        System.out.println("in put");
        Task task = theTaskRepository.findById(id).get();

        System.out.println(task.getHistory());
        String status = task.getStatus();
        if(status.equals("available")){
            task.setStatus("assigned");
            HistoryItem historyItem = new HistoryItem("Task was was assigned to " + task.getAssignee());

            task.addToHistory(historyItem);
        }else if(status.equals("assigned")){
            task.setStatus("accepted");
            HistoryItem historyItem = new HistoryItem("Task was was accepted by " + task.getAssignee());

            task.addToHistory(historyItem);
        }else if(status.equals("accepted")){
            HistoryItem historyItem = new HistoryItem("Task was was finished by " + task.getAssignee());

            task.addToHistory(historyItem);
            task.setStatus("finished");
        }
        theTaskRepository.save(task);
        return task;
    }
    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task updateAssignee(@PathVariable String id, @PathVariable String assignee){
        Task task = theTaskRepository.findById(id).get();
        task.setAssignee(assignee);
        task.setStatus("assigned");
        HistoryItem historyItem = new HistoryItem("Task was was assigned to " + assignee);

        task.addToHistory(historyItem);
        theTaskRepository.save(task);
        return task;
    }
}