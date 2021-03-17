package com.itstep;

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

@Controller
@RequestMapping("/notes")
public class NoteController {
	private NoteRepository noteRepository;

	@Autowired
	public NoteController(NoteRepository noteRepository) {
		this.noteRepository = noteRepository;
	}

	@GetMapping("/create")
	public String create() {
		return "create_note";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute(name = "note") Note note) {
		noteRepository.save(note);
		return "redirect:/notes";
	}

	@GetMapping
	public String all(Model model) {
		model.addAttribute("notes", noteRepository.findAll());
		return "notes";
	}

	@GetMapping("/{id}")
	public String info(@PathVariable(name = "id") int id, Model model) {
		// ищем объект по ид
		Note note = noteRepository.findById(id).get();
		model.addAttribute("note", note);
		return "info";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") int id) {
		noteRepository.deleteById(id);
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
	public String search(@RequestParam(name = "word") String word, Model model) {
//		List<Note> notes = noteRepository.findByTitleContainingOrMessageContaining(word, word);
		List<Note> notes = noteRepository.search("%" + word + "%");
		model.addAttribute("notes", notes);
		return "notes";
	}
}
