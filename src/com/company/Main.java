package com.company;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int width = 30;
    static int height = 31;


    static int possitionY = width -2 , possitionX = height / 2;

    static int RIGHT = 0, LEFT = 0;

    static int METEOR1x, METEOR1y, METEOR2x,METEOR2y, METEOR3x,METEOR3y, METEOR4x,METEOR4y,METEOR5y,METEOR5x;

   static int shootPosX, shootPosY;

   static int realPosX, K;

   static boolean shoot;




    static boolean running = true;



    public static void main(String[] args) throws InterruptedException {
        int prints = 0;
        createMeteors();

        while (running){
            System.out.print("\033[H\033[2J");
            draw();
            Input();
            Thread.sleep(500);
            System.out.println(realPosX);
        }

    }

    public static void draw(){

        System.out.print(" ");
        for (int i = 0; i < width + 1; i++){
            System.out.print("#");
        }
        System.out.print("\n ");

        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){

                if (j == 0 ){
                    System.out.print("#");
                }

                if (j == width - 1){
                    System.out.print("#\n");
                }

                if(possitionY == i && possitionX == j + LEFT - RIGHT){
                    System.out.print("^");
                    K = j;
                }

                if (METEOR1x == i && METEOR1y == j){

                    System.out.print("O");
                }

                if (shoot){
                    if(shootPosX == i && shootPosY == j){
                        System.out.print("|");
                    }
                }

                if (shootPosX == METEOR1x -1 && shootPosY == METEOR1y){
                    shoot = false;
                    shootPosX = 100;
                    shootPosY = 100;
                    METEOR1x-= METEOR1x;
                    METEOR1y-= METEOR1y;
                    Random random = new Random();
                    int upperBound = width;
                    METEOR1x = 0;
                    METEOR1y = random.nextInt(upperBound);
                }

                meteorHit();

                if( possitionY == i && possitionX == j + LEFT - RIGHT || METEOR1x == i && METEOR1y == j || shootPosX == i && shootPosY == j){
                    System.out.print("");
                } else {
                    System.out.print(" ");
                }
            }
        }

        for (int i = 0; i < width; i++){
            System.out.print("#");
        }
        METEOR1x++;
        shootPosX--;
        realPosX = possitionX - LEFT + RIGHT;

    }

    public static void Input(){
        Scanner s = new Scanner(System.in);
        char input ;
        try{
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
                shoot = true;
                shootPosY = realPosX;
                shootPosX = possitionY - 1;
                break;
        }} catch (Exception e){
            System.out.print("\033[H\033[2J");
        }

    }

    static public void meteorHit(){

        if (shootPosX == METEOR1x -1 && shootPosY == METEOR1y){
            shoot = false;
            shootPosX = 100;
            shootPosY = 100;
            METEOR1x-= METEOR1x;
            METEOR1y-= METEOR1y;
            Random random = new Random();
            int upperBound = width;
            METEOR1x = 0;
            METEOR1y = random.nextInt(upperBound);
        }
    }



    static public void createMeteors() {
        Random random = new Random();
        int upperBound = width;
        METEOR1x = 0;
        METEOR1y = random.nextInt(upperBound);
/*
        METEOR2x = 0;
        METEOR2y = random.nextInt(upperBound);

        METEOR3x = 0;
        METEOR3y = random.nextInt(upperBound);

        METEOR4x = 0;
        METEOR4y = random.nextInt(upperBound);

        METEOR5x = 0;
        METEOR5y = random.nextInt(upperBound);
*/
    }
}
