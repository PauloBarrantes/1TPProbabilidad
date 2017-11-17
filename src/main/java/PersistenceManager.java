import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.StringTokenizer;

public class PersistenceManager {
    public PersistenceManager(){


    }
    public List<Word> readToShow() throws FileNotFoundException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Word> list = new ArrayList<Word>();

        String lecture = "";
        String hil = "";
        double frecS = 0;
        double frecN = 0;
        double probS = 0;
        double probN = 0;
        try {
            archivo = new File ("C:\\Users\\Paulo\\Words.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String line;
            while((line=br.readLine())!=null){
                StringTokenizer Stword = new StringTokenizer(line);

                hil = Stword.nextElement().toString();
                lecture = Stword.nextElement().toString();
                frecS = Double.parseDouble(lecture);
                lecture = Stword.nextElement().toString();
                frecN = Double.parseDouble(lecture);
                lecture = Stword.nextElement().toString();
                probS = Double.parseDouble(lecture);
                lecture = Stword.nextElement().toString();
                probN = Double.parseDouble(lecture);
                Word w = new Word(hil,frecS,frecN,probS, probN);
                list.add(w);

            }

        }catch(Exception ie){
            ie.printStackTrace();
        }finally{
            try{
                if( null != fr ){
                    fr.close();
                }
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }


        // Cargar en una lista las palabras que están en un archivo
        return list;
    }
    public void write( HashMap<String,Word> words) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("C:\\Users\\Paulo\\Words.txt");
            pw = new PrintWriter(fichero);

            PrintWriter finalPw = pw;
            words.forEach((k, v) -> {
                finalPw.println(v.getWord() + " " + v.getFrecuencyS() + " " + v.getFrecuencyN() + " " +  v.getProbabilityS() + " " + v.getProbabilityN());
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        //Crear un archivo guardando las palabras
    }

}
