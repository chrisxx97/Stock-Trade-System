package camelinaction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class Client extends Server{
	public int clientID;
	public State currentState;
	public ArrayList<String> subscriptions;
	
	// constructor 
	public Client(int id, State st) {
		this.clientID = id;
		this.currentState = st;
		this.subscriptions = new ArrayList<String>();
	}
	
	public void setSubscriptions(String ...arg) {
		for (String subString : arg) {
			subscriptions.add(subString);
		}
	}
	
	
	public void setCamel() throws Exception{
		// create CamelContext
        context = new DefaultCamelContext();
        
        // set properties
        PropertiesComponent prop = context.getComponent("properties", PropertiesComponent.class);
        prop.setLocation("test.properties");
        
        // connect to ActiveMQ JMS broker listening on localhost on port 61616
        ConnectionFactory connectionFactory = 
        	new ActiveMQConnectionFactory("tcp://localhost:61616");
        context.addComponent("jms",
            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() throws Exception {
            	// subscribe to multiple agents
            	for (String sub : subscriptions) {
            		from("jms:topic:"+sub)
            		.process(new Processor() {
	                	public void process(Exchange exchange) throws Exception {
	                		String messageString  = exchange.getIn().getBody(String.class);
	                		List<String> suggestion = Arrays.asList(messageString.split(","));
	                		boolean decision = currentState.processAdvice(suggestion.get(1));
	                		if (decision == true) {
	                			messageString += ",y";
	                		}
	                		else {
	                			messageString += ",n";
	                		}
//	                		System.out.println();
//	                		System.out.println("Here");
//	                		System.out.println();
	                		messageString += ","+ String.valueOf(clientID);
	                		exchange.getIn().setBody(messageString);
	                		
	                	}
            		})
            		.to("jms:queue:Trade_Engine");
            	}

               
            }
        });
	}
	
	
	public void startCamel(int ms) throws Exception{
		// start the route and let it do its work
        context.start();
        Thread.sleep(ms);

        // stop the CamelContext
        context.stop();
	}
}
