
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import java.sql.Timestamp;

public class TxtFileHandler {

    private static FileWriter file;
    
    private TxtFileHandler(){

    }

    public static void updateCurrent(String name, String[] txt){

    /* Writes the current stats with timestamp to a text file.
    Creates new .txt file if it doesnt exist
    The file name is always the name of the player*/

        // First removes the file to keep newest update on top (there's no easier way)
        String[] previousUpdates = readPlayerStats(name, -1);
        File txtFile = new File(name +".txt");
        txtFile.delete();

        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(name+".txt", true));){
                // Write timestamp on the first row
                writer.write("<"+timeStamp().toString()+ ">\n");

                // Go through total lvl and all the skills and record them
                for(int i=0; i< 24; i++){
                    if(txt[i] != null){
                        writer.write(txt[i]+"\n");
                    }
            }
            writer.write("\n");

            // Writes the previous updates
            if(previousUpdates != null){
                for(String line:previousUpdates){
                writer.write(line+"\n");
                }
            }
            writer.write("\n");
            
            System.out.println("Stats stored successfully!");
        } catch (IOException ioe){
            System.out.println("Problem reading the file");
        }
    }

    public static void removePlayer(String name){

    /* Deletes the the player.txt file. 
    Called when user presses the "-" button next to the player's name in the sidepanel */

        File txtFile = new File(name +".txt");
        if(txtFile.delete()){
            System.out.println("Deleted the file: " + txtFile.getName());
        }
        else System.out.println("Failed to delete file: <" +txtFile.getName()+ ">");
    }

    public static String[] readPlayerStats(String name, int updateIndex){

        /*Reads and returns the stats of a player from the text file.
        The stats are only returned from certain time period.
        The time period is based on updateIndex. For example index 1 is the latest
        update */

        String[] stats = null;
        try (BufferedReader br = new BufferedReader(new FileReader(name+".txt"))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            // Counts which update is handled while reading the text file
            int currentUpdateIndex = 0;

            while (line != null) {
                if(line.startsWith("<")){
                    // The index is incremented when new timestamp is parsed
                    // Every timestamp starts with character "<"

                    currentUpdateIndex += 1;
                    //System.out.println(currentUpdateIndex);
                }
                if(currentUpdateIndex == updateIndex || updateIndex == -1){
                    // Appends the text only from certain timestamp based on update index
                    // If the update index is -1 all the updates are appended

                    sb.append(line);
                    sb.append(System.lineSeparator());
                }
                line = br.readLine();
            }
            stats = sb.toString().split("\n");

        } catch (IOException ioe){
            System.out.println("Failed to read the file: <"+name+">");
        }
        return stats;
    }

    public static String[] calcProgress(String name, int updateIndex){

        String[] laterUpdate = readPlayerStats(name, updateIndex);
        String[] prevUpdate = readPlayerStats(name, updateIndex+1);
        String[] diffs = new String[laterUpdate.length-2];

        for(int i=1; i < laterUpdate.length-1; i++){

            int currentRank = Integer.parseInt(laterUpdate[i].split(",")[0]);
            int prevRank = Integer.parseInt(prevUpdate[i].split(",")[0]);
            int rankDiff = currentRank - prevRank;

            int currentLvl = Integer.parseInt(laterUpdate[i].split(",")[1]);
            int prevLvl = Integer.parseInt(prevUpdate[i].split(",")[1]);
            int lvlDiff = currentLvl - prevLvl;

            int currentXp = Integer.parseInt(laterUpdate[i].split(",")[2].strip());
            int prevXp = Integer.parseInt(prevUpdate[i].split(",")[2].strip());
            int xpDiff = currentXp - prevXp;

            String diffStr = Integer.toString(rankDiff)+" "+Integer.toString(lvlDiff)+" "+
            Integer.toString(xpDiff);
            diffs[i-1] = diffStr;
        }

        return diffs;
    }
    
    public static Timestamp timeStamp(){
        Date date= new Date();
        long time = date.getTime();
        return new Timestamp(time);
    }

}
