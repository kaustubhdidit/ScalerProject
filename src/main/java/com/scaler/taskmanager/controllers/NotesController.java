package com.scaler.taskmanager.controllers;

import com.scaler.taskmanager.dto.CreateNoteDto;
import com.scaler.taskmanager.dto.CreateNoteResponseDto;
import com.scaler.taskmanager.entities.NoteEntity;
import com.scaler.taskmanager.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NotesController {

    private final NoteService notesService;

    public NotesController(NoteService notesService) {
        this.notesService = notesService;
    }

    @GetMapping("")
    public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId") Integer taskId) {
        var notes = notesService.getNotesForTask(taskId);
        return ResponseEntity.ok(notes);
    }

    @PostMapping("")
    public ResponseEntity<CreateNoteResponseDto> addNote(
            @PathVariable("taskId") Integer taskId,
            @RequestBody CreateNoteDto body) {
        var note = notesService.addNoteForTask(taskId, body.getTitle(),body.getBody());
        return ResponseEntity.ok(new CreateNoteResponseDto(taskId,note));
    }
}
