package camelinaction;

public class CDMDriver {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		CDM cdm = CDM.getInstance();
		cdm.setCamel();
		cdm.startCamel(3000);

	}

}
