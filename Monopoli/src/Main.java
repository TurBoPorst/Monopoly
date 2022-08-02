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
    public static String owner = highestBedder;
    public static int ownerMoney;
    public static boolean isItEvenBidding = false;
    public static String upgradeAnswer;
    public static int level = 1;
    public static boolean isPlayerInJain;
    public static String[] prisonName;
    public static int[] countDays;
    public static String[] playersCards;
    public static String[] cards = {"DRAW NEW CARD",
            "PAY MONEY",
            "GO TO JAIL",
            "GET OUT OF JAIL FREE",
            "THROW THE DICES AGAIN",
            "GET MONEY", "GO TO THE START",
            "PAY TAXES", "SKIP PAYING",
            "DOUBLE PAYMENT",
            "THEY PAY YOU DOUBLE"};
    public static int[] moneyToPay = {100, 150, 200, 250, 300, 350, 400};
    public static void chosePlayersCountAndNames() {
        int count = 1;
        System.out.println("Enter players count {2:4}");
        playersCount = scan.nextInt();
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
                        //Start Bonus
                        startPositionBonus(i);
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
    public static void startPositionBonus(int i){
        if(positionArray[i] == 0){
            trackPlayersMoney[i] = trackPlayersMoney[i] + 200;
            System.out.println(playersName[i]+" arrived on the start!");
            System.out.println(playersName[i]+" got paid 200$!");
        }
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
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 50;
                        level = level + 1;
                        System.out.println("Congrats! You've upgraded your property to " + level + " level!");
                        if(level == 2){
                            System.out.println("The rent that other players need to pay is 30$");
                        }
                        else if(level == 3){
                            System.out.println("The rent that other players need to pay is 90$");
                        }
                        else if(level == 4){
                            System.out.println("The rent that other players need to pay is 160$");
                        }
                        else if(level == 5){
                            System.out.println("The rent that other players need to pay is 250$");
                        }
                        System.out.println();
                        System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                        System.out.println();
                        System.out.println("Do you want to upgrade it again?");
                        upgradeAnswer = scan.next();
                    }
                    else if (upgradeAnswer.equalsIgnoreCase("No")) {
                        System.out.println("Your property level is "+ level);
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
                if(owner == null){
                    mediterraneanAvenue(i);
                   }
                else if (!Objects.equals(playersName[i], owner) && positionArray[i] == 2) {
                    System.out.println(currentPlayer+ " landed on foreign property!");
                    if (level == 1) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 10;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        System.out.println(currentPlayer+ "paid " +owner+ " 10$");
                        ownerMoney = ownerMoney + 10;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 2) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 30;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        System.out.println(currentPlayer+ "paid " +owner+ " 30$");
                        ownerMoney = ownerMoney + 30;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 3) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 90;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        System.out.println(currentPlayer+ "paid " +owner+ " 90$");
                        ownerMoney = ownerMoney + 90;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 4) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 160;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        System.out.println(currentPlayer+ "paid " +owner+ " 160$");
                        ownerMoney = ownerMoney + 160;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    } else if (level == 5) {
                        System.out.println("You need to pay rent!");
                        trackPlayersMoney[i] = trackPlayersMoney[i] - 250;
                        System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                        System.out.println(currentPlayer+ "paid " +owner+ " 250$");
                        ownerMoney = ownerMoney + 250;
                        System.out.println(owner + " money is " + ownerMoney);
                        System.out.println();
                    }
                }
            }
           public static void balticAvenue(int i){
               if (owner == null) {
                   if (positionArray[i] == 4) {
                       System.out.println(currentPlayer + " you've landed on Baltic Avenue!(BROWN COLOR)");
                       System.out.println("Do you want to purchase the property for 60$?");
                       System.out.println("If you chose not to buy the property it will be put up for action!");
                       answer = scan.next();
                       if (answer.equalsIgnoreCase("Yes")) {
                           owner = playersName[i];
                           trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                           System.out.println(currentPlayer + " balance is " + trackPlayersMoney[i] + "$");
                           System.out.println();
                           System.out.println(owner + " is now the owner of Baltic Avenue!");
                           ownerMoney = trackPlayersMoney[i];
                       } else if (answer.equalsIgnoreCase(("No"))) {
                           beddingAnswer = "Yes";
                           Biding(i);
                       }
                   }
               }
           }
           public static void upgradeBalticAvenue(int i){
               if (positionArray[i] == 4 && owner == playersName[i]) {
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
                           }
                           else if(level == 3){
                               System.out.println("The rent that other players need to pay is 180$");
                           }
                           else if(level == 4){
                               System.out.println("The rent that other players need to pay is 320$");
                           }
                           else if(level == 5){
                               System.out.println("The rent that other players need to pay is 450$");
                           }
                           System.out.println();
                           System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                           System.out.println();
                           System.out.println("Do you want to upgrade it again?");
                           upgradeAnswer = scan.next();
                       }
                       else if (upgradeAnswer.equalsIgnoreCase("No")) {
                           System.out.println("Your property level is "+ level);
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
           public static void rentBalticAvenue(int i){
               if(owner == null){
                   balticAvenue(i);
               }
               else if (!Objects.equals(playersName[i], owner) && positionArray[i] == 4) {
                   System.out.println(currentPlayer+ " landed on foreign property!");
                   if (level == 1) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 20;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 20$");
                       ownerMoney = ownerMoney + 20;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 2) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 60;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 60$");
                       ownerMoney = ownerMoney + 60;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 3) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 180;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 180$");
                       ownerMoney = ownerMoney + 180;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 4) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 320;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 320$");
                       ownerMoney = ownerMoney + 320;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 5) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 450;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 450$");
                       ownerMoney = ownerMoney + 450;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   }
               }
           }
           public static void orientalAvenue(int i){
               if (owner == null) {
                   if (positionArray[i] == 7) {
                       System.out.println(currentPlayer + " you've landed on Oriental Avenue!(LIGHT BLUE COLOR)");
                       System.out.println("Do you want to purchase the property for 100$?");
                       System.out.println("If you chose not to buy the property it will be put up for action!");
                       answer = scan.next();
                       if (answer.equalsIgnoreCase("Yes")) {
                           owner = playersName[i];
                           trackPlayersMoney[i] = trackPlayersMoney[i] - 100;
                           System.out.println(currentPlayer + " balance is " + trackPlayersMoney[i] + "$");
                           System.out.println();
                           System.out.println(owner + " is now the owner of Oriental Avenue!");
                           ownerMoney = trackPlayersMoney[i];
                       } else if (answer.equalsIgnoreCase(("No"))) {
                           beddingAnswer = "Yes";
                           Biding(i);
                       }
                   }
               }
           }
           public static void upgradeOrientalAvenue(int i){
               if (positionArray[i] == 7 && owner == playersName[i]) {
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
                           }
                           else if(level == 3){
                               System.out.println("The rent that other players need to pay is 270$");
                           }
                           else if(level == 4){
                               System.out.println("The rent that other players need to pay is 400$");
                           }
                           else if(level == 5){
                               System.out.println("The rent that other players need to pay is 550$");
                           }
                           System.out.println();
                           System.out.println(playersName[i] + " money is " + trackPlayersMoney[i] + "!");
                           System.out.println();
                           System.out.println("Do you want to upgrade it again?");
                           upgradeAnswer = scan.next();
                       }
                       else if (upgradeAnswer.equalsIgnoreCase("No")) {
                           System.out.println("Your property level is "+ level);
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
               if(owner == null){
                   orientalAvenue(i);
               }
               else if (!Objects.equals(playersName[i], owner) && positionArray[i] == 4) {
                   System.out.println(currentPlayer+ " landed on foreign property!");
                   if (level == 1) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 30;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 30$");
                       ownerMoney = ownerMoney + 30;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 2) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 90;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 90$");
                       ownerMoney = ownerMoney + 90;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 3) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 270;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 270$");
                       ownerMoney = ownerMoney + 270;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 4) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 400;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 400$");
                       ownerMoney = ownerMoney + 400;
                       System.out.println(owner + " money is " + ownerMoney);
                       System.out.println();
                   } else if (level == 5) {
                       System.out.println("You need to pay rent!");
                       trackPlayersMoney[i] = trackPlayersMoney[i] - 550;
                       System.out.println(playersName[i] + " money now is " + trackPlayersMoney[i]);
                       System.out.println(currentPlayer+ "paid " +owner+ " 550$");
                       ownerMoney = ownerMoney + 550;
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
            System.out.println(currentPlayer+ "is escorted to jail!");
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
                     String useCardAnswer = scan.next();
                     if (useCardAnswer.equalsIgnoreCase("Yes")) {
                         if(playersCards[i].contains("SKIP PAYING") || playersCards[i].contains("THEY PAY YOU DOUBLE")){
                             playersCards[i].replace("GET OUT OF JAIL FREE","none");
                         }
                         playersName[i] = prisonName[i];
                         prisonName[i] = playersName[i];
                         System.out.println("You are free to go!");
                     } else if (useCardAnswer.equalsIgnoreCase("No")) {
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
                System.out.println(currentPlayer+ " drawn DRAW NEW CARD!");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
                randomCard = random.nextInt(cards.length);
                System.out.println(cards[randomCard]);
                if(cards[randomCard].equalsIgnoreCase("GET OUT OF JAIL FREE")){
                    playersCards[i] = playersCards[i] + "GET OUT OF JAIL FREE";
                    System.out.println(currentPlayer+ " drawn GET OUT OF JAIL FREE");
                    System.out.println("You can save it and use later!");
                }
                else if(cards[randomCard].equalsIgnoreCase("SKIP PAYING")){
                    playersCards[i] = playersCards[i] + "SKIP PAYING";
                }
            }
            else if (cards[randomCard].equalsIgnoreCase("PAY MONEY")) {
                System.out.println(currentPlayer +" drawn PAY MONEY!");
                int randomMoneyAmount = random.nextInt(moneyToPay.length);
                System.out.println("You have to pay " + moneyToPay[randomMoneyAmount]);
                trackPlayersMoney[i] = trackPlayersMoney[i] - moneyToPay[randomMoneyAmount];
                System.out.println(playersName[i] + "your money is" + trackPlayersMoney[i] + "$");
            }
            else if (cards[randomCard].equalsIgnoreCase("GO TO JAIL")) {
                System.out.println(currentPlayer+ " drawn GO TO JAIL");
                InJail(i);
                getOutOfJail(i);
            }
            else if (cards[randomCard].equalsIgnoreCase("GET OUT OF JAIL FREE")) {
                System.out.println(currentPlayer+ " drawn GET OUT OF JAIL FREE");
                System.out.println("You can save it and use later!");
                playersCards[i] = playersCards[i] + "GET OUT OF JAIL FREE";
            }
            else if (cards[randomCard].equalsIgnoreCase(("THROW THE DICES AGAIN"))) {
                System.out.println(currentPlayer+ " drawn THROW THE DICES AGAIN");
                System.out.println("Throw the dices");
                try {
                    System.in.read();
                } catch (Exception e) {
                }
                firstDice = roll.nextInt(6) + 1;
                secondDice = roll.nextInt(6) + 1;
                System.out.println(firstDice + "," + secondDice);
                positionArray[i] = positionArray[i] + firstDice + secondDice;
                System.out.println(currentPlayer+ " position is " + positionArray[i]);
            }
        }
    }
    public static void main(String[] args) {
        chosePlayersCountAndNames();
        shuffleTheOrder();
        throwTwoDices();
    }
}

