package br.com.hudsonof.admin.catalog.application.category.retrieve.list;

import br.com.hudsonof.admin.catalog.application.UseCase;
import br.com.hudsonof.admin.catalog.domain.category.CategorySearchQuery;
import br.com.hudsonof.admin.catalog.domain.pagination.Pagination;

public abstract class ListCategoryUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
