import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String checkIfPlayerOwnIt;
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
    public static String owner = null;
    public static String answer;
    public static String answerBid;
    public static int[] beddedMoney;
    public static String highestBedder;
    public static int highestBid;
    public static int noS;
    public static boolean isBiddingOn = false;
    public static boolean isItEvenBidding = false;
    public static String lastElement;
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
                    isBiddingOn(i);
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
                    System.out.println("Your balance is "+trackPlayersMoney[i]+"$");
                    System.out.println();
                    System.out.println(owner+" is now the owner of Mediterranean Avenue!");
                    System.out.println();
                    System.out.println("If you land on this property again you can upgrade it for 50$");
                }
                else if(answer.equalsIgnoreCase(("No"))){
                       Biding(i);
                }
            }
        }
    }
    public static void Biding(int i){
        do{
            highestBid = 0;
            lastElement = playersName[playersName.length - 1];
            if(playersName[i].equals(lastElement)){
                System.out.println(playersName[i] +" do you want to bid");

                answerBid = scan.next();
                if(answerBid.equalsIgnoreCase("yes")){
                    isItEvenBidding = true;
                    for(int z = 0; z < playersCount;z++){
                        System.out.println(playersName[i]+" how much you want to bid?");
                        System.out.println("Highest bid is 0!");
                        int amountOfBeddedMoney = scan.nextInt();
                        beddedMoney[i] = beddedMoney[i] + amountOfBeddedMoney;
                        trackPlayersMoney[i] = trackPlayersMoney[i] - amountOfBeddedMoney;
                        System.out.println(playersName[i]+" has bedded " +beddedMoney[i]);
                        if(highestBid < amountOfBeddedMoney){
                            beddedMoney[i] = 0;
                            if(highestBid < amountOfBeddedMoney){
                                highestBid = amountOfBeddedMoney;
                            }
                            System.out.println(playersName[i]+" has bedded the highest bid of " + highestBid);
                            highestBedder = playersName[i];
                            System.out.println("Highest bedder is "+highestBedder);

                            throwTwoDices();
                        }
                        else{
                            System.out.println("You've bedded less money than the highest bid of"+ highestBid);
                            throwTwoDices();
                        }
                    }
                }
                else{
                    noS(i);
                    throwTwoDices();
                }
            }
            else{
                System.out.println(playersName[i + 1] +" do you want to bid");
                answerBid = scan.next();
                if(answerBid.equalsIgnoreCase("yes")){
                    isItEvenBidding = true;
                    for(int z = 0; z < playersCount;z++){
                        System.out.println(playersName[i]+" how much you want to bid?");
                        System.out.println("Highest bid is 0!");
                        int amountOfBeddedMoney = scan.nextInt();
                        beddedMoney[i] = beddedMoney[i] + amountOfBeddedMoney;
                        trackPlayersMoney[i] = trackPlayersMoney[i] - amountOfBeddedMoney;
                        System.out.println(playersName[i]+" has bedded " +beddedMoney[i]);
                        if(highestBid < amountOfBeddedMoney){
                            beddedMoney[i] = 0;
                            if(highestBid < amountOfBeddedMoney){
                                highestBid = amountOfBeddedMoney;
                            }
                            System.out.println(playersName[i]+" has bedded the highest bid of " + highestBid);
                            highestBedder = playersName[i];
                            System.out.println("Highest bedder is "+highestBedder);
                            throwTwoDices();
                        }
                        else{
                            System.out.println("You've bedded less money than the highest bid of"+ highestBid);
                            throwTwoDices();
                        }
                    }
                }
                else{
                    noS(i);
                    throwTwoDices();
                }
            }
        }
        while(owner == playersName[i]);
    }
    public static void noS(int i){
        if(answerBid.equalsIgnoreCase("no")){
            if(playersCount == 2){
                noS++;
                if(noS == 1){
                    owner = playersName[i + 1];
                    System.out.println("The owner is " +owner);
                }
            }
            else if(playersCount == 3){
                noS++;
                if(noS == 2){
                    owner = playersName[i + 1];
                    System.out.println("The owner is " +owner);
                }
            }
            else{
                noS++;
                if(noS == 3){
                    owner = playersName[i + 1];
                    System.out.println("The owner is " +owner);
                }
            }
        }
        if(playersName[i].equals(lastElement)){
            playersName[i] = playersName[0];
            Biding(i);
        }
    }
    public static void isBiddingOn(int i){
        if(answerBid.equalsIgnoreCase("Yes")){
            isBiddingOn = true;
            Biding(i);
        }
        else{
            isBiddingOn = false;
        }
    }

    public static void main(String[] args) {
        chosePlayersCountAndNames();
        shuffleTheOrder();
        playersStats();
        throwTwoDices();

    }
}
