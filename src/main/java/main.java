import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args){

        File file = new File("TestFile.txt"); //Create file var with that name
        String fileString = ""; //Where the file as one string will be stored

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String currLine;
            while((currLine = br.readLine()) != null){ //Loop until the line you read from the file is null which means its end of file
            fileString = fileString.concat(currLine); //Concat the curr line of the file in the fileString var
            } 
            } catch (IOException e) {
                e.printStackTrace();
            }

            List<String> tokens = Compiler.tokenize(fileString); //Turn the file into tokens
            System.out.println(tokens);
    }
}