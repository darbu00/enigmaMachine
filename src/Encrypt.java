import java.util.ArrayList;

public class Encrypt {

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

            for (int indexCounter = 0 ; indexCounter <= 25; indexCounter ++){
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


}
