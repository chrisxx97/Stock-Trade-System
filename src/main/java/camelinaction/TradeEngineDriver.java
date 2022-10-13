package camelinaction;

import java.util.HashSet;
import java.util.Set;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class TradeEngineDriver {

	public static void main(String[] args) throws Exception{
		//
		final Portfolio clientPortfolio1 =  new Portfolio(1);
		final Portfolio clientPortfolio2 =  new Portfolio(2);
		
		
		
        CamelContext context = new DefaultCamelContext();

        // connect to ActiveMQ JMS broker listening on localhost on port 61616
        ConnectionFactory connectionFactory = 
        	new ActiveMQConnectionFactory("tcp://localhost:61616");
        context.addComponent("jms",
            JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        
        // add our route to the CamelContext
        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("jms:queue:Trade_Engine")
                .process(new Processor() {
                	public void process(Exchange exchange) throws Exception {
                		String[] array = exchange.getIn().getBody(String.class).split(",");
                		int clientID = Integer.valueOf(array[4]);
                		String ticker = array[0];
                		if (clientID == 1) {
                			clientPortfolio1.addComponent(new OwnedStock(1, ticker, 1));
                		}else if (clientID == 2){
                			clientPortfolio2.addComponent(new OwnedStock(2, ticker, 1));
                		}
                		
                		
                	}
                });
                
            }
        });
        
        // run the camel context
        context.start();
        Thread.sleep(6000);
        context.stop();
        
        
        // add the client portfolios to the engine portfolio
        Portfolio enginePortfolio = new Portfolio();
        
        enginePortfolio.addComponent(clientPortfolio1);
        
        enginePortfolio.addComponent(clientPortfolio2);
        
        System.out.println(enginePortfolio.toString());
        
        
        
        
        
        

	}

}
