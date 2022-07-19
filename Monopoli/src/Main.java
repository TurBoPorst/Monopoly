import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static String currentPlayer;
    public static boolean gameState;
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
    public static int ownerMoney;
    public static boolean isItEvenBidding = false;
    public static String upgradeAnswer;
    public static int level = 1;
    public static boolean isPlayerInJain;
    public static String[] prisonName;
    public static int countDays;
    public static void chosePlayersCountAndNames() {
        int count = 1;
        System.out.println("Enter players count {2:4}");
        playersCount = scan.nextInt();
        positionArray = new int[playersCount];
        trackPlayersMoney = new int[playersCount];
        beddedMoney = new int[playersCount];
        Arrays.fill(trackPlayersMoney, 1500);
        playersName = new String[playersCount];
        prisonName = new String[playersCount];
        if (playersCount > 1 && playersCount < 5) {
            for (int i = 0; i < playersName.length; i++) {
                System.out.println("Enter your " + count + " player name...");
                playersName[i] = scan.next();
                count++;
            }
        } else {
            System.out.println("Error");
            System.exit(0);
        }
    }

    public static void shuffleTheOrder() {
        for (int i = 0; i < playersName.length; i++) {
            int randomIndexToSwap = roll.nextInt(playersName.length);
            String temp = (playersName[randomIndexToSwap]);
            playersName[randomIndexToSwap] = playersName[i];
            playersName[i] = String.valueOf(temp);
        }
        System.out.println("The order in which the players will play is " + Arrays.toString(playersName));
        System.out.println();
    }

    public static void throwTwoDices() {
        do {
                for (int i = 0; i < playersName.length; i++) {
                    currentPlayer = playersName[i];
                    if(playersName[i].equalsIgnoreCase("INMATE")){
                        getOutOfJail(i);
                        if(playersName[i].equalsIgnoreCase("INMATE")) {
                            if(countDays == 3){
                                System.out.println("You've served enough!");
                                System.out.println("You are free to go.");
                                playersName[i] = prisonName[i];
                                prisonName[i] = playersName[i];
                            }
                            else{
                                continue;
                            }
                        }
                    }
                    if (!isItEvenBidding) {
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
                        upgradeMediterraneanAvenue(i);
                        rentMediterraneanAvenue(i);
                        ToJail(i);
                    } else {
                        isItEvenBidding(i);
                    }
                }
        }
            while (!gameState) ;
    }

    public static void trackingPosition(int i){
        int count = 1;
        for(int g = 0; g < (firstDice + secondDice);g++){
            positionArray[i] = positionArray[i] + count;
            if(positionArray[i] == 40){
                positionArray[i] = 0;
            }
        }
        System.out.println(playersName[i]+" position is "+positionArray[i]);
    }
    public static void trackingPlayersMoney(int i){
        System.out.println(playersName[i]+" money is "+trackPlayersMoney[i]);
    }
    public static void mediterraneanAvenue(int i) {
        if (owner == null) {
            if (positionArray[i] == 2) {
                System.out.println(currentPlayer + " you've landed on Mediterranean Avenue!(BROWN COLOR)");
                System.out.println("Do you want to purchase the property for 60$?");
                System.out.println("If you chose not to buy the property it will be put up for action!");
                answer = scan.next();
                if (answer.equalsIgnoreCase("Yes")) {
                    owner = playersName[i];
                    trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                    System.out.println(currentPlayer + " balance is " + trackPlayersMoney[i] + "$");
                    System.out.println();
                    System.out.println(owner + " is now the owner of Mediterranean Avenue!");
                    ownerMoney = trackPlayersMoney[i];
                } else if (answer.equalsIgnoreCase(("No"))) {
                    beddingAnswer = "Yes";
                    Biding(i);
                }
            }
        }
    }
        public static void upgradeMediterraneanAvenue(int i) {
            if (positionArray[i] == 2 && owner == playersName[i]) {
                System.out.println("You are on your own property!(Mediterranean Avenue)");
                System.out.println("Do you want to upgrade it for 50$?");
                upgradeAnswer = scan.next();
                do {
                    if (upgradeAnswer.equalsIgnoreCase("Yes")) {
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 30;
                        level = level + 1;
                        System.out.println("Congrats! You've upgraded your property to " + level + " level!");
                        System.out.println();
                        System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                        System.out.println();
                        System.out.println("Do you want to upgrade it again?");
                        upgradeAnswer = scan.next();
                    }
                    else if (upgradeAnswer.equalsIgnoreCase("No")) {
                        System.out.println("Your property level is "+ level);
                        break;
                    }
                    if(level == 5){
                        System.out.println("You've reached the maximum level "+level+"!");
                        break;
                    }
                }
                while(level <= 4);
            }
        }
            public static void rentMediterraneanAvenue(int i) {
                if(owner == null){

                   }
                else if (!Objects.equals(playersName[i], owner) && positionArray[i] == 2) {
                    if (level == 1) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 10;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        ownerMoney = ownerMoney + 10;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 2) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 30;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        ownerMoney = ownerMoney + 10;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 3) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 90;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        ownerMoney = ownerMoney + 10;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 4) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 160;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        ownerMoney = ownerMoney + 10;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 5) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 250;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        ownerMoney = ownerMoney + 10;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    }
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
                    owner = null;
                }
            }
            if (owner == null) {
                isItEvenBidding = false;
                System.out.println("The new owner is no one!");
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
    public static void ToJail(int i){
        if(positionArray[i] == 11 || positionArray[i] == 31){
            prisonName[i] = playersName[i];
            playersName[i] = "INMATE";
            isPlayerInJain = true;
            System.out.println(prisonName[i]+ " you've landed in jail!");
            System.out.println(prisonName[i]+ " is now inmate!");
        }
    }
    public static void getOutOfJail(int i){
        if(isPlayerInJain) {
            System.out.println(prisonName[i] + " chose: ");
            System.out.println("1.You can get out of jail if you pay 50$!");
            System.out.println("2.You can roll doubles and if you don't roll doubles you can try for three turns and then you will get out for free!");
            System.out.println("3.You can use your get out of jail free card!");
            System.out.println("4.Just wait");
            int option = scan.nextInt();
            if (option == 1) {
                playersName[i] = prisonName[i];
                prisonName[i] = playersName[i];
                trackPlayersMoney[i] = trackPlayersMoney[i] - 50;
                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                System.out.println(playersName[i] + " you are now free to go!");
            } else if (option == 2) {
                System.out.print(prisonName[i] + " roll the dices!");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
                firstDice = roll.nextInt(6) + 1;
                secondDice = roll.nextInt(6) + 1;
                System.out.println(firstDice);
                System.out.println(secondDice);
                if (firstDice == secondDice) {
                    playersName[i] = prisonName[i];
                    prisonName[i] = playersName[i];
                    System.out.println("You are now free to go!");
                    System.out.println("Lucky you :)");
                } else {
                    System.out.println("Good luck next time!");
                }
            }
            else if (option == 3) {

            }
            else if(option == 4){
                countDays++;
                System.out.println("Days that have passed "+countDays);
            }
        }
    }
    public static void main(String[] args) {
        chosePlayersCountAndNames();
        shuffleTheOrder();
        throwTwoDices();
    }
}

