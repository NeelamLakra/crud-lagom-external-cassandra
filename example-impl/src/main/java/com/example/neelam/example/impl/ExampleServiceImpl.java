package com.example.neelam.example.impl;

import akka.NotUsed;
import com.example.neelam.example.api.Example;
import com.example.neelam.example.api.ExampleService;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;


public class ExampleServiceImpl implements ExampleService {
    private final CassandraSession session;


    @Inject
    public ExampleServiceImpl(CassandraSession session){

        this.session=session;
    }

    @Override
    public ServiceCall<Example,Example> postStudent() {
        return req-> session.executeWrite("insert into user.user_detail(s_id, name, roll_no) Values(?,?,?)", req.getSid(),req.getName(),req.getRollno())
    .thenApply(result-> req);
    }

    @Override
    public ServiceCall<NotUsed, List<Example>> getStudent(int id) {

        return req-> session.selectOne("select * from user.user_detail where s_id =?",id)
                .thenApply(row -> {
                    return Arrays.asList(Example.builder()
                            .name(row.get().getString("name"))
                            .build());
                });
    }

    @Override
    public ServiceCall<NotUsed, String> removeStudent(int id) {

        return req->session.selectOne("delete from user.user_detail where s_id=?",id)
                .thenApply(result->"record deleted");

                }
    }
