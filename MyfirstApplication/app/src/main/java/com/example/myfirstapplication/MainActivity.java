package com.example.myfirstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    Button btn_afficher;
    Button btn_ajouter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_afficher = (Button)findViewById(R.id.affichage);
        btn_afficher.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Affichage ok",Toast.LENGTH_LONG).show();
                        Intent activityChangeIntent = new Intent(MainActivity.this, ListPersonActivity.class);
                        MainActivity.this.startActivity(activityChangeIntent);
                                           }
                }
        );
        btn_ajouter = (Button) findViewById(R.id.ajout);
        btn_ajouter.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getApplicationContext(), "Ajout ok", Toast.LENGTH_LONG).show();
                        Intent activityChangeIntent = new Intent(MainActivity.this, AjoutPersonActivity.class);
                        MainActivity.this.startActivity(activityChangeIntent);
                    }
                }
        );
    }




}

