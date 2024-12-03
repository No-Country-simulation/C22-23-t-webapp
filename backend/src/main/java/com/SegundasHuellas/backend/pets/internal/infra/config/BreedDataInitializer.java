package com.SegundasHuellas.backend.pets.internal.infra.config;

import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class BreedDataInitializer {

    private final BreedRepository breedRepository;
    private final BreedService breedService;

    @EventListener
    @Transactional
    @Order(1)
    public void handleApplicationStartedEvent(ApplicationStartedEvent event) {
        if (breedRepository.count() > 0) {
            return;
        }

        try {
            log.info("🐱🐶 Loading initial breeds...");
            long start = System.currentTimeMillis();
            loadInitialBreeds();
            logExecutionTime(start);
        } catch (Exception e) {
            String msg = "❌ Error loading initial breeds: ";
            log.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }

    private void logExecutionTime(long start) {
        double executionTime = (System.currentTimeMillis() - start) / 1000.0;
        log.info("🐱🐶 Initial breeds loaded in {} seconds 🐱🐶", executionTime);
    }

    private void loadInitialBreeds() {
        List<Breed> breeds = new ArrayList<>();

        //Dogs
        breeds.addAll(List.of(
                Breed.builder().name("Mestizo").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Labrador Retriever").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Pastor Alemán").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Bulldog Francés").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Yorkshire Terrier").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Golden Retriever").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Chihuahua").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Poodle").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Schnauzer Miniatura").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Rottweiler").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Beagle").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Boxer").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Husky Siberiano").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Pug").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Dálmata").species(Species.DOG).isSpeciesDefault(false).build(),
                Breed.builder().name("Cocker Spaniel").species(Species.DOG).isSpeciesDefault(false).build()
        ));

        // Cats
        breeds.addAll(List.of(
                Breed.builder().name("Mestizo").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Común Doméstico").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Siamés").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Persa").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Maine Coon").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Bengalí").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Angora Turco").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Ragdoll").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Británico de Pelo Corto").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Sphynx").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Azul Ruso").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Común Europeo").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Himalayo").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Exótico de Pelo Corto").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Birmano").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Munchkin").species(Species.CAT).isSpeciesDefault(false).build(),
                Breed.builder().name("Bosque de Noruega").species(Species.CAT).isSpeciesDefault(false).build()
        ));

        breedService.createManyBreeds(breeds);
    }
}


