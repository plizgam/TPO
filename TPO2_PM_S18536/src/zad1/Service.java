/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Currency;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

public class Service {

    String country, city, currency, isoCountry, currencyForCountry;
    double temperature, nbp, rate, pressure;



    public Service(String country){
        this.country = country;
        
        isoCountry = "";
        Locale english = Locale.ENGLISH;


        for (String iso : Locale.getISOCountries()) {
            Locale l = new Locale("", iso);
            if(l.getDisplayName(english).contains(country)){
                isoCountry = iso;
                break;
            }
        }
        
        this.currencyForCountry = Currency.getInstance(new Locale("", isoCountry)).getCurrencyCode();
    }

    String getWeather(String city){
        this.city = city;
        
       
        
        String result = getResponse("https://openweathermap.org/data/2.5/weather?q=" + city + ","+ isoCountry +"&appid=b6907d289e10d714a6e88b30761fae22");
        temperature = new JSONObject(result).getJSONObject("main").getDouble("temp");
        pressure = new JSONObject(result).getJSONObject("main").getDouble("pressure");

        return result;
    }

    Double getRateFor(String currency){
        this.currency = currency;
     
        String response = getResponse("https://api.exchangeratesapi.io/latest?base=" + currencyForCountry);
        return rate = new JSONObject(response).getJSONObject("rates").getDouble(currency);
    }

    Double getNBPRate(){
        String response = getResponse("http://www.nbp.pl/kursy/kursya.html");
        response += getResponse("http://www.nbp.pl/kursy/kursyb.html");

        if(!currencyForCountry.equals("PLN")) {
      
        Pattern compiledPattern = Pattern.compile(currencyForCountry+"</td>[^>]+[^(\\\\d+\\\\.\\\\d+)]+</td>");
        Matcher matcher = compiledPattern.matcher(response);
        matcher.find();
        return nbp = Double.parseDouble(matcher.group().split(">")[2].replace("</td","").replace(",", "."));
        } else {
        	return 0.0;
        }
     }

    String getResponse(String url){
        try {
            URL weatherAPI = new URL(url);
            String readLine;

            HttpURLConnection myConnection = (HttpURLConnection) weatherAPI.openConnection();
            myConnection.setRequestMethod("GET");

            int responseCode = myConnection.getResponseCode();
            StringBuffer response = new StringBuffer();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));

                while ((readLine = in.readLine()) != null)
                    response.append(readLine);

                in.close();
            }

            return response.toString();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        return null;
    }


}  
