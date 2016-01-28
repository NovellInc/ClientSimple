package MainCommands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Omelchuk.Roman on 22.01.2016.
 */
class ConnectionControl {

    public void closeSession(PrintWriter printWriter, BufferedReader bufferedReader) throws IOException {
        printWriter.close();
        bufferedReader.close();
    }

    public void closeSession(BufferedReader bufferedReader) throws IOException {
        bufferedReader.close();
    }

    public void closeConnection(BufferedReader inUser, Socket Server) throws IOException {
        inUser.close();
        Server.close();
    }

    public Socket checkConnection(Socket server, String HOST, int PORT) throws IOException {
        if(server.isClosed() && !server.isConnected()) {
            server = new Socket(HOST, PORT);
        }
        return server;
    }
}
