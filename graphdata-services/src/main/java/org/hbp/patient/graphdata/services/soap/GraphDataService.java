package org.hbp.patient.graphdata.services.soap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.hbp.graphdata.services.soap.client.diagnostic.BarchartDiagnosticCodes;
import org.hbp.graphdata.services.soap.client.diagnostic.BarchartDiagnosticCodesService;
import org.hbp.graphdata.services.soap.client.diagnostic.GetBARCHARTDIAGNOSTICCODESBySEARCH;
import org.hbp.graphdata.services.soap.client.diagnostic.GetBARCHARTDIAGNOSTICCODESBySEARCHException_Exception;
import org.hbp.graphdata.services.soap.client.diagnostic.IBARCHARTDIAGNOSTICCODESReturn;
import org.hbp.graphdata.services.soap.client.diagnostic.IBARCHARTDIAGNOSTICCODESReturnRow;
import org.hbp.graphdata.services.soap.client.patient.BarchartPatient;
import org.hbp.graphdata.services.soap.client.patient.BarchartPatientService;
import org.hbp.graphdata.services.soap.client.patient.GetBARCHARTPATIENTBySEARCH;
import org.hbp.graphdata.services.soap.client.patient.GetBARCHARTPATIENTBySEARCHException_Exception;
import org.hbp.graphdata.services.soap.client.patient.IBARCHARTPATIENTReturn;
import org.hbp.graphdata.services.soap.client.patient.IBARCHARTPATIENTReturnRow;
import org.hbp.graphdata.services.soap.client.sunburst.GetSUNBURSTbySEARCH;
import org.hbp.graphdata.services.soap.client.sunburst.GetSUNBURSTbySEARCHException_Exception;
import org.hbp.graphdata.services.soap.client.sunburst.ISUNBURSTReturn;
import org.hbp.graphdata.services.soap.client.sunburst.ISUNBURSTReturnRow;
import org.hbp.graphdata.services.soap.client.sunburst.Sunburst;
import org.hbp.graphdata.services.soap.client.sunburst.SunburstService;
import org.hbp.restfull.rest.DiagnosticCodesData;
import org.hbp.restfull.rest.Hierarchy;
import org.hbp.restfull.rest.PatientData;
import org.hbp.restfull.rest.SunburstData;


/**
 * 
 * <h1>mock version</h1>
 * 
 * @author irmmp16
 *
 */
public class GraphDataService {

	private static StringBuffer getMockData(String filename) {
		File inFile;
		StringBuffer results = new StringBuffer();
		try {
			inFile = new File(GraphDataService.class.getResource(filename)
					.toURI());
			Reader in = new FileReader(inFile);
			StringWriter writer = new StringWriter();
			char[] buffer = new char[2096];
			int readByte = 0;
			while ((readByte = in.read(buffer)) > 0) {
				writer.append(String.valueOf(buffer), 0, readByte);
			}
			in.close();
			results = writer.getBuffer();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			results.append("### URISyntaxException");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			results.append("### FileNotFoundException");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			results.append("### IOException");
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			results.append("### NullPointerException / file not found");
		}
		return results;
	}

