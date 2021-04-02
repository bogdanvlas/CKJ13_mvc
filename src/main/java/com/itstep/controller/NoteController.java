package com.itstep.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itstep.model.Note;
import com.itstep.model.User;
import com.itstep.repository.NoteRepository;
import com.itstep.repository.UserRepository;

@Controller
@RequestMapping("/notes")
public class NoteController {
	private NoteRepository noteRepository;

	private UserRepository userRepository;

	@Autowired
	public NoteController(NoteRepository noteRepository, UserRepository userRepository) {
		this.noteRepository = noteRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("/create")
	public String create() {
		return "create_note";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute(name = "note") Note note, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());

		note.setCreationDate(LocalDateTime.now());

		user.addNote(note);
		noteRepository.save(note);
		userRepository.save(user);

		return "redirect:/notes";
	}

	@GetMapping
	public String all(Model model, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		List<Note> notes = user.getNotes();
		System.out.println(notes);
		model.addAttribute("notes", user.getNotes());
		return "notes";
	}

	@GetMapping("/{id}")
	public String info(@PathVariable(name = "id") int id, Model model, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		List<Note> notes = user.getNotes();
		// ищем объект по ид
		Note note = noteRepository.findById(id).get();
		if (notes.contains(note)) {
			model.addAttribute("note", note);
			return "info";
		} else {
			return "redirect:/notes";
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") int id, Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		Note note = noteRepository.findById(id).get();
		if (user.getNotes().contains(note)) {
			noteRepository.deleteById(id);
		}
		return "redirect:/notes";
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable(name = "id") int id, Model model) {
		model.addAttribute("note", noteRepository.findById(id).get());
		return "edit";
	}

	@PostMapping("/change")
	public String change(@ModelAttribute("note") Note note) {
		// если такого ид нет, добавляет новую запись
		// если есть, редактирует существующую
		noteRepository.save(note);
		return "redirect:/notes";
	}

	@GetMapping("/search")
	public String search(@RequestParam(name = "word") String word, Model model,
			Principal prl) {
		User user = userRepository.findByUsername(prl.getName());
		word = "%" + word + "%";
		List<Note> notes = noteRepository.search(word, user.getId());
		model.addAttribute("notes", notes);
		return "notes";
	}
}
