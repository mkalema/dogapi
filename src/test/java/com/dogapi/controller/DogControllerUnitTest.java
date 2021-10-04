package com.dogapi.controller;

import com.dogapi.model.Dog;
import com.dogapi.model.DogSubType;
import com.dogapi.service.DogService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(DogController.class)
public class DogControllerUnitTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DogService dogService;

    @InjectMocks
    DogController dogController;

    @Autowired
    ResourceLoader resourceLoader;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test that validates the data returned by API")
    public void testGetAllData_shouldpass() throws Exception {

        // read input data
        List<Dog> dogsInputData = this.getDogsData();
        // read test data
        String testData = getDogTestData();

        // mock dog service
        Mockito.when(dogService.getAllDogs()).thenReturn(dogsInputData);

        // call endpoint
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/dogs")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("success")))
                .andReturn();
        String resultData = result.getResponse().getContentAsString();

        Assertions.assertEquals(testData, resultData);

    }

    private List<Dog> getDogsData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:data/apidata.json");
        JsonNode jsonNode = objectMapper.readTree(resource.getInputStream());

        Iterator<Map.Entry<String, JsonNode>> dataMap = jsonNode.fields();

        List<Dog> dogs = new ArrayList<>();

        while (dataMap.hasNext()) {
            Map.Entry<String, JsonNode> entry = dataMap.next();

            String dogName = entry.getKey();
            Dog dog = new Dog();
            dog.setName(dogName);
            // get dog sub types

            List<DogSubType> subTypes = new ArrayList<>();
            for (JsonNode node : entry.getValue()) {
                DogSubType dogSubType = new DogSubType();
                dogSubType.setName(node.asText());
                subTypes.add(dogSubType);
            }
            dog.setDogSubTypes(subTypes);
            dogs.add(dog);

        }

        return dogs;
    }

    private String getDogTestData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:testdata/dogs.json");
        JsonNode jsonNode = objectMapper.readTree(resource.getInputStream());
        return jsonNode.toString();
    }
}
