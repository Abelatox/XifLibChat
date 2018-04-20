package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import xiflib.XifLib;

public class RebreMissatge extends Thread{

	private Socket sktClient;
	private static BufferedReader in;
	private XifLib xifLib = new XifLib();
	
	public RebreMissatge( Socket sktClient ) {
		
		this.sktClient = sktClient;
		try {
			in =  new BufferedReader( new InputStreamReader(sktClient.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			
			while ( !in.ready() ) {}
			
			String sMissatgeXifrat;
			sMissatgeXifrat =  in.readLine();
			
			String sMissatgeDesxifrat;
			sMissatgeDesxifrat = xifLib.xifrar( sMissatgeXifrat );
			
			System.out.println( sMissatgeXifrat );
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
	}

}
