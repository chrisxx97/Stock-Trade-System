package camelinaction;

import camelinaction.Agent.Specialty;

public class AgentDriver {

	public static void main(String[] args) throws Exception{
		Agent a1 = new Agent(new FinanceAdvise(), Specialty.Finance);
		a1.setCamel();
		a1.startCamel(5000);
		
		Agent a2 = new Agent(new TechnologyAdvise(), Specialty.Technology);
		a2.setCamel();
		a2.startCamel(5000);
		
		Agent a3 = new Agent(new ConsumerAdvise(), Specialty.ConsumerServices);
		a3.setCamel();
		a3.startCamel(8000);
		
		Agent a4 = new Agent(new EnergyAdvise(), Specialty.Energy);
		a4.setCamel();
		a4.startCamel(8000);

	}

}
