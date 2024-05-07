import java.util.ArrayList;
import java.util.Scanner;

public class RunEnigma {

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

  Scanner kbScanner = new Scanner(System.in);
  Enigma enigmaMachine;
  EnigmaController enigmaController = new EnigmaController();

  public static void main(String[] args) {
    EnigmaController enigmaMachine = new EnigmaController();
    enigmaMachine.startEnigma();
  }

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
          if (checkMainMenuSelection(menuSelection)) {
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
              int numberOfWheels = 5;
              int numberOfPlugs = 6;
              int[] wheelOrder = new int[3];
              wheelOrder[0] = 3;
              wheelOrder[1] = 2;
              wheelOrder[2] = 0;
              boolean defaultEnigma = true;
              enigmaMachine = new Enigma(numberOfWheels, wheelOrder, numberOfPlugs, defaultEnigma);
              clearScreen();
              System.out.println("Default enigma machine created.");
              System.out.println("This is your Enigma:");
              System.out.println(enigmaMachine.toString());
              System.out.println("\n\n");
              System.out.print(" Press ENTER to continue...");
              kbScanner = new Scanner(System.in);
              kbScanner.nextLine();

            } else if (selection == 2) {
              validSelections = displayCustomCreateMenu(enigmaMachine);
              selection = getNumericInput();
              if (checkMenuSelection(selection, validSelections)) {
                boolean hybrid = false;
                switch (selection) {
                  case 1: // Random wheel ciphers
                    enigmaController.createCustomEnigma(hybrid);
                    break;

                  case 2: // Hybrid default/random wheel ciphers
                    hybrid = true;
                    enigmaController.createCustomEnigma(hybrid);
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
            enigmaController.testEncryption(enigmaMachine, null);
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

  private void displayChangeConfigMenu(Enigma enigmaMachine) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'displayChangeConfigMenu'");
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

  private boolean checkMainMenuSelection(int menuSelection) {
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

  public void clearScreen() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

}
