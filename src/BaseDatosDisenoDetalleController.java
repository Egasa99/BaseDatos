/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import basededatos.Trabajo;
import basededatos.Usuario;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;

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
    private TableView tableViewAnterior;
    private Usuario usuario;
    private EntityManager entityManager;
    private boolean nuevoUsuario;
    @FXML
    private ComboBox<?> ComboBoxGrupoSanguineo;
    @FXML
    private ComboBox<Trabajo> comboBoxTrabajo;
    
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

    public void setTablaAnterior(TableView tableViewAnterior){
        this.tableViewAnterior = tableViewAnterior;
    }
    
    public void setUsuario(EntityManager entityManager, Usuario usuario, boolean nuevoUsuario){
        
        this.nuevoUsuario = nuevoUsuario;
        this.entityManager = entityManager;
        entityManager.getTransaction().begin();
        System.out.println(nuevoUsuario+" SetUsuario");
        if(!nuevoUsuario){
            this.usuario = entityManager.find(Usuario.class,usuario.getIdUsuario());
        }
        else{
            this.usuario = usuario;
        }
    }
    
    public void mostrarDatos(){
        nombreDetalle.setText(usuario.getNombre());
        apellidoDetalle.setText(usuario.getApellidos());
        telefonodetalle.setText(usuario.getTelefono());
        Query queryTrabajoFindAll = entityManager.createNamedQuery("Trabajo.findAll");
        List listTrabajo = queryTrabajoFindAll.getResultList();
        comboBoxTrabajo.setItems(FXCollections.observableList(listTrabajo));
        if(usuario.getTrabajo() != null){
            comboBoxTrabajo.setValue(usuario.getTrabajo());
        }
        comboBoxTrabajo.setCellFactory((ListView<Trabajo> l) -> new ListCell<Trabajo>() {
    @Override
    protected void updateItem(Trabajo trabajo, boolean empty) {
        super.updateItem(trabajo, empty);
        if (trabajo == null || empty) {
            setText("");
        } else {
            setText(trabajo.getNombre());
        }
    }
});
        comboBoxTrabajo.setConverter(new StringConverter<Trabajo>() {
        @Override
        public String toString(Trabajo trabajo) {
            if (trabajo == null) {
                return null;
            } else {
                return trabajo.getNombre();
            }
        }
        @Override
        public Trabajo fromString(String userId) {
            return null;
        }
});
    }
    @FXML
    private void onActionGuardar(ActionEvent event) {
    int numFilaSeleccionada;

    StackPane rootMain = (StackPane)rootBaseDatosDetalle.getScene().getRoot();
    rootMain.getChildren().remove(rootBaseDatosDetalle);      
    rootBaseDatosDiseno.setVisible(true);
    usuario.setNombre(nombreDetalle.getText());
    usuario.setApellidos(apellidoDetalle.getText());
    usuario.setTelefono(telefonodetalle.getText());
    if (comboBoxTrabajo.getValue() != null) {
            usuario.setTrabajo(comboBoxTrabajo.getValue());
    }
    System.out.println(nuevoUsuario+" Vista detalle");
    if(nuevoUsuario) {
        tableViewAnterior.getItems().add(usuario);
        numFilaSeleccionada = tableViewAnterior.getItems().size() - 1;
        tableViewAnterior.getSelectionModel().select(numFilaSeleccionada);
        tableViewAnterior.scrollTo(numFilaSeleccionada);
        entityManager.persist(usuario);
    } else {
        numFilaSeleccionada = tableViewAnterior.getSelectionModel().getSelectedIndex();
        tableViewAnterior.getItems().set(numFilaSeleccionada, usuario);
        entityManager.merge(usuario);
    }
    entityManager.getTransaction().commit();
    TablePosition pos = new TablePosition(tableViewAnterior, numFilaSeleccionada, null);
    tableViewAnterior.getFocusModel().focus(pos);
    tableViewAnterior.requestFocus();
    }

    @FXML
    private void onActionCancelar(ActionEvent event) {
        StackPane rootMain = (StackPane)rootBaseDatosDetalle.getScene().getRoot();
        rootMain.getChildren().remove(rootBaseDatosDetalle);      
        rootBaseDatosDiseno.setVisible(true);
        entityManager.getTransaction().rollback();

        int numFilaSeleccionada = tableViewAnterior.getSelectionModel().getSelectedIndex();
        TablePosition pos = new TablePosition(tableViewAnterior, numFilaSeleccionada, null);
        tableViewAnterior.getFocusModel().focus(pos);
        tableViewAnterior.requestFocus();
    }
    
}
