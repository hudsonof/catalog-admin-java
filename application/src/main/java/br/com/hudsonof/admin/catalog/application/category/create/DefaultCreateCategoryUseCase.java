package br.com.hudsonof.admin.catalog.application.category.create;

import br.com.hudsonof.admin.catalog.domain.category.Category;
import br.com.hudsonof.admin.catalog.domain.category.CategoryGateway;
import br.com.hudsonof.admin.catalog.domain.validation.handler.Notification;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var description = aCommand.description();
        final var isActive = aCommand.isActive();

        final var notification = Notification.create();

        final var aCategory = Category.newCategory(aName, description, isActive);

        aCategory.validate(notification);

        return notification.hasErrors() ? Left(notification) : create(aCategory);
    }

    private Either<Notification, CreateCategoryOutput> create(Category aCategory) {
        return Try(() -> this.categoryGateway.create(aCategory))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
