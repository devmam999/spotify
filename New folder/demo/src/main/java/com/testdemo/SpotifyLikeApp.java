package com.testdemo;
import java.io.File; 
import java.io.IOException; 
import java.util.Scanner; 
  
import javax.sound.sampled.AudioInputStream; 
import javax.sound.sampled.AudioSystem; 
import javax.sound.sampled.Clip; 
import javax.sound.sampled.LineUnavailableException; 
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;

import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;

/*
    To compile: javac SpotifyLikeApp.java
    To run: java SpotifyLikeApp
 */

// declares a class for the app
public class SpotifyLikeApp {

    // global variables for the app
    String status;
    Long position;
    static Clip audioClip;

    // "main" makes this class a java app that can be executed
    public static void main(final String[] args) {

        // create a scanner for user input
        Scanner input = new Scanner(System.in);

        String userInput = "o";
        while (!userInput.equals("q")) {

            menu();

            // get input
            userInput = input.nextLine();

            // accept upper or lower case commands

            userInput.toLowerCase();

            // do something
            handleMenu(userInput);

            if (userInput.equals("s")) {
                System.out.print("Enter the title of the song you would like to listen to (Available songs are in the library): ");
                userInput = input.nextLine();
                userInput.toLowerCase();
                break;
            }
            else if (userInput.equals("l")) {
                //menu of songs the user can listen to
                System.out.println("Here are the songs you can listen to:");
                System.out.println("Cement Lunch by Ava Luna");
                System.out.println("Journey of King by Bisou");
                System.out.println("Tanzen by Checkie Brown");
                System.out.println("Wirklich Wichtig by Checkie Brown");
                System.out.println("Cirlces by Post Malone");
                System.out.println("Vacaciones Salsa by Dee Yan Key");
                System.out.println("El Preso Numero Nueve by Kathleen Martin");
                System.out.println("Welcome by Kitkat Club");
                System.out.println("Burn it Down by Mid Air Machine");
                System.out.println("Permission to Dance by BTS");
                System.out.println("Storybook by Scott Holmes");
                System.out.println("Zumbido by The Dubbstyle");
            }

        }

        // close the scanner
        input.close();

    }

    /*
     * displays the menu for the app
     */
    public static void menu() {

        System.out.println("---- SpotifyLikeApp ----");
        System.out.println("[H]ome");
        System.out.println("[S]earch by title");
        System.out.println("[L]ibrary");
        System.out.println("[P]lay");
        System.out.println("[Q]uit");
        System.out.print("Enter q to Quit:");

        System.out.println("");
        System.out.print("");

    }

    /*
     * handles the user input for the app
     */

    //default song, if user presses "s", they can change the name and author of song
    static String name = "Permission to Dance";
    static String author = "BTS";
    public static void handleMenu(String userInput) {

        switch (userInput) {

            case "h":
                System.out.println("-->Home<--");
                break;

            case "s":
                System.out.println("-->Search by Title<--");
                break;

            case "l":
                System.out.println("-->Library<--");
                break;
                
            case "p":
                System.out.println("-->Play<--");
                play();
                break;

            case "q":
                System.out.println("-->Quit<--");
                break;

            default:
                break;
        }

    }
    

    /*
     * plays an audio file
     */
    public static void play() {

        // open the audio file
        // src\library\example audio\cropped_wav\Checkie_Brown_-_11_-_Wirklich_Wichtig_CB_27.wav
            final File file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/testdemo/spotify-example-code-and-audio/wav/Permission-To-Dance.wav");

        try {
        
            // create clip 
            audioClip = AudioSystem.getClip();

            // get input stream
            final AudioInputStream in = getAudioInputStream(file);

            audioClip.open(in);
            audioClip.setMicrosecondPosition(0);
            audioClip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch(Exception e) {
            e.printStackTrace(); 
        }
        
        //Tells the user what song they are listening to
        System.out.println("Now playing " + name + " by " + author);

    }


}