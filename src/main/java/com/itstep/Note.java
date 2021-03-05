package com.itstep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//get, set, equals, hashCode, toString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
	private int id;
	private String title;
	private String message;
}
