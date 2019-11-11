package sender;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.QueueConnectionFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MySender {

	public static void main(String[] args) {
		
		try{
			
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			
			Queue queue = (Queue) applicationContext.getBean("queue");
			
			// Create a connection. See https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html	
			 Connection connection = factory.createConnection();
			
			 // Open a session
			 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			 // Start the connection
			 connection.start();
			
			 //Create a receiver
			 Queue queue1 = session.createQueue("test-queue");
			 
			 // Create a sender	
			 MessageProducer producer = session.createProducer(queue1);
			
			
			
			 // Create a message
			 for (int i = 0; i < 3; i++) {
		            TextMessage textMessage = session.createTextMessage("No."+i+ "ActiveMQ queue mes");
			// Send the message
		            producer.send(textMessage);
		        }
			 
		    // Close the session			
			// Close the connection
			 producer.close();
		     session.close();
		     connection.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
