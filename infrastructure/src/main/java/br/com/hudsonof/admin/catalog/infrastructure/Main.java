package br.com.hudsonof.admin.catalog.infrastructure;

import br.com.hudsonof.admin.catalog.application.UseCase;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        System.out.println(new UseCase().execute());
    }
}