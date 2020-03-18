package com.company;

/**
 * Made by Michael C. D. Lima, 2020 all rights reserved
 */

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Main {
    //Game Screen
    static int width = 60;
    static int height = 60;

    //Main Menu Screen
    static int MMWidth = 10;
    static int MMHeight = 60;


    //Aircraft Data
    static int possitionY = width - 2, possitionX = height / 2;
    static int realPosX, K;
    static int RIGHT = 0, LEFT = 0;

    //Meteors Data
    static int METEOR1x, METEOR1y, METEOR2x, METEOR2y, METEOR3x, METEOR3y, METEOR4x, METEOR4y, METEOR5y, METEOR5x;
    static int meteors;
    static boolean meteor2 = false, meteor3 = false, meteor4 = false, meteor5 = false;


    // Bullets data
    static boolean shoot = false, shoot2 = false, shoot3 = false, shoot4 = false, shoot5 = false, shoot6 = false;
    static int shootPos1Y, shootPos1X, shootPos2Y, shootPos2X, shootPos3Y, shootPos3X, shootPos4Y, shootPos4X,
            shootPos5Y, shootPos5X, shootPos6Y, shootPos6X, shooting = 0;

    //Points
    static int points = -5;
    private static int highScore;
    private static String nickName;

    //Game running
    static boolean running = false;

    //Main menu
    static boolean mainMenu = true;
    static int pointer = 4;
    static boolean MM = true;
    static int songCounter = 0;
    static boolean playSong = true;


    //Credits
    static boolean credits;
    static boolean highData;

    //High Score

    static Clip clip;


    static {
        try {
            clip = AudioSystem.getClip();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException, AWTException, IOException {

        mainMenuMethod();
        highScoreMethod();
        Game();

    }

    public static void Game() throws InterruptedException, AWTException, IOException {
        cueSong();

        while (running) {
            draw();
            getUserInput();
            clearScreen();
        }
        saveData();
        mainMenu = true;
        mainMenuMethod();
    }

    //Cues the song of the game
    public static void cueSong() throws InterruptedException {
        String filePath = "/Users/michaellima/Desktop/SkyFall_Sound/Song/ArcadeSong.wav";
        song(filePath);
    }

    public static void cueMMSong() throws InterruptedException {
        String filePath = "/Users/michaellima/Desktop/SkyFall_Sound/Song/MainMenu.wav";
        sound(filePath);
    }

    static public void highScoreMethod() throws IOException, InterruptedException, AWTException {
        while (highData) {
            loadData();
            Thread.sleep(50);
            HSPointer();
            clearScreen();
            if (playSong) {
                cueMMSong();
                songCounter += 190;
            }
            playSong = false;
            if (songCounter > 0) {
                songCounter--;
            } else {
                playSong = true;
            }
            System.out.println(songCounter);
        }
    }

    static public void mainMenuMethod() throws InterruptedException, AWTException {
        while (mainMenu) {
            menu();
            movePointer();
            Thread.sleep(50);
            clearScreen();

            if(highData){

            }

            if (playSong) {
                cueMMSong();
                songCounter += 190;
            }
            playSong = false;
            if (songCounter > 0) {
                songCounter--;
            } else {
                playSong = true;
            }
            System.out.println(songCounter);
        }
        clearScreen();
    }

    static public void menu() {
        int dontPrint = 0;
        System.out.print("                                                  ");
        for (int i = 0; i < MMHeight + 1; i++) {
            System.out.print("#");
        }
        System.out.print("\n");
        for (int i = 0; i < MMWidth; i++) {
            for (int j = 0; j < MMHeight; j++) {

                if (j == 0) {
                    System.out.print("                                                  ");
                    System.out.print("#");
                } else if (i == 3 && j == 20) {
                    System.out.print("Start Game");
                    dontPrint += 10;
                } else if (i == 4 && j == 20) {
                    System.out.print("High Score");
                    dontPrint += 10;
                } else if (i == 5 && j == 20) {
                    System.out.print("Credits");
                    dontPrint += 7;
                } else if (i == 6 && j == 20) {
                    System.out.print("Exit");
                    dontPrint += 4;
                }

                if (i == pointer && j == 17) {
                    System.out.print("->");
                    dontPrint += 2;
                }

                if (dontPrint == 0) {
                    System.out.print(" ");
                } else if (dontPrint > 0) {
                    dontPrint--;
                }


                if (j == MMHeight - 1) {
                    System.out.print("#\n");
                }
            }
        }
        System.out.print("                                                  ");
        for (int i = 0; i < MMHeight + 1; i++) {
            System.out.print("#");
        }
        System.out.println("\n\n");
    }

    public static void movePointer() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        Scanner s = new Scanner(System.in);

        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(100);
        char move;

        //3  = Start Game // 4 = High Score // 5 = Credits

        try {
            switch (move = s.nextLine().charAt(0)) {
                case 's':
                    if (pointer == 3 || pointer == 4 || pointer == 5) {
                        pointer++;
                    } else {
                        System.out.println("");
                    }
                    break;
                case 'w':
                    if (pointer == 4 || pointer == 5 || pointer == 6) {
                        pointer--;
                    } else {
                        System.out.println("");
                    }
                    break;
                case ' ':
                    if (pointer == 3) {
                        mainMenu = false;
                        running = true;
                    } else if (pointer == 4) {
                        mainMenu = false;
                        highData = true;
                        loadData();
                    } else if (pointer == 5) {
                        System.out.print("");
                    } else if (pointer == 6) {
                        mainMenu = false;
                        highData = false;
                        running = false;

                    } else {
                        mainMenu = false;
                        credits = true;
                    }
            }
        } catch (Exception e) {
            System.out.println("");
        }

    }

    //Draws out the screen
    public static void draw() throws InterruptedException {

        System.out.print(" ");
        for (int i = 0; i < width + 1; i++) {
            System.out.print("#");
        }
        System.out.print("\n " + "");

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {

                if (METEOR1x == height - 1 || METEOR2x == height - 1 || METEOR3x == height - 1 || METEOR4x == height - 1 || METEOR5x == height - 1) {
                    running = false;
                    i = width + 1;
                    j = height + 1;
                }

                if (j == 0) {
                    System.out.print("#");
                }

                if (j == width - 1) {
                    System.out.print("#\n");
                }

                if (possitionY == i && possitionX == j + LEFT - RIGHT) {
                    System.out.print("^");
                    K = j;
                }

                if (METEOR1x == i && METEOR1y == j) {

                    System.out.print("O");
                }

                if (METEOR2x == i && METEOR2y == j && meteor2) {

                    System.out.print("O");
                }
                if (METEOR3x == i && METEOR3y == j && meteor3) {

                    System.out.print("O");
                }

                if (METEOR4x == i && METEOR4y == j && meteor4) {

                    System.out.print("O");
                }
                if (METEOR5x == i && METEOR5y == j && meteor5) {

                    System.out.print("O");
                }


                if (shoot) {
                    if (shootPos1X == i && shootPos1Y == j) {
                        System.out.print("|");
                    }
                }

                if (shoot2) {
                    if (shootPos2X == i && shootPos2Y == j) {
                        System.out.print("|");
                    }
                }

                if (shoot3) {
                    if (shootPos3X == i && shootPos3Y == j) {
                        System.out.print("|");
                    }
                }
                if (shoot4) {
                    if (shootPos4X == i && shootPos4Y == j) {
                        System.out.print("|");
                    }
                }
                if (shoot5) {
                    if (shootPos5X == i && shootPos5Y == j) {
                        System.out.print("|");
                    }
                }
                if (shoot6) {
                    if (shootPos6X == i && shootPos6Y == j) {
                        System.out.print("|");
                    }
                }

                if (shootPos1X == METEOR1x - 1 && shootPos1Y == METEOR1y) {
                    shoot = false;
                    shootPos1X = 100;
                    shootPos1Y = 100;
                    METEOR1x -= METEOR1x;
                    METEOR1y -= METEOR1y;
                    Random random = new Random();
                    int upperBound = width;
                    METEOR1x = 0;
                    METEOR1y = random.nextInt(upperBound) + 2;
                }

                meteorHit();

                if (possitionY == i && possitionX == j + LEFT - RIGHT || METEOR1x == i && METEOR1y == j || shootPos1X == i && shootPos1Y == j
                ) {
                    System.out.print("");

                } else if (METEOR2x == i && METEOR2y == j && meteor2) {
                    System.out.print("");

                } else if (METEOR3x == i && METEOR3y == j && meteor3) {
                    System.out.print("");

                } else if (METEOR4x == i && METEOR4y == j && meteor4) {
                    System.out.print("");

                } else if (METEOR5x == i && METEOR5y == j && meteor5) {
                    System.out.print("");

                } else if (shootPos2X == i && shootPos2Y == j) {
                    System.out.print("");
                } else if (shootPos3X == i && shootPos3Y == j) {
                    System.out.print("");
                } else if (shootPos4X == i && shootPos4Y == j) {
                    System.out.print("");
                } else if (shootPos5X == i && shootPos5Y == j) {
                    System.out.print("");
                } else if (shootPos6X == i && shootPos6Y == j) {
                    System.out.print("");
                } else {
                    System.out.print(" ");
                }
            }

        }

        for (int i = 0; i < width; i++) {
            System.out.print("#");
        }

        if (meteors % 10 == 0) {
            METEOR1x++;
            METEOR2x++;
            METEOR3x++;
            METEOR4x++;
            METEOR5x++;
        }

        shootPos1X--;
        shootPos2X--;
        shootPos3X--;
        shootPos4X--;
        shootPos5X--;
        shootPos6X--;


        meteors++;
        createMeteors();
        if (shootPos1X == 0) {
            shootPos1X -= shootPos1X;
            shoot = false;
        }
        if (shootPos2X == 0) {
            shootPos2X -= shootPos2X;
            shoot2 = false;
        }
        if (shootPos3X == 0) {
            shootPos3X -= shootPos3X;
            shoot3 = false;
        }

        realPosX = possitionX - LEFT + RIGHT;

    }

    //Gets the user input and processes it
    public static void getUserInput() throws AWTException, InterruptedException {
        Robot r = new Robot();
        Scanner s = new Scanner(System.in);
        char input;

        r.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(35);


        try {
            switch (input = s.nextLine().charAt(0)) {
                case 'a':
                    LEFT++;
                    break;
                case 'd':
                    RIGHT++;
                    break;

                case 'x':
                    running = false;
                case 'w':
                    if (shooting == 0 && !shoot) {
                        shoot = true;
                        shootPos1Y = realPosX;
                        shootPos1X = possitionY - 1;
                        shooting++;
                        String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Shooting.wav";
                        sound(path);
                        break;
                    } else if (shooting == 1) {
                        shoot2 = true;
                        shootPos2Y = realPosX;
                        shootPos2X = possitionY - 1;
                        shooting++;
                        String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Shooting.wav";
                        sound(path);
                    } else if (shooting == 2) {
                        shoot3 = true;
                        shootPos3Y = realPosX;
                        shootPos3X = possitionY - 1;
                        shooting++;
                        String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Shooting.wav";
                        sound(path);
                    } else if (shooting == 3) {
                        shoot4 = true;
                        shootPos4Y = realPosX;
                        shootPos4X = possitionY - 1;
                        shooting++;
                        String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Shooting.wav";
                        sound(path);
                    } else if (shooting == 4) {
                        shoot5 = true;
                        shootPos5Y = realPosX;
                        shootPos5X = possitionY - 1;
                        shooting++;
                        String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Shooting.wav";
                        sound(path);
                    } else if (shooting == 5) {
                        shoot6 = true;
                        shootPos6Y = realPosX;
                        shootPos6X = possitionY - 1;
                        shooting -= shooting;
                        String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Shooting.wav";
                        sound(path);
                    } else {
                        System.out.println("");
                    }
                    break;

            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
        }

    }

    //Places meteors
    static public void createMeteors() {
        Random random = new Random();
        int upperBound = width;
        if (meteors == 0) {
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
        }

        if (meteors == 50) {
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            meteor2 = true;
        }

        if (meteors == 100) {
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            meteor3 = true;
        }

        if (meteors == 150) {
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            meteor4 = true;
        }
        if (meteors == 200) {
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            meteor5 = true;
        }


    }

    //Detects when bullet hits a meteor
    static public void meteorHit() throws InterruptedException {


        if (shootPos1X == METEOR1x && shootPos1Y == METEOR1y) {
            shoot = false;
            shootPos1X = 100;
            shootPos1Y = 100;
            METEOR1x -= METEOR1x;
            METEOR1y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos1X == METEOR2x && shootPos1Y == METEOR2y) {
            shoot = false;
            shootPos1X = 100;
            shootPos1Y = 100;
            METEOR2x -= METEOR1x;
            METEOR2y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos1X == METEOR3x && shootPos1Y == METEOR3y) {
            shoot = false;
            shootPos1X = 100;
            shootPos1Y = 100;
            METEOR3x -= METEOR3x;
            METEOR3y -= METEOR3y;
            Random random = new Random();
            int upperBound = width;
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos1X == METEOR4x && shootPos1Y == METEOR4y) {
            shoot = false;
            shootPos1X = 100;
            shootPos1Y = 100;
            METEOR4x -= METEOR4x;
            METEOR4y -= METEOR4y;
            Random random = new Random();
            int upperBound = width;
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos1X == METEOR5x && shootPos1Y == METEOR5y) {
            shoot = false;
            shootPos1X = 100;
            shootPos1Y = 100;
            METEOR5x -= METEOR5x;
            METEOR5y -= METEOR5y;
            Random random = new Random();
            int upperBound = width;
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        // ########################################################################################


        if (shootPos2X == METEOR1x && shootPos2Y == METEOR1y) {
            shoot2 = false;
            shootPos2X = 100;
            shootPos2Y = 100;
            METEOR1x -= METEOR1x;
            METEOR1y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos2X == METEOR2x && shootPos2Y == METEOR2y) {
            shoot2 = false;
            shootPos2X = 100;
            shootPos2Y = 100;
            METEOR2x -= METEOR1x;
            METEOR2y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos2X == METEOR3x && shootPos2Y == METEOR3y) {
            shoot2 = false;
            shootPos2X = 100;
            shootPos2Y = 100;
            METEOR3x -= METEOR3x;
            METEOR3y -= METEOR3y;
            Random random = new Random();
            int upperBound = width;
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos2X == METEOR4x && shootPos2Y == METEOR4y) {
            shoot2 = false;
            shootPos2X = 100;
            shootPos2Y = 100;
            METEOR4x -= METEOR4x;
            METEOR4y -= METEOR4y;
            Random random = new Random();
            int upperBound = width;
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos2X == METEOR5x && shootPos2Y == METEOR5y) {
            shoot2 = false;
            shootPos2X = 100;
            shootPos2Y = 100;
            METEOR5x -= METEOR5x;
            METEOR5y -= METEOR5y;
            Random random = new Random();
            int upperBound = width;
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        // ########################################################################################


        if (shootPos3X == METEOR1x && shootPos3Y == METEOR1y) {
            shoot3 = false;
            shootPos3X = 100;
            shootPos3Y = 100;
            METEOR1x -= METEOR1x;
            METEOR1y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos3X == METEOR2x && shootPos3Y == METEOR2y) {
            shoot3 = false;
            shootPos3X = 100;
            shootPos3Y = 100;
            METEOR2x -= METEOR1x;
            METEOR2y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos3X == METEOR3x && shootPos3Y == METEOR3y) {
            shoot3 = false;
            shootPos3X = 100;
            shootPos3Y = 100;
            METEOR3x -= METEOR3x;
            METEOR3y -= METEOR3y;
            Random random = new Random();
            int upperBound = width;
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos3X == METEOR4x && shootPos3Y == METEOR4y) {
            shoot3 = false;
            shootPos3X = 100;
            shootPos3Y = 100;
            METEOR4x -= METEOR4x;
            METEOR4y -= METEOR4y;
            Random random = new Random();
            int upperBound = width;
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos3X == METEOR5x && shootPos3Y == METEOR5y) {
            shoot3 = false;
            shootPos3X = 100;
            shootPos3Y = 100;
            METEOR5x -= METEOR5x;
            METEOR5y -= METEOR5y;
            Random random = new Random();
            int upperBound = width;
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }
        // ########################################################################################


        if (shootPos4X == METEOR1x && shootPos4Y == METEOR1y) {
            shoot4 = false;
            shootPos4X = 100;
            shootPos4Y = 100;
            METEOR1x -= METEOR1x;
            METEOR1y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos4X == METEOR2x && shootPos4Y == METEOR2y) {
            shoot4 = false;
            shootPos4X = 100;
            shootPos4Y = 100;
            METEOR2x -= METEOR1x;
            METEOR2y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos4X == METEOR3x && shootPos4Y == METEOR3y) {
            shoot4 = false;
            shootPos4X = 100;
            shootPos4Y = 100;
            METEOR3x -= METEOR3x;
            METEOR3y -= METEOR3y;
            Random random = new Random();
            int upperBound = width;
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos4X == METEOR4x && shootPos4Y == METEOR4y) {
            shoot4 = false;
            shootPos4X = 100;
            shootPos4Y = 100;
            METEOR4x -= METEOR4x;
            METEOR4y -= METEOR4y;
            Random random = new Random();
            int upperBound = width;
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos4X == METEOR5x && shootPos4Y == METEOR5y) {
            shoot4 = false;
            shootPos4X = 100;
            shootPos4Y = 100;
            METEOR5x -= METEOR5x;
            METEOR5y -= METEOR5y;
            Random random = new Random();
            int upperBound = width;
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        // ########################################################################################


        if (shootPos5X == METEOR1x && shootPos5Y == METEOR1y) {
            shoot5 = false;
            shootPos5X = 100;
            shootPos5Y = 100;
            METEOR1x -= METEOR1x;
            METEOR1y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos5X == METEOR2x && shootPos5Y == METEOR2y) {
            shoot5 = false;
            shootPos5X = 100;
            shootPos5Y = 100;
            METEOR2x -= METEOR1x;
            METEOR2y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos5X == METEOR3x && shootPos5Y == METEOR3y) {
            shoot5 = false;
            shootPos5X = 100;
            shootPos5Y = 100;
            METEOR3x -= METEOR3x;
            METEOR3y -= METEOR3y;
            Random random = new Random();
            int upperBound = width;
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos5X == METEOR4x && shootPos5Y == METEOR4y) {
            shoot5 = false;
            shootPos5X = 100;
            shootPos5Y = 100;
            METEOR4x -= METEOR4x;
            METEOR4y -= METEOR4y;
            Random random = new Random();
            int upperBound = width;
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos5X == METEOR5x && shootPos5Y == METEOR5y) {
            shoot5 = false;
            shootPos5X = 100;
            shootPos5Y = 100;
            METEOR5x -= METEOR5x;
            METEOR5y -= METEOR5y;
            Random random = new Random();
            int upperBound = width;
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }
        // ########################################################################################


        if (shootPos6X == METEOR1x && shootPos6Y == METEOR1y) {
            shoot6 = false;
            shootPos6X = 100;
            shootPos6Y = 100;
            METEOR1x -= METEOR1x;
            METEOR1y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos6X == METEOR2x && shootPos6Y == METEOR2y) {
            shoot6 = false;
            shootPos6X = 100;
            shootPos6Y = 100;
            METEOR2x -= METEOR1x;
            METEOR2y -= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR2x = 0;
            METEOR2y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos6X == METEOR3x && shootPos6Y == METEOR3y) {
            shoot6 = false;
            shootPos6X = 100;
            shootPos6Y = 100;
            METEOR3x -= METEOR3x;
            METEOR3y -= METEOR3y;
            Random random = new Random();
            int upperBound = width;
            METEOR3x = 0;
            METEOR3y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos6X == METEOR4x && shootPos6Y == METEOR4y) {
            shoot6 = false;
            shootPos6X = 100;
            shootPos6Y = 100;
            METEOR4x -= METEOR4x;
            METEOR4y -= METEOR4y;
            Random random = new Random();
            int upperBound = width;
            METEOR4x = 0;
            METEOR4y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }

        if (shootPos6X == METEOR5x && shootPos6Y == METEOR5y) {
            shoot6 = false;
            shootPos6X = 100;
            shootPos6Y = 100;
            METEOR5x -= METEOR5x;
            METEOR5y -= METEOR5y;
            Random random = new Random();
            int upperBound = width;
            METEOR5x = 0;
            METEOR5y = random.nextInt(upperBound);
            String path = "/Users/michaellima/Desktop/SkyFall_Sound/SoundEffects/Explode.wav";
            sound(path);
            points++;
        }
    }

    //Runs when Game ends
    static public void gameOver() throws InterruptedException {
        for (int i = 0; i < width; i++) {
            System.out.print("#");
        }
        System.out.println("\nGAME OVER");
        for (int i = 0; i < width; i++) {
            System.out.print("#");
        }
        System.out.println("\n");

        String filePath = "/Users/michaellima/Desktop/SkyFall_Sound/GameOver/GOSound.wav";
        sound(filePath);

        System.out.println("\n\n                                                        TOTAL AMOUNT OF POINTS: " + points + "\n\n\n");


    }

    //Method for sound effects
    public static void sound(String fileLocation) throws InterruptedException {
        int i = 0;
        try {
            File greet = new File(fileLocation);
            if (greet.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(greet);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.start();

                if (Thread.interrupted()) {
                    System.out.println("woops");
                    ;
                }

            } else {
                System.out.println("Can't find file");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Method for song loop
    public static void song(String fileLocation) throws InterruptedException {
        int i = 0;
        try {
            File song = new File(fileLocation);
            if (song.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(song);
                clip.open(audioInput);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);

                if (Thread.interrupted()) {
                    System.out.println("woops");
                    ;
                }

            } else {
                System.out.println("Can't find file");

            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Method to clear screen
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
    }

    public static void saveData() throws IOException {
        int score;
        InputStream in;
        BufferedReader read = new BufferedReader(new FileReader("saveFile.rtf"));
        highScore = Integer.parseInt(read.readLine());

        if (points > highScore) {
            Scanner s = new Scanner(System.in);
            System.out.println("NEW HIGH SCORE!! \n\nPlease Inform Your Name To Save High Score: \n");

            nickName = s.nextLine();

            highScore = points;

            try (BufferedWriter write = new BufferedWriter(new FileWriter("saveFile.rtf"))) {
                write.write("" + points);
                write.newLine();
                write.write(nickName);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void loadData() throws IOException {
        int HSDigits;
        try {
            InputStream in;
            BufferedReader read = new BufferedReader(new FileReader("saveFile.rtf"));
            highScore = Integer.parseInt(read.readLine());
            nickName = read.readLine();

            read.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        int dontPrint = 0;
        System.out.print("                                                  ");
        for (int i = 0; i < MMHeight + 1; i++) {
            System.out.print("#");
        }
        System.out.print("\n");
        for (int i = 0; i < MMWidth; i++) {
            for (int j = 0; j < MMHeight; j++) {

                if (j == 0) {
                    System.out.print("                                                  ");
                    System.out.print("#");
                } else if (i == 3 && j == 15) {
                    System.out.print("High Score : ");
                    dontPrint += 13;
                } else if (i == 5 && j == 15) {
                    System.out.print(nickName + "           " + highScore);
                    dontPrint += nickName.length();
                    dontPrint += 11;
                    int clone = highScore;
                    HSDigits = 0;
                    while (clone!=0){
                        clone /=10;
                        HSDigits++;
                    }
                    dontPrint += HSDigits;

                } else if (i == 9 && j == 12) {
                    System.out.print("->");
                    dontPrint += 2;
                } else if (i == 9 && j == 15) {
                    System.out.print("Back");
                    dontPrint += 4;
                }


                if (dontPrint == 0) {
                    System.out.print(" ");
                } else if (dontPrint > 0) {
                    dontPrint--;
                }


                if (j == MMHeight - 1) {
                    System.out.print("#\n");
                }
            }
        }
        System.out.print("                                                  ");
        for (int i = 0; i < MMHeight + 1; i++) {
            System.out.print("#");
        }
        System.out.println("\n\n");

    }

    public static void HSPointer() throws AWTException, InterruptedException {
        Robot robot = new Robot();
        Scanner s = new Scanner(System.in);
        char input;

        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(100);

        try {
            switch (input = s.nextLine().charAt(0)) {

                case 'x':
                    highData = false;
                    running = false;
                case ' ':
                    highData = false;
                    mainMenu = true;
                    mainMenuMethod();
            }
        } catch (Exception e){}



    }
}
