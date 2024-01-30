package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;
    private static GenericContainer<?> firstCont = new GenericContainer<>("devapp").
            withExposedPorts(8080);
    private static GenericContainer<?> secondCont = new GenericContainer<>("devapp").
            withExposedPorts(8081);


    @BeforeAll
    public static void setUp() {
        firstCont.start();
        secondCont.start();

    }

    @Test
    void contextLoads1() {
        String prof = "Current profile is dev";
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" +
                firstCont.getMappedPort(8080), String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals(prof, forEntity.getBody());
    }
    @Test
    void contextLoads2() {
        String prof = "Current profile is production";
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://localhost:" +
                firstCont.getMappedPort(8081), String.class);
        System.out.println(forEntity.getBody());
        Assertions.assertEquals(prof, forEntity.getBody());
    }


}