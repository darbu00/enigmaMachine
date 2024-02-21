import java.util.Arrays;

public class Plugboard {

  private int numberOfPlugs;
  private char[][] configuration;

  public int getNumberOfPlugs() {
    return numberOfPlugs;
  }

  public void setNumberOfPlugs(int numberOfPlugs) {
    this.numberOfPlugs = numberOfPlugs;
  }

  public char[][] getConfiguration() {
    return configuration;
  }

  public void setConfiguration(char[][] configuration) {
    this.configuration = configuration;
  }

  public Plugboard(int numberOfPlugs) {
    this.numberOfPlugs = numberOfPlugs;
    this.configuration = generatePlugConfiguration(numberOfPlugs);
  }

  @Override
  public String toString() {
    return "Plugboard [numberOfPlugs=" + numberOfPlugs + ", configuration=" + configuration.toString() + "]";
  }

  private char[][] generatePlugConfiguration(int numberOfPlugs) {

    char[][] configuration = new char[numberOfPlugs][2];
    char[] usedChars = new char[numberOfPlugs * 2];

    if (numberOfPlugs == 0) {
      return configuration;
    }

    for (int i = 0; i < numberOfPlugs; i++) {
      configuration[i][0] = getRandomLetter(usedChars);
      usedChars[i] = configuration[i][0];
      configuration[i][1] = getRandomLetter(usedChars);
      usedChars[i + numberOfPlugs] = configuration[i][1];
    }

    // System.out.print("Plugboard: ");
    // for (int i = 0; i < numberOfPlugs; i++){
    // System.out.print(configuration[i][0] + "" + configuration[i][1] + " ");
    // }
    // System.out.println("");
    // System.out.println(usedChars);
    return configuration;
  }

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
