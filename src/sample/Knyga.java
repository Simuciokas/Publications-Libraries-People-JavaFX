package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Knyga extends Leidinys {
    
    /*
     * Tuscias Knyga konstruktorius
     */
    public Knyga() {
        
    }
    
    /*
     * Knyga konstruktorius
     * Parametrai:
     *
     * String autorius
     * String pavadinimas
     * int leidimoMetai
     * String leidykla
     * int puslapiuSkaicius
     */
    public Knyga(String autorius, String pavadinimas, int leidimoMetai, String leidykla, int puslapiuSkaicius) {
        this.type = new SimpleStringProperty("Knyga");
        this.autorius = new SimpleStringProperty(autorius);
        this.pavadinimas = new SimpleStringProperty(pavadinimas);
        this.puslapiuSkaicius = new SimpleIntegerProperty(puslapiuSkaicius);
        this.leidimoMetai = new SimpleIntegerProperty(leidimoMetai);
        this.leidykla = new SimpleStringProperty(leidykla);
    }

    public Knyga(Integer id, String autorius, String pavadinimas, int leidimoMetai, String leidykla, int puslapiuSkaicius) {
        this.type = new SimpleStringProperty("Knyga");
        this.id = new SimpleIntegerProperty(id);
        this.autorius = new SimpleStringProperty(autorius);
        this.pavadinimas = new SimpleStringProperty(pavadinimas);
        this.puslapiuSkaicius = new SimpleIntegerProperty(puslapiuSkaicius);
        this.leidimoMetai = new SimpleIntegerProperty(leidimoMetai);
        this.leidykla = new SimpleStringProperty(leidykla);
    }

    //Parodo Knygos informacija
    public void showInformation() {
        if (this.pavadinimas != null)
            System.out.println(this);
        else
            System.out.println("Informacijos nera");
    }

    //Pavercia Knyga objekta i grazu teksta
    public String toString() {
        return this.pavadinimas.get().toUpperCase()
                + "\nAutorius: " + this.autorius.get()
                + "\nLeidimo metai: " + this.leidimoMetai.get()
                + "\nLeidykla: " + this.leidykla.get()
                + "\nPuslapiu skaicius: " + this.puslapiuSkaicius.get()
                + "\nTipas: Knyga" + "\n";
    }

}
