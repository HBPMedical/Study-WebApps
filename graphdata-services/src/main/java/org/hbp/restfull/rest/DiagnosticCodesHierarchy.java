package org.hbp.restfull.rest;

public class DiagnosticCodesHierarchy {
	private String diagnosis;
	private long total;
	private long men;
	private long women;
	
	public DiagnosticCodesHierarchy(String diagnosis, long total, long men, long women) {
		this.diagnosis = diagnosis;
		this.total = total;
		this.men = men;
		this.women = women;
	}
	
	public String getDiagnosis() {
		return diagnosis;
	}
	public void setDiagnosis(String diagnosis) {
		this.diagnosis = diagnosis;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public long getMen() {
		return men;
	}
	public void setMen(int men) {
		this.men = men;
	}
	public long getWomen() {
		return women;
	}
	public void setWomen(int women) {
		this.women = women;
	}
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\ndiagonosis :"+diagnosis);
		buffer.append("\ntotal :"+total);
		buffer.append("\nmen :"+men);
		buffer.append("\nwomen :"+women);
		return buffer.toString();
	}
	
}
