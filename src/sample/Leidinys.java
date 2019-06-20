package sample;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author 20163125
 */
public class Leidinys {
    public SimpleStringProperty pavadinimas, leidykla, autorius;
    public SimpleStringProperty type;
    public SimpleIntegerProperty puslapiuSkaicius, leidimoMetai;
    public SimpleIntegerProperty id;

    public Integer getId() {
        return this.id.get();
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    //Grazina knygos autoriu
    public String getAutorius() {
        if (this.getType().equals("Zurnalas"))
            return null;
        else
            return this.autorius.get();
    }

    //Nustatyti knygos autoriu
    public void setAutorius(String autorius) {
        if (this.getType().equals("Knyga"))
            this.autorius.set(autorius);
        else
            System.out.println("Zurnalas neturi autoriaus");
    }

    //Nustatyti leidinio pavadinima
    public void setPavadinimas(String pavadinimas) {

        this.pavadinimas.set(pavadinimas);
    }

    //Nustatyti leidinio puslapiu skaiciu
    public void setPuslapiuSkaicius(Integer puslapiuSkaicius) {

        this.puslapiuSkaicius.set(puslapiuSkaicius);
    }

    //Nustatyti leidinio leidykla
    public void setLeidykla(String leidykla) {

        this.leidykla.set(leidykla);
    }

    //Nustatyti leidinio leidimo metus
    public void setLeidimoMetai(Integer leidimoMetai) {

        this.leidimoMetai.set(leidimoMetai);
    }

    //Grazina leidinio pavadinima
    public String getPavadinimas() {

        return this.pavadinimas.get();
    }

    //Grazina leidinio puslapiu skaiciu
    public Integer getPuslapiuSkaicius() {
        return this.puslapiuSkaicius.get();
    }

    //Grazina leidinio leidyklos pavadinima
    public String getLeidykla() {

        return this.leidykla.get();
    }

    //Grazina leidinio leidimo metus
    public Integer getLeidimoMetai() {

        return this.leidimoMetai.get();
    }

    //Parodo leidinio informacija
    public void showInformation() {
        System.out.println(this);
    }

    //Grazina leidinio tipa
    public String getType() {
        return this.type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

}
