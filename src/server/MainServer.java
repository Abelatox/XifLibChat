package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import xiflib.XifLib;

public class MainServer implements Runnable {

	static final int PORT = 9876;

	static XifLib xiflib = new XifLib();

	// Tots els clients
	static ArrayList<Socket> clients = new ArrayList<Socket>();

	// Socket del server
	static ServerSocket svSocket;

	// Socket que representa el client de cada thread
	Socket socket;

	// Constructor per a cada thread del servidor amb un client
	public MainServer(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Thread
	 */
	@Override
	public void run() {
		// Format de la hora
		DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		while (true) {
			try {
				// Reb missatges del socket del client assignat
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// Espera a que rebi un msg
				while (!in.ready()) {
				}
				String msg = in.readLine();
				// Formateja la data dins el missatge
				Date date = new Date();
				msg = xiflib.xifrar("[" + dateFormat.format(date) + "] ") +msg;
				// Mostra el msg i l'envia
				System.out.println(xiflib.xifrar(msg));
				sendAll(msg);

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void sendAll(String msg) {
		// Va per tots els sockets un per un i envia el missatge 'msg'
		for (Socket skt : clients) {
			PrintWriter out;
			try {
				// Agafa el PrintWriter de cada socket
				out = new PrintWriter(skt.getOutputStream(), true);
				// Envia el missatge
				out.println(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Acceptar clients
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Servidor operatiu, esperant clients");
			// Obre un sol cop el port per al ServerSocket
			svSocket = new ServerSocket(PORT);

			while (true) {
				try {
					// Accepta el client
					Socket socket = svSocket.accept();
					// L'afegeix a la llista de clients
					clients.add(socket);
					System.out.println("Added client " + socket.getInetAddress() + ":" + socket.getPort());
					sendAll(xiflib.xifrar(socket.getInetAddress() + ":" + socket.getPort() + " HA ENTRAT AL XAT."));
					// Comença un nou thread per comunicar-se amb el client assignat
					new Thread(new MainServer(socket)).start();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}