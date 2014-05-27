package RCServer;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;

public class RCServer {
	static int i, j;
	static ServerSocket serverSocket;
	static InputStream in;
	static ObjectInputStream ois;
	static Socket client;
	static int[] coordinate;
//	static Point mouse;

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, AWTException {
		Thread Server = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				int ServerPort = 1025;
				System.out.println("Server: Waiting for connection......");
				try {
					serverSocket = new ServerSocket(ServerPort);
					Robot robot = new Robot();
					while (true) {
						client = serverSocket.accept();
						in = client.getInputStream();
						j = in.read();
						Thread.sleep(500);
						if (j == 6) {
							System.out
									.println("Server: PowerPointControl Mode");
							do {
								i = in.read();
								if (i == 5) {
									client.close();
									serverSocket.close();
									System.out
											.println("Server: Client connected");
									break;
								}
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
						}
						if (j == 7) {
							System.out.println("Server: MouseControl Mode");
							ois = new ObjectInputStream(client.getInputStream());
							do {
//								mouse = MouseInfo.getPointerInfo()
//										.getLocation();
								coordinate = (int[]) ois.readObject();
								robot.mouseMove(4*coordinate[0],
										(int) (1.35* coordinate[1]));
							} while (j != 4);
						}
					}
				} catch (Exception e) {
					System.out.println("Server: Error");
					e.printStackTrace();
				}
			}
		});
		Server.start();
	}

}
