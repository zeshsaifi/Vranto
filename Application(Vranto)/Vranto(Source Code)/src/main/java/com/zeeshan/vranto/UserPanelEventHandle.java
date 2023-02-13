package com.zeeshan.vranto;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;
public  class UserPanelEventHandle implements Initializable {

    //         **********$-$-$-$-$-VARIABLES FOR GLOBAL USE-$-$-$-$-$**********
    String fn,ln,dob,user=null,sf,path;
    int y=210,y1=0;

    //         **********$-$-$-$-$-USER PANEL VARIABLES OF FXML-$-$-$-$-$**********
    @FXML
    private Label userNameLabel,searchUsername,searchErrorLabel,editFirstnameError,editLastnameError,editDobError,userMessageName;
    @FXML
    private ImageView searchUsernameProfile,userProfilePhoto,editPhotoImageView,userMessageProfile;
    @FXML
    private Button searchButton,editSaveButton;
    @FXML
    private Pane userMainPanel,leftPanel,centerPanel,rightPanel,SearchResultPanel,editProfile,p1,p2,p3;
    @FXML
    private TextField searchingField,editFirstname,editLastname,editEmail,messageField;
    @FXML
    private DatePicker editDob;
    @FXML
    private AnchorPane contactPanel,messagePanel;
    @FXML
    private ScrollPane messageScrollpane,contactScrollpane;
    @FXML
    private Label un1,un2,un3;
    @FXML
    private ImageView up1,up2,up3;

    //         **********$-$-$-$-$-MAIN PANEL ANIMATION PROGRAMS-$-$-$-$-$**********
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //-----Search Button Image Program-----
        Path path1=Path.of("images\\searchLogo");
        FileInputStream fp1;
        try {
            fp1=new FileInputStream(String.valueOf(path1));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        Image img1 = new Image(fp1);
        ImageView searchIcon = new ImageView(img1);
        searchIcon.setFitHeight(20);
        searchIcon.setPreserveRatio(true);

        searchButton.setGraphic(searchIcon);

        //-----Username E-mail-----
        Path p = Path.of("Data\\com.zeeshan.vranto.user.information");
        try {
            user=Files.readString(p);
        }
        catch(IOException e){
            throw new RuntimeException(e);
        }

        //-----PROFILE PHOTO-----

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");

            PreparedStatement p1 = conn.prepareStatement("SELECT `profilephoto` FROM `users` WHERE `email`='"+user+"'");
            ResultSet rs1 = p1.executeQuery();

            byte b[];
            Blob blob;
            while(rs1.next())
            {
                File f1=new File("Data\\com.zeeshan.vranto.user.information.photo");
                FileOutputStream fs=new FileOutputStream(f1);
                blob=rs1.getBlob(1);
                b=blob.getBytes(1, (int)blob.length());
                fs.write(b);
                System.out.println("Image retrieved successfully.");
            }
        }
        catch(Exception err){
            err.printStackTrace();
        }

