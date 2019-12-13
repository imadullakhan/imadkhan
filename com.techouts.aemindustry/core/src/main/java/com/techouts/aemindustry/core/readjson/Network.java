package com.techouts.aemindustry.core.readjson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public final class Network {

	private static final String USER_AGENT = "Chrome/5.0";

	public static String readJson(String url) {

		try {

			URL requestUrl = new URL(url);

			HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();

			connection.setRequestMethod("GET");

			connection.setRequestProperty("User-Agent", USER_AGENT);

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {

				BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

				String inputLine;

				StringBuffer responceSB = new StringBuffer();

				while ((inputLine = br.readLine()) != null) {

					responceSB.append(inputLine);
				}

				br.close();

				return responceSB.toString();

			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";

	}

}
