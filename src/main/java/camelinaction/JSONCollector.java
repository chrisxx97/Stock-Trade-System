package camelinaction;

import java.util.Arrays;
import java.util.LinkedHashMap;

import javax.jms.ConnectionFactory;
import javax.xml.bind.JAXBContext;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.ListJacksonDataFormat;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.impl.DefaultCamelContext;
import org.json.JSONObject;


public class JSONCollector extends Server{

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
            public void configure() throws Exception{
            	
            	// XML Data Format
        		JaxbDataFormat xmlDataFormat = new JaxbDataFormat();
        		JAXBContext con = JAXBContext.newInstance(StockObject.class);
        		xmlDataFormat.setContext(con);

        		// JSON Data Format
        		ListJacksonDataFormat jsonDataFormat = new ListJacksonDataFormat(StockObject.class);
        		
                from("file:data/inbox/json?noop=true")
                .log("RETRIEVED: ${file:name}")
                // unmarshal from json to java object and then marhsal to xml
                .unmarshal(jsonDataFormat)
                .split(body())
                .marshal(xmlDataFormat)
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

