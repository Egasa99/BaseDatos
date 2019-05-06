/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PC06
 */
public class Main extends Application {
    
    private EntityManagerFactory emf;
    private EntityManager em;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
    
        
    StackPane rootMain = new StackPane();
    
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("BaseDatosDiseno.fxml"));
    Pane rootBaseDatosDiseno = fxmlLoader.load();
    rootMain.getChildren().add(rootBaseDatosDiseno);
    
    emf = Persistence.createEntityManagerFactory("BaseDeDatosPU");
    em = emf.createEntityManager();

    BaseDatosDisenoController baseDatosDisenoController = (BaseDatosDisenoController) fxmlLoader.getController();                
    baseDatosDisenoController.setEntityManager(em);
    baseDatosDisenoController.cargarTodosLosUsuarios();
    
    Scene scene = new Scene(rootMain, 512, 374);
    primaryStage.setTitle("Base Datos");
    primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("Database.png")));
    primaryStage.setResizable(true);
    primaryStage.setFullScreenExitHint("");
    primaryStage.setScene(scene);
    primaryStage.show();
    
}


    @Override
    public void stop() throws Exception {
        em.close();
        emf.close();
        try { 
            DriverManager.getConnection("jdbc:derby:Database;shutdown=true"); 
        } catch (SQLException ex) { 
        }  
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
