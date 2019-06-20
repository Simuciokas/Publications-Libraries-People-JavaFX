package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;

import javax.swing.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class LeidiniuController {

    Integer globalID;

    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    @FXML
    private Pane leidEditDB, leidCreate, leidDelete, leidSelect, leidShowAll, leidShowInfo, leidSave, menuGoPer, menuGoLib, leidShowAllDB;

    @FXML
    private Button leidEditUpdate, leidEditSubmit, leidShowAllDBUpdate, leidCreateSubmit, leidDeleteSubmit, leidShowAllUpdate, leidShowInfoUpdate, leidSelectSubmit, leidSaveSubmit;

    @FXML
    private TextField leidCreateType2, leidEditPageCount, leidEditID, leidEditPublisher, leidEditYear, leidEditName, leidEditAuthor, leidCreateAuthor, leidCreateName, leidCreatePublisher, leidCreateYear, leidCreatePageCount, leidDeleteIndex, leidSelectName;

    @FXML
    private TextArea leidEditOutput, leidCreateOutput, leidDeleteOutput, leidShowAllOutput, leidShowInfoOutput, leidSelectOutput, leidSaveOutput;

    @FXML
    private ComboBox leidEditType, leidCreateType, leidShowAllDBType;

    @FXML
    private CheckBox leidDeleteAllCheckBox, leidCreateCheckBox, leidDeleteCheckBox, leidSelectCheckBox;

    @FXML
    private Text leidCreateExit;

    @FXML
    private TableView leidShowAllDBTable;

    private void addLeidTypeToDB(String type) throws Exception {
        ObservableList data = null;
        String sql = "SELECT * FROM leidTypes";
        try {
            System.out.println("Debug3");
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();

            data = FXCollections.observableArrayList();
            data.add("");
            while (resultSet.next()) {
                data.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        preparedStatement = null;
        System.out.println("Debug2");

        if (data != null) {
            System.out.println("Debug4");
            if (!data.contains(type)) {
                System.out.println("Debug1");
                sql = "INSERT INTO leidTypes (name) VALUES (?)";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    preparedStatement.setString(1, type);
                    Integer up = preparedStatement.executeUpdate();
                    if (up == 1) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Dėl pridėjimo");
                        alert.setHeaderText("Pridėtas naujas leidinio tipas");
                        alert.setContentText("" + type);
                        alert.showAndWait();
                        updateComboBox();
                    } else
                        System.out.println("Įvyko klaida");
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }
    }

    private void updateComboBox() throws Exception {
        String sql = "SELECT * FROM leidTypes";
        try {
            preparedStatement = con.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        ObservableList data = FXCollections.observableArrayList();
        while (resultSet.next())
            data.add(resultSet.getString(2));
        leidCreateType.setItems(data);
        leidCreateType.setValue(null);
    }

    private void addPublicationToDB(Leidinys leidinys) {
        String sql = "INSERT INTO leidiniai (author, name, year, publisher, pageCount) VALUES (?, ?, ?, ?, ?)";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, leidinys.getAutorius());
            preparedStatement.setString(2, leidinys.getPavadinimas());
            preparedStatement.setInt(3, leidinys.getLeidimoMetai());
            preparedStatement.setString(4, leidinys.getLeidykla());
            preparedStatement.setInt(5, leidinys.getPuslapiuSkaicius());
            Integer up = preparedStatement.executeUpdate();
            if (up == 1)
                leidCreateOutput.setText("Pridėtas leidinys į DB");
            else
                leidCreateOutput.setText("Įvyko klaida");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void deleteAllFromDB() {
        String sql = "DELETE FROM leidiniai";
        try {
            preparedStatement = con.prepareStatement(sql);
            Integer up = preparedStatement.executeUpdate();
            if (up == 1)
                leidDeleteOutput.setText("Ištrinti leidiniai iš DB");
            else
                leidDeleteOutput.setText("???");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Dėl ištrynimo");
            alert.setHeaderText("Viskas ištrinta");
            alert.setContentText("Jūs esate žiaurus žmogus :(");
            alert.showAndWait();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void deletePublicationFromDB(Integer index) {
        String sql = "DELETE FROM leidiniai WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, index);
            Integer up = preparedStatement.executeUpdate();
            if (up == 1)
                leidDeleteOutput.setText("Ištrintas leidinys iš DB: ");
            else
                leidDeleteOutput.setText("Leidinys nesurastas");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private Leidinys getLeidinysFromDB(Integer id) throws Exception {
        String sql = "SELECT * FROM leidiniai WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        if (resultSet.next()) {
            Leidinys leidinys;
            if (resultSet.getString(2) != null)
                leidinys = new Knyga(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6));
            else
                leidinys = new Zurnalas(resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6));
            leidEditOutput.setText("Surastas leidinys su ID " + id);
            return leidinys;
        } else {
            leidEditOutput.setText("Nesurastas leidinys su ID " + id);
            return null;
        }
    }

    private void updatePublication(Integer id, Leidinys leidinys) {
        leidEditOutput.setText("Atnaujintas leidinys su ID " + globalID);

        String sql = "UPDATE leidiniai SET type = ?, author = ?, name = ?, year = ?, publisher = ?, pageCount = ? WHERE id = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, leidinys.getAutorius());
            preparedStatement.setString(2, leidinys.getPavadinimas());
            preparedStatement.setInt(3, leidinys.getLeidimoMetai());
            preparedStatement.setString(4, leidinys.getLeidykla());
            preparedStatement.setInt(5, leidinys.getPuslapiuSkaicius());
            preparedStatement.setInt(6, id);
            Integer up = preparedStatement.executeUpdate();
            if (up == 1)
                leidEditOutput.setText("Atnaujintas leidinys su ID " + id);
            else {
                toggleEdit(true);
                leidEditOutput.setText("Nesurastas leidinys su ID " + id);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            leidEditOutput.setText("Įvyko klaida: " + ex);
        }
    }

    private void selectPublicationFromDB(String name) throws Exception {
        String sql = "SELECT * FROM leidiniai WHERE name = ?";
        try {
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        Leidiniai leidiniai = new Leidiniai();
        if (resultSet != null) {
            while (resultSet.next()) {
                if (resultSet.getString(2) != null)
                    leidiniai.addPublication(new Knyga(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6)));
                else
                    leidiniai.addPublication(new Zurnalas(resultSet.getInt(1), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5), resultSet.getInt(6)));
            }
            if (leidiniai.isEmpty())
                leidSelectOutput.setText("Nesurastas joks leidinys su pavadinimu " + name);
            else {
                Main.setPasirinktiLeidiniai(leidiniai);
                leidSelectOutput.setText("Surasti " + leidiniai.getPublications().size() + " leidiniai");
            }
        }
    }

    private void toggleEdit(boolean disable){
        leidEditType.setDisable(disable);
        leidEditAuthor.setDisable(disable);
        leidEditName.setDisable(disable);
        leidEditYear.setDisable(disable);
        leidEditPublisher.setDisable(disable);
        leidEditPageCount.setDisable(disable);
        leidEditSubmit.setDisable(disable);
    }

    public void handleSubmitAction(ActionEvent event) throws Exception {
        if (event.getSource() == leidCreateSubmit) {
            if (leidCreateAuthor.getText().isEmpty() && leidCreateType.getValue().toString().equals("Knyga")) {
                leidCreateOutput.setText("Missing Author");
            }
            else if (leidCreateName.getText().isEmpty()) {
                leidCreateOutput.setText("Missing Name");
            } else if (leidCreatePublisher.getText().isEmpty()) {
                leidCreateOutput.setText("Missing Publisher");
            } else if (leidCreateYear.getText().isEmpty()) {
                leidCreateOutput.setText("Missing Year");
            } else if (leidCreatePageCount.getText().isEmpty()) {
                leidCreateOutput.setText("Missing Page Count");
            } else {
                Integer releaseYear;
                try {
                    releaseYear = Integer.parseInt(leidCreateYear.getText());
                } catch (Exception e) {
                    leidCreateOutput.setText("Wrong Year");
                    releaseYear = -1;
                }
                if (!releaseYear.equals(-1)) {
                    Integer pageCount;
                    try {
                        pageCount = Integer.parseInt(leidCreatePageCount.getText());
                    } catch (Exception e) {
                        leidCreateOutput.setText("Wrong Page count");
                        pageCount = -1;
                    }
                    if (!pageCount.equals(-1)) {
                        String type;
                        if (leidCreateType.getValue() == null) {
                            addLeidTypeToDB(leidCreateType2.getText());
                            type = leidCreateType2.getText();
                        }
                        else
                            type = leidCreateType.getValue().toString();
                        leidCreateOutput.setText("Creating new " + type + "\n"
                                + (type.equals("Knyga") ? ("Author: " + leidCreateAuthor.getText() + "\n") : "")
                                + "Name: " + leidCreateName.getText() + "\n"
                                + "Year: " + releaseYear + "\n"
                                + "Publisher: " + leidCreatePublisher.getText() + "\n"
                                + "Page count: " + leidCreatePageCount.getText()
                        );
                        Leidinys publication;
                        if (type.equals("Zurnalas"))
                                publication = new Zurnalas(leidCreateName.getText(), releaseYear, leidCreatePublisher.getText(), pageCount);
                        else if (type.equals("Knyga"))
                                publication = new Knyga(leidCreateAuthor.getText(), leidCreateName.getText(), releaseYear, leidCreatePublisher.getText(), pageCount);
                        else
                            publication = new Leidinys();
                        if (leidCreateCheckBox.isSelected())
                            addPublicationToDB(publication);
                        else
                            Main.getLeidiniai().addPublication(publication);
                    }
                }
            }
        }

        else if (event.getSource() == leidEditUpdate) {
            Integer id;
            try {
                id = Integer.parseInt(leidEditID.getText());
            } catch (Exception e) {
                leidEditOutput.setText("Wrong ID");
                id = -1;
            }

            if (!id.equals(-1)) {
                Leidinys leidinys = getLeidinysFromDB(id);
                if (leidinys != null) {
                    toggleEdit(false);
                    leidEditAuthor.setText(leidinys.getAutorius());
                    leidEditName.setText(leidinys.getPavadinimas());
                    leidEditPageCount.setText(leidinys.getPuslapiuSkaicius().toString());
                    leidEditPublisher.setText(leidinys.getLeidykla());
                    leidEditYear.setText(leidinys.getLeidimoMetai().toString());
                    globalID = id;
                } else
                    toggleEdit(true);
            }
            else
                toggleEdit(true);
        }

        else if (event.getSource() == leidEditSubmit) {
            if (leidEditAuthor.getText().isEmpty() && leidEditType.getValue().toString().equals("Knyga")) {
                leidEditOutput.setText("Missing Author");
            }
            else if (leidEditName.getText().isEmpty()) {
                leidEditOutput.setText("Missing Name");
            } else if (leidEditPublisher.getText().isEmpty()) {
                leidEditOutput.setText("Missing Publisher");
            } else if (leidEditYear.getText().isEmpty()) {
                leidEditOutput.setText("Missing Year");
            } else if (leidEditPageCount.getText().isEmpty()) {
                leidEditOutput.setText("Missing Page Count");
            } else {
                Integer releaseYear;
                try {
                    releaseYear = Integer.parseInt(leidEditYear.getText());
                } catch (Exception e) {
                    leidEditOutput.setText("Wrong Year");
                    releaseYear = -1;
                }
                if (!releaseYear.equals(-1)) {
                    Integer pageCount;
                    try {
                        pageCount = Integer.parseInt(leidEditPageCount.getText());
                    } catch (Exception e) {
                        leidEditOutput.setText("Wrong Page count");
                        pageCount = -1;
                    }
                    if (!pageCount.equals(-1)) {
                        Leidinys leidinys = (leidEditType.getValue().equals("Zurnalas") ?
                                new Zurnalas(globalID, leidEditName.getText(), releaseYear, leidEditPublisher.getText(), pageCount) :
                                new Knyga(globalID, leidEditAuthor.getText(), leidEditName.getText(), releaseYear, leidEditPublisher.getText(), pageCount));
                        updatePublication(globalID, leidinys);
                    }
                }
            }
        }

        else if (event.getSource() == leidDeleteSubmit) {
            if (leidDeleteAllCheckBox.isSelected()) {
                if (leidDeleteCheckBox.isSelected()) {
                    deleteAllFromDB();
                }
                else {
                    Main.getLeidiniai().clear();
                }
            } else {
                Integer deleteIndex;
                try {
                    deleteIndex = Integer.parseInt(leidDeleteIndex.getText());
                } catch (Exception e) {
                    leidDeleteOutput.setText("Prašau rašyti skaičių");
                    deleteIndex = -1;
                }

                if (!deleteIndex.equals(-1)) {
                    Leidinys toDelete = null;

                    if (leidDeleteCheckBox.isSelected())
                        deletePublicationFromDB(deleteIndex);
                    else {
                        try {
                            toDelete = Main.getLeidiniai().getPublication(deleteIndex);
                        } catch (Exception e) {
                            leidDeleteOutput.setText("Leidinys su tokiu indeksu sąraše nerastas");
                        }

                        if (toDelete != null) {
                            Main.getLeidiniai().removePublication(toDelete);
                            leidDeleteOutput.setText("Sėkmingai ištrintas leidinys su indeksu/id " + deleteIndex);
                        }
                    }
                }
            }
        }

        else if (event.getSource() == leidSelectSubmit) {
            String name = leidSelectName.getText();
            Leidiniai pasirinktiLeidiniai = new Leidiniai();
            if (leidSelectCheckBox.isSelected())
                selectPublicationFromDB(leidSelectName.getText());
            else {
                pasirinktiLeidiniai.addPublications(Main.getLeidiniai().findByName(name));
                if (pasirinktiLeidiniai.getPublications().size() > 0) {
                    leidSelectOutput.setText("Surasti ir pasirinkti " + pasirinktiLeidiniai.getPublications().size() + " Leidiniai");
                    Main.setPasirinktiLeidiniai(pasirinktiLeidiniai);
                } else
                    leidSelectOutput.setText("Nesurastas joks leidinys");
            }
        }

        else if (event.getSource() == leidSaveSubmit) {

            List<String> lines = new ArrayList<>();
            String path;
            Path file;

            for (Leidinys leidinys : Main.getPasirinktiLeidiniai().getPublications()) {
                lines.add(leidinys.getPavadinimas() + ":");
                lines.add(leidinys.getLeidykla() + ", " + (leidinys.getLeidimoMetai()) + ", " + (leidinys.getPuslapiuSkaicius()) + (leidinys.getType().equals("Knyga") ? leidinys.getAutorius() : ""));
            }

            path = "D:\\Java\\Lab5\\output.txt";
            file = Paths.get(path);
            try {
                //Issaugoma leidiniu informacija i faila
                Files.write(file, lines, Charset.forName("UTF-8"));
            } catch (Exception e) {
                leidSaveOutput.setText("Įvyko klaida mėginant išsaugoti failus:");
                System.out.println(e.getMessage());
            }
            finally {
                leidSaveOutput.setText("Sėkmingai išsaugoti " + Main.getPasirinktiLeidiniai().getPublications().size() + " leidiniai į " + path);
            }
        }
    }

    public void handleToggleAction(ActionEvent event) {
        if (event.getSource() == leidDeleteAllCheckBox) {
            if (leidDeleteAllCheckBox.isSelected())
                leidDeleteIndex.setDisable(true);
            else
                leidDeleteIndex.setDisable(false);
        }
    }

    public void handleTypeChange(ActionEvent event) throws Exception {
        if (event.getSource() == leidCreateType) {
            if (leidCreateType.getValue().toString().equals("Zurnalas"))
                leidCreateAuthor.setDisable(true);
            else if (leidCreateType.getValue().toString().equals("Knyga"))
                leidCreateAuthor.setDisable(false);
            else if (leidCreateType.getValue().toString().equals(""))
                leidCreateType2.setDisable(false);
            else
                leidCreateType2.setDisable(true);
        }
    }

    //Changes scenes depending on inputted fileName parameter
    private void changeScene(Stage stage, String fileName) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fileName)));
        stage.setScene(scene);
    }

    public void handleButtonAction(MouseEvent event) throws Exception {

        //Needed variables
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //Leidiniu buttons
        if (event.getSource() == leidCreate)
            changeScene(stage,"leid\\leidCreate.fxml");
        else if (event.getSource() == leidDelete)
            changeScene(stage,"leid\\leidDelete.fxml");
        else if (event.getSource() == leidSelect)
            changeScene(stage,"leid\\leidSelect.fxml");
        else if (event.getSource() == leidShowAll)
            changeScene(stage,"leid\\leidShowAll.fxml");
        else if (event.getSource() == leidShowInfo)
            changeScene(stage,"leid\\leidShowInfo.fxml");
        else if (event.getSource() == leidSave)
            changeScene(stage,"leid\\leidSave.fxml");
        else if (event.getSource() == leidCreateExit)
            changeScene(stage,"Leidiniu.fxml");
        else if (event.getSource() == menuGoLib)
            changeScene(stage,"Biblioteku.fxml");
        else if (event.getSource() == menuGoPer)
            changeScene(stage,"Zmoniu.fxml");
        else if (event.getSource() == leidShowAllDB)
            changeScene(stage, "leid\\leidShowAllDB.fxml");
        else if (event.getSource() == leidEditDB)
            changeScene(stage, "leid\\leidEditDB.fxml");

        else if (event.getSource() == leidShowAllUpdate) {
            if (!Main.getLeidiniai().getPublications().isEmpty()) {
                for (Leidinys leidinys : Main.getLeidiniai().getPublications()) {
                    leidShowAllOutput.setText(leidShowAllOutput.getText() + "Indeksas [ " + Main.getLeidiniai().indexOf(leidinys) + " ]: " + leidinys + "\n");
                }
            } else
                leidShowAllOutput.setText("Leidinių sąrašas tuščias");
        }
        else if (event.getSource() == leidShowInfoUpdate) {
            if (!Main.getPasirinktiLeidiniai().getPublications().isEmpty()) {
                for (Leidinys leidinys : Main.getPasirinktiLeidiniai().getPublications()) {
                    leidShowInfoOutput.setText(leidShowInfoOutput.getText() + "Indeksas [ " + Main.getLeidiniai().indexOf(leidinys) + " ]: " + leidinys + "\n");
                }
            } else
                leidShowInfoOutput.setText("Pasirinktų Leidinių sąrašas tuščias");
        }
        else if (event.getSource() == leidShowAllDBUpdate) {
            String type = leidShowAllDBType.getValue().toString();
            String sql;
            if (type.equals("Any")) {
                sql = "SELECT * FROM Leidiniai";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            else if (type.equals("Knyga")) {
                sql = "SELECT * FROM Leidiniai WHERE author IS NOT NULL";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            else {
                sql = "SELECT * FROM Leidiniai WHERE author IS NULL";
                try {
                    preparedStatement = con.prepareStatement(sql);
                    resultSet = preparedStatement.executeQuery();
                } catch (SQLException ex) {
                    System.err.println(ex.getMessage());
                }
            }

            ObservableList<Leidinys> data = FXCollections.observableArrayList();
            while (resultSet.next()) {
                if (resultSet.getString(2) != null)
                    data.add(new Knyga(resultSet.getInt("id"), resultSet.getString(2), resultSet.getString(3), resultSet.getInt("year"), resultSet.getString(5), resultSet.getInt("pageCount")));
                else
                    data.add(new Zurnalas(resultSet.getInt("id"), resultSet.getString(3), resultSet.getInt("year"), resultSet.getString(5), resultSet.getInt("pageCount")));
            }
            if (!data.isEmpty()) {
                leidShowAllDBTable.setEditable(true);
                TableColumn id = new TableColumn("ID");
                id.setMinWidth(10);
                id.setCellValueFactory(new PropertyValueFactory<Leidinys, Integer>("id"));

                TableColumn author = new TableColumn("Author");
                author.setMinWidth(100);
                author.setCellValueFactory(new PropertyValueFactory<Leidinys, String>("autorius"));

                TableColumn name = new TableColumn("Name");
                name.setMinWidth(100);
                name.setCellValueFactory(new PropertyValueFactory<Leidinys, String>("pavadinimas"));

                TableColumn year = new TableColumn("Year");
                year.setMinWidth(10);
                year.setCellValueFactory(new PropertyValueFactory<Leidinys, Integer>("leidimoMetai"));

                TableColumn publisher = new TableColumn("Publisher");
                publisher.setMinWidth(100);
                publisher.setCellValueFactory(new PropertyValueFactory<Leidinys, String>("leidykla"));

                TableColumn pages = new TableColumn("Page count");
                pages.setMinWidth(10);
                pages.setCellValueFactory(new PropertyValueFactory<Leidinys, Integer>("puslapiuSkaicius"));


                leidShowAllDBTable.setItems(data);

                leidShowAllDBTable.getColumns().addAll(id, author, name, year, publisher, pages);

            }
        }
    }

    public LeidiniuController() {
        con = ConnectionUtil.conDB();

    }

    @FXML
    public void initialize() throws Exception {
        if (leidCreateType != null) {
            updateComboBox();
        }
        if (con == null)
            System.out.println("No DB");
    }

}