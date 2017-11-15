import java.io.File;
import java.util.ArrayList;
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
        String hil = "";
        String frecS = "";
        String probS = "";

        double frec = 0;
        double prob = 0;
        try {
            archivo = new File ("C:\\words.txt");
            fr = new FileReader (archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String line;
            while((line=br.readLine())!=null){
                StringTokenizer Stword = new StringTokenizer(line);
                hil = Stword.nextElement().toString();
                frecS = Stword.nextElement().toString();
                frec = Double.parseDouble(frecS);
                probS = Stword.nextElement().toString();
                prob = Double.parseDouble(probS);

                Word w = new Word(hil,frec,prob);
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
    public void write( List<Word> words) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("c:/words.txt");
            pw = new PrintWriter(fichero);

            for (Word i : words) {
                pw.println(i.getWord() + " " + i.getFrecuency() + " " + i.getProbability());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para
                // asegurarnos que se cierra el fichero.
                if (null != fichero)
                    fichero.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        //Crear un archivo guardando las palabras
    }

}
