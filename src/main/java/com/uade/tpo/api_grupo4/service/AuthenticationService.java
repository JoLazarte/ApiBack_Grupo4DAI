package com.uade.tpo.api_grupo4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.uade.tpo.api_grupo4.controllers.auth.AuthenticationRequest;
import com.uade.tpo.api_grupo4.controllers.auth.AuthenticationResponse;
import com.uade.tpo.api_grupo4.controllers.auth.RegisterRequest;
import com.uade.tpo.api_grupo4.controllers.config.JwtService;
import com.uade.tpo.api_grupo4.entity.User;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.repository.UserRepository;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
        @Autowired
        private final UserRepository userRepository;
        @Autowired
        private final JwtService jwtService;
        @Autowired
        private final AuthenticationManager authenticationManager;
        @Autowired
        private UserService userService;
      

        public AuthenticationResponse registerUser(RegisterRequest request) throws Exception {
                try{
                        User user = userService.createUser(request);
                        String jwtToken = jwtService.generateToken(user);
                        return AuthenticationResponse.builder()
                                .accessToken(jwtToken)
                                .build();
                }catch(UserException error){
                        throw new UserException(error.getMessage());
                }catch (Exception error){
                        throw new Exception("[AuthenticationService.register] -> " + error.getMessage());
                }
        }
        
        public AuthenticationResponse authenticateUser(AuthenticationRequest request) throws Exception {
                try{
                        authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                        request.getUsername(),
                                        request.getPassword()));
                User user = userRepository.findByUsername(request.getUsername())
                        .orElseThrow(() -> new UserException("El usuario " + request.getUsername() + " no existe."));
                
                String jwtToken = jwtService.generateToken(user);
                return AuthenticationResponse.builder()
                        .accessToken(jwtToken)
                        .build();
                } catch (AuthenticationException error){
                        System.out.printf("[AuthenticationService.authenticate] -> %s", error.getMessage());
                        throw new AuthException("Usuario o contraseña incorrecto.");
                }catch (UserException error) {
                        throw new UserException(error.getMessage());
                }catch (Exception error) {
                        throw new Exception("[AuthenticationService.authenticate] -> " + error.getMessage());
                }
        }
      
}
