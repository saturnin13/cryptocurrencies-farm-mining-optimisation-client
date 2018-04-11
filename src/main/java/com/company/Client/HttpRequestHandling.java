package com.company.Client;

import com.company.MachineInformation.Configuration.ClientConfiguration;
import com.company.Miners.Configuration.MiningConfiguration;
import com.company.Miners.MinedCurrencyShortName;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static com.company.Variables.POST_REQUEST_TIMEOUT;

public class HttpRequestHandling {

    final static Logger logger = Logger.getLogger(URLConnection.class);
    private final String requestAddress = "https://crypto-mining-optimisation.herokuapp.com/";

    public MiningConfiguration getMiningConfiguration(ClientConfiguration clientConfiguration) {
        logger.info("Getting mining configurations");

        Gson g = new Gson();
        String jsonClientConfig = g.toJson(clientConfiguration);
        // TODO: remove hardcoding
        jsonClientConfig = "{\"userEmail\":\"saturnin.13@hotmail.fr\", \"data\":{\"sysconfig\":{\"OS\":\"linux\"}, \"benchMarking\":[]}}";
        String response = postRequest(requestAddress, jsonClientConfig, "POST");
        logger.info("Obtained the following mining configuration: " + response);
        return g.fromJson(response, MiningConfiguration.class);
    }

    // TODO: set recurring work on the server to delete the workers records in the database which are no longer valid (give them a timestamp)
    public void reportMiningDiagnosis(MinedCurrencyShortName currency, float hashRate) {
        logger.info("Sending a report of mining diagnosis with currency " + currency + " and hashrate of " + hashRate + " H/s");
        String request = "{\"userEmail\":\"saturnin.13@hotmail.fr\", \"workerName\": \"" + getWorkerName() + "\",\"currency\":\"" + currency + "\", \"hashrate\":\"" + hashRate + "\"}";
        postRequest(requestAddress, request, "PUT");
    }

    //TODO: remove hardcoded data
    private String postRequest(String urlToRead, String data, String requestMethod) {
        logger.trace("Sending a post request for the following url " + urlToRead + " with the following data " + data);
        try{
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlToRead);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            byte[] postData = data.getBytes(StandardCharsets.US_ASCII);

            conn.setRequestMethod(requestMethod);
            conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            conn.addRequestProperty("Accept", "*/*");
            conn.setConnectTimeout(POST_REQUEST_TIMEOUT / 2); // TODO : manage when a timeout happens
            conn.setReadTimeout(POST_REQUEST_TIMEOUT / 2);

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

            logger.trace("Post request got the following response: " + result);
            return result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Get request failed to the following url " + urlToRead);
            return "";
        }
    }

    private String getWorkerName() {
        String name = null;
        try {
            name = InetAddress.getLocalHost().getHostName() == null ? InetAddress.getLocalHost().getHostAddress(): InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return name;
    }
}
