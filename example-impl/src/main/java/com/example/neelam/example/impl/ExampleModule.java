package com.example.neelam.example.impl;

import com.example.neelam.example.api.ExampleService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ExampleModule extends AbstractModule implements ServiceGuiceSupport {
    @Override

    protected void configure() {bindService(ExampleService.class,ExampleServiceImpl.class);}
}
