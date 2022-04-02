package network;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

// This class is the main method for the app, it mainly consists of Action listeners for all the buttons
// It also reads the network description file and creates an instance of a network from the file.

public class App {

	public static Algorithm smallNetwork;
	public static Gui gui = new Gui();

	public static void main(String[] args) {

		JFrame frame = new JFrame("Network Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(gui);
		frame.pack();
		frame.setVisible(true);

		try {
			List<String> networkDescription = Files.readAllLines(Paths.get("network"));
			Algorithm smallNetwork = new Algorithm(networkDescription);

			gui.updateDropDowns(smallNetwork.nodeList.size());
			gui.jcomp1.setText(smallNetwork.getRoutingTable(gui.jcomp5.getSelectedItem().toString()));

			gui.jcomp9.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					smallNetwork.nextIteration();
					updateRoutingTables(gui, smallNetwork);
				}
			});

			gui.jcomp10.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					smallNetwork.removeLink(gui.jcomp11.getText());
					updateRoutingTables(gui, smallNetwork);

				}
			});

			gui.jcomp14.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					smallNetwork.reachConvergence();
					updateRoutingTables(gui, smallNetwork);
				}
			});
			
			gui.jcomp15.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					smallNetwork.SplitHorizonUpdate(gui.jcomp15.isSelected());
				}
			});
			
			gui.jcomp13.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String iterations = gui.jcomp12.getText();
					if (!iterations.isEmpty()) {
						for(int i = 0; i<Integer.parseInt(iterations);i++){
							smallNetwork.nextIteration();
							updateRoutingTables(gui, smallNetwork);
				
			}
					}

				}
			});

			gui.jcomp5.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gui.jcomp1.setText(smallNetwork.getRoutingTable(gui.jcomp5.getSelectedItem().toString()));

				}
			});

			gui.jcomp6.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gui.jcomp2.setText(smallNetwork.getRoutingTable(gui.jcomp6.getSelectedItem().toString()));

				}
			});

			gui.jcomp7.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gui.jcomp3.setText(smallNetwork.getRoutingTable(gui.jcomp7.getSelectedItem().toString()));

				}
			});

			gui.jcomp8.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					gui.jcomp4.setText(smallNetwork.getRoutingTable(gui.jcomp8.getSelectedItem().toString()));

				}
			});

		} catch (IOException e) {
			System.out.println("No input network file");
			System.exit(0);
		}

	}

	static void updateRoutingTables(Gui gui, Algorithm smallNetwork) {
		gui.jcomp1.setText(smallNetwork.getRoutingTable(gui.jcomp5.getSelectedItem().toString()));
		gui.jcomp2.setText(smallNetwork.getRoutingTable(gui.jcomp6.getSelectedItem().toString()));
		gui.jcomp3.setText(smallNetwork.getRoutingTable(gui.jcomp7.getSelectedItem().toString()));
		gui.jcomp4.setText(smallNetwork.getRoutingTable(gui.jcomp8.getSelectedItem().toString()));

	}

}
