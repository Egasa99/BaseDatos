/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author PC06
 */
public class BaseDatosDisenoDetalleController implements Initializable {

    @FXML
    private AnchorPane rootBaseDatosDetalle;
    @FXML
    private TextField nombreDetalle;
    @FXML
    private TextField apellidoDetalle;
    @FXML
    private TextField telefonodetalle;
    @FXML
    private DatePicker fechaDetalle;
    @FXML
    private Button examinarDetalle;
    @FXML
    private Button botonGuardarDetalle;
    @FXML
    private Button cancelarDetalle;
    
    private Pane rootBaseDatosDiseno;

    public void setRootBaseDatosDiseno(Pane rootBaseDatosDiseno) {
    this.rootBaseDatosDiseno = rootBaseDatosDiseno;
}
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onActionGuardar(ActionEvent event) {
    StackPane rootMain = (StackPane)rootBaseDatosDetalle.getScene().getRoot();
    rootMain.getChildren().remove(rootBaseDatosDetalle);      

    rootBaseDatosDiseno.setVisible(true);
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootBaseDatosDetalle.getScene().getRoot();
        rootMain.getChildren().remove(rootBaseDatosDetalle);      

        rootBaseDatosDiseno.setVisible(true);
    }
    
}
