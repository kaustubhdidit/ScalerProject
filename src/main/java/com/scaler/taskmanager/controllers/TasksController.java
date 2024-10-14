package com.scaler.taskmanager.controllers;

import com.scaler.taskmanager.dto.CreateTaskDto;
import com.scaler.taskmanager.dto.ErrorResponseDto;
import com.scaler.taskmanager.dto.TaskResponseDto;
import com.scaler.taskmanager.dto.UpdateTaskDto;
import com.scaler.taskmanager.entities.TaskEntity;
import com.scaler.taskmanager.service.NoteService;
import com.scaler.taskmanager.service.TaskService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final TaskService taskService;      //TaskService variable will be available to us via dependency injection
    private final NoteService notesService;
    private ModelMapper modelMapper=new ModelMapper();
    public TasksController(TaskService taskService,NoteService notesService) {
        this.taskService = taskService;
        this.notesService = notesService;
    }




    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks(){
var tasks=taskService.getTasks();
return ResponseEntity.ok(tasks);
}

@GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> getTaskById(@PathVariable("id") Integer id){
        var task=taskService.getTask(id);
        var notes=notesService.getNotesForTask(id);
        if(task==null){
            return ResponseEntity.notFound().build();
        }
        var taskResponse=modelMapper.map(task,TaskResponseDto.class);
        taskResponse.setNotes(notes);
//        task.setNotes(notes);
    return ResponseEntity.ok(taskResponse);
}

@PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDto body) throws ParseException {
        var task=taskService.addTask(body.getTitle(),body.getDescription(),body.getDeadline());
        return ResponseEntity.ok(task);
}

@PatchMapping("/{id}")
public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") Integer id, @RequestBody UpdateTaskDto body) throws ParseException {
    var task=taskService.updateTask(id,body.getDescription(),body.getDeadline(),body.getCompleted());
    if(task==null)
        return null;
    return ResponseEntity.ok(task);
}

@ExceptionHandler(ParseException.class)
public ResponseEntity<ErrorResponseDto> handleErrors(Exception e) {
    if (e instanceof ParseException) {
        return ResponseEntity.badRequest().body(new ErrorResponseDto("Invalid Date Format"));
    }
    e.printStackTrace();
    return ResponseEntity.internalServerError().body(new ErrorResponseDto("Internal Server Error"));
}
}
