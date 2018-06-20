package com.example.neelam.external.service.api;
import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;


import static com.lightbend.lagom.javadsl.api.Service.named;

public interface MyService extends Service {
    ServiceCall<NotUsed, ExternalJson> invokeExternal();
    ServiceCall<NotUsed,String> postExternalData();
    @Override
    default Descriptor descriptor() {
        return named("my-service")
                .withCalls(
                        Service.restCall(Method.GET,"/api/invokeExternal",this::invokeExternal),
                        Service.restCall(Method.POST,"/api/postExternalJson",this::postExternalData)

                ).withAutoAcl(true);
    }
}
