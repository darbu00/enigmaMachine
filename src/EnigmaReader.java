import java.io.*;
import java.util.ArrayList;

public class EnigmaReader {

  public ArrayList<String> readFile(String fileName) throws Exception {

    fileName = "/Users/Shared/enigmaTestFile.txt";
    File file = new File(fileName);
    FileInputStream fileInputStream;

    {
      try {
        fileInputStream = new FileInputStream(file);
      } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
      }
    }

    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

    ArrayList<String> fileLine = new ArrayList<String>();
    String nextLine = new String();

    while ((nextLine = bufferedReader.readLine()) != null) {
      // process the line
      fileLine.add(nextLine);
      fileLine.add("\n");
    }

    bufferedReader.close();

    return fileLine;

  }
}
