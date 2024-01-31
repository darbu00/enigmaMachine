import java.io.*;
import java.util.ArrayList;

public class EnigmaReader {

    public ArrayList<String> readFile(String fileName) throws Exception {

        fileName = "/Users/David/Desktop/alphabet.txt";
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
        int lineNumber = 0;

        while ((nextLine = bufferedReader.readLine()) != null) {
            //process the line
            fileLine.add(nextLine);
            //System.out.println(fileLine);
            lineNumber++;
        }

        bufferedReader.close();

        return fileLine;

    }
}
