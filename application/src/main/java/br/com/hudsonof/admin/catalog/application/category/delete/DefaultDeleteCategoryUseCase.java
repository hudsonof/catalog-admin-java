package br.com.hudsonof.admin.catalog.application.category.delete;

import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import br.com.hudsonof.admin.catalog.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultDeleteCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public void execute(final String aId) {
        this.categoryGateway.deleteById(CategoryID.from(aId));
    }
}
