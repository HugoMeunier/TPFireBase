package com.example.meuni.tp7;

//ajoute des bouteilles à un cellar existant
//recupere des cellars


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meuni.tp7.models.Bottle;
import com.example.meuni.tp7.models.Cellar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String OWNER_NAME = "Hugo";
    Bottle bottle1, bottle2;
    Cellar cellar;
    private BottleDao bottleDao;

    private static final int MAIN_ACTIVITY_INTENT_RESULT_CODE = 1;
    public static  final int MAIN_ACTIVITY_INTENT_RESULT_CODE_FIREBASE_KEY = 2;
    private static final String PATH = "Cellar";

    private List<Bottle> listBottle = new ArrayList<>();

    Retrofit retrofit = new Retrofit.Builder() //declaration utilisation retrofit
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://chivas-container.herokuapp.com/")
            .build();

    WebService service = retrofit.create(WebService.class);
    FirebaseDatabase database;
    TextView tVCellarURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cellar = new Cellar();
        cellar.setOwnerName(OWNER_NAME);

        bottle1 = new Bottle(1,"bouteille1");
        bottle2 = new Bottle(2,"bouteille2");
        cellar.addBottle(bottle1);
        cellar.addBottle(bottle2);
        cellar.addBottle(bottle2);


        database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(PATH);

        Button btnStart = findViewById(R.id.buttonStart);
        btnStart.setVisibility(View.VISIBLE);

        Button btnCreateCellarOnline = findViewById(R.id.buttonCreateCellarOnline);
        btnStart.setVisibility(View.VISIBLE);

        Button btnGetBottleOnline = findViewById(R.id.buttonGetBottleOnline);
        btnGetBottleOnline.setVisibility(View.VISIBLE);

        tVCellarURL = findViewById(R.id.textViewCellarUrl);

        bottleDao = AppDatabase.getAppDatabase(this).getBottleDao();
        bottleDao.getAll();


        btnStart.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateBottleActivity.class);
            MainActivity.this.startActivityForResult(intent, MAIN_ACTIVITY_INTENT_RESULT_CODE);
        });

        btnGetBottleOnline.setOnClickListener((view) -> {
            //loadBottleFromServer();
            Intent intent = new Intent(MainActivity.this, FireBase.class);
            MainActivity.this.startActivityForResult(intent, MAIN_ACTIVITY_INTENT_RESULT_CODE_FIREBASE_KEY);
        });

        btnCreateCellarOnline.setOnClickListener((view) -> {
           // postBottleOnServer();
            tVCellarURL.setText(bottleDao.getAll().toString());
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GenericTypeIndicator<Cellar> bottleListType = new GenericTypeIndicator<Cellar>() {};
                Cellar serverBottles = dataSnapshot.getValue(bottleListType);
                if (serverBottles == null){
                    Toast.makeText(MainActivity.this, "null ", Toast.LENGTH_LONG).show();
                }
                else {
                    tVCellarURL.setText(serverBottles.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    private void postBottleOnServer() {
        service.postBottle(OWNER_NAME, bottle1)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        tVCellarURL.setText("creation cellar ok");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        tVCellarURL.setText("creation cellar non");
                    }
                });
    }

    private void loadBottleFromServer() {
        service.getAllBottles(OWNER_NAME)
                .enqueue(new Callback<List<Bottle>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Bottle>> call,
                                           @NonNull Response<List<Bottle>> response) {
                        List<Bottle> serverData = response.body();
                        tVCellarURL.setText(serverData.toString());
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Bottle>> call,
                                          @NonNull Throwable t) {
                        Toast.makeText(MainActivity.this, "Unable to load data", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAIN_ACTIVITY_INTENT_RESULT_CODE) {
            Bottle addedBottle = (Bottle) data.getSerializableExtra(CreateBottleActivity.ADDED_BOTTLE_KEY);
            cellar.addBottle(addedBottle);
            listBottle.add(addedBottle);
            Toast.makeText(MainActivity.this, addedBottle.toString(), Toast.LENGTH_LONG).show();
            //postBottleOnServer();
            bottleDao.insert(addedBottle);
            List<Bottle> a = bottleDao.getAll();
            tVCellarURL.setText(bottleDao.getAll().toString());
            Toast.makeText(MainActivity.this, bottleDao.toString(), Toast.LENGTH_LONG).show();
        }
        if (requestCode == MAIN_ACTIVITY_INTENT_RESULT_CODE_FIREBASE_KEY) {
            String stringResult = (String) data.getSerializableExtra(FireBase.FIREBASE_KEY);
            DatabaseReference myRef = database.getReference(PATH);
            myRef.setValue(cellar);
            Toast.makeText(MainActivity.this, stringResult, Toast.LENGTH_LONG).show();
        }

    }



}
