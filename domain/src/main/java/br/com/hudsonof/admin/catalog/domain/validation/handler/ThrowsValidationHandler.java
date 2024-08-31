package br.com.hudsonof.admin.catalog.domain.validation.handler;

import br.com.hudsonof.admin.catalog.domain.exception.DomainException;
import br.com.hudsonof.admin.catalog.domain.validation.Error;
import br.com.hudsonof.admin.catalog.domain.validation.ValidationHandler;

import java.util.List;

public class ThrowsValidationHandler implements ValidationHandler {
    @Override
    public ValidationHandler append(final Error anError) {
        throw DomainException.with(anError);
    }

    @Override
    public ValidationHandler append(final ValidationHandler anHandler) {
        throw DomainException.with(anHandler.getErrors());
    }

    @Override
    public ValidationHandler validate(Validation aValidation) {
        try {
            aValidation.validate();
        } catch (final DomainException e) {
            throw DomainException.with(List.of(new Error(e.getMessage())));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return List.of();
    }
}
