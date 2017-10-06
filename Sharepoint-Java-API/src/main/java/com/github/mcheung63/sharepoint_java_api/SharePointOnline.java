package com.github.mcheung63.sharepoint_java_api;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class SharePointOnline {

	public static List<String> getListNames() throws Exception {
		String uri = "https://quantr.sharepoint.com/_api/web/lists";
		URL url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		connection.setRequestProperty("Accept", "application/xml");

//		JAXBContext jc = JAXBContext.newInstance(Customer.class);
		InputStream in = connection.getInputStream();
//		Customer customer = (Customer) jc.createUnmarshaller().unmarshal(xml);
		String str = IOUtils.toString(in);
		System.out.println(str);

		connection.disconnect();
		return null;
	}
}
