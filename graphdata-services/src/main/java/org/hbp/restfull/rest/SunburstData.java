package org.hbp.restfull.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SunburstData {

	public List<Hierarchy> counts;
	public Map<String, String> colors;
	public Map<String, String> legend;

	public List<Hierarchy> getCounts() {
		return counts;
	}
	public void setCounts(List<Hierarchy> counts) {
		this.counts = counts;
	}

	public Map<String, String> getColors() {
		return colors;
	}
	public void setColors(Map<String, String> colors) {
		this.colors = colors;
	}
	public Map<String, String> getLegend() {
		return legend;
	}
	public void setLegend(Map<String, String> legend) {
		this.legend = legend;
	}
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("counts" +counts +"\ncolors" +colors+"\nlegend" +legend);
		return buffer.toString();
	}
}