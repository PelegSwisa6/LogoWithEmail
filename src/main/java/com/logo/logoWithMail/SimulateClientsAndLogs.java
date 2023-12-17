package com.logo.logoWithMail;

public class SimulateClientsAndLogs {
	public static void main(String[] args) {
		int numOfClients = 10; //Choose how many different clients will send information

		for(int i = 0; i < numOfClients; i++) {
			MultithreadThing client = new MultithreadThing(numOfClients);
			client.start();	
		}
	}


}


