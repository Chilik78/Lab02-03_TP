package test.Controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.collections.ObservableList;

import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;

import Controller.CurrencyConverterController;
import Model.CalculatorModel;
import View.CurrencyConverterView;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ConverterControllerTest extends Application {

    private CurrencyConverterView view;
    private CalculatorModel model;
    private CurrencyConverterController controller;
    private static CountDownLatch testLatch = new CountDownLatch(1);
    private static boolean testsPassed = true;

    @Override
    public void start(Stage primaryStage) {
        try {
            Platform.runLater(() -> {
                try {
                    view = new CurrencyConverterView();
                    model = new CalculatorModel();
                    controller = new CurrencyConverterController(model, view);

                    Scene scene = new Scene(view.getRoot(), 450, 700);
                    primaryStage.setScene(scene);
                    primaryStage.setTitle("Тест конвертера валют");
                    primaryStage.show();

                    // Ждем отрисовки UI
                    Thread.sleep(500);
                    testConverterController();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                    testsPassed = false;
                    testLatch.countDown();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void testConverterController() {
        try {
            System.out.println("\n=== ТЕСТИРОВАНИЕ КОНВЕРТЕРА ВАЛЮТ ===\n");
            
            // Тест 1: Проверка инициализации
            System.out.println("Тест 1: Проверка инициализации компонентов...");
            testInitialization();
            System.out.println("✓ Инициализация прошла успешно");
            
            // Тест 2: Обмен валют
            System.out.println("\nТест 2: Проверка обмена валют...");
            testCurrencySwap();
            System.out.println("✓ Обмен валют работает корректно");
            
            // Тест 3: Конвертация валют
            System.out.println("\nТест 3: Проверка конвертации валют...");
            testCurrencyConversion();
            System.out.println("✓ Конвертация работает корректно");
            
            // Тест 4: Неверный ввод
            System.out.println("\nТест 4: Проверка обработки неверного ввода...");
            testInvalidInput();
            System.out.println("✓ Обработка ошибок работает корректно");
            
            // Тест 5: История конвертаций
            System.out.println("\nТест 5: Проверка истории конвертаций...");
            testConversionHistory();
            System.out.println("✓ История конвертаций работает корректно");
            
            System.out.println("\n========================================");
            System.out.println("✓ ВСЕ ТЕСТЫ ПРОЙДЕНЫ УСПЕШНО!");
            Platform.exit();
        } catch (Exception err) {
            System.out.println("✗ Тесты не были пройдены: " + err.getMessage());
            err.printStackTrace();
            testsPassed = false;
            Platform.exit();
        }
    }

    private void testInitialization() {
        // Проверка наличия компонентов
        assertNotNull(view.getFromCurrencyCombo(), "ComboBox 'Из' не инициализирован");
        assertNotNull(view.getToCurrencyCombo(), "ComboBox 'В' не инициализирован");
        assertNotNull(view.getAmountField(), "Поле суммы не инициализировано");
        assertNotNull(view.getResultField(), "Поле результата не инициализировано");
        assertNotNull(view.getConvertButton(), "Кнопка конвертации не инициализирована");
        assertNotNull(view.getSwapButton(), "Кнопка обмена не инициализирована");
        assertNotNull(view.getHistoryList(), "История не инициализирована");
        assertNotNull(view.getClearHistoryButton(), "Кнопка очистки не инициализирована");
        
        // Проверка начальных значений
        assertEquals("USD", view.getFromCurrencyCombo().getValue(), "Начальная валюта 'Из' должна быть USD");
        assertEquals("EUR", view.getToCurrencyCombo().getValue(), "Начальная валюта 'В' должна быть EUR");
        assertEquals("1.00", view.getAmountField().getText(), "Начальная сумма должна быть 1.00");
        assertTrue(view.getResultField().getText().isEmpty(), "Поле результата должно быть пустым при инициализации");
        
        // Проверка доступных валют
        ObservableList<String> currencies = view.getFromCurrencyCombo().getItems();
        assertTrue(currencies.contains("USD"), "Должна быть доступна валюта USD");
        assertTrue(currencies.contains("EUR"), "Должна быть доступна валюта EUR");
        assertTrue(currencies.contains("GBP"), "Должна быть доступна валюта GBP");
        assertTrue(currencies.contains("JPY"), "Должна быть доступна валюта JPY");
    }

    private void testCurrencySwap() {
        // Запоминаем начальные значения
        String initialFrom = view.getFromCurrencyCombo().getValue();
        String initialTo = view.getToCurrencyCombo().getValue();
        
        // Выполняем обмен валют
        view.getSwapButton().fire();
        
        // Проверяем, что значения поменялись местами
        assertEquals(initialTo, view.getFromCurrencyCombo().getValue(), 
            "Валюта 'Из' должна поменяться на предыдущую 'В'");
        assertEquals(initialFrom, view.getToCurrencyCombo().getValue(), 
            "Валюта 'В' должна поменяться на предыдущую 'Из'");
        
        // Еще раз меняем - должно вернуться к исходным значениям
        view.getSwapButton().fire();
        assertEquals(initialFrom, view.getFromCurrencyCombo().getValue(), 
            "После повторного обмена должна вернуться исходная валюта 'Из'");
        assertEquals(initialTo, view.getToCurrencyCombo().getValue(), 
            "После повторного обмена должна вернуться исходная валюта 'В'");
    }

    private void testCurrencyConversion() {
        Platform.runLater(() -> {// Устанавливаем тестовые значения
            view.getFromCurrencyCombo().setValue("USD");
            view.getToCurrencyCombo().setValue("EUR");
            view.getAmountField().setText("100");
            
            // Выполняем конвертацию
            view.getConvertButton().fire();
            
            // Даем время на обработку
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            
            // Проверяем результат
            String result = view.getResultField().getText();
            assertNotNull(result, "Результат не должен быть null");
            assertFalse(result.isEmpty(), "Результат не должен быть пустым");
            assertTrue(result.contains("USD"), "Результат должен содержать USD");
            assertTrue(result.contains("EUR"), "Результат должен содержать EUR");
            assertTrue(result.contains("100,00"), "Результат должен содержать сумму 100.00");
            
            // Проверяем формат результата
            assertTrue(result.matches(".*\\d+\\,\\d{2}\\s+USD\\s*=\\s*\\d+\\,\\d{2}\\s+EUR.*"), 
                "Неправильный формат результата");
            
            // Проверяем, что история обновилась
            ObservableList<String> history = view.getHistoryList().getItems();
            assertFalse(history.isEmpty(), "История не должна быть пустой после конвертации");
            
            String lastHistoryEntry = history.get(history.size() - 1);
            assertTrue(lastHistoryEntry.contains("100,00 USD"), 
                "Последняя запись в истории должна содержать '100,00 USD'");
            assertTrue(lastHistoryEntry.contains("EUR"), 
                "Последняя запись в истории должна содержать 'EUR'");
        });
    }

    private void testInvalidInput() {
        // Тест 1: Пустой ввод
        view.getAmountField().setText("");
        view.getConvertButton().fire();
        String result1 = view.getResultField().getText();
        assertTrue(result1.contains("Ошибка") || result1.contains("Error") || result1.isEmpty(),
            "При пустом вводе должна быть ошибка или пустой результат");
        
        // Тест 2: Нечисловой ввод
        view.getAmountField().setText("abc");
        view.getConvertButton().fire();
        String result2 = view.getResultField().getText();
        assertTrue(result2.contains("Ошибка") || result2.contains("Error"),
            "При нечисловом вводе должна быть ошибка");
        
        // Тест 3: Отрицательное число (должно работать)
        view.getAmountField().setText("-50");
        view.getConvertButton().fire();
        String result3 = view.getResultField().getText();
        assertTrue(result3.contains("-50,00"), "Отрицательные числа должны обрабатываться");
        
        // Восстанавливаем валидное значение
        view.getAmountField().setText("1.00");
    }

    private void testConversionHistory() {
        // Очищаем историю перед тестом
        view.getClearHistoryButton().fire();
        
        // Проверяем, что история пуста
        ObservableList<String> initialHistory = view.getHistoryList().getItems();
        assertTrue(initialHistory.isEmpty(), "История должна быть пустой после очистки");
        
        // Выполняем несколько конвертаций
        int testOperations = 3;
        for (int i = 1; i <= testOperations; i++) {
            view.getAmountField().setText(String.valueOf(i * 50));
            view.getConvertButton().fire();
            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }
        
        // Проверяем количество записей в истории
        ObservableList<String> history = view.getHistoryList().getItems();
        assertEquals(testOperations, history.size(), 
            "В истории должно быть " + testOperations + " записей");
        
        // Проверяем содержание записей
        for (int i = 0; i < testOperations; i++) {
            String entry = history.get(i);
            double expectedAmount = (i + 1) * 50.0;
            String expectedAmountStr = String.format("%.2f", expectedAmount);
            assertTrue(entry.contains(expectedAmountStr + " USD"), 
                "Запись " + (i+1) + " должна содержать сумму " + expectedAmountStr);
        }
        
        // Очищаем историю
        view.getClearHistoryButton().fire();
        
        // Проверяем, что история снова пуста
        ObservableList<String> clearedHistory = view.getHistoryList().getItems();
        assertTrue(clearedHistory.isEmpty(), "История должна быть пустой после очистки");
        
        // Восстанавливаем значение
        view.getAmountField().setText("1.00");
    }
}