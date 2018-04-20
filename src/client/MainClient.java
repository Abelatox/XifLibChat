package client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import xiflib.XifLib;

public class MainClient extends Thread {

	private Scanner sc = new Scanner(System.in);
	private XifLib xifLib = new XifLib();

	@Override
	public void run() {

		String sMissatge;
		sMissatge = sc.nextLine();

		String sMissatgeXifrat;
		sMissatgeXifrat = xifLib.xifrar(sMissatge);

		out.println(sMissatgeXifrat);
		out.flush();

	}

	private static final int _PORT = 9876;
	private static final String _IP = "192.168.19.99";
	private static Socket sktClient;
	private static PrintWriter out;

	public static void main(String args[]) {

		try {
			sktClient = new Socket(_IP, _PORT);
			out = new PrintWriter(sktClient.getOutputStream(), true);
			while (true) {

				((RebreMissatge) new RebreMissatge(sktClient)).start();
				((MainClient) new MainClient()).start();

			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}

}
