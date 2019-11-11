package receiver;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyReceiver {

	public static void main(String[] args) {
		try{
			
			ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContextJMS.xml");
			QueueConnectionFactory factory = (QueueConnectionFactory) applicationContext.getBean("connectionFactory");
			
			Queue queue = (Queue) applicationContext.getBean("queue");
			
			// Create a connection. See https://docs.oracle.com/javaee/7/api/javax/jms/package-summary.html					
			 Connection connection = factory.createConnection();
			// Open a session	
			 connection.start();
			// start the connection	
			 Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			 Queue queue1 = session.createQueue("test-queue");
			 
			 // Create a receive	
			 MessageConsumer consumer = session.createConsumer(queue1);
		       
			// Receive the message
			 consumer.setMessageListener(new MessageListener() {

		            public void onMessage(Message message) {
		             
		                TextMessage textMessage = (TextMessage) message;
		                String text;
		                try {
		                    text = textMessage.getText();
		                    System.out.println("this is the receive mes：" + text);
		                } catch (JMSException e) {
		                    e.printStackTrace();
		                }

		            }
		        }
			 );
			// close
		        consumer.close();
		        session.close();
		        connection.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
