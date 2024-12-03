package com.SegundasHuellas.backend.users.internal.application.service.impl;

import com.SegundasHuellas.backend.users.api.dto.SignUpRequest;
import com.SegundasHuellas.backend.users.api.dto.UserDTO;
import com.SegundasHuellas.backend.users.internal.application.service.AuthService;
import com.SegundasHuellas.backend.users.internal.domain.entity.User;
import com.SegundasHuellas.backend.users.internal.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    private final UserRepository userRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public UserDTO createUser(SignUpRequest signUpRequest) {


        if (userRepository.findByDni(signUpRequest.getDni()).isPresent()) {
            throw new RuntimeException("El DNI '" + signUpRequest.getDni() + "' ya está registrado.");
        }

        if (userRepository.findByUser(signUpRequest.getUser()).isPresent()) {
            throw new RuntimeException("El nombre de usuario '" + signUpRequest.getUser() + "' ya está registrado.");
        }

        if (userRepository.findByEmail(signUpRequest.getEmail()).isPresent()) {
            throw new RuntimeException("El correo electrónico '" + signUpRequest.getEmail() + "' ya está registrado.");
        }

        String nombreDeUsuario = signUpRequest.getUser();
        if (nombreDeUsuario == null || nombreDeUsuario.isEmpty()) {
            nombreDeUsuario = userServiceImpl.sugerirNombreDeUsuario(signUpRequest.getNombre(), signUpRequest.getApellido());
        }

        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setUser(nombreDeUsuario);
        user.setNombre(signUpRequest.getNombre());
        user.setApellido(signUpRequest.getApellido());
        user.setDni(signUpRequest.getDni());
        user.setPassword(new BCryptPasswordEncoder().encode(signUpRequest.getPassword()));
        User createdUser = userRepository.save(user);

        return createdUser.getUserDto();
    }
}