	public static StringBuffer getBargraphdata(Map<String, String> parameters) {
		StringBuffer resultStringBuffer = new StringBuffer();
		String searchString = parameters.get("keyword");
		String option = parameters.get("option");
		
		if (option.equals("patient")) {			
			GetBARCHARTPATIENTBySEARCH getBARCHARTPATIENTBySEARCH = new GetBARCHARTPATIENTBySEARCH();
			BarchartPatientService bargraphPatientService = new BarchartPatientService();
			BarchartPatient bargraphPatient = bargraphPatientService.getBarchartPatient();		
			getBARCHARTPATIENTBySEARCH.setSEARCH(searchString);
			IBARCHARTPATIENTReturn returnResult;
			try {
				returnResult = bargraphPatient.getBARCHARTPATIENTBySEARCH(getBARCHARTPATIENTBySEARCH);
				List<IBARCHARTPATIENTReturnRow> ibargraphPatientReturnRows = returnResult.getItem();
				
				ObjectMapper mapper = new ObjectMapper();
				List<List<Object>> counts = new ArrayList<List<Object>>();
				
				List<Object> header = new ArrayList<Object>();
				header.add("year_of_birth");
				header.add("total");
				header.add("men");
				header.add("women");
				header.add("description");
				counts.add(header);
				
				List<Object> itList;	
				for (IBARCHARTPATIENTReturnRow ibarchartpatientReturnRow : ibargraphPatientReturnRows) {
					itList = new ArrayList<Object>();
					itList.add(ibarchartpatientReturnRow.getYEAROFBIRTH());
					itList.add(ibarchartpatientReturnRow.getTOTAL());
					itList.add(ibarchartpatientReturnRow.getMALECNT());
					itList.add(ibarchartpatientReturnRow.getFEMALECNT());
					itList.add(ibarchartpatientReturnRow.getDESCRIPTION());
					counts.add(itList);
				}
				
				PatientData data = new PatientData(counts, searchString, ibargraphPatientReturnRows.size(), option);
				
				resultStringBuffer.append(mapper.writeValueAsString(data));				
				
			} catch (GetBARCHARTPATIENTBySEARCHException_Exception e) {
				e.printStackTrace();
			} catch (JsonGenerationException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		} else if(option.equals("diagnostic")) {
			
			GetBARCHARTDIAGNOSTICCODESBySEARCH getBARCHARTDIAGNOSTICCODESBySEARCH = new GetBARCHARTDIAGNOSTICCODESBySEARCH();
			BarchartDiagnosticCodesService bargraphDiagnosticCodesService = new BarchartDiagnosticCodesService();
			BarchartDiagnosticCodes bargraphDiagnosticCodes = bargraphDiagnosticCodesService.getBarchartDiagnosticCodes();
			getBARCHARTDIAGNOSTICCODESBySEARCH.setSEARCH(searchString);
			IBARCHARTDIAGNOSTICCODESReturn returnResult;
			
			
				try {
					returnResult = bargraphDiagnosticCodes.getBARCHARTDIAGNOSTICCODESBySEARCH(getBARCHARTDIAGNOSTICCODESBySEARCH);
					List<IBARCHARTDIAGNOSTICCODESReturnRow> items = returnResult.getItem();
					
					ObjectMapper mapper = new ObjectMapper();
					List<List<Object>> counts = new ArrayList<List<Object>>();
	
					// adding header
					List<Object> header = new ArrayList<Object>();
					header.add("diagnosis");
					header.add("total");
					header.add("men");
					header.add("women");
					counts.add(header);
	
					List<Object> itList;
					
					for (IBARCHARTDIAGNOSTICCODESReturnRow item : items) {
						itList = new ArrayList<Object>();
						
						itList.add(item.getDESCRIPTION());
						itList.add(item.getTOTAL());
						itList.add(item.getMALECNT());
						itList.add(item.getFEMALECNT());
						
						counts.add(itList);
					}
					
					DiagnosticCodesData data = new DiagnosticCodesData(counts, searchString, items.size(), option);				
					resultStringBuffer.append(mapper.writeValueAsString(data));	
					
				} catch (GetBARCHARTDIAGNOSTICCODESBySEARCHException_Exception e) {
					e.printStackTrace();
				} catch (JsonGenerationException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
		

		}
		return resultStringBuffer;
	}

	public static StringBuffer getSunburstdata(Map<String, String> parameters) {
		StringBuffer resultStringBuffer = new StringBuffer();
		Logger log = Logger.getLogger(GraphDataService.class.getSimpleName());
		GetSUNBURSTbySEARCH getSUNBURSTbySEARCH = new GetSUNBURSTbySEARCH();
		SunburstService sunburstService = new SunburstService();
		Sunburst sunburst = sunburstService.getSunburst();
		String searchString = parameters.get("keyword");
		log.info("Query with " + searchString);
		try {
			getSUNBURSTbySEARCH.setSEARCH(searchString);
			ISUNBURSTReturn returnResult = sunburst
					.getSUNBURSTbySEARCH(getSUNBURSTbySEARCH);
			List<ISUNBURSTReturnRow> isunburstReturnRows = returnResult
					.getItem();

			SunburstData sunburstData = getJSonObjectMapper();
			List<Hierarchy> hierarchiesDesc = sunburstData.getCounts();
			ArrayNode hierarchyNodes = JsonNodeFactory.instance.arrayNode();
			for (Iterator<ISUNBURSTReturnRow> iterRows = isunburstReturnRows
					.iterator(); iterRows.hasNext();) {
				ISUNBURSTReturnRow isunburstReturnRow = iterRows.next();
				ObjectNode hierarchy = JsonNodeFactory.instance.objectNode();
				String code = isunburstReturnRow.getDIAGNOSTICCODE();
				hierarchy.put("code", code);
				hierarchy.put("counts", isunburstReturnRow.getTOTAL());
				hierarchy.put("description",
						isunburstReturnRow.getDESCRIPTION());
				// find hierarchy for a code
				Hierarchy hierarchyDetail = getHierachyByCode(hierarchiesDesc,
						code);
				ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
				for (Iterator<String> iterator2 = hierarchyDetail
						.getHierarchy().iterator(); iterator2.hasNext();) {
					String hierarchy2 = iterator2.next();
					arrayNode.add(hierarchy2);
				}
				hierarchy.put("hierarchy", arrayNode);
				hierarchyNodes.add(hierarchy);
				log.info(hierarchy.toString());
			}
			JsonNode sunburstJSonNodes = getJSonObjectMapperWithoutCounts();
			((ObjectNode) sunburstJSonNodes).put("counts", hierarchyNodes);
			log.info(sunburstJSonNodes.toString());
			resultStringBuffer.append(sunburstJSonNodes.toString());
		} catch (GetSUNBURSTbySEARCHException_Exception e) {
			e.printStackTrace();
		}
		return resultStringBuffer;
	}

	static Hierarchy getHierachyByCode(List<Hierarchy> hierarchies, String code) {
		Hierarchy hierarchy = new Hierarchy();
		for (Iterator<Hierarchy> iterator = hierarchies.iterator(); iterator
				.hasNext();) {
			Hierarchy hierarchyItem = iterator.next();
			if (hierarchyItem.getCode().equals(code)) {
				hierarchy = hierarchyItem;
				break;
			}
		}
		return hierarchy;
	}

	static SunburstData getJSonObjectMapper() {

		String filename = "sunburst_data.json";
		SunburstData sunburstData = null;
		File inFile;
		try {
			inFile = new File(GraphDataService.class.getResource(filename)
					.toURI());
			JsonParser jsonData = new JsonFactory().createJsonParser(inFile);

			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// convert json string to object
			// {hierarchy=[Diseases of the circulatory system, Hypertensive
			// diseases, Essential hypertension , unspecified : Without mention
			// of hypertensive crisis], counts=1769, code=I10.90,
			// description=Essential hypertension , unspecified : Without
			// mention of hypertensive crisis}
			sunburstData = objectMapper.readValue(jsonData, SunburstData.class);
			return sunburstData;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return sunburstData;
	}

	static JsonNode getJSonObjectMapperWithoutCounts() {
		String filename = "sunburst_data.json";
		JsonNode sunburstData = null;
		File inFile;
		InputStream inStream;
		try {
			inStream = GraphDataService.class.getResourceAsStream(filename);

			// create ObjectMapper instance
			ObjectMapper objectMapper = new ObjectMapper();

			// convert json string to object
			// {hierarchy=[Diseases of the circulatory system, Hypertensive
			// diseases, Essential hypertension , unspecified : Without mention
			// of hypertensive crisis], counts=1769, code=I10.90,
			// description=Essential hypertension , unspecified : Without
			// mention of hypertensive crisis}
			sunburstData = objectMapper.readTree(inStream);
			((ObjectNode) sunburstData).put("counts",
					JsonNodeFactory.instance.arrayNode());
			return sunburstData;
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sunburstData;
	}

}
