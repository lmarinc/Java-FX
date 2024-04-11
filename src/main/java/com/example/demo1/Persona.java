package com.example.demo1;

import java.util.Objects;

public class Persona {

    private String name;
    private String surname;
    private String age;

    public Persona(String name, String surname, String age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getAge() {
        return age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Persona persona = (Persona) o;
        return Objects.equals(name, persona.name) && Objects.equals(surname, persona.surname) && Objects.equals(age, persona.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age);
    }
}
