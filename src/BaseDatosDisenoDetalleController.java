/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import basededatos.Trabajo;
import basededatos.Usuario;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.RollbackException;

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
    public static final String CASADO = "Casado";
    public static final String SOLTERO = "Soltero";
    private Pane rootBaseDatosDiseno;
    private TableView tableViewAnterior;
    private Usuario usuario;
    private EntityManager entityManager;
    private boolean nuevoUsuario;
    public static final String CARPETA_FOTOS = "Fotos";
    @FXML
    private ComboBox<String> comboBoxGrupoSanguineo;
    @FXML
    private ComboBox<Trabajo> comboBoxTrabajo;
    @FXML
    private ToggleGroup Civil;
    @FXML
    private TextField emailDetalle;
    @FXML
    private RadioButton botonSoltero;
    @FXML
    private RadioButton botonCasado;
    @FXML
    private ImageView fotoView;
       
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

    //Guarda el contenido de la anterior tabla
    public void setTablaAnterior(TableView tableViewAnterior){
        this.tableViewAnterior = tableViewAnterior;
    }
    //Detecta si va a ser un usuario nuevo o uno existente
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
 
   //Limita caracteres
    public static void limitTextField(TextField textField, int limit) {
        UnaryOperator<Change> textLimitFilter = change -> {
            if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
            }
            return change;
        };
        textField.setTextFormatter(new TextFormatter(textLimitFilter));
    } 
    
    
    
    public void mostrarDatos(){
        emailDetalle.setText(usuario.getEmail());
        limitTextField(emailDetalle,30);
        nombreDetalle.setText(usuario.getNombre());
        limitTextField(nombreDetalle,20);
        apellidoDetalle.setText(usuario.getApellidos());
        limitTextField(apellidoDetalle,20);
        telefonodetalle.setText(usuario.getTelefono());
        //Opciones de los grupos sanguíneos
        ArrayList<String> listaGrupoSanguineo = new ArrayList();
        listaGrupoSanguineo.add("A+");
        listaGrupoSanguineo.add("A-");
        listaGrupoSanguineo.add("B+");
        listaGrupoSanguineo.add("B-");
        listaGrupoSanguineo.add("AB+");
        listaGrupoSanguineo.add("AB-");
        listaGrupoSanguineo.add("0+");
        listaGrupoSanguineo.add("0-");

        comboBoxGrupoSanguineo.setItems(FXCollections.observableList(listaGrupoSanguineo));
        if (usuario.getGruposanguineo() != null) {
            comboBoxGrupoSanguineo.setValue(usuario.getGruposanguineo());
        }

        //Convierte el valor de date a datePicker
        if (usuario.getFechanacimiento() != null) {
            Date date = usuario.getFechanacimiento();
            Instant instant = date.toInstant();
            ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
            LocalDate localDate = zdt.toLocalDate();
            fechaDetalle.setValue(localDate);
            
        }
        // Casos Casado soltero
        if (usuario.getEstadocivil() != null) {
            switch (usuario.getEstadocivil()) {
                case CASADO:
                    botonCasado.setSelected(true);
                    break;
                case SOLTERO:
                    botonSoltero.setSelected(true);
                    break;
        }
        }
        //Guarda comboBox Trabajo
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
        
        // Imagen
        if (usuario.getImagen() != null) {
        String imageFileName = usuario.getImagen();
        File file = new File(CARPETA_FOTOS + "/" + imageFileName);
        if (file.exists()) {
            Image image = new Image(file.toURI().toString());
            fotoView.setImage(image);
        } 
        else {
            Alert alert = new Alert(AlertType.INFORMATION, "No se encuentra la imagen");
            alert.showAndWait();
        }
        }
    }
    
        
    @FXML
    private void onActionGuardar(ActionEvent event) {
        int numFilaSeleccionada;
        boolean errorFormato = false;
        if(!errorFormato){
            try{
                StackPane rootMain = (StackPane)rootBaseDatosDetalle.getScene().getRoot();
                rootMain.getChildren().remove(rootBaseDatosDetalle);      
                rootBaseDatosDiseno.setVisible(true);
                //Guardar los String en la Base de Datos y mostrarlos en Diseño
                usuario.setNombre(nombreDetalle.getText());
                usuario.setEmail(emailDetalle.getText());
                usuario.setApellidos(apellidoDetalle.getText());
                usuario.setTelefono(telefonodetalle.getText());
                
                //Guardar RadioButton botonCasado
                if(botonCasado.isSelected()) {
                    usuario.setEstadocivil("Casado");
                } else if(botonSoltero.isSelected()) {
                    usuario.setEstadocivil("Soltero");
                }
                //Guarda el valor seleccionado de comboBox Grupo Sanguineo
                if(comboBoxGrupoSanguineo.getValue() != null) {
                    usuario.setGruposanguineo(comboBoxGrupoSanguineo.getValue());
                }
                //Guarda el valor seleccionado de comboBox Trabajo
                if (comboBoxTrabajo.getValue() != null) {
                        usuario.setTrabajo(comboBoxTrabajo.getValue());
                }
                System.out.println(nuevoUsuario+" Vista detalle");
                //Todos los valores son guardardos en la base de datos
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
                //Guarda la fecha
                if(fechaDetalle.getValue() != null) {
                    LocalDate localDate = fechaDetalle.getValue();
                    ZonedDateTime zonedDateTime = localDate.atStartOfDay(ZoneId.systemDefault());
                    Instant instant = zonedDateTime.toInstant();
                    Date date = Date.from(instant);
                    usuario.setFechanacimiento(date);
                } else {
                    usuario.setFechanacimiento(null);
                }

                entityManager.getTransaction().commit();
                TablePosition pos = new TablePosition(tableViewAnterior, numFilaSeleccionada, null);
                tableViewAnterior.getFocusModel().focus(pos);
                tableViewAnterior.requestFocus();
            }catch (RollbackException ex) { 
            // Los datos introducidos no cumplen los requisitos de la BD
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("No se han podido guardar los cambios. "
                    + "Compruebe que los datos cumplen los requisitos");
            alert.setContentText(ex.getLocalizedMessage());
            alert.showAndWait();
        }

        }
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

    @FXML
    private void onActionExaminar(ActionEvent event) {
        //Inserta una imagen
        if(fotoView.getImage()== null){  
        File carpetaFotos = new File(CARPETA_FOTOS);
            if(!carpetaFotos.exists()) {
                carpetaFotos.mkdir();
            }
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Seleccionar imagen");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imágenes (jpg, png)", "*.jpg", "*.png"),
                    new FileChooser.ExtensionFilter("Todos los archivos", "*.*")
                );
            File file = fileChooser.showOpenDialog(rootBaseDatosDetalle.getScene().getWindow());
            if(file != null) {
                try {
                    Files.copy(file.toPath(), new File(CARPETA_FOTOS + "/"+file.getName()).toPath());
                    usuario.setImagen(file.getName());
                    Image image = new Image(file.toURI().toString());
                    fotoView.setImage(image);
                } catch (FileAlreadyExistsException ex) {
                    Alert alert = new Alert(AlertType.WARNING, "Nombre de archivo duplicado");
                    alert.showAndWait();
                } catch (IOException ex) {
                    Alert alert = new Alert(AlertType.WARNING, "No se ha podido guardar la imagen");
                    alert.showAndWait();
                }
            }
        }
        //Elimina la imagen si existe
        else{
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("ATENCION");
            alert.setHeaderText("Ya existe una imagen. \n"
                    + "Se procederá a eliminar la foto si desea añadir otra");
            alert.setContentText("Elija la opción deseada:");

            ButtonType buttonTypeEliminar = new ButtonType("Suprimir");
            ButtonType buttonTypeCancel = new ButtonType("Cancelar", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeEliminar, buttonTypeCancel);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeEliminar){
                String imageFileName = usuario.getImagen();
                File file = new File(CARPETA_FOTOS + "/" + imageFileName);
                if(file.exists()) {
                    file.delete();
                }
                usuario.setImagen(null);
                fotoView.setImage(null);
            }
        }
    }  
}


