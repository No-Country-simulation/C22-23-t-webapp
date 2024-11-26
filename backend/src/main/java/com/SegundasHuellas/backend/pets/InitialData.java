package com.SegundasHuellas.backend.pets;

import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("dev")
@RequiredArgsConstructor
@Slf4j
public class InitialData {

    public static final int NUMBER_OF_FAKE_PETS = 1000;
    public static final int BATCH_SIZE = 50;

    private static final List<String> DOG_IMAGES = List.of(
            "https://picsum.photos/id/237/300/300",
            "https://picsum.photos/id/1025/300/300",
            "https://picsum.photos/id/169/300/300",
            "https://picsum.photos/id/1062/300/300"
    );
    private static final List<String> CAT_IMAGES = List.of(
            "https://placecats.com/neo/300/200",
            "https://placecats.com/millie/300/150",
            "https://placecats.com/millie_neo/300/200",
            "https://placecats.com/neo_banana/300/200",
            "https://placecats.com/neo_2/300/200",
            "https://placecats.com/bella/300/200",
            "https://placecats.com/poppy/300/200",
            "https://placecats.com/louie/300/200",
            "https://placecats.com/g/300/200"
    );
    private static final List<String> OTHER_IMAGES = List.of(
            "https://picsum.photos/id/433/300/300",
            "https://picsum.photos/id/582/300/300",
            "https://picsum.photos/id/659/300/300",
            "https://picsum.photos/id/783/300/300",
            "https://picsum.photos/id/593/300/300"
    );
    private static final Map<Species, List<String>> IMAGE_POOLS = Map.of(
            Species.DOG, DOG_IMAGES,
            Species.CAT, CAT_IMAGES,
            Species.OTHER, OTHER_IMAGES
    );
    private final PetRepository petRepository;
    private final BreedService breedService;
    private final Faker faker;


    @EventListener
    @Transactional
    public void handleApplicationStartedEvent(ApplicationStartedEvent event) {
        try {
            log.info("🌱 Starting data seeding...");
            long start = System.currentTimeMillis();
            seedData();
            logExecutionTime(start);
        } catch (Exception e) {
            String errorMsg = "❌ Error loading seeding data: ";
            log.error(errorMsg, e);
            throw new RuntimeException(errorMsg, e);
        }

    }


    private void seedData() {
        if (petRepository.count() > 0) {
            return;
        }

        Map<Species, Breed> defaultBreeds = Arrays.stream(Species.values())
                                                  .collect(Collectors.toMap(
                                                          species -> species,
                                                          breedService::getDefaultBreedForSpecies
                                                  ));


        IntStream.range(0, NUMBER_OF_FAKE_PETS)
                 .mapToObj(i -> createRandomPet(defaultBreeds))
                 .collect(Collectors.groupingBy(pet -> Math.floorDiv(faker.number().randomNumber(), BATCH_SIZE)))
                 .values()
                 .forEach(petRepository::saveAll);
    }


    private Pet createRandomPet(Map<Species, Breed> defaultBreeds) {
        Species randomSpecies = faker.options().option(Species.class);
        Pet randomPet = Pet.withDefaults(faker.name().firstName(), randomSpecies);
        randomPet.assignBreed(defaultBreeds.get(randomSpecies));
        randomPet.setGender(faker.options().option(Gender.class));
        randomPet.setAge(Age.ofDays(faker.number().numberBetween(1, 5500)));
        randomPet.setIsCastrated(faker.options().option(true, false));
        randomPet.setHealthStatus(faker.lorem().sentence());
        randomPet.setComments(faker.lorem().sentence(40));

        List<String> imagePool = IMAGE_POOLS.get(randomSpecies);
        String randomImage = imagePool.get(faker.number().numberBetween(0, imagePool.size()));
        randomPet.setPhoto(Image.fromUrl(randomImage));

        return randomPet;
    }

    private void logExecutionTime(long start) {
        double executionTime = (System.currentTimeMillis() - start) / 1000.0;
        log.info("🎉🎉 Seeding data completed in {} seconds 🎉🎉", executionTime);
    }


}
