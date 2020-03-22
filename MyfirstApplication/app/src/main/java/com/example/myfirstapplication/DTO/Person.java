package com.example.myfirstapplication.DTO;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Person {
    private String  firstName;
    private String  lastName;
    private int  avatarColor;

    public Person(String firstName, String lastName, int avatarColor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarColor = avatarColor;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAvatarColor() {
        return avatarColor;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAvatarColor(int avatarColor) {
        this.avatarColor = avatarColor;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return avatarColor == person.avatarColor &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, avatarColor);
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", avatarColor=" + avatarColor +
                '}';
    }
}

