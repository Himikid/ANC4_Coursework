package network;

//This class describes an entry in a routing table.
//Origin is used for split-horizon functionality

public class Entry {
	
	
	String cost;
	Node path = null;
	Node Origin = null;
	
	public Entry(int i) {
		cost = "N/A";
	}

	public Entry(Node path, int cost) {
		this.cost = String.valueOf(cost);
		this.path = path;
		if(this.Origin == null) {
			this.Origin = path;
		}
	}
	

	int getCost() {
		if (cost == "N/A") {
			return 1000;
		} else {
			return Integer.parseInt(cost);
		}
	}
	
	
}
