package camelinaction;

// advise is the strategy
public interface Advise {
	public boolean advise(double bidPrice, int bidQuantity, double askPrice, int askQuantity);
	
}
