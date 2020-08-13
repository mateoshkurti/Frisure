package com.frisure.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Services {

	private Long serviceID;
	private String name;
	private Long price;
	private String description;
	private Long businessID;
}
