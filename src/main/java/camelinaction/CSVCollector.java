package camelinaction;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.dataformat.XStreamDataFormat;

public class CSVCollector extends Server{

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
            public void configure() {
                from("file:data/inbox/csv?noop=true")
                .log("RETRIEVED: ${file:name}")
//                .log("{{filename}}")
                .unmarshal().csv()
                .split(body())
                .process(new Processor() {
                	public void process(Exchange exchange) throws Exception {
                		
                       String [] stock = exchange.getIn().getBody(String.class).split(",");
                       
                       StringBuffer sb = new StringBuffer();
                       sb.append("<record>");
                       sb.append("<ticker>" + stock[0].substring(1) + "</ticker>");
                       sb.append("<sector>" + stock[1].trim() + "</sector>");
                       sb.append("<bidPrice>" + stock[2].trim() + "</bidPrice>");
                       sb.append("<bidQuantity>" + stock[3].trim() + "</bidQuantity>");
                       sb.append("<askPrice>" + stock[4].trim() + "</askPrice>");
                       sb.append("<askQuantity>" + stock[5].substring(0,stock[5].length()-1).trim() + "</askQuantity>");
                       sb.append("</record>");
                       exchange.getIn().setBody(sb.toString());
                       
                       
                	}})
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
