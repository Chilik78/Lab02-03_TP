package Model;

import java.util.Arrays;
import java.util.Map;

public class CurrencyRates {
    private static final String[] CURRENCIES = {
        "USD", "EUR", "GBP", "JPY", "RUB", "CNY", "CHF", "CAD", "AUD"
    };
    private String base;
    private Map<String, Double> rates;
    private String date;

    public CurrencyRates() {}

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Double> getRates() {
        return rates;
    }

    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double convert(String from, String to, double amount) {
        if (rates == null) return 0.0;
        if (!Arrays.asList(CURRENCIES).contains(from) || !Arrays.asList(CURRENCIES).contains(to) ) return 0.0;

        try {
            if (from.equals(base)) {
                return amount * rates.getOrDefault(to, 0.0);
            } else if (to.equals(base)) {
                return amount / rates.getOrDefault(from, 0.0);
            } else {
                double toBase = 1 / rates.getOrDefault(from, 0.0);
                return amount * toBase * rates.getOrDefault(to, 0.0);
            }
        } catch (Error error) {
            return 0.0;
        }
    }
}