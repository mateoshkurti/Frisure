package com.frisure.beans;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	private Long bookingID;
	private String username;
	private Long businessID;
	private LocalTime time;
	private LocalDate date;
	
}
