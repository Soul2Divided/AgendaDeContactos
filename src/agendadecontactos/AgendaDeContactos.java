/*

Funcionamientos requeridos:
 - Agregar contactos.
 - Listar contactos.
 - Buscar contactos.
 - Eliminar contactos.
 - Guardar informacion.
 - Leer informacion.

FALTAN ALGUNAS VALIDACIONES, COMO QUE NO SE REPITAN LOS CONTACTOS, PERO ME DIO VAGANCIA HACERLA.
AGREGAR ALGUNA MANERA DE CANCELAR EL INGRESO DE DATOS (AÑADIR, MODIFICACION, ETC)
 */
package agendadecontactos;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author gaby-
 */
public class AgendaDeContactos {

    public static void main(String[] args) {
        File contactsFile = new File("contactsDataFile.txt");
        DataManager newList = new DataManager(contactsFile);
        Scanner kb = new Scanner(System.in);
        boolean loop = true;
        int opt;

        newList.dataCharge();

        while (loop) {
            System.out.println("--- SELECCIONA UNA OPCION ---");
            System.out.print("[1] Ver lista de contactos\n[2] Agregar contacto\n[3] Eliminar contacto\n[4] Modificar contacto\n[0] Para cerrar el programa\n\n--> ");
            opt = kb.nextInt();
            kb.nextLine();

            switch (opt) {
                case 0:
                    loop = false;
                    break;
                case 1:
                    newList.listContacts();
                    System.out.println("Pulse [ENTER] para continuar");
                    kb.nextLine();
                    clearConsole();
                    break;
                case 2:
                    newList.addContact();
                    System.out.println("Pulse [ENTER] para continuar");
                    kb.nextLine();
                    clearConsole();
                    break;
                case 3:
                    newList.deleteContact();
                    System.out.println("Pulse [ENTER] para continuar");
                    kb.nextLine();
                    clearConsole();
                    break;
                case 4:
                    newList.modifyContact();
                    System.out.println("Pulse [ENTER] para continuar");
                    kb.nextLine();
                    clearConsole();
                    break;
                default:
                    System.out.println("Numero incorrecto, intente nuevamente.\n");
                    System.out.println("Pulse [ENTER] para continuar");
                    kb.nextLine();
                    clearConsole();
            }
        }
    }

    public static void clearConsole() {
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }
}
