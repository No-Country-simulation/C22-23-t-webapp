package com.SegundasHuellas.backend;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

class BackendApplicationTests {

    ApplicationModules modules = ApplicationModules.of(BackendApplication.class);

    @Test
    void encapsulated_and_withoutCycles() {
        modules.forEach(System.out::println);
        modules.verify();
    }

    @Test
    void generateDiagrams() {

        new Documenter(modules)
                .writeModulesAsPlantUml()
                .writeIndividualModulesAsPlantUml();
    }

    @Test
    void generateAsciidoc() {
        var canvasOptions = Documenter.CanvasOptions.defaults();

        var docOptions = Documenter.DiagramOptions.defaults()
                                                  .withStyle(Documenter.DiagramOptions.DiagramStyle.UML);

        new Documenter(modules)
                .writeDocumentation(docOptions, canvasOptions);
    }
}
