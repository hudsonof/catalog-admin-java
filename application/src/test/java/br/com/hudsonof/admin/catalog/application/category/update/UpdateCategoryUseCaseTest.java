package br.com.hudsonof.admin.catalog.application.category.update;

import br.com.hudsonof.admin.catalog.domain.category.Category;
import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import br.com.hudsonof.admin.catalog.domain.category.CategoryID;
import br.com.hudsonof.admin.catalog.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenShouldReturnCategoryId() throws InterruptedException {
        final var aCategory = Category.newCategory("Film", "All films", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        Thread.sleep(10);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(argThat(aUpdatedCategory ->
                Objects.equals(expectedName, aUpdatedCategory.getName())
                && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                && Objects.equals(expectedId, aUpdatedCategory.getId())
                && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                && isNull(aUpdatedCategory.getDeletedAt())));
    }

    @Test
    public void givenAInvalidName_whenCallsUpdateCategory_thenShouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", "All films", true);

        final String expectedName = null;
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());

        verify(categoryGateway, never()).update(any());
    }

    @Test
    public void givenAValidInactivateCommand_whenCallsUpdateCategory_thenShouldReturnInactiveCategoryId() {
        final var aCategory = Category.newCategory("Film", "All films", true);

        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeletedAt());

        final var expectedName = "Filmes";
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = false;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any())).thenAnswer(returnsFirstArg());

        assertTrue(aCategory.isActive());
        assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).update(argThat(aUpdatedCategory ->
                Objects.equals(expectedName, aUpdatedCategory.getName())
                        && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                        && Objects.equals(expectedId, aUpdatedCategory.getId())
                        && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                        && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                        && nonNull(aUpdatedCategory.getDeletedAt())));
    }

    @Test
    public void givenAValidCommand_whenGatewayThrowsRandomException_thenShouldReturnAException() {
        final var aCategory = Category.newCategory("Film", "All films", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = true;
        final var expectedId = aCategory.getId();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway Error";

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive);

        when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(aCategory.clone()));

        when(categoryGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        assertEquals(expectedErrorCount, notification.getErrors().size());
        assertEquals(expectedErrorMessage, notification.getErrors().getFirst().message());

        verify(categoryGateway, times(1)).update(argThat(aUpdatedCategory ->
                Objects.equals(expectedName, aUpdatedCategory.getName())
                        && Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
                        && Objects.equals(expectedId, aUpdatedCategory.getId())
                        && Objects.equals(aCategory.getCreatedAt(), aUpdatedCategory.getCreatedAt())
                        && aCategory.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
                        && isNull(aUpdatedCategory.getDeletedAt())));
    }

    @Test
    public void givenACommandWithInvalidId_whenCallsUpdateCategory_thenShouldReturnNotFoundException() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with ID: %s, was not found".formatted(expectedId);
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(CategoryID.from(expectedId)))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand).get());

        assertEquals(expectedErrorCount, actualException.getErrors().size());
        assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

        verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));

        verify(categoryGateway, never()).update(any());
    }
}
