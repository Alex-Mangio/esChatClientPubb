package client_pubb;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadClient extends Thread {

    Socket s;
    List lista = new ArrayList<String>();

    public ThreadClient(Socket s) {
        this.s = s;
    }

    public void run() {

        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            System.out.println("Ti sei connesso con il server");

            // gestione username
            String benvenuto = in.readLine();
            System.out.println(benvenuto);
            Scanner inputUsername = new Scanner(System.in);
            out.writeBytes(inputUsername.nextLine() + "\n");

            // MENU PRINCIPALE
            String menu = in.readLine();

            System.out.println("MENU PRINCIPALE \n");
            System.out.println("1)Digita '1' per andare in chat PRIVATA \n");
            System.out.println("2)Digita '2' per andare in chat PUBBLICA \n");
            System.out.println("3)Digita '3' per disconnetterti \n");

            Scanner sceltaMenuPrinc = new Scanner(System.in);

            do {
                switch (sceltaMenuPrinc.nextLine()) {

                    case "1":
                        out.writeBytes("PRIVATO" + "\n");
                        String risposta1 = in.readLine();

                        // MENU per le chat PRIVATE
                        System.out.println("Scegli cosa vuoi fare in chat privata: \n");
                        System.out.println("1)Digita '1' per vedere la lista delle tue chat PRIVATE \n");
                        System.out.println("2)Digita '2' per iniziare a chattare con un tuo contatto \n");
                        System.out.println("3)Digita '3' per tornare al MENU PRINCIPALE \n");

                        Scanner sceltaMenuPriv = new Scanner(System.in);

                        switch (sceltaMenuPriv.nextLine()) {

                            case "1":
                                // stampa la lista dei contatti PRIVATI
                                ObjectInputStream objIn = new ObjectInputStream(s.getInputStream());
                                List contatti = new ArrayList<String>();

                                out.writeBytes("LISTA_CONTATTI" + "\n");

                                contatti = (List) objIn.readObject();

                                if (contatti.size() > 0) {
                                    for (int i = 0; i < contatti.size(); i++) {
                                        System.out.println(contatti.get(i));
                                    }
                                } else {
                                    System.out.println("Non hai nessun contatto privato registrato \n");
                                }
                                break;

                            case "2":
                                break;

                            case "3":

                                break;

                        }

                        break;

                    case "2":
                        out.writeBytes("PUBBLICO");
                        String risposta2 = in.readLine();

                        // MENU per le chat PUBBLICHE
                        System.out.println("Scegli cosa vuoi fare in chat pubblica: \n");
                        System.out.println("1)Digita '1' per vedere la lista dei tuoi gruppi PUBBLICI \n");
                        System.out.println("2)Digita '2' per iniziare a chattare con un gruppo \n");
                        System.out.println("3)Digita '3' per tornare al MENU PRINCIPALE \n");

                        Scanner sceltaMenuPubb = new Scanner(System.in);

                        switch (sceltaMenuPubb.nextLine()) {

                            case "1":
                                // stampa la lista dei gruppi PUBBLICI
                                ObjectInputStream objIn = new ObjectInputStream(s.getInputStream());
                                List gruppi = new ArrayList<String>();

                                out.writeBytes("LISTA_GRUPPI" + "\n");

                                gruppi = (List) objIn.readObject();

                                if (gruppi.size() > 0) {
                                    for (int i = 0; i < gruppi.size(); i++) {
                                        System.out.println(gruppi.get(i));
                                    }
                                } else {
                                    System.out.println("Non hai nessun gruppo pubblico registrato \n");
                                }
                                break;

                            case "2":
                                break;

                            case "3":

                                break;

                        }
                        break;

                    case "3":
                        // DISCONNESSIONE client
                        out.writeBytes("DISCONNETTI");
                        String risposta3 = in.readLine();
                        break;

                }
            } while (true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
