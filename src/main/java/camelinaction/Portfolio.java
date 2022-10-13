package camelinaction;

import java.util.ArrayList;
import java.util.List;

public class Portfolio extends Component{

	private List<Component> port;
	public int clientID;
	
	public Portfolio() {
		port = new ArrayList<Component>();
	}
	
	public Portfolio(int id) {
		port = new ArrayList<Component>();
		clientID = id;
		
	}

	
	
	public void addComponent(Component comp) {
		port.add(comp);
	}
	
	public List<Component> getComponents(){
		return port;
	}
	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		for (Component component : port) {
			ret.append(component.toString());
			ret.append("\n");
		}
		return ret.toString();
	}
	
	

}
