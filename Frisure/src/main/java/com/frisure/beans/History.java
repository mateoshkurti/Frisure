package com.frisure.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {

	private Long histID;
	private String username;
	private Long businessID, haircutID, emplID;
}
