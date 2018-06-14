package com.example.neelam.example.api;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Value;

@Value
@JsonDeserialize
@Builder
public class Example {
    Integer sid;
    String name;
    int rollno;
}
