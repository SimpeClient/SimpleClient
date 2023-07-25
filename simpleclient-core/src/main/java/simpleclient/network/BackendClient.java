package simpleclient.network;

import simpleclient.SimpleClient;

import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

public class BackendClient {
    private Socket socket;
    private Thread thread;
    private Cipher cipher;
    private PublicKey publicKey;
    private BufferedReader reader;

    public boolean connect(String accessToken) {
        try {
            socket = new Socket(SimpleBackend.URL, SimpleBackend.PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            thread = new Thread(() -> run(accessToken));
            thread.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isConnected() {
        try {
            return socket != null && !socket.isClosed() && !socket.isInputShutdown() && socket.isOutputShutdown();
        } catch (Exception e) {return false;}
    }

    public void disconnect() {
        try {
            thread.stop();
            thread = null;
            socket.close();
            socket = null;
        } catch (Exception e) {}
        SimpleClient.LOGGER.info("SimpleClient disconnected from SimpleBackend");
    }

    private void run(String accessToken) {
        try {
            if (!isConnected()) {disconnect(); return;}
            socket.getOutputStream().write((SimpleClient.VERSION + "\n").getBytes());
            socket.getOutputStream().flush();
            KeyFactory factory = KeyFactory.getInstance("RSA");
            publicKey = factory.generatePublic(new X509EncodedKeySpec(readLine().getBytes()));
            cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            if (!isConnected()) {disconnect(); return;}
            send(accessToken);
            String command;
            if (!readLine().equals("authenticated")) disconnect();
            else while (isConnected() && (command = reader.readLine()) != null) {
                command = new String(cipher.doFinal(command.getBytes()));
                SimpleBackend.handleBackCommand(command);
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    public String readLine() {
        try {
            return reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void send(String message) {
        send(message.toString());
    }

    public void send(byte[] message) {
        try {
            socket.getOutputStream().write(cipher.doFinal(message));
            socket.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}