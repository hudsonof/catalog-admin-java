package br.com.hudsonof.admin.catalog.application.category.retrieve.get;

import br.com.hudsonof.admin.catalog.domain.category.Category;
import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import br.com.hudsonof.admin.catalog.domain.category.CategoryID;
import br.com.hudsonof.admin.catalog.domain.exception.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryByIdUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    void givenAValidId_whenCallsGetCategory_thenShouldReturnCategory() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);
        final var expectedId = aCategory.getId();

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory));

        final var actualCategory = useCase.execute(expectedId.getValue());

        assertEquals(CategoryOutput.from(aCategory), actualCategory);
        assertEquals(expectedName, actualCategory.name());
        assertEquals(expectedDescription, actualCategory.description());
        assertEquals(expectedIsActive, actualCategory.isActive());
        assertEquals(aCategory.getId(), actualCategory.id());
        assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
        assertEquals(aCategory.getDeletedAt(), actualCategory.deletedAt());
    }

    @Test
    void givenAnInvalidId_whenCallsGetCategory_thenShouldReturnNotFound() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Category with ID: 123, was not found";

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var expectedException =
                assertThrows(DomainException.class, () -> useCase.execute(expectedId.getValue()));

        assertEquals(expectedErrorCount, expectedException.getErrors().size());
        assertEquals(expectedErrorMessage, expectedException.getMessage());

    }

    @Test
    void givenAValidId_whenGatewayThrowsException_thenReturnException() {
        final var expectedId = CategoryID.from("123");
        final var expectedErrorMessage = "Gateway error";

        when(categoryGateway.findById(eq(expectedId))).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var expectedException =
                assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        assertEquals(expectedErrorMessage, expectedException.getMessage());
    }
}
