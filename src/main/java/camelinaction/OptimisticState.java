package camelinaction;

public class OptimisticState implements State{

	public boolean processAdvice(String advice) {
		if (advice.equals("buy")) {
			return true;
		}
		return false;
	}

}
