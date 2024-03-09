import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class EnigmaWriter {
  public static void writeEncryptedMessage(ArrayList<Character> encryptedMessage) {
    String fileName = "/Users/Shared/enigmaTestFile.txt";

    try {
      BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));

      for (int i = 0; i < encryptedMessage.size(); i++) {
        if (encryptedMessage.get(i) == '\000') {
          bufferedWriter.append('.');
        } else {
          bufferedWriter.append(encryptedMessage.get(i));
        }
      }

      bufferedWriter.close();

      // byte[] encryptedBytes = new byte[encryptedMessage.size()];
      // for (int i = 0; i < encryptedMessage.size(); i++) {
      // encryptedBytes[i] = new Byte(encryptedMessage.get(i));
      //
      //
      // }
      // System.out.println("Byte length: " + encryptedBytes.length);
      // System.out.println("\n" + encryptedBytes.toString());
      // FileOutputStream fOStream = new FileOutputStream(fileName, true);
      // fOStream.write(encryptedBytes);
      // fOStream.close();

    } catch (IOException e) {
      System.out.println("Error: " + e);
      Scanner kbScanner = new Scanner(System.in);
      System.out.print("Press ENTER to continue ... ");
      kbScanner.nextLine();
      kbScanner.close();
    }

  }

}
