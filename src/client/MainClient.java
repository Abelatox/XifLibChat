package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import xiflib.XifLib;

public class MainClient implements Runnable{
	Socket socket;
	static String nick;
	
	static String IP = "192.168.19.106";
	//static String IP = "localhost";
	static int PORT = 9876;

	static XifLib xiflib = new XifLib();
	
	public MainClient(Socket server) {
		this.socket = server;
	}

	@Override
	public void run() {
		System.out.println("Connexió establerta, xat obert");
		PrintWriter out;
		try {
			while(true) {
				//Sempre pot escriure 
				String msg = new Scanner(System.in).nextLine();//"This is the first message for TCP/IP comm.";
				//Afegeix el nick al missatge
				msg = "<"+nick+"> "+msg;
				//Envia el missatge al servidor
				out = new PrintWriter(socket.getOutputStream(), true);
				out.println(xiflib.xifrar(msg));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.print("Nick: ");
		nick = new Scanner(System.in).nextLine();
		
		try {
			//Estableix connexió amb el servidor
			Socket server = new Socket(IP,PORT);
			//Agafa el BufferedReader dels missatges provinents del servidor
	        BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
	        //Comença el thread per enviar missatges al servidor
	        new Thread(new MainClient(server)).start();
	        //Sempre està esperant missatges del servidor
	        while(true) {
	        	//Fins que no hi hagi un missatge del servidor espera
	        	while(!in.ready()) {}
	        	String msg = in.readLine();
	        	if(!msg.contains("<"+nick+"> ")) {
	        		//Imprimeix el missatge
	        		System.out.println(xiflib.xifrar(msg));
	        	}
	        }
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}

