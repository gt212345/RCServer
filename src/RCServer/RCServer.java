package RCServer;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RCServer {
	static int i, j;
	static ServerSocket serverSocket;
	static InputStream in;
	static ObjectInputStream ois;
	static Socket client;
	static Point mouse;

	// static Point mouse;

	public static void main(String[] args) throws IOException,
			ClassNotFoundException, AWTException {
		Thread Server = new Thread(new Runnable() {

			@Override
			public void run() {
				int[] coordinate = new int[]{};
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
									.println("Server: PowerPoint Control Mode");
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
											.println("Server: PowerPoint Control Mode Cancelled");
									break;
								default:
									break;
								}
							} while (i != 4);
						}
						if (j == 7) {
							System.out.println("Server: MouseControl Mode");
							ois = new ObjectInputStream(client.getInputStream());
							while (true) {
								coordinate = (int[]) ois.readObject();
								if (coordinate[0] != 2000) {
									 mouse = MouseInfo.getPointerInfo()
											 .getLocation();
//									robot.mouseMove(4 * coordinate[0],
//											(int) (1.35 * coordinate[1]));
									robot.mouseMove(mouse.x-coordinate[0], mouse.y-coordinate[1]);
									if(coordinate[0] == 1){
//										robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
//										robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
									}
								} else {
									System.out
											.println("Server: MouseControl Mode Cancelled");
									break;
								}
								coordinate[0] = 0;
								coordinate[1] = 0;
							};
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
