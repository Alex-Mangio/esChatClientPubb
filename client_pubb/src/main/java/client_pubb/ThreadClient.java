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

            boolean connessione = true;
            boolean connection = true;

            // gestione username
            do {
                String benvenuto = in.readLine();
                System.out.println(benvenuto);
                Scanner inputUsername = new Scanner(System.in);
                out.writeBytes(inputUsername.nextLine() + "\n");

                // MENU PRINCIPALE
                String menu = in.readLine();

                if (menu.equals("MENU")) {

                    connection = false;

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
                                System.out.println("1)Digita '1' per vedere la lista dei tuoi contatti \n");
                                System.out.println("2)Digita '2' per iniziare a chattare con un tuo contatto \n");
                                System.out.println("3)Digita '3' per tornare al MENU PRINCIPALE \n");

                                Scanner sceltaMenuPriv = new Scanner(System.in);

                                switch (sceltaMenuPriv.nextLine()) {

                                    case "1":
                                        // stampa la lista dei contatti PRIVATI
                                        ObjectInputStream objIn = new ObjectInputStream(s.getInputStream());
                                        ArrayList contatti = new ArrayList<String>();

                                        out.writeBytes("LISTA_CONTATTI" + "\n");

                                        contatti = (ArrayList) objIn.readObject();

                                        if (contatti.size() > 0) {
                                            for (int i = 0; i < contatti.size(); i++) {
                                                System.out.println(contatti.get(i));
                                            }
                                        } else {
                                            System.out.println("Non hai nessun contatto registrato \n");
                                        }
                                        break;

                                    case "2":
                                        break;

                                    case "3":

                                        break;

                                }

                                break;

                            case "2":
                                out.writeBytes("PUBBLICO" + "\n");
                                String risposta2 = in.readLine();

                                do {
                                    // MENU per le chat PUBBLICHE
                                    System.out.println("Scegli cosa vuoi fare in chat pubblica: \n");
                                    System.out.println("1)Digita '1' per vedere la lista dei tuoi contatti \n");
                                    System.out.println("2)Digita '2' per iniziare a chattare con un gruppo \n");
                                    System.out.println("3)Digita '3' per tornare al MENU PRINCIPALE \n");

                                    Scanner sceltaMenuPubb = new Scanner(System.in);

                                    switch (sceltaMenuPubb.nextLine()) {

                                        case "1":
                                            // stampa la lista dei gruppi PUBBLICI
                                            ArrayList<String> gruppi = new ArrayList<String>();

                                            out.writeBytes("LISTA_PUBBL" + "\n");

                                            while (true) {
                                                String lista = in.readLine();
                                                if (lista.equals("VUOTO")) {
                                                    System.out.println("Non hai contatti registrati \n");
                                                    break;
                                                } else {
                                                    gruppi.add(lista);
                                                }
                                            }

                                            if (!gruppi.isEmpty()) {
                                                for (String i : gruppi) {
                                                    System.out.println(i + "\n");
                                                }
                                            }
                                            break;

                                        case "2":
                                            //mando un messaggio in brodcast a tutti gli altri client connessi
                                            out.writeBytes("SCRIVI_PUBBLIC");
                                            String conferma = in.readLine();
                                            System.out.println(conferma);

                                            Scanner messaggioInput = new Scanner(System.in);
                                            String messaggio = messaggioInput.nextLine();

                                            String[] arrayMess = messaggio.split(messaggio);

                                            for(String i: arrayMess){
                                                //
                                            }

                                            break;

                                        case "3":

                                            break;

                                    }
                                } while (true);

                            case "3":
                                // DISCONNESSIONE client
                                out.writeBytes("DISCONNETTI" + "\n");
                                String risposta3 = in.readLine();
                                //username + s.close()
                                break;

                        }
                    } while (connessione);
                } else {
                    System.out.println(menu);
                }
            } while (connection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
