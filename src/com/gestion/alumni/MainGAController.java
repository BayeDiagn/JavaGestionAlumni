package com.gestion.alumni;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Button;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MainGAController implements Initializable {
     @FXML
    private Button connecter;
    @FXML
    private AnchorPane leftpane;
    @FXML
    private AnchorPane rightpane;
    @FXML
    private Pane pane1;
    @FXML
    private Pane pane2;
    @FXML
    private Pane pane3;
    @FXML
    private JFXPasswordField userpassword;
    @FXML
    private JFXTextField usertexfield;
    @FXML
    private JFXTextField adresse;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField nom;
     @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField tel;
    @FXML
    private DatePicker anndebutuni;
    @FXML
    private DatePicker annfinuni;
    @FXML
    private JFXTextField diplome;
    @FXML
    private JFXTextField univ;
     @FXML
    private DatePicker anndebutpro;
     @FXML
    private DatePicker annfinpro;
     @FXML
    private JFXTextField entreprise;
     @FXML
    private JFXTextField typecont;
     @FXML
    private JFXTextField poste;
     @FXML
    private JFXTextField cp;
     @FXML
    private JFXTextField niveau;
     @FXML
    private Label champsvides1;
    @FXML
    private Label champsvides2;
    @FXML
    private Label champsvides3;
    @FXML
    private Label labelemail1;
    @FXML
    private Label labeltel1;
    @FXML
    private Label labelemail2;
    @FXML
    private Label labeltel2;
     @FXML
    private Label labelcodeP;
    @FXML
    private JFXButton suivant2;
    @FXML
    private JFXButton terminer;
    
    private Connection con;
    private PreparedStatement stment;
    private ResultSet rst;

    
    public void switched(int pos,Pane obj1,Pane obj2){
        TranslateTransition transition=new TranslateTransition(Duration.seconds(0.8),obj1);
        transition.setToX(pos);
        transition.play();
        
        transition.setOnFinished(ex->{
                obj1.setVisible(false);
                obj2.setVisible(true);
                obj1.setTranslateX(0);});
   }
    
    public void inscrire(){
       //switched(230, leftpane,pane1);
        TranslateTransition transition=new TranslateTransition(Duration.seconds(1.1),leftpane);
            transition.setToX(350);
            transition.play();
            transition.setOnFinished(ex->{
                rightpane.setVisible(false);
                leftpane.setVisible(false);
                pane1.setVisible(true); 
                leftpane.setTranslateX(0);
        });
    }
   
    String insertuser="INSERT INTO Users(`codeP`, `prenom`, `nom`, `adresse`, `email`, `tel`, `password`)"
            + " values(?,?,?,?,?,?,?)";
    String insertcursus="INSERT INTO Formation(`beginingF`, `endF`, `univ`, `niveau`, `diplome`,`codeP`)"
            + " values(?,?,?,?,?,?)";
    String insertemploi="INSERT INTO Emploi(`beginingE`, `endE`, `entreprise`, `typecont`, `poste`,`codeP`)"
            + " values(?,?,?,?,?,?)";
    
    public void suivant1() throws SQLException{
        con=DatabaseConnection.connection();
        
            if (prenom.getText().isEmpty() || nom.getText().isEmpty()|| adresse.getText().isEmpty()|| 
                    email.getText().isEmpty()|| tel.getText().isEmpty()|| password.getText().isEmpty()|| cp.getText().isEmpty()) {
                champsvides1.setVisible(true);
            }
            else{                                             
                String mailValidate="^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
                String numberValidate= "^(\\+\\d{1,4}|00\\d{1,4}|0)[1-9](\\d{6,12})$|^(\\+\\d{1,4}|00\\d{1,4}|0)[1-9](\\d{6,10})$|"
                        + "^(\\+1)?[2-9][0-9]{9}$|^(\\+\\d{1,4}|00\\d{1,4}|0)[1-9](\\d{6,12})$";
                
                Pattern pattern1 = Pattern.compile(mailValidate);
                Pattern pattern2 = Pattern.compile(numberValidate);
                Matcher check1 = pattern1.matcher(email.getText());
                Matcher check2 = pattern2.matcher(tel.getText());
                
                if((check1.matches())&&(check2.matches())){ 
                    String requet1 = "select * from Users where codeP=?";
                    String requet2 = "select * from Users where email=?";
                    String requet3 = "select * from Users where tel=?"; 
                    
                    PreparedStatement stment1 = con.prepareStatement(requet1);
                    PreparedStatement stment2 = con.prepareStatement(requet2);
                    PreparedStatement stment3 = con.prepareStatement(requet3);
                    stment1.setString(1, cp.getText().trim());
                    stment2.setString(1, email.getText().trim());
                    stment3.setString(1, tel.getText().trim());                           
                    
                    if(!(stment1.executeQuery().next())&&!(stment2.executeQuery().next())&&!(stment3.executeQuery().next())){ 
                        labelcodeP.setVisible(false);
                        labelemail1.setVisible(false);
                        labeltel1.setVisible(false);
                        labelemail2.setVisible(false);
                        labeltel2.setVisible(false);
                        
                        Alert alert;
                        alert = new Alert(AlertType.CONFIRMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Avez-vous bien lu vos informations ?");
                        ButtonType oui=new ButtonType("Oui");
                        ButtonType non=new ButtonType("Non");
                        alert.getButtonTypes().setAll(oui,non);
                        Optional<ButtonType> rep=alert.showAndWait();
                        if(rep.get()==oui){                        
                            stment = con.prepareStatement(insertuser);
                            stment.setString(1, cp.getText().trim());
                            stment.setString(2, prenom.getText().trim());
                            stment.setString(3, nom.getText().trim());
                            stment.setString(4, adresse.getText().trim());
                            stment.setString(5, email.getText().trim());
                            stment.setString(6, tel.getText().trim());
                            stment.setString(7, password.getText().trim());
                            stment.executeUpdate();
                                
                            switched(-610, pane1,pane2);
                            champsvides1.setVisible(false);
                                
                        }
                    } 
                    else{
                        if(stment1.executeQuery().next())
                            labelcodeP.setVisible(true);                                                                            
                        else 
                            labelcodeP.setVisible(false);
                        
                        if(stment2.executeQuery().next()){
                            labelemail2.setVisible(true);
                            labelemail1.setVisible(false);
                        }
                        else
                           labelemail2.setVisible(false);
                        
                        if(stment3.executeQuery().next()){
                            labeltel2.setVisible(true); 
                            labeltel1.setVisible(false);
                        } 
                        else
                            labeltel2.setVisible(false);
                    }
                }
                else{
                    if(!check1.matches()){                    
                        labelemail1.setVisible(true);
                        labelemail2.setVisible(false);
                    }
                    else
                        labelemail1.setVisible(false);
                                                  
                    if(!check2.matches()){                   
                        labeltel1.setVisible(true);
                        labeltel2.setVisible(false);
                    }
                    else
                        labeltel1.setVisible(false);
                         
                }
                                                                                                                                                                                       
            }
    }
    public void suivant2() throws SQLException{                             
        Alert alert;
        alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Avez-vous déjà travaillé en entreprise ?");
        ButtonType oui=new ButtonType("Oui");
        ButtonType non=new ButtonType("Non");
        alert.getButtonTypes().setAll(oui,non);
        Optional<ButtonType> rst=alert.showAndWait();
        if(rst.get()==oui)
            switched(-610, pane2,pane3);
        else{            
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("L'utilisateur a été créé avec succès.");                        
            alert.show();
                
            TranslateTransition transition=new TranslateTransition(Duration.seconds(0.7),pane2);
            transition.setToX(-350);
            transition.play();
            transition.setOnFinished(ex->{
            pane2.setVisible(false); 
            rightpane.setVisible(true);
            leftpane.setVisible(true);
            pane2.setTranslateX(0);});           
                          
            champsvides2.setVisible(false);
            suivant2.setDisable(true);
            terminer.setDisable(true);
            }                        
    }       
    public void ajouter() throws SQLException{        
        /*if(choix.getSelectionModel().getSelectedItem()=="Oui"){*/
        con = DatabaseConnection.connection();
        
        if (univ.getText().isEmpty()|| niveau.getText().isEmpty()||anndebutuni.getEditor().getText().isEmpty()
            || annfinuni.getEditor().getText().isEmpty()|| diplome.getText().isEmpty())             
            champsvides2.setVisible(true);                                                  
                                            
        else{                                                                                    
            stment=con.prepareStatement(insertcursus);
            stment.setString(1, anndebutuni.getEditor().getText().trim());
            stment.setString(2, annfinuni.getEditor().getText().trim());
            stment.setString(3, univ.getText().trim());
            stment.setString(4, niveau.getText().trim());
            stment.setString(5, diplome.getText().trim());
            stment.setString(6, cp.getText().trim());
                        
            int rep = stment.executeUpdate(); 
            if(rep>0){
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("L'ajout a été réalisé avec succès. Si vous en avez d'autres à ajouter, n'hésitez pas ; sinon, vous pouvez cliquer sur \"Suivant\".");                        
                alert.show();
                clearInput();
                suivant2.setDisable(false);
            }            
            //champsvides2.setVisible(false);
                    
            /*LocalDate localdate=anndebutuni.getValue();
            String format="dd/MM/yyyy";
            String formate=localdate.format(DateTimeFormatter.ofPattern(format));
            System.out.print(formate);*/
        }   
    }
    
    public void precedent2(){
        switched(610, pane3,pane2);
    } 
    public void ajouterP() throws SQLException{
        con = DatabaseConnection.connection();
        
        if (entreprise.getText().isEmpty()|| typecont.getText().isEmpty()|| poste.getText().isEmpty()
                 || anndebutpro.getEditor().getText().isEmpty()|| annfinpro.getEditor().getText().isEmpty())
                    champsvides3.setVisible(true);
        else{
            stment=con.prepareStatement(insertemploi);
            stment.setString(1, anndebutpro.getEditor().getText().trim());
            stment.setString(2, annfinpro.getEditor().getText().trim());
            stment.setString(3, entreprise.getText().trim());
            stment.setString(4, typecont.getText().trim());
            stment.setString(5, poste.getText().trim());
            stment.setString(6, cp.getText().trim());
                
            int rep = stment.executeUpdate();
                
            if(rep>0){                    
                Alert alert;
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("L'ajout a été réalisé avec succès. Si vous en avez d'autres à ajouter, n'hésitez pas ; sinon, vous pouvez cliquer sur \"Terminer\".");
                alert.show(); 
                clearInput();
                terminer.setDisable(false);
            }
        }
    }
    public void terminer() throws SQLException{ 
        Alert alert;
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("L'utilisateur a été créé avec succès.");                        
        alert.show();
        
        TranslateTransition transition=new TranslateTransition(Duration.seconds(0.7),pane3);
        transition.setToX(-350);
        transition.play();
        transition.setOnFinished(ex->{
        pane3.setVisible(false); 
        rightpane.setVisible(true);
        leftpane.setVisible(true);
        pane3.setTranslateX(0);});
        
        cp.clear();
        champsvides3.setVisible(false);
        suivant2.setDisable(true);
        terminer.setDisable(true);
    }
        
    public void login() throws SQLException{
        con = DatabaseConnection.connection();

        String requet = "select * from Users where email=? and password=?";

        try {
            stment = con.prepareStatement(requet);
            stment.setString(1, usertexfield.getText());
            stment.setString(2, userpassword.getText());
            rst = stment.executeQuery();
            

            Alert alert;
            if (usertexfield.getText().isEmpty() || userpassword.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Veuillez renseigner les champs vides!!");
                alert.show();
            } else 
                if (rst.next()) {
                    getData.userCodeP=rst.getString("codeP").trim();                  
                    getData.userEmail=usertexfield.getText().trim();
                    
                    if (rst.getString(8).equals("admin")) {
                    //System.out.println("admin welcom!!");
                    Parent root = FXMLLoader.load(getClass().getResource("/Ressources/Fxml/Admin.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                    stage.setResizable(false);
                    connecter.getScene().getWindow().hide();
                    
                } else if(rst.getString(8).equals("user")) {
                    //System.out.println("user welcom!!");

                    Parent root = FXMLLoader.load(getClass().getResource("/Ressources/Fxml/Home.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                    stage.setResizable(false);

                    connecter.getScene().getWindow().hide();
                        //System.out.println(getEmail());
                                             
                        
                  }
                else if(rst.getString(8).equals("personnel")||rst.getString(8).equals("directeur")){
                    Parent root = FXMLLoader.load(getClass().getResource("/Ressources/Fxml/Personnel.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();
                    stage.setScene(scene);

                    stage.show();
                    stage.setResizable(false);

                    connecter.getScene().getWindow().hide();
                }   
                    
                          
                } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Nom d'utilisateur ou mot de passe incorrecte!!");
                alert.show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void clearInput(){
        //cp.clear();
        prenom.clear();
        nom.clear();
        adresse.clear();
        email.clear();
        tel.clear();
        password.clear();
        univ.clear();
        diplome.clear();
        anndebutuni.setValue(null);
        annfinuni.setValue(null);
        entreprise.clear();
        niveau.clear();
        typecont.clear();
        poste.clear();
        anndebutpro.setValue(null);
        annfinpro.setValue(null);
        
    }
    
    /*public String getEmail(){
        
         return this.usertexfield.getText();
        
    }*/
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       // choix.getItems().addAll("Oui","Non");
    }    
    
}
