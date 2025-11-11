package test.Model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Model.CalculatorModel;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorModelTest {
    
    private CalculatorModel model;
    
    @BeforeEach
    void setUp() {
        model = new CalculatorModel();
    }
    
    @Test
    void testInitialState() {
        assertEquals("0", model.getCurrentInput());
        assertTrue(model.isStartNewNumber());
        assertEquals("", model.getOperator());
    }
    
    @Test
    void testNumberInput() {
        model.inputNumber("5");
        assertEquals("5", model.getCurrentInput());
        assertFalse(model.isStartNewNumber());
        
        model.inputNumber("3");
        assertEquals("53", model.getCurrentInput());
    }
    
    @Test
    void testDecimalInput() {
        model.inputNumber("3");
        model.inputDecimal();
        model.inputNumber("14");
        assertEquals("3.14", model.getCurrentInput());
    }
    
    @Test
    void testAddition() {
        model.inputNumber("5");
        model.setOperator("+");
        model.inputNumber("3");
        model.calculate();
        
        assertEquals("8", model.getCurrentInput());
    }
    
    @Test
    void testSubtraction() {
        model.inputNumber("10");
        model.setOperator("-");
        model.inputNumber("4");
        model.calculate();
        
        assertEquals("6", model.getCurrentInput());
    }
    
    @Test
    void testMultiplication() {
        model.inputNumber("6");
        model.setOperator("×");
        model.inputNumber("7");
        model.calculate();
        
        assertEquals("42", model.getCurrentInput());
    }
    
    @Test
    void testDivision() {
        model.inputNumber("15");
        model.setOperator("÷");
        model.inputNumber("3");
        model.calculate();
        
        assertEquals("5", model.getCurrentInput());
    }
    
    @Test
    void testDivisionByZero() {
        model.inputNumber("5");
        model.setOperator("÷");
        model.inputNumber("0");
        model.calculate();
        
        assertEquals("Ошибка", model.getCurrentInput());
    }
    
    @Test
    void testClear() {
        model.inputNumber("123");
        model.setOperator("+");
        model.clear();
        
        assertEquals("0", model.getCurrentInput());
        assertTrue(model.isStartNewNumber());
        assertEquals("", model.getOperator());
    }
    
    @Test
    void testToggleSign() {
        model.inputNumber("5");
        model.toggleSign();
        assertEquals("-5", model.getCurrentInput());
        
        model.toggleSign();
        assertEquals("5", model.getCurrentInput());
    }
    
    @Test
    void testPercentage() {
        model.inputNumber("50");
        model.calculatePercentage();
        assertEquals("0.5", model.getCurrentInput());
    }
    
    @Test
    void testComplexCalculation() {
        // (5 + 3) × 2
        model.inputNumber("5");
        model.setOperator("+");
        model.inputNumber("3");
        model.calculate(); // 8
        
        model.setOperator("×");
        model.inputNumber("2");
        model.calculate();
        
        assertEquals("16", model.getCurrentInput());
    }
    
    @Test
    void testOperatorWithoutSecondNumber() {
        model.inputNumber("5");
        model.setOperator("+");
        // Не вводим второе число
        model.calculate();
        
        assertEquals("10", model.getCurrentInput());
    }
}