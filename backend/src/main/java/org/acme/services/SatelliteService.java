package org.acme.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import javax.enterprise.context.ApplicationScoped;

import org.acme.models.dto.SatelliteModel;

import com.fasterxml.jackson.databind.ObjectMapper;

@ApplicationScoped
public class SatelliteService {
    
    public SatelliteModel GetSatelliteById(long id)
    {
        String url = "https://api.wheretheiss.at/v1/satellites/" + id;

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
}
