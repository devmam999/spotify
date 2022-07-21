package com.spotifyapp;
import java.io.File; 
import java.io.IOException; 
  
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
import java.util.*;
/*
    To compile: javac SpotifyLikeApp.java
    To run: java SpotifyLikeApp
 */

// declares a class for the app
public class SpotifyLikeApp {

    // global variables for the app
    String status;
    static boolean isFavorite;
    static Long position;
    static Clip audioClip;
    static String userSong = "";
    static Scanner input = new Scanner(System.in);

    // "main" makes this class a java app that can be executed
    public static void main(final String[] args) {
        
        String userInput = "o";
        while (!userInput.equals("q")) {
            

            menu();

            

            // get input
            userInput = input.nextLine();

            // accept upper or lower case commands

            userInput.toLowerCase();

            // do something
            handleMenu(userInput);

            //if user presses h without listening to any song, the program shouldn't just stop
            if (!userInput.equals("h")) {
                break;
            }

        }


    }

    /**
     * 
     */
    public static void playerMenu() {
        

        String playerAction = "o";
        while (!playerAction.equals("s")) {
           
            songMenu();
            
            playerAction = input.nextLine();

            playerAction.toLowerCase();

            handleSongMenu(playerAction);

            break;
        }

    }

    public static void songMenu() {
        System.out.println("---- SpotifyLikeApp ----");
        System.out.println("[P]ause");
        System.out.println("[S]top");
        System.out.println("[R]ewind 5 seconds");
        System.out.println("[G]o forward 5 seconds");
        System.out.println("[F]avorite");
        System.out.print("");

        System.out.println("");
        System.out.print("");
    }
    public static void handleSongMenu(String playerAction) {
        switch (playerAction) {
            case "p":
                audioClip.stop();
                System.out.print("Enter r to resume: ");
                Scanner continueMenu = new Scanner(System.in);
                String continuing = continueMenu.nextLine();
                continuing.toLowerCase();
                if (continuing.equals("r")) {
                    audioClip.start();
                    playerMenu();
                }
                
                break;
            case "g":
                position = audioClip.getMicrosecondPosition();
                audioClip.stop();
                long fastForward = position + 5000000;
                audioClip.setMicrosecondPosition(fastForward);
                audioClip.start();
                playerMenu();
                break;
            case "r":
                position = audioClip.getMicrosecondPosition();
                audioClip.stop();
                long rewind = position - 5000000;
                audioClip.setMicrosecondPosition(rewind);
                audioClip.start();
                playerMenu();
                break;
            case "f":
                isFavorite = true;
                System.out.println("Song is now a favorite. To access a favorite, press f on the homescreen");
                playerMenu();
                break;
            // if the user presses "s", the program won't stop. This is useful incase the user wants to access home
            case "s":
                audioClip.stop();

        
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
                        Scanner anotherInput = new Scanner(System.in);
                        userSong = anotherInput.nextLine();
                        userSong.toLowerCase();
                        anotherInput.close();
                        play();
                        playerMenu();
                        break;
                    }
                    if (userInput.equals("l")) {
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
                        System.out.print("Enter the title of the song you would like to listen to: ");
                        userSong = input.nextLine();
                        userSong.toLowerCase();
                        play();
                        playerMenu();
                        break;
                        
                    }
                    else if (userInput.equals("q")) {
                        break;
                    }

        }

        // close the scanner
        if (userInput.equals("q")) {
            input.close();
        }

                break;

            

