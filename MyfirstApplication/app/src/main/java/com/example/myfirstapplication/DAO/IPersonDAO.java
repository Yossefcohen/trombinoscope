package com.example.myfirstapplication.DAO;

import com.example.myfirstapplication.DTO.Person;

import java.util.List;

public interface IPersonDAO {
    //public List<Person> getPerson();


    public  List<Person> getPersons();

    public void addPerson(Person person);

    public void removePerson(Person person);
}
