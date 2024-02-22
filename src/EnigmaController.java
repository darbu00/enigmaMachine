import java.util.ArrayList;
import java.util.Scanner;

public class EnigmaController {

  private enum ENIGMA_STATE {
    STARTUP,
    DISPLAY_MENU,
    CONFIGURE,
    GENERATE,
    LOAD_ENIGMA,
    SAVE_ENIGMA,
    DISPLAY_ENIGMA,
    ENCRYPT,
    EXIT
  }

  private Enigma enigmaMachine;
  private final char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
      'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
  private int[] wheelOrder = { 3, 2, 0 };
  private int numberOfWheels = 5;
  private int numberOfPlugs = 6;
  private boolean defaultEnigma = true;
  Scanner kbScanner = new Scanner(System.in);

  public void startEnigma() {

    ENIGMA_STATE enigmaState = ENIGMA_STATE.STARTUP;

    do {
      switch (enigmaState) {

        case STARTUP:
          System.out.println("""
              \n\nThe Enigma Machine was a World War to era cipher device used by the Germans
              to encrypt and decrypt military communications transmitted (mostly) by Morse
              Code over-the-air.\n""");
          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          break;
        case DISPLAY_MENU:
          displayMainMenu();
          int menuSelection = getNumericInput();
          if (checkMenuSelection(menuSelection)) {
            switch (menuSelection) {

              case 1:
                enigmaState = ENIGMA_STATE.CONFIGURE;
                break;

              case 2:
                enigmaState = ENIGMA_STATE.GENERATE;
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

        case GENERATE:
          if (numberOfWheels != 0) {

            // update this and/or Enigma to properly use defaultEnigma
            //
            if (defaultEnigma) {
              enigmaMachine = new Enigma(numberOfWheels, wheelOrder, numberOfPlugs, defaultEnigma);
            } else {
              enigmaMachine = new Enigma(numberOfWheels, wheelOrder, numberOfPlugs, defaultEnigma);
            }
            enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          } else {
            System.out.println("\n\nCONFIGURE YOUR ENIGMA MACHINE FIRST!\n");
            enigmaState = ENIGMA_STATE.DISPLAY_MENU;
          }
          break;

        case CONFIGURE:
          displayConfigureMenu(enigmaMachine);
          getNumericInput();

          enigmaState = ENIGMA_STATE.DISPLAY_MENU;
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
          System.out.println("This enigma machine:");
          System.out.println(enigmaMachine.toString());
          System.out.print("Hit any key to return to menu.");
          kbScanner.next();
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

  private int getNumericInput() {
    int i = 0;
    boolean validInput = false;
    do {
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

  private static void displayMainMenu() {

    System.out.println("1. Configure New or Existing Enigma Machine Settings");
    System.out.println("2. Generate New Enigma Machine");
    System.out.println("3. Load Existing Enigma Machine");
    System.out.println("4. Save Enigma Machine Settings");
    System.out.println("5. Encrypt Text");
    System.out.println("6. Display Enigma Configuration");
    System.out.println("\n7. Exit");
    System.out.print("\n\nWhat would you like to do? ");

  }

  public static void displayConfigureMenu(Enigma enigmaMachine) {
    System.out.println("1. Configure Standard Enigma Machine");
    System.out.println("2. Configure a Non-Standard Enigma Machine");

    if (enigmaMachine != null) {
      System.out.println("3. Configure The Existing Enigma Machine");
    }

    System.out.println("\n7. Exit");
    System.out.print("\n\nWhat would you like to do? ");
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

  // This is a test encryption

  public void testEncryption(Plugboard plugboard, Wheel[] wheels, Reflector reflector, Encryptor encryptor,
      int[] wheelOrder, ArrayList<String> inputString) {

    String testString = "this is my crazy long test message to the Allied commmand asking about Dday.  AAAAAAAAAAAAAAAAAAAAAAAAAAAA";

    if (inputString != null) {
      StringBuilder testStringBuilder = new StringBuilder();
      for (int i = 0; i < inputString.size(); i++) {
        testStringBuilder.append(inputString.get(i));
        testStringBuilder.append("\n");
      }
      testString = testStringBuilder.toString();
    }

    char[] resultChar = new char[testString.toCharArray().length];
    int index = 0;
    for (char testChar : testString.toCharArray()) {

      Enigma.turnWheels(wheels, wheelOrder);

      char character = Character.toUpperCase(testChar);

      character = plugboard.checkPlugboard(character);

      for (int i = 0; i < wheelOrder.length; i++) {
        character = encryptor.encryptChar(true, wheels[wheelOrder[i]], character);
      }
      // System.out.print(character);

      character = reflector.reflectChar(character);

      for (int i = (wheelOrder.length - 1); i >= 0; i--) {
        character = encryptor.encryptChar(false, wheels[wheelOrder[i]], character);
      }
      // System.out.println(character);

      character = plugboard.checkPlugboard(character);

      resultChar[index] = character;
      // System.out.println(" Index: " + index);
      index++;

    }

    System.out.println(testString);
    System.out.println(testString.toUpperCase());
    System.out.print("Encrypt: ");
    for (char result : resultChar) {
      System.out.print(result);
    }
    System.out.println("");
    // Start test decryption

    enigmaMachine.resetWheels();

    StringBuilder decryptResult = new StringBuilder();
    for (char dtestChar : resultChar) {
      char character = dtestChar;

      Enigma.turnWheels(wheels, wheelOrder);

      character = plugboard.checkPlugboard(character);

      for (int i = 0; i < wheelOrder.length; i++) {
        character = encryptor.encryptChar(true, wheels[wheelOrder[i]], character);
      }

      character = reflector.reflectChar(character);

      for (int i = (wheelOrder.length - 1); i >= 0; i--) {
        character = encryptor.encryptChar(false, wheels[wheelOrder[i]], character);
      }

      character = plugboard.checkPlugboard(character);

      decryptResult.append(character);
      // System.out.print(character);
    }
    System.out.println("Decrypt: " + decryptResult);

    enigmaMachine.resetWheels();
  }
  // End test encryption above

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

  // convert encryption to a method here

}
