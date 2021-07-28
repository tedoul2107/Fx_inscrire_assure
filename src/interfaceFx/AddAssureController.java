/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceFx;

import classe.Assure;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import helpers.DbConnect;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ULRICH TEDONGMO
 */
public class AddAssureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    TableAssureController tableassurecontroller;

    String query = null;
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet resultSet = null;
    Assure assure = null;

    private boolean update = false;
    int assureID;
    @FXML
    private JFXTextField nomid;
    @FXML
    private JFXTextField prenomid;
    @FXML
    private JFXDatePicker dateid;
    @FXML
    private JFXTextField adresseid;
    @FXML
    private JFXTextField telephoneid;
    @FXML
    private JFXRadioButton radio_mid;
    @FXML
    private JFXRadioButton radio_fid;

    private ToggleGroup sexeToggleGroup;
    String sexe = "";
    @FXML
    private JFXButton btn_save;
    @FXML
    private JFXButton btn_clean;
    @FXML
    private Text label;

    public void setTableassurecontroller(TableAssureController tableviewcontroller) {
        this.tableassurecontroller = tableviewcontroller;
    }

    void setUpdate(boolean b) {
        this.update = b;
    }

    void setTextField(int num, String nom, String prenom, LocalDate date, String sexe, String adresse, String telephone) {
        assureID = num;
        nomid.setText(nom);
        prenomid.setText(prenom);
        dateid.setValue(date);
        this.sexe = sexe;
        changedButtonradion();
        adresseid.setText(adresse);
        telephoneid.setText(telephone);
    }

    private void getQuery() {

        if (update == false) {
            query = "insert into assure (nom, prenom, date_naissance, sexe, adresse, telephone) values (?,?,?,?,?,?)";
        } else {
            query = "UPDATE assure SET "
                    + "nom=?,"
                    + "prenom=?,"
                    + "date_naissance=?,"
                    + "sexe=?,"
                    + "adresse=?,"
                    + "telephone=? WHERE num ='" + assureID + "'";
        }
    }

    public void message() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);

        if (update == false) {
            alert.setContentText("Enregistrement effectué avec succes");
        } else {
            alert.setContentText("Modification effectuée avec succes");
        }
        alert.showAndWait();
    }

    private void insert() {
        try {

            pst = connection.prepareStatement(query);
            pst.setString(1, nomid.getText());
            pst.setString(2, prenomid.getText());
            pst.setString(3, String.valueOf(dateid.getValue()));
            pst.setString(4, sexe);
            pst.setString(5, adresseid.getText());
            pst.setString(6, telephoneid.getText());
            pst.execute();

        } catch (SQLException e) {
        }
    }

    @FXML
    private void save(ActionEvent event) {

        connection = DbConnect.getConnect();
        String nom = nomid.getText();
        String prenom = prenomid.getText();
        String adresse = adresseid.getText();
        String telephone = telephoneid.getText();
        this.sexe = radioButtonChanged();

        if (nom.isEmpty() || prenom.isEmpty() || dateid.getValue() == null || sexe.isEmpty() || adresse.isEmpty() || telephone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Renseigner tous les champs");
            alert.showAndWait();
        } else {
            getQuery();
            insert();
            message();
            tableassurecontroller.refreshTable();
            ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
        }
    }

    public String radioButtonChanged() {

        if (radio_mid.isSelected()) {
            sexe = "Masculin";
        }
        if (radio_fid.isSelected()) {
            sexe = "Féminin";
        }

        return sexe;
    }

    public void changedButtonradion() {
        if (sexe.equals("Masculin")) {
            radio_mid.setSelected(true);
            radio_fid.setSelected(false);
        } else if (sexe.equals("Féminin")) {
            radio_mid.setSelected(false);
            radio_fid.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sexeToggleGroup = new ToggleGroup();
        this.radio_fid.setToggleGroup(sexeToggleGroup);
        this.radio_mid.setToggleGroup(sexeToggleGroup);
        
        label.setFocusTraversable(true);
    }

    @FXML
    private void clean(ActionEvent event) {

        nomid.setText("");
        prenomid.setText("");
        dateid.setValue(null);
        adresseid.setText("");
        telephoneid.setText("");
        radio_mid.setSelected(false);
        radio_fid.setSelected(false);
    }

}
