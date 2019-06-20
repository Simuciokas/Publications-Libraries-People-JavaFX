package sample;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author 20163125
 */
public class Biblioteka {
    
    public HashMap<Leidinys, Integer> leidiniai = new HashMap<>();
    public String pavadinimas;
    
    public Biblioteka() {
        
    }
    
    Biblioteka(String pavadinimas) {
        this.pavadinimas = pavadinimas;
    }
    
    Biblioteka(List<Leidinys> leidiniai, String pavadinimas) {
        for (Leidinys leidinys : leidiniai)
            this.leidiniai.put(leidinys, 1);
        this.pavadinimas = pavadinimas;
    }
    
    public Biblioteka(Leidinys[] leidiniai, String pavadinimas) {
        for (Leidinys leidinys : leidiniai)
            this.leidiniai.put(leidinys, 1);
        this.pavadinimas = pavadinimas;
    }
    
    public Biblioteka(Leidinys leidinys, String pavadinimas) {
        leidiniai.put(leidinys, 1);
        this.pavadinimas = pavadinimas;
    }
    
    public void addPublication(Leidinys leidinys) {
        Integer val = leidiniai.get(leidinys);
        if (val == null)
            this.leidiniai.put(leidinys, 1);
        else
            leidiniai.put(leidinys, val+1);
    }

    public void addPublications(List<Leidinys> leidiniai) {
        for (Leidinys leidinys : leidiniai) {
            addPublication(leidinys);
        }
    }
    
    public void removePublication(Leidinys leidinys) {
        leidiniai.remove(leidinys);
    }
    
    public List<Leidinys> getPublications() {
        return new ArrayList<>(this.leidiniai.keySet());
    }
    
    public boolean hasPublication(Leidinys leidinys) {
        return this.leidiniai.keySet().contains(leidinys);
    }
    
    public Integer getAmountOfPublication(Leidinys leidinys) {
        return this.leidiniai.get(leidinys);
    }
    
    public void setAmountOfPublication(Leidinys leidinys, int amount) {
        this.leidiniai.put(leidinys, amount);
    }
    
    public void showLibrary() {
        if (leidiniai.isEmpty())
            System.out.println("Biblioteka tuščia");
        else
            for (Leidinys leidinys : new ArrayList<>(leidiniai.keySet())) {
                System.out.print(leidinys);
                System.out.println("Kiekis: " + leidiniai.get(leidinys));
            }
    }
}
