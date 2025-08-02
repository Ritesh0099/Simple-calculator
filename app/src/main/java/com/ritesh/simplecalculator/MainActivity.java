package com.ritesh.simplecalculator;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class






MainActivity extends AppCompatActivity {

    EditText number1, number2;
    Button addButton, subtractButton, multiplyButton, divideButton, resetButton;
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Views
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        resultText = findViewById(R.id.resultText);

        addButton = findViewById(R.id.addButton);
        subtractButton = findViewById(R.id.subtractButton);
        multiplyButton = findViewById(R.id.multiplyButton);
        divideButton = findViewById(R.id.divideButton);
        resetButton = findViewById(R.id.resetButton); // New

        // Operation Listeners
        addButton.setOnClickListener(view -> calculate('+', view));
        subtractButton.setOnClickListener(view -> calculate('-', view));
        multiplyButton.setOnClickListener(view -> calculate('*', view));
        divideButton.setOnClickListener(view -> calculate('/', view));

        // Reset Button Listener
        resetButton.setOnClickListener(view -> {
            number1.setText("");
            number2.setText("");
            resultText.setText("Result will appear here");
            hideKeyboard(view);
            Snackbar.make(view, "Fields cleared", Snackbar.LENGTH_SHORT).show();
        });
    }

    private void calculate(char operator, View view) {
        String input1 = number1.getText().toString();
        String input2 = number2.getText().toString();

        if (input1.isEmpty() || input2.isEmpty()) {
            Snackbar.make(view, "Please enter both numbers", Snackbar.LENGTH_SHORT).show();
            return;
        }

        try {
            double num1 = Double.parseDouble(input1);
            double num2 = Double.parseDouble(input2);
            double result;

            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    if (num2 == 0) {
                        resultText.setText("Cannot divide by zero");
                        return;
                    } else {
                        result = num1 / num2;
                    }
                    break;
                default:
                    resultText.setText("Unknown operator");
                    return;
            }

            // Animate Result
            resultText.setAlpha(0f);
            resultText.setText("Result: " + result);
            resultText.animate().alpha(1f).setDuration(400).start();
            hideKeyboard(view);

        } catch (NumberFormatException e) {
            Snackbar.make(view, "Invalid number input", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
