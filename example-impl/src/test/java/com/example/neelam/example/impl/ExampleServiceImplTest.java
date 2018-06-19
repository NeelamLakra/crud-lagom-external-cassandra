package com.example.neelam.example.impl;
import com.datastax.driver.core.Session;
import com.example.neelam.example.api.Example;
import com.example.neelam.example.api.ExampleService;
import com.lightbend.lagom.javadsl.persistence.cassandra.CassandraSession;
import com.lightbend.lagom.javadsl.testkit.ServiceTest;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.defaultSetup;
import static com.lightbend.lagom.javadsl.testkit.ServiceTest.startServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;



public class ExampleServiceImplTest {
    private static ServiceTest.TestServer server;
   
    @BeforeClass
    public static void setUp() throws Exception {
        final ServiceTest.Setup setup = defaultSetup();
    
        server = startServer(setup.withCassandra(true));
    
        CassandraSession cassandraSession = server.injector().instanceOf(CassandraSession.class);
    
        Session session = cassandraSession.underlying().toCompletableFuture().get();
        
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
    
    @AfterClass
    public static void tearDown() {
        if (server != null) {
            server.stop();
            server = null;
        }
    }
    
    @Test
    public void postStudent() throws Exception{
        ExampleService exampleService = server.client(ExampleService.class);
        Example newList = exampleService.postStudent()
                .invoke(Example.builder().sid(1).name("neel").rollno(12).build())
                .toCompletableFuture().get(5,TimeUnit.SECONDS);
        System.out.println(newList);
        assertEquals("neel",newList.getName());
        
    }
    
    @Test
    public void getStudent() throws Exception{
        ExampleService exampleService = server.client(ExampleService.class);
        List<Example> newList =exampleService.getStudent(1).invoke().toCompletableFuture().get(5,TimeUnit.SECONDS);
        assertEquals("neel",newList.get(0).getName());
    }
    
    @Test
    public void removeStudent() throws Exception{
        ExampleService exampleService = server.client(ExampleService.class);
        String message=exampleService.removeStudent(1).invoke().toCompletableFuture().get(5,TimeUnit.SECONDS);
        assertEquals("record deleted",message);
    }
    
    


    
    
}
