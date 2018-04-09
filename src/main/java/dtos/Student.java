package dtos;

import java.time.LocalDate;
import java.util.Date;

import at.technikum.mse.est.Label;

public class Student {
	
	@Label("Name")
	private String name;
	
	@Label("Id")
	private Double id;
	
	@Label("Address")
	private String address;
//	
//	@Label("Date Of Birth")
//	private LocalDate dateOfBirth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getId() {
		return id;
	}

	public void setId(Double id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

//	public LocalDate getDateOfBirth() {
//		return dateOfBirth;
//	}
//
//	public void setDateOfBirth(LocalDate dateOfBirth) {
//		this.dateOfBirth = dateOfBirth;
//	}

	public Student(String name, Double id, String address) {
		this.name = name;
		this.id = id;
		this.address = address;
//		this.dateOfBirth = dateOfBirth;
	}
	
	

}
