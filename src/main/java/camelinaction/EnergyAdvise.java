package camelinaction;

public class EnergyAdvise implements Advise{

	public boolean advise(double bidPrice, int bidQuantity, double askPrice, int askQuantity) {
		// TODO Auto-generated method stub
		if (bidQuantity >= askQuantity) {
			return true;
		}
		return false;
	}
	

}
