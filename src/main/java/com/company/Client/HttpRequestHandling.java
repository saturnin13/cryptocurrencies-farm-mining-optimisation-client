package com.company.Client;

import com.company.Client.JsonFormat.ClientJson.MiningConfiguration.MiningConfigurationRequestData;
import com.company.Client.JsonFormat.ClientJson.MiningConfigurationRequest;
import com.company.Client.JsonFormat.ClientJson.ReportMiningDiagnosis.ReportMiningDiagnosisRequestData;
import com.company.Client.JsonFormat.ClientJson.ReportMiningDiagnosisRequest;
import com.company.Client.JsonFormat.ServerJson.MiningConfigurationResponse;
import com.google.gson.Gson;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;

import static com.company.Variables.HARDCODED_EMAIL;
import static com.company.Variables.POST_REQUEST_TIMEOUT;

public class HttpRequestHandling {

    final static Logger logger = Logger.getLogger(URLConnection.class);
    private final String requestAddress = "https://crypto-mining-optimisation.herokuapp.com/";

    public MiningConfigurationResponse getMiningConfiguration(MiningConfigurationRequestData miningConfigurationRequestData) {
        logger.info("Getting mining configurations");

        Gson g = new Gson();
        // TODO: remove hardcoding
        String request = g.toJson(MiningConfigurationRequest.builder().userEmail(HARDCODED_EMAIL).workerName(getWorkerName()).data(miningConfigurationRequestData).build());
        String response = postRequest(requestAddress, request, "POST");

        logger.info("Obtained the following mining configuration: " + response);
        return g.fromJson(response, MiningConfigurationResponse.class);
    }

    // TODO: set recurring work on the server to delete the workers records in the database which are no longer valid (give them a timestamp)
    public void reportMiningDiagnosis(ReportMiningDiagnosisRequestData reportMiningDiagnosisRequestData) {
        logger.info("Sending a report of mining diagnosis with currency " + reportMiningDiagnosisRequestData.getCurrency() + " and hashrate of " + reportMiningDiagnosisRequestData.getHashRate() + " H/s");

        Gson g = new Gson();
        String request = g.toJson(ReportMiningDiagnosisRequest.builder().userEmail(HARDCODED_EMAIL).workerName(getWorkerName()).data(reportMiningDiagnosisRequestData).build());
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
