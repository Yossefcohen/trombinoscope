package com.example.myfirstapplication;

import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myfirstapplication.DAO.IPersonDAO;
import com.example.myfirstapplication.DTO.Person;
import com.example.myfirstapplication.DAO.PersonDAOData;

public class AjoutPersonActivity extends AppCompatActivity {

    EditText    prenom;
    EditText    nom;
    Button      btn_ajouter;

    IPersonDAO personDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personDAO  = new PersonDAOData();

        setContentView(R.layout.activity_ajout_person);

        prenom = (EditText) findViewById(R.id.nom);
        nom  = (EditText) findViewById(R.id.prenom);

        btn_ajouter = (Button) findViewById(R.id.bouton_ajouter_personne);
        btn_ajouter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Person person = new Person(prenom.getText().toString(), nom.getText().toString(), Color.BLACK);
                personDAO.addPerson(person);
                Toast.makeText(getApplicationContext(), "ajout ok : ", Toast.LENGTH_LONG).show();

                prenom.setText("");
                nom.setText("");
            }
        });
    }

}
