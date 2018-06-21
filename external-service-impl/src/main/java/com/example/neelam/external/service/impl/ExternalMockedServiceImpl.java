package com.example.neelam.external.service.impl;

import akka.NotUsed;
import com.example.neelam.external.service.api.ExternalJson;
import com.example.neelam.external.service.api.ExternalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class ExternalMockedServiceImpl implements ExternalService {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private String data = "{\n" +
            "  \"userId\": 1,\n" +
            "  \"id\": 1,\n" +
            "  \"title\": \"ABC\",\n" +
            "  \"body\": \"DEF\"\n" +
            "}";
    
    @Override
    public ServiceCall<NotUsed, ExternalJson> postStudent() {
        
        return req -> {
            ExternalJson externalJson = null;
            try {
                externalJson = MAPPER.readValue(data,ExternalJson.class);
                
            } catch (IOException ex) {
                System.out.println("Its exception :)\n" + ex.getMessage());
                
            }
            return CompletableFuture.completedFuture(externalJson);
        };
    }
}
