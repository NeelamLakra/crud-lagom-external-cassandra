package com.example.neelam.external.service.impl;

import com.example.neelam.external.service.api.ExternalService;
import com.example.neelam.external.service.api.MyService;
import com.google.inject.AbstractModule;
import com.lightbend.lagom.javadsl.server.ServiceGuiceSupport;

public class ExternalServiceModule extends AbstractModule implements ServiceGuiceSupport {
    @Override

    protected void configure() {bindService(MyService.class, MyServiceImpl.class);

    bindClient(ExternalService.class);}


}
