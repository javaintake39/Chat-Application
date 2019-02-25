/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.controllers;

import eg.gov.iti.chatserver.dao.ServerDAO;
import eg.gov.iti.chatserver.dao.UserDAO;
import eg.gov.iti.chatserver.daoImplementation.ServerDAOImplementation;
import eg.gov.iti.chatserver.daoImplementation.UserDAOImplementation;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
//import sun.plugin.javascript.navig.Anchor;

/**
 *
 * @author ahmed
 */
public class statisticsController implements Initializable {
    
    @FXML
    private AnchorPane basePane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        ServerDAO server=new ServerDAOImplementation();
        
        
        
        CategoryAxis xAxis=new CategoryAxis();
        xAxis.setLabel("Users States");
        NumberAxis yAxis=new NumberAxis();
        yAxis.setLabel("Numbers");
        
        XYChart.Series<String,Number> Users_Status=new XYChart.Series<>();
        Users_Status.setName("Users Status");
        Users_Status.getData().addAll(new XYChart.Data<>("online",server.countOnlineUsers()),      
                new XYChart.Data<>("offline",server.countOfflineUsers()));
        
        XYChart.Series<String,Number> Users_Type=new XYChart.Series<>();
        Users_Type.setName("Users Type");
        Users_Type.getData().addAll(new XYChart.Data<>("Males",server.countMales()),
                new XYChart.Data<>("Females",server.countFemales()));
        
        
        
        XYChart.Series<String,Number> Countries_Type=new XYChart.Series<>();
        Countries_Type.setName("Countries");
        Countries_Type.getData().addAll(new XYChart.Data<>("Astrulia",server.countUsersCountry("Astrulia")),
                new XYChart.Data<>("Bolivia",server.countUsersCountry("Bolivia")),new XYChart.Data<>("Bosnia",server.countUsersCountry("Bosnia")),
                new XYChart.Data<>("Brazil",server.countUsersCountry("Brazil")),new XYChart.Data<>("Egypt",server.countUsersCountry("Egypt")),new XYChart.Data<>("USA",server.countUsersCountry("USA")),
                new XYChart.Data<>("Russia",server.countUsersCountry("Russia")),new XYChart.Data<>("Qatar",server.countUsersCountry("Qatar")),
                new XYChart.Data<>("China",server.countUsersCountry("China")),new XYChart.Data<>("Colombia",server.countUsersCountry("Colombia")),
                new XYChart.Data<>("Croatia",server.countUsersCountry("Croatia")),
                new XYChart.Data<>("Denmark",server.countUsersCountry("Denmark")),
                new XYChart.Data<>("France",server.countUsersCountry("France")),
                new XYChart.Data<>("Germany",server.countUsersCountry("Germany")),
                new XYChart.Data<>("Greece",server.countUsersCountry("Greece"))  
                );
        
        
        ObservableList<XYChart.Series<String,Number>> data=FXCollections.observableArrayList();
        data.addAll(Users_Status,Users_Type,Countries_Type);
        BarChart bc= new BarChart(xAxis, yAxis);
        bc.setData(data);
        bc.setTitle("Statistics numbers");
        basePane.getChildren().add(bc);
        
    }    
    
}
