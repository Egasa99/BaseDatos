/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import basededatos.Usuario;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    private Usuario usuarioSeleccionado;
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
    @FXML
    private AnchorPane rootBaseDatosDiseno;
    @FXML
    private MenuItem cerraraAplicacion;
    @FXML
    private MenuItem addRegistro;
    @FXML
    private MenuItem modifyRegistro;
    @FXML
    private MenuItem borrarRegistro;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tabla.setEditable(true);
        columna1.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columna1.setCellFactory(TextFieldTableCell.<Usuario> forTableColumn());
        columna1.setMinWidth(20);
        columna1.setOnEditCommit((CellEditEvent<Usuario,String>event)->{
            TablePosition<Usuario, String> pos = event.getTablePosition();
            String nombre = event.getNewValue();
            int row = pos.getRow();
            Usuario usuario = event.getTableView().getItems().get(row);
 
            usuario.setNombre(nombre);
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
        });
        columna2.setCellValueFactory(new PropertyValueFactory<>("apellidos"));
        columna2.setCellFactory(TextFieldTableCell.<Usuario> forTableColumn());
        columna2.setMinWidth(20);
        columna2.setOnEditCommit((CellEditEvent<Usuario,String>event)->{
            TablePosition<Usuario, String> pos = event.getTablePosition();
            String apellidos = event.getNewValue();
            int row = pos.getRow();
            Usuario usuario = event.getTableView().getItems().get(row);
 
            usuario.setApellidos(apellidos);
            entityManager.getTransaction().begin();
            entityManager.getTransaction().commit();
        });
        // Trabajo
        columna3.setCellValueFactory(
        cellData -> {
                SimpleStringProperty property = new SimpleStringProperty();
                if (cellData.getValue().getTrabajo() != null) {
                    property.setValue(cellData.getValue().getTrabajo().getNombre());
                }
                return property;
        });	
        columna4.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        columna5.setCellValueFactory(new PropertyValueFactory<>("email"));
        columna6.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        columna7.setCellValueFactory(new PropertyValueFactory<>("Estado civil"));
        columna8.setCellValueFactory(new PropertyValueFactory<>("Grupo sanguineo"));
        
        tabla.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> {
            usuarioSeleccionado = newValue;
        });
        tabla.setOnMouseClicked(new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                if(mouseEvent.getClickCount() == 2){
                    System.out.println("Doble click");
                }
            }
        }
    });
    }
    
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    public void cargarTodosLosUsuarios() {
    Query queryUsuarioFindAll = entityManager.createNamedQuery("Usuario.findAll");
    List<Usuario> listUsuario = queryUsuarioFindAll.getResultList();
    tabla.setItems(FXCollections.observableArrayList(listUsuario));
    }

    @FXML
    private void onActionCerrar(ActionEvent event) {
        System.out.println("Cerrar");
    }

    @FXML
    private void onActionAdd(ActionEvent event) {
        
        System.out.println("Añadir");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseDatosDetalle.fxml"));
            Parent rootBaseDatosDetalle = fxmlLoader.load();
            
            rootBaseDatosDiseno.setVisible(false);
            
            StackPane rootMain = (StackPane)rootBaseDatosDiseno.getScene().getRoot();
            rootMain.getChildren().add(rootBaseDatosDetalle);
            
            BaseDatosDisenoDetalleController baseDatosDisenoDetalleController = (BaseDatosDisenoDetalleController) fxmlLoader.getController(); 
            baseDatosDisenoDetalleController.setRootBaseDatosDiseno(rootBaseDatosDiseno);
            baseDatosDisenoDetalleController.setTablaAnterior(tabla);
            usuarioSeleccionado = new Usuario();
            baseDatosDisenoDetalleController.setUsuario(entityManager, usuarioSeleccionado, true);
            baseDatosDisenoDetalleController.mostrarDatos();
            

        }
        catch (IOException ex){
            Logger.getLogger(BaseDatosDisenoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionModificar(ActionEvent event) {
        System.out.println("Modificar");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseDatosDetalle.fxml"));
            Parent rootBaseDatosDetalle = fxmlLoader.load();
            
            rootBaseDatosDiseno.setVisible(false);
            
            StackPane rootMain = (StackPane)rootBaseDatosDiseno.getScene().getRoot();
            rootMain.getChildren().add(rootBaseDatosDetalle);
            
            BaseDatosDisenoDetalleController baseDatosDisenoDetalleController = (BaseDatosDisenoDetalleController) fxmlLoader.getController();  
            baseDatosDisenoDetalleController.setRootBaseDatosDiseno(rootBaseDatosDiseno);
            baseDatosDisenoDetalleController.setTablaAnterior(tabla);
            baseDatosDisenoDetalleController.setUsuario(entityManager, usuarioSeleccionado, false);
            baseDatosDisenoDetalleController.mostrarDatos();

        }
        catch (IOException ex){
            Logger.getLogger(BaseDatosDisenoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void onActionBorrar(ActionEvent event) {
        System.out.println("Borrar");
        
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmar");
        alert.setHeaderText("¿Desea suprimir el siguiente registro?");
        alert.setContentText("Una vez aceptado no habrá vuelta atrás");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            entityManager.getTransaction().begin();
            entityManager.merge(usuarioSeleccionado);
            entityManager.remove(usuarioSeleccionado);
            entityManager.getTransaction().commit();

            tabla.getItems().remove(usuarioSeleccionado);

            tabla.getFocusModel().focus(null);
            tabla.requestFocus();
        } else {
            int numFilaSeleccionada = tabla.getSelectionModel().getSelectedIndex();
            tabla.getItems().set(numFilaSeleccionada, usuarioSeleccionado);
            TablePosition pos = new TablePosition(tabla, numFilaSeleccionada, null);
            tabla.getFocusModel().focus(pos);
            tabla.requestFocus();   
        }
    }
}