            default:
                break;
        }
    }

    /*
     * displays the menu for the app
     */
    public static void menu() {

        System.out.println("---- SpotifyLikeApp ----");
        System.out.println("[H]ome");
        System.out.println("[S]earch by title");
        System.out.println("[L]ibrary");
        System.out.println("[F]avorites");
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
    static String name = null;
    static String author = null;
    public static void handleMenu(String userInput) {

        switch (userInput) {

            case "h":
                System.out.println("-->Home<--");
                if (name != null && author != null) {
                    System.out.println("You have recently listened to " + name + " by " + author);
                }
                else {
                    System.out.println("You haven't listened to any songs recently");
                }
                break;

            case "s":
                System.out.println("-->Search by Title<--");               
                System.out.print("Enter the title of the song you would like to listen to (Available songs are in the library): ");
                userSong = input.nextLine();
                userSong.toLowerCase();
                play();
                playerMenu();
                break;

            case "l":
                System.out.println("-->Library<--");
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
                System.out.print("Enter the title of the song you would like to listen to: ");
                userSong = input.nextLine();
                userSong.toLowerCase();
                play();
                playerMenu();
                break;
                
            case "p":
                System.out.println("-->Play<--");
                name = "Permission to Dance";
                author = "BTS";
                play();
                playerMenu();
                break;

            case "f":
                System.out.println("-->Favorites<--");
                if (isFavorite = true) {
                    System.out.println("You have recently favorited " + name + " by " + author);
                }
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
    /**
     * 
     */
    public static void play() {

        // open the audio file
        // src\library\example audio\cropped_wav\Checkie_Brown_-_11_-_Wirklich_Wichtig_CB_27.wav
        File file = new File("C:/Users/devesh/downloads/quiet.wav");
        if (userSong.equals("circles")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Circles.wav");
            name = "Circles";
            author = "Post Malone";
            isFavorite = false;
        }
        else if (userSong.equals("cement lunch")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Ava-Luna-Cement-Lunch.wav");
            name = "Cement Lunch";
            author = "Ava Luna";
            isFavorite = false;
        }
        else if (userSong.equals("journey of king")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Bisou-Journey-Of-King.wav");
            name = "Journey of King";
            author = "Bisou";
            isFavorite = false;
        }
        else if (userSong.equals("tanzen")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Checkie-Brown-Tanzen.wav");
            name = "Tanzen";
            author = "Checkie Brown";
            isFavorite = false;
        }
        else if (userSong.equals("wirklich wichtig")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Checkie-Brown-Wirklich-Wichtig.wav");
            name = "Wirklich Wichtig";
            author = "Checkie Brown";
            isFavorite = false;
        }
        else if (userSong.equals("vacaciones salsa")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Dee-Yan-Key-Vacaciones-Salsa.wav");
            name = "Vacaciones Salsa";
            author = "Dee Yan-Key";
            isFavorite = false;
        }
        else if (userSong.equals("el preso numero nueve")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Kathleen-Martin-El-Preso-Numero-Nueve.wav");
            name = "El Preso Numero Nueve";
            author = "Kathleen Martin";
            isFavorite = false;
        }
        else if (userSong.equals("welcome")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Kitkat-Club-Welcome.wav");
            name = "Welcome";
            author = "Kitkat Club";
            isFavorite = false;
        }
        else if (userSong.equals("burn it down")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Mid-Air-Machine-Burn-It-Down.wav");
            name = "Burn It Down";
            author = "Midair Machine";
            isFavorite = false;
        }
        else if (userSong.equals("storybook")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Scott-Holmes-Storybook.wav");
            name = "Storybook";
            author = "Scott Holmes";
            isFavorite = false;
        }
        else if (userSong.equals("zumbido")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/The-Dubbstyle-Zumbido.wav");
            name = "Zumbido";
            author = "The Dubbstyle";
            isFavorite = false;
        }
        else if (name == "Permission to Dance" || userSong.equals("permission to dance")) {
            file = new File("C:/Users/devesh/Documents/GitHub/spotify/New folder/demo/src/main/java/com/spotifyapp/spotify-example-code-and-audio/wav/Permission-To-Dance.wav");
            name = "Permission to Dance";
            author = "BTS";
            isFavorite = false;
        }

        try {
            
            // create clip l

            audioClip = AudioSystem.getClip();

            // get input stream
            final AudioInputStream in = getAudioInputStream(file);

            
            audioClip.open(in);
            audioClip.setMicrosecondPosition(0);
            audioClip.start();

            //Tells the user what song they are listening to
            if (name != null) {
                System.out.println("Now playing " + name + " by " + author);
            }

        } 
        catch(Exception e) {
            e.printStackTrace();
        }

        
        
    }
}



