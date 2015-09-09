import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * Created by heiruwu on 9/9/15.
 */
public class Server {
    private static final int SERVER_PORT = 2025;
    private static final int END = 10060;
    private static int flag = 0;


    public static void main(String[] args) {
        Thread server = new Thread(() -> {
            System.out.println("Server Thread Start");
            try {
                int[] coordinate = new int[]{};
                ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
                Robot robot = new Robot();
                Point mouse;
                while (flag == 0) {
                    System.out.println("Port: " + SERVER_PORT + " Waiting for Connection");
                    Socket client = serverSocket.accept();
                    if (client.isConnected()) {
                        System.out.println("Client connected");
                        ObjectInputStream inputStream = new ObjectInputStream(client.getInputStream());
                        while (true) {
                            coordinate = (int[]) inputStream.readObject();
                            if (coordinate[0] == END) {
                                System.out.println("Connection Lost");
                                break;
                            }
                            mouse = MouseInfo.getPointerInfo().getLocation();
                            robot.mouseMove(mouse.x - coordinate[0], mouse.y - coordinate[1]);
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException: " + e.toString());
            } catch (AWTException e) {
                System.out.println("AWTException: " + e.toString());
            } catch (ClassNotFoundException e) {
                System.out.println("ClassNotFountException: " + e.toString());
            }
        });
        server.start();
    }
}
