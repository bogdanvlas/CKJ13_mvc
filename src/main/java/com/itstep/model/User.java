package com.itstep.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.ToString.Exclude;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	@JsonIgnore
	private String password;
	private String role;
	private String email;
	private boolean enabled;

	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	private List<Note> notes = new ArrayList<>();

	public void addNote(Note note) {
		note.setUser(this);
		notes.add(note);
	}
}
