package com.example.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HelloController {


        @FXML
        private TextField city;

        @FXML
        private Button getData;

        @FXML
        private Text temp_fells;

        @FXML
        private Text temp_info;

        @FXML
        private Text temp_max;

        @FXML
        private Text temp_min;

        @FXML
        private Text temp_preassure;

    @FXML
    void initialize() {
        getData.setOnAction(Event -> {
            String getUserCity = city.getText().trim();
            if (!getUserCity.isEmpty()) {
                String output = getURLContent("https://api.openweathermap.org/data/2.5/weather?q="+ getUserCity +"&appid=1fa79c6dbd9660d72927f7b1b531f8e2&units=metric");
                System.out.println("Все работает");
 
                if (!output.isEmpty()) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(output);
                        JsonNode mainNode = rootNode.path("main");

                        temp_info.setText("Temp: " + mainNode.path("temp").asInt());
                        temp_fells.setText("Feels: " + mainNode.path("feels_like").asInt());
                        temp_max.setText("Max: " + mainNode.path("temp_max").asInt());
                        temp_min.setText("Min: " + mainNode.path("temp_min").asInt());
                        temp_preassure.setText("Pressure: " + mainNode.path("pressure").asInt());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private static String getURLContent(String urlAddress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAddress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while ((line = bufferedreader.readLine()) != null) {
                content.append(line + "\n");
            }
        } catch (Exception e) {
            System.out.println("Cannot find that city");
        }
        return content.toString();
    }
}
