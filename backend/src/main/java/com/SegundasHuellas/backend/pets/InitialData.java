package com.SegundasHuellas.backend.pets;

import com.SegundasHuellas.backend.pets.internal.application.service.BreedService;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Breed;
import com.SegundasHuellas.backend.pets.internal.domain.entity.Pet;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Gender;
import com.SegundasHuellas.backend.pets.internal.domain.enums.PetStatus;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Size;
import com.SegundasHuellas.backend.pets.internal.domain.enums.Species;
import com.SegundasHuellas.backend.pets.internal.domain.vo.Age;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.BreedRepository;
import com.SegundasHuellas.backend.pets.internal.infra.persistence.PetRepository;
import com.SegundasHuellas.backend.shared.domain.vo.Image;
import com.SegundasHuellas.backend.shared.infrastructure.logging.LoggerManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@Profile("!prod")
@RequiredArgsConstructor
@Slf4j
public class InitialData {


    private final PetRepository petRepository;
    private final BreedService breedService;
    private final BreedRepository breedRepository;
    private final Faker faker;
    private final LoggerManager loggerManager;
    private final List<String> catImages = loadUrlsFromFile("static/data/cat-urls.txt");
    private final List<String> dogImages = loadUrlsFromFile("static/data/dog-urls.txt");
    private final List<String> otherImages = loadUrlsFromFile("static/data/other-urls.txt");
    private final Map<Species, List<String>> IMAGE_POOLS = Map.of(
            Species.DOG, dogImages,
            Species.CAT, catImages,
            Species.OTHER, otherImages
    );
    private final List<String> randomDescriptions = loadResourceFromClasspath("static/data/pet-descriptions.txt");
    private final List<String> randomHealthStatuses = loadResourceFromClasspath("static/data/health-statuses.txt");
    @Value("${app.seeding.enabled:false}")
    private boolean seedingEnabled;
    @Value("${app.seeding.pets.count:1000}")
    private int numberOfFakePets;
    @Value("${app.seeding.batch-size:50}")
    private int batchSize;

    @Order(2)
    @EventListener
    @Transactional
    public void handleApplicationStartedEvent(ApplicationStartedEvent event) {

        if (!seedingEnabled) {
            log.info("🌱🚫 Seeding data is disabled");
            return;
        }

        try {
            log.info("🌱 Starting data seeding...");
            long start = System.currentTimeMillis();

            loggerManager.executeWithSuppressedSQLLogs(this::seedData);

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

        Map<Species, List<Breed>> breedsBySpecies = Arrays.stream(Species.values())
                                                          .collect(Collectors.toMap(
                                                                  species -> species,
                                                                  species -> {
                                                                      List<Breed> breeds = breedRepository.findAllBySpecies(species);
                                                                      breeds.add(defaultBreeds.get(species));
                                                                      return breeds;
                                                                  }
                                                          ));

        IntStream.range(0, numberOfFakePets)
                 .mapToObj(i -> createRandomPet(breedsBySpecies))
                 .collect(Collectors.groupingBy(pet -> Math.floorDiv(faker.number().randomNumber(), batchSize)))
                 .values()
                 .forEach(petRepository::saveAll);
    }


    private Pet createRandomPet(Map<Species, List<Breed>> breedsBySpecies) {
        Species randomSpecies = getWeightedRandomSpecies();
        Pet randomPet = Pet.withDefaults(faker.name().firstName(), randomSpecies);


        randomPet.assignBreed(breedsBySpecies
                .get(randomSpecies)
                .get(faker.number().numberBetween(0, breedsBySpecies.get(randomSpecies).size())));

        randomPet.setGender(faker.options().option(Gender.class));
        randomPet.setAge(Age.ofDays(faker.number().numberBetween(1, 5500)));
        randomPet.setIsCastrated(faker.options().option(true, false));
        randomPet.setHealthStatus(randomHealthStatuses.get(faker.number().numberBetween(0, randomHealthStatuses.size())));
        randomPet.setComments(randomDescriptions.get(faker.number().numberBetween(0, randomDescriptions.size())));
        randomPet.setStatus(faker.options().option(PetStatus.class));
        randomPet.setSize(faker.options().option(Size.class));

        int numberOfPhotos = 4;

        List<Image> randomPhotos = createRandomPhotos(randomSpecies, numberOfPhotos);
        randomPet.setPhotos(randomPhotos);
        randomPet.setMainPhoto(randomPhotos.isEmpty() ?
                Image.fromUrl("default-image-url") :
                randomPhotos.getFirst());

        return randomPet;
    }

    private List<Image> createRandomPhotos(Species species, int numberOfPhotos) {
        List<String> imagePool = new ArrayList<>(IMAGE_POOLS.get(species));
        if (imagePool.isEmpty()) {
            log.warn("No images available for species {}", species);
            return List.of(Image.fromUrl("default-image-url"));
        }

        List<Image> photos = new ArrayList<>();
        int maxAttempts = Math.min(numberOfPhotos, imagePool.size());

        for (int i = 0; i < maxAttempts; i++) {
            if (imagePool.isEmpty()) break;
            int randomIndex = faker.number().numberBetween(0, imagePool.size());
            String randomImage = imagePool.remove(randomIndex);
            photos.add(Image.fromUrl(randomImage));
        }

        return photos;
    }

    private void logExecutionTime(long start) {
        double executionTime = (System.currentTimeMillis() - start) / 1000.0;
        log.info("🎉🎉 Seeding data completed in {} seconds 🎉🎉", executionTime);
    }


    private List<String> loadResourceFromClasspath(String resourceName) {

        ClassPathResource resource = new ClassPathResource(resourceName);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return Arrays.stream(reader.lines()
                                       .collect(Collectors.joining())
                                       .split("&"))
                         .map(String::trim)
                         .filter(s -> !s.isEmpty())
                         .toList();


        } catch (IOException e) {
            throw new RuntimeException("Failed to load resource from classpath: " + resourceName, e);

        }
    }

    private List<String> loadUrlsFromFile(String resourcePath) {
        Resource resource = new ClassPathResource(resourcePath);
        try (InputStream inputStream = resource.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines()
                         .map(String::trim)
                         .filter(line -> !line.isEmpty())
                         .filter(line -> line.startsWith("http"))
                         .filter(line -> line.length() < 200)
                         .toList();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load urls from classpath: " + resourcePath, e);
        }
    }

    private Species getWeightedRandomSpecies() {
        int random = faker.number().numberBetween(1, 8);

        return switch (random) {
            case 1, 2, 3 -> Species.DOG;
            case 4, 5, 6 -> Species.CAT;
            case 7 -> Species.OTHER;
            default -> throw new IllegalStateException("Unexpected value: " + random);
        };
    }

}