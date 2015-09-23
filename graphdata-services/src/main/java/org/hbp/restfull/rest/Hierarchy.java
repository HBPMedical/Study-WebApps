package org.hbp.restfull.rest;

import java.util.ArrayList;
import java.util.List;

// {hierarchy=[Diseases of the circulatory system, Hypertensive diseases, Essential hypertension , unspecified : Without mention of hypertensive crisis], counts=1769, code=I10.90, description=Essential hypertension , unspecified : Without mention of hypertensive crisis}
public class Hierarchy {
	List<String> hierarchy = new ArrayList<String>();
	Long counts;
	String code;
	String description;
	public List<String> getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(List<String> hierarchy) {
		this.hierarchy = hierarchy;
	}
	public Long getCounts() {
		return counts;
	}
	public void setCounts(Long counts) {
		this.counts = counts;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nhierarchy :"+hierarchy);
		buffer.append("\ncounts :"+counts);
		buffer.append("\ncode :"+code);
		buffer.append("\ndescription :"+description);
		return buffer.toString();
	}
}