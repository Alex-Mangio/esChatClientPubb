package client_pubb;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadClient extends Thread {

    Socket s;
    List lista;

    public ThreadClient(Socket s) {
        this.s = s;
        this.lista  =  new ArrayList<>();
    }

    public void run() {

        try{

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        DataOutputStream out = new DataOutputStream(s.getOutputStream());

        System.out.println("Ti sei connesso con il server");

        //gestione username{
        System.out.println("Prima digita il tuo username");
        Scanner inputUsername = new Scanner(System.in);
        out.writeBytes(inputUsername.nextLine() + "\n");
        //}

        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
