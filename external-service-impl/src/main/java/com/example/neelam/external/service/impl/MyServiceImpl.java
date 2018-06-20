package com.example.neelam.external.service.impl;

import akka.NotUsed;
import com.example.neelam.external.service.api.ExternalJson;
import com.example.neelam.external.service.api.ExternalService;
import com.example.neelam.external.service.api.MyService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;

public class MyServiceImpl implements MyService {
    private final ExternalService externalService;
    private final CassandraSession session;
    @Inject
    public MyServiceImpl(ExternalService externalService, CassandraSession session) {
    
        this.externalService=externalService;
        this.session=session;
    }

    @Override
    public ServiceCall<NotUsed, ExternalJson> invokeExternal() {
        return req->externalService.postStudent().invoke().thenApply(row->row);
        }
        
        @Override
    public ServiceCall<NotUsed, String> postExternalData(){
        ExternalJson externalJson = externalService.postStudent().invoke().thenApply(row -> row).toCompletableFuture().join();
        return req->session.executeWrite("insert into user.external_json(userId,id,title,body) values(?,?,?,?)",externalJson.getId(),
                                        externalJson.getUserId(),externalJson.getTitle(),externalJson.getBody()).thenApply(done ->"inserted");
        
        }
    }

