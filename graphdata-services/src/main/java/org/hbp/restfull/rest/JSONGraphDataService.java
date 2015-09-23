package org.hbp.restfull.rest;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.hbp.patient.graphdata.services.soap.GraphDataService;

@Path("/")
public class JSONGraphDataService {

	@GET
	@Path("bargraph/query")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 *  call synopsis
	 *  http://localhost:8080/services-graphdata/bargraph/query?keyword=hello&option=patient
	 *  
	 * @param keyword
	 * @return
	 */
	public String getBargraph(
			@QueryParam("keyword") String keyword ,
			@QueryParam("option") String option ) {

		Map<String, String> query = new HashMap<String, String>();
		query.put("keyword",keyword);
		query.put("option",option);
		StringBuffer results = GraphDataService.getBargraphdata(query);
		return results.toString();
	}
	
	
	@GET
	@Path("sunburst/query")
	@Produces(MediaType.APPLICATION_JSON)
	/**
	 * 
	 *  call synopsis
	 *  http://localhost:8080/services-graphdata/sunburst/query?keyword=hello
	 *  
	 * @param keyword
	 * @return
	 */
	public String getSunburst(
			@QueryParam("keyword") String keyword) {

		Map<String, String> query = new HashMap<String, String>();
		query.put("keyword",keyword);
		StringBuffer results = GraphDataService.getSunburstdata(query);
		return results.toString();
	}
	
}