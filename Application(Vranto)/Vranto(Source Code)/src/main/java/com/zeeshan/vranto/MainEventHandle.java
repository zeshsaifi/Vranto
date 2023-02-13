package com.zeeshan.vranto;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.net.URL;
import java.nio.file.Path;
import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;

public class MainEventHandle implements Initializable {

    //         **********$-$-$-$-$-VARIABLES FOR GLOBAL USE-$-$-$-$-$**********
    Image eyeOpen,eyeClose;
    int r,r1;
    String email=null,uservalue=null,passvalue=null;

    //         **********$-$-$-$-$-MAIN PANEL VARIABLES OF FXML-$-$-$-$-$**********
    @FXML
    private Pane mainPanel,forgetPasswordPanel,signupPanel;
    @FXML
    private ImageView logoMain;
    @FXML
    private Label titleMain,slogenMain,loginLabel,showPasswordField,usernameError,passwordError,invalidEmailLabel,invalidOtpLabel,invalidNewpasswordLabel,invalidConformpasswordLabel;
    @FXML
    private TextField usernameField,otpField,forgetPasswordEmailField;
    @FXML
    private PasswordField passwordField,newPassword,conformPassword;
    @FXML
    private Button loginButton,signupLogin,forgetPasswordButton,otpSendButton,otpVerifyButton;
    @FXML
    private ImageView showHidePasswordLogo;
    @FXML
    private CheckBox showHidePasswordChekbox;

    //         **********$-$-$-$-$-SIGNUP PANEL VARIABLES OF FXML-$-$-$-$-$**********

    @FXML
    private TextField signupFirstnameField,signupEmailField,signupPasswordField,signupLastnameField,signupConformPasswordField,signupOtpField;
    @FXML
    private Button signupOtpButton,createAccountButton,signupVerifyOtpButton;
    @FXML
    private Label signupFirstnameInvalidLabel,signupLastnameInvalidLabel,signupEmailInvalidLabel,signupOtpInvalidLabel,signupPasswordInvalidLabel,signupConformPasswordInvalidLabel;

    //         **********$-$-$-$-$-MAIN PANEL ANIMATION PROGRAMS-$-$-$-$-$**********

    @Override
    public void initialize(URL args0, ResourceBundle args1) {

        TranslateTransition tt1 = new TranslateTransition(Duration.seconds(4));
        tt1.setNode(logoMain);
        tt1.setFromY(-200.0);
        tt1.setToY(0.0);
        tt1.play();

        FadeTransition fadeTitle = new FadeTransition(Duration.seconds(3));
        fadeTitle.setNode(titleMain);
        fadeTitle.setFromValue(0.0f);
        fadeTitle.setToValue(1.0f);
        fadeTitle.play();

        FadeTransition fadeSlogen = new FadeTransition(Duration.seconds(3));
        fadeSlogen.setNode(slogenMain);
        fadeSlogen.setFromValue(0.0f);
        fadeSlogen.setToValue(1.0f);
        fadeSlogen.play();

        FadeTransition fadeLoginLabel = new FadeTransition(Duration.seconds(3));
        fadeLoginLabel.setNode(loginLabel);
        fadeLoginLabel.setFromValue(0.0f);
        fadeLoginLabel.setToValue(1.0f);
        fadeLoginLabel.play();

        FadeTransition fadeUser = new FadeTransition(Duration.seconds(3));
        fadeUser.setNode(usernameField);
        fadeUser.setFromValue(0.0f);
        fadeUser.setToValue(1.0f);
        fadeUser.play();

        FadeTransition fadePass = new FadeTransition(Duration.seconds(3));
        fadePass.setNode(passwordField);
        fadePass.setFromValue(0.0f);
        fadePass.setToValue(1.0f);
        fadePass.play();

        FadeTransition fadeLogin = new FadeTransition(Duration.seconds(3));
        fadeLogin.setNode(loginButton);
        fadeLogin.setFromValue(0.0f);
        fadeLogin.setToValue(1.0f);
        fadeLogin.play();

        FadeTransition fadeSignup = new FadeTransition(Duration.seconds(3));
        fadeSignup.setNode(signupLogin);
        fadeSignup.setFromValue(0.0f);
        fadeSignup.setToValue(1.0f);
        fadeSignup.play();

        FadeTransition fadeEye = new FadeTransition(Duration.seconds(3));
        fadeEye.setNode(showHidePasswordLogo);
        fadeEye.setFromValue(0.0f);
        fadeEye.setToValue(1.0f);
        fadeEye.play();

        FadeTransition fadeCheckbox = new FadeTransition(Duration.seconds(3));
        fadeCheckbox.setNode(showHidePasswordChekbox);
        fadeCheckbox.setFromValue(0.0f);
        fadeCheckbox.setToValue(1.0f);
        fadeCheckbox.play();
        showHidePasswordChekbox.setOnAction(this::showHidePassword);

        FadeTransition fadeForgetButton = new FadeTransition(Duration.seconds(3));
        fadeForgetButton.setNode(forgetPasswordButton);
        fadeForgetButton.setFromValue(0.0f);
        fadeForgetButton.setToValue(1.0f);
        fadeForgetButton.play();
    }

