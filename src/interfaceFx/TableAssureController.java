/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceFx;

import classe.Assure;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import helpers.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author ULRICH TEDONGMO
 */
public class TableAssureController implements Initializable {

    @FXML
    private TableColumn<Assure, String> idCol;
    @FXML
    private TableColumn<Assure, String> nomCol;
    @FXML
    private TableColumn<Assure, String> prenomCol;
    @FXML
    private TableColumn<Assure, String> dateCol;
    @FXML
    private TableColumn<Assure, String> sexeCol;
    @FXML
    private TableColumn<Assure, String> adresseCol;
    @FXML
    private TableColumn<Assure, String> telephoneCol;
    @FXML
    private TableColumn<Assure, String> editCol;
    @FXML
    private TableView<Assure> tableview;

    String query = null;
    Connection connection = null;
    PreparedStatement pst = null;
    ResultSet resultSet = null;
    Assure assure = null;
    
    ObservableList<Assure> AssureList = FXCollections.observableArrayList();
    @FXML
    private JFXTextField txt_search;
    /**
     * Initializes the controller class.
     */
    
    @FXML
    void getAddView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfaceFx/AddAssure.fxml"));
            Parent parent = loader.load();
            AddAssureController AddAssureController = loader.getController();
            AddAssureController.setTableassurecontroller(this);

            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex) {
            Logger.getLogger(TableAssureController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void refreshTable() {

        try {
            AssureList.clear();

            query = "select * from assure";
            pst = connection.prepareStatement(query);
            resultSet = pst.executeQuery();

            while (resultSet.next()) {
                AssureList.add(new Assure(
                        resultSet.getInt("num"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("date_naissance"),
                        resultSet.getString("sexe"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone")));

                tableview.setItems(AssureList);

            }

        } catch (SQLException ex) {
            Logger.getLogger(TableAssureController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    
    private void loadDate() {

        connection = DbConnect.getConnect();
        refreshTable();

        idCol.setCellValueFactory(new PropertyValueFactory<>("num"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date_naissance"));
        sexeCol.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        telephoneCol.setCellValueFactory(new PropertyValueFactory<>("telephone"));

        TableAssureController tableassurecontroller = this;

        Callback<TableColumn<Assure, String>, TableCell<Assure, String>> cellFoctory = (TableColumn<Assure, String> param) -> {
            // make cell containing buttons
            final TableCell<Assure, String> cell = new TableCell<Assure, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);
                        setText(null);

                    } else {

                        FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                        FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.PENCIL_SQUARE);

                        deleteIcon.setStyle(
                                "  -fx-cursor : hand ; "
                                + " -glyph-size :28px ; "
                                + " -fx-fill :#ff1744 ; "
                        );
                        editIcon.setStyle(
                                "  -fx-cursor : hand ; "
                                + " -glyph-size :28px ; "
                                + " -fx-fill :#1e9bfb ; "
                        );

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                            try {
                                assure = tableview.getSelectionModel().getSelectedItem();
                                query = "DELETE FROM assure WHERE num  =" + assure.getNum();
                                connection = DbConnect.getConnect();
                                pst = connection.prepareStatement(query);
                                pst.execute();
                                refreshTable();

                            } catch (SQLException ex) {
                                Logger.getLogger(TableAssureController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        });

                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            assure = tableview.getSelectionModel().getSelectedItem();
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/interfaceFx/AddAssure.fxml"));

                            try {
                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(TableAssureController.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            AddAssureController addAssureController = loader.getController();
                            addAssureController.setTableassurecontroller(tableassurecontroller);

                            addAssureController.setUpdate(true);
                            addAssureController.setTextField(assure.getNum(), assure.getNom(),
                                    assure.getPrenom(), assure.getDate_naissance().toLocalDate(), assure.getSexe(), assure.getAdresse(), assure.getTelephone());
                            
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.showAndWait();

                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle(" -fx-alignment :center ");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);

                        setText(null);

                    }
                }

            };

            return cell;
        };
        editCol.setCellFactory(cellFoctory);
        tableview.setItems(AssureList);

    }
    
    private void SearchName() {

        txt_search.setOnKeyReleased(e -> {

            if (txt_search.getText().isEmpty()) {

                loadDate();

            } else {
                try {
                    AssureList.clear();

                    query = "Select * from assure where nom LIKE '%" + txt_search.getText() + "%'";
                    pst = connection.prepareStatement(query);
                    resultSet = pst.executeQuery();

                    while (resultSet.next()) {
                        AssureList.add(new Assure(
                        resultSet.getInt("num"),
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getDate("date_naissance"),
                        resultSet.getString("sexe"),
                        resultSet.getString("adresse"),
                        resultSet.getString("telephone")));

                tableview.setItems(AssureList);

                    }

                } catch (SQLException ex) {
                    Logger.getLogger(TableAssureController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadDate();
        SearchName();
    }    
    
}
