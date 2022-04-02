package network;

import java.util.ArrayList;
import java.util.List;

// This class is the main instance for a network

public class Algorithm {

	private Boolean nodesGenerated = false;
	List<Node> nodeList = new ArrayList<Node>();
	private List<Link> linkList = new ArrayList<Link>();
	int convergenceLimiter = 20;
	int count = 0;

	public Algorithm(List<String> networkDescription) {

		// Loop through network description to generate nodes and links
		for (String s : networkDescription) {
			if (!nodesGenerated) {
				createNodes(s);
				nodesGenerated = true;
			} else {
				createLink(s);
			}

		}

		// loop nodes to create initial tables
		for (Node node : nodeList) {
			node.createTables(nodeList);
		}

	}

	// Function to create nodes
	private void createNodes(String Nodes) {
		for (String s : Nodes.split(",")) {
			nodeList.add(new Node(s));
		}

	}

	//Function to create links
	private void createLink(String s) {
		String[] split = s.split(",");

		try {
			Node start = nodeList.get(Integer.parseInt(split[0]) - 1);
			Node end = nodeList.get(Integer.parseInt(split[1]) - 1);
			int cost = Integer.parseInt(split[2]);
			Link link = new Link(cost, start, end);
			start.addLink(link);
			end.addLink(link);
			linkList.add(link);

		} catch (IndexOutOfBoundsException e) {
			System.out.println("Unknown node used in links that isn't specified");

		}

	}

	// This function simulates and iteration - it iterates through each node and creates a list of its neighbours 
	// and then calls a function to exchange tables with it's neighbours.
	void nextIteration() {

		for (Node n : nodeList) {
			List<Node> neighbours = n.getNeighbours();
			for (Node test : neighbours) {
				n.updateTable(nodeList.get(test.toInt() - 1));
			}

		}

	}
	
	//This function returns a string for a routing table for a given node. e.g the index for Node 1 would be 0
	String getRoutingTable(String index) {
		String table = "";
		String path = "";
		Node node = nodeList.get(Integer.parseInt(index) - 1);
		for (int i = 0; i < nodeList.size(); i++) {
			try {
				path = "- via Node " + node.routingTable.get(i).path.toString();
			} catch (NullPointerException e) {
				path = "";
			}
			table = table + String.format("%s to %d costs: %s  %s \n", node.toString(), i + 1,
					node.routingTable.get(i).cost, path);
		}

		return table;
	}

	//This function removes a links, it gets the link information and updates the nodes that use the link 
	// and their neighbours.
	public void removeLink(String link) {
		String[] split = link.split(",");
		try {
			Node startNode = nodeList.get(Integer.parseInt(split[0]) - 1);
			Node endNode = nodeList.get(Integer.parseInt(split[1]) - 1);

			int cost = Integer.parseInt(split[2]);

			startNode.updateCost(endNode, cost);
			endNode.updateCost(startNode, cost);

			List<Node> neighbours = startNode.getNeighbours();
			for (Node test : neighbours) {
				startNode.updateTable(nodeList.get(test.toInt() - 1));
			}

			List<Node> neighbours_2 = endNode.getNeighbours();
			for (Node test : neighbours_2) {
				endNode.updateTable(nodeList.get(test.toInt() - 1));
			}

		} catch (IndexOutOfBoundsException e) {
			System.out.println("Link you're trying to remove doesn't exist");
		}

	}

	// Function used to loop upntil convergence it keeps calling nextiteration() until convergence checksum doesn't change.
	public void reachConvergence() {
		int currentCheckSum = getCheckSum();
		nextIteration();
		int newCheckSum = getCheckSum();
		if (newCheckSum != currentCheckSum & count != convergenceLimiter) {
			count++;
			reachConvergence();
		} else {
			count = 0;
		}

	}
	
	
	//Function to get checksum of costs
	public int getCheckSum() {
		int checkSum = 0;
		for (Node n : nodeList) {
			for (Entry e : n.routingTable) {
				checkSum = checkSum + e.getCost();
			}
		}
		return checkSum;

	}

	//Updates all nodes to tell them split horizon is selected
	public void SplitHorizonUpdate(boolean selected) {
		for (Node n : nodeList) {
			n.setSplitHorizon(selected);
		}

	}

}
