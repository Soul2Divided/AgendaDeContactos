package agendadecontactos;

import java.io.*;

/**
 *
 * @author gaby-
 */

public class FilesFunctions {

    private File dataFile;

    public FilesFunctions(File newFile) {
        this.dataFile = newFile;
    }

    public void createFile() {
        try {
            if (dataFile.createNewFile()) {
                System.out.println("Archivo creado correctamente.");
            }
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public void deleteFile() {
        if (dataFile.delete()) {
            System.out.println("Archivo eliminado con exito.");
        } else {
            System.out.println("El archivo que intenta eliminar no existe.");
        }
    }

    public void writeFile(String dataToWrite, boolean append) {
        try (FileWriter dataWrite = new FileWriter(dataFile, append)) {
            dataWrite.write(dataToWrite + "\n");
            dataWrite.close();
            //System.out.println("Escritura en el archivo realizada con exito.");
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    public String readFile() {
        String saveLines = "";

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                saveLines += line + "\n";
            }
        } catch (IOException ex) {
            System.out.println("Ocurrió un error al leer el archivo.");
            ex.printStackTrace(System.out);
        }

        return saveLines;
    }
    
    public String lastLineReaded() {
        String saveLines = "";

        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            String lastLine;
            while ((lastLine = br.readLine()) != null) {
                saveLines = lastLine;
            }
        } catch (IOException ex) {
            System.out.println("Ocurrió un error al leer el archivo.");
            ex.printStackTrace(System.out);
        }

        return saveLines;
    }
}
