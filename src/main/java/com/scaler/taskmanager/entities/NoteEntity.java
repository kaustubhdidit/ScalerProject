package com.scaler.taskmanager.entities;

import lombok.Data;

import java.util.Date;

@Data
public class NoteEntity {
    private int id;
    private String title;
    private String body;
}
