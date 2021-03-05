package com.itstep;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
	private List<Note> notes = new ArrayList<>();

	@GetMapping("/create")
	public String create() {
		return "create_note";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute(name = "note") Note note) {
		Comparator<Integer> cmp = (i1, i2) -> i1 - i2;
		// обеспечить уникальность идентификаторов
		int id = notes.stream().map(n -> n.getId())// получаю идентификаторы
				.max(cmp)// нахожу максимальный ид, если он есть
				.orElse(0);// если пустой, то 0
		note.setId(++id);
		notes.add(note);
		return "redirect:/notes";
	}

	@GetMapping("/notes")
	public String all(Model model) {
		model.addAttribute("notes", notes);
		return "notes";
	}

	@GetMapping("/notes/{id}")
	public String info(@PathVariable(name = "id") int id, Model model) {
		Note note = notes.stream().filter(n -> n.getId() == id).findFirst().get();
		model.addAttribute("note", note);
		return "info";
	}
}
