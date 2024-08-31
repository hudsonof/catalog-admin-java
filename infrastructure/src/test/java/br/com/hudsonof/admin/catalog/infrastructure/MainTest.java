package br.com.hudsonof.admin.catalog.infrastructure;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MainTest {

    @Test
    public void testMain() {
        System.setProperty("spring.profiles.active", "test");

        Assertions.assertNotNull(new Main());
        Main.main(new String[] {});
    }
}
