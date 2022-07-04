import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String checkIfPlayerOwnIt;
    public static int currentPlayersPosition;
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
    public static String answer;
    public static int[] beddedMoney;
    public static String highestBedder;
    public static int highestBid;
    public static String playersBidAnswer;
    public static String beddingAnswer;
    public static String owner = highestBedder;
    public static boolean isItEvenBidding = false;
    public static void chosePlayersCountAndNames(){
        int count =1;
        System.out.println("Enter players count {2:4}");
        playersCount = scan.nextInt();
        positionArray = new int[playersCount];
        trackPlayersMoney = new int[playersCount];
        beddedMoney = new int[playersCount];
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
                if(!isItEvenBidding){
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
                    mediterraneanAvenue(i);
                }
                else{
                    isItEvenBidding(i);
                }
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
        System.out.println(currentPlayer+" position is "+positionArray[i]);
    }
    public static void trackingPlayersMoney(int i){
        System.out.println(currentPlayer+" money is "+trackPlayersMoney[i]);
    }
    public static void mediterraneanAvenue(int i){
        if(owner == null){
            if(positionArray[i] == 2){
                System.out.println(currentPlayer+" you've landed on Mediterranean Avenue!(BROWN COLOR)");
                System.out.println("Do you want to purchase the property for 60$?");
                System.out.println("If you chose not to buy the property it will be put up for action!");
                answer = scan.next();
                if(answer.equalsIgnoreCase("Yes")){
                    owner = playersName[i];
                    trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                    System.out.println(currentPlayer+ " balance is "+trackPlayersMoney[i]+"$");
                    System.out.println();
                    System.out.println(owner+" is now the owner of Mediterranean Avenue!");
                    System.out.println();
                    System.out.println(currentPlayer+ " If you land on this property again you can upgrade it for 50$");

                }
                else if(answer.equalsIgnoreCase(("No"))){
                    beddingAnswer = "Yes";
                    Biding(i);
                }
            }
        }
        else if(owner == highestBedder){
            System.out.println();
        }
    }
    public static void Biding(int i) {
            for (i = 0; i < playersName.length; i++) {
                System.out.println(playersName[i] + " do you want to bid?");
                System.out.println("Highest bid is " + highestBid);
                playersBidAnswer = scan.next();
                if (playersBidAnswer.equalsIgnoreCase("Yes")) {
                    highestBid = 0;
                    beddingAnswer = "yes";
                    isItEvenBidding = true;
                    System.out.println(playersName[i] + " how much you want to bid?");
                    beddedMoney[i] = scan.nextInt();
                    trackPlayersMoney[i] = trackPlayersMoney[i] - beddedMoney[i];
                    System.out.println(playersName[i] + " money is " + trackPlayersMoney[i]);
                    if (highestBid < beddedMoney[i]) {
                        highestBid = beddedMoney[i];
                        highestBedder = playersName[i];
                        System.out.println(highestBedder + " is the highest bedder with " + highestBid + "$ bet!");
                        owner = highestBedder;
                    }
                } else if (playersBidAnswer.equalsIgnoreCase("No")) {
                    isItEvenBidding = true;
                    beddedMoney[i] = 0;
                }
            }
            if (highestBedder == owner || owner == null) {
                isItEvenBidding = false;
                System.out.println("The new owner is " + owner);
                throwTwoDices();
            }
        }
    public static void isItEvenBidding(int i) {
        if(beddingAnswer.equalsIgnoreCase("yes")) {
            isItEvenBidding = true;
            Biding(i);
        }
        else {
            isItEvenBidding = false;
            throwTwoDices();
        }
    }
    public static void main(String[] args) {
        chosePlayersCountAndNames();
        shuffleTheOrder();
        playersStats();
        throwTwoDices();
    }
}
