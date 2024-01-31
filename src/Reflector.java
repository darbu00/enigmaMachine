public class Reflector {

    private final char[] DEFAULT_REFLECTOR = {'Y','R','U','H','Q','S','L','D','P','X','N','G','O','K','M','I','E','B','F','Z','C','W','V','J','A','T'};

    public char reflectChar (char character) {

        if ((int) character < 65 || (int) character > 90) {
            return '_';
        }

        int charValue = ((int) character) - 65;
        //charValue += 13;
        character = DEFAULT_REFLECTOR[charValue];
        if ((int) character > 90) {
            character = (char) (charValue - 26);
        }
        return character;
    }

}
