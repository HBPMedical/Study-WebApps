package org.hbp.patient.graphdata.services.soap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * <h1>mock version</h1>
 * @author irmmp16
 *
 */
public class GraphDataService {

	private static StringBuffer getMockData(String filename){
		File inFile;
		StringBuffer results = new StringBuffer();
		try {
			inFile = new File(GraphDataService.class.getResource(filename).toURI());
			Reader in = new FileReader(inFile);
			StringWriter writer = new StringWriter();
			char[] buffer = new char[2096];
			int readByte = 0;
			while((readByte = in.read(buffer)) > 0 ){
				writer.append(String.valueOf(buffer),0 , readByte);
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

	public static StringBuffer getBargraphdata(Map<String, String> parameters){
		return getMockData("bargraph_data.json");
	}

	public static StringBuffer getSunburstdata(Map<String, String> parameters){
		return getMockData("sunburst_data.json");
	}

}
