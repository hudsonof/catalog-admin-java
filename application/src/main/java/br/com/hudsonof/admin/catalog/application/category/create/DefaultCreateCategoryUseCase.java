package br.com.hudsonof.admin.catalog.application.category.create;

import br.com.hudsonof.admin.catalog.domain.category.Category;
import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import br.com.hudsonof.admin.catalog.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = Category.newCategory(aName, description, isActive);

        aCategory.validate(new ThrowsValidationHandler());

        return CreateCategoryOutput.from(this.categoryGateway.create(aCategory));
    }
}
