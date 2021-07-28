/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaceFx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import helpers.DbConnect;
import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ULRICH TEDONGMO
 */
public class AuthentificationController implements Initializable {

    @FXML
    private JFXTextField txt_login;
    @FXML
    private JFXPasswordField txt_pass;
    @FXML
    private JFXButton btn_connexion;
    
    String query = null;
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        txt_pass.setFocusTraversable(false);
        txt_login.setFocusTraversable(false);
    }    

    @FXML
    private void Connection(ActionEvent event) {
        if(getConnect()==0){
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fail");
            alert.setContentText("L'authentification a échouée");
            alert.showAndWait();
            txt_login.setText("");
            txt_pass.setText("");
        }
        else if(getConnect()==1){
            try {
                Parent Add_page_parent = FXMLLoader.load(getClass().getResource("/interfaceFx/TableAssure.fxml"));
                Scene Add_page_scene = new Scene(Add_page_parent);
                Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(Add_page_scene);
                app_stage.show();
            } catch (IOException ex) {
                Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public int getConnect(){
        int result=0;
        try {
            con = DbConnect.getConnect();
            
            pst=con.prepareStatement("select login, password from compte where login=? and password=?");
            pst.setString(1, txt_login.getText());
            pst.setString(2, getEncodedPassword(txt_pass.getText()));
            resultSet = pst.executeQuery();
            
            while(resultSet.next()){
            result = 1;
        }
            
        } catch (SQLException ex) {
            Logger.getLogger(AuthentificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }
    
    private static String getEncodedPassword(String key) {
        byte[] uniqueKey = key.getBytes();
        byte[] hash = null;
        try {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        } catch (NoSuchAlgorithmException e) {
            throw new Error("no MD5 support in this VM");
        }
        StringBuffer hashString = new StringBuffer();
        for (int i = 0; i < hash.length; ++i) {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1) {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            } else {
                hashString.append(hex.substring(hex.length() - 2));
            }
        }
        return hashString.toString();
    }
}
