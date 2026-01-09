package Model;

import com.google.gson.Gson;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class CurrencyService {
    private static final String API_URL = "https://api.exchangerate-api.com/v4/latest/";
    //private static final String FIXER_API = "https://api.exchangeratesapi.io/v1/latest?access_key=YOUR_API_KEY"; // альтернативный API
    private Map<String, CurrencyRates> cachedRates = new HashMap<>();
    private Gson gson = new Gson();

    public CurrencyRates getCurrencyRates(String baseCurrency) throws Exception {
        // Проверяем кэш (актуальность в течение 1 часа)
        if (cachedRates.containsKey(baseCurrency)) {
            // В реальном приложении здесь проверка времени кэширования
            return cachedRates.get(baseCurrency);
        }

        // Используем бесплатный API (лимит 1500 запросов в месяц)
        URL url = new URL(API_URL + baseCurrency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        CurrencyRates rates = gson.fromJson(response.toString(), CurrencyRates.class);
        cachedRates.put(baseCurrency, rates);
        
        return rates;
    }

    // Метод для работы без API (тестовые данные)
    public CurrencyRates getDefaultRates() {
        CurrencyRates rates = new CurrencyRates();
        rates.setBase("USD");
        rates.setDate("2026-01-01");
        
        Map<String, Double> testRates = new HashMap<>();
        testRates.put("EUR", 0.92);
        testRates.put("GBP", 0.79);
        testRates.put("JPY", 147.5);
        testRates.put("RUB", 92.5);
        testRates.put("CNY", 7.2);
        
        rates.setRates(testRates);
        return rates;
    }

    public double convertCurrency(String from, String to, double amount) {
        try {
            CurrencyRates rates = getCurrencyRates(from);
            return rates.convert(from, to, amount);
        } catch (Exception e) {
            System.err.println("Ошибка получения курсов: " + e.getMessage());
            // Используем тестовые данные при ошибке сети
            CurrencyRates rates = getDefaultRates();
            return rates.convert(from, to, amount);
        }
    }
}