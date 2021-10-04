package com.dogapi;

import com.dogapi.model.Dog;
import com.dogapi.model.DogSubType;
import com.dogapi.service.DogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class DogapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DogapiApplication.class, args);
    }

    // create bean to load initial data from json file
    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    DogService dogService;

    @Bean
    public CommandLineRunner run(){
        return args -> {
            ObjectMapper objectMapper = new ObjectMapper();
            Resource resource = resourceLoader.getResource("classpath:data/apidata.json");
            JsonNode jsonNode = objectMapper.readTree(resource.getInputStream());

            Iterator<Map.Entry<String, JsonNode>> dataMap = jsonNode.fields();

            List<Dog> dogs = new ArrayList<>();

            while (dataMap.hasNext()){
                Map.Entry<String, JsonNode> entry =  dataMap.next();

                String dogName = entry.getKey();
                Dog dog = new Dog();
                dog.setName(dogName);
                // get dog sub types

                List<DogSubType> subTypes = new ArrayList<>();
                for(JsonNode node: entry.getValue()){
                    DogSubType dogSubType = new DogSubType();
                    dogSubType.setName(node.asText());
                    subTypes.add(dogSubType);
                }
                dog.setDogSubTypes(subTypes);
                dogs.add(dog);

                // save to database
                dogService.addDog(dog);
            }
        };
    }
}
