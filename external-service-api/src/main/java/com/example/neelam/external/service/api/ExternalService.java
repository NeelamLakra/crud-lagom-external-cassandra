package com.example.neelam.external.service.api;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;


import static com.lightbend.lagom.javadsl.api.Service.named;


public interface ExternalService extends Service {

    ServiceCall<NotUsed, ExternalJson> postStudent();


    @Override
    default Descriptor descriptor() {
        return named("external-service")
                .withCalls(
                        Service.restCall(Method.GET,"/posts/21",this::postStudent)
                ).withAutoAcl(true);
    }
}
