package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import sun.java2d.pipe.SpanShapeRenderer;

public class Zurnalas extends Leidinys {
    
    /*
     * Tuscias Zurnalas konstruktorius
     */
    public Zurnalas() {
        
    }
    
    /*
     * Zurnalas konstruktorius
     * Parametrai:
     *
     * String pavadinimas
     * int leidimoMetai
     * String leidykla
     * int puslapiuSkaicius
     */
    public Zurnalas(String pavadinimas, int leidimoMetai, String leidykla, int puslapiuSkaicius) {
        this.type = new SimpleStringProperty("Zurnalas");
        this.autorius = new SimpleStringProperty("");
        this.pavadinimas = new SimpleStringProperty(pavadinimas);
        this.puslapiuSkaicius = new SimpleIntegerProperty(puslapiuSkaicius);
        this.leidimoMetai = new SimpleIntegerProperty(leidimoMetai);
        this.leidykla = new SimpleStringProperty(leidykla);
    }

    public Zurnalas(Integer id, String pavadinimas, int leidimoMetai, String leidykla, int puslapiuSkaicius) {
        this.type = new SimpleStringProperty("Zurnalas");
        this.id = new SimpleIntegerProperty(id);
        this.autorius = new SimpleStringProperty("");
        this.pavadinimas = new SimpleStringProperty(pavadinimas);
        this.puslapiuSkaicius = new SimpleIntegerProperty(puslapiuSkaicius);
        this.leidimoMetai = new SimpleIntegerProperty(leidimoMetai);
        this.leidykla = new SimpleStringProperty(leidykla);
    }
    
    //Parodo zurnalo informacija
    public void showInformation() {
        if (this.pavadinimas != null)
            System.out.println(this);
        else
            System.out.println("Informacijos nera");
    }

    //Pavercia zurnalo objekta i grazu teksta
    public String toString() {
        return this.pavadinimas.get().toUpperCase()
                + "\nLeidimo metai: " + this.leidimoMetai.get()
                + "\nLeidykla: " + this.leidykla.get()
                + "\nPuslapiu skaicius: " + this.puslapiuSkaicius.get()
                + "\nTipas: Zurnalas" + "\n";
    }

}
