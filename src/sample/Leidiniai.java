package sample;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 20163125
 */
public class Leidiniai {

    private List<Leidinys> leidiniai = new ArrayList<>();

    public void clear() {
        leidiniai = new ArrayList<>();
    }

    //Prideti nauja leidini
    public void addPublication(Leidinys leidinys) {
        leidiniai.add(leidinys);
    }

    //Prideti nauju leidinu array
    public void addPublications(Leidinys[] leidiniai) {
        for (Leidinys leidinys : leidiniai) {
            this.leidiniai.add(leidinys);
        }
    }

    //Prideti nauju leidiniu List
    public void addPublications(List<Leidinys> leidiniai) {
        for (Leidinys leidinys : leidiniai) {
            this.leidiniai.add(leidinys);
        }
    }

    //Panaikinti leidini
    public void removePublication(Leidinys leidinys) {
        if (leidiniai.contains(leidinys) && leidinys != null) {
            leidiniai.remove(leidinys);
            System.out.println("Sekmingai istrintas leidinys");
        }
        else
            System.out.println("Sitas leidinys neegzistuoja sarase");
    }

    //Panaikinti leidini pagal jo index
    public void removePublication(Integer index) {
        removePublication(leidiniai.get(index));
    }

    //Gauti leidini pagal jo index
    public Leidinys getPublication(Integer index) {
        return leidiniai.get(index);
    }

    //Gauti leidiniu List
    public List<Leidinys> getPublications() {
        return leidiniai;
    }

    //Gauti leidinio index
    public Integer indexOf(Leidinys leidinys) {
        return leidiniai.indexOf(leidinys);
    }

    public List<Leidinys> findByName(String name) {
        List<Leidinys> leidiniai = new ArrayList<>();
        for (Leidinys leidinys : this.leidiniai) {
            if (leidinys.getPavadinimas().toUpperCase().equals(name.toUpperCase()))
                leidiniai.add(leidinys);
        }
        return leidiniai;
    }
    
    public boolean isEmpty() {
        return this.leidiniai.isEmpty();
    }

    //Sukurti ir prideti leidinius is failo
    public void createFromFile(String nuoroda) throws Exception {
        List<Leidinys> leidiniai = new ArrayList<>();
        BufferedReader in = null;
        FileReader fr;
        try {

            fr = new FileReader(nuoroda);
            in = new BufferedReader(fr);
            String line;
            String name = "";

            while ((line = in.readLine()) != null)

                //Gaunamas pavadinimas
                if (line.contains(":")) {
                    name = line.replace(":","");
                }

                //Gaunami leidinio duomenys
                else {
                    String[] args = line.split(", ");
                    Leidinys leidinys;

                    //Zurnalas
                    if (args.length == 3)
                        leidinys = new Zurnalas(name, Integer.parseInt(args[1]), args[0], Integer.parseInt(args[2]));

                    //Knyga
                    else {
                        leidinys = new Knyga(args[3], name, Integer.parseInt(args[1]), args[0], Integer.parseInt(args[2]));
                        System.out.println("Sekmingai sukurtas leidinys ir pridetas i leidiniu sarasa:");
                    }
                    leidiniai.add(leidinys);
                    System.out.println(leidinys);
                }
        } finally {
            if (in != null)
                in.close();
        }
        addPublications(leidiniai);
    }
}
