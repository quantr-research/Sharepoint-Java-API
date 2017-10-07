/*
 * Copyright (C) 2017 Peter <mcheung63@hotmail.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */
package hk.quantr.sharepoint;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Peter <mcheung63@hotmail.com>
 */
public class SPOnline {

	public static Pair<String, String> login(String username, String password, String domain) {
		Pair<String, String> result;
		String token;
		try {
			token = requestToken(domain);
			result = submitToken(domain, token);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String generateSAML(String domain) {
		String reqXML = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><s:Envelope xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:a=\"http://www.w3.org/2005/08/addressing\" xmlns:u=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><s:Header><a:Action s:mustUnderstand=\"1\">http://schemas.xmlsoap.org/ws/2005/02/trust/RST/Issue</a:Action><a:ReplyTo><a:Address>http://www.w3.org/2005/08/addressing/anonymous</a:Address></a:ReplyTo><a:To s:mustUnderstand=\"1\">https://login.microsoftonline.com/extSTS.srf</a:To><o:Security s:mustUnderstand=\"1\" xmlns:o=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\"><o:UsernameToken><o:Username>[username]</o:Username><o:Password>[password]</o:Password></o:UsernameToken></o:Security></s:Header><s:Body><t:RequestSecurityToken xmlns:t=\"http://schemas.xmlsoap.org/ws/2005/02/trust\"><wsp:AppliesTo xmlns:wsp=\"http://schemas.xmlsoap.org/ws/2004/09/policy\"><a:EndpointReference><a:Address>[endpoint]</a:Address></a:EndpointReference></wsp:AppliesTo><t:KeyType>http://schemas.xmlsoap.org/ws/2005/05/identity/NoProofKey</t:KeyType><t:RequestType>http://schemas.xmlsoap.org/ws/2005/02/trust/Issue</t:RequestType><t:TokenType>urn:oasis:names:tc:SAML:1.0:assertion</t:TokenType></t:RequestSecurityToken></s:Body></s:Envelope>";
		String saml = reqXML.replace("[username]", "peter@1234.hk");
		saml = saml.replace("[password]", "1234");
		saml = saml.replace("[endpoint]", String.format("https://%s.sharepoint.com/_forms/default.aspx?wa=wsignin1.0", domain));
		return saml;
	}

	private static String requestToken(String domain) throws XPathExpressionException, SAXException, ParserConfigurationException, IOException {
		String saml = generateSAML(domain);
		String sts = "https://login.microsoftonline.com/extSTS.srf";
		URL u = new URL(sts);
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
		OutputStream out = connection.getOutputStream();
		Writer writer = new OutputStreamWriter(out);
		writer.write(saml);

		writer.flush();
		writer.close();

		InputStream in = connection.getInputStream();
		int c;
		StringBuilder sb = new StringBuilder("");
		while ((c = in.read()) != -1) {
			sb.append((char) (c));
		}
		in.close();
		String result = sb.toString();
		String token = extractToken(result);
		return token;
	}

	private static String extractToken(String result) throws SAXException, IOException, ParserConfigurationException, XPathExpressionException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.parse(new InputSource(new StringReader(result)));
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xp = xpf.newXPath();
		String token = xp.evaluate("//BinarySecurityToken/text()", document.getDocumentElement());
		return token;
	}

	private static Pair<String, String> submitToken(String domain, String token) throws IOException {
		String loginContextPath = "/_forms/default.aspx?wa=wsignin1.0";
		String url = String.format("https://%s.sharepoint.com%s", domain, loginContextPath);
		URL u = new URL(url);
		URLConnection uc = u.openConnection();
		HttpURLConnection connection = (HttpURLConnection) uc;

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.addRequestProperty("Accept", "application/x-www-form-urlencoded");
		connection.addRequestProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Win64; x64; Trident/5.0)");
		connection.addRequestProperty("Content-Type", "text/xml; charset=utf-8");
		connection.setInstanceFollowRedirects(false);
		OutputStream out = connection.getOutputStream();
		Writer writer = new OutputStreamWriter(out);
		writer.write(token);
		writer.flush();
		writer.close();
		out.close();

		InputStream in = connection.getInputStream();
		String rtFa = null;
		String fedAuth = null;
		for (int i = 0;; i++) {
			String headerName = connection.getHeaderFieldKey(i);
			String headerValue = connection.getHeaderField(i);
			if (headerName == null && headerValue == null) {
				break;
			}
			if (headerName == null) {
			} else {
				if (headerName.equals("Set-Cookie") && headerValue.startsWith("rtFa=")) {
					rtFa = headerValue;
				} else if (headerName.equals("Set-Cookie") && headerValue.startsWith("FedAuth=")) {
					fedAuth = headerValue;
				}
			}
		}

		Pair<String, String> result = ImmutablePair.of(rtFa, fedAuth);

//		System.out.println("rtFa1=" + rtFa);
//		System.out.println("FedAuth1=" + fedAuth);
//		String headerName = connection.getHeaderField("Set-Cookie");
//		System.out.println("headerName=" + headerName);
//		int c;
//		StringBuilder sb = new StringBuilder("");
//		while ((c = in.read()) != -1) {
//			sb.append((char) (c));
//		}
//		in.close();
//		String result = sb.toString();
//		System.out.println("loginResult=" + result);
		return result;
	}

}
