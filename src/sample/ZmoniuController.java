package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.time.YearMonth;

public class ZmoniuController {

    @FXML
    private Button zmgCreateSubmit;

    @FXML
    private TextField zmgName, zmgSurname;

    @FXML
    private Text goBack;

    @FXML
    private TextArea zmgCreateOutput;

    @FXML
    private ComboBox zmgBirthDay, zmgBirthMonth, zmgBirthYear, zmgLibraries, zmgPublications;

    @FXML
    private Pane menuGoLeid, zmgCreate, zmgDelete, zmgShowAll, zmgShowInfo;

    private void changeScene(Stage stage, String fileName) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource(fileName))));
    }

    public void handleButtonAction(MouseEvent event) throws Exception {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (event.getSource() == menuGoLeid)
            changeScene(stage, "Leidiniu.fxml");
        if (event.getSource() == zmgCreate)
            changeScene(stage, "zmg\\zmgCreate.fxml");
        if (event.getSource() == zmgDelete)
            changeScene(stage, "zmg\\zmgDelete.fxml");
        if (event.getSource() == zmgShowAll)
            changeScene(stage, "zmg\\zmgShowAll.fxml");
        if (event.getSource() == zmgShowInfo)
            changeScene(stage, "zmg\\zmgShowInfo.fxml");
        if (event.getSource() == goBack)
            changeScene(stage, "Zmoniu.fxml");

    }

    public void handleSubmitAction(ActionEvent event) {

        if (event.getSource() == zmgCreateSubmit) {
            if (zmgName.getText().isEmpty() || zmgSurname.getText().isEmpty())
                zmgCreateOutput.setText("Empty Name or Surname");
            else {
                String name = zmgName.getText();
                String surname = zmgSurname.getText();
                int birthDay = Integer.parseInt(zmgBirthDay.getValue().toString());
                int birthMonth = Integer.parseInt(zmgBirthMonth.getValue().toString());
                int birthYear = Integer.parseInt(zmgBirthYear.getValue().toString());
                Main.addZmogus(new Zmogus(name, surname, birthDay+"/"+birthMonth+"/"+birthYear));
            }
        }

    }

    public void handleComboBoxAction() {
        updateComboBox();
    }

    private void updateComboBox() {
        ObservableList<Integer> data = FXCollections.observableArrayList();
        System.out.println(zmgBirthYear.getValue());
        int tempDay = zmgBirthDay.getValue() == null ? 1 : Integer.parseInt(zmgBirthDay.getValue().toString());
        int birthMonth = Integer.parseInt(zmgBirthMonth.getValue().toString());
        int birthYear = Integer.parseInt(zmgBirthYear.getValue().toString());
        int days = YearMonth.of(birthYear, birthMonth).lengthOfMonth();
        for (int i = 1; i <= days; i++)
            data.add(i);
        zmgBirthDay.setItems(data);
        zmgBirthDay.setValue(data.contains(tempDay) ? tempDay : data.get(data.size()-1));
    }

    @FXML
    public void initialize() {
        if (zmgBirthDay != null && zmgBirthMonth != null && zmgBirthYear != null) {
            ObservableList<Integer> data = FXCollections.observableArrayList();
            for (int i = 1950; i <= 2019; i++)
                data.add(i);
            zmgBirthYear.setItems(data);
            zmgBirthYear.setValue(1950);
            data = FXCollections.observableArrayList();
            for (int i = 1; i <= 12; i++)
                data.add(i);
            zmgBirthMonth.setItems(data);
            zmgBirthMonth.setValue(1);
            updateComboBox();
        }

        //Visit Library
        if (zmgLibraries != null);
    }

}
