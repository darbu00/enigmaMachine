import java.util.Arrays;
import java.lang.StringBuilder;

public class Plugboard {

  private int numberOfPlugs;
  private char[][] configuration;

  public int getNumberOfPlugs() {
    return numberOfPlugs;
  }

  public void setNumberOfPlugs(int numberOfPlugs) {
    if (verifyNumberOfPlugs(numberOfPlugs)) {
      this.numberOfPlugs = numberOfPlugs;
    } else {
      this.numberOfPlugs = 0;
    }
  }

  public char[][] getConfiguration() {
    return configuration;
  }

  public void setConfiguration(char[][] configuration) {
    this.configuration = configuration;
  }

  public Plugboard(int numberOfPlugs) {
    if (verifyNumberOfPlugs(numberOfPlugs)) {
      this.numberOfPlugs = numberOfPlugs;
    } else {
      this.numberOfPlugs = 0;
    }
    this.configuration = generatePlugConfiguration(numberOfPlugs);
  }

  @Override
  public String toString() {
    StringBuilder configString = new StringBuilder();
    for (int i = 0; i < configuration.length; i++) {
      configString.append("[");
      configString.append(configuration[i][0]);
      configString.append(configuration[i][1]);
      configString.append("] ");
    }
    return "Plugboard [numberOfPlugs=" + numberOfPlugs + ", configuration=" + configString + "]";
  }

  private char[][] generatePlugConfiguration(int numberOfPlugs) {

    if (numberOfPlugs == 0) {
      char[][] charArray = new char[0][0];
      return charArray;
    }

    char[][] configuration = new char[numberOfPlugs][2];
    char[] usedChars = new char[numberOfPlugs * 2];

    for (int i = 0; i < numberOfPlugs; i++) {
      configuration[i][0] = getRandomLetter(usedChars);
      usedChars[i] = configuration[i][0];
      configuration[i][1] = getRandomLetter(usedChars);
      usedChars[i + numberOfPlugs] = configuration[i][1];
    }

    return configuration;
  }

  /**
   * Returns a randomly generated character after verifying it is not already
   * in usedChars; i.e.<!-- --> each character can only be used once.
   * <p>
   * Potentially never ending loop, though realistically unlikely.
   *
   * @param usedChars
   * @return
   */
  private char getRandomLetter(char[] usedChars) {

    char cipher = '_';

    int iterations = 0;

    int minValue = 65;
    int maxRandom = 26;

    int currentValue;
    char currentChar;
    boolean isUsed = true;

    while (isUsed) {
      currentValue = (int) ((maxRandom * Math.random()) + minValue);
      currentChar = (char) currentValue;

      iterations++;

      boolean valueFound = false;

      for (int j = 0; j < usedChars.length; j++) {
        if (currentChar == usedChars[j]) {
          valueFound = true;
        }
      }

      if (valueFound == false) {
        cipher = currentChar;
        isUsed = false;

      }
    }

    return cipher;
  }

  private boolean verifyNumberOfPlugs(int numberOfPlugs) {
    if (numberOfPlugs < 14) {
      return true;
    } else {
      return false;
    }
  }

  public char checkPlugboard(char character) {
    char plugboardChar = character;

    for (int i = 0; i < this.getNumberOfPlugs(); i++) {
      if (character == this.getConfiguration()[i][0]) {
        plugboardChar = this.getConfiguration()[i][1];
        break;
      }
    }

    for (int i = 0; i < this.getNumberOfPlugs(); i++) {
      if (character == this.getConfiguration()[i][1]) {
        plugboardChar = this.getConfiguration()[i][0];
        break;
      }
    }

    return plugboardChar;
  }

}
