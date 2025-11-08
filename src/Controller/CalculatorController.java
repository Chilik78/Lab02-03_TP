package Controller;
import javafx.scene.control.Button;
import Model.CalculatorModel;
import View.CalculatorView;

public class CalculatorController {
    private CalculatorModel model;
    private CalculatorView view;

    public CalculatorController(CalculatorModel model, CalculatorView view) {
        this.model = model;
        this.view = view;

        setupEventHandlers();
        updateView();
    }

    private void setupEventHandlers() {
        Button[] numberButtons = view.getNumberButtons();
        for (int i = 0; i < numberButtons.length; i++) {
            final int number = i;
            numberButtons[i].setOnAction(e -> handleNumberInput(String.valueOf(number)));
        }

        view.getClearButton().setOnAction(e -> handleClear());
        view.getSignButton().setOnAction(e -> handleSignToggle());
        view.getPercentButton().setOnAction(e -> handlePercentage());
        view.getDecimalButton().setOnAction(e -> handleDecimal());
        view.getEqualsButton().setOnAction(e -> handleEquals());
        view.getDivideButton().setOnAction(e -> handleDivide());
        view.getMultiplyButton().setOnAction(e -> handleMultiply());
        view.getSubstractButton().setOnAction(e -> handleSubstract());
        view.getAddButton().setOnAction(e -> handleAdd());
    }

    private void handleNumberInput(String number) {
        model.inputNumber(number);
        updateView();
    }

    private void handleDecimal() {
        model.inputDecimal();
        updateView();
    }

    public void handleOperator(String operator) {
        model.setOperator(operator);
        updateView();
    }

    private void handleEquals() {
        model.calculate();
        updateView();
    }

    private void handleClear() {
        model.clear();
        updateView();
    }

    private void handleSignToggle() {
        model.toggleSign();
        updateView();
    }

    private void handlePercentage() {
        model.calculatePercentage();
        updateView();
    }

    private void handleDivide() {
        model.setOperator("÷");
        updateView();
    }

    private void handleMultiply() {
        model.setOperator("×");
        updateView();
    }

    private void handleSubstract() {
        model.setOperator("-");
        updateView();
    }

    private void handleAdd() {
        model.setOperator("+");
        updateView();
    }

    private void updateView() {
        view.updateDisplay(model.getCurrentInput());
    }
}