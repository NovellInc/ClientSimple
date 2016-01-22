package MainCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Omelchuk.Roman on 22.01.2016.
 */
class ConnectionControl {

    public void closeSession(PrintWriter out, BufferedReader in) throws IOException {
        out.close();
        in.close();
    }

    public void closeConnection(BufferedReader inUser, Socket fromServer) throws IOException {
        inUser.close();
        fromServer.close();
    }
}