    //         **********$-$-$-$-$-SHOW PASSWORD ACTION EVENTS-$-$-$-$-$**********
    public void showHidePassword(ActionEvent e){
        Path path1=Path.of("images\\eyeOpen");
        FileInputStream fp1;
        try {
            fp1=new FileInputStream(String.valueOf(path1));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        eyeOpen=new Image(fp1);

        Path path2=Path.of("images\\eyeClose");
        FileInputStream fp2;
        try {
            fp2=new FileInputStream(String.valueOf(path2));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
        eyeClose=new Image(fp2);

        if(showHidePasswordChekbox.isSelected()){
            showHidePasswordLogo.setImage(eyeOpen);

            showPasswordField.setText(passwordField.getText());
            showPasswordField.setVisible(true);
        } else{
            showHidePasswordLogo.setImage(eyeClose);
            showPasswordField.setVisible(false);
        }
    }

    //         **********$-$-$-$-$-LOGIN ACTION EVENTS-$-$-$-$-$**********
    public void loginAction(ActionEvent e){

        if(usernameField.getText().equals(""))
        {
            usernameError.setText("Please enter username");
            usernameError.setVisible(true);
        }
        else if (usernameField.getText().length()<6)
        {
            usernameError.setText("Username must be 6 character");
            usernameError.setVisible(true);
        }
        else
        {
            usernameError.setVisible(false);
            if(passwordField.getText().equals(""))
            {
                passwordError.setText("Please enter password");
                passwordError.setVisible(true);
            }
            else if(passwordField.getText().length()<8)
            {
                passwordError.setText("Password must be 8 character");
                passwordError.setVisible(true);
            }
            else{
                passwordError.setVisible(false);

                //-----DATABASE CONNECTIVITY FOR LOGIN ACTION-----

                try{
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");
                    Statement stmt= con.createStatement();

                    ResultSet rs = stmt.executeQuery("SELECT `email`,`password` FROM `users` WHERE `email`='"+usernameField.getText()+"'");

                    while (rs.next()) {
                        uservalue= rs.getString(1);
                        passvalue=rs.getString(2);
                    }
                    System.out.println(uservalue+"\n"+passvalue);

                    if(uservalue.equals(usernameField.getText())) {
                        if (passvalue.equals(passwordField.getText())) {

                            try {
                                FileWriter o1 = new FileWriter("Data\\com.zeeshan.vranto.user.information");
                                o1.write(usernameField.getText());
                                System.out.println("'com.zeeshan.vranto.user.information' created.");
                                o1.close();
                            } catch (Exception er) {
                                System.out.println("Creating 'com.zeeshan.vranto.user.information' error :" + er);
                            }

                            //-----CALLING TO USER PANEL-----
                            Main m=new Main();
                            m.start1("UserPanel.fxml");
                        } else {
                            passwordError.setText("Invalid password");
                            passwordError.setVisible(true);
                        }
                    }
                    else{
                        usernameError.setText("Invalid Username");
                        usernameError.setVisible(true);
                    }
                }
                catch (CommunicationsException cm){
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("No internet connection");
                    a.setContentText("There is no Internet Connection.\nPlease connect to Internet.");
                    a.setHeaderText(null);
                    a.show();
                }
                catch (Exception ex){
                    usernameError.setText("Invalid username");
                    usernameError.setVisible(true);
                    System.out.println(ex);
                }

            }
        }
    }

    //         **********$-$-$-$-$-FORGET PASSWORD ACTION EVENTS-$-$-$-$-$**********
    public void forgetPassword(ActionEvent e){
        mainPanel.setDisable(true);
        mainPanel.setOpacity(0.25);
        forgetPasswordPanel.setDisable(false);
        forgetPasswordPanel.setVisible(true);
        otpSendButton.setText("Send OTP");
    }
    public void otpGenerator(ActionEvent e) {
        //OTP generator
        if (forgetPasswordEmailField.getText().equals("")) {
            invalidEmailLabel.setVisible(true);
            invalidEmailLabel.setText("Please enter Email ID");
        } else {
            invalidEmailLabel.setVisible(false);
            otpSendButton.setText("Resend OTP");

            //E-mail sending programme----------------------

            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");
                Statement stmt = con.createStatement();

                ResultSet rs = stmt.executeQuery("SELECT `email` FROM `users` WHERE `email`='" + forgetPasswordEmailField.getText() + "'");

                while(rs.next()) {
                    email = rs.getString(1);
                }

                    if (email.equals(forgetPasswordEmailField.getText()))
                    {
                        Random ren = new Random();
                        r = ren.nextInt(9999);
                        System.out.println(r);

                        Properties properties = System.getProperties();

                        properties.put("mail.smtp.host", "smtp.gmail.com");
                        properties.put("mail.smtp.port", "25");
                        properties.put("mail.smtp.auth", "true");
                        properties.put("mail.smtp.starttls.enable", "true");
                        properties.put("mail.smtp.EnableSSL.enable", "true");

                        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                        properties.setProperty("mail.smtp.port", "465");
                        properties.setProperty("mail.smtp.socketFactory.port", "465");

                        Session session = Session.getInstance(properties, new Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication("kumarprianshu8375@gmail.com", "kbfzleetkemjmhka");
                            }
                        });

                        try {
                            MimeMessage message = new MimeMessage(session);
                            message.setFrom(new InternetAddress("kumarprianshu8375@gmail.com"));
                            message.addRecipient(Message.RecipientType.TO, new InternetAddress(forgetPasswordEmailField.getText()));
                            message.setSubject("Vranto Login OTP");
                            message.setContent("<h2>Your OTP is " + r + ".</h2><h3>Thank you for visiting us.</h3>Regards<br>Vranto.<br>Stay Connected.....", "text/html");

                            System.out.println("e-mail sending..........");
                            Transport.send(message);
                            System.out.println("e-mail sent..........");

                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("E-mail");
                            a.setContentText("E-mail has been sent. Please check your spam session");
                            a.setHeaderText(null);
                            a.show();

                        }
                        catch (MessagingException mex)
                        {
                            mex.printStackTrace();

                            Alert a = new Alert(Alert.AlertType.INFORMATION);
                            a.setTitle("Error");
                            a.setContentText("There is a problem to send E-mail.\nCheck internet connection or\nPlease try again later");
                            a.setHeaderText(null);
                            a.show();
                        }
                    }
            }
            catch (CommunicationsException cm){
                Alert a = new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("No internet connection");
                a.setContentText("There is no Internet Connection.\nPlease connect to Internet.");
                a.setHeaderText(null);
                a.show();
            }
            catch (Exception x) {
                invalidEmailLabel.setText("Invalid e-mail ID");
                invalidEmailLabel.setVisible(true);
            }
        }
    }
    public void verifyOTP(ActionEvent e){
        if(otpField.getText().equals("")){
            invalidOtpLabel.setVisible(true);
            invalidOtpLabel.setText("Please enter OTP");
        }
        else{
            invalidOtpLabel.setVisible(false);
            //OTP checker programme--------------------
            if(otpField.getText().equals(Integer.toString(r))){
                forgetPasswordEmailField.setEditable(false);
                otpField.setEditable(false);
                newPassword.setEditable(true);
                conformPassword.setEditable(true);
                otpSendButton.setDisable(true);
                otpVerifyButton.setDisable(true);
            }
            else{
                invalidOtpLabel.setVisible(true);
                invalidOtpLabel.setText("Invalid OTP");
            }
        }
    }
    public void changePasswordAction(ActionEvent e){
        if(newPassword.getText().equals("") || conformPassword.getText().equals("")){
            invalidNewpasswordLabel.setVisible(true);
            invalidNewpasswordLabel.setText("Please enter password");
            invalidConformpasswordLabel.setVisible(true);
            invalidConformpasswordLabel.setText("Please enter password");
        }
        else if(newPassword.getText().length()<8 || conformPassword.getText().length()<8){
            invalidNewpasswordLabel.setText("Password is too shot");
        }
        else{
            invalidNewpasswordLabel.setVisible(false);
            invalidConformpasswordLabel.setVisible(false);

            if(newPassword.getText().equals(conformPassword.getText())){

                //-----JDBC FOR CHANGE PASSOWRD-----

                try {
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");
                    Statement stmt = con.createStatement();

                    stmt.executeUpdate("UPDATE `users` SET `password`='"+newPassword.getText()+"' WHERE `email`='"+forgetPasswordEmailField.getText()+"'");

                    System.out.println("Password Changed : "+newPassword.getText());

                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Password changed");
                    a.setContentText("New password has been updated");
                    a.setHeaderText(null);
                    a.show();

                    forgetPasswordEmailField.setEditable(true);
                    otpField.setEditable(true);
                    newPassword.setEditable(false);
                    conformPassword.setEditable(false);

                    invalidEmailLabel.setVisible(false);
                    invalidOtpLabel.setVisible(false);
                    invalidNewpasswordLabel.setVisible(false);
                    invalidConformpasswordLabel.setVisible(false);

                    forgetPasswordEmailField.setText("");
                    otpField.setText("");
                    newPassword.setText("");
                    conformPassword.setText("");

                    forgetPasswordPanel.setDisable(true);
                    forgetPasswordPanel.setVisible(false);
                    mainPanel.setVisible(true);
                    mainPanel.setDisable(false);
                    mainPanel.setOpacity(1);

                }
                catch (CommunicationsException cm){
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("No internet connection");
                    a.setContentText("There is no Internet Connection.\nPlease connect to Internet.");
                    a.setHeaderText(null);
                    a.show();
                }
                catch (Exception ex){
                    Alert a=new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("Error");
                    a.setContentText("Something is wrong.\nPlease try again later");
                    a.setHeaderText(null);
                    a.show();
                }
            }
            else{
                Alert a=new Alert(Alert.AlertType.INFORMATION);
                a.setTitle("Password invalid");
                a.setContentText("Both password does not match");
                a.setHeaderText(null);
                a.show();
            }
        }
    }
    public void forgetPasswordExitAction(ActionEvent e){

        forgetPasswordEmailField.setEditable(true);
        otpField.setEditable(true);
        newPassword.setEditable(false);
        conformPassword.setEditable(false);

        invalidEmailLabel.setVisible(false);
        invalidOtpLabel.setVisible(false);
        invalidNewpasswordLabel.setVisible(false);
        invalidConformpasswordLabel.setVisible(false);

        forgetPasswordEmailField.setText("");
        otpField.setText("");
        newPassword.setText("");
        conformPassword.setText("");

        forgetPasswordPanel.setDisable(true);
        forgetPasswordPanel.setVisible(false);
        mainPanel.setVisible(true);
        mainPanel.setDisable(false);
        mainPanel.setOpacity(1);
    }

    //         **********$-$-$-$-$-SIGNUP ACTION EVENTS-$-$-$-$-$**********

    public void signupButton(ActionEvent e){
        mainPanel.setDisable(true);
        mainPanel.setOpacity(0.25);
        signupPanel.setDisable(false);
        signupPanel.setVisible(true);
    }
    public void SignupOtpSend(ActionEvent e){

        if(signupFirstnameField.getText().equals("")){
            signupFirstnameInvalidLabel.setVisible(true);
            signupFirstnameInvalidLabel.setText("Please enter Firstname");
        }
        else{
            signupFirstnameInvalidLabel.setVisible(false);
            signupFirstnameInvalidLabel.setText("");

           if(signupLastnameField.getText().equals("")){
               signupLastnameInvalidLabel.setVisible(true);
               signupLastnameInvalidLabel.setText("Please enter Lastname");
           }
           else{
               signupLastnameInvalidLabel.setVisible(false);
               signupLastnameInvalidLabel.setText("");

               if(signupEmailField.getText().equals("")){
                   signupEmailInvalidLabel.setVisible(true);
                   signupEmailInvalidLabel.setText("Please enter E-mail ID");
               }
               else{
                   signupEmailInvalidLabel.setVisible(false);
                   signupEmailInvalidLabel.setText("");

                   //E-mail sending Programme of Sign-Up

                   Random ren1 = new Random();
                   r1 = ren1.nextInt(9999);
                   System.out.println(r1);

                   Properties properties = System.getProperties();

                   properties.put("mail.smtp.host", "smtp.gmail.com");
                   properties.put("mail.smtp.port", "25");
                   properties.put("mail.smtp.auth", "true");
                   properties.put("mail.smtp.starttls.enable","true");
                   properties.put("mail.smtp.EnableSSL.enable","true");

                   properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                   properties.setProperty("mail.smtp.socketFactory.fallback", "false");
                   properties.setProperty("mail.smtp.port", "465");
                   properties.setProperty("mail.smtp.socketFactory.port", "465");

                   Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
                       protected PasswordAuthentication getPasswordAuthentication() {
                           return new PasswordAuthentication("kumarprianshu8375@gmail.com", "kbfzleetkemjmhka");
                       }
                   });

                   try {
                       MimeMessage message = new MimeMessage(session);
                       message.setFrom(new InternetAddress("kumarprianshu8375@gmail.com"));
                       message.addRecipient(Message.RecipientType.TO, new InternetAddress(signupEmailField.getText()));
                       message.setSubject("Vranto Sign-Up OTP");
                       message.setContent("<h2>Your OTP is "+r1+".</h2><h3>Thank you for visiting us.</h3>Regards<br>Vranto.<br>Stay Connected.....", "text/html");

                       System.out.println("e-mail sending..........");
                       Transport.send(message);
                       System.out.println("e-mail sent..........");

                       Alert a = new Alert(Alert.AlertType.INFORMATION);
                       a.setTitle("E-mail");
                       a.setContentText("E-mail has been sent. Please check your spam session");
                       a.setHeaderText(null);
                       a.show();
                   }
                   catch (MessagingException mex) {
                       mex.printStackTrace();

                       Alert a = new Alert(Alert.AlertType.INFORMATION);
                       a.setTitle("Error");
                       a.setContentText("There is a problem to send E-mail.\nPlease try again later or\nCheck internet connection");
                       a.setHeaderText(null);
                       a.show();
                       signupOtpButton.setText("Resend OTP");
                   }
               }
           }
        }
    }
    public void VerifySignupOtp(ActionEvent e) {
        if (signupOtpField.getText().equals("")) {
            signupOtpInvalidLabel.setVisible(true);
            signupOtpInvalidLabel.setText("Please enter OTP");
        }
        else{
            signupOtpInvalidLabel.setVisible(false);
            signupOtpInvalidLabel.setText("");

            //-----OTP CHECKER PROGRAM-----

            if (signupOtpField.getText().equals(Integer.toString(r1))) {

                signupOtpInvalidLabel.setVisible(false);
                signupOtpInvalidLabel.setText("");

                signupPasswordField.setEditable(true);
                signupConformPasswordField.setEditable(true);

                signupFirstnameField.setEditable(false);
                signupLastnameField.setEditable(false);
                signupEmailField.setEditable(false);
                signupOtpField.setEditable(false);

                createAccountButton.setDisable(false);

                signupOtpButton.setDisable(true);
                signupVerifyOtpButton.setDisable(true);
            }
            else{
                signupOtpInvalidLabel.setVisible(true);
                signupOtpInvalidLabel.setText("Invalid OTP");
            }
        }
    }
    public void CreateAccount(ActionEvent e){
        if(signupPasswordField.getText().equals(""))
        {
            signupPasswordInvalidLabel.setVisible(true);
            signupPasswordInvalidLabel.setText("Please enter password");
        }
        else if(signupPasswordField.getText().length() < 8) {
            signupPasswordInvalidLabel.setText("Password is too shot");
        }
        else{
            signupPasswordInvalidLabel.setVisible(false);
            signupPasswordInvalidLabel.setText("");

            if(signupConformPasswordField.getText().equals("")){
                signupConformPasswordInvalidLabel.setVisible(true);
                signupConformPasswordInvalidLabel.setText("Please enter conform password");
            }
            else if(signupConformPasswordField.getText().equals(signupPasswordField.getText())){

                signupConformPasswordInvalidLabel.setVisible(false);
                signupConformPasswordInvalidLabel.setText("");

                //-----JDBC for CREATING ACCOUNT-----
                try{
                    Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");
                    Statement stmt= con.createStatement();

                    stmt.executeUpdate("INSERT INTO `users`(`firstname`, `lastname`, `email`, `password`,`DOB`) VALUES ('"+signupFirstnameField.getText()+"','"+signupLastnameField.getText()+"','"+signupEmailField.getText()+"','"+signupPasswordField.getText()+"','2000-01-01')");

                    try {
                        FileWriter o1 = new FileWriter("Data\\com.zeeshan.vranto.user.information");
                        o1.write(signupEmailField.getText());
                        System.out.println("File created.");
                        o1.close();
                    }
                    catch (Exception er) {
                        er.getStackTrace();
                    }

                    try {
                        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/vranto_database", "root", "");

                        PreparedStatement pstmt1 = con1.prepareStatement("UPDATE `users` SET `profilephoto`=(?) WHERE `email`='"+signupEmailField.getText()+"'");

                        Path path3=Path.of("Data\\com.zeeshan.vranto.user.information.default.photo");
                        FileInputStream fin = new FileInputStream(String.valueOf(path3));

                        pstmt1.setBinaryStream(1, fin);
                        pstmt1.execute();
                    }
                    catch(Exception err) {
                        System.out.println("ERROR : "+err);
                    }

                    //-----CALLING USER PANEL-----

                    Main m=new Main();
                    m.start1("UserPanel.fxml");
                }
                catch (CommunicationsException cm){
                    Alert a = new Alert(Alert.AlertType.INFORMATION);
                    a.setTitle("No internet connection");
                    a.setContentText("There is no Internet Connection.\nPlease connect to Internet.");
                    a.setHeaderText(null);
                    a.show();
                }
                catch (Exception ex){
                    System.out.println("ERROR = "+ex);
                }
            }
            else{
                signupConformPasswordInvalidLabel.setVisible(true);
                signupConformPasswordInvalidLabel.setText("Both fields are not matching");
            }
        }
    }
    public void signupExitButton(ActionEvent e){

        signupPasswordField.setEditable(false);
        signupConformPasswordField.setEditable(false);

        signupFirstnameField.setEditable(true);
        signupLastnameField.setEditable(true);
        signupEmailField.setEditable(true);
        signupOtpField.setEditable(true);

        signupFirstnameInvalidLabel.setVisible(false);
        signupLastnameInvalidLabel.setVisible(false);
        signupEmailInvalidLabel.setVisible(false);
        signupOtpInvalidLabel.setVisible(false);
        signupPasswordInvalidLabel.setVisible(false);
        signupConformPasswordInvalidLabel.setVisible(false);

        signupFirstnameInvalidLabel.setText("");
        signupLastnameInvalidLabel.setText("");
        signupEmailInvalidLabel.setText("");
        signupOtpInvalidLabel.setText("");
        signupPasswordInvalidLabel.setText("");
        signupConformPasswordInvalidLabel.setText("");

        signupFirstnameField.setText("");
        signupEmailField.setText("");
        signupPasswordField.setText("");
        signupLastnameField.setText("");
        signupConformPasswordField.setText("");
        signupOtpField.setText("");

        createAccountButton.setDisable(true);
        signupOtpButton.setText("Send OTP");
        signupOtpButton.setDisable(false);

        createAccountButton.setDisable(false);
        signupVerifyOtpButton.setDisable(false);

        signupPanel.setDisable(true);
        signupPanel.setVisible(false);
        mainPanel.setVisible(true);
        mainPanel.setDisable(false);
        mainPanel.setOpacity(1);
    }
}