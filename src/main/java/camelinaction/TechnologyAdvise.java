package camelinaction;

public class TechnologyAdvise implements Advise{

	public boolean advise(double bidPrice, int bidQuantity, double askPrice, int askQuantity) {
		// TODO Auto-generated method stub
		if (bidPrice >= askPrice) {
			return true;
		}
		return false;
	}

}
