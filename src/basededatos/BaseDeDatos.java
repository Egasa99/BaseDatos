/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basededatos;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author PC06
 */
public class BaseDeDatos extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        
        Pane root = new Pane();
        Scene scene = new Scene(root, 512, 512);
        primaryStage.setTitle("Database");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
