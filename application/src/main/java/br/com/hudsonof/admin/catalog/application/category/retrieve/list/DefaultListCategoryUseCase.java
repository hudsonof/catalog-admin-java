package br.com.hudsonof.admin.catalog.application.category.retrieve.list;

import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import br.com.hudsonof.admin.catalog.domain.category.CategorySearchQuery;
import br.com.hudsonof.admin.catalog.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoryUseCase extends ListCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultListCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Pagination<CategoryListOutput> execute(final CategorySearchQuery aQuery) {
        return this.categoryGateway.findAll(aQuery)
                .map(CategoryListOutput::from);
    }
}
