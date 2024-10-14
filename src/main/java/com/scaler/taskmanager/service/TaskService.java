package com.scaler.taskmanager.service;

import com.scaler.taskmanager.entities.TaskEntity;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TaskService {
    private ArrayList<TaskEntity> tasks=new ArrayList<>();
    private int taskId=1;
    private final SimpleDateFormat deadlineFormatter = new SimpleDateFormat("yyyy-MM-dd");
    public TaskEntity addTask(String title, String description, String deadline) throws ParseException {
        TaskEntity task=new TaskEntity();
        task.setId(taskId);
        task.setTitle(title);
        task.setDescription(description);
        task.setDeadline(deadlineFormatter.parse(deadline));
        task.setCompleted(false);
        tasks.add(task);
        taskId++;
        return task;
    }

    public TaskEntity updateTask(int id, String description, String deadline, Boolean completed) throws ParseException {
        TaskEntity task=getTask(id);
        if(task==null) {
        return null;
        }
        if(description!=null)
            task.setDescription(description);
        if(deadline!=null)
            task.setDeadline(deadlineFormatter.parse(deadline));
        if(completed!=null)
            task.setCompleted(completed);

            return task;

    }

    public ArrayList<TaskEntity> getTasks(){
        return tasks;
    }

    public TaskEntity getTask(int id){
        System.out.println(id);
        return tasks.stream()
                .filter(task -> task.getId() == id)
                .findAny()
                .orElse(null);

    }

}
