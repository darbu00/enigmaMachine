import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

public class Enigma {

  private int numberOfWheels;
  private int[] wheelOrder;
  private Plugboard plugboard;
  private Wheel[] wheels;
  private Reflector reflector;
  private Encryptor encryptor;
  private boolean allowSpecialCharacters = false;

  public int getNumberOfWheels() {
    return numberOfWheels;
  }

  public void setNumberOfWheels(int numberOfWheels) {
    this.numberOfWheels = numberOfWheels;
  }

  public int[] getWheelOrder() {
    return wheelOrder;
  }

  public void setWheelOrder(int[] wheelOrder) {
    this.wheelOrder = wheelOrder;
  }

  public Plugboard getPlugboard() {
    return plugboard;
  }

  public void setPlugboard(Plugboard plugboard) {
    this.plugboard = plugboard;
  }

  public Wheel[] getWheels() {
    return wheels;
  }

  public void setWheels(Wheel[] wheels) {
    this.wheels = wheels;
  }

  public Reflector getReflector() {
    return reflector;
  }

  public void setReflector(Reflector reflector) {
    this.reflector = reflector;
  }

  public Encryptor getEncryptor() {
    return encryptor;
  }

  public void setEncryptor(Encryptor encryptor) {
    this.encryptor = encryptor;
  }

  public boolean isAllowSpecialCharacters() {
    return allowSpecialCharacters;
  }

  public void setAllowSpecialCharacters(boolean allowSpecialCharacters) {
    this.allowSpecialCharacters = allowSpecialCharacters;
  }

  @Override
  public String toString() {
    return "Enigma [numberOfWheels=" + numberOfWheels + ", wheelOrder=" + Arrays.toString(wheelOrder) + ", plugboard="
        + plugboard + "\nwheels=" + Arrays.toString(wheels) + "\n" + "reflector="
        + reflector + ", encryptor=" + encryptor + ", allowSpecialCharacters=" + allowSpecialCharacters + "]";
  }

  public Enigma(int numberOfWheels, int[] wheelOrder, int numberOfPlugs, boolean defaultEnigma) {
    this.numberOfWheels = numberOfWheels;
    this.wheelOrder = wheelOrder;
    this.plugboard = new Plugboard(numberOfPlugs);
    this.reflector = new Reflector();
    this.encryptor = new Encryptor();
    this.wheels = new Wheel[numberOfWheels];

    if (defaultEnigma) {
      for (int i = 0; i < numberOfWheels; i++) {
        this.wheels[i] = new Wheel(0, 0, i, defaultEnigma);
      }
    } else {
      // TODO: create non-standard wheels here
      for (int i = 0; i < numberOfWheels; i++) {
        this.wheels[i] = new Wheel(0, 0);
      }
    }
    this.wheels[wheelOrder[0]].setFirstTurnWheel(true);
  }

  public Enigma(int numberOfWheels, int numberOfDefaultWheels, Set<Integer> wheelOrder) {
    this.numberOfWheels = numberOfWheels;
    this.wheelOrder = new int[wheelOrder.size()];
    int index = 0;
    for (int i : wheelOrder) {
      this.wheelOrder[index] = i;
      index++;
    }
    this.plugboard = new Plugboard(0);
    this.reflector = new Reflector();
    this.encryptor = new Encryptor();
    this.wheels = new Wheel[numberOfWheels];
    if (numberOfDefaultWheels > 0) {
      for (int i = 0; i < numberOfDefaultWheels; i++) {
        this.wheels[i] = new Wheel(0, 0, i, true);
      }
      for (int i = numberOfDefaultWheels; i < numberOfWheels; i++) {
        this.wheels[i] = new Wheel(0, 0);
      }
    } else {
      for (int i = numberOfDefaultWheels; i < numberOfWheels; i++) {
        this.wheels[i] = new Wheel(0, 0);
      }
    }
    this.wheels[this.wheelOrder[0]].setFirstTurnWheel(true);
  }

  public void turnWheels(Wheel[] wheels, int[] wheelOrder) {

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
          /*
           * If any wheel other than the first turns, the preceeding wheel should
           * turn with it. In reality this only impacts wheels between the first
           * and last, because the first always turns and due to the mechanics,
           * the last does not preceede a wheel.
           *
           */
          if ((wheels[wheelOrder[i]].getRingPosition() == wheels[wheelOrder[i]].getCurrentWheelPosition())
              && i != (wheelOrder.length - 1)) {
            wheels[wheelOrder[i]].setSecondTurnWheel(true);

          }
        } else {
          wheels[wheelOrder[i]].setSecondTurnWheel(false);
        }
      }
    }
    // System.out.println(Arrays.toString(wheels));
  }

  protected void resetWheels() {
    for (int num : wheelOrder) {
      this.wheels[num].resetWheel();
    }
    this.wheels[wheelOrder[0]].setFirstTurnWheel(true);
  }
}
