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
	static int pptmode, modeDetect,youtubemode;
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
				int[] coordinate = new int[] {};
				// TODO Auto-generated method stub
				int ServerPort = 2025;
				System.out.println("Server: Waiting for connection......");
				try {
					serverSocket = new ServerSocket(ServerPort);
					Robot robot = new Robot();
					while (true) {
						client = serverSocket.accept();
						in = client.getInputStream();
						modeDetect = in.read();
						Thread.sleep(500);
						if (modeDetect == 106) {
							System.out
									.println("Server: PowerPoint Control Mode");
							do {
								pptmode = in.read();
								if (pptmode < 10) {
									robot.keyPress(0x30 + pptmode);
									Thread.sleep(20);
									robot.keyRelease(0x30 + pptmode);
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_ENTER);
									Thread.sleep(20);
									robot.keyRelease(KeyEvent.VK_ENTER);
								}
								if (pptmode >= 10 && pptmode < 20) {
									robot.keyPress(0x31);
									Thread.sleep(20);
									robot.keyRelease(0x31);
									Thread.sleep(20);
									robot.keyPress(0x30 + (pptmode - 10));
									Thread.sleep(20);
									robot.keyRelease(0x30 + (pptmode - 10));
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_ENTER);
									Thread.sleep(20);
									robot.keyRelease(KeyEvent.VK_ENTER);
								}
								if (pptmode >= 20 && pptmode < 30) {
									robot.keyPress(0x32);
									Thread.sleep(20);
									robot.keyRelease(0x32);
									Thread.sleep(20);
									robot.keyPress(0x30 + (pptmode - 20));
									Thread.sleep(20);
									robot.keyRelease(0x30 + (pptmode - 20));
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_ENTER);
									Thread.sleep(20);
									robot.keyRelease(KeyEvent.VK_ENTER);
								}
								if (pptmode >= 30 && pptmode < 40) {
									robot.keyPress(0x33);
									Thread.sleep(20);
									robot.keyRelease(0x33);
									Thread.sleep(20);
									robot.keyPress(0x30 + (pptmode - 30));
									Thread.sleep(20);
									robot.keyRelease(0x30 + (pptmode - 30));
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_ENTER);
									Thread.sleep(20);
									robot.keyRelease(KeyEvent.VK_ENTER);
								}
								if (pptmode >= 40 && pptmode < 50) {
									robot.keyPress(0x34);
									Thread.sleep(20);
									robot.keyRelease(0x34);
									Thread.sleep(20);
									robot.keyPress(0x30 + (pptmode - 40));
									Thread.sleep(20);
									robot.keyRelease(0x30 + (pptmode - 40));
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_ENTER);
									Thread.sleep(20);
									robot.keyRelease(KeyEvent.VK_ENTER);
								}
								switch (pptmode) {
								case 100:
									robot.keyPress(KeyEvent.VK_SHIFT);
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_F5);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_F5);
									robot.keyRelease(KeyEvent.VK_SHIFT);
									Thread.sleep(10);
									break;

								case 101:
									robot.keyPress(KeyEvent.VK_LEFT);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_LEFT);
									Thread.sleep(10);
									break;

								case 102:
									robot.keyPress(KeyEvent.VK_RIGHT);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_RIGHT);
									Thread.sleep(10);
									break;

								case 103:
									robot.keyPress(KeyEvent.VK_ESCAPE);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_ESCAPE);
									Thread.sleep(10);
									break;

								case 104:
									robot.keyPress(KeyEvent.VK_W);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_W);
									Thread.sleep(10);
									break;

								case 105:
									robot.keyPress(KeyEvent.VK_CONTROL);
									Thread.sleep(20);
									robot.keyPress(KeyEvent.VK_P);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_CONTROL);
									robot.keyRelease(KeyEvent.VK_P);
									Thread.sleep(10);
									break;

								case 107:
									System.out
											.println("Server: PowerPoint Control Mode Cancelled");
									break;

								default:
									break;

								}
							} while (pptmode != 107);
						}
						if (modeDetect == 7) {
							System.out.println("Server: MouseControl Mode");
							ois = new ObjectInputStream(client.getInputStream());
							while (true) {
								coordinate = (int[]) ois.readObject();
								if (coordinate[0] != 2000
										&& coordinate[0] != 2001 && coordinate[0] != 2002) {
									mouse = MouseInfo.getPointerInfo()
											.getLocation();
									robot.mouseMove(mouse.x - coordinate[0],
											mouse.y - coordinate[1]);
								} else {
									if (coordinate[0] == 2001) {
										robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
										Thread.sleep(20);
										robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
										Thread.sleep(300);
									} else if (coordinate[0] == 2002) {
										robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
										Thread.sleep(20);
										robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
										Thread.sleep(300);
									} else {
										System.out
												.println("Server: MouseControl Mode Cancelled");
										break;
									}
								}
								coordinate[0] = 0;
								coordinate[1] = 0;
							}
							;
						}
						if (modeDetect == 9) {
							System.out.println("Server: YoutubeControl Mode");
							do {
								youtubemode = in.read();
								switch (youtubemode) {
								case 0:
									robot.keyPress(KeyEvent.VK_0);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_0);
									Thread.sleep(10);
									break;
								case 1:
									robot.keyPress(KeyEvent.VK_1);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_1);
									Thread.sleep(10);
									break;
								case 2:
									robot.keyPress(KeyEvent.VK_2);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_2);
									Thread.sleep(10);
									break;
								case 3:
									robot.keyPress(KeyEvent.VK_3);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_3);
									Thread.sleep(10);
									break;
								case 4:
									robot.keyPress(KeyEvent.VK_4);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_4);
									Thread.sleep(10);
									break;
								case 5:
									robot.keyPress(KeyEvent.VK_5);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_5);
									Thread.sleep(10);
									break;
								case 6:
									robot.keyPress(KeyEvent.VK_6);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_6);
									Thread.sleep(10);
									break;
								case 7:
									robot.keyPress(KeyEvent.VK_7);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_7);
									Thread.sleep(10);
									break;
								case 8:
									robot.keyPress(KeyEvent.VK_8);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_8);
									Thread.sleep(10);
									break;
								case 9:
									robot.keyPress(KeyEvent.VK_9);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_9);
									Thread.sleep(10);
									break;
								case 10:
									robot.keyPress(KeyEvent.VK_SPACE);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_SPACE);
									Thread.sleep(10);
									break;
								case 11:
									robot.keyPress(KeyEvent.VK_LEFT);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_LEFT);
									Thread.sleep(10);
									break;
								case 12:
									robot.keyPress(KeyEvent.VK_RIGHT);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_RIGHT);
									Thread.sleep(10);
									break;
								case 13:
									robot.keyPress(KeyEvent.VK_UP);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_UP);
									Thread.sleep(10);
									break;
								case 14:
									robot.keyPress(KeyEvent.VK_DOWN);
									Thread.sleep(10);
									robot.keyRelease(KeyEvent.VK_DOWN);
									Thread.sleep(10);
									break;
								case 15:
									System.out
											.println("Server: YoutubControl Mode Cancelled");
									break;
								default:
									break;
								}
							} while (youtubemode != 15);
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
