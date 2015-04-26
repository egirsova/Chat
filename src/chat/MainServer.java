package chat;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainServer {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            ExecutorService executor = Executors.newCachedThreadPool();

            Transcript theTranscript = new Transcript();

            while (true) {
                // Listen for a new connection request
                Socket socket = serverSocket.accept();

                HandleAClient task = new HandleAClient(socket, theTranscript);
                executor.execute(task);
            }
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }
}

class HandleAClient implements Runnable {

    private Transcript trans;
    private Socket socket;
    boolean active = true;
    DataInputStream in;
    DataOutputStream out;

    public HandleAClient(Socket s, Transcript t) {
        socket = s;
        trans = t;
    }

    public void run() {
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Connected Successfully");

            while (active) {
                int request = in.read();
                switch (request) {
                    case 1:
                        saveComment();
                        break;
                    case 2:
                        sendComments();
                        break;
                    case 3:
                        sendCommentCount();
                        break;
                }
            }
        } catch (IOException ex) {
            System.out.println("MainServer.Error: Cannot run");
        }
    }

    private void saveComment() {
        try {
            String comment = in.readUTF();
            trans.addComment(comment+"\n");
            int count = trans.getCommentCount();
        } catch (IOException ex) {
            System.out.println("MainServer.Error: Cannot save comment");
        }
    }

    private void sendComments() {
        try {
            int n = in.read();
            out.writeUTF(trans.getComments(n));
        } catch (IOException ex) {
            System.out.println("MainServer.Error: Cannot send comments");
        }
    }

    private void sendCommentCount() {
        try {
            int count = trans.getCommentCount();
            out.writeInt(count);
        } catch (IOException ex) {
            System.out.println("MainServer.Error: Cannot send comment count");
        }
    }
}
