package com.SegundasHuellas.backend.users.internal.application.service.impl;

import com.SegundasHuellas.backend.users.internal.application.service.UserService;
import com.SegundasHuellas.backend.users.internal.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepository.findFirstByEmail(username).or(() -> userRepository.findFirstByUser(username)).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            }
        };
    }




    public String sugerirNombreDeUsuario(String nombre, String apellido) {
        Random random = new Random();

        int longitudNombre = random.nextInt(3) + 3;
        int longitudApellido = random.nextInt(3) + 3;

        String primeraParte = nombre.substring(0, Math.min(longitudNombre, nombre.length())).toLowerCase();
        String segundaParte = apellido.substring(0, Math.min(longitudApellido, apellido.length())).toLowerCase();


        String baseUsuario;
        if (random.nextBoolean()) {
            baseUsuario = primeraParte + segundaParte;
        } else {
            baseUsuario = segundaParte + primeraParte;
        }

        int longitudNumero = random.nextInt(4) + 3;
        StringBuilder numeroAleatorio = new StringBuilder();
        for (int i = 0; i < longitudNumero; i++) {
            int numero = random.nextInt(10);
            numeroAleatorio.append(numero);
        }

        String sugerencia = baseUsuario + numeroAleatorio.toString();

        int contador = 1;
        while (userRepository.existsByUser(sugerencia)) {
            sugerencia = baseUsuario + contador;
            contador++;
        }

        return sugerencia;
    }


    public String generarContraseñaSegura() {
        String LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
        String UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String DIGITS = "0123456789";
        String SPECIAL_CHARACTERS = "!@#$%*_\".";
        String ALL_CHARACTERS = LOWERCASE + UPPERCASE + DIGITS + SPECIAL_CHARACTERS;

        SecureRandom random = new SecureRandom();

        int longitudContraseña = 8 + random.nextInt(8);

        StringBuilder contraseña = new StringBuilder();
        contraseña.append(LOWERCASE.charAt(random.nextInt(LOWERCASE.length())));
        contraseña.append(UPPERCASE.charAt(random.nextInt(UPPERCASE.length())));
        contraseña.append(DIGITS.charAt(random.nextInt(DIGITS.length())));
        contraseña.append(SPECIAL_CHARACTERS.charAt(random.nextInt(SPECIAL_CHARACTERS.length())));

        for (int i = contraseña.length(); i < longitudContraseña; i++) {
            contraseña.append(ALL_CHARACTERS.charAt(random.nextInt(ALL_CHARACTERS.length())));
        }

        char[] contraseniaArray = contraseña.toString().toCharArray();
        for (int i = 0; i < contraseniaArray.length; i++) {
            int j = random.nextInt(contraseniaArray.length);
            char temp = contraseniaArray[i];
            contraseniaArray[i] = contraseniaArray[j];
            contraseniaArray[j] = temp;
        }

        return new String(contraseniaArray);
    }



}
