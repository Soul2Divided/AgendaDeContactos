package agendadecontactos;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gaby-
 */
public class DataManager {

    private Scanner kb = new Scanner(System.in);
    private ArrayList<Contact> contactsList;
    private FilesFunctions ffile;

    public DataManager(File receivedFile) {
        contactsList = new ArrayList();
        ffile = new FilesFunctions(receivedFile);
    }

    public void dataCharge() {
        int j = 1;
        String rd = ffile.readFile();
        String[] jumpLine = rd.split("\n");

        if (!rd.isBlank()) {
            for (String contactData : jumpLine) {
                String[] dataSplit = contactData.split(",");
                Contact contactCharge = new Contact(j, dataSplit[1], dataSplit[2], dataSplit[3]);
                j++;
                contactsList.add(contactCharge);
            }
        }

        writeCsv();
    }

    public boolean addContact() {
        boolean success = true;
        String name;
        String cellphone;
        String email;

        name = addName();

        if (name.isEmpty()) {
            success = false;
            System.out.println("\nVolviendo al menu...\n");
            return success;
        }

        cellphone = addCellphone();

        if (cellphone == null) {
            success = false;
            System.out.println("\nVolviendo al menu...\n");
            return success;
        }

        email = addEmail();

        int lastId = readLastId();

        Contact newContact = new Contact(lastId, name, cellphone, email);
        contactsList.add(newContact);

        ffile.createFile();
        writeCsv();

        return success;
    }

