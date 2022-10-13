package camelinaction;

public class OwnedStock extends Component{
	public int clientID;
	public String ticker;
	public int numShares;
	
	public OwnedStock(int id, String ticker, int numShares) {
		clientID = id;
		this.ticker = ticker;
		this.numShares = numShares;
	}
	
	public String toString() {
		return "Client ID: " + String.valueOf(clientID)+ " Ticker: "+ 
	ticker+ " NumShares: "+String.valueOf(numShares);
	}
	
	
	
	

}
