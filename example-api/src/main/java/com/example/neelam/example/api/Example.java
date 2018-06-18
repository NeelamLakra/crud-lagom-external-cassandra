package com.example.neelam.example.api;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Example {
    Integer sid;
    String name;
    int rollno;
}
