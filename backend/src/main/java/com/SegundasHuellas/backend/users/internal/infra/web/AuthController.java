package com.SegundasHuellas.backend.users.internal.infra.web;

import com.SegundasHuellas.backend.users.api.dto.AuthenticationRequest;
import com.SegundasHuellas.backend.users.api.dto.AuthenticationResponse;
import com.SegundasHuellas.backend.users.api.dto.SignUpRequest;
import com.SegundasHuellas.backend.users.api.dto.UserDTO;
import com.SegundasHuellas.backend.users.internal.application.service.AuthService;
import com.SegundasHuellas.backend.users.internal.application.service.UserService;
import com.SegundasHuellas.backend.users.internal.application.service.impl.UserServiceImpl;
import com.SegundasHuellas.backend.users.internal.domain.entity.User;
import com.SegundasHuellas.backend.users.internal.infra.persistence.UserRepository;
import com.SegundasHuellas.backend.users.internal.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final UserServiceImpl userServiceImpl;

    @PostMapping("/signup")
    public ResponseEntity<?> signUpUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            UserDTO createdUser = authService.createUser(signUpRequest);
            return new ResponseEntity<>(createdUser, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e) {
            return new ResponseEntity<>("Ocurrió un error", HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping("/login")
    public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
        }catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userService.userDetailsService().loadUserByUsername(authenticationRequest.getEmail());
        Optional<User> optionalUser = userRepository.findByEmail(authenticationRequest.getEmail()).or(()-> userRepository.findByUser(authenticationRequest.getEmail()));
        final String jwt = jwtUtil.generateToken(userDetails);

        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        if (optionalUser.isPresent()) {
            authenticationResponse.setJwt(jwt);
            authenticationResponse.setUserId(optionalUser.get().getId());
        }
        return authenticationResponse;
    }


    @GetMapping("/sugerir-nombre-usuario")
    public String sugerirNombreUsuario(@RequestParam String nombre, @RequestParam String apellido) {
        return userServiceImpl.sugerirNombreDeUsuario(nombre, apellido);
    }

    @GetMapping("/sugerir-contraseña")
    public String generarContraseñaSegura() {
        return userServiceImpl.generarContraseñaSegura();
    }

}
