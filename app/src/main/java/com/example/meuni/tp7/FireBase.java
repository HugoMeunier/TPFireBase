package com.example.meuni.tp7;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meuni.tp7.models.Bottle;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBase extends Activity {

    public static String FIREBASE_KEY = "FIREBASE_KEY";
    Button btn;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fire_base);

        btn = findViewById(R.id.btn);
        txt = findViewById(R.id.txt);
        Bottle bottle = new Bottle(12, "bottle");

        btn.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.putExtra(FIREBASE_KEY, "ok");
            setResult(RESULT_OK, intent);
            finish();
        });
    }

}
