package camelinaction;

public class ClientDriver {

	public static void main(String[] args) throws Exception{
		Client c1 = new Client(1, new OptimisticState());
		c1.setSubscriptions("Finance","Technology");
		c1.setCamel();
		c1.startCamel(15000);
		
		Client c2 = new Client(2, new PessimisticState());
		c2.setSubscriptions("Energy", "ConsunerServices");
		c2.setCamel();
		c2.startCamel(18000);

	}

}
