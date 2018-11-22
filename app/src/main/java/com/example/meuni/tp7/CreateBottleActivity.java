package com.example.meuni.tp7;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.meuni.tp7.models.Bottle;


public class CreateBottleActivity extends Activity {

    public static String ADDED_BOTTLE_KEY = "ADDED_BOTTLE_KEY";

    private Button btnAddBottle;
    private EditText eTBottleName;
    private EditText eTBottlePrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_bottle);

        btnAddBottle = findViewById(R.id.buttonCreateBottle);
        eTBottleName = findViewById(R.id.editTextBottleName);
        eTBottlePrice = findViewById(R.id.editTextBottlePrice);

        btnAddBottle.setOnClickListener(v -> {
            float prix = Float.valueOf(eTBottlePrice.getText().toString());
            String name = eTBottleName.getText().toString();
            Bottle bottle = new Bottle(prix, name);
            Toast.makeText(CreateBottleActivity.this, bottle.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent();
            intent.putExtra(ADDED_BOTTLE_KEY, bottle);
            setResult(RESULT_OK, intent);
            finish();
        });
    }
}
