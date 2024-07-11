package br.com.hudsonof.admin.catalog.application.category.update;

import br.com.hudsonof.admin.catalog.domain.category.Category;
import br.com.hudsonof.admin.catalog.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
