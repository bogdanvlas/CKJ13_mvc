package com.itstep;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
		// обеспечить уникальность идентификаторов
		int id = notes.stream()
				.map(n -> n.getId())
				.max((i1, i2) -> i1 - i2).orElse(0);
		note.setId(++id);
		notes.add(note);
		return "redirect:/all";
	}

	@GetMapping("/all")
	public String all(Model model) {
		model.addAttribute("notes", notes);
		return "notes";
	}
}
