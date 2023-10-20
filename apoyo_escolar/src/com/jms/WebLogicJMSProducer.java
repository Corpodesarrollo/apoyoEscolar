package com.jms;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class WebLogicJMSProducer {

	//It created initial context to access WebLogic Naming service
	public final static String JNDI_FACTORY="weblogic.jndi.WLInitialContextFactory";
	//JNDI name of WebLogic connection factory 
	public static String CONNECTION_FACTORY = "jdbc/JMSConnectionFactoryApoyo";
	//JNDI name of WebLogic QUEUE 
	public static String QUEUE = "jdbc/JMSQueueApoyo";
	
	 private static InitialContext getInitialContext(String url)
		     throws NamingException
		   {
		   Hashtable<String, String> env = new Hashtable();
		   env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		   env.put(Context.PROVIDER_URL, url);
		   return new InitialContext(env);
		   }
	
	public static void main(String[] args) {
		
//		The hostname and port of WebLogic server where JMS Server is configured
		int port = 7001;
		String host="localhost";
		
		try{
	     //Initial Context		
		 InitialContext namingContext = getInitialContext("t3://" + host + ":" + port);
		 
		 //Queue
		 Queue queue = (Queue) namingContext.lookup(QUEUE); 
		
		//Connection Factory 
		 QueueConnectionFactory queueConnectionFactory = 
				 (QueueConnectionFactory) namingContext.lookup(CONNECTION_FACTORY);

		QueueConnection  conn = queueConnectionFactory.createQueueConnection();
		
		QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		
		TextMessage message =  session.createTextMessage("How are you?");
		conn.start();
		
		session.createSender(queue).send(message);
		conn.close();
		
		
		}
		catch(NamingException e){
			e.printStackTrace();
		}
		catch(JMSException e){
			e.printStackTrace();
		}
		
	}
	
}