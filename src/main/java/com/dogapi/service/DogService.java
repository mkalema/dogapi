package com.dogapi.service;

import com.dogapi.model.Dog;
import com.dogapi.repository.DogRepository;
import com.dogapi.repository.DogSubTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogSubTypeRepository dogSubTypeRepository;

    public Dog addDog(Dog dog){
        return dogRepository.save(dog);
    }

    public Dog updateDog(Dog dog){
        return dogRepository.saveAndFlush(dog);
    }


    public List<Dog> getAllDogs(){
        return dogRepository.findAll();
    }
}
