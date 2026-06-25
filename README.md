# Калькулятор с конвертером валют

Лабораторные работы №2 и №3 по дисциплине **«Технологии Программирования»**.

Десктопное приложение на **Java + JavaFX** с архитектурой **MVC**, объединяющее стандартный калькулятор и конвертер валют с актуальными курсами.

---

## Возможности

### Калькулятор
- Четыре арифметические операции: `+`, `-`, `×`, `÷`
- Ввод десятичных чисел, смена знака (`±`), процент (`%`)
- Обработка деления на ноль
- Сброс состояния (`C`)

### Конвертер валют
- Получение курсов в реальном времени через [exchangerate-api.com](https://www.exchangerate-api.com)
- 9 поддерживаемых валют: `USD`, `EUR`, `GBP`, `JPY`, `RUB`, `CNY`, `CHF`, `CAD`, `AUD`
- Быстрая смена направления конвертации (кнопка «Поменять»)
- История конвертаций с возможностью очистки
- Резервные курсы при отсутствии сети

---

## Структура проекта

```
Lab02-03_TP/
├── src/
│   ├── App.java                              # Точка входа, переключение режимов
│   ├── Controller/
│   │   ├── CalculatorController.java
│   │   └── CurrencyConverterController.java
│   ├── Model/
│   │   ├── CalculatorModel.java              # Логика вычислений
│   │   ├── CurrencyService.java              # Запросы к API, кэширование курсов
│   │   └── CurrencyRates.java                # Модель данных курсов
│   ├── View/
│   │   ├── CalculatorView.java
│   │   └── CurrencyConverterView.java
│   └── test/
│       ├── Controller/
│       │   ├── CalculatorControllerTest.java
│       │   └── ConverterControllerTest.java
│       └── Model/
│           ├── CalculatorModelTest.java      # 12 тестов
│           ├── CurrencyServiceTest.java      # 9 тестов
│           └── CurrencyRatesTest.java        # 13 тестов
└── lib/                                      # JavaFX 25.0.1
```

---

## Требования

| Компонент | Версия |
|-----------|--------|
| JDK       | 17+    |
| JavaFX    | 25.0.1 |
| Gson      | любая  |
| JUnit 5   | 5.x    |
| Mockito   | 5.x    |

---

## Сборка и запуск

### Компиляция

```bash
javac -cp "lib/*" \
  src/App.java \
  src/Controller/*.java \
  src/Model/*.java \
  src/View/*.java \
  -d out/
```

### Запуск

```bash
java --module-path lib/javafx \
     --add-modules javafx.controls \
     -cp "lib/*:out" App
```

> На Windows замените `:` на `;` в пути `-cp`.

---

## Тесты

Тесты расположены в `src/test/` и покрывают Model- и Controller-слои.

| Файл                          | Тестов | Что проверяется                          |
|-------------------------------|--------|------------------------------------------|
| `CalculatorModelTest`         | 12     | Арифметика, граничные случаи, ошибки     |
| `CurrencyRatesTest`           | 13     | Конвертация, точность, null-значения     |
| `CurrencyServiceTest`         | 9      | API, fallback-курсы, сетевые ошибки      |
| `CalculatorControllerTest`    | 5      | UI-события, отображение результата       |
| `ConverterControllerTest`     | 6      | Конвертация, история, валидация ввода    |

---

## Архитектура

Приложение строго следует паттерну **MVC**:

- **Model** — бизнес-логика и работа с данными (`CalculatorModel`, `CurrencyService`, `CurrencyRates`)
- **View** — построение интерфейса на JavaFX (`CalculatorView`, `CurrencyConverterView`)
- **Controller** — связывает View с Model, обрабатывает события

Переключение режимов реализовано в `App.switchMode()` — сцена и размер окна меняются без перезапуска приложения.

---

## Лицензия

MIT — подробности в файле [LICENSE](LICENSE).
