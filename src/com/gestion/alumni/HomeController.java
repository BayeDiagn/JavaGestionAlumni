package com.gestion.alumni;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeController implements Initializable {
    @FXML
    private JFXButton friend;
    @FXML
    private JFXButton profit;
    @FXML
    private JFXButton update;
    @FXML
    private AnchorPane parentPane;
    @FXML
    private JFXListView cursus;
    @FXML
    private JFXListView emploi;
    @FXML
    private JFXListView infos;
    @FXML
    private Label labelEmploi;
    @FXML
    private JFXButton deconnect; 
    @FXML
    private Pane centerPaneProfit;
    @FXML
    private Pane centerPaneUpdate1;
    @FXML
    private Pane centerPaneUpdate2;
    @FXML
    private Pane centerPaneFriend;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField nom;
     @FXML
    private JFXTextField prenom;
     @FXML
    private JFXTextField tel;
     @FXML
    private JFXTextField adress;
    @FXML
    private Label labelCV;
    @FXML
    private JFXTextField textUpdate;
    @FXML
    private JFXButton saveparcours;
    @FXML
    private JFXListView<String>cursusUpdate;
    @FXML
    private JFXListView<String>emploiUpdate;
    @FXML
    private Label champvideParcours;
    
    private Connection con;
    private PreparedStatement stment;
    private ResultSet rst;
    String  value1,value2,value3,value4;
    
    public void switching(ActionEvent e) throws SQLException{
        if(e.getSource().equals(profit)){
            centerPaneProfit.setVisible(true);
            centerPaneUpdate1.setVisible(false);    
            centerPaneFriend.setVisible(false);
            centerPaneUpdate2.setVisible(false);
            labelCV.setVisible(false);
            champvideParcours.setVisible(false);
            
        }
        else 
            if(e.getSource().equals(update)){
               centerPaneUpdate1.setVisible(true);
               centerPaneProfit.setVisible(false); 
               centerPaneFriend.setVisible(false);
               centerPaneUpdate2.setVisible(false);
               
               
               con = DatabaseConnection.connection();
               String select= "select * from Users where codeP=?";
               String codeP=getData.userCodeP;
               stment=con.prepareStatement(select);
               stment.setString(1, codeP);
               rst=stment.executeQuery();
               if(rst.next()){
                   prenom.setText(rst.getString("prenom"));
                   nom.setText(rst.getString("nom"));
                   email.setText(rst.getString("email"));
                   adress.setText(rst.getString("adresse"));
                   tel.setText(rst.getString("tel"));
                   
                   labelCV.setVisible(false);
                   champvideParcours.setVisible(false);
               }
            }
            else 
                if(e.getSource().equals(friend)){
                    centerPaneFriend.setVisible(true);
                    centerPaneProfit.setVisible(false);
                    centerPaneUpdate1.setVisible(false); 
                    centerPaneUpdate2.setVisible(false); 
                    labelCV.setVisible(false);
                    champvideParcours.setVisible(false);
                             
                }
                else{
                    centerPaneUpdate2.setVisible(true);
                    centerPaneUpdate1.setVisible(false);
                    centerPaneProfit.setVisible(false);
                    centerPaneFriend.setVisible(false);
                    labelCV.setVisible(false);
                    champvideParcours.setVisible(false);
                }
        
    }
    
    public void save() throws SQLException{
        String update= "update Users set prenom=?,nom=?,adresse=?,email=?,tel=? where codeP=?";
        String codeP=getData.userCodeP;
        
        if (prenom.getText().isEmpty() || nom.getText().isEmpty()|| adress.getText().isEmpty()|| 
                    email.getText().isEmpty()|| tel.getText().isEmpty()) {
            
                labelCV.setVisible(true);
            }
        else{
           Alert alert;
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
            ButtonType oui=new ButtonType("Oui");
            ButtonType non=new ButtonType("Non");
            alert.getButtonTypes().setAll(oui,non);
            Optional<ButtonType> rep=alert.showAndWait();
            
            if(rep.get()==oui)
                Update.update(update, prenom.getText(), nom.getText(), adress.getText(),
                    email.getText(), tel.getText(),codeP);
             
            infos.getItems().setAll("", "Code Permenant : "+codeP,
                         "Prénom : "+prenom.getText(),"Nom : "+nom.getText(),
                         "Adresse : "+adress.getText(),"E-mail : "+email.getText(),
                         "Téléphone : "+tel.getText() );
            
            labelCV.setVisible(false);
        }     
        
    }

    public void saveParcours(MouseEvent event) throws SQLException{       
        String selectedCurs =  cursusUpdate.getSelectionModel().getSelectedItem();
        int indexCurs=selectedCurs.indexOf(':');    
        String ch1Curs=selectedCurs.substring(indexCurs+1);
        String ch2curs = "";
        if (indexCurs >= 0) {
            ch2curs = selectedCurs.substring(0, indexCurs);
            ch2curs = ch2curs.trim();
        }
        ch1Curs=ch1Curs.trim();
       
        if (ch1Curs != null) 
            textUpdate.setText(ch1Curs);
        
        if(ch2curs.equals("Années de debut")){ 
           int selectedEmpind =  cursusUpdate.getSelectionModel().getSelectedIndex();          
            value1=cursusUpdate.getItems().get(selectedEmpind+1);
            value2=cursusUpdate.getItems().get(selectedEmpind+2);
            value3=cursusUpdate.getItems().get(selectedEmpind+3);
            value4=cursusUpdate.getItems().get(selectedEmpind+4);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();    
           //String select = "select idF from Formation where beginingF=?";
           //int id = myId(select, textUpdate.getText());         
           
           saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Formation set beginingF=? where endF=? and univ=? and niveau=? and diplome=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                           updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                       String editedText = "Années de debut : "+textUpdate.getText();
                       int selectedIndex = cursusUpdate.getSelectionModel().getSelectedIndex();
                       if (selectedIndex >= 0) {
                            cursusUpdate.getItems().set(selectedIndex, editedText);
                            //textUpdate.clear();
                        cursus.getItems().set(selectedIndex,editedText);
                        champvideParcours.setVisible(false);
                        }
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }        
        if(ch2curs.equals("Années de fin")){
            int selectedEmpind =  cursusUpdate.getSelectionModel().getSelectedIndex();          
            value1=cursusUpdate.getItems().get(selectedEmpind-1);
            value2=cursusUpdate.getItems().get(selectedEmpind+1);
            value3=cursusUpdate.getItems().get(selectedEmpind+2);
            value4=cursusUpdate.getItems().get(selectedEmpind+3);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();  
     
           saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Formation set endF=? where beginingF=? and univ=? and niveau=? and diplome=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                           updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                           String editedText = "Années de fin : "+textUpdate.getText();
                           int selectedIndex = cursusUpdate.getSelectionModel().getSelectedIndex();
                           if (selectedIndex >= 0) {
                                cursusUpdate.getItems().set(selectedIndex, editedText);
                                 //textUpdate.clear();
                             cursus.getItems().set(selectedIndex,editedText);
                             champvideParcours.setVisible(false);
                            }
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }                
                }   
                
           });
        }
           
        if(ch2curs.equals("Université")){
            int selectedEmpind =  cursusUpdate.getSelectionModel().getSelectedIndex();          
            value1=cursusUpdate.getItems().get(selectedEmpind-2);
            value2=cursusUpdate.getItems().get(selectedEmpind-1);
            value3=cursusUpdate.getItems().get(selectedEmpind+1);
            value4=cursusUpdate.getItems().get(selectedEmpind+2);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();  
           
            saveparcours.setOnAction((ActionEvent eh)->{
            String req ="update Formation set univ=? where beginingF=? and endF=? and niveau=? and diplome=?";
                              
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                           updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                           String editedText = "Université : "+textUpdate.getText();
                           int selectedIndex = cursusUpdate.getSelectionModel().getSelectedIndex();
                           if (selectedIndex >= 0) {
                               cursusUpdate.getItems().set(selectedIndex, editedText);
                               //textUpdate.clear();
                            cursus.getItems().set(selectedIndex,editedText);
                            champvideParcours.setVisible(false);
                            }
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }
           
        if(ch2curs.equals("Niveau")){
            int selectedEmpind =  cursusUpdate.getSelectionModel().getSelectedIndex();          
            value1=cursusUpdate.getItems().get(selectedEmpind-3);
            value2=cursusUpdate.getItems().get(selectedEmpind-2);
            value3=cursusUpdate.getItems().get(selectedEmpind-1);
            value4=cursusUpdate.getItems().get(selectedEmpind+1);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();  
           
            saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Formation set niveau=? where beginingF=? and endF=? and univ=? and diplome=?";
               
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       if(rep.get().equals(oui)){
                           updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                           String editedText = "Niveau : "+textUpdate.getText();
                           int selectedIndex = cursusUpdate.getSelectionModel().getSelectedIndex();
                           if (selectedIndex >= 0) {
                                cursusUpdate.getItems().set(selectedIndex, editedText);
                                //textUpdate.clear();
                             cursus.getItems().set(selectedIndex,editedText);
                             champvideParcours.setVisible(false);
                            }
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }     
                }   
                
           });
        }
            
        if(ch2curs.equals("Diplôme")){
            int selectedEmpind =  cursusUpdate.getSelectionModel().getSelectedIndex();          
            value1=cursusUpdate.getItems().get(selectedEmpind-4);
            value2=cursusUpdate.getItems().get(selectedEmpind-3);
            value3=cursusUpdate.getItems().get(selectedEmpind-2);
            value4=cursusUpdate.getItems().get(selectedEmpind-1);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();  
                      
            saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Formation set diplome=? where beginingF=? and endF=? and univ=? and niveau=?";
   
               if (textUpdate.getText().isEmpty())
                   champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       if(rep.get().equals(oui)){
                           updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                           String editedText = "diplôme : "+textUpdate.getText();
                           int selectedIndex = cursusUpdate.getSelectionModel().getSelectedIndex();
                           if (selectedIndex >= 0) {
                                cursusUpdate.getItems().set(selectedIndex, editedText);
                                //textUpdate.clear();
                            cursus.getItems().set(selectedIndex,editedText);
                            champvideParcours.setVisible(false);
                            }
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }                 
                }   
                
           });
        }
                  
    } 
    
    public void saveParcoursEmp() throws SQLException{
        String selectedEmp =  emploiUpdate.getSelectionModel().getSelectedItem();
        
        int indexEmp=selectedEmp.indexOf(':');       
        String ch1Emp=selectedEmp.substring(indexEmp+1);
        String ch2Emp = "";
        if (indexEmp >= 0) {
            ch2Emp = selectedEmp.substring(0, indexEmp);
            ch2Emp = ch2Emp.trim();
        }
        
        if (ch1Emp != null) 
            textUpdate.setText(ch1Emp.trim());
        
        if(ch2Emp.equals("Années de debut")){ 
            int selectedEmpind =  emploiUpdate.getSelectionModel().getSelectedIndex();
            //System.out.println(selectedEmpind+" "+ch1Emp);
            value1=emploiUpdate.getItems().get(selectedEmpind+1);
            value2=emploiUpdate.getItems().get(selectedEmpind+2);
            value3=emploiUpdate.getItems().get(selectedEmpind+3);
            value4=emploiUpdate.getItems().get(selectedEmpind+4);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();    
            
           saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Emploi set beginingE=? where endE=? and entreprise=? and typecont=? and poste=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                            updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                            String editedText = "Années de debut : "+textUpdate.getText();
                            int selectedIndex = emploiUpdate.getSelectionModel().getSelectedIndex();
                            if (selectedIndex >= 0) {
                                emploiUpdate.getItems().set(selectedIndex, editedText);
                                
                                emploi.getItems().set(selectedIndex,editedText);
                                champvideParcours.setVisible(false);
                            }  
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }
        if(ch2Emp.equals("Années de fin")){ 
            int selectedEmpind =  emploiUpdate.getSelectionModel().getSelectedIndex();
            value1=emploiUpdate.getItems().get(selectedEmpind-1);
            value2=emploiUpdate.getItems().get(selectedEmpind+1);
            value3=emploiUpdate.getItems().get(selectedEmpind+2);
            value4=emploiUpdate.getItems().get(selectedEmpind+3);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();  
   
           saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Emploi set endE=? where beginingE=? and entreprise=? and typecont=? and poste=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                            updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                            String editedText = "Années de fin : "+textUpdate.getText();
                            int selectedIndex = emploiUpdate.getSelectionModel().getSelectedIndex();
                            if (selectedIndex >= 0) {
                                emploiUpdate.getItems().set(selectedIndex, editedText);
                                
                            emploi.getItems().set(selectedIndex,editedText);
                            champvideParcours.setVisible(false);
                            }  
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }
        if(ch2Emp.equals("Entreprise")){ 
           int selectedEmpind =  emploiUpdate.getSelectionModel().getSelectedIndex();
            value1=emploiUpdate.getItems().get(selectedEmpind-2);
            value2=emploiUpdate.getItems().get(selectedEmpind-1);
            value3=emploiUpdate.getItems().get(selectedEmpind+1);
            value4=emploiUpdate.getItems().get(selectedEmpind+2);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim(); 
            
               saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Emploi set entreprise=? where beginingE=? and endE=? and typecont=? and poste=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                            updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                            String editedText = "Entreprise : "+textUpdate.getText();
                            int selectedIndex = emploiUpdate.getSelectionModel().getSelectedIndex();
                            if (selectedIndex >= 0) {
                                emploiUpdate.getItems().set(selectedIndex, editedText);
                                
                            emploi.getItems().set(selectedIndex,editedText);
                            champvideParcours.setVisible(false);
                            }  
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }
        if(ch2Emp.equals("Type de contrat")){ 
           int selectedEmpind =  emploiUpdate.getSelectionModel().getSelectedIndex();
            //System.out.println(emploiUpdate.getItems().get(selectedEmpind-3));
            value1=emploiUpdate.getItems().get(selectedEmpind-3);
            value2=emploiUpdate.getItems().get(selectedEmpind-2);
            value3=emploiUpdate.getItems().get(selectedEmpind-1);
            value4=emploiUpdate.getItems().get(selectedEmpind+1);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();  
            
           saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Emploi set typecont=? where beginingE=? and endE=? and entreprise=? and poste=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                            updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                            String editedText = "Type de contrat : "+textUpdate.getText();
                            int selectedIndex = emploiUpdate.getSelectionModel().getSelectedIndex();
                            if (selectedIndex >= 0) {
                                emploiUpdate.getItems().set(selectedIndex, editedText);
                                
                            emploi.getItems().set(selectedIndex,editedText);
                            champvideParcours.setVisible(false);
                            }  
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }
        if(ch2Emp.equals("Poste")){ 
           int selectedEmpind =  emploiUpdate.getSelectionModel().getSelectedIndex();
            value1=emploiUpdate.getItems().get(selectedEmpind-4);
            value2=emploiUpdate.getItems().get(selectedEmpind-3);
            value3=emploiUpdate.getItems().get(selectedEmpind-2);
            value4=emploiUpdate.getItems().get(selectedEmpind-1);
            int index1=value1.indexOf(':');
            int index2=value2.indexOf(':');
            int index3=value3.indexOf(':');
            int index4=value4.indexOf(':');
            value1=value1.substring(index1+1).trim(); 
            value2=value2.substring(index2+1).trim();      
            value3=value3.substring(index3+1).trim();      
            value4=value4.substring(index4+1).trim();
           //String select = "select idE from Emploi where poste=?";
           //int id = myIdEmp(select, textUpdate.getText());
           
           saveparcours.setOnAction((ActionEvent eh)->{
               String req ="update Emploi set poste=? where beginingE=? and endE=? and entreprise=? and typecont=?";
                
               if (textUpdate.getText().isEmpty())
                    champvideParcours.setVisible(true);

               else{
                   try {
                       Alert alert;
                       alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setHeaderText(null);
                       alert.setContentText("êtes-vous sûre d'effectuer ces changements ?");
                       ButtonType oui=new ButtonType("Oui");
                       ButtonType non=new ButtonType("Non");
                       alert.getButtonTypes().setAll(oui,non);
                       Optional<ButtonType> rep=alert.showAndWait();
                       
                       if(rep.get().equals(oui)){
                            updateParcours(req,textUpdate.getText(),value1,value2,value3,value4);
                            String editedText = "Poste : "+textUpdate.getText();
                            int selectedIndex = emploiUpdate.getSelectionModel().getSelectedIndex();
                            if (selectedIndex >= 0) {
                                emploiUpdate.getItems().set(selectedIndex, editedText);
                                
                            emploi.getItems().set(selectedIndex,editedText);
                            champvideParcours.setVisible(false);
                            }  
                       }
                       
                    } catch (SQLException ex) {
                        Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                   
                }   
                
           });
        }
    }
    
    public void updateParcours(String requet,String input,String value2,String value3,String value4,String value5) throws SQLException{                       
        PreparedStatement stment=con.prepareStatement(requet);
        stment.setString(1, input );
        stment.setString(2, value2 );
        stment.setString(3, value3 );
        stment.setString(4, value4 );
        stment.setString(5, value5 );
        //stment.setInt(6, id);
         
        int rep=stment.executeUpdate();
        if(rep>0){
            Alert alert =new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Mise à jour éffectuée avec succès");
            alert.show();
        }
    }
     /*public int myId (String request,String input) throws SQLException{
        con = DatabaseConnection.connection();
        int idF = 0;
        stment=con.prepareStatement(request);
        stment.setString(1, input);
        rst=stment.executeQuery();
        if (rst.next()) 
            idF = rst.getInt("idF");
        return idF;   
    }*/
       
    public void ajoutCursus() throws SQLException{
        con = DatabaseConnection.connection();
        Dialog dialog=new Dialog();
        dialog.setTitle("Ajout Curcus");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(paneCursus());
        dialog.getDialogPane().setPrefSize(650, 420);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/Ressources/Css/home.css").toExternalForm());
        dialog.show();
    }
    private Node paneCursus() {
        GridPane gridpane=new GridPane();
        JFXTextField uni=new JFXTextField();
        uni.setPromptText("Etablissement  d'accueil");
        uni.setLabelFloat(true);
        uni.setFocusColor(Color.web("#4059a9"));
        uni.setUnFocusColor(Color.web("#0cbeeb"));
        uni.setPrefSize(240, 37);
        //GridPane.setRowSpan(uni, 20);//uni s'etend de 20 lignes
        uni.setStyle("-fx-font-family: Avenir Book; -fx-font-size:14px");
        
        JFXTextField niv=new JFXTextField();
        niv.setPromptText("Niveau");
        niv.setLabelFloat(true);
        niv.setFocusColor(Color.web("#4059a9"));
        niv.setUnFocusColor(Color.web("#0cbeeb"));
        niv.setPrefSize(260, 37);
        //GridPane.setColumnSpan(niv, 5); 
        niv.setStyle("-fx-font-family: Avenir Book; -fx-font-size:14px;");
        
        JFXTextField dpl=new JFXTextField();
        dpl.setPromptText("Nom du diplôme");
        dpl.setLabelFloat(true);
        dpl.setFocusColor(Color.web("#4059a9"));
        dpl.setUnFocusColor(Color.web("#0cbeeb"));
        dpl.setPrefSize(266, 37);
        dpl.setStyle("-fx-font-family: Avenir Book; -fx-font-size:14px;");
        
        DatePicker datedebut=new DatePicker();
        datedebut.setPromptText("Année de debut");
        datedebut.setPrefSize(210, 30);
        DatePicker datefin=new DatePicker();
        datefin.setPrefSize(210, 40);
        datefin.setPromptText("Année de fin");
        
        JFXButton save =new JFXButton("Enregistrer");
        save.setPrefSize(110, 40);
        save.setId("save");
        Label champ1=new Label("Veuillez renseigner les champs vides !");
        champ1.setVisible(false);
        champ1.setStyle("-fx-text-fill: #ee0f0f;");
        
        
        save.setOnAction(e->{
            String insertcursus="INSERT INTO Formation(`beginingF`, `endF`, `univ`, `niveau`, `diplome`,`codeP`)"
            + " values(?,?,?,?,?,?)";
            String codeP=getData.userCodeP;
            
            if (uni.getText().isEmpty()|| niv.getText().isEmpty()||datedebut.getEditor().getText().isEmpty()
            || datefin.getEditor().getText().isEmpty()|| dpl.getText().isEmpty())             
                champ1.setVisible(true);
            else{                                                                                    
                try {
                    stment=con.prepareStatement(insertcursus);
                    stment.setString(1, datedebut.getEditor().getText().trim());
                    stment.setString(2, datefin.getEditor().getText().trim());
                    stment.setString(3, uni.getText().trim());
                    stment.setString(4, niv.getText().trim());
                    stment.setString(5, dpl.getText().trim());
                    stment.setString(6, codeP);
                        
                    int rep =stment.executeUpdate();
                    if(rep>0){
                        cursus.getItems().addAll("", "Années de debut : "+datedebut.getEditor().getText(),
                         "Années de fin : "+datefin.getEditor().getText(),"Université : "+uni.getText(),
                         "Niveau : "+niv.getText(),"Diplôme : "+dpl.getText());
                        cursusUpdate.getItems().addAll("", "Années de debut : "+datedebut.getEditor().getText(),
                         "Années de fin : "+datefin.getEditor().getText(),"Université : "+uni.getText(),
                         "Niveau : "+niv.getText(),"Diplôme : "+dpl.getText());
                        datedebut.setValue(null);
                        datefin.setValue(null);
                        uni.clear();
                        niv.clear();
                        dpl.clear();
                        
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        
        gridpane.add(uni, 0, 0);
        gridpane.add(niv, 1, 0);
        gridpane.add(dpl, 0, 1);
        gridpane.add(datedebut, 1, 1);
        gridpane.add(datefin, 0, 2);
        gridpane.add(save, 0, 3);
        gridpane.add(champ1, 0, 4);
        gridpane.setPadding(new Insets(85,0,-20,20));
        gridpane.setHgap(90);
        gridpane.setVgap(70);
           
        return gridpane;
           
    }
    
    public void ajoutEmploi() throws SQLException{
        con = DatabaseConnection.connection();
        Dialog dialog=new Dialog();
        dialog.setTitle("Ajout Curcus");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.getDialogPane().setContent(paneEmploi());
        dialog.getDialogPane().setPrefSize(650, 420);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/Ressources/Css/home.css").toExternalForm());
        dialog.show(); 
    }
    private Node paneEmploi() {
        GridPane gridpane=new GridPane();
        JFXTextField entrep=new JFXTextField();
        entrep.setPromptText("Nom de l'entreprise");
        entrep.setLabelFloat(true);
        entrep.setFocusColor(Color.web("#4059a9"));
        entrep.setUnFocusColor(Color.web("#0cbeeb"));
        entrep.setPrefSize(240, 37);
        entrep.setStyle("-fx-font-family: Avenir Book; -fx-font-size:14px");
        
        JFXTextField typec=new JFXTextField();
        typec.setPromptText("Type de contrat");
        typec.setLabelFloat(true);
        typec.setFocusColor(Color.web("#4059a9"));
        typec.setUnFocusColor(Color.web("#0cbeeb"));
        typec.setPrefSize(260, 37);
        typec.setStyle("-fx-font-family: Avenir Book; -fx-font-size:14px;");
        
        JFXTextField poste=new JFXTextField();
        poste.setPromptText("Poste occupé");
        poste.setLabelFloat(true);
        poste.setFocusColor(Color.web("#4059a9"));
        poste.setUnFocusColor(Color.web("#0cbeeb"));
        poste.setPrefSize(266, 37);
        poste.setStyle("-fx-font-family: Avenir Book; -fx-font-size:14px;");
        
        DatePicker datedebut=new DatePicker();
        datedebut.setPromptText("Année de debut");
        datedebut.setPrefSize(210, 30);
        DatePicker datefin=new DatePicker();
        datefin.setPrefSize(210, 40);
        datefin.setPromptText("Année de fin");
        
        JFXButton save =new JFXButton("Enregistrer");
        save.setPrefSize(110, 40);
        save.setId("save");
        Label champ1=new Label("Veuillez renseigner les champs vides !");
        champ1.setVisible(false);
        champ1.setStyle("-fx-text-fill: #ee0f0f;");
        
        
        save.setOnAction(e->{
            String insertemploi="INSERT INTO Emploi(`beginingE`, `endE`, `entreprise`, `typecont`, `poste`,`codeP`)"
            + " values(?,?,?,?,?,?)";
            String codeP=getData.userCodeP;
            
            if (entrep.getText().isEmpty()|| typec.getText().isEmpty()||datedebut.getEditor().getText().isEmpty()
            || datefin.getEditor().getText().isEmpty()|| poste.getText().isEmpty())             
                champ1.setVisible(true);
            else{
                try {
                    stment=con.prepareStatement(insertemploi);
                    stment.setString(1, datedebut.getEditor().getText().trim());
                    stment.setString(2, datefin.getEditor().getText().trim());
                    stment.setString(3, entrep.getText().trim());
                    stment.setString(4, typec.getText().trim());
                    stment.setString(5, poste.getText().trim());
                    stment.setString(6, codeP);
                
                    int rep=stment.executeUpdate();
                    if(rep>0){
                        labelEmploi.setDisable(false);
                        emploi.setDisable(false);
                        emploi.getItems().addAll("", "Années de debut : "+datedebut.getEditor().getText(),
                         "Années de fin : "+datefin.getEditor().getText(),"Entreprise : "+entrep.getText(),
                         "Type de contrat : "+typec.getText(), "Poste : "+poste.getText());
                        emploiUpdate.getItems().addAll("", "Années de debut : "+datedebut.getEditor().getText(),
                         "Années de fin : "+datefin.getEditor().getText(),"Entreprise : "+entrep.getText(),
                         "Type de contrat : "+typec.getText(), "Poste : "+poste.getText());
                        
                        datedebut.setValue(null);
                        datefin.setValue(null);
                        entrep.clear();
                        typec.clear();
                        poste.clear();
                    }
                    
                } catch (SQLException ex) {
                    Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        
        
        gridpane.add(entrep, 0, 0);
        gridpane.add(typec, 1, 0);
        gridpane.add(poste, 0, 1);
        gridpane.add(datedebut, 1, 1);
        gridpane.add(datefin, 0, 2);
        gridpane.add(save, 0, 3);
        gridpane.add(champ1, 0, 4);
        gridpane.setPadding(new Insets(85,0,-20,20));
        gridpane.setHgap(90);
        gridpane.setVgap(70);
           
        return gridpane;
    }
    
    public void deconnected() throws IOException{
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setContentText("Êtes-vous sûr(e) de vouloir vous déconnecter ?");
        ButtonType oui=new ButtonType("Oui");
        ButtonType non =new ButtonType("Non");
        alert.getButtonTypes().setAll(oui,non);
        Optional<ButtonType> rep =alert.showAndWait();
        
        if(rep.get().equals(oui)){
           Parent root = FXMLLoader.load(getClass().getResource("/Ressources/Fxml/MainGA.fxml"));
           Scene scene = new Scene(root);
           Stage stage = new Stage();
           stage.setScene(scene);

           stage.show();
           stage.setResizable(false);
           deconnect.getScene().getWindow().hide(); 
        }
        
    }
     
    public void closed(){
        System.exit(0);
        
    }
    public void reduire(){
       Stage stage = (Stage) parentPane.getScene().getWindow();
       stage.setIconified(true);
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) { 
         try {   
             try {
                 //infos.getItems().addAll(null,"Moussa ","Matar","seck","baye");
                 //System.out.println(getData.userEmail);

                 con = DatabaseConnection.connection();
             } catch (SQLException ex) {
                 Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
             }
             String request1="select * from Users where email=?";
             String request2="select * from Formation where codeP=?";
             String request3="select * from Emploi where codeP=?";
             String email=getData.userEmail;
             String codeP=getData.userCodeP;
             
             //String ch="-----------------------------------------------";
             
             stment=con.prepareStatement(request1);
             stment.setString(1, email);
             rst=stment.executeQuery();
             
             if(rst.next()){
                 infos.getItems().addAll("", "Code Permenant : "+rst.getString("codeP"),
                         "Prénom : "+rst.getString("prenom"),"Nom : "+rst.getString("nom"),
                         "Adresse : "+rst.getString("adresse"),"E-mail : "+rst.getString("email"),
                         "Téléphone : "+rst.getString("tel") );
             }
             
             PreparedStatement stmentf=con.prepareStatement(request2);
             stmentf.setString(1, codeP);
             ResultSet rstf=stmentf.executeQuery();
             //id=new ArrayList<>();
             while(rstf.next()){
                 //getData.id=rstf.getInt("idF");              
                 //id.add(getData.id);
                 
                 cursus.getItems().addAll("", "Années de debut : "+rstf.getString("beginingF"),
                         "Années de fin : "+rstf.getString("endF"),"Université : "+rstf.getString("univ"),
                         "Niveau : "+rstf.getString("niveau"),"Diplôme : "+rstf.getString("diplome"));
                 
                 cursusUpdate.getItems().addAll("", "Années de debut : "+rstf.getString("beginingF"),
                       "Années de fin : "+rstf.getString("endF"),"Université : "+rstf.getString("univ"),
                       "Niveau : "+rstf.getString("niveau"),"Diplôme : "+rstf.getString("diplome"));
             }
             
             PreparedStatement stmente=con.prepareStatement(request3);
             stmente.setString(1, codeP);
             ResultSet rste=stmente.executeQuery();
             
             while(rste.next()){
                 labelEmploi.setDisable(false);
                 emploi.setDisable(false);
                 emploi.getItems().addAll("", "Années de debut : "+rste.getString("beginingE"),
                         "Années de fin : "+rste.getString("endE"),"Entreprise : "+rste.getString("entreprise"),
                         "Type de contrat : "+rste.getString("typecont"), "Poste : "+rste.getString("poste"));
                 
                 
                 emploiUpdate.getItems().addAll("", "Années de debut : "+rste.getString("beginingE"),
                         "Années de fin : "+rste.getString("endE"),"Entreprise : "+rste.getString("entreprise"),
                         "Type de contrat : "+rste.getString("typecont"), "Poste : "+rste.getString("poste"));
        
             }
             
         } catch (SQLException ex) {
             Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
         }
          
      
    }    
}
