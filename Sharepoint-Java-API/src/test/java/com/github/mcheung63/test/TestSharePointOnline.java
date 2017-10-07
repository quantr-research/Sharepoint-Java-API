package com.github.mcheung63.test;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class TestSharePointOnline {

	@Test
	public void test() throws Exception {
		CloseableHttpClient httpclient = HttpClients.custom().setRetryHandler(new DefaultHttpRequestRetryHandler(0, false)).build();

		String user = "guest1@1234.hk";
		String pwd = "1234";
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(AuthScope.ANY, new NTCredentials(user, pwd, "", ""));

		HttpHost target = new HttpHost("quantr.sharepoint.com", 443, "https");
		HttpClientContext context = HttpClientContext.create();
		context.setCredentialsProvider(credsProvider);

		HttpGet request1 = new HttpGet("/_api/web?$select=Title");
		CloseableHttpResponse response1 = null;
		try {
			response1 = httpclient.execute(target, request1, context);
			EntityUtils.consume(response1.getEntity());
			System.out.println("1 : " + response1.getStatusLine().getStatusCode());
		} finally {
			if (response1 != null) {
				response1.close();
			}
		}

//		String file = "/Shared%20Documents/1.txt";
//		HttpGet request2 = new HttpGet("/_api/web/GetFileByServerRelativeUrl('" + file + "')/Etag");
//		CloseableHttpResponse response2 = null;
//		try {
//			response2 = httpclient.execute(target, request2, context);
//			EntityUtils.consume(response2.getEntity());
//			int rc = response2.getStatusLine().getStatusCode();
//			String reason = response2.getStatusLine().getReasonPhrase();
//			if (rc != HttpStatus.SC_OK) {
//				System.out.println(file + " is missing.  Reason : "
//						+ reason + "   rc : " + rc + "(500 is the equivalent of NOT FOUND)");
//			} else {
//				System.out.println(file + " exists.");
//			}
//		} finally {
//			if (response2 != null) {
//				response2.close();
//			}
//		}
	}
}
