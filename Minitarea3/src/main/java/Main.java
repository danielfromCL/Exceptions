import java.io.*;
import java.util.Hashtable;
import java.util.regex.Pattern;
/**
 * Main class that has a private Hashtable.
 */
public class Main {
    private static Hashtable <String, String> ruts = new Hashtable<String, String>();


    public static boolean validarRut(int rut, char digitoVerificador) {
        int m = 0;
        int s = 1;
        while (rut != 0) {
            s = (s + rut % 10 * (9 - m++ % 6)) % 11;
            rut /= 10;
        }
        return digitoVerificador == (char) (s != 0 ? s + 47 : 75);
    }

    /**
     * Checks whether a line is separated by one tab and, if not, throws an Exception.
     */
    public static void splitter(String s) throws InvalidFormatException {
        String[] a = s.split("\t");
        if (a.length != 2){
            throw new InvalidFormatException("Invalid line format");
        }
    }
    /**
     * Checks whether a rut is valid and, if not, throws an Exception.
     */
    public static void rLine(String a) throws InvalidRutException {
        if(Pattern.matches("^\\d{1,2}(.\\d{3}.\\d{3}|\\d{6})-\\d$", a)) {
            String rut = a.replace(".", "");
            String[] rutsep = rut.split("-");
            int number = Integer.parseInt(rutsep[0]);
            char verifier = rutsep[1].charAt(0);
            if(!validarRut(number, verifier)){
                throw new InvalidRutException("Invalid rut");
            }
        }
        else{
            throw new InvalidRutException("Invalid rut format");
        }
    }
    /**
     * Opens the file and inserts valid values on the class's Hashtable;
     * Thows InvalidFileException if the file isn't valid (if it's extension is not ".tsv", if the file doesn't exist or if it wasn't found).
     */
    static void openFile(String rootname) throws InvalidFileException {
        if (rootname.length() < 4) {
            throw new InvalidFileException("Invalid file");
        } else {
            if(!rootname.substring(rootname.length() - 4).equals(".tsv")){
                throw new InvalidFileException("Invalid file extension");
            }
            else{
                InputStream inputStream = null;
                try {
                    File file = new File(rootname);
                    inputStream = new FileInputStream(file);
                    FileReader fr=new FileReader(file);
                    BufferedReader br=new BufferedReader(fr);
                    String line;
                    while((line=br.readLine())!=null)
                    {
                        try{
                            splitter(line);
                            String[] a = line.split("\t");
                            try{
                                rLine(a[0]);
                                String rut = a[0].replace(".", "");
                                ruts.put(rut, a[1]);
                            }
                            catch(InvalidRutException e){
                                System.out.println("Invalid Rut: "+ a[0]);
                            }
                        }
                        catch(InvalidFormatException e){
                            System.out.println("Invalid line format (not two blocks of text divided by a TAB): " + line);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new InvalidFileException("File wasn't found");
                } catch (IOException e) {
                    System.out.println("Stream error");
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            throw new InvalidFileException("File wasn't closed");
                        }
                    }
                    else {
                        throw new InvalidFileException("File wasn't opened");
                    }
                }
            }
        }
    }
    /**
     * Runs openFile and returns the class's Hashtable.
     */
    public static Hashtable AuxMethod(String filename){
        try{
            openFile(filename);
        }
        catch(InvalidFileException e){
            System.out.println("Invalid File");
        }
        finally{
            return ruts;
        }
    }

    public static void main(String[] args){
        String filename = args[0];
        AuxMethod(filename);
    }
}
