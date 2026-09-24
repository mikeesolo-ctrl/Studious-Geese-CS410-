import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.List;

public class main {
    public static void main(String[] args){

        Scanner scnr = new Scanner(System.in); //Create Scanner to get file name
        String fileName = scnr.next(); //Set the next System in to fileName

        File file = new File(fileName); //Create file var with that name
        String fileString; //Where the file as one string will be stored
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

            //print tokens to the console
            for(String token : tokens){
                System.out.println(token);
            }

            //write tokens to a file
            try(FileWriter output = new FileWriter("tokens.txt")) {
                for(String token : tokens){
                    output.write(token + "\n");
                }
            } catch (IOException e) {
                System.err.println("Error writing output file: " + e.getMessage());
            }
    }
}