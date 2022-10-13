package camelinaction;


import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;

// Canonical Data Model
public class CDM extends Server{
	// singleton design pattern
	private static CDM cdm;
	
	private CDM() {}
	
	public static synchronized CDM getInstance() {
		if (cdm == null) {
			cdm = new CDM();
			
		}
		return cdm;
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

        		
                from("jms:queue:Canonical_Data_Model")
                .log("RETRIEVED: ${file:name}")
                // message filter
                // ignore invalid stocks that have no ticker
                .filter().xpath("/record[ticker!='']")
                .choice()
                // content based router
                // route stocks based on their sectors
                .when().xpath("/record/sector='Finance'").to("jms:queue:Agent_Finance")
                .when().xpath("/record/sector='Technology'").to("jms:queue:Agent_Technology")
                .when().xpath("/record/sector='Consumer Services'").to("jms:queue:Agent_ConsumerServices")
                .when().xpath("/record/sector='Energy'").to("jms:queue:Agent_Energy")
                .otherwise().
                // others are sent to the invalid messages queue
                to("jms:queue:Invalid_messages");
               
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
