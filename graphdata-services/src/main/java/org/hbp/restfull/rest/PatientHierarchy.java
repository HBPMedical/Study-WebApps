package org.hbp.restfull.rest;

import java.util.List;

public class PatientHierarchy {
	private long year_of_birth;
	private long total;
	private long men;
	private long women;
	private String description;
	
	public PatientHierarchy(long year_of_birth, long total, long men, long women, String description) {
		this.year_of_birth = year_of_birth;
		this.total = total;
		this.men = men;
		this.women = women;
		this.description = description;
	}
	
	public long getYear_of_birth() {
		return year_of_birth;
	}
	public void setYear_of_birth(long year_of_birth) {
		this.year_of_birth = year_of_birth;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getMen() {
		return men;
	}
	public void setMen(long men) {
		this.men = men;
	}
	public long getWomen() {
		return women;
	}
	public void setWomen(long women) {
		this.women = women;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
