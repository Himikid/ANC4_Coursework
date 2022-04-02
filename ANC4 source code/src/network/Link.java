package network;

// This class describes links between nodes.

public class Link {

	private int cost;
	private Node startNode;
	private Node endNode;

	public Link(int cost, Node start, Node end) {
		this.cost = cost;
		this.startNode = start;
		this.endNode = end;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public Node getStartNode() {
		return startNode;
	}

	public void setStartNode(Node startNode) {
		this.startNode = startNode;
	}

	public Node getEndNode() {
		return endNode;
	}

	public void setEndNode(Node endNode) {
		this.endNode = endNode;
	}
	
	

}
