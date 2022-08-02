import java.util.*;

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
    public static String owner2 = highestBedder;
    public static String owner4 = highestBedder;
    public static String owner7 = highestBedder;
    public static int ownerMoney;
    public static boolean isItEvenBidding = false;
    public static String upgradeAnswer;
    public static int level = 1;
    public static boolean isPlayerInJain;
    public static String[] prisonName;
    public static int[] countDays;
    public static String[] playersCards;
    public static String[] cards = {
            "DRAW NEW CARD",
            "GO TO JAIL",
            "GET MONEY",
            "THROW THE DICES AGAIN",
            "PAY TAXES",
            "PAY MONEY",
            "GET OUT OF JAIL FREE",
            "PAY DOUBLE",
            "NOTHING",
            "SKIP PAYING",};

    public static int[] moneyToPay = {100, 150, 200, 250, 300, 350, 400};
    public static int[] moneyToGet = {50, 100, 150, 200, 250};
    public static int[] valueOfProperties;
    public static int[] valueOfAPlayer;
    public static String cardUsage;
    public static void chosePlayersCountAndNames() {
        int count = 1;
        System.out.println("Enter players count {2:4}");
        playersCount = scan.nextInt();
        valueOfProperties = new int[playersCount];
        valueOfAPlayer = new int[playersCount];
        playersCards = new String[4];
        positionArray = new int[playersCount];
        trackPlayersMoney = new int[playersCount];
        beddedMoney = new int[playersCount];
        Arrays.fill(trackPlayersMoney, 1500);
        playersName = new String[playersCount];
        prisonName = new String[playersCount];
        countDays = new int[playersCount];
        if (playersCount > 1 && playersCount < 5) {
            for (int i = 0; i < playersName.length; i++) {
                System.out.println("Enter your " + count + " player name...");
                playersCards[i] = "none";
                valueOfProperties[i] = 0;
                valueOfAPlayer[i] = 0;
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
                            if(countDays[i] == 4){
                                System.out.println("You've served enough!");
                                System.out.println("You are free to go.");
                                playersName[i] = prisonName[i];
                                prisonName[i] = playersName[i];
                                countDays[i] = 0;
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
                        //Gameplay
                        trackingPosition(i);
                        trackingPlayersMoney(i);
                        //Properties
                        mediterraneanAvenue(i);
                        balticAvenue(i);
                        orientalAvenue(i);
                        //Upgrade
                        upgradeMediterraneanAvenue(i);
                        upgradeBalticAvenue(i);
                        upgradeOrientalAvenue(i);
                        //Rents
                        rentMediterraneanAvenue(i);
                        rentBalticAvenue(i);
                        rentOrientalAvenue(i);
                        //Jail
                        InJail(i);
                        ToJail(i);
                        //Community chest/Chance
                        chanceCardsAndCommunityChests(i);
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
                if(positionArray[i] == 0){
                    startPositionBonus(i);
                }
            }
        }
        System.out.println(playersName[i]+" position is "+positionArray[i]);
    }
    public static void trackingPlayersMoney(int i){
        System.out.println(playersName[i]+" money is "+trackPlayersMoney[i]);
    }
    public static void mediterraneanAvenue(int i) {
        if (owner2 == null) {
            if (positionArray[i] == 2) {
                System.out.println(currentPlayer + " you've landed on Mediterranean Avenue!(BROWN COLOR)");
                System.out.println("Do you want to purchase the property for 60$?");
                System.out.println("If you chose not to buy the property it will be put up for action!");
                answer = scan.next();
                if (answer.equalsIgnoreCase("Yes")) {
                    owner2 = playersName[i];
                    trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                    System.out.println(currentPlayer + " balance is " + trackPlayersMoney[i] + "$");
                    System.out.println();
                    System.out.println(owner2 + " is now the owner of Mediterranean Avenue!");
                    ownerMoney = trackPlayersMoney[i];
                } else if (answer.equalsIgnoreCase(("No"))) {
                    beddingAnswer = "Yes";
                    Biding(i);
                }
            }
        }
    }
        public static void upgradeMediterraneanAvenue(int i) {
            if (positionArray[i] == 2 && owner2.equals(playersName[i])) {
                if(level == 5){
                    System.out.println("This property is max level!");
                }
                System.out.println("You are on your own property!(Mediterranean Avenue)");
                System.out.println("Do you want to upgrade it for 50$?");
                upgradeAnswer = scan.next();
                do {
                    if (upgradeAnswer.equalsIgnoreCase("Yes")) {
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 50;
                        level = level + 1;
                        System.out.println("Congrats! You've upgraded your property to " + level + " level!");
                        if(level == 2){
                            System.out.println("The rent that other players need to pay is 30$");
                            valueOfProperties[i] = valueOfProperties[i] + 50;
                        }
                        else if(level == 3){
                            System.out.println("The rent that other players need to pay is 90$");
                            valueOfProperties[i] = valueOfProperties[i] + 50;
                        }
                        else if(level == 4){
                            System.out.println("The rent that other players need to pay is 160$");
                            valueOfProperties[i] = valueOfProperties[i] + 50;
                        }
                        else if(level == 5){
                            System.out.println("The rent that other players need to pay is 250$");
                            valueOfProperties[i] = valueOfProperties[i] + 50;
                        }
                        System.out.println();
                        System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                        System.out.println();
                        System.out.println("Do you want to upgrade it again?");
                        upgradeAnswer = scan.next();
                    }
                    else if (upgradeAnswer.equalsIgnoreCase("No")) {
                        System.out.println("Your property level is "+ level);
                        valueOfProperties[i] = valueOfProperties[i] + 60;
                        System.out.println("The rent that other players need to pay is 10$");
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
                if (owner2 == null) {
                    mediterraneanAvenue(i);
                }
                if (playersCards[i].equalsIgnoreCase("SKIP PAYING") && positionArray[i] == 2 && !Objects.equals(playersName[i], owner2)) {
                    System.out.println(playersName[i]+ " have to pay rent!");
                    System.out.println(currentPlayer + " has SKIP PAYING card!");
                    System.out.println("Do you want to use it?");
                    cardUsage = scan.next();
                    if (cardUsage.equalsIgnoreCase("Yes")) {
                        if (playersCards[i].contains("GET OUT OF JAIL FREE") || playersCards[i].contains("THEY PAY YOU DOUBLE")) {
                            playersCards[i].replace("SKI PAYING", "none");
                        }
                        System.out.println("You skipped the payment for the rent!");
                    }
                } else {
                    if (!Objects.equals(playersName[i], owner2) && positionArray[i] == 2) {
                        System.out.println(currentPlayer + " landed on foreign property!");
                        if (level == 1) {
                            if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                System.out.println("You pay double the rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 20;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner7 + " 20$");
                                ownerMoney = ownerMoney + 20;
                            }
                            else{
                                System.out.println("You need to pay rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 10;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner2 + " 10$");
                                ownerMoney = ownerMoney + 10;
                            }
                            System.out.println(owner2 + " money is " + ownerMoney);
                            System.out.println();
                        } else if (level == 2) {
                            if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                System.out.println("You pay double the rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner7 + " 60$");
                                ownerMoney = ownerMoney + 60;
                            }
                            else{
                                System.out.println("You need to pay rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 30;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner2 + " 30$");
                                ownerMoney = ownerMoney + 30;
                            }
                            System.out.println(owner2 + " money is " + ownerMoney);
                            System.out.println();
                        } else if (level == 3) {
                            if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                System.out.println("You pay double the rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 180;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner7 + " 180$");
                                ownerMoney = ownerMoney + 180;
                            }
                            else{
                                System.out.println("You need to pay rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 90;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner2 + " 90$");
                                ownerMoney = ownerMoney + 90;
                            }
                            System.out.println(owner2 + " money is " + ownerMoney);
                            System.out.println();
                        } else if (level == 4) {
                            if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                System.out.println("You pay double the rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 320;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner7 + " 320$");
                                ownerMoney = ownerMoney + 320;
                            }
                            else{
                                System.out.println("You need to pay rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 160;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner2 + " 160$");
                                ownerMoney = ownerMoney + 160;
                            }
                            System.out.println(owner2 + " money is " + ownerMoney);
                            System.out.println();
                        } else if (level == 5) {
                            if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                System.out.println("You pay double the rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 500;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner7 + " 500$");
                                ownerMoney = ownerMoney + 500;
                            }
                            else{
                                System.out.println("You need to pay rent!");
                                trackPlayersMoney[i] = trackPlayersMoney[i] - 250;
                                System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                System.out.println(currentPlayer + " paid " + owner2 + " 250$");
                                ownerMoney = ownerMoney + 250;
                            }
                            System.out.println(owner2 + " money is " + ownerMoney);
                            System.out.println();
                        }
                    }
                }
            }
           public static void balticAvenue(int i){
               if (owner4 == null) {
                   if (positionArray[i] == 4) {
                       System.out.println(currentPlayer + " you've landed on Baltic Avenue!(BROWN COLOR)");
                       System.out.println("Do you want to purchase the property for 60$?");
                       System.out.println("If you chose not to buy the property it will be put up for action!");
                       answer = scan.next();
                       if (answer.equalsIgnoreCase("Yes")) {
                           owner4 = playersName[i];
                           trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                           System.out.println(currentPlayer + " balance is " + trackPlayersMoney[i] + "$");
                           System.out.println();
                           System.out.println(owner4 + " is now the owner of Baltic Avenue!");
                           ownerMoney = trackPlayersMoney[i];
                       } else if (answer.equalsIgnoreCase(("No"))) {
                           beddingAnswer = "Yes";
                           Biding(i);
                       }
                   }
               }
           }
           public static void upgradeBalticAvenue(int i){
               if (positionArray[i] == 4 && owner4 == playersName[i]) {
                   if(level == 5){
                       System.out.println("This property is max level!");
                   }
                   System.out.println("You are on your own property!(Baltic Avenue)");
                   System.out.println("Do you want to upgrade it for 50$?");
                   upgradeAnswer = scan.next();
                   do {
                       if (upgradeAnswer.equalsIgnoreCase("Yes")) {
                           trackPlayersMoney[i] = trackPlayersMoney[i] - 50;
                           level = level + 1;
                           System.out.println("Congrats! You've upgraded your property to " + level + " level!");
                           if(level == 2){
                               System.out.println("The rent that other players need to pay is 60$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           else if(level == 3){
                               System.out.println("The rent that other players need to pay is 180$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           else if(level == 4){
                               System.out.println("The rent that other players need to pay is 320$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           else if(level == 5){
                               System.out.println("The rent that other players need to pay is 450$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           System.out.println();
                           System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                           System.out.println();
                           System.out.println("Do you want to upgrade it again?");
                           upgradeAnswer = scan.next();
                       }
                       else if (upgradeAnswer.equalsIgnoreCase("No")) {
                           System.out.println("Your property level is "+ level);
                           valueOfProperties[i] = valueOfProperties[i] + 60;
                           System.out.println("The rent that other players need to pay is 20$");
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
           public static void rentBalticAvenue(int i) {
               if (owner4 == null) {
                   balticAvenue(i);
               }
               if (playersCards[i].equalsIgnoreCase("SKIP PAYING") && positionArray[i] == 4 && !Objects.equals(playersName[i], owner4)) {
                   System.out.println(playersName[i] + " have to pay rent!");
                   System.out.println(currentPlayer + " has SKIP PAYING card!");
                   System.out.println("Do you want to use it?");
                   cardUsage = scan.next();
                   if (cardUsage.equalsIgnoreCase("Yes")) {
                       if (playersCards[i].contains("GET OUT OF JAIL FREE") || playersCards[i].contains("THEY PAY YOU DOUBLE")) {
                           playersCards[i].replace("SKI PAYING", "none");
                       }
                       System.out.println("You skipped the payment for the rent!");
                   }
               }
               else {
                       if (!Objects.equals(playersName[i], owner4) && positionArray[i] == 4) {
                           System.out.println(currentPlayer + " landed on foreign property!");
                           if (level == 1) {
                               if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                   System.out.println("You pay double the rent!");
                                   trackPlayersMoney[i] = trackPlayersMoney[i] - 40;
                                   System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                   System.out.println(currentPlayer + " paid " + owner7 + " 40$");
                                   ownerMoney = ownerMoney + 40;
                               }
                               else{
                                   System.out.println("You need to pay rent!");
                                   trackPlayersMoney[i] = trackPlayersMoney[i] - 20;
                                   System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                   System.out.println(currentPlayer + " paid " + owner4 + " 20$");
                                   ownerMoney = ownerMoney + 20;
                               }
                               System.out.println(owner4 + " money is " + ownerMoney);
                               System.out.println();
                           } else if (level == 2) {
                               if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                   System.out.println("You pay double the rent!");
                                   trackPlayersMoney[i] = trackPlayersMoney[i] - 120;
                                   System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                   System.out.println(currentPlayer + " paid " + owner7 + " 120$");
                                   ownerMoney = ownerMoney + 120;
                               }
                               else{
                                   System.out.println("You need to pay rent!");
                                   trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                                   System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                   System.out.println(currentPlayer + " paid " + owner4 + " 60$");
                                   ownerMoney = ownerMoney + 60;
                               }
                               System.out.println(owner4 + " money is " + ownerMoney);
                               System.out.println();
                           }
                       } else if (level == 3) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 360;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 360$");
                               ownerMoney = ownerMoney + 360;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 180;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner4 + " 180$");
                               ownerMoney = ownerMoney + 180;
                           }
                           System.out.println(owner4 + " money is " + ownerMoney);
                           System.out.println();
                       } else if (level == 4) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 640;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 640$");
                               ownerMoney = ownerMoney + 640;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 320;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner4 + " 320$");
                               ownerMoney = ownerMoney + 320;
                           }
                           System.out.println(owner4 + " money is " + ownerMoney);
                           System.out.println();
                       } else if (level == 5) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 900;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 900$");
                               ownerMoney = ownerMoney + 900;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 450;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner4 + " 450$");
                               ownerMoney = ownerMoney + 450;
                           }
                           System.out.println(owner4 + " money is " + ownerMoney);
                           System.out.println();
                       }
                   }
               }
           public static void orientalAvenue(int i){
               if (owner7 == null) {
                   if (positionArray[i] == 7) {
                       System.out.println(currentPlayer + " you've landed on Oriental Avenue!(LIGHT BLUE COLOR)");
                       System.out.println("Do you want to purchase the property for 100$?");
                       System.out.println("If you chose not to buy the property it will be put up for action!");
                       answer = scan.next();
                       if (answer.equalsIgnoreCase("Yes")) {
                           owner7 = playersName[i];
                           trackPlayersMoney[i] = trackPlayersMoney[i] - 100;
                           System.out.println(currentPlayer + " balance is " + trackPlayersMoney[i] + "$");
                           System.out.println();
                           System.out.println(owner7 + " is now the owner of Oriental Avenue!");
                           ownerMoney = trackPlayersMoney[i];
                       } else if (answer.equalsIgnoreCase(("No"))) {
                           beddingAnswer = "Yes";
                           Biding(i);
                       }
                   }
               }
           }
           public static void upgradeOrientalAvenue(int i){
                if (positionArray[i] == 7 && owner7 == playersName[i]) {
                    if(level == 5){
                        System.out.println("This property is max level!");
                    }
                   System.out.println("You are on your own property!(Baltic Avenue)");
                   System.out.println("Do you want to upgrade it for 50$?");
                   upgradeAnswer = scan.next();
                   do {
                       if (upgradeAnswer.equalsIgnoreCase("Yes")) {
                           trackPlayersMoney[i] = trackPlayersMoney[i] - 50;
                           level = level + 1;
                           System.out.println("Congrats! You've upgraded your property to " + level + " level!");
                           if(level == 2){
                               System.out.println("The rent that other players need to pay is 90$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           else if(level == 3){
                               System.out.println("The rent that other players need to pay is 270$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           else if(level == 4){
                               System.out.println("The rent that other players need to pay is 400$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           else if(level == 5){
                               System.out.println("The rent that other players need to pay is 550$");
                               valueOfProperties[i] = valueOfProperties[i] + 50;
                           }
                           System.out.println();
                           System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                           System.out.println();
                           System.out.println("Do you want to upgrade it again?");
                           upgradeAnswer = scan.next();
                       }
                       else if (upgradeAnswer.equalsIgnoreCase("No")) {
                           System.out.println("Your property level is "+ level);
                           valueOfProperties[i] = valueOfProperties[i] + 100;
                           System.out.println("The rent that other players need to pay is 30$");
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
           public static void rentOrientalAvenue(int i){
               if(owner7 == null){
                   orientalAvenue(i);
               }
               if(playersCards[i].equalsIgnoreCase("SKIP PAYING") && positionArray[i] == 7 && !Objects.equals(playersName[i], owner7)) {
                   System.out.println(playersName[i]+ " have to pay rent!");
                   System.out.println(currentPlayer + " has SKIP PAYING card!");
                   System.out.println("Do you want to use it?");
                   cardUsage = scan.next();
                   if (cardUsage.equalsIgnoreCase("Yes")) {
                       if (playersCards[i].contains("GET OUT OF JAIL FREE") || playersCards[i].contains("THEY PAY YOU DOUBLE")) {
                           playersCards[i].replace("SKI PAYING", "none");
                       }
                       System.out.println("You skipped the payment for the rent!");
                   }
               }
                   else{
                       if (!Objects.equals(playersName[i], owner7) && positionArray[i] == 7) {
                           System.out.println(currentPlayer + " landed on foreign property!");
                           if (level == 1) {
                               if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                                   System.out.println("You pay double the rent!");
                                   trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                                   System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                   System.out.println(currentPlayer + " paid " + owner7 + " 60$");
                                   ownerMoney = ownerMoney + 60;
                               }
                               else{
                                   trackPlayersMoney[i] = trackPlayersMoney[i] - 30;
                                   System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                                   System.out.println(currentPlayer + " paid " + owner7 + " 30$");
                                   ownerMoney = ownerMoney + 30;
                               }
                               System.out.println(owner7 + " money is " + ownerMoney);
                               System.out.println();
                           }
                       }
                       else if (level == 2) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 180;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 180$");
                               ownerMoney = ownerMoney + 180;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 90;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 90$");
                               ownerMoney = ownerMoney + 90;
                           }
                           System.out.println(owner7 + " money is " + ownerMoney);
                           System.out.println();
                       }
                       else if (level == 3) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 540;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 540$");
                               ownerMoney = ownerMoney + 540;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 270;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 270$");
                               ownerMoney = ownerMoney + 270;
                           }
                           System.out.println(owner7 + " money is " + ownerMoney);
                           System.out.println();
                       }
                       else if (level == 4) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 800;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 800$");
                               ownerMoney = ownerMoney + 800;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 400;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 400$");
                               ownerMoney = ownerMoney + 400;
                           }
                           System.out.println(owner7 + " money is " + ownerMoney);
                           System.out.println();
                       }
                       else if (level == 5) {
                           if(playersCards[i].equalsIgnoreCase("PAY DOUBLE")){
                               System.out.println("You pay double the rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 1100;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 1100$");
                               ownerMoney = ownerMoney + 1100;
                           }
                           else{
                               System.out.println("You need to pay rent!");
                               trackPlayersMoney[i] = trackPlayersMoney[i] - 550;
                               System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                               System.out.println(currentPlayer + " paid " + owner7 + " 550$");
                               ownerMoney = ownerMoney + 550;
                           }
                           System.out.println(owner7 + " money is " + ownerMoney);
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
                        owner7 = highestBedder;
                    }
                } else if (playersBidAnswer.equalsIgnoreCase("No")) {
                    isItEvenBidding = true;
                    beddedMoney[i] = 0;
                    owner7 = null;
                }
            }
            if (owner7 == null) {
                isItEvenBidding = false;
                System.out.println("The new owner is none!");
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
    public static void InJail(int i){
        if(positionArray[i] == 11 ){
            prisonName[i] = playersName[i];
            playersName[i] = "INMATE";
            isPlayerInJain = true;
            System.out.println(prisonName[i]+ " you've landed in jail!");
            System.out.println(prisonName[i]+ " is now inmate!");
        }
    }
    public static void ToJail(int i){
        if(positionArray[i] == 31){
            System.out.println(currentPlayer+ " is escorted to jail!");
            prisonName[i] = playersName[i];
            playersName[i] = "INMATE";
            isPlayerInJain = true;
            InJail(i);
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
                System.out.println("Good luck!");
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
                    System.out.println("Better luck next time!");
                }
            }
            else if (option == 3) {
                 if(playersCards[i].equalsIgnoreCase("GET OUT OF JAIL FREE")) {
                     System.out.println("Do you want to use your GET OUT OF JAIL FREE CARD ?");
                     cardUsage = scan.next();
                     if (cardUsage.equalsIgnoreCase("Yes")) {
                         if(playersCards[i].contains("SKIP PAYING") || playersCards[i].contains("THEY PAY YOU DOUBLE")){
                             playersCards[i].replace("GET OUT OF JAIL FREE","none");
                         }
                         playersName[i] = prisonName[i];
                         prisonName[i] = playersName[i];
                         System.out.println("You are free to go!");
                     } else if (cardUsage.equalsIgnoreCase("No")) {
                         System.out.println("I guess you like where you are!");
                     }
                 }
                 else{
                     System.out.println("You don't have GET OUT OF JAIL FREE card!");
                     getOutOfJail(i);
                 }
            }
            else if(option == 4){
                System.out.println("Days that have passed "+countDays[i]);
            }
            countDays[i]++;
        }
    }
    public static void chanceCardsAndCommunityChests(int i){
        if(positionArray[i] == 3 || positionArray[i] == 8 || positionArray[i] == 18 || positionArray[i] == 23 || positionArray[i] == 34) {
            System.out.println("You've landed on chance/community chest!");
            System.out.println("Draw a card!");
            try {
                System.in.read();
            } catch (Exception e) {
            }
            Random random = new Random();
            int randomCard = random.nextInt(cards.length);
            System.out.println(cards[randomCard]);
            if (cards[randomCard].equalsIgnoreCase("DRAW NEW CARD")) {
                chanceCardsAndCommunityChests(i);
            }
            else if (cards[randomCard].equalsIgnoreCase("PAY MONEY")) {
                System.out.println(playersName[i] +" drawn PAY MONEY card!");
                int randomMoneyAmount = random.nextInt(moneyToPay.length);
                System.out.println("You have to pay " + moneyToPay[randomMoneyAmount]+"$");
                trackPlayersMoney[i] = trackPlayersMoney[i] - moneyToPay[randomMoneyAmount];
                System.out.println(playersName[i] + " your money is " + trackPlayersMoney[i] + "$");
            }
            else if (cards[randomCard].equalsIgnoreCase("GO TO JAIL")) {
                System.out.println(playersName[i]+ " drawn GO TO JAIL");
                prisonName[i] = playersName[i];
                playersName[i] = "INMATE";
                isPlayerInJain = true;
                System.out.println(playersName[i]+ " you are escorted to jail!");

            }
            else if (cards[randomCard].equalsIgnoreCase("GET OUT OF JAIL FREE")) {
                System.out.println(playersName[i]+ " drawn GET OUT OF JAIL FREE card");
                System.out.println("You can save it and use later!");
                playersCards[i] = "GET OUT OF JAIL FREE";
            }
            else if (cards[randomCard].equalsIgnoreCase(("THROW THE DICES AGAIN"))) {
                System.out.println(playersName[i]+ " drawn THROW THE DICES AGAIN card");
                System.out.println("Throw the dices");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
                firstDice = roll.nextInt(6) + 1;
                secondDice = roll.nextInt(6) + 1;
                System.out.println(firstDice + "," + secondDice);
                positionArray[i] = positionArray[i] + firstDice + secondDice;
                System.out.println(playersName[i]+ " position is " + positionArray[i]);
            }
            else if(cards[randomCard].equalsIgnoreCase("GET MONEY")){
                System.out.println(playersName[i]+" drawn GET MONEY card");
                int randomMoneyAmountToGet = random.nextInt(moneyToGet.length);
                trackPlayersMoney[i] = trackPlayersMoney[i] + moneyToGet[randomMoneyAmountToGet];
                System.out.println(playersName[i]+ " got paid "+moneyToGet[randomMoneyAmountToGet]);
                System.out.println(playersName[i]+" money is "+ trackPlayersMoney[i]);
            }
            else if(cards[randomCard].equalsIgnoreCase("GO TO THE START")){
                System.out.println(playersName[i]+" drawn GO TO THE START card");
                positionArray[i] = 0;
                startPositionBonus(i);
            }
            else if(cards[randomCard].equalsIgnoreCase("PAY TAXES")){
                System.out.println(playersName[i]+" drawn PAY TAXES card");
                valueOfAPlayer[i] = trackPlayersMoney[i] + valueOfProperties[i];
                System.out.println(playersName[i]+ " have to pay 200$ or 10% of your total worth(Properties too)!");
                System.out.println("1.200$ 2.10%");
                int taxAnswer = scan.nextInt();
                if(taxAnswer == 1){
                    trackPlayersMoney[i] = trackPlayersMoney[i] - 200;
                    System.out.println(playersName[i]+ " paid 200$ for taxes!");
                    System.out.println(playersName[i]+ "money is "+trackPlayersMoney[i]);
                }
                else if(taxAnswer == 2){
                    trackPlayersMoney[i] = (int) (valueOfAPlayer[i] -((0.10 * valueOfAPlayer[i] )));
                    System.out.println("You paid 10% of your total worth!");
                    System.out.println(playersName[i]+ "money is "+trackPlayersMoney[i]);
                }
            }
            else if(cards[randomCard].equalsIgnoreCase("SKIP PAYING")){
                System.out.println(playersName[i]+ " drawn SKIP PAYING card !");
                System.out.println("You can save it and use it later!");
                playersCards[i] = "SKIP PAYING";
            }
            else if(cards[randomCard].equalsIgnoreCase("PAY DOUBLE")){
                System.out.println(playersName[i]+ "drawn PAY DOUBLE card");
                System.out.println("Your next payment for the rent will be doubled!");
                playersCards[i] = "PAY DOUBLE";
            }
        }
    }
    public static void startPositionBonus(int i){
        if(positionArray[i] == 0){
            System.out.println(playersName[i]+" arrived on the start!");
            System.out.println(playersName[i]+" got paid 200$!");
            trackPlayersMoney[i] = trackPlayersMoney[i] + 200;
        }
    }
    public static void main(String[] args) {
        chosePlayersCountAndNames();
        shuffleTheOrder();
        throwTwoDices();
    }
}

