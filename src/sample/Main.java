package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main extends Application {

    private static Leidiniai leidiniai;
    private static Leidiniai pasirinktiLeidiniai;
    private static List<Biblioteka> bibliotekos;
    private static Biblioteka pasirinktaBiblioteka;
    private static List<Zmogus> zmones;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setLeidiniai(new Leidiniai());
        setPasirinktiLeidiniai(new Leidiniai());
        setBibliotekos(new ArrayList<>());
        setZmones(new ArrayList<>());
        setPasirinktaBiblioteka(null);
        instance = this;
        this.primaryStage = primaryStage;
        Parent page = FXMLLoader.<Parent>load(getClass().getResource("Leidiniu.fxml"));
        Scene scene = new Scene(page);
        this.primaryStage.initStyle(StageStyle.DECORATED);
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        leidiniai.addPublications(
            Arrays.asList(
                //String autorius, String pavadinimas, int leidimoMetai, String leidykla, int puslapiuSkaicius
                new Knyga("Author3", "AAA", 10, "Publisher", 11),
                new Knyga("Author2", "AAA", 11, "Publisher", 12),
                new Knyga("Author", "AAA", 12, "Publisher", 13),
                new Knyga("Author1", "BBB", 13, "Publisher1", 14),
                new Knyga("Author2", "AAA", 14, "Publisher1", 15),
                new Knyga("Author", "AAA", 15, "Publisher1", 16)
            )
        );
    }

    public static List<Biblioteka> getBibliotekos() { return bibliotekos; }

    public static void setBibliotekos(List<Biblioteka> bibliotekos) { Main.bibliotekos = bibliotekos; }

    public static Biblioteka getPasirinktaBiblioteka() { return pasirinktaBiblioteka; }

    public static void setPasirinktaBiblioteka(Biblioteka biblioteka) { pasirinktaBiblioteka = biblioteka; }

    public static Leidiniai getLeidiniai() {
        return leidiniai;
    }

    public static void setLeidiniai(Leidiniai leidiniai) {
        Main.leidiniai = leidiniai;
    }

    public static Leidiniai getPasirinktiLeidiniai() { return pasirinktiLeidiniai; }

    public static void setPasirinktiLeidiniai(Leidiniai leidiniai) { pasirinktiLeidiniai = leidiniai; }

    public static List<Zmogus> getZmones() {return zmones;}

    public static void setZmones(List<Zmogus> zmones) {Main.zmones = zmones;}

    public static void addZmogus(Zmogus zmogus) {zmones.add(zmogus);}

    public static void main(String[] args) {
        launch(args);
    }

    public Stage primaryStage;
    public static Main instance;

}
