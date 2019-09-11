package com.trevorjdobson.taskmaster.controllers;



import com.amazonaws.services.dynamodbv2.datamodeling.ItemConverter;
import com.trevorjdobson.taskmaster.models.HistoryItem;
import com.trevorjdobson.taskmaster.models.Task;
import com.trevorjdobson.taskmaster.repository.TheTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @GetMapping("/users/{name}/tasks")
    public List<Task> getTasksForUser(@PathVariable String name) {
        return (List) theTaskRepository.findAllByAssignee(name);
    }

    @PostMapping("/tasks")
    public Task addNewTask (@RequestBody Task task) {
        System.out.println(task.getAssignee());
        System.out.println(task.getTitle());
        Task t = new Task(task.getTitle(),task.getDescription(),task.getAssignee());
        HistoryItem historyItem = new HistoryItem("Task was created and assigned to " + task.getAssignee());
//        List<HistoryItem> history = t.getHistory();
//        history.add(historyItem);
//        t.setHistory(history);
//        String historyItem = "Task was created and assigned to " + task.getAssignee() + " on " + new Date().toString();
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

//            List<HistoryItem> history = task.getHistory();
//            history.add(historyItem);
//            task.setHistory(history);
//            String historyItem = "Task was assigned to " + task.getAssignee() + " on " + new Date().toString();

            task.addToHistory(historyItem);
        }else if(status.equals("assigned")){
            task.setStatus("accepted");
            HistoryItem historyItem = new HistoryItem("Task was was accepted by " + task.getAssignee());
//            List<HistoryItem> history = task.getHistory();
//            history.add(historyItem);
//            task.setHistory(history);
//            String historyItem = "Task was accepted " + task.getAssignee() + " on " + new Date().toString();

            task.addToHistory(historyItem);
        }else if(status.equals("accepted")){
            HistoryItem historyItem = new HistoryItem("Task was was finished by " + task.getAssignee());
//            List<HistoryItem> history = task.getHistory();
//            history.add(historyItem);
//            task.setHistory(history);
//            String historyItem = "Task was finished by " + task.getAssignee() + " on " + new Date().toString();

            task.addToHistory(historyItem);
            task.setStatus("finished");
        }
        theTaskRepository.save(task);
        return task;
    }
    @PutMapping("/tasks/{id}/assign/{assignee}")
    public Task updateAssignee(@PathVariable String id, String assignee){
        Task task = theTaskRepository.findById(id).get();
        task.setAssignee(assignee);
        task.setStatus("assigned");
        HistoryItem historyItem = new HistoryItem("Task was was assigned to " + assignee);
//        List<HistoryItem> history = task.getHistory();
//        history.add(historyItem);
//        task.setHistory(history);
//        String historyItem = "Task was assigned to " + task.getAssignee() + " on " + new Date().toString();

        task.addToHistory(historyItem);
        theTaskRepository.save(task);
        return task;
    }
}