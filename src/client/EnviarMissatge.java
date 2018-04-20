package client;

import java.net.Socket;

public class EnviarMissatge extends Thread {

	private Socket sktClient;
	
	public EnviarMissatge( Socket sktClient ) {
		this.sktClient = sktClient;
	}
	
	@Override
	public void run() {
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
