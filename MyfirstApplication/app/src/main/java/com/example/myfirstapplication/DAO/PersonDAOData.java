package com.example.myfirstapplication.DAO;

import android.graphics.Color;

import com.example.myfirstapplication.DTO.Person;

import java.util.ArrayList;
import java.util.List;

public class PersonDAOData implements IPersonDAO {

    private static List<Person> persons= new ArrayList<Person>() {{
        add(new Person("PRENOM1", "NOM1", Color.BLACK));
        add(new Person("PRENOM2", "NOM2", Color.BLUE));
    }};

    @Override
    public List<Person> getPersons() {
        return persons;
    }

    @Override
    public void addPerson(Person person) {
        persons.add(person);
    }

    @Override
    public void removePerson(Person person) {
        persons.remove(person);
    }
}