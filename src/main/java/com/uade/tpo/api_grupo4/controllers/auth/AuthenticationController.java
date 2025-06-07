package com.uade.tpo.api_grupo4.controllers.auth;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.api_grupo4.entity.ResponseData;
import com.uade.tpo.api_grupo4.exceptions.UserException;
import com.uade.tpo.api_grupo4.service.AuthenticationService;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseData<?>> register(
        @RequestBody RegisterRequest request) {
        try {
            AuthenticationResponse authResponse = authService.register(request);
            if(authResponse.getAccessToken() != null) return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(authResponse));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseData.error("No se pudo registrar el usuario"));

        } catch (UserException error) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));

            } catch (Exception error) {
            System.out.printf("[AuthenticationController.register] -> %s", error.getMessage() );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("No se pudo registrar el usuario"));
        }

    }

    @PostMapping("/authenticateUser")
    public ResponseEntity<ResponseData<?>> authenticate(
        @RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(ResponseData.success(authService.authenticate(request)));

            }catch (UserException | AuthException error) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseData.error(error.getMessage()));
        }catch (Exception error) {
            System.out.printf("[AuthenticationController.authenticate] -> %s", error.getMessage() );
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseData.error("Usuario o contraseña invalido."));
        }

    }
   
}