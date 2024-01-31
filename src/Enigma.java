import java.util.ArrayList;

public class Enigma {

    private int numberOfWheels;
    private int[] wheelOrder;
    private Plugboard plugboard;
    private int numberOfPlugs;
    private Wheel[] wheels;
    private Reflector reflector;
    private Encrypt encryptor;
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

    public int getNumberOfPlugs() {
        return numberOfPlugs;
    }

    public void setNumberOfPlugs(int numberOfPlugs) {
        this.numberOfPlugs = numberOfPlugs;
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

    public Encrypt getEncryptor() {
        return encryptor;
    }

    public void setEncryptor(Encrypt encryptor) {
        this.encryptor = encryptor;
    }

    public boolean isAllowSpecialCharacters() {
        return allowSpecialCharacters;
    }
    public void setAllowSpecialCharacters(boolean allowSpecialCharacters) {
        this.allowSpecialCharacters = allowSpecialCharacters;
    }

    public Enigma(int numberOfWheels, int[] wheelOrder, int numberOfPlugs) {
        this.numberOfWheels = numberOfWheels;
        this.wheelOrder = wheelOrder;

        this.plugboard = new Plugboard(numberOfPlugs);
        this.reflector = new Reflector();
        this.encryptor = new Encrypt();

        this.wheels = new Wheel[numberOfWheels];

        for (int i = 0; i < numberOfWheels; i++) {
            this.wheels[i] = new Wheel(0, 0);
        }
        this.wheels[wheelOrder[0]].setFirstTurnWheel(true);
    }

    public static void turnWheels(Wheel[] wheels, int[] wheelOrder) {

        for (int i = 0; i < wheelOrder.length; i++) {
            if (wheels[wheelOrder[i]].getCurrentWheelPosition() == wheels[wheelOrder[i]].getRingPosition()) {
                if (i != (wheelOrder.length -1)) {
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
                        wheels[wheelOrder[i]].setSecondTurnWheel(true); //If the last wheel turns, the next last wheel turns again on the next input character
                    }
                } else {
                    wheels[wheelOrder[i]].setSecondTurnWheel(false);
                }
            }
        }
    }



}
