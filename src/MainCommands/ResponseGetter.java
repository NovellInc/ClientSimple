package MainCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Omelchuk.Roman on 26.01.2016.
 */
public class ResponseGetter extends Thread {

    private BufferedReader in;
    private Socket server;
    private ConnectionControl control = new ConnectionControl();

    private String progress;

    public ResponseGetter(BufferedReader in, Socket server) {
        this.in = in;
        this.server = server;
    }

    public String getProgress() {
        return progress;
    }

    @Override
    public void run() throws IllegalArgumentException{
        super.run();
        String serverResponse = null;
        while(true) {
            try {
                //in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                if(server.isClosed()) {
                    this.interrupt();
                    break;
                }
                try {
                    serverResponse = in.readLine();
                    this.progress = serverResponse;
                    if (!serverResponse.isEmpty()) System.out.println(serverResponse);
                    if (serverResponse.contains("!")) break;
                } catch (IOException e) {
                    System.out.println("Error read response: " + e.toString());
                    try {
                        control.closeConnection(in, server);
                    } catch (IOException ioe) {
                        System.out.println("Error close session: " + ioe.toString());
                    }
                    this.interrupt();
                    break;
                }
            } catch (IllegalArgumentException iae) {
                System.out.println("Error read response: " + iae.toString());
                iae.printStackTrace();
                this.interrupt();
                break;
            }
//            catch (IOException e) {
//                System.out.println("RG IOException error: " + e.toString());
//                try {
//                    control.closeConnection(in, server);
//                } catch (IOException ioe) {
//                    System.out.println("Error close session: " + ioe.toString());
//                }
//                e.printStackTrace();
//                this.interrupt();
//                break;
//            }
        }
    }
}
