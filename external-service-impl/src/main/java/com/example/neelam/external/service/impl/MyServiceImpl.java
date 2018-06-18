package com.example.neelam.external.service.impl;

import akka.NotUsed;
import com.example.neelam.external.service.api.ExternalJson;
import com.example.neelam.external.service.api.ExternalService;
import com.example.neelam.external.service.api.MyService;
import com.lightbend.lagom.javadsl.api.ServiceCall;

import javax.inject.Inject;

public class MyServiceImpl implements MyService {
    ExternalService externalService;
    @Inject
    public MyServiceImpl(ExternalService externalService) {
    this.externalService=externalService;
    }

    @Override
    public ServiceCall<NotUsed, ExternalJson> invokeExternal() {
        return req->externalService.postStudent().invoke().thenApply(row->row);
        }
    }

