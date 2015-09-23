package org.hbp.patient.graphdata.services.soap;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
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
import org.hbp.patient.graphdata.services.soap.GraphDataService;
import org.hbp.restfull.rest.DiagnosticCodesData;
import org.hbp.restfull.rest.Hierarchy;
import org.hbp.restfull.rest.JSONGraphDataService;
import org.hbp.restfull.rest.SunburstData;
import org.junit.Ignore;
import org.junit.Test;


public class GraphDataServiceTest {
	Logger log = Logger.getLogger(getClass().getSimpleName());

	@Ignore	
	@Test
	public void getBargraphTest() {
//		brain_feature year_of_birth gender city country
		Map<String ,String> parameters = new HashMap<String, String>();
		parameters.put("keyword", "lh.pial");
		parameters.put("option", "lh.pial");
		
		StringBuffer obj = GraphDataService.getBargraphdata(parameters);
		log.info(obj.toString());
	}

	
	@Test
	public void getBargraphPatientWSTest() throws GetBARCHARTPATIENTBySEARCHException_Exception {
		Logger log = Logger.getLogger(getClass().getSimpleName());
		
		GetBARCHARTPATIENTBySEARCH getBARCHARTPATIENTBySEARCH = new GetBARCHARTPATIENTBySEARCH();
		BarchartPatientService bargraphPatientService = new BarchartPatientService();
		BarchartPatient bargraphPatient = bargraphPatientService.getBarchartPatient();		
		String searchString = "mastoid";
		log.info("BargraphPatient Query with "+searchString);
		getBARCHARTPATIENTBySEARCH.setSEARCH(searchString);
		IBARCHARTPATIENTReturn returnResult =  bargraphPatient.getBARCHARTPATIENTBySEARCH(getBARCHARTPATIENTBySEARCH);
		List<IBARCHARTPATIENTReturnRow> ibargraphPatientReturnRows = returnResult.getItem();
		assertNotNull(ibargraphPatientReturnRows);
	}

	@Test
	public void getBargraphDiagnosticCodeTest() throws GetBARCHARTDIAGNOSTICCODESBySEARCHException_Exception {
		Logger log = Logger.getLogger(getClass().getSimpleName());
		
		JSONGraphDataService dataService = new JSONGraphDataService();
		String  result = dataService.getBargraph("keyword=mastoid", "option=diagnostic");
		assertNotNull(result);
		log.info(result);
	}
	
