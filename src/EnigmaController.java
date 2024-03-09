import java.util.ArrayList;
import java.util.Set;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class EnigmaController {

  private enum ENIGMA_STATE {
    STARTUP,
    DISPLAY_MENU,
    CREATE,
    SETTINGS,
    LOAD_ENIGMA,
    SAVE_ENIGMA,
    DISPLAY_ENIGMA,
    ENCRYPT,
    EXIT
  }

  private Enigma enigmaMachine;
  public final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
      'Q',
      'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
  private int[] wheelOrder;
  private int numberOfWheels;
  private int numberOfPlugs;
  private boolean defaultEnigma;
  Scanner kbScanner = new Scanner(System.in);

  public void startEnigma() {

    ENIGMA_STATE enigmaState = ENIGMA_STATE.STARTUP;

    do {
      switch (enigmaState) {

        case STARTUP:
          System.out.println("""
                  \n\nThe Enigma Machine was a World War II era cipher device used by the Germans
                  to encrypt and decrypt military communications transmitted (mostly) by Morse
                  Code over-the-air.

                  This program attempts to replicate the process using some of the historically
                  known configurations as well as allowing for cusomizable configurations that
                  rely on the same encryption mechanisms.  Enjoy.
              """);
          System.out.print("Press ENTER to continue...");
          kbScanner.nextLine();
          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          break;
        case DISPLAY_MENU:
          displayMainMenu();
          int menuSelection = getNumericInput();
          if (checkMenuSelection(menuSelection)) {
            switch (menuSelection) {

              case 1:
                enigmaState = ENIGMA_STATE.CREATE;
                break;

              case 2:
                enigmaState = ENIGMA_STATE.SETTINGS;
                break;

              case 3:
                enigmaState = ENIGMA_STATE.LOAD_ENIGMA;
                break;

              case 4:
                enigmaState = ENIGMA_STATE.SAVE_ENIGMA;
                break;

              case 5:
                enigmaState = ENIGMA_STATE.ENCRYPT;
                break;

              case 6:
                enigmaState = ENIGMA_STATE.DISPLAY_ENIGMA;
                break;

              case 7:
                enigmaState = ENIGMA_STATE.EXIT;
                break;
            }
          }
          break;

        case SETTINGS:
          if (enigmaMachine != null) {
            ArrayList<Integer> validSelections = displaySettingsMenu(enigmaMachine);
            int selection = getNumericInput();
            if (checkMenuSelection(selection, validSelections)) {
              switch (selection) {
                case 1: // Set wheel order
                  break;

                case 2: // Set wheel starting position
                  break;

                case 3: // Set wheel ring position
                  break;

                case 4: // Set plugboard configuration
                  break;

                case 5: // Set all daily settings
                  break;

                case 7:
                  break;
              }
            } else {
              System.out.print("Invalid Selection.  Press ENTER to continue...");
              kbScanner = new Scanner(System.in);
              kbScanner.nextLine();
              enigmaState = ENIGMA_STATE.SETTINGS;
            }
          } else {
            System.out.println("You have not created your Enigma Machine yet!");
            System.out.print("Press ENTER to continue...");
            kbScanner = new Scanner(System.in);
            kbScanner.nextLine();
            enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          }
          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          break;

        case CREATE:
          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          // displayWheelOrderExample();
          ArrayList<Integer> validSelections = displayCreateMenu(enigmaMachine);
          int selection = getNumericInput();
          if (checkMenuSelection(selection, validSelections)) {
            if (selection == 1) {
              numberOfWheels = 5;
              numberOfPlugs = 6;
              wheelOrder = new int[3];
              wheelOrder[0] = 3;
              wheelOrder[1] = 2;
              wheelOrder[2] = 0;
              defaultEnigma = true;
              enigmaMachine = new Enigma(numberOfWheels, wheelOrder, numberOfPlugs, defaultEnigma);
              clearScreen();
              System.out.println("Default enigma machine created.");
              System.out.println("This is your Enigma:");
              System.out.println(enigmaMachine.toString());
              System.out.println("\n\n");
              System.out.print(" Press ENTER to continue...");
              kbScanner.nextLine();
              kbScanner.nextLine();

            } else if (selection == 2) {
              validSelections = displayCustomCreateMenu(enigmaMachine);
              selection = getNumericInput();
              if (checkMenuSelection(selection, validSelections)) {
                boolean hybrid = false;
                switch (selection) {
                  case 1: // Random wheel ciphers
                    createCustomEnigma(hybrid);
                    break;

                  case 2: // Hybrid default/random wheel ciphers
                    hybrid = true;
                    createCustomEnigma(hybrid);
                    break;

                  case 7:
                    enigmaState = ENIGMA_STATE.CREATE;
                    break;
                }
              } else {

              }
            } else if (selection == 3) {
              displayChangeConfigMenu(enigmaMachine);
            }
          }
          break;

        case LOAD_ENIGMA:
          EnigmaReader enigmaReader = new EnigmaReader();
          ArrayList<String> fileContent = new ArrayList<String>();
          try {
            fileContent = enigmaReader.readFile("/Users/David/Desktop/enigmaMessage.txt");
          } catch (Exception e) {

          }

          for (String lineContent : fileContent) {
            System.out.println(lineContent);
          }

          testEncryption(enigmaMachine.getPlugboard(), enigmaMachine.getWheels(), enigmaMachine.getReflector(),
              enigmaMachine.getEncryptor(), enigmaMachine.getWheelOrder(), fileContent);

          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          break;

        case DISPLAY_ENIGMA:
          clearScreen();
          if (enigmaMachine != null) {
            System.out.println("This enigma machine:");
            System.out.println(enigmaMachine.toString());
          } else {
            System.out.println("You don't have an Enigma Machine to display!");
          }
          System.out.print("\nPress ENTER to continue...");
          kbScanner = new Scanner(System.in);
          kbScanner.nextLine();
          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          break;

        case SAVE_ENIGMA:
          displaySaveMenu();

          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          break;

        case ENCRYPT:
          if (enigmaMachine != null) {
            testEncryption(enigmaMachine.getPlugboard(), enigmaMachine.getWheels(), enigmaMachine.getReflector(),
                enigmaMachine.getEncryptor(), enigmaMachine.getWheelOrder(), null);
            enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          } else {
            System.out.println("\n\nYOU HAVE NOT GENERATED AN ENIGMA MACHINE YET!!\n");
            enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          }
          break;

        case EXIT:
          break;
      }

    } while (enigmaState != ENIGMA_STATE.EXIT);

  } // End startEnigma here

  private boolean checkMenuSelection(int menuSelection) {
    if (menuSelection >= 1 && menuSelection <= 7) {
      return true;
    } else {
      System.out.println("\n\nINVALID SELECTION!!\n");
      return false;
    }
  }

  private boolean checkMenuSelection(int menuSelection, ArrayList<Integer> validSelections) {
    if (validSelections.contains(menuSelection)) {
      return true;
    } else {
      System.out.println("\n\nINVALID SELECTION!!\n");
      return false;
    }
  }

  private int getNumericInput() {
    int i = 0;
    boolean validInput = false;
    do {
      kbScanner = new Scanner(System.in);
      String kbInput = kbScanner.next();
      try {
        i = Integer.parseInt(kbInput);
        validInput = true;
      } catch (NumberFormatException e) {
        System.out.println("NUMBER EXPECTED - RETRY INPUT LINE!!");
        System.out.print("?");
      }
    } while (!validInput);
    return i;
  }

  private ArrayList<Integer> getNumericArrayInput(int maxArrayLength) {
    boolean validArrayInput = false;
    ArrayList<Integer> numericArray = new ArrayList<Integer>();
    while (!validArrayInput) {
      kbScanner = new Scanner(System.in);
      StringBuffer kbInput = new StringBuffer(kbScanner.nextLine());
      if (kbInput.isEmpty()) {
        // numericArray.add(0);
        System.out.println("Your entry was empty.  Do you want to leave it empty (y/n)? ");
        if (getYesNo()) {
          validArrayInput = true;
        }
      } else {
        for (int i = 0; i < kbInput.length(); i++) {
          char c = kbInput.charAt(i);
          if (!Character.isDigit(c) && c != ' ') {
            validArrayInput = false;
          } else {
            validArrayInput = true;
            // System.out.println("Valid. Your input: " + kbInput.toString());
          }
        }
        if (validArrayInput) {
          for (String number : kbInput.toString().split(" ")) {
            numericArray.add(Integer.parseInt(number));
            // System.out.println("numericArray: " + numericArray.toString());
          }
        }
        if (numericArray.size() <= maxArrayLength) {
          validArrayInput = true;
        } else {
          System.out.println("There was something wrong with your input.");
          System.out.println("Try again: ");
        }
      }
    }
    return numericArray;
  }

  private boolean getYesNo() {
    char c;
    boolean returnYesNo = true;
    boolean validInput = false;
    do {
      kbScanner = new Scanner(System.in);
      String kbInput = kbScanner.next();
      c = kbInput.toUpperCase().charAt(0);
      if (c == 'Y' || c == 'N') {
        validInput = true;
        if (c == 'N') {
          returnYesNo = false;
        }
      } else {
        System.out.println("Y OR N EXPECTED - RETRY INPUT LINE!!");
        System.out.print("?");
      }
    } while (!validInput);
    return returnYesNo;
  }

  private void displayMainMenu() {
    clearScreen();
    System.out.println("1. Create a Standard or Custom Enigma Machine");
    System.out.println("2. Configure Daily Settings");
    System.out.println("3. Load Existing Enigma Machine");
    System.out.println("4. Save Enigma Machine Settings");
    System.out.println("5. Encrypt or Decrypt Text");
    System.out.println("6. Display Enigma Configuration");
    System.out.println("\n7. Exit");
    System.out.print("\n\nWhat would you like to do? ");
  }

  private ArrayList<Integer> displaySettingsMenu(Enigma enigmaMachine) {

    ArrayList<Integer> validSelections = new ArrayList<Integer>() {
      {
        add(1);
        add(2);
        add(3);
        add(4);
        add(5);
        add(7);
      }
    };
    System.out.println("1. Set wheel order");
    System.out.println("2. Set wheel starting positions");
    System.out.println("3. Set wheel ring postions");
    System.out.println("4. Set plugboard configuration");
    System.out.println("5. Set all daily settings");
    System.out.println("\n7. Exit to main menu");
    System.out.print("What would you like to do? ");
    return validSelections;
  }

  private ArrayList<Integer> displayCreateMenu(Enigma enigmaMachine) {
    ArrayList<Integer> validSelections = new ArrayList<Integer>() {
      {
        add(1);
        add(2);
        add(7);
      }
    };
    System.out.println("1. Create a Standard Enigma Machine");
    System.out.println("2. Create a Non-Standard Enigma Machine");

    if (enigmaMachine != null) {
      System.out.println("3. Configure The Existing Enigma Machine");
      validSelections.add(3);
    }

    System.out.println("\n7. Exit");
    System.out.print("\n\nWhat would you like to do? ");
    return validSelections;
  }

  private ArrayList<Integer> displayCustomCreateMenu(Enigma enigmaMachine) {
    ArrayList<Integer> validSelections = new ArrayList<Integer>() {
      {
        add(1);
        add(2);
        // add(3);
        // add(4);
        add(7);
      }
    };
    clearScreen();
    // System.out.println("1. Create custom Enigma with default wheel ciphers");
    System.out.println("1. Create custom Enigma with random wheel ciphers");
    System.out.println("2. Create hybrid Enigma with default wheel ciphers");
    System.out.println("\tplus additional random wheel ciphers");
    System.out.println("7. Exit to main menu");

    return validSelections;
  }

  private void createCustomEnigma(boolean hybrid) {
    int numberOfWheels = 0;
    int numberOfDefaultWheels = 0;
    int numberOfUsedWheels = 0;
    Set<Integer> wheelOrder = new LinkedHashSet<Integer>();
    boolean validEntry = false;

    clearScreen();
    while (!validEntry) {
      System.out.println("TOTAL NUMBER OF WHEELS");
      System.out.println("Recommended minimum of 3 and there is maximum of 20.");
      System.out.println("The default Enigma has 5 wheels, using 3 at any time.");
      System.out.print("How many total wheels would you like to have? ");
      numberOfWheels = getNumericInput();
      if (numberOfWheels < 21) {
        validEntry = true;
      } else {
        System.out.println("\nInvalid, try again!\n");
      }

      if (numberOfWheels == 0) {
        System.out.print("Are you sure you want zero wheels (y/n)? ");
        if (getYesNo()) {
          validEntry = true;
        } else {
          validEntry = false;
        }
      }
    }
    clearScreen();
    validEntry = false;
    if (hybrid) {
      while (!validEntry) {
        System.out.println("DEFAULT CIPHERS");
        System.out.println("You can use up to a maximum of 5 default ciphers.");
        System.out.println("Zero will switch you back to all random.");
        System.out.print("Defalut wheel ciphers? ");
        numberOfDefaultWheels = getNumericInput();
        if (numberOfDefaultWheels < 6) {
          validEntry = true;
        } else {
          System.out.println("\nInvalid, try again!\n");
        }
      }
    }
    clearScreen();
    validEntry = false;
    while (!validEntry) {
      System.out.println("WHEEL ORDER");
      System.out.println("Here you will configure the order of the wheels.");
      System.out.println("Would you like to see an example of what this means?");
      System.out.print("Enter y/n ");
      if (getYesNo()) {
        displayWheelOrderExample();
      }
      clearScreen();
      System.out.println("WHEEL ORDER");
      System.out.print("Would you like to set this randomly (y/n)");

      if (getYesNo()) {
        boolean validWheelNumber = false;
        while (!validWheelNumber) {
          System.out.println("Ok, how many of your " + numberOfWheels + " do you want to use?");
          System.out.print("Enter number: ");
          numberOfUsedWheels = getNumericInput();
          if (numberOfUsedWheels <= numberOfWheels) {
            wheelOrder = setRandomWheelOrder(numberOfWheels, numberOfUsedWheels);
            validWheelNumber = true;
          } else {
            System.out.println("\nInvalid number!\n");
          }
        }
        validEntry = true;
      } else {
        boolean isValidArray = false;
        while (!isValidArray) {
          System.out.println("\nYour Enigma has a total of " + numberOfWheels + " wheels.");
          if (hybrid && numberOfDefaultWheels > 0) {
            System.out.println("Of those, wheel numbers 0 through " + (numberOfDefaultWheels - 1) + " are defaults.");
          }
          System.out.println("You can use as few or as many as you want, up to " + numberOfWheels + ".");
          System.out.println("Remember, you wheels are numbered 0 through " + (numberOfWheels - 1));
          System.out.println("Ok, enter your wheel order separated by spaces.");
          System.out.print("Wheel Order: ");
          ArrayList<Integer> randomWheelOrder = getNumericArrayInput(numberOfWheels);
          // here we need to verify that no wheels were repeated in the array
          Set<Integer> testSet = new LinkedHashSet<>(randomWheelOrder);
          if (testSet.size() == randomWheelOrder.size()) {
            wheelOrder = testSet;
            numberOfUsedWheels = wheelOrder.size();
            isValidArray = true;
            for (int i : wheelOrder) {
              if (i >= numberOfWheels) {
                isValidArray = false;
                System.out.println("You entered a wheel number out of range.");
                System.out.print("Press ENTER to try again...");
                kbScanner = new Scanner(System.in);
                kbScanner.nextLine();
              } else {
                validEntry = true;
              }
            }
          } else {
            System.out.println("You have a duplicate.  Each wheel can only be used once.");
            System.out.print("Press ENTER to try again...");
            kbScanner = new Scanner(System.in);
            kbScanner.nextLine();
          }
        }
      }

    } // End wheel order
    System.out.println("Ok, here are your settings...:)");
    System.out.println("Settings: \n" + "Number of wheels: " + numberOfWheels + " \n" + "Default wheels: "
        + numberOfDefaultWheels + " \n" + "Used wheels: " + numberOfUsedWheels + " \n"
        + "Wheel order: " + wheelOrder.toString());
    boolean buildEnigma = false;
    while (!buildEnigma) {
      System.out.print("Would you like to build this Enigma? (y/n)");
      if (!getYesNo()) {
        System.out.print("This configuratin will be lost.  Are you sure (y/n)?");
        if (!getYesNo()) {
          buildEnigma = true;
          System.out.print("Ok, we will build it then.  Press ENTER to continue...");
        }
      } else {
        buildEnigma = true;
      }
    }
    if (buildEnigma) {
      System.out.println("Ok, building your enigma");
      // call constructor
      enigmaMachine = new Enigma(numberOfWheels, numberOfDefaultWheels, wheelOrder);
      System.out.println(enigmaMachine.toString());
      System.out.print("\nPress ENTER to continue...");
      kbScanner = new Scanner(System.in);
      kbScanner.nextLine();
    }

  } // End custom create

  private void displayChangeConfigMenu(Enigma enigmaMachine) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'displayChangeConfigMenu'");
  }

  public static void displayLoadMenu() {

  }

  public static void displaySaveMenu() {
  }

  public static void displayEncryptMenu() {
    System.out.println("1. Encyrpt from Keyboard");
    System.out.println("2. Load Plain Text File to Encrypt");
    System.out.println("3. Decrypt Previously Encrypted File");
    System.out.println("\n7. Exit");
    System.out.print("\n\nWhat would you like to do? ");

  }

  public static void displayEnigmaMachine(Enigma enigmaMachine) {

  }

  private void displayWheelOrderExample() {
    clearScreen();
    System.out.println("For example, if your Engima has 5 wheels and it is using");
    System.out.println("wheels 0, 2 and 3 in the order 3 0 2 it would look something");
    System.out.println("this:");
    System.out.println("Wheel\t\t2\t\t\t\t0\t\t\t\t3");
    for (int i = 0; i < 3; i++) {
      System.out.println("\t\t|\t\t\t\t|\t\t\t\t|");
      System.out.println("\t\t*\t\t\t\t*\t\t\t\t*");
      System.out.println("\t\t|\t\t\t\t|\t\t\t\t|");
    }
    System.out.println("Position\t3\t\t\t\t2\t\t\t\t1");
    System.out.println("\nPress ENTER to continue ");
    kbScanner.nextLine();
    kbScanner.nextLine();
  }

  private Set<Integer> setRandomWheelOrder(int numberOfWheels, int numberOfUsedWheels) {
    Set<Integer> randomSet = new LinkedHashSet<Integer>();
    while (randomSet.size() < numberOfUsedWheels) {
      randomSet.add((int) (numberOfWheels * Math.random()));
    }
    return randomSet;
  }
  // End test encryption above

  // This method probably belongs in Encryptor (or Enigma)
  public static void turnWheels(Wheel[] wheels, int[] wheelOrder) {

    for (int i = 0; i < wheelOrder.length; i++) {
      if (wheels[wheelOrder[i]].getCurrentWheelPosition() == wheels[wheelOrder[i]].getRingPosition()) {
        if (i != (wheelOrder.length - 1)) {
          wheels[wheelOrder[i + 1]].setFirstTurnWheel(true);
        }
      }
    }

    for (int i = 0; i < wheelOrder.length; i++) {
      if (wheels[wheelOrder[i]].isFirstTurnWheel() || wheels[wheelOrder[i]].isSecondTurnWheel()) {

        if (wheels[wheelOrder[i]].getCurrentWheelPosition() < 25) {
          wheels[wheelOrder[i]].setCurrentWheelPosition(wheels[wheelOrder[i]].getCurrentWheelPosition() + 1);
        } else {
          wheels[wheelOrder[i]].setCurrentWheelPosition(0);
        }

        if (i != 0 && wheels[wheelOrder[i]].isFirstTurnWheel()) {
          wheels[wheelOrder[i]].setFirstTurnWheel(false);
          if ((wheels[wheelOrder[i]].getRingPosition() == wheels[wheelOrder[i]].getCurrentWheelPosition())) {
            wheels[wheelOrder[i]].setSecondTurnWheel(true); // If the last wheel turns, the next last wheel turns again
                                                            // on the next input character
          }
        } else {
          wheels[wheelOrder[i]].setSecondTurnWheel(false);
        }
      }
    }
  }

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  // This is a test encryption
  // TO-DO Change to accept a Enigma and message only as parameters.

  public void testEncryption(Plugboard plugboard, Wheel[] wheels, Reflector reflector, Encryptor encryptor,
      int[] wheelOrder, ArrayList<String> messageString) {

    // String testString = "this is my crazy long test message to the Allied
    // commmand asking about DdayAAAAAAAAAAAAAAAAAAAAAAAAAAAA";
    String testString = """
        8 Mar 2024 320 aaa aaa
        99 999 $#Thisis a test.  Going to try to see how this works.
        It may/not depending how the system reacts to this message.
        But we will give it a go, I guess.""";

    if (messageString != null) {
      StringBuilder testStringBuilder = new StringBuilder();
      for (int i = 0; i < messageString.size(); i++) {
        testStringBuilder.append(messageString.get(i));
        testStringBuilder.append("\n");
      }
      testString = testStringBuilder.toString();
    }

    // test new method
    //
    // System.out.println(enigmaMachine.toString());
    enigmaMachine.resetWheels();
    enigmaMachine.setAllowSpecialCharacters(false);

    System.out.println("New method:");
    ArrayList<Character> methodEncrypt = enigmaMachine.getEncryptor().encryptMessage(new StringBuilder(testString),
        enigmaMachine);
    System.out.println(methodEncrypt);
    for (int i = 0; i < methodEncrypt.size(); i++) {
      if (methodEncrypt.get(i) != '\000') {
        System.out.print(methodEncrypt.get(i));
      }
    }
    System.out.println();

    EnigmaWriter.writeEncryptedMessage(methodEncrypt);

    System.out.println();
    enigmaMachine.resetWheels();

    StringBuilder decryptMessage = new StringBuilder();
    for (int i = 0; i < methodEncrypt.size(); i++) {
      decryptMessage.append(methodEncrypt.get(i));
    }

    ArrayList<Character> methodDecrypt = enigmaMachine.getEncryptor().encryptMessage(decryptMessage,
        enigmaMachine);
    System.out.println(methodDecrypt.toString());

    System.out
        .println("Test:" + testString.length() + " En: " + methodEncrypt.size()
            + " De:" + methodDecrypt.size());

    // Test enigmaReader here
    ArrayList<String> readFile = new ArrayList<String>();
    try {
      readFile = EnigmaReader.readFile(null);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    if (!readFile.isEmpty()) {
      System.out.println("File contents:\n" + readFile.toString() + "\n");
    }

    decryptMessage = new StringBuilder();

    for (char c : readFile.get(0).toCharArray()) {
      decryptMessage.append(c);
    }
    enigmaMachine.resetWheels();
    methodDecrypt = enigmaMachine.getEncryptor().encryptMessage(decryptMessage, enigmaMachine);
    for (int i = 0; i < methodDecrypt.size(); i++) {
      System.out.print(methodDecrypt.get(i));
    }
    System.out.println();

    System.out.print("Press ENTER to continue...");
    kbScanner.nextLine();
    kbScanner.nextLine();
    enigmaMachine.resetWheels();

  }

  // convert encryption to a method here
  //

}
