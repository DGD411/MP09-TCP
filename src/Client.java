import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    public static void main(String[] args) {
        try {
            Socket clientSocket = new Socket("localhost", 12345);

            ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

            List<Integer> numberList = Arrays.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3);
            Llista llista = new Llista("Exemple", numberList);

            outToServer.writeObject(llista);
            System.out.println("Llista enviada al servidor: " + llista.getNom() + " - " + llista.getNumberList());

            Llista modifiedList = (Llista) inFromServer.readObject();
            System.out.println("Llista rebuda del servidor: " + modifiedList.getNom() + " - " + modifiedList.getNumberList());

            clientSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
