package facebooklite.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import facebooklite.UserDao;
import facebooklite.User;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegController {
    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField age;

    @FXML
    private TextField userEmail;

    @FXML
    private TextField userName;

    @FXML
    private TextField password;

    @FXML
    private Label registrationSuccessful;

    @FXML
    private Label userNameTaken;

    @FXML
    private void registerUser() throws SQLException {

        boolean fNameProvided = false;
        boolean lNameProvided = false;
        boolean ageProvided = false;
        boolean emailProvided = false;
        boolean unameProvided = false;
        boolean passwordProvided = false;

        if(containsSpaceOrIsEmpty(firstName.getText())){
            firstName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            fNameProvided = false;
        }
        else{
            fNameProvided = true;
            firstName.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(lastName.getText())){
            lastName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            lNameProvided = false;
        }
        else{
            lNameProvided = true;
            lastName.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(age.getText())){
            age.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            ageProvided = false;
        }
        else{
            ageProvided = true;
            age.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(userEmail.getText())){
            userEmail.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            emailProvided = false;
        }
        else{
            emailProvided = true;
            userEmail.setStyle(null);
        }

        if(containsSpaceOrIsEmpty(userName.getText())){
            userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            unameProvided = false;
        }
        else{
            unameProvided = true;
            userName.setStyle(null);
        }
        if(containsSpaceOrIsEmpty(password.getText())){
            password.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
            passwordProvided = false;
        }
        else{
            passwordProvided = true;
            password.setStyle(null);
        }

        if(fNameProvided && lNameProvided && ageProvided && emailProvided && unameProvided && passwordProvided){
            /**Check if user name is Unique (since it is our primary key)*/
            if (UserDao.userExists(userName.getText())){
                userName.setPromptText("User name already exists");
                userName.setStyle("-fx-border-color: red; -fx-border-width: 1px;");
                userNameTaken.setVisible(true);
            }else{
                System.out.println("User does not exist. User will be added");
                User user = new User(firstName.getText(), lastName.getText(), Integer.parseInt(age.getText()), userEmail.getText(), userName.getText(), password.getText());
                UserDao.createUser(user);
                userNameTaken.setVisible(false);
                registrationSuccessful.setVisible(true);

                System.out.println("User added successfully!");
            }
        }

    }

    public void openMainFXML(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent mainFXMLParent = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene mainFXMLScene = new Scene(mainFXMLParent);
        Stage window = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        window.setScene(mainFXMLScene);
        window.show();
    }

    private boolean containsSpaceOrIsEmpty(String text) {
        if(text.contains(" ") || text.equals("")){
            return true;
        }
        return false;
    }

}
