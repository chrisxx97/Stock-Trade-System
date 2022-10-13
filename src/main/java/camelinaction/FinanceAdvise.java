package camelinaction;

public class FinanceAdvise implements Advise{

	public boolean advise(double bidPrice, int bidQuantity, double askPrice, int askQuantity) {
		if ( bidPrice < askPrice) {
			return true;
		}
		return false;
	}

}
