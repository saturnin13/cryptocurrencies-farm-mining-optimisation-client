package com.company.Client;

import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.Miners.Configuration.MiningConfiguration;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class HttpRequestHandling {

    final static Logger logger = Logger.getLogger(URLConnection.class);
    private final String requestAddress = "https://crypto-mining-optimisation.herokuapp.com/";

    public MiningConfiguration getMiningConfiguration(ClientConfiguration clientConfiguration) {
        Gson g = new Gson();
        String jsonClientConfig = g.toJson(clientConfiguration);
        // TODO: remove hardcoding
        jsonClientConfig = "{\"userEmail\":\"saturnin.13@hotmail.fr\", \"data\":{\"sysconfig\":{\"OS\":\"linux\"}, \"benchMarking\":[]}}";
        String response = postRequest(requestAddress, jsonClientConfig);
        return g.fromJson(response, MiningConfiguration.class);
    }


    //TODO: remove hardcoded data
    private String postRequest(String urlToRead, String data) {
        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            byte[] postData = data.getBytes(StandardCharsets.US_ASCII);

            conn.setRequestMethod("POST");
            conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            conn.addRequestProperty("Accept", "*/*");

            conn.setDoOutput(true);
            try(DataOutputStream wr = new DataOutputStream(conn.getOutputStream())) {
                wr.write(postData);
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line);
            }
            rd.close();

            logger.info("Sending a post request for the following url " + urlToRead + " with the following data " + data + " with a response of " + result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Get request failed to the following url " + urlToRead);
            return "";
        }
    }
}
