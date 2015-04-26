/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.awt.Toolkit;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Liza Girsova
 */
public class ChatGateway {

    DataInputStream in;
    DataOutputStream out;

    public synchronized void startChat(String handle, String server) {
        try {
            Socket socket = new Socket(server, 8000);
            in = new DataInputStream(
                    socket.getInputStream());
            out = new DataOutputStream(
                    socket.getOutputStream());
        } catch (IOException ex) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Error: Cannot connect to server");
        }
    }

    public synchronized void sendComment(String commentWithHandle) {
        try {
            out.write(1);
            out.writeUTF(commentWithHandle);
            out.flush();
        } catch (IOException ex) {
            System.out.println("ChatGateway.Error: Cannot send comment");
        }
    }

    public synchronized int getCommentCount() {
        int count = 0;
        try {
            out.write(3);
            count = in.readInt();
        } catch (IOException ex) {
            System.out.println("ChatGateway.Error: Cannot get comment count");
        }
        return count;
    }

    public synchronized String getComment(int n) {
        String comment = null;
        try {
            out.write(2);
            out.write(n);
            comment = in.readUTF();
        } catch (IOException ex) {
            System.out.println("ChatGateway.Error: Cannot get comment");
        }
        return comment;
    }
}
