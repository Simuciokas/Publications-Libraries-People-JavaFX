package sample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author 20163125
 */
public class Zmogus {

    private SimpleStringProperty name, surname, birthDate;
    private HashMap<Biblioteka, Leidiniai> bibliotekos;

    Zmogus(String name, String surname, String birthDate) {
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
    }

    Zmogus(String name, String surname, String birthDate, HashMap<Biblioteka, Leidiniai> bibliotekos) {
        setName(name);
        setSurname(surname);
        setBirthDate(birthDate);
        setBibliotekos(bibliotekos);
    }

    public void addBiblioteka(Biblioteka biblioteka) {
        if (!bibliotekos.containsKey(biblioteka))
            bibliotekos.put(biblioteka, new Leidiniai());
    }

    public void addPublication(Biblioteka biblioteka, Leidinys leidinys) {
        if (!bibliotekos.containsKey(biblioteka))
            addBiblioteka(biblioteka);
        bibliotekos.get(biblioteka).addPublication(leidinys);
    }

    public void addPublications(Biblioteka biblioteka, List<Leidinys> leidiniai) {
        if (!bibliotekos.containsKey(biblioteka))
            addBiblioteka(biblioteka);
        bibliotekos.get(biblioteka).addPublications(leidiniai);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setSurname(String surname) {
        this.surname.set(surname);
    }

    public void setBirthDate(String birthDate) {
        this.birthDate.set(birthDate);
    }

    public String getName() {
        return name.get();
    }

    public String getSurname() {
        return surname.get();
    }

    public String getBirthDate() {
        return birthDate.get();
    }

    public void setBibliotekos(HashMap<Biblioteka, Leidiniai> bibliotekos) {
        this.bibliotekos = bibliotekos;
    }

    public HashMap<Biblioteka, Leidiniai> getBibliotekos() {
        return bibliotekos;
    }

    public String toString() {
        return "Vardas: " + getName() +
                "\nPavarde: " + getSurname() +
                "\nGimimo data: " + getBirthDate();
    }



    
}
