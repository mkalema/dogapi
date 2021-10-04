package com.dogapi.controller;

import com.dogapi.model.Dog;
import com.dogapi.model.DogSubType;
import com.dogapi.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/dogs")
public class DogController {

    @Autowired
    private DogService dogService;

    @GetMapping
    public Map<String, Object> getAllDogs(){
        List<Dog> dogs = dogService.getAllDogs();
        Map<String, Object> map = new TreeMap<>();
        for (Dog dog: dogs){
            List<DogSubType> subTypes = dog.getDogSubTypes();

            List<String> subs = subTypes.stream().map(s -> s.getName()).collect(Collectors.toList());
            map.put(dog.getName(), subs);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", map);
        response.put("status", "success");
        return response;
    }


}
