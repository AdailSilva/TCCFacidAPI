package com.adailsilva.tcc.facid.api.mqtt;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class PushBullet {

	// Constants
	final static String accessToken = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
	final static String host = "https://api.pushbullet.com:443/v2/pushes";

	public static void send(String status) {
		// Locals
		URL url = null;
		HttpURLConnection con = null;
		int responseCode;
		String postData = "{\"body\":\"Soil is too " + status + "!\",\"title\":\"Java Soil Alert!\",\"type\":\"note\"}";
		// Send message
		try {
			url = new URL(host);
			con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Authorization", "Bearer " + accessToken);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Content-Length", Integer.toString(postData.length()));
			con.getOutputStream().write(postData.getBytes("UTF8"));
			// Check response
			responseCode = con.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
