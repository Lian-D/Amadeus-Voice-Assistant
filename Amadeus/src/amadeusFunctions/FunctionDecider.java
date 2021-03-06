package amadeusFunctions;
import amadeusFunctions.exceptions.nonAlphanumericalException;
import amadeusFunctions.exceptions.nullDataException;
import amadeusFunctions.lookUp.WeatherFunction;
import amadeusFunctions.lookUp.WikiSearch;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;;
import static org.apache.commons.lang3.text.WordUtils.capitalizeFully;

public class FunctionDecider extends Observable{
/**
 * Code made by Lian-D 2018 for Amadeus-Voice-Assistant
 * This class takes a String and decides based on substrings which function and how to respond.
 *@Author Lian Duan
 * */

    public static String decideFunctionality(String input) {
        String result = null;

        //These are words that classify the String as a function that searches for weather
        if (input.toLowerCase().contains("when is your birthday") || input.toLowerCase().contains("when were you")) {
            result = "September 16 2018";
        }
        else if (input.toLowerCase().contains("what is the date")) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            result = "The date is:" +dateFormat.format(date);
        }
        else if (input.toLowerCase().contains("what is the time")) {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:");
            Date date = new Date();
            result = "The time is:" +dateFormat.format(date);
        }
        else if (input.toLowerCase().contains("what is your name")) {
            result = "My name is Amadeus";
        }
        else if (input.toLowerCase().contains("weather") && (input.toLowerCase().contains("what") || (input.toLowerCase().contains("whats")))){
            //Instantiates the Weather Function
            WeatherFunction weather = new WeatherFunction();
            weather.callClass();
            result = weather.filterForecast(weather.getWeatherLocal());
        }
        //These are words that classify the String as a function that searches on wikipedia
        else if (input.toLowerCase().contains("what is") || (input.toLowerCase().contains("who is")) || (input.toLowerCase().contains("whose") || (input.toLowerCase().contains("tell me about")))){

            //Removes command words
            String topic = input.replaceAll("what is","");
            topic = topic.replaceAll("tell me about","");
            topic = topic.replaceAll("Tell me about","");
            topic = topic.replaceAll("What is","");
            topic = topic.replaceAll("who is","");
            topic = topic.replaceAll("Who is","").trim();
            topic = capitalizeFully(topic);
            //Instantiates The Wiki Search
            WikiSearch wiki = new WikiSearch();
            try {
                result =  wiki.parseWikiJson(wiki.aquireJsonfromWeb(topic));
            } catch (IOException e) {
                result = "I've run into an error";
            }
        }
        else if (input.toLowerCase().contains("how are you")){
            result = "I'm good, thank you for asking";
        }
        else if (input.toLowerCase().contains("who made you")){
            result = "Lian Duan, in his second year at University of British Columbia";
        }

        else {
            //Error: when you ask it something that doesn't make sense
            result = "I'm sorry, I don't understand";
        }
        //Returns the result drawn from the functions
        return result.replaceAll("()", ""); //safety measure because it breaks the TTS
    }
}
