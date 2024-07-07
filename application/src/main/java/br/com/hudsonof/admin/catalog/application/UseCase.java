package br.com.hudsonof.admin.catalog.application;

import br.com.hudsonof.admin.catalog.domain.category.Category;

public class UseCase {

    public Category execute() {
        return Category.newCategory("Category Name", "Category Description", true);
    }
}