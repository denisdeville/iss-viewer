package org.acme;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;

import org.acme.exceptions.CustomException;
import org.acme.models.wheretheissat.WhereTheIssAtException;

import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpUtilities {
    
    private static HttpClient client = HttpClient.newHttpClient();

    public static HttpResponse<String> getRequest(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();
   
          return client.send(request, BodyHandlers.ofString());
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T handleResponse(HttpResponse<String> response, Class<T> parseTarget) throws CustomException {
        String bodyAsString = response.body();
        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.readValue(bodyAsString, parseTarget);
            } catch(Exception ex) {
                throw new CustomException(ex.getMessage(), 500);
            }
        } else {
            WhereTheIssAtException exception;
            try {
                exception = new ObjectMapper().readValue(bodyAsString, WhereTheIssAtException.class);
            } catch(Exception ex) {
                throw new CustomException(ex.getMessage(), 500);
            }
            throw new CustomException(exception.getError(), exception.getStatusCode());
        }
    }

    public static <T> List<T> handleResponseList(HttpResponse<String> response, Class<T> parseTarget) throws CustomException {
        String bodyAsString = response.body();
        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return  mapper.readValue(bodyAsString, mapper.getTypeFactory().constructCollectionType(List.class, parseTarget));
            } catch(Exception ex) {
                throw new CustomException(ex.getMessage(), 500);
            }
        } else {
            WhereTheIssAtException exception;
            try {
                exception = new ObjectMapper().readValue(bodyAsString, WhereTheIssAtException.class);
            } catch(Exception ex) {
                throw new CustomException(ex.getMessage(), 500);
            }
            throw new CustomException(exception.getError(), exception.getStatusCode());
        }
    }
}
