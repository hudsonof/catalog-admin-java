package br.com.hudsonof.admin.catalog.infrastructure.category.persistence;

import br.com.hudsonof.admin.catalog.domain.category.Category;
import br.com.hudsonof.admin.catalog.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void givenAnInvalidNullName_whenCallsSave_shouldReturnError() {
        final var expectedException = "not-null property references a null or transient value : br.com.hudsonof.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity.name";
        final var expectedPropertyName = "name";

        final var aCategory = Category.newCategory("Filmes", "Categoria de filmes", true);

        final var aEntity = CategoryJpaEntity.from(aCategory);
        aEntity.setName(null);

        final var actualException =
                assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(aEntity));

        final var actualCause =
                assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedException, actualCause.getMessage());
    }

    @Test
    void givenAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {
        final var expectedException = "not-null property references a null or transient value : br.com.hudsonof.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity.createdAt";
        final var expectedPropertyName = "createdAt";

        final var aCategory = Category.newCategory("Filmes", "Categoria de filmes", true);

        final var aEntity = CategoryJpaEntity.from(aCategory);
        aEntity.setCreatedAt(null);

        final var actualException =
                assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(aEntity));

        final var actualCause =
                assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedException, actualCause.getMessage());
    }

    @Test
    void givenAnInvalidNullUpdatedAt_whenCallsSave_shouldReturnError() {
        final var expectedException = "not-null property references a null or transient value : br.com.hudsonof.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity.updatedAt";
        final var expectedPropertyName = "updatedAt";

        final var aCategory = Category.newCategory("Filmes", "Categoria de filmes", true);

        final var aEntity = CategoryJpaEntity.from(aCategory);
        aEntity.setUpdatedAt(null);

        final var actualException =
                assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(aEntity));

        final var actualCause =
                assertInstanceOf(PropertyValueException.class, actualException.getCause());

        assertEquals(expectedPropertyName, actualCause.getPropertyName());
        assertEquals(expectedException, actualCause.getMessage());
    }
}
