package com.example.neelam.external.service.impl;

import com.datastax.driver.core.Session;
import com.example.neelam.external.service.api.ExternalJson;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.startServer;
import static org.junit.Assert.assertEquals;

public class MyServiceImplTest {
    private static ExternalMockedServiceImpl externalMockedService = new ExternalMockedServiceImpl();
    private static MyServiceImpl myService;
    private static ServiceTest.TestServer server;
    
    
    @BeforeClass
    public static void initializer() throws Exception {
        final ServiceTest.Setup setup = defaultSetup();
    
        server = startServer(setup.withCassandra(true));
    
        CassandraSession cassandraSession = server.injector().instanceOf(CassandraSession.class);
    
        Session session = cassandraSession.underlying().toCompletableFuture().get();
    
        myService = new MyServiceImpl(externalMockedService,cassandraSession);
    
        createSchema(session);
    
        populateSchema(session);
    
    }
    
    public static void createSchema(Session session){
        session.execute("CREATE KEYSPACE user WITH replication = {'class': 'SimpleStrategy', 'replication_factor':1};");
        session.execute("CREATE TABLE user.user_detail(s_id int PRIMARY KEY, name text,roll_no int);");
        
    }
    
    public static void populateSchema(Session session){
        session.execute("insert into user.user_detail(s_id,name,roll_no) values(1,'neel',21);");
        session.execute("insert into user.user_detail(s_id,name,roll_no) values(2,'kittu',22);");
        
    }
    
    
    @Test
    public void invokeExternal() throws Exception {
        ExternalJson external = myService.invokeExternal().invoke()
                .toCompletableFuture()
                .get(5, TimeUnit.SECONDS);
        
        
        assertEquals(ExternalJson.builder()
                .userId(1)
                .id(1)
                .body("DEF").title("ABC").build(),external);
    }
}
