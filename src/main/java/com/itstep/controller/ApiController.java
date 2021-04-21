package com.itstep.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.itstep.model.Note;
import com.itstep.model.User;
import com.itstep.repository.NoteRepository;
import com.itstep.repository.UserRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

	private UserRepository userRepository;

	private NoteRepository noteRepository;

	@GetMapping("/users")
	public Iterable<User> getUsers() {
		return userRepository.findAll();
	}

	// получение всех заметок
	@GetMapping("/notes")
	public Iterable<Note> getNotes() {
		var notes = noteRepository.findAll();
		return notes;
	}

	// создание заметки
	@PostMapping("/notes")
	public Note saveNote(@RequestBody Note note) {
		note.setCreationDate(LocalDateTime.now());
		return noteRepository.save(note);
	}

	// получение одной заметки

	// редактирование заметки

	// удаление заметки
}