	@Test
	public void getBargraphPatientTest() {
		Logger log = Logger.getLogger(getClass().getSimpleName());
		
		JSONGraphDataService dataService = new JSONGraphDataService();
		String  result = dataService.getBargraph("keyword=mastoid", "option=patient");
		assertNotNull(result);
		log.info(result);
	}

	
	@Test
	public void getSunburstWSTest() {
//		brain_feature year_of_birth gender city country
		GetSUNBURSTbySEARCH getSUNBURSTbySEARCH = new GetSUNBURSTbySEARCH();
		SunburstService sunburstService = new SunburstService();
		Sunburst sunburst = sunburstService.getSunburst();
		try {
			String searchString = "mastoid";
			log.info("Query with "+searchString);
			getSUNBURSTbySEARCH.setSEARCH(searchString);
			ISUNBURSTReturn returnResult =  sunburst.getSUNBURSTbySEARCH(getSUNBURSTbySEARCH);
			List<ISUNBURSTReturnRow> isunburstReturnRows = returnResult.getItem();
			assertNotNull(isunburstReturnRows);
			SunburstData sunburstData = GraphDataService.getJSonObjectMapper();
			List<Hierarchy> hierarchiesDesc = sunburstData.getCounts();
			
			JsonNode sunburstJSonNodes = GraphDataService.getJSonObjectMapperWithoutCounts();
			ArrayNode hierarchyNodes = JsonNodeFactory.instance.arrayNode();
			for (Iterator<ISUNBURSTReturnRow> iterator = isunburstReturnRows.iterator(); iterator
					.hasNext();) {
				ISUNBURSTReturnRow isunburstReturnRow = iterator.next();
				 
				ObjectNode hierarchy = JsonNodeFactory.instance.objectNode();
				String code = isunburstReturnRow.getDIAGNOSTICCODE();
				hierarchy.put("code",code);
				hierarchy.put("counts",isunburstReturnRow.getTOTAL());
				hierarchy.put("description",isunburstReturnRow.getDESCRIPTION());
				Hierarchy hierarchyDetail = GraphDataService.getHierachyByCode(hierarchiesDesc, code);
				ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
				for (Iterator<String> iterator2 = hierarchyDetail.getHierarchy().iterator(); iterator2
						.hasNext();) {
					String hierarchy2 = iterator2.next();
					arrayNode.add(hierarchy2);
				}
				hierarchy.put("hierarchy",arrayNode);
				hierarchyNodes.add(hierarchy);
				log.info(hierarchy.toString());
			}
			((ObjectNode) sunburstJSonNodes).put("counts",hierarchyNodes);
			log.info(sunburstJSonNodes.toString());
			
		} catch (GetSUNBURSTbySEARCHException_Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void getSunburstDataTest() {
		log.info("Query Webservice with mastoid");
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("keyword","mastoid");
		StringBuffer jsonSring = GraphDataService.getSunburstdata(parameters);
		log.info(jsonSring.toString());
	}

	
	@Ignore
	@Test
	public void getJSonJacksonTest() {
		try {
			String filename = "sunburst_data.json";
			File inFile = new File(GraphDataService.class.getResource(filename).toURI());
			JsonParser jsonParser = new JsonFactory().createJsonParser(inFile);
			JsonToken jsonToken = jsonParser.getCurrentToken();
			JsonNode jsonNode = jsonParser.readValueAsTree();
			jsonNode.findPath("counts");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Ignore
	@Test
	public void getJSonObjectMapperTest() {

		SunburstData sunburstData = GraphDataService.getJSonObjectMapper();

		log.info("SunburstData Object\n" + sunburstData);
		List<Hierarchy> countsJSon = sunburstData.getCounts();
		log.info("\n" + countsJSon);
		LinkedHashMap<String, String> colorsJSon = (LinkedHashMap<String, String>) sunburstData
				.getColors();
		log.info("\n" + colorsJSon);
		Hierarchy hierarchy = new Hierarchy();
		List<String> hierarchyStrings = new ArrayList<String>();
		hierarchyStrings.add("Symptoms");
		hierarchy.setHierarchy(hierarchyStrings);
		hierarchy.setCode("H52.2");
		hierarchy.setCounts(666l);
		hierarchy.setDescription("Astigmatism");
		List<Hierarchy> hierarchies = new ArrayList<Hierarchy>();
		hierarchies.add(hierarchy);
		sunburstData.setCounts(hierarchies);
		log.info("SunburstData New Object\n" + sunburstData);

	}

		@Test
		public void getJSonJacksonJDomTest() {
			try {
				String filename = "sunburst_data.json";
				InputStream inStream = GraphDataService.class.getResourceAsStream(filename);
				ObjectMapper objectMapper = new ObjectMapper();
				JsonNode rootNode = objectMapper.readTree(inStream);
				log.info("JSON rootNode "+rootNode.toString());
				JsonNode countsNode = rootNode.get("counts");
				log.info("raw JSON countsNode "+countsNode.toString());
				ArrayNode newCountsNode = JsonNodeFactory.instance.arrayNode();
				
				// {hierarchy=[Diseases of the circulatory system, Hypertensive diseases, Essential hypertension , unspecified : Without mention of hypertensive crisis], 
				// counts=1769, 
				// code=I10.90, 
				// description=Essential hypertension , unspecified : Without mention of hypertensive crisis}
				Map<String, Object> hierarchy = new HashMap<String, Object>();
				hierarchy.put("hierarchy",JsonNodeFactory.instance.arrayNode());
				hierarchy.put("counts",1999l);
				hierarchy.put("code","I10.90");
				hierarchy.put("description","Essential hypertension , unspecified : Without mention of hypertensive crisis");
				newCountsNode.addPOJO(hierarchy);
				newCountsNode.addPOJO(hierarchy);
				((ObjectNode) rootNode).put("counts", newCountsNode);
				log.info("JSON rootNode "+rootNode.toString());

			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}


		@Test
		public void getBargraphDiagnosticCodeWSTest() throws GetBARCHARTDIAGNOSTICCODESBySEARCHException_Exception {
			Logger log = Logger.getLogger(getClass().getSimpleName());
			
			GetBARCHARTDIAGNOSTICCODESBySEARCH getBARCHARTDIAGNOSTICCODESBySEARCH = new GetBARCHARTDIAGNOSTICCODESBySEARCH();
			BarchartDiagnosticCodesService bargraphDiagnosticCodesService = new BarchartDiagnosticCodesService();
			BarchartDiagnosticCodes bargraphDiagnosticCodes = bargraphDiagnosticCodesService.getBarchartDiagnosticCodes();		
			String searchString = "mastoid";
			log.info("BargraphDiagnosticCode Query with "+searchString);
			getBARCHARTDIAGNOSTICCODESBySEARCH.setSEARCH(searchString);
			IBARCHARTDIAGNOSTICCODESReturn returnResult =  bargraphDiagnosticCodes.getBARCHARTDIAGNOSTICCODESBySEARCH(getBARCHARTDIAGNOSTICCODESBySEARCH);
			List<IBARCHARTDIAGNOSTICCODESReturnRow> ibargraphDiagnosticCodesReturnRows = returnResult.getItem();
			assertNotNull(ibargraphDiagnosticCodesReturnRows);
			
			ObjectMapper mapper = new ObjectMapper();
			List<List<Object>> counts = new ArrayList<List<Object>>();
			
			//adding header
			List<Object> header = new ArrayList<Object>();
			header.add("diagnosis");
			header.add("total");
			header.add("men");
			header.add("women");
			counts.add(header);
			
			
			List<Object> itList;
			for (Iterator<IBARCHARTDIAGNOSTICCODESReturnRow> iterator = ibargraphDiagnosticCodesReturnRows.iterator(); iterator
					.hasNext();) {
				IBARCHARTDIAGNOSTICCODESReturnRow ibarchartdiagnosticcodesReturnRow = iterator.next();
				log.info("BARCHARTDIAGNOSTICCODESReturnRow result "+ibarchartdiagnosticcodesReturnRow.getDESCRIPTION());
				itList = new ArrayList<Object>();
				itList.add(ibarchartdiagnosticcodesReturnRow.getDESCRIPTION());
				itList.add(ibarchartdiagnosticcodesReturnRow.getTOTAL());
				itList.add(ibarchartdiagnosticcodesReturnRow.getMALECNT());
				itList.add(ibarchartdiagnosticcodesReturnRow.getFEMALECNT());
				counts.add(itList);
			}
			
			DiagnosticCodesData data = new DiagnosticCodesData(counts, "pain", 18, "counts");
					
			try {
				//mapper.writeValue(new File("test.json"), data);
				log.info("JSON response to DiagnosticCodes :\n"+mapper.writeValueAsString(data));
			} catch (JsonGenerationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

}
