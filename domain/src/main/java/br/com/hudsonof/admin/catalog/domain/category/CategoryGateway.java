package br.com.hudsonof.admin.catalog.domain.category;

import br.com.hudsonof.admin.catalog.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    void deleteById(CategoryID anId);

    Optional<Category> findById(CategoryID anId);

    void update(Category aCategory);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
