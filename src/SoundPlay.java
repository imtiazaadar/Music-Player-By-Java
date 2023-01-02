import javax.sound.sampled.*;
import java.io.*;
import java.util.Scanner;
/*
* Author : Imtiaz Adar
* Date : 02/01/2023
* Language : Java
* Twitter : /@imtiazaadar
*/
public class SoundPlay {
    public static void playMusic(Clip clip) throws InterruptedException {
        Thread.sleep(1000);
        clip.start();
    }
    public static void pauseMusic(Clip clip) throws InterruptedException {
        Thread.sleep(1000);
        clip.stop();
    }
    public static void resetMusic(Clip clip) throws InterruptedException {
        Thread.sleep(1000);
        clip.setMicrosecondPosition(0);
    }
    public static void stopMusic(Clip clip) throws InterruptedException {
        Thread.sleep(1000);
        clip.stop();
        clip.setMicrosecondPosition(0);
    }
    public static File loadAudioSystem(String path) throws UnsupportedAudioFileException, IOException {
        File file = new File(path);
        return file;
    }
    public static void printFileNames(File[] songs, int start){
        if(start == songs.length){
            return;
        }
        if(songs[start].isFile()){
            //System.out.println(songs[start].toString());
            char[] song = songs[start].toString().toCharArray();
            boolean wav = false;
            for(int i = 0; i < song.length; i++){
                if(song[i] == '.'){
                    int j = i + 1;
                    if(song[j] == 'w' && song[j + 1] == 'a' && song[j + 2] == 'v'){
                        wav = true;
                        break;
                    }
                }
            }
            if(wav) {
                System.out.println(songs[start].getName());
            }
        }
        printFileNames(songs, start + 1);
    }
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        System.out.println("MUSIC PLAYER BY IMTIAZ ADAR\n\n");
        String directory = "C:\\Users\\imtia\\IdeaProjects\\Java2023\\src";
        File[] songList = new File[] {};
        File directoryFile = new File(directory);
        if(directoryFile.isDirectory()){
            songList = (directoryFile.listFiles());
        }
        System.out.println("----- SONG LIST -----\n");
        printFileNames(songList, 0);
        System.out.println("\nSong Name: ");
        String songname = scan.nextLine();
        String path = "C:\\Users\\imtia\\IdeaProjects\\Java2023\\src\\" + songname;
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(loadAudioSystem(path));
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        boolean run = true;
        while (run) {
            System.out.println("\n----- OPERATIONS -----\n\n[P] to play music\n[C] to pause the music\n" +
                    "[R] to reset the music\n[Q] to stop the music\n[X] to change the music\n[E] to exit the program\n");
            String option = scan.nextLine().toLowerCase();
            switch (option) {
                case "p":
                    playMusic(clip);
                    System.out.println("Music started...");
                    break;
                case "c":
                    pauseMusic(clip);
                    System.out.println("Music paused...");
                    break;
                case "r":
                    resetMusic(clip);
                    System.out.println("Music reset...");
                    break;
                case "q":
                    stopMusic(clip);
                    System.out.println("Music stopped...");
                    break;
                case "x":
                    System.out.println("----- SONG LIST -----\n");
                    printFileNames(songList, 0);
                    System.out.println("\nSong Name: ");
                    songname = scan.nextLine();
                    path = "C:\\Users\\imtia\\IdeaProjects\\Java2023\\src\\" + songname;
                    audioStream = AudioSystem.getAudioInputStream(loadAudioSystem(path));
                    clip = AudioSystem.getClip();
                    clip.open(audioStream);
                    break;
                case "e":
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect key...");
            }
        }
    }
}