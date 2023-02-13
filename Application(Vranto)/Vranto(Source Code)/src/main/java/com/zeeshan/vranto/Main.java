package com.zeeshan.vranto;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends Application {
    public String fxmlFile,s1=null;

    public void start1(String a) throws IOException{
        Stage stage=new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(a));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("Theme.css").toExternalForm());
        Path path1 = Path.of("images/logo");
        FileInputStream i=new FileInputStream(String.valueOf(path1));
        stage.getIcons().add(new Image(i));
        stage.setTitle("Vranto.");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws IOException {

        try {
            Path f1 = Path.of("Data\\com.zeeshan.vranto.user.information");
            s1 = Files.readString(f1);
        }
        catch (Exception e){
            System.out.println(e);
        }

        if (s1.equals("")) {
            fxmlFile = "Main.fxml";
        }
        else {
            try{
                Connection c= DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database","root","");
                fxmlFile = "UserPanel.fxml";
            }
            catch (CommunicationsException ce){
                fxmlFile = "main.fxml";
            }
            catch (SQLException e) {
                System.out.println("ERROR :"+e);
            }

        }

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(fxmlFile));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("Theme.css").toExternalForm());
        Path path2=Path.of("images/logo");
        FileInputStream i=new FileInputStream(String.valueOf(path2));
        stage.getIcons().add(new Image(i));
        stage.setTitle("Vranto.");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}