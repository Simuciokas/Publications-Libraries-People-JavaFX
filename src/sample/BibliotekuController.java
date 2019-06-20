package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class BibliotekuController {

    @FXML
    private Pane menuGoLeid, biblCreate, biblAdd, biblSelect, biblShowAll, biblShowInfo, biblEditAmount, biblDelete;

    @FXML
    private Button biblEditAmountSubmit, biblCreateSubmit, biblShowAllUpdate, biblShowInfoUpdate, biblDeleteSubmit, biblSelectSubmit, biblAddSubmit;

    @FXML
    private TextField biblAuthor, biblName, biblYear, biblPublisher, biblPageCount, biblType, biblAmount;

    @FXML
    private ComboBox biblLeidIndex;

    @FXML
    private TextArea biblEditAmountOutput, biblCreateOutput, biblShowAllOutput, biblShowInfoOutput, biblDeleteOutput, biblSelectOutput, biblAddOutput;

    @FXML
    private TextField biblCreateName, biblDeleteIndex, biblSelectIndex;

    @FXML
    private CheckBox biblCheck;

    @FXML
    private Text biblExit;

    @FXML
    private void initialize() {
        if (biblLeidIndex != null)
            updateComboBox();
    }

    private void updateComboBox() {
        ObservableList data = FXCollections.observableArrayList();
        if (Main.getPasirinktaBiblioteka() != null) {
            for (int i = 0; i < Main.getPasirinktaBiblioteka().getPublications().size(); i++)
                data.add("" + i);
            biblLeidIndex.setItems(data);
            biblLeidIndex.setValue(null);
        }
    }

    //Changes scenes depending on inputted fileName parameter
    private void changeScene(Stage stage, String fileName) throws Exception {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fileName)));
        stage.setScene(scene);
    }

    private void changeText(String author, String name, Integer year, String publisher, Integer pageCount, String type, Integer amount) {
        biblAuthor.setText(author);
        biblName.setText(name);
        biblYear.setText(year.toString());
        biblPublisher.setText(publisher);
        biblPageCount.setText(pageCount.toString());
        biblType.setText(type);
        biblAmount.setText(amount.toString());
    }

    public void handleIndexChange(ActionEvent event) {
        if (event.getSource() == biblLeidIndex) {
            if (biblLeidIndex.getValue() == null || biblLeidIndex.getValue().toString().equals(""))
                changeText("", "", null, "", null, "", null);
            else {
                Leidinys leidinys = Main.getPasirinktaBiblioteka().getPublications().get(Integer.parseInt(biblLeidIndex.getValue().toString()));
                changeText(leidinys.getAutorius(), leidinys.getPavadinimas(), leidinys.getLeidimoMetai(), leidinys.getLeidykla(), leidinys.getPuslapiuSkaicius(), leidinys.getType(), Main.getPasirinktaBiblioteka().getAmountOfPublication(leidinys));
            }
        }
    }

    public void handleButtonAction(MouseEvent event) throws Exception {

        //Needed variables
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        //Biblioteku buttons
        if (event.getSource() == menuGoLeid)
            changeScene(stage,"Leidiniu.fxml");
        else if (event.getSource() == biblCreate)
            changeScene(stage, "bibl/biblCreate.fxml");
        else if (event.getSource() == biblAdd)
            changeScene(stage, "bibl/biblAdd.fxml");
        else if (event.getSource() == biblSelect)
            changeScene(stage, "bibl/biblSelect.fxml");
        else if (event.getSource() == biblShowAll)
            changeScene(stage, "bibl/biblShowAll.fxml");
        else if (event.getSource() == biblShowInfo)
            changeScene(stage, "bibl/biblShowInfo.fxml");
        else if (event.getSource() == biblEditAmount)
            changeScene(stage, "bibl/biblEditAmount.fxml");
        else if (event.getSource() == biblDelete)
            changeScene(stage, "bibl/biblDelete.fxml");
        else if (event.getSource() == biblExit)
            changeScene(stage, "Biblioteku.fxml");

        else if (event.getSource() == biblShowAllUpdate) {
            if (!Main.getBibliotekos().isEmpty())
                for (Biblioteka biblioteka : Main.getBibliotekos())
                    biblShowAllOutput.setText(biblShowAllOutput.getText() + "[" + Main.getBibliotekos().indexOf(biblioteka) + "]\n"
                            + "Pavadinimas: " + biblioteka.pavadinimas.toUpperCase() + "\n"
                            + "Leidiniai: \n" + biblioteka.getPublications()
                            + "\n"
                    );
            else
                biblShowAllOutput.setText("Bibliotekų sąrašas tuščias");
        }
        else if (event.getSource() == biblShowInfoUpdate) {
            if (Main.getPasirinktaBiblioteka() != null) {
                if (!Main.getPasirinktaBiblioteka().getPublications().isEmpty())
                    biblShowInfoOutput.setText("Pavadinimas: " + Main.getPasirinktaBiblioteka().pavadinimas.toUpperCase() + "\n"
                        + "Leidiniai: \n" + Main.getPasirinktaBiblioteka().getPublications()
                        + "\n"
                    );
                else
                    biblShowInfoOutput.setText("Biblioteka tuščia");
            } else
                biblShowInfoOutput.setText("Nepasirinkta biblioteka");
        }

    }

    public void handleSubmitAction(ActionEvent event) {
        if (event.getSource() == biblCreateSubmit) {
            if (biblCreateName.getText().isEmpty())
                biblCreateOutput.setText("Missing Library name input");
            else {
                Biblioteka biblioteka;
                if (biblCheck.isSelected()) {
                    if (!Main.getPasirinktiLeidiniai().isEmpty()) {
                        biblioteka = new Biblioteka(Main.getPasirinktiLeidiniai().getPublications(), biblCreateName.getText());
                        biblCreateOutput.setText("Sekmingai prideta nauja biblioteka su pavadinimu " + biblCreateName.getText() + " ir pasirinktais leidiniais");
                    } else {
                        biblioteka = new Biblioteka(biblCreateName.getText());
                        biblCreateOutput.setText("Sekmingai prideta nauja biblioteka su pavadinimu " + biblCreateName.getText());
                    }
                }
                else {
                    biblioteka = new Biblioteka(biblCreateName.getText());
                    biblCreateOutput.setText("Sekmingai prideta nauja biblioteka su pavadinimu " + biblCreateName.getText());
                }
                List<Biblioteka> bibliotekos = Main.getBibliotekos();
                bibliotekos.add(biblioteka);
                Main.setBibliotekos(bibliotekos);
                Main.setPasirinktaBiblioteka(biblioteka);
            }
        }

        else if (event.getSource() == biblDeleteSubmit) {
            Integer deleteIndex;
            try {
                deleteIndex = Integer.parseInt(biblDeleteIndex.getText());
            } catch (Exception e) {
                biblDeleteOutput.setText("Prašau rašyti skaičių!");
                deleteIndex = -1;
            }

            if (!deleteIndex.equals(-1)) {
                Biblioteka toDelete = null;

                try {
                    toDelete = Main.getBibliotekos().get(deleteIndex);
                } catch (Exception e) {
                    biblDeleteOutput.setText("Biblioteka su tokiu indeksu sąraše nerasta");
                }

                if (toDelete != null) {
                    List<Biblioteka> bibliotekos = Main.getBibliotekos();
                    bibliotekos.remove(toDelete);
                    Main.setBibliotekos(bibliotekos);
                    biblDeleteOutput.setText("Sekmingai ištrinta biblioteka su indeksu " + deleteIndex);
                }
            }
        }

        else if (event.getSource() == biblSelectSubmit) {
            Integer bibliotekosIndex;
            try {
                bibliotekosIndex = Integer.parseInt(biblSelectIndex.getText());
            } catch (Exception e) {
                biblSelectOutput.setText("Prašau rašyti skaičių!");
                bibliotekosIndex = -1;
            }

            if (!bibliotekosIndex.equals(-1)) {
                Biblioteka biblioteka;
                try {
                    biblioteka = Main.getBibliotekos().get(bibliotekosIndex);
                } catch (Exception e) {
                    biblioteka = null;
                    biblSelectOutput.setText("Biblioteka su tokiu indeksu nerasta");
                }

                if (biblioteka != null) {
                    Main.setPasirinktaBiblioteka(biblioteka);
                    biblSelectOutput.setText("Pasirinkta Biblioteka su indeksu " + bibliotekosIndex);
                }
            }
        }

        else if (event.getSource() == biblAddSubmit) {
            if (Main.getPasirinktaBiblioteka() != null) {

                if (!Main.getPasirinktiLeidiniai().isEmpty()) {
                    List<Biblioteka> bibliotekos = Main.getBibliotekos();
                    for (Biblioteka biblioteka : bibliotekos) {
                        if (biblioteka == Main.getPasirinktaBiblioteka()) {
                            biblioteka.addPublications(Main.getPasirinktiLeidiniai().getPublications());
                            Main.setPasirinktaBiblioteka(biblioteka);
                            biblAddOutput.setText("Sekmingai prideti pasirinkti leidiniai i biblioteka");
                        }
                    }
                    Main.setBibliotekos(bibliotekos);
                } else {
                    biblAddOutput.setText("Neturite pasirinke jokiu leidiniu");
                }
            } else {
                if (Main.getBibliotekos().isEmpty())
                    biblAddOutput.setText("Prasau sukurti biblioteka ir ja pasirinkti");
                else
                    biblAddOutput.setText("Prasau pasirinkti biblioteka");
            }
        }

        else if (event.getSource() == biblEditAmountSubmit) {
            if (Main.getPasirinktaBiblioteka() == null)
                biblEditAmountOutput.setText("No selected library");
            else {
                if (Main.getPasirinktaBiblioteka().getPublications().size() == 0)
                    biblEditAmountOutput.setText("Empty library");
                else {
                    if (biblAmount.getText().isEmpty())
                        biblEditAmountOutput.setText("Missing Amount");
                    else {

                        Integer amount;

                        try {
                            amount = Integer.parseInt(biblAmount.getText());
                        } catch (Exception e) {
                            biblEditAmountOutput.setText("Wrong amount");
                            amount = null;
                        }

                        if (amount != null) {

                            Leidinys leidinys = Main.getPasirinktaBiblioteka().getPublications().get(Integer.parseInt(biblLeidIndex.getValue().toString()));

                            for (Biblioteka biblioteka : Main.getBibliotekos()) {
                                if (biblioteka == Main.getPasirinktaBiblioteka()) {
                                    biblioteka.setAmountOfPublication(leidinys, amount);
                                    Main.setPasirinktaBiblioteka(biblioteka);
                                }
                            }

                            biblEditAmountOutput.setText("The amount has been set to " + amount);
                        }

                    }
                }
            }
        }
    }
}
