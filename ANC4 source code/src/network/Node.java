package network;

//This class describes a node
import java.util.ArrayList;
import java.util.List;

public class Node {

	private String name;
	private List<Link> linksList = new ArrayList<Link>();
	List<Entry> routingTable = new ArrayList<Entry>();
	private boolean splitHorizon = false;

	public Node(String name) {
		this.name = name;
	}

	public void addLink(Link link) {
		this.linksList.add(link);
	}

	public String toString() {
		return this.name;
	}

	public List<Link> getLinksList() {
		return linksList;
	}

	//Function to create tables, loops through nodes and adds links
	public void createTables(List<Node> nodeList) {
		for (Node n : nodeList) {
			this.routingTable.add(new Entry(0));
		}

		for (Link l : linksList) {
			Node destination;
			if (l.getStartNode().equals(this)) {
				destination = l.getEndNode();
			} else {
				destination = l.getStartNode();
			}
			routingTable.set(destination.toInt() - 1, new Entry(destination, l.getCost()));

			routingTable.set(this.toInt() - 1, new Entry(this, 0));
		}

	}

	//As the name is a string this function gives the name as an int
	int toInt() {
		return Integer.parseInt(this.name);

	}

	//Function to get a nodes neighbours, it does this by looping through the links list
	public List<Node> getNeighbours() {
		List<Node> neighbours = new ArrayList<Node>();
		for (Link l : linksList) {
			if (l.getStartNode().equals(this)) {
				neighbours.add(l.getEndNode());
			} else {
				neighbours.add(l.getStartNode());
			}

		}
		return neighbours;
	}

	//Function to update a routing table in a given exchange with another node
	public void updateTable(Node node) {
		int cost = 0;
		for (Link l : linksList) {
			if (l.getStartNode().equals(node) || l.getEndNode().equals(node)) {
				cost = l.getCost();
			}
		}

		routingTable.size();
		for (int i = 0; i < routingTable.size(); i++) {

			try {
				if (node.routingTable.get(i).path.equals(this) & (routingTable.get(i).getCost() + cost < 1000)) {

					node.routingTable.get(i).cost = String.valueOf(routingTable.get(i).getCost() + cost);

				}

				if (splitHorizon) {

					if (node.routingTable.get(i).Origin.equals(this)) {
						if (routingTable.get(i).getCost() + cost > 1000) {
							node.routingTable.get(i).cost = "N/A";
						} else {
							node.routingTable.get(i).cost = String.valueOf(routingTable.get(i).getCost() + cost);
						}
					}
				}

			} catch (NullPointerException e) {

			}

			if (routingTable.get(i).getCost() > (node.routingTable.get(i).getCost() + cost)) {
				if ((node.routingTable.get(i).getCost() + cost) < 1000) {
					routingTable.set(i, new Entry(node, node.routingTable.get(i).getCost() + cost));
				} else {
					routingTable.set(i, new Entry(0));
				}
			}

		}

	}

	// function to update cost of a link between two nodes
	public void updateCost(Node Node, int cost) {

		if (cost == 1000) {
			removeLink(this, Node);
		}

		for (Link l : linksList) {
			if (l.getStartNode().equals(Node) || l.getEndNode().equals(Node)) {
				l.setCost(cost);
			}
		}

	}

	//function to remove a link
	public void removeLink(Node node, Node node2) {

		for (int i = 0; i < linksList.size(); i++) {
			if (node.linksList.get(i).getStartNode().equals(node2)
					|| node.linksList.get(i).getEndNode().equals(node2)) {
				linksList.remove(i);
				node.routingTable.get(Integer.parseInt(node2.name) - 1).cost = "N/A";
				node2.routingTable.get(Integer.parseInt(node.name) - 1).cost = "N/A";
			}

		}

	}

	// function to set split horizon boolean
	public void setSplitHorizon(boolean splitHorizon) {
		this.splitHorizon = splitHorizon;
	}

}
