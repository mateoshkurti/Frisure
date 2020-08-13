package com.frisure.beans;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkingHours {

	private Long businessID;
	private LocalTime oppeningHour, closingHour;
	private String day;
}
