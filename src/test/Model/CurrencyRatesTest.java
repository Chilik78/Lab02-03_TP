package test.Model;

import Model.CurrencyRates;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование класса CurrencyRates")
public class CurrencyRatesTest {
    
    private CurrencyRates currencyRates;
    private Map<String, Double> testRates;
    
    @BeforeEach
    public void setUp() {
        currencyRates = new CurrencyRates();
        testRates = new HashMap<>();
        
        // Создаем тестовые курсы валют
        testRates.put("USD", 1.0);    // Базовая валюта
        testRates.put("EUR", 0.92);
        testRates.put("GBP", 0.79);
        testRates.put("JPY", 147.5);
        testRates.put("RUB", 92.5);
        
        currencyRates.setBase("USD");
        currencyRates.setRates(testRates);
        currencyRates.setDate("2024-01-01");
    }
    
    @Test
    @DisplayName("Тест геттеров и сеттеров")
    public void testGettersAndSetters() {
        assertEquals("USD", currencyRates.getBase());
        assertEquals("2024-01-01", currencyRates.getDate());
        assertEquals(testRates, currencyRates.getRates());
        assertEquals(5, currencyRates.getRates().size());
        
        // Проверка сеттеров
        currencyRates.setBase("EUR");
        currencyRates.setDate("2024-02-01");
        
        Map<String, Double> newRates = new HashMap<>();
        newRates.put("USD", 1.08);
        currencyRates.setRates(newRates);
        
        assertEquals("EUR", currencyRates.getBase());
        assertEquals("2024-02-01", currencyRates.getDate());
        assertEquals(newRates, currencyRates.getRates());
    }
    
    @Test
    @DisplayName("Тест конвертации из базовой валюты")
    public void testConvertFromBaseCurrency() {
        // Конвертация из USD (базовой) в EUR
        double result = currencyRates.convert("USD", "EUR", 100.0);
        double expected = 100.0 * 0.92; // 100 USD * 0.92
        assertEquals(expected, result, 0.001);
        
        // Конвертация из USD в JPY
        result = currencyRates.convert("USD", "JPY", 50.0);
        expected = 50.0 * 147.5;
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    @DisplayName("Тест конвертации в базовую валюту")
    public void testConvertToBaseCurrency() {
        // Конвертация из EUR в USD (базовую)
        double result = currencyRates.convert("EUR", "USD", 100.0);
        double expected = 100.0 / 0.92; // 100 EUR / 0.92
        assertEquals(expected, result, 0.001);
        
        // Конвертация из JPY в USD
        result = currencyRates.convert("JPY", "USD", 5000.0);
        expected = 5000.0 / 147.5;
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    @DisplayName("Тест конвертации между небазовыми валютами")
    public void testConvertBetweenNonBaseCurrencies() {
        // Конвертация EUR → GBP через USD
        // 100 EUR → USD: 100 / 0.92 ≈ 108.6957 USD
        // 108.6957 USD → GBP: 108.6957 * 0.79 ≈ 85.87 GBP
        double result = currencyRates.convert("EUR", "GBP", 100.0);
        double expected = (100.0 / 0.92) * 0.79;
        assertEquals(expected, result, 0.001);
        
        // Конвертация GBP → JPY
        result = currencyRates.convert("GBP", "JPY", 50.0);
        expected = (50.0 / 0.79) * 147.5;
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    @DisplayName("Тест конвертации с неизвестной валютой")
    public void testConvertWithUnknownCurrency() {
        // Конвертация с неизвестной валютой 'from'
        double result = currencyRates.convert("UNKNOWN", "EUR", 100.0);
        assertEquals(0.0, result, 0.001, "Должен вернуть 0 для неизвестной валюты");
        
        // Конвертация с неизвестной валютой 'to'
        result = currencyRates.convert("USD", "UNKNOWN", 100.0);
        assertEquals(0.0, result, 0.001, "Должен вернуть 0 для неизвестной валюты");
    }
    
    @Test
    @DisplayName("Тест конвертации с нулевым amount")
    public void testConvertWithZeroAmount() {
        double result = currencyRates.convert("USD", "EUR", 0.0);
        assertEquals(0.0, result, 0.001);
    }
    
    @Test
    @DisplayName("Тест конвертации с отрицательным amount")
    public void testConvertWithNegativeAmount() {
        double result = currencyRates.convert("USD", "EUR", -100.0);
        double expected = -100.0 * 0.92;
        assertEquals(expected, result, 0.001, "Должен корректно обрабатывать отрицательные значения");
    }
    
    @Test
    @DisplayName("Тест конвертации с нулевыми rates")
    public void testConvertWithNullRates() {
        CurrencyRates emptyRates = new CurrencyRates();
        emptyRates.setBase("USD");
        // rates не установлены (null)
        
        double result = emptyRates.convert("USD", "EUR", 100.0);
        assertEquals(0.0, result, 0.001, "Должен вернуть 0 если rates == null");
    }
    
    @Test
    @DisplayName("Тест конвертации с одинаковыми валютами")
    public void testConvertSameCurrency() {
        double result = currencyRates.convert("USD", "USD", 100.0);
        assertEquals(100.0, result, 0.001, "Конвертация в ту же валюту должна возвращать исходную сумму");
        
        result = currencyRates.convert("EUR", "EUR", 150.0);
        double expected = (150.0 / 0.92) * 0.92; // EUR → USD → EUR
        assertEquals(expected, result, 0.001);
    }
    
    @Test
    @DisplayName("Тест с пустым Map rates")
    public void testConvertWithEmptyRates() {
        CurrencyRates emptyRates = new CurrencyRates();
        emptyRates.setBase("USD");
        emptyRates.setRates(new HashMap<>());
        
        double result = emptyRates.convert("USD", "EUR", 100.0);
        assertEquals(0.0, result, 0.001, "Должен вернуть 0 если rates пустой");
    }
    
    @Test
    @DisplayName("Тест точности вычислений")
    public void testCalculationPrecision() {
        // Проверяем точность вычислений для разных величин
        double[] testAmounts = {0.01, 1.0, 1000.0, 1000000.0};
        
        for (double amount : testAmounts) {
            double result = currencyRates.convert("USD", "EUR", amount);
            double expected = amount * 0.92;
            assertEquals(expected, result, 0.000001, 
                String.format("Погрешность для суммы %.2f должна быть минимальной", amount));
        }
    }
}