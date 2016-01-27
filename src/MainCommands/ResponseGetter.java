package MainCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by Omelchuk.Roman on 26.01.2016.
 */
public class ResponseGetter extends Thread {

    private BufferedReader in;
    private Socket server;

    private String progress;

    public ResponseGetter(BufferedReader in, Socket server) {
        this.in = in;
        this.server = server;
    }

    public String getProgress() {
        return progress;
    }

    @Override
    public void run() {
        super.run();
        String serverResponse;
        while(true) {
            try {
                in = new BufferedReader(new InputStreamReader(server.getInputStream()));
                serverResponse = in.readLine();
                this.progress = serverResponse;
                System.out.println(serverResponse);
                if(serverResponse.contains("!")) break;
            } catch (IOException e) {
                System.out.println("Error: "+e.toString());
                e.printStackTrace();
            }
        }
    }
}
