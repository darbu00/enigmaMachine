import java.util.ArrayList;

public class Encryptor {

  public char encryptChar(boolean isForward, Wheel wheel, char character) {

    character = Character.toUpperCase(character);

    if ((int) character < 65 || (int) character > 90) {
      return '_';
    }

    int charValue;
    char cipherChar;
    int cipherIndex = 0;

    if (isForward) {
      charValue = (((int) character) - 65) + wheel.getCurrentWheelPosition();
      if (charValue > 25) {
        charValue -= 26;
      }
      cipherChar = (wheel.getCipher()[charValue]);
      return cipherChar;

    } else {

      for (int indexCounter = 0; indexCounter <= 25; indexCounter++) {
        if (wheel.getCipher()[indexCounter] == character) {
          cipherIndex = indexCounter;
          break;
        } else {
          cipherIndex = 99;
        }
      }

      if (cipherIndex - wheel.getCurrentWheelPosition() < 0) {
        cipherChar = (char) (((cipherIndex - wheel.getCurrentWheelPosition()) + 65) + 26);
      } else {
        cipherChar = (char) ((cipherIndex - wheel.getCurrentWheelPosition()) + 65);
      }
      return cipherChar;
    }

  }

  public ArrayList<Character> encryptMessage(StringBuilder inputMessage, Enigma enigmaMachine) {
    ArrayList<Character> encryptedMessage = new ArrayList<Character>();
    char encryptedChar = 0;
    char emptyChar = '\u200B'; // This is a unicode empty space character
    if (enigmaMachine == null || inputMessage.length() < 1) {
      return encryptedMessage;
    }

    for (int i = 0; i < inputMessage.length(); i++) {
      char inputChar = Character.toUpperCase(inputMessage.charAt(i));
      enigmaMachine.turnWheels(enigmaMachine.getWheels(), enigmaMachine.getWheelOrder());

      encryptedChar = 0;

      if (Character.isDigit(inputChar)) {
        String digitWord = digitToWord(inputChar);
        for (int j = 0; j < digitWord.toCharArray().length; j++) {
          encryptedChar = encryptChar(digitWord.toCharArray()[j], enigmaMachine);
          encryptedMessage.add(encryptedChar);
          if (j < digitWord.toCharArray().length - 1) {
            enigmaMachine.turnWheels(enigmaMachine.getWheels(), enigmaMachine.getWheelOrder());
          }
        }
        if (inputMessage.charAt(i + 1) != ' ') {
          enigmaMachine.turnWheels(enigmaMachine.getWheels(), enigmaMachine.getWheelOrder());
          if (enigmaMachine.isAllowSpecialCharacters()) {
            encryptedMessage.add(' ');
          } else {
            encryptedMessage.add(emptyChar);
          }

        }

      } else if ((int) inputChar >= 65 && (int) inputChar <= 90) {
        encryptedChar = encryptChar(inputChar, enigmaMachine);
        encryptedMessage.add(encryptedChar);

      } else if (!enigmaMachine.isAllowSpecialCharacters()) {
        if (inputChar == ' ') {
          encryptedMessage.add(emptyChar);
        } else if (inputChar == emptyChar) {
          encryptedMessage.add(' ');
        } else {
          encryptedMessage.add('.');
        } // TODO still have problem of what to do with special characters other than
          // spaces

      } else {
        encryptedMessage.add(inputChar);
      }
    }
    // System.out.println("After encryption:");
    // System.out.println(enigmaMachine.toString());

    return encryptedMessage;
  }

  public char encryptChar(char inputChar, Enigma enigmaMachine) {
    char encryptedChar = ' ';

    encryptedChar = enigmaMachine.getPlugboard().checkPlugboard(inputChar);
    for (int i = 0; i < enigmaMachine.getWheelOrder().length; i++) {
      encryptedChar = encryptChar(true, enigmaMachine.getWheels()[enigmaMachine.getWheelOrder()[i]], encryptedChar);
    }

    encryptedChar = enigmaMachine.getReflector().reflectChar(encryptedChar);

    for (int i = enigmaMachine.getWheelOrder().length - 1; i >= 0; i--) {
      encryptedChar = encryptChar(false, enigmaMachine.getWheels()[enigmaMachine.getWheelOrder()[i]], encryptedChar);
    }

    encryptedChar = enigmaMachine.getPlugboard().checkPlugboard(encryptedChar);

    return encryptedChar;
  }

  private String digitToWord(char c) {
    String[] digitWords = { "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT", "NINE" };

    return digitWords[Character.getNumericValue(c)];
  }

}
