package MyPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class capitalone {
    public static void main(String[] args){
        boolean keepgoing=true;
        while(keepgoing) {
            //Get user input
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please insert filepath directory, e.g(C:\\Users\\wenso\\IdeaProjects\\capitalone\\src\\MyPackage\\test.txt)");
            System.out.println("Type \"N\" to stop program");
            String filePath = scanner.nextLine();
            if(filePath.equalsIgnoreCase("n")){
                keepgoing=false;
                System.out.println("thank you for using my software, enjoy the rest of your day");
                break;
            }
            System.out.println("You have selected the filepath " + filePath);

            //open files

            String filename = "test3.txt";
            String[] filenameArr= filename.split("\\\\",0);
            filename=filenameArr[filenameArr.length-1];
            System.out.println("you have selected filename: "+filename);



            String line = "";
            boolean inMultiLineComment = false;
            int lineCounter = 0;
            int TODOCounter = 0;
            int singleLineComment = 0;
            int blockLineComments = 0;
            int multiLineComment = 0;
            boolean[] trackLastTwoPythonLines = new boolean[2];
            trackLastTwoPythonLines[0] = false;
            trackLastTwoPythonLines[1] = false;
            if (!filename.contains(".")) {
                System.out.println("No \".\" was found, rejecting filename");
                return;
            }
            if (filename.charAt(0) == '.') {
                System.out.println("filename started with \".\" , rejecting filename");
                return;
            }


            // pass the path to the file as a parameter
            File file = new File(filePath);
            //File file = new File("test.txt");
            try (Scanner sc = new Scanner(file)) {
                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    //Count number of lines
                    lineCounter++;
                    //check single line comments (java)
                    if (line.contains("//") || (line.contains("/*") && line.contains("*/"))) {
                        if (line.trim().charAt(0) != '/') {
                            //System.out.println("\nBLOCK LINE COMMENT");
                            blockLineComments++;
                        }
                        //System.out.println("\nSINGLE LINE COMMENT");
                        if (line.contains("//")) {
                            singleLineComment++;
                        }
                    }
                    //Check multi line comments (java)
                    if (line.contains("/*")) {
                        inMultiLineComment = true;
                    }
                    if (inMultiLineComment) {
                        multiLineComment++;
                        if (line.contains("*/")) {
                            inMultiLineComment = false;
                        }

                    }


                    //check single line comments (python)

                    if (line.contains("# ")) {
                        if (line.trim().charAt(0) != '#') {
                            //System.out.println("\nBLOCK LINE COMMENT");
                            blockLineComments++;
                        }
                        //System.out.println("\nSINGLE LINE COMMENT");
                        if (trackLastTwoPythonLines[0] && !line.contains("TODO")) {
                            if (!trackLastTwoPythonLines[1]) {
                                //Remove the added single comment previously and replace it with a multiline comment
                                multiLineComment++;
                                singleLineComment--;
                            }
                            multiLineComment++;
                            trackLastTwoPythonLines[1] = trackLastTwoPythonLines[0];
                            trackLastTwoPythonLines[0] = true;
                            System.out.println("\nIN BLOCK COMMENT");
                        } else {
                            trackLastTwoPythonLines[1] = trackLastTwoPythonLines[0];
                            trackLastTwoPythonLines[0] = false;
                            singleLineComment++;
                        }
                    } else {
                        trackLastTwoPythonLines[1] = false;
                        trackLastTwoPythonLines[0] = false;
                    }


                    //Check TODO's
                    if (line.contains("TODO")) {
                        TODOCounter++;
                    }


                    //Print out line
                    System.out.println(line);
                }
                System.out.println("Total Lines: " + lineCounter);
                System.out.println("Total comment lines: " + (singleLineComment + multiLineComment));
                System.out.println("Total # of single line comments: " + singleLineComment);
                System.out.println("Total comment lines within block comments " + (multiLineComment));
                System.out.println("Total # of block line comments:" + blockLineComments);
                System.out.println("Total # of TODOs: " + TODOCounter);

            } catch (FileNotFoundException e) {
                System.out.printf("\ncannot find file %s", file.toString());
            } finally {
                //file.close();
            }
        }



    }

}

