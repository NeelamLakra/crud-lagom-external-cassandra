package com.example.neelam.example.api;

import akka.NotUsed;
import com.lightbend.lagom.javadsl.api.Descriptor;
import com.lightbend.lagom.javadsl.api.Service;
import static com.lightbend.lagom.javadsl.api.Service.named;
import com.lightbend.lagom.javadsl.api.ServiceCall;
import com.lightbend.lagom.javadsl.api.transport.Method;

import java.util.List;

public interface ExampleService extends Service {

    ServiceCall<Example, Example> postStudent();
    ServiceCall<NotUsed,List<Example>> getStudent(int id);
    ServiceCall<NotUsed,String> removeStudent(int id);


    @Override
   default Descriptor descriptor() {
        return named("example")
                .withCalls(
                        Service.restCall(Method.POST,"/api/addExample",this::postStudent),
                        Service.restCall(Method.GET,"/api/getExample/:id",this::getStudent),
                        Service.restCall(Method.DELETE,"/api/removeExample/:id",this::removeStudent)

                ).withAutoAcl(true);
    }
}
