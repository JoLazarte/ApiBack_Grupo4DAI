package com.uade.tpo.api_grupo4.DAOs;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;

import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.repository.UserRepository;


public class UserDAO {

    private static UserDAO instancia;
    @Autowired
    private UserRepository userRepository;
    
    private UserDAO() {}

    public static UserDAO getInstancia() {
        if (instancia == null) {
            instancia = new UserDAO();
        }
        return instancia;
    }
    public UserDAO(UserRepository usuarioRepositorio) {
        this.userRepository = usuarioRepositorio;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
    public void save(User usuario) {
        userRepository.save(usuario);
    }

    public User update(User usuarioModelo) {
        return userRepository.save(usuarioModelo);
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }


}