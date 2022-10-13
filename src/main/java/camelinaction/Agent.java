package camelinaction;

import java.io.StringReader;

//import org.dom4j.*;
import javax.jms.ConnectionFactory;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

// agent is the context in strategy design pattern
public class Agent extends Server{
	public Advise advise;
	public enum Specialty{
		Finance,
		Technology,
		ConsumerServices,
		Energy;
	}
	public Specialty specialty;
	
	// constructor
	public Agent(Advise advise, Specialty specialty) {
		this.advise = advise;
		this.specialty = specialty;
	};
	
	public void setCamel() throws Exception {
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

                switch (specialty) {
				case Finance:
					from("jms:queue:Agent_Finance")
					.process(new Processor() {
	                	public void process(Exchange exchange) throws Exception {
	                		// imported JDOM2 and use SAXBuilder to parse XML
	                		SAXBuilder saxBuilder = new SAXBuilder();
	                		Document document = saxBuilder.build(new StringReader(exchange.getIn().getBody(String.class)));
	                		Element rootElement = document.getRootElement();
	                		
	                		String ticker= rootElement.getChildText("ticker");
	                		double bidPrice = Double.valueOf(rootElement.getChildText("bidPrice"));
	                		int bidQuantity = Integer.valueOf(rootElement.getChildText("bidQuantity"));
	                		double askPrice = Double.valueOf(rootElement.getChildText("askPrice"));
	                		int askQuantity = Integer.valueOf(rootElement.getChildText("askQuantity"));
	                		
	                		boolean suggestion = advise.advise(bidPrice, bidQuantity, askPrice, askQuantity);
	                		
	                		StringBuilder result = new StringBuilder();
		                   // add agent's advice to the message
	                       result.append(ticker+',');
	                       if (suggestion == true) {
	                    	   result.append("buy,");
	                       }else {
	                    	   result.append("sell,");
	                       }
	                       result.append(String.valueOf(askPrice)); 
	                       
	                       exchange.getIn().setBody(result.toString());
	                       
	                	}})
					.to("jms:topic:Finance");
					break;
					
				case Technology:
					from("jms:queue:Agent_Technology")
					.process(new Processor() {
	                	public void process(Exchange exchange) throws Exception {
	                		// imported JDOM2 and use SAXBuilder to parse XML
	                		SAXBuilder saxBuilder = new SAXBuilder();
	                		Document document = saxBuilder.build(new StringReader(exchange.getIn().getBody(String.class)));
	                		Element rootElement = document.getRootElement();
	                		
	                		String ticker= rootElement.getChildText("ticker");
	                		double bidPrice = Double.valueOf(rootElement.getChildText("bidPrice"));
	                		int bidQuantity = Integer.valueOf(rootElement.getChildText("bidQuantity"));
	                		double askPrice = Double.valueOf(rootElement.getChildText("askPrice"));
	                		int askQuantity = Integer.valueOf(rootElement.getChildText("askQuantity"));
	                		
	                		boolean suggestion = advise.advise(bidPrice, bidQuantity, askPrice, askQuantity);
	                		
	                		StringBuilder result = new StringBuilder();
		                       
	                       result.append(ticker+',');
	                       if (suggestion == true) {
	                    	   result.append("buy,");
	                       }else {
	                    	   result.append("sell,");
	                       }
	                       result.append(String.valueOf(askPrice)); 
	                       
	                       exchange.getIn().setBody(result.toString());
	                       
	                	}})
					.to("jms:topic:Technology");
					break;
					
				case ConsumerServices:
					from("jms:queue:Agent_ConsumerServices")
					.process(new Processor() {
	                	public void process(Exchange exchange) throws Exception {
	                		// imported JDOM2 and use SAXBuilder to parse XML
	                		SAXBuilder saxBuilder = new SAXBuilder();
	                		Document document = saxBuilder.build(new StringReader(exchange.getIn().getBody(String.class)));
	                		Element rootElement = document.getRootElement();
	                		
	                		String ticker= rootElement.getChildText("ticker");
	                		double bidPrice = Double.valueOf(rootElement.getChildText("bidPrice"));
	                		int bidQuantity = Integer.valueOf(rootElement.getChildText("bidQuantity"));
	                		double askPrice = Double.valueOf(rootElement.getChildText("askPrice"));
	                		int askQuantity = Integer.valueOf(rootElement.getChildText("askQuantity"));
	                		
	                		boolean suggestion = advise.advise(bidPrice, bidQuantity, askPrice, askQuantity);
	                		
	                		StringBuilder result = new StringBuilder();
		                       
	                       result.append(ticker+',');
	                       if (suggestion == true) {
	                    	   result.append("buy,");
	                       }else {
	                    	   result.append("sell,");
	                       }
	                       result.append(String.valueOf(askPrice)); 
	                       
	                       exchange.getIn().setBody(result.toString());
	                       
	                	}})
					.to("jms:topic:ConsumerServices");
					break;
					
				case Energy:
					from("jms:queue:Agent_Energy")
					.process(new Processor() {
	                	public void process(Exchange exchange) throws Exception {
	                		// imported JDOM2 and use SAXBuilder to parse XML
	                		SAXBuilder saxBuilder = new SAXBuilder();
	                		Document document = saxBuilder.build(new StringReader(exchange.getIn().getBody(String.class)));
	                		Element rootElement = document.getRootElement();
	                		
	                		String ticker= rootElement.getChildText("ticker");
	                		double bidPrice = Double.valueOf(rootElement.getChildText("bidPrice"));
	                		int bidQuantity = Integer.valueOf(rootElement.getChildText("bidQuantity"));
	                		double askPrice = Double.valueOf(rootElement.getChildText("askPrice"));
	                		int askQuantity = Integer.valueOf(rootElement.getChildText("askQuantity"));
	                		
	                		boolean suggestion = advise.advise(bidPrice, bidQuantity, askPrice, askQuantity);
	                		
	                       StringBuilder result = new StringBuilder();
	                       
	                       result.append(ticker+',');
	                       if (suggestion == true) {
	                    	   result.append("buy,");
	                       }else {
	                    	   result.append("sell,");
	                       }
	                       result.append(String.valueOf(askPrice)); 
	                       
	                       exchange.getIn().setBody(result.toString());
	                       
	                	}})
					.to("jms:topic:Energy");
					break;
				default:
					break;

				
				}
               
            }
        });
	};
	
	public void startCamel(int ms) throws Exception {
		// start the route and let it do its work
        context.start();
        Thread.sleep(ms);

        // stop the CamelContext
        context.stop();
	};
	
	
	

}