        Path path2=Path.of("Data\\com.zeeshan.vranto.user.information.photo");
        FileInputStream fp2;
        try {
            fp2=new FileInputStream(String.valueOf(path2));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        Image i1=new Image(fp2);

        userProfilePhoto.setImage(i1);
        editPhotoImageView.setImage(i1);

        //----PROFILE EDITION-----
        try{
            Connection c= DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database","root","");
            Statement st=c.createStatement();
            ResultSet rs=st.executeQuery("SELECT `firstname`,`lastname`,`DOB` FROM `users` WHERE `email`='"+user+"'");
            while (rs.next()){
                fn=rs.getString(1);
                ln=rs.getString(2);
                dob=rs.getString(3);
            }

            userNameLabel.setText(fn+" "+ln);

        }
        catch (Exception e){
            System.out.println("ERROR = "+e);
        }

        editFirstname.setText(fn);
        editLastname.setText(ln);
        editDob.setValue(LocalDate.parse(dob));
        editEmail.setText(user);


        FadeTransition fadeLeftPanel = new FadeTransition(Duration.seconds(3));
        fadeLeftPanel.setNode(leftPanel);
        fadeLeftPanel.setFromValue(0.0f);
        fadeLeftPanel.setToValue(1.0f);
        fadeLeftPanel.play();

        FadeTransition fadeCenterPanel = new FadeTransition(Duration.seconds(3));
        fadeCenterPanel.setNode(centerPanel);
        fadeCenterPanel.setFromValue(0.0f);
        fadeCenterPanel.setToValue(1.0f);
        fadeCenterPanel.play();

        FadeTransition fadeRightPanel = new FadeTransition(Duration.seconds(3));
        fadeRightPanel.setNode(rightPanel);
        fadeRightPanel.setFromValue(0.0f);
        fadeRightPanel.setToValue(1.0f);
        fadeRightPanel.play();

        p1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                userMessageProfile.setImage(up1.getImage());
                userMessageName.setText(un1.getText());
                centerPanel.setDisable(false);
            }
        });
        p1.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                p1.setStyle("-fx-border-color:#ffc801; -fx-border-radius:10px; -fx-background-radius:10px;");
            }
        });
        p1.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                p1.setStyle("-fx-border-color:#ffffff;");
            }
        });

        p2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                userMessageProfile.setImage(up2.getImage());
                userMessageName.setText(un2.getText());
                centerPanel.setDisable(false);
            }
        });
        p2.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                p2.setStyle("-fx-border-color:#ffc801; -fx-border-radius:10px; -fx-background-radius:10px;");
            }
        });
        p2.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                p2.setStyle("-fx-border-color:#ffffff;");
            }
        });

        p3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                userMessageProfile.setImage(up3.getImage());
                userMessageName.setText(un3.getText());
                centerPanel.setDisable(false);
            }
        });
        p3.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                p3.setStyle("-fx-border-color:#ffc801; -fx-border-radius:10px; -fx-background-radius:10px;");
            }
        });
        p3.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                p3.setStyle("-fx-border-color:#ffffff;");
            }
        });
    }

    //         **********$-$-$-$-$-EDIT PROFILE ACTION EVENTS-$-$-$-$-$**********
    public void EditProfile(ActionEvent e){
        userMainPanel.setOpacity(0.25);
        userMainPanel.setDisable(true);
        editProfile.setVisible(true);
        editProfile.setDisable(false);
    }
    public void ChangeProfilePhotoAction(ActionEvent e){

        //FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        File file = fileChooser.showOpenDialog(null);

        path=file.getPath();
        System.out.println(path);

        //JDBC
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");

            PreparedStatement pstmt = con.prepareStatement("UPDATE `users` SET `profilephoto`=(?) WHERE `email`='"+user+"'");

            FileInputStream fin = new FileInputStream(path);

            pstmt.setBinaryStream(1, fin);
            pstmt.execute();

            System.out.println("Data inserted");

            editPhotoImageView.setImage(new Image(path));
            userProfilePhoto.setImage(new Image(path));
        }
        catch(Exception err) {
            System.out.println(err);
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Size Error");
            a.setContentText("Image size must be under 16MB.");
            a.setHeaderText(null);
            a.show();
        }

    }
    public void EditEnableButton(ActionEvent e){
        editFirstname.setDisable(false);
        editLastname.setDisable(false);
        editDob.setDisable(false);
        editSaveButton.setDisable(false);
    }
    public void EditProfileSaveAction(ActionEvent e){
        if(editFirstname.getText().equals("")){
            editFirstnameError.setText(editFirstnameError.getText()+" Please enter firstname");
        }
        else {
            editFirstnameError.setText("Firstname");
            if (editLastname.getText().equals("")){
                editLastnameError.setText(editLastnameError.getText()+" Please enter lastname");
            }
            else {
                editLastnameError.setText("Lastname");
                if (editDob.getValue() == null){
                    editDobError.setText(editDobError.getText()+" Please enter D.O.B.");
                }
                else{
                    editDobError.setText("Date of Birth");
                    editFirstname.setDisable(true);
                    editLastname.setDisable(true);
                    editDob.setDisable(true);
                    editSaveButton.setDisable(true);
                    //JDBC

                    try{
                        Connection c=DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database","root","");
                        Statement st=c.createStatement();
                        st.executeUpdate("UPDATE `users` SET `firstname`='"+editFirstname.getText()+"',`lastname`='"+editLastname.getText()+"',`DOB`='"+editDob.getValue()+"' WHERE `email`='"+user+"'");

                        userNameLabel.setText(editFirstname.getText()+" "+editLastname.getText());

                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Profile Updated");
                        a.setContentText("Your profile has been updated.");
                        a.setHeaderText(null);
                        a.show();
                    }
                    catch (Exception exx){
                        Alert a = new Alert(Alert.AlertType.INFORMATION);
                        a.setTitle("Error");
                        a.setContentText("There is an Error.\nPlease try again after some Time.");
                        a.setHeaderText(null);
                        a.show();
                    }
                }
            }
        }
    }
    public void EditProfileExit(ActionEvent e){
        userMainPanel.setOpacity(1);
        userMainPanel.setDisable(false);
        editProfile.setVisible(false);
        editProfile.setDisable(true);
    }

    //         **********$-$-$-$-$-SEARCHING USER ACTION EVENTS-$-$-$-$-$**********
    public void SearchUser(ActionEvent e){
        if(searchingField.getText().equals("")){
            searchErrorLabel.setText("Please enter e-mail");
            searchErrorLabel.setVisible(true);
        }
        else{
            searchErrorLabel.setVisible(false);

            //JDBC
            try{
                Connection c= DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database","root","");
                Statement st=c.createStatement();
                ResultSet rs=st.executeQuery("SELECT `email` FROM `users` WHERE `email`='"+searchingField.getText()+"'");

                while (rs.next())
                {
                    sf=rs.getString(1);
                }

                if(sf == null){
                    searchErrorLabel.setText("Invalid e-mail");
                    searchErrorLabel.setVisible(true);
                }
                else if (sf.equals(user)) {
                    searchErrorLabel.setText("This is you");
                    searchErrorLabel.setVisible(true);
                }
                else {
                    try{
                        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");

                        PreparedStatement p1 = conn.prepareStatement("SELECT `profilephoto` FROM `users` WHERE `email`='"+searchingField.getText()+"'");
                        ResultSet rs1 = p1.executeQuery();

                        byte b[];
                        Blob blob;
                        while(rs1.next())
                        {
                            File f1=new File("Data\\com.zeeshan.vranto.user.information.search.photo");
                            FileOutputStream fs=new FileOutputStream(f1);
                            blob=rs1.getBlob(1);
                            b=blob.getBytes(1, (int)blob.length());
                            fs.write(b);
                            System.out.println("Image retrieved successfully.");
                        }
                    }
                    catch(Exception err){
                        err.printStackTrace();
                    }
                    Path path3=Path.of("Data\\com.zeeshan.vranto.user.information.search.photo");
                    FileInputStream fp3;
                    try {
                        fp3=new FileInputStream(String.valueOf(path3));
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }
                    Image i=new Image(fp3);
                    searchUsernameProfile.setImage(i);
                    searchUsername.setText(sf);
                    SearchResultPanel.setVisible(true);

                    sf=null;

                }
            }
            catch (Exception er){
                searchErrorLabel.setText("Invalid e-mail");
                searchErrorLabel.setVisible(true);
                System.out.println(er);
            }
        }
    }
    public void ClearSearchField(ActionEvent e){
        searchingField.setText("");
        searchUsername.setText("");
        SearchResultPanel.setVisible(false);
    }
    public void AddUserAction(ActionEvent e) {
        Path path4=Path.of("Data\\com.zeeshan.vranto.user.information.search.photo");
        FileInputStream fp4;
        try {
            fp4=new FileInputStream(String.valueOf(path4));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        ImageView iv = new ImageView(new Image(fp4));
        iv.setFitWidth(50);
        iv.setFitHeight(50);
        iv.setLayoutX(10);
        iv.setLayoutY(5);

        String f = null, l = null;
        try {
            Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT `firstname`, `lastname`FROM `users` WHERE `email`='" + searchUsername.getText() + "'");
            while (rs.next()) {
                f = rs.getString(1);
                l = rs.getString(2);
            }
        } catch (Exception exx) {
            System.out.println("ERROR : " + exx);
        }

        Label sun = new Label(f + " " + l);
        sun.setFont(new Font("ARIAL", 24));
        sun.setLayoutX(70);
        sun.setLayoutY(0);
        sun.setPrefWidth(260);
        sun.setPrefHeight(60);

        Pane p = new Pane(iv, sun);
        p.setPrefWidth(330);
        p.setPrefHeight(60);
        p.setLayoutX(10);
        p.setLayoutY(y);

        p.setOnMouseEntered(mouseEvent -> p.setStyle("-fx-border-color:#ffc801; -fx-border-radius:10px; -fx-background-radius:10px;"));
        p.setOnMouseExited(mouseEvent -> p.setStyle("-fx-border-color:#ffffff;"));
        p.setOnMouseClicked(mouseEvent -> {
            centerPanel.setDisable(false);
        });

        contactPanel.getChildren().add(p);
        contactScrollpane.setContent(contactPanel);
        y += 70;
    }

    //         **********$-$-$-$-$-SEND MESSAGING ACTION EVENTS-$-$-$-$-$**********
    public void SendMessageAction(ActionEvent e) {

        if (messageField.getText() == null) {
            System.out.println("Null");
        }
        else {
        Label l = new Label(messageField.getText());


        l.setStyle("-fx-border-color:#ffc801; -fx-border-radius:10px; -fx-background-radius:10px; -fx-padding:5px;");
        l.setFont(new Font("ARIAL",14));

        Pane p=new Pane(l);

        p.setLayoutX(10);
        p.setLayoutY(y1);

        messagePanel.getChildren().add(p);

        messageScrollpane.setContent(messagePanel);
        messageField.setText(null);
        y1+=30;
        }
    }

    //         **********$-$-$-$-$-LOGOUT USER ACTION EVENTS-$-$-$-$-$**********
    public void LogoutAction(ActionEvent e) throws IOException {
        try {
            FileWriter o1 = new FileWriter("Data\\com.zeeshan.vranto.user.information");
            o1.write("");
            System.out.println("File created.");
            o1.close();
        }
        catch (Exception er) {
            er.getStackTrace();
        }
        Main m=new Main();
        m.start1("Main.fxml");
    }
}
