package MainCommands;

/**
 * Created by Omelchuk.Roman on 21.01.2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Client {
    public static void main(String[] args) throws IOException {

        final int PORT = 4444;

        System.out.println("Welcome to Client side");
        String arg = "localhost";
        int timeout = 0;

        Socket server = null;
        ConnectionControl control = new ConnectionControl();

        while(true)
        {
            System.out.println("Connecting to " + arg);

            try {
                server = new Socket("localhost", PORT);
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                BufferedReader inUser = new BufferedReader(new InputStreamReader(System.in));

                String userQuery = "",
                       serverResponse = "";

                while (true) {
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    //server.setSoTimeout(timeout);
                    System.out.print("Input query: ");
                    userQuery = inUser.readLine();// + " " + sdf.format(new Date());
                    out.println(userQuery);
                    if (userQuery.contains("close")) {
                        control.closeSession(out, in);
                        System.out.println("Connection closed");
                        break;
                    }
                    if (userQuery.contains("exit")) {
                        control.closeSession(out, in);
                        System.out.println("Client shut down");
                        control.closeConnection(inUser, server);
                        System.exit(-1);
                    }

                        serverResponse = in.readLine();
                        System.out.println(serverResponse);

                }
                System.out.print("Exit: 0\nContinue: press any key\n>>> ");
                BufferedReader desicion = new BufferedReader(new InputStreamReader(System.in));
                desicion.readLine();

                if(desicion.equals("0")) {
                    control.closeConnection(inUser, server);
                    System.exit(-1);
                }

            } catch (IOException e) {
                System.out.println("No connection. Error "+e.toString());
            }
        }

    }
}
