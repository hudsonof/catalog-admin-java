package br.com.hudsonof.admin.catalog.domain.category;

import br.com.hudsonof.admin.catalog.domain.exception.DomainException;
import br.com.hudsonof.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParam_whenCallNewCategory_thenInstantiateACategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAnInvalidNullName_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = null;
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorsCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidEmptyName_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = "   ";
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorsCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidNameLengthLessThan3_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = "Fi ";
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorsCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAnInvalidNameLengthMoreThan255_whenCallNewCategory_thenShouldReceiveError() {
        final String expectedName = """
                Por conseguinte, o fenômeno da Internet aponta para a melhoria dos relacionamentos verticais entre as hierarquias.
                Assim mesmo, o início da atividade geral de formação de atitudes ainda não demonstrou convincentemente
                que vai participar na mudança dos métodos utilizados na avaliação de resultados. O incentivo ao avanço tecnológico,
                assim como o julgamento imparcial das eventualidades deve passar por modificações independentemente das formas de ação.
                Não obstante, a complexidade dos estudos efetuados faz parte de um processo de gerenciamento dos paradigmas corporativos.
                Por outro lado, a mobilidade dos capitais internacionais facilita a criação das condições inegavelmente apropriadas.
                """;
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorsCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());
    }

    @Test
    public void givenAValidEmptyDescription_whenCallNewCategory_thenShouldReceiveError() {
        final var expectedName = "Filmes";
        final var expectedDescription = "   ";
        final var expectedActive = true;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidFalseIsActive_whenCallNewCategory_thenShouldReceiveError() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = false;

        final var actualCategory =
                Category.newCategory(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidActiveCategory_whenCallDeactivate_thenReturnCategoryInactivated() throws InterruptedException {
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = false;

        final var oldCategory =
                Category.newCategory(expectedName, expectedDescription, true);

        final var updatedAt = oldCategory.getUpdatedAt();
        final var createdAt = oldCategory.getCreatedAt();

        Assertions.assertTrue(oldCategory.isActive());
        Assertions.assertNull(oldCategory.getDeletedAt());

        Thread.sleep(20);

        final var actualCategory = oldCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(oldCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var oldCategory =
                Category.newCategory(expectedName, expectedDescription, false);

        final var updatedAt = oldCategory.getUpdatedAt();
        final var createdAt = oldCategory.getCreatedAt();

        Assertions.assertFalse(oldCategory.isActive());
        Assertions.assertNotNull(oldCategory.getDeletedAt());

        final var actualCategory = oldCategory.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(actualCategory);
        Assertions.assertEquals(oldCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdate_thenReturnCategoryUpdated() throws InterruptedException {
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var oldCategory =
                Category.newCategory("Fil", "Filmes de todos os gêneros e séries", expectedActive);

        Assertions.assertDoesNotThrow(() -> oldCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = oldCategory.getUpdatedAt();
        final var createdAt = oldCategory.getCreatedAt();

        Thread.sleep(20);

        final var actualCategory = oldCategory.update(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(oldCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertEquals(expectedActive, actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateToInactive_thenReturnCategoryUpdated() throws InterruptedException {
        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = false;

        final var oldCategory =
                Category.newCategory("Filmes", "Filmes de todos os gêneros e séries", true);

        Assertions.assertDoesNotThrow(() -> oldCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertTrue(oldCategory.isActive());
        Assertions.assertNull(oldCategory.getDeletedAt());

        final var updatedAt = oldCategory.getUpdatedAt();
        final var createdAt = oldCategory.getCreatedAt();

        Thread.sleep(20);

        final var actualCategory = oldCategory.update(expectedName, expectedDescription, expectedActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(oldCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void givenAValidCategory_whenCallUpdateWithInvalidParams_thenReturnCategoryUpdated() throws InterruptedException {
        final String expectedName = null;
        final var expectedDescription = "Filmes de todos os gêneros";
        final var expectedActive = true;

        final var oldCategory =
                Category.newCategory("Filmes", "Filmes de todos os gêneros e séries", expectedActive);

        Assertions.assertDoesNotThrow(() -> oldCategory.validate(new ThrowsValidationHandler()));

        final var updatedAt = oldCategory.getUpdatedAt();
        final var createdAt = oldCategory.getCreatedAt();

        Thread.sleep(20);

        final var actualCategory = oldCategory.update(expectedName, expectedDescription, expectedActive);

        Assertions.assertEquals(oldCategory.getId(), actualCategory.getId());
        Assertions.assertEquals(expectedName, actualCategory.getName());
        Assertions.assertEquals(expectedDescription, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }
}
