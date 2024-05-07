import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wheel {

  private char[] cipher;
  private int startOffset;
  private int currentWheelPosition; // current wheel position
  private int ringPosition; // what currentWheelPosition turns the next wheel
  private boolean firstTurnWheel;
  private boolean secondTurnWheel;
  private final char[][] DEFAULT_CIPHER = {
      { 'E', 'K', 'M', 'F', 'L', 'G', 'D', 'Q', 'V', 'Z', 'N', 'T', 'O', 'W', 'Y', 'H', 'X', 'U', 'S', 'P', 'A', 'I',
          'B', 'R', 'C', 'J' },
      { 'A', 'J', 'D', 'K', 'S', 'I', 'R', 'U', 'X', 'B', 'L', 'H', 'W', 'T', 'M', 'C', 'Q', 'G', 'Z', 'N', 'P', 'Y',
          'F', 'V', 'O', 'E' },
      { 'B', 'D', 'F', 'H', 'J', 'L', 'C', 'P', 'R', 'T', 'X', 'V', 'Z', 'N', 'Y', 'E', 'I', 'W', 'G', 'A', 'K', 'M',
          'U', 'S', 'Q', 'O' },
      { 'E', 'S', 'O', 'V', 'P', 'Z', 'J', 'A', 'Y', 'Q', 'U', 'I', 'R', 'H', 'X', 'L', 'N', 'F', 'T', 'G', 'K', 'D',
          'C', 'M', 'W', 'B' },
      { 'V', 'Z', 'B', 'R', 'G', 'I', 'T', 'Y', 'U', 'P', 'S', 'D', 'N', 'H', 'L', 'X', 'A', 'W', 'M', 'J', 'Q', 'O',
          'F', 'E', 'C', 'K' }
  };

  // ETW ABCDEFGHIJKLMNOPQRSTUVWXYZ
  // I EKMFLGDQVZNTOWYHXUSPAIBRCJ Y Q 1
  // II AJDKSIRUXBLHWTMCQGZNPYFVOE M E 1
  // III BDFHJLCPRTXVZNYEIWGAKMUSQO D V 1
  // IV ESOVPZJAYQUIRHXLNFTGKDCMWB R J 1
  // V VZBRGITYUPSDNHLXAWMJQOFECK H Z 1
  // UKW-A EJMZALYXVBWFCRQUONTSPIKHGD
  // UKW-B YRUHQSLDPXNGOKMIEBFZCWVJAT
  // UKW-C FVPJIAOYEDRZXWGCTKUQSBNMHL

  public char[] getCipher() {
    return this.cipher;
  }

  public void setCipher(char[] cipher) {
    this.cipher = cipher;
  }

  public int getStartOffset() {
    return this.startOffset;
  }

  public void setStartOffset(int startOffset) {
    this.startOffset = startOffset;
  }

  public int getCurrentWheelPosition() {
    return currentWheelPosition;
  }

  public void setCurrentWheelPosition(int currentWheelPosition) {
    this.currentWheelPosition = currentWheelPosition;
  }

  public int getRingPosition() {
    return ringPosition;
  }

  public void setRingPosition(int ringPosition) {
    this.ringPosition = ringPosition;
  }

  public boolean isFirstTurnWheel() {
    return firstTurnWheel;
  }

  public void setFirstTurnWheel(boolean firstTurnWheel) {
    this.firstTurnWheel = firstTurnWheel;
  }

  public boolean isSecondTurnWheel() {
    return secondTurnWheel;
  }

  public void setSecondTurnWheel(boolean secondTurnWheel) {
    this.secondTurnWheel = secondTurnWheel;
  }

  @Override
  public String toString() {
    return "Wheel [startOffset=" + numToChar(startOffset) +
        ", currentWheelPosition=" + numToChar(currentWheelPosition) +
        ", ringPosition=" + numToChar(ringPosition) + "]\n" +
        "cipher= " + Arrays.toString(cipher) + "\n";
  }

  public Wheel() {
    this.cipher = generateCipher();
    // this.cipher = generateCaesarCipher();
  }

  public Wheel(int startOffset, int ringPosition) {
    this.cipher = generateCipher();
    this.startOffset = startOffset;
    this.ringPosition = ringPosition;
    this.currentWheelPosition = startOffset;
    this.firstTurnWheel = false;
    this.secondTurnWheel = false;
  }

  public Wheel(int startOffset, int ringPosition, int wheelNumber, boolean defaultEnigma) {
    if (defaultEnigma) {
      this.cipher = DEFAULT_CIPHER[wheelNumber];
    } else {
      this.cipher = generateCipher();
    }
    this.startOffset = startOffset;
    this.ringPosition = ringPosition;
    this.currentWheelPosition = startOffset;
    this.firstTurnWheel = false;
    this.secondTurnWheel = false;
  }

  private char[] generateCipher() {

    char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
        'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    char[] cipher = new char[26];
    ArrayList<Character> alphaList = new ArrayList<Character>(26);
    int maxRandom = 25;
    int randValue;

    for (char c : ALPHABET) {
      alphaList.add(c);
    }

    for (int i = 0; i < ALPHABET.length - 1; i++) {
      randValue = (int) ((maxRandom * Math.random()));
      cipher[i] = alphaList.get(randValue);
      alphaList.remove(randValue);
      maxRandom--;
    }

    cipher[ALPHABET.length - 1] = alphaList.get(0);

    return cipher;
  }

  public char[] generateCaesarCipher() {
    char[] cipher = new char[26];

    int random = (int) ((17 * Math.random()));

    System.out.println("Caesar: " + random);
    for (int i = 0; i <= 25; i++) {
      if (i + random > 25) {
        cipher[i] = (char) ((i + random - 26) + 65);
      } else {
        cipher[i] = (char) ((i + random) + 65);
      }
    }
    return cipher;
  }

  private char numToChar(int number) {
    return (char) (number + 65);
  }

  public void resetWheel() {
    this.currentWheelPosition = this.startOffset;
    this.firstTurnWheel = false;
    this.secondTurnWheel = false;
  }

}
