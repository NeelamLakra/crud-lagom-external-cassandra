package com.example.neelam.external.service.api;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;


@Value
@AllArgsConstructor
    @Builder
    public class ExternalJson {
        int userId;
        int id;
        String title;
        String body;
    }

