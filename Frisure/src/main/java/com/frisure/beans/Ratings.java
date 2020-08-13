package com.frisure.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ratings {

	private Long ratingID;
	private String title, description, username;
	private Long businessID;
}