    private String addName() {
        String name;

        while (true) {
            try {
                System.out.print("\nIngrese el nombre del contacto ([0] Para cancelar): ");
                name = kb.nextLine();
                if (goBack(name)) {
                    return "";
                }
                if (!validateName(name)) {
                    throw new RuntimeException("El nombre no puede quedar vacio y debe contener mas de 3 letras.\nVuelva a intentar.");
                }
                if (!noNumbers(name)) {
                    throw new RuntimeException("El nombre no puede contener numeros ni caracteres especiales.\nVuelva a intentar.");
                }
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        name = capitalize(name);

        return name;
    }

    private String addCellphone() {
        String cellphone = null;
        boolean loop = true;

        while (loop) {
            try {
                System.out.print("\nIngrese el numero telefonico del contacto siguiendo el siguiente patron -> [11XXXXXX]: ");
                cellphone = kb.nextLine();
                if (!validateCellphone(cellphone)) {
                    throw new RuntimeException("Estructura del numero telefonico incorrecta.\nVuelva a intentar y recuerde que debe seguir el siguiente patron -> [11XXXXXX]");
                }
                if (!alreadyExist(cellphone)) {
                    loop = false;
                    cellphone = null;
                    throw new RuntimeException("Este numero telefonico ya esta asociado a un contacto.\nCancelando carga...\n");
                }
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }

        return cellphone;
    }

    private String addEmail() {
        String email;
        
        while (true) {
            try {
                System.out.print("\nIngrese el email del contacto siguiendo la estructura basica: ");
                email = kb.nextLine();
                if (!validateEmail(email)) {
                    throw new RuntimeException("Estructura del email incorrecta.\nVuelva a intentar y recuerde que debe seguir la estructura basica de un email (ej: alguien@ejemplo.com): ");
                }
                break;
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
        
        return email;
    }

    public boolean deleteContact() {
        boolean success = true;
        Contact contactSearch;
        String contactToDelete;

        listContacts();
        
        System.out.print("\nIntroduzca el numero del contacto que desea eliminar ([0] Para cancelar): ");
        contactToDelete = kb.nextLine();

        if (goBack(contactToDelete)) {
            success = false;
            System.out.println("\nVolviendo al menu...\n");
            return success;
        }

        contactSearch = searchContact(contactToDelete);

        if (contactSearch != null) {
            contactsList.remove(contactPosition(contactSearch));
            System.out.println("El contacto " + contactSearch.getName() + " se ha eliminado correctamente.");
            writeCsv();
        } else {
            System.out.println("Ningun contacto tiene ese numero.");
        }

        return success;
    }

    public boolean modifyContact() {
        boolean success = true;
        Contact contactSearch;
        String contactToModify;
        String modify;
        int option = 0;
        int getPos = 0;
        
        listContacts();
        
        System.out.print("\nIntroduzca el numero del contacto que desea modificar ([0] Para cancelar): ");
        contactToModify = kb.nextLine();

        if (goBack(contactToModify)) {
            success = false;
            System.out.println("\nVolviendo al menu...\n");
            return success;
        }

        contactSearch = searchContact(contactToModify);
        
        if (contactSearch != null) {
            System.out.print("Seleccione el atributo a modificar:\n[1] Nombre\n[2] Telefono\n[3] Email\n\n--> ");
            option = kb.nextInt();
            kb.nextLine();

            switch (option) {
                case 1:
                    modify = addName();
                    contactsList.get(getPos).modifyName(modify);
                    break;
                case 2:
                    modify = addCellphone();
                    contactsList.get(getPos).modifyCellphone(modify);
                    break;
                case 3:
                    modify = addEmail();
                    contactsList.get(getPos).modifyEmail(modify);
                    break;
            }
        }
        
        writeCsv();
        
        System.out.println("Contacto modificado con exito");

        return success;
    }
    
    public void listContacts() {
        for (Contact c : contactsList) {
            System.out.println(c.toList() + "\n-----------");
        }
    }

    private boolean validateName(String name) {
        boolean success = true;

        if (name.isBlank()) {
            success = false;
        }

        if (name.length() < 3) {
            success = false;
        }

        return success;
    }

    private boolean noNumbers(String name) {
        boolean success = true;
        String lowerName = name.toLowerCase();
        String[] split = lowerName.split("");
        
        for (int i=0; i < name.length(); i++) {
            String single = split[i];
            int asciiValue = (int) single.charAt(0);
            if (asciiValue < 97 || asciiValue > 122) {
                success = false;
                break;
            }
        }

        return success;
    }

    private boolean validateCellphone(String cellphone) {
        String cellphonePattern = "^11\\d{6}$";

        Pattern pattern = Pattern.compile(cellphonePattern);
        Matcher matcher = pattern.matcher(cellphone);

        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    private boolean alreadyExist(String cellphone) {
        boolean exist = true;

        if (searchContact(cellphone) != null) {
            exist = false;
        }

        return exist;
    }

    private void writeCsv() {
        String joinData = "";

        for (int i = 0; i < contactsList.size(); i++) {
            joinData += contactsList.get(i).toCsv() + "\n";
        }

        ffile.writeFile(joinData, false);
    }

    private int readLastId() {
        int lastId;

        try {
            lastId = contactsList.getLast().getId();
            lastId++;
        } catch (Exception e) {
            lastId = 1;
        }

        return lastId;
    }

    private Contact searchContact(String cellphoneToSearch) {
        Contact contactFinded = null;

        for (Contact c : contactsList) {
            if (c.getCellphone().equals(cellphoneToSearch)) {
                contactFinded = c;
            }
        }

        return contactFinded;
    }
    
    private int contactPosition(Contact c) {
        int pos = 0;
        
        for (int i = 0; i < contactsList.size(); i++) {
            if(contactsList.get(i).getCellphone().equals(c.getCellphone())) {
                pos = i;
                break;
            }
        }
        
        return pos;
    }

    private boolean goBack(String character) {
        boolean yes = false;

        if (character.equalsIgnoreCase("0")) {
            yes = true;
        }

        return yes;
    }

    private String capitalize(String str) {
        String capStr = "";
        String [] split = str.split("");

        split[0] = split[0].toUpperCase();

        for (int i=0; i < split.length; i++) {
            capStr += split[i];
        }

        return capStr;
    }

    public void testing() {
        String name = "papa";

        System.out.println(capitalize(name));
    }

    @Override
    public String toString() {
        return "== CONTACTS ==\n" + contactsList;
    }
}
