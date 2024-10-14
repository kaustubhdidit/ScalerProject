package com.scaler.taskmanager.entities;

import com.scaler.taskmanager.service.NoteService;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data           //Data class mapping Generates getters and setters automatically
public class TaskEntity {
    private int id;
    private String title;
    private String description;
    private Date deadline;
    private boolean completed;
//    private List<NoteEntity> notes;

}
