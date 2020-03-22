package com.example.myfirstapplication;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.myfirstapplication.DAO.PersonDAOData;
import com.example.myfirstapplication.DAO.IPersonDAO;
import com.example.myfirstapplication.DTO.Person;



    public class ListPersonActivity extends AppCompatActivity {

        ListView mListView;
        PersonAdapter adapter;
        TextView txt_listPerson;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_list_person);

            IPersonDAO personDAOData = new PersonDAOData();


            mListView = (ListView) findViewById(R.id.personsList);
            adapter = new PersonAdapter(ListPersonActivity.this, personDAOData.getPersons());
            mListView.setAdapter(adapter);
        }
    }


