package br.com.hudsonof.admin.catalog.application.category.create;

import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @Test
    public void givenAValidCommand_whenCallsCreateCategory_thenShouldReturnCategoryId() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Todos os filmes";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = mock(CategoryGateway.class);
        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand);

        assertNotNull(actualOutput);
        assertNotNull(actualOutput.id());

        verify(categoryGateway, times(1)).create(argThat(aCategory ->
                Objects.equals(expectedName, aCategory.getName())
                && Objects.equals(expectedDescription, aCategory.getDescription())
                && Objects.equals(expectedIsActive, aCategory.isActive())
                && nonNull(aCategory.getId())
                && nonNull(aCategory.getCreatedAt())
                && nonNull(aCategory.getUpdatedAt())
                && isNull(aCategory.getDeletedAt())));
    }
}
