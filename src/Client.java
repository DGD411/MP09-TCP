import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);
            System.out.println("Servidor iniciat. Esperant clients...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connectat.");

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clientHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {

                ObjectInputStream inFromClient = new ObjectInputStream(clientSocket.getInputStream());
                ObjectOutputStream outToClient = new ObjectOutputStream(clientSocket.getOutputStream());

                Llista receivedList = (Llista) inFromClient.readObject();
                System.out.println("Llista rebuda del client: " + receivedList.getNom() + " - " + receivedList.getNumberList());

                Set<Integer> uniqueNumbers = new TreeSet<>(receivedList.getNumberList());
                List<Integer> sortedList = new ArrayList<>(uniqueNumbers);

                Llista modifiedList = new Llista(receivedList.getNom(), sortedList);
                outToClient.writeObject(modifiedList);
                System.out.println("Llista modificada enviada al client: " + modifiedList.getNom() + " - " + modifiedList.getNumberList());


                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
