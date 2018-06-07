package game;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class BoardGame {

    private boolean on;
    private Scanner sc;
    private int size;
    private int level;
    private int Board[][];
    private int move1;
    private int move2;
    private ScriptEngine engine;
    private Random generator;


    public BoardGame() throws FileNotFoundException, ScriptException {
        sc = new Scanner(System.in);
        engine = new ScriptEngineManager().getEngineByName("nashorn");
        engine.eval(new FileReader("algorithms.js"));
        generator = new Random();
        on = true;
    }

    private void start() throws IOException {
        clear();
        System.out.println("Podaj wielkość");
        size = sc.nextInt();
        Board = new int[size][size];

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Board[i][j] = 0;
            }
        }
        System.out.println("Podaj poziom   1 - latwy, 2 - trudny");
        level = sc.nextInt();
    }

    private void play() throws IOException, ScriptException, NoSuchMethodException {
        while(on){
            clear();
            printBoard();
            playerMove();
            computerMove();
            ifEnd();
        }
    }

    private void ifEnd() {
        int counter1 = 0;
        int counter2 = 0;
        // poziomo
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++){
                if(counter1 == 5){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                    System.out.println("Wygrales");
                }
                if(counter2 == 5){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                    System.out.println("Przegrales");
                }
                if(Board[i][j] == 1){
                    counter1++;
                }
                else {
                    counter1 = 0;
                }
                if(Board[i][j] == 2){
                    counter2++;
                }
                else {
                    counter2 = 0;
                }
            }
        }

        counter1 = 0;
        counter2 = 0;
        // pionowo
        for(int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++){
                if(counter1 == 5){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                }
                if(counter2 == 5){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                }
                if(Board[i][j] == 2){
                    counter2++;
                }
                else {
                    counter2 = 0;
                }
                if(Board[i][j] == 1){
                    counter1++;
                }
                else {
                    counter1 = 0;
                }
            }
        }
        // przekatne w prawo/dol
        for(int i = 0; i < size - 4; i++) {
            for(int j = 0; j < size - 4; j++){
                if(     Board[i][j] == 1 &&
                        Board[i + 1][j + 1] == 1 &&
                        Board[i + 2][j + 2] == 1 &&
                        Board[i + 3][j + 3] == 1 &&
                        Board[i + 4][j + 4] == 1){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                    System.out.println("Wygrales");
                }
                else if(Board[i][j] == 2 &&
                        Board[i + 1][j + 1] == 2 &&
                        Board[i + 2][j + 2] == 2 &&
                        Board[i + 3][j + 3] == 2 &&
                        Board[i + 4][j + 4] == 2){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                    System.out.println("Przegrales");
                }
            }
        }

        // przekatne w lewo/dol
        for(int i = 0; i < size - 4; i++) {
            for(int j = 4; j < size; j++){
                if(     Board[i][j] == 1 &&
                        Board[i + 1][j - 1] == 1 &&
                        Board[i + 2][j - 2] == 1 &&
                        Board[i + 3][j - 3] == 1 &&
                        Board[i + 4][j - 4] == 1){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                    System.out.println("Wygrales");
                }
                else if(Board[i][j] == 2 &&
                        Board[i + 1][j - 1] == 2 &&
                        Board[i + 2][j - 2] == 2 &&
                        Board[i + 3][j - 3] == 2 &&
                        Board[i + 4][j - 4] == 2){
                    on = false;
                    printBoard();
                    System.out.println("Koniec gry");
                    System.out.println("Przegrales");
                }
            }
        }
    }

    private void computerMove() throws FileNotFoundException, ScriptException, NoSuchMethodException {
        engine.eval(new FileReader("algorithms.js"));
        Invocable invocable = (Invocable) engine;
        Object result = "";
        switch (level){
            case 1:
                result = invocable.invokeFunction("move1", (Object) Board);
                break;
            case 2:
                result = invocable.invokeFunction("move2", (Object) Board);
                break;
        }

        String numbers = result.toString();
        String[] numbersArray = numbers.split(" ");
        if (numbersArray[0].equals("-1")){
            System.out.println("Koniec gry");
            System.out.println("Remis");
            on = false;
            printBoard();
        }
        else {
            int cmove1 = Integer.parseInt(numbersArray[0]);
            int cmove2 = Integer.parseInt(numbersArray[1]);
            paint(cmove1, cmove2, 2);
        }
    }

    private void playerMove() {
        boolean notok = true;
        System.out.println("Podaj ruch");
        move1 = sc.nextInt() - 1;
        move2 = sc.nextInt() - 1;
        while (notok){
            if(move1 >= size || move2 >= size){
                System.out.println("Zle podany ruch, podaj jeszcze raz");
                move1 = sc.nextInt() - 1;
                move2 = sc.nextInt() - 1;
            }
            else if (Board[move1][move2] == 1 || Board[move1][move2] == 2){
                System.out.println("Pole zajete, podaj inne");
                move1 = sc.nextInt() - 1;
                move2 = sc.nextInt() - 1;
            }
            else
                notok = false;
        }
        paint(move1, move2, 1);
    }

    private void paint(int move1, int move2, int who) {
        if(who == 1){
            Board[move1][move2] = 1;
        }
        else if (who == 2){
            Board[move1][move2] = 2;
        }
    }

    private void clear() throws IOException {
        for(int i = 0; i < 10; i++){
            System.out.println("\n\n\n");
        }
    }

    private void printBoard() {
        // 1 numeracja u gory
        System.out.print("   ");
        for(int i = 1; i <= size; i++){
            if (i < 10){
                System.out.print(i + " ");
            }
            else{
                System.out.print(i);
            }
        }
        System.out.println();

        // tablica + numeracja z lewej
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){

                // numeracja z lewej
                if(j == 0){
                    if (i < 9){
                        System.out.print(i + 1 + "  ");
                    }
                    else{
                        System.out.print(i + 1 + " ");
                    }
                }

                // tablica
                if(Board[i][j] == 0){
                    System.out.print(". ");
                }
                else if(Board[i][j] == 1){
                    System.out.print("X ");
                }
                else if(Board[i][j] == 2){
                    System.out.print("O ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void main(String args[]) throws IOException, ScriptException, NoSuchMethodException {
        BoardGame board = new BoardGame();
        board.start();
        board.play();
    }
}
