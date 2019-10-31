package common.Util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class UrlSender {

	//private final String USER_AGENT = "Mozilla/5.0";

	public static void main(String[] args) throws Exception {

		UrlSender http = new UrlSender();


		http.sendGet();


		http.sendPost();

	}

	// HTTP GET request
	private void sendGet() throws Exception {

		String url = "http://localhost:8080/Bkeryah/Services?request=1&LicNewAplOwnerId=2392966590";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		//con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();



		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result


	}

	// HTTP POST request
	private void sendPost() throws Exception {

		String url = "http://localhost:8080/Bkeryah/Services";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// add reuqest header
		con.setDoOutput(true);
		con.setRequestMethod("POST");
	//	con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

		String urlParameters = "request=1&LicNewAplOwnerId=23925966590";

		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.close();

		int responseCode = con.getResponseCode();




		InputStream conInputStream = con.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(conInputStream));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result


	}

}
