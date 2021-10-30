package ar.ed.um.programacion2;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.ed.um.programacion2");

        noClasses()
            .that()
            .resideInAnyPackage("ar.ed.um.programacion2.service..")
            .or()
            .resideInAnyPackage("ar.ed.um.programacion2.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ar.ed.um.programacion2.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
