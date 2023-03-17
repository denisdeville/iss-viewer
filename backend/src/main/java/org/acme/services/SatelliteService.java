package org.acme.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import org.acme.models.dto.SatelliteModel;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class SatelliteService {
    
    private final String whereTheIssAtBaseUrl =  "https://api.wheretheiss.at/v1/satellites/";

    public SatelliteModel GetSatelliteInformationById(long id)
    {
        String url =  whereTheIssAtBaseUrl + id;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .build();
     
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            String responseAsString = response.body();
            
            return new ObjectMapper().readValue(responseAsString, SatelliteModel.class);

        } catch(Exception e) {
            System.out.print("exception" + e);
        }
       
        return new SatelliteModel();
    }
    
    public List<SatelliteModel> GetSatelliteSunExposionById(long id, String timestamps)
    {

        String url = whereTheIssAtBaseUrl + id + "/positions?timestamps=" + timestamps;


        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
              .uri(URI.create(url))
              .build();
     
            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            String responseAsString = response.body();
            
            ObjectMapper mapper = new ObjectMapper();

            return mapper.readValue(responseAsString, mapper.getTypeFactory().constructCollectionType(List.class, SatelliteModel.class));

        } catch(Exception e) {
            System.out.print("exception" + e);
        }
       
        return new ArrayList<>();
    }

    public SatelliteModel GetSatelliteInformationByIdMock(long id)
    {

        double randomLatitude = Math.random() * 120;
        double randomLongitude = Math.random() * 60;

        SatelliteModel satelliteModel = new SatelliteModel();
        satelliteModel.setLatitude(randomLatitude);
        satelliteModel.setLongitude(randomLongitude);

        return satelliteModel;
    }
}
