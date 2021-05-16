
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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

        //Removes the 
        File fileName = new File("players.txt");
        File tempFile = new File("temp.txt");
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));){
            String lineToRemove = name.toLowerCase();
            String currentLine;
            
            while((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(lineToRemove)) continue;
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        } catch (FileNotFoundException fe){
            System.out.println("Filenotfound");
        } catch (IOException ie){
            System.out.println("");
        }
        if(fileName.delete()){
            System.out.println("Deleted the file: ");
        }
        tempFile.renameTo(fileName);

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

        /*Calculates the progress between two consecutive updates, which is needed
        for the "progress" state display */

        String[] laterUpdate = readPlayerStats(name, updateIndex);
        String[] prevUpdate = readPlayerStats(name, updateIndex+1);
        String[] diffs = new String[laterUpdate.length-2];

        if(prevUpdate.length == 1){
            // There is no previous updates
            return new String[]{"First update"};
        }

        for(int i=1; i < laterUpdate.length-1; i++){

            // Difference of ranks
            int currentRank = Integer.parseInt(laterUpdate[i].split(",")[0]);
            int prevRank = Integer.parseInt(prevUpdate[i].split(",")[0]);
            int rankDiff = currentRank - prevRank;

            // Difference of levels
            int currentLvl = Integer.parseInt(laterUpdate[i].split(",")[1]);
            int prevLvl = Integer.parseInt(prevUpdate[i].split(",")[1]);
            int lvlDiff = currentLvl - prevLvl;

            // Difference of experience
            int currentXp = Integer.parseInt(laterUpdate[i].split(",")[2].strip());
            int prevXp = Integer.parseInt(prevUpdate[i].split(",")[2].strip());
            int xpDiff = currentXp - prevXp;

            String diffStr = Integer.toString(rankDiff)+" "+Integer.toString(lvlDiff)+" "+
            Integer.toString(xpDiff);
            diffs[i-1] = diffStr;
        }

        return diffs;
    }
    
    public static String getTimestamp(String name, int updateIndex){

        /*Reads the timestamp of certain update from the text file*/

        String timeStamp = "0";

        try (BufferedReader br = new BufferedReader(new FileReader(name+".txt"))){
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            
            // Counts which update is handled while reading the text file
            int currentUpdateIndex = 1;

            while (line != null) {
                if(line.startsWith("<")){
                    if(currentUpdateIndex == updateIndex){
                        sb.append(line.substring(12,20));
                        sb.append(System.lineSeparator());
                        return sb.toString();
                    }
                    currentUpdateIndex += 1;
                }
                line = br.readLine();
            }
        } catch (IOException ioe){
            System.out.println("Failed to read the file: <"+name+">");
        }

        // Timestamp fetching is failed if it returns this
        return timeStamp;
    }

    public static String getUpdateDates(String name){

        /*Reads through the txt file of certain player and gathers all the 
        timestamps of the updates (needed for update log)*/

        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(name+".txt"))){
            String line = br.readLine();

            while (line != null) {
                if(line.startsWith("<")){
                    sb.append(line.substring(1,line.length()-1));
                    sb.append(System.lineSeparator());
                }
                line = br.readLine();
            }
        } catch (IOException ioe){
            System.out.println("Failed to read the file: <"+name+">");
        }
        return sb.toString();
    }

    public static void removeUpdate(String name, int updateIndex){

        /*Removes certain update from the text file.*/

        String[] dates = UpdateLog.dates.toArray(new String[0]);
        int tempIndex = 1;
        File txtFile = new File(name +".txt");
        File tempFile = new File("temp.txt");
        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter(tempFile, true));){
                for(String date:dates){
                    if(tempIndex == updateIndex){
                        tempIndex += 1;
                        continue;
                    }
                    String[] updateData = readPlayerStats(name, tempIndex);
                    for(String stat: updateData){
                        writer.write(stat+"\n");
                    }
                    tempIndex += 1;
                }
            } catch (IOException ioe){
                System.out.println("Problem reading the file");
        }
        if(txtFile.delete()){
            System.out.println("Deleted the file: " + txtFile.getName());
        }
        tempFile.renameTo(txtFile);
    }

    public static void addPlayer(String player){
        /*Adds player to players.txt file*/

        try (BufferedWriter writer = new BufferedWriter(
            new FileWriter("players.txt", true));){
                // Write timestamp on the first row
                writer.write(player+"\n");
            } catch (IOException ioe){
                System.out.println("Problem reading the file");
        }
    }

    public static String[] readPlayers(){

        /*Gathers all the followed players from players.txt file. For storing
        the players even after the user session ends. */

        String[] players = null;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("players.txt"))){
            String line = br.readLine();

            while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
            }
        } catch (IOException ioe){
                System.out.println("Failed to read players.txt file:");
        }
        players = sb.toString().split("\n");
        return players;
    }

    public static String[] readCertainSkill(String name, int skillIndex, int updateIndex){

        /*Reads text file filtered by player name, update date and the skill.
        Needed for mouse hover popups when displaying skill in "total" -mode */

        StringBuilder sb = new StringBuilder();
        int currentUpdateIndex = 0;
        int currentSkillIndex = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(name+".txt"))){
            String line = br.readLine();

            while (line != null) {
                if(currentUpdateIndex == updateIndex){
                    if(currentSkillIndex == skillIndex){
                        sb.append(line);
                        sb.append(System.lineSeparator());
                        return sb.toString().split(",");
                    }
                    currentSkillIndex += 1;
                }
                if(line.startsWith("<")){
                    currentUpdateIndex += 1;
                }
                line = br.readLine();
            }
        } catch (IOException ioe){
                System.out.println("Failed to read players.txt file:");
        }
        return new String[]{"Failed to fetch data"};
    }

    public static Timestamp timeStamp(){
        Date date= new Date();
        long time = date.getTime();
        return new Timestamp(time);
    }

}
