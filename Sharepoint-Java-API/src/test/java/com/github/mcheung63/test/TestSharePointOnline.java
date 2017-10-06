/*
 * Copyright (C) 2017 Peter <peter@quantr.hk>.
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
package com.github.mcheung63.test;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.NTCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.NTLMSchemeFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.Test;

public class TestSharePointOnline {

	@Test
	public void test() throws Exception {
//		CredentialsProvider credsProvider = new BasicCredentialsProvider();
//		credsProvider.setCredentials(
//				new AuthScope("https://quantr.sharepoint.com", 80),
//				new UsernamePasswordCredentials("peter@quantr.hk", "1qaz2wsx#EDC"));
//		CloseableHttpClient httpclient = HttpClients.custom()
//				.setDefaultCredentialsProvider(credsProvider)
//				.build();

		String url = "https://quantr.sharepoint.com";
		//HttpClient httpclient = HttpClientBuilder.create().build();
		DefaultHttpClient httpclient = (DefaultHttpClient) new DefaultHttpClient();

		HttpGet request = new HttpGet(url);

		((AbstractHttpClient) httpclient).getAuthSchemes().register("ntlm", new NTLMSchemeFactory());
		CredentialsProvider provider = ((AbstractHttpClient) httpclient).getCredentialsProvider();
		NTCredentials creds = new NTCredentials("peter@quantr.hk", "1qaz2wsx#EDC", "", "");

		provider.setCredentials(AuthScope.ANY, creds);
		HttpResponse response = httpclient.execute(request);

		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

//		try {
//			HttpGet httpget = new HttpGet("http://quantr.sharepoint.com/_api/web/lists");
//
//			System.out.println("Executing request " + httpget.getRequestLine());
//			CloseableHttpResponse response = httpclient.execute(httpget);
//			System.out.println("response=" + response);
//			System.out.println();
//			try {
//				System.out.println("----------------------------------------");
//				System.out.println("statusline=" + response.getStatusLine());
//				EntityUtils.consume(response.getEntity());
//			} finally {
//				response.close();
//			}
//		} finally {
//			httpclient.close();
//		}
	}
}
