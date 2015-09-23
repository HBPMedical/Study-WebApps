package org.hbp.restfull.rest;

import java.util.List;

public class DiagnosticCodesData {
	private List<List<Object>> counts;
	private String keyword;
	private int limit;
	private String option;
	
	public DiagnosticCodesData(List<List<Object>> counts, String keyword, int limit, String option) {
		this.counts = counts;
		this.keyword = keyword;
		this.limit = limit;
		this.option = option;
	}
	
	public List<List<Object>> getCounts() {
		return counts;
	}
	public void setCounts(List<List<Object>> counts) {
		this.counts = counts;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}

}
