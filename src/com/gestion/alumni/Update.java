package com.gestion.alumni;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Update {
        
    public static void update(String request,String input1,String input2,String input3,String input4,String input5,String input6) throws SQLException{
        Connection con = DatabaseConnection.connection();
        
        PreparedStatement stment=con.prepareStatement(request);
        stment.setString(1, input1 );
        stment.setString(2, input2);
        stment.setString(3, input3);
        stment.setString(4, input4);
        stment.setString(5, input5);
        stment.setString(6, input6);
                        
        int rep=stment.executeUpdate();
        if(rep>0){
            Alert alert =new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Mise à jour éffectuée avec succès");
            alert.show();
        }
        
    }
}
