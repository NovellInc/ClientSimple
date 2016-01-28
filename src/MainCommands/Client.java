package MainCommands;

/**
 * Created by Omelchuk.Roman on 21.01.2016.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.ServerException;

public class Client {
    public static void main(String[] args) throws IOException {

        final int PORT = 4444;

        System.out.println("Welcome to Client side");
        String arg = "localhost";
        int timeout = 0;

        Socket server = null;
        ConnectionControl control = new ConnectionControl();
        int tryConnect = 0;
        while(true)
        {
            System.out.println("Connecting to " + arg);
            tryConnect++;
            try {
                server = new Socket("localhost", PORT);
                PrintWriter out = new PrintWriter(server.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                BufferedReader inUser = new BufferedReader(new InputStreamReader(System.in));

                String userQuery = "",
                       serverResponse = null;

                while (true) {
                    //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                    //server.setSoTimeout(timeout);
                    tryConnect = 0;
                    System.out.println("Choose command below.");
                    System.out.print("1.Build: ");
                    System.out.println("11.House 12.Civil 13.Enterprise");
                    System.out.println("2.Player statistic");
                    userQuery = inUser.readLine();// + " " + sdf.format(new Date());
                    if(server.isClosed()) {
                        System.out.println("No connection.");
                        System.exit(-1);
                    }
                    out.println(userQuery);
                    if (userQuery.contains("close")) {
                        control.closeSession(out, in);
                        System.out.println("Connection closed");
                        break;
                    } else
                    if (userQuery.contains("exit")) {
                        control.closeSession(out, in);
                        System.out.println("Client shut down");
                        control.closeConnection(inUser, server);
                        System.exit(-1);
                    }
                    in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                    new ResponseGetter(in, server).start();

                }
                System.out.print("Exit: 0\nContinue: press any key\n>>> ");
                BufferedReader decision = new BufferedReader(new InputStreamReader(System.in));
                decision.readLine();

                if(decision.equals("0")) {
                    control.closeConnection(inUser, server);
                    System.exit(-1);
                }

            } catch (ServerException se) {
                System.out.println("ServerException error: " + se.toString());
            } catch (SocketTimeoutException ste) {
                System.out.println("SocketTimeoutException error: " + ste.toString());
            } catch (IOException e) {
                System.out.println("No connection. Error "+e.toString());
            }

            if(tryConnect == 5) {
                System.out.println("Client shut down");
                break;
            }
        }

    }
}
