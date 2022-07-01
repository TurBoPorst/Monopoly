import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static int position = 0;
    public static String currentPlayer;
    public static boolean gameState;
    public static String playerOne;
    public static String playerTwo;
    public static String playerThree;
    public static String playerFour;
    public static Random roll = new Random();
    public static Scanner scan = new Scanner(System.in);
    public static String[] playersName;
    public static int playersCount;
    public static int firstDice = roll.nextInt(6) + 1;
    public static int secondDice = roll.nextInt(6) + 1;
    public static int[] positionArray;
    public static int[] trackPlayersMoney;
    public static void chosePlayersCountAndNames(){
        int count =1;
        System.out.println("Enter players count {2:4}");
        playersCount = scan.nextInt();
        positionArray = new int[playersCount];
        trackPlayersMoney = new int[playersCount];
        Arrays.fill(trackPlayersMoney,1500);
        playersName = new String[playersCount];
        if(playersCount > 1 && playersCount < 5) {
            for (int g = 0; g < playersName.length; g++) {
                System.out.println("Enter your " + count + " player name...");
                playersName[g] = scan.next();
                count++;
            }
        }
        else{
            System.out.println("Error");
            System.exit(0);
        }
    }
    public static void shuffleTheOrder(){
        for (int i = 0; i < playersName.length; i++) {
            int randomIndexToSwap = roll.nextInt(playersName.length);
            String temp = (playersName[randomIndexToSwap]);
            playersName[randomIndexToSwap] = playersName[i];
            playersName[i] = String.valueOf(temp);
        }
            System.out.println("The order in which the players will play is "+Arrays.toString(playersName));
            System.out.println();
    }
    public static void playersStats(){
       for(int i = 0; i < playersName.length;i++){
           if(playersCount == 2){
               playerOne = playersName[0];
               playerTwo = playersName[1];
           }
           else if(playersCount == 3){
               playerOne = playersName[0];
               playerTwo = playersName[1];
               playerThree = playersName[2];
           }
           else{
               playerOne = playersName[0];
               playerTwo = playersName[1];
               playerThree = playersName[2];
               playerFour = playersName[3];
           }
        }
    }
    public static void throwTwoDices(){
        do {
            for (int i = 0; i < playersName.length; i++) {
                currentPlayer = playersName[i];
                System.out.print(playersName[i] + " it's your turn!Press enter, to throw the dices!");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
                firstDice = roll.nextInt(6) + 1;
                secondDice = roll.nextInt(6) + 1;
                System.out.println(firstDice + "," + secondDice);
                trackingPosition(i);
                trackingPlayersMoney(i);
            }
        }
        while(!gameState);
    }
    public static void trackingPosition(int i){
        int count = 1;
        for(int g = 0; g < (firstDice + secondDice);g++){
            positionArray[i] = positionArray[i] + count;
            if(positionArray[i] == 40){
                positionArray[i] = 0;
            }
        }
        System.out.println(positionArray[i]);
    }
    public static void trackingPlayersMoney(int i){
        System.out.println(currentPlayer+" money is "+trackPlayersMoney[i]);
        trackPlayersMoney[i] = trackPlayersMoney[i] + 100;
        System.out.println(currentPlayer+" money is "+trackPlayersMoney[i]);
    }

    public static void main(String[] args) {
        chosePlayersCountAndNames();
        shuffleTheOrder();
        playersStats();
        throwTwoDices();

    }
}
