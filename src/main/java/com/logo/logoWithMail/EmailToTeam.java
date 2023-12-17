package com.logo.logoWithMail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.beans.factory.annotation.Autowired;


//An application to send the mail if needed
@SpringBootApplication
public class EmailToTeam {
    @Autowired
    private EmailSenderService senderService;
    private String teamEmail = "pelegswisa6@gmail.com";

    public static void main(String[] args) {
        SpringApplication.run(EmailToTeam.class, args);
        
    }
    @EventListener(ApplicationReadyEvent.class)
    public void sendMail() {
    	if(MainServer.mainServerState) {
    		senderService.sendEmail(teamEmail, "Starting the server", "The server is running correctly");    		
    	}
    	else { 
    		senderService.sendEmail(teamEmail, "Critical problem in the main server", "The main server stopped! The secondary server will try to restart it, please check what is happening");    		
    	}
    }
    
    
}