package camelinaction;

import java.util.Arrays;

import javax.jms.ConnectionFactory;
import javax.xml.bind.JAXBContext;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;


public class XMLCollector extends Server{

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
            	// XML Data Format
            	
        		JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        		
        		JAXBContext con = JAXBContext.newInstance(StockObject.class);
        		xmlDataFormat.setContext(con);
        		
        		
                from("file:data/inbox/xml?noop=true")
                .log("RETRIEVED: ${file:name}")
                .unmarshal(xmlDataFormat)
                .split(body())
                .to("jms:queue:Canonical_Data_Model");
               
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


