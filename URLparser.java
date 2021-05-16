import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class URLparser {
    
   /*Executes an URL query from Jagex highscores endpoint */

    private URLparser(){
    }
    
    public static String reqPlayerStats(String name) {
        String result = null;
        try {
           URL url = new URL("https://secure.runescape.com/m=hiscore_oldschool/index_lite.ws?player=" + name);
           Scanner sc = new Scanner(url.openStream());
           StringBuffer sb = new StringBuffer();
           while (sc.hasNext()) {
              sb.append(sc.next() + " ");
           }
           result = sb.toString();
           sc.close();
           return result;
        } catch (MalformedURLException mue){
           System.out.println("Player not found!");
           return result;
        } catch (IOException ioe){
           System.out.println("Player not found");
        }
        return result;
     }
}
