package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


public class Controller {

    private String api_key = "enter-your-api-key";

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text temp_feel;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    void initialize() {
        getData.setOnAction(e -> {
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) {
                String output = getUrlContent("https://api.openweathermap.org/data/2.5/weather?q=" + getUserCity + "&appid=" + api_key);
                
                if(!output.isEmpty()) {
                    JSONObject obj = new JSONObject(output);
                    temp.setText(calvinToCelsius(obj.getJSONObject("main").getDouble("temp")) + "\u00B0");
                    temp_feel.setText(calvinToCelsius(obj.getJSONObject("main").getDouble("feels_like")) + "\u00B0");
                    temp_min.setText(calvinToCelsius(obj.getJSONObject("main").getDouble("temp_min")) + "\u00B0");
                    temp_max.setText(calvinToCelsius(obj.getJSONObject("main").getDouble("temp_max")) + "\u00B0");
                }
            }
        });
    }

    private static int calvinToCelsius(double calvin_temp) {
        int celsius_temp = (int) (calvin_temp - 273.15);
        return celsius_temp;
    }

    private static String getUrlContent(String urlAdress) {
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConn = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String line;

            while((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();

        } catch(Exception e) {
            System.out.println("No such city has been found :(");
        }
        return content.toString();
    }

}
