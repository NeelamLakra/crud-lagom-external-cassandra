package com.example.neelam.external.service.api;


import lombok.Builder;
import lombok.Value;

@Value
    @Builder
    public class ExternalJson {
        int userId;
        int id;
        String title;
        String body;
    }

