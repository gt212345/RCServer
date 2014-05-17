package RCServer;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RCServer {
	static int i;
	static ServerSocket serverSocket;
	static InputStream in;
	static Socket client;
	private static int key;

	// private static ObjectInputStream fromClient;
	// private static ObjectOutputStream fromServer;

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, AWTException {
		Thread Server = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int ServerPort = 1025;
				System.out.println("Server: Waiting for connection......");
				while (true) {
					try {
						serverSocket = new ServerSocket(ServerPort);
						Robot robot = new Robot();
						while (true) {
							client = serverSocket.accept();
							// fromClient = new
							// ObjectInputStream(client.getInputStream());
							// fromServer = new
							// ObjectOutputStream(client.getOutputStream());
							in = client.getInputStream();
							do {
								i = in.read();
								if (i == 5) {
									client.close();
									serverSocket.close();
									System.out
											.println("Server: Client connected");
									break;
								}
								// ChoicesServer choice =
								// (ChoicesServer)fromClient.readObject();
								// System.out.println("the flag is " +
								// choice.getKey());
								// key = choice.getKey();
								switch (i) {
								case 0:
									robot.keyPress(KeyEvent.VK_SHIFT);
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_F5);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_F5);
									robot.keyRelease(KeyEvent.VK_SHIFT);
									Thread.sleep(10);
									break;

								case 1:
									robot.keyPress(KeyEvent.VK_LEFT);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_LEFT);
									Thread.sleep(10);
									break;

								case 2:
									robot.keyPress(KeyEvent.VK_RIGHT);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_RIGHT);
									Thread.sleep(10);
									break;

								case 3:
									robot.keyPress(KeyEvent.VK_ESCAPE);
									Thread.sleep(10);
									robot.keyPress(KeyEvent.VK_ESCAPE);
									Thread.sleep(10);
									break;
								case 4:
									System.out
											.println("Server: Client disconnected, waiting for another......");
									in.close();
									client.close();
									serverSocket.close();
									break;
								default:
									break;
								}
							} while (i != 4);
							break;
						}
					} catch (Exception e) {
						System.out.println("Server: Error");
						e.printStackTrace();
					}
				}
			}
		});
		Server.start();
	}
}
