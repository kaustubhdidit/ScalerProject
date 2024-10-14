package com.scaler.taskmanager.service;

import com.scaler.taskmanager.entities.NoteEntity;
import com.scaler.taskmanager.entities.TaskEntity;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class NoteService {
    private TaskService taskService;      //TaskService variable will be available to us via dependency injection
    private HashMap<Integer, TaskNotesHolder> taskNoteHolders= new HashMap<>();
    public NoteService(TaskService taskService) {
        this.taskService = taskService;
    }
    class TaskNotesHolder{
        protected int noteId=1;
        protected ArrayList<NoteEntity> notes=new ArrayList<>();

    }

    public List<NoteEntity> getNotesForTask(int taskId){
        TaskEntity task = taskService.getTask(taskId) ;
        if (task == null) {
            return null;
        }
        if (taskNoteHolders.get(taskId) == null) {
            taskNoteHolders.put(taskId,new TaskNotesHolder());
        }
        return taskNoteHolders.get(taskId).notes;
    }

    public NoteEntity addNoteForTask(int taskId, String title, String body) {
        // Retrieve the task entity by its ID
        TaskEntity task = taskService.getTask(taskId);

        // Correct conditional check, should use '==' for comparison
        if (task == null) {
            return null; // Return null if task doesn't exist
        }

        // Ensure the taskNoteHolders map contains the taskId
        if (taskNoteHolders.get(taskId) == null) {
            // Correct object instantiation syntax
            taskNoteHolders.put(taskId, new TaskNotesHolder());
        }

        // Retrieve the TaskNotesHolder associated with this task
        TaskNotesHolder taskNotesHolder = taskNoteHolders.get(taskId);

        // Create a new NoteEntity instance
        NoteEntity note = new NoteEntity();
        note.setId(taskNotesHolder.noteId); // Set the note ID
        note.setTitle(title);               // Set the note title
        note.setBody(body);                 // Set the note body

        // Add the note to the list of notes for this task
        taskNotesHolder.notes.add(note);

        // Increment the note ID for future notes
        taskNotesHolder.noteId++;

        // Return the newly created note
        return note;
    }
}
