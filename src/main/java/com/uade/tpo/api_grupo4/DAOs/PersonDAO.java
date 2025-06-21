package com.uade.tpo.api_grupo4.DAOs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.uade.tpo.api_grupo4.entity.Person;
import com.uade.tpo.api_grupo4.repository.PersonRepository;

public class PersonDAO {
 
    @Autowired
    PersonRepository personRepository;

    public List<Person> findAll(){
        return personRepository.findAll();
    }

    public Optional<Person> findById(Long personId) {
        return  personRepository.findById(personId);
    }

    public void deleteById(Long personId) {
        personRepository.deleteById(personId);
    }

    public void save(Person personaModelo) {
        personRepository.save(personaModelo);
    }

    public Person update (Person personaModelo){
        return  personRepository.save(personaModelo);
    }
}