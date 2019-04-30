/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import basededatos.Usuario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * FXML Controller class
 *
 * @author PC06
 */
public class BaseDatosDisenoController implements Initializable {

    private EntityManager entityManager;
    
    @FXML
    private TableView<Usuario> tabla;
    @FXML
    private TableColumn<Usuario,String> columna1;
    @FXML
    private TableColumn<Usuario,String> columna2;
    @FXML
    private TableColumn<Usuario,String> columna3;
    @FXML
    private TableColumn<Usuario,String> columna4;
    @FXML
    private TableColumn<Usuario,String> columna5;
    @FXML
    private TableColumn<Usuario,String> columna6;
    @FXML
    private TableColumn<Usuario,String> columna7;
    @FXML
    private TableColumn<Usuario,String> columna8;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        columna1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna2.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columna3.setCellValueFactory(new PropertyValueFactory<>("trabajo"));
        columna4.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columna5.setCellValueFactory(new PropertyValueFactory<>("email"));
        columna6.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        columna7.setCellValueFactory(new PropertyValueFactory<>("Estado civil"));
        columna8.setCellValueFactory(new PropertyValueFactory<>("Grupo sanguineo"));
        
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void cargarTodosLosUsuarios() {
    Query queryUsuarioFindAll = entityManager.createNamedQuery("Usuario.findAll");
    List<Usuario> listUsuario = queryUsuarioFindAll.getResultList();
    tabla.setItems(FXCollections.observableArrayList(listUsuario));
    }
}
