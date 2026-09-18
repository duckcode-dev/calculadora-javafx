package com.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CalculatorFxmlTest {

    @BeforeAll
    static void startJavaFxToolkit() throws InterruptedException {
        CountDownLatch startup = new CountDownLatch(1);

        try {
            Platform.startup(startup::countDown);
        } catch (IllegalStateException ignored) {
            startup.countDown();
        }

        assertTrue(startup.await(10, TimeUnit.SECONDS), "JavaFX no se inició a tiempo");
        Platform.setImplicitExit(false);
    }

    @Test
    void loadsFxmlAndConnectsButtonsToController() throws Exception {
        runOnJavaFxThread(() -> {
            URL fxmlLocation = getClass().getResource("/calculator.fxml");
            assertNotNull(fxmlLocation);

            Parent root = new FXMLLoader(fxmlLocation).load();
            TextField display = findTextField(root);

            assertFalse(display.isFocusTraversable());
            assertEquals(display.getLength(), display.getCaretPosition());

            findButton(root, "2").fire();
            findButton(root, "+").fire();
            findButton(root, "3").fire();
            findButton(root, "=").fire();

            assertEquals("5", display.getText());

            findButton(root, "C").fire();

            assertEquals("0", display.getText());
        });
    }

    private TextField findTextField(Parent root) {
        return root.getChildrenUnmodifiable().stream()
                .filter(TextField.class::isInstance)
                .map(TextField.class::cast)
                .findFirst()
                .orElseThrow();
    }

    private Button findButton(Parent root, String text) {
        return root.getChildrenUnmodifiable().stream()
                .filter(Button.class::isInstance)
                .map(Button.class::cast)
                .filter(button -> button.getText().equals(text))
                .findFirst()
                .orElseThrow();
    }

    private void runOnJavaFxThread(ThrowingRunnable action) throws Exception {
        CountDownLatch completion = new CountDownLatch(1);
        AtomicReference<Throwable> failure = new AtomicReference<>();

        Platform.runLater(() -> {
            try {
                action.run();
            } catch (Throwable exception) {
                failure.set(exception);
            } finally {
                completion.countDown();
            }
        });

        assertTrue(completion.await(10, TimeUnit.SECONDS), "La acción de JavaFX no terminó a tiempo");
        if (failure.get() != null) {
            throw new AssertionError("Falló la prueba de integración JavaFX", failure.get());
        }
    }

    @FunctionalInterface
    private interface ThrowingRunnable {
        void run() throws Exception;
    }
}
