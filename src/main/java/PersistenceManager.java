import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.io.*;
import java.util.StringTokenizer;

import static java.lang.Integer.parseInt;
/**
 * @author Paulo Barrantes & Berta Sánchez
 */
public class PersistenceManager {
    public PersistenceManager(){

    }

    /**
     *
     * @return bool
     */
    public boolean archiveExist(){
        File archivo = null;
        archivo = new File (System.getProperty("user.home")+"/Documents/bayesian.txt");
        boolean bool = archivo.exists();
        return bool;
    }

    /**
     *
     * @return bool
     */
    public boolean existCredentials(){
        File archivo = null;
        archivo = new File (System.getProperty("user.home")+"/.credentials/BayesianSpamFilter/StoredCredential");
        boolean bool = archivo.exists();
        return bool;
    }

    /**
     *
     * @return ListOfWords
     * @throws FileNotFoundException
     */
    public List<Word> readToShow() throws FileNotFoundException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        List<Word> list = new ArrayList<Word>();

        String lecture = "";
        String hil = "";
        int frecS = 0;
        int frecN = 0;
        double probS = 0;
        double probN = 0;
        try {
            archivo = new File (System.getProperty("user.home")+"/Documents/bayesian.txt");

                fr = new FileReader (archivo);
                br = new BufferedReader(fr);

                // Lectura del fichero
                String line;
                while((line=br.readLine())!=null){
                    StringTokenizer Stword = new StringTokenizer(line);

                    hil = Stword.nextElement().toString();
                    lecture = Stword.nextElement().toString();
                    frecS = parseInt(lecture);
                    lecture = Stword.nextElement().toString();
                    frecN = parseInt(lecture);
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

    /**
     *
     * @return ListOfMessages
     * @throws FileNotFoundException
     */
    public HashMap<String,Word> readToBayesian() throws FileNotFoundException {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        HashMap<String,Word> list = new HashMap<String,Word>();

        String lecture = "";
        String hil = "";
        int frecS = 0;
        int frecN = 0;
        double probS = 0;
        double probN = 0;
        try {
            archivo = new File (System.getProperty("user.home")+"/Documents/bayesian.txt");

            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String line;
            while((line=br.readLine())!=null){
                StringTokenizer Stword = new StringTokenizer(line);

                hil = Stword.nextElement().toString();
                lecture = Stword.nextElement().toString();
                frecS = parseInt(lecture);
                lecture = Stword.nextElement().toString();
                frecN = parseInt(lecture);
                lecture = Stword.nextElement().toString();
                probS = Double.parseDouble(lecture);
                lecture = Stword.nextElement().toString();
                probN = Double.parseDouble(lecture);
                Word w = new Word(hil,frecS,frecN,probS, probN);
                list.put(hil,w);
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

    /**
     *
     *
     * @param words
     */
    public void write( HashMap<String,Word> words) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(System.getProperty("user.home")+"/Documents/bayesian.txt");
            pw = new PrintWriter(fichero);

            PrintWriter finalPw = pw;
            words.forEach((k, v) -> {
                finalPw.println(v.getWord() + "\t \t \t " + v.getFrecuencyS() + "\t \t " + v.getFrecuencyN() + "\t \t \t " +  v.getProbabilityS() + "\t \t \t " + v.getProbabilityN());
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
