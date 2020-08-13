package com.frisure.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employees {

	private Long emplID;
	private String firstName, lastName, description;
	private Long businessID;
}
