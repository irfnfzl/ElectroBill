package com.example.electrobill;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    TextView result;

    EditText et1, et2;

    Button btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        // Initialize views
        result = findViewById(R.id.result);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        // Calculate button click listener
        btnCalculate.setOnClickListener(v -> {
            // Get user inputs
            String kWhInput = et1.getText().toString();
            String rebateInput = et2.getText().toString();

            if (kWhInput.isEmpty() || rebateInput.isEmpty()) {
                result.setText("Please enter valid inputs.");
                return;
            }

            double kWh = Double.parseDouble(kWhInput);
            double rebatePercent = Double.parseDouble(rebateInput);

            if (rebatePercent < 0 || rebatePercent > 5) {
                result.setText("Rebate percentage must be between 0% and 5%.");
                return;
            }

            // Calculate total charges
            double totalCharges = calculateCharges(kWh);

            // Apply rebate
            double finalAmount = totalCharges - (totalCharges * rebatePercent / 100);

            // Display result
            result.setText(String.format("Total Charges: RM %.2f\nFinal Amount after Rebate: RM %.2f", totalCharges, finalAmount));
        });

        // Clear button click listener
        btnClear.setOnClickListener(v -> {
            et1.setText("");
            et2.setText("");
            result.setText("");
        });
    }

    private double calculateCharges(double kWh) {
        double charges = 0.0;

        if (kWh <= 200) {
            charges = kWh * 0.218;
        } else if (kWh <= 300) {
            charges = (200 * 0.218) + ((kWh - 200) * 0.334);
        } else if (kWh <= 600) {
            charges = (200 * 0.218) + (100 * 0.334) + ((kWh - 300) * 0.516);
        } else {
            charges = (200 * 0.218) + (100 * 0.334) + (300 * 0.516) + ((kWh - 600) * 0.546);
        }

        return charges;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selected = item.getItemId();

        if (selected == R.id.menuAbout) {
            Toast.makeText(this, "about clicked",Toast.LENGTH_SHORT).show();
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    }
