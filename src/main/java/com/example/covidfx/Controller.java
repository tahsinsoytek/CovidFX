package com.example.covidfx;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;


import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable
{
    XYChart.Series series;
    @FXML
    private JFXButton selectButton1;

    @FXML
    private LineChart<String,Integer> lineChart;

    @FXML
    private JFXListView<Country> jfxlistView;

    @FXML
    private JFXButton selectButton;


    @FXML
    private TextField txtField;

    static private URL url1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        selectButton.setDisable(true);
        try
        {
            url1=new URL("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
            txtField.setText("https://opendata.ecdc.europa.eu/covid19/casedistribution/xml/");
            DataReader.getInstance().dataPulling();
        }
        catch (Exception e)
        {
            System.out.println(e);
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("WARNING");
            alert.showAndWait();
        }
    }

    @FXML
    void getDataBtn(ActionEvent event)
    {

        jfxlistView.getItems().setAll(DataReader.getInstance().getCountries());
        jfxlistView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Country>()
        {
            @Override
            public void changed(ObservableValue<? extends Country> observableValue, Country country, Country t1)
            {
                if(jfxlistView.getSelectionModel().getSelectedItem()!=null)
                {
                    selectButton.setDisable(false);
                }
                else
                {
                    selectButton.setDisable(true);
                }
            }
        });
    }
    @FXML
    void selectPressed()
    {
        Country country;
        country=jfxlistView.getSelectionModel().getSelectedItem();
        ObservableList<String> strings=country.getLocalDates();
        ObservableList<Integer> integers=country.getDeathNumbers();
        series=new XYChart.Series();
        try
        {
           for(int i=0;i<strings.size();i++)
           {
                series.getData().add(new XYChart.Data(strings.get(i),integers.get(i)));
           }
            series.setName(country.getName());
            lineChart.getData().add(series);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    @FXML
    void clearPressed()
    {
        lineChart.getData().clear();
    }
    public static URL getUrl()
    {
        return url1;
    }
}