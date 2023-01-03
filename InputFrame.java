import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class InputFrame extends JFrame {
	
	private JPanel panel;
	private JPanel diamoniPanel;
	private JPanel allInclusivePanel;
	private JPanel buttonPanel;
	
	private JLabel diamoniLabel;
	private JLabel hotelLabel;
	private JLabel allInclusiveLabel;
	
	private JList<String> listView;
	private DefaultListModel<String> model;
	
	private JTextField daysField;
	private JTextField mealsField;
	
	private JButton storeButton;
	private JButton calculateCostButton;
	
	private JTextField costField;
	
	private ArrayList<Hotel> hotels;
	
	public InputFrame() {
		
		try {
			FileInputStream fileIn = new FileInputStream("hotels.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			hotels = (ArrayList<Hotel>)in.readObject();
			fileIn.close();
			in.close();
		}
		catch(IOException exc1)
		{
			exc1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		panel = new JPanel();
		diamoniPanel = new JPanel();
		allInclusivePanel = new JPanel();
		buttonPanel = new JPanel();	
		
		listView = new JList<String>();
		model = new DefaultListModel<String>();
		
		Collections.sort(hotels);
		
		/*
		for(Hotel h : hotels)
		{
			model.addElement(h.getName());
		}
		*/
		
		for(int i=0; i<hotels.size();i++)
		{
			model.addElement(hotels.get(i).getName());
		}
		
		
		listView.setModel(model);
		
		diamoniLabel = new JLabel("Stoixeia Diamonis");
		hotelLabel = new JLabel("Hotel");
		allInclusiveLabel = new JLabel("AllInclusive");
		
		daysField = new JTextField("Hmeres Diamonis");
		mealsField = new JTextField("Plithos Geumatwn (1,2,3)");
		costField = new JTextField("Synoliko Kostos");
		
		storeButton = new JButton("Apothikeusi Kratisis");
		calculateCostButton = new JButton("Ypologismos Kostous");
		
		diamoniPanel.setLayout(new BoxLayout(diamoniPanel, BoxLayout.Y_AXIS));
		diamoniLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		diamoniPanel.add(diamoniLabel);
		hotelLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		diamoniPanel.add(hotelLabel);
		diamoniPanel.add(listView);
		diamoniPanel.add(daysField);
		diamoniPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		allInclusivePanel.setLayout(new GridLayout(2,0));
		allInclusivePanel.add(allInclusiveLabel);
		allInclusivePanel.add(mealsField);
		allInclusivePanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		buttonPanel.setLayout(new GridLayout(2,0));
		buttonPanel.add(storeButton);
		buttonPanel.add(calculateCostButton);
		buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		panel.add(diamoniPanel);
		panel.add(allInclusivePanel);
		panel.add(buttonPanel);
		panel.add(costField);
		
		this.setContentPane(panel);
		
		ButtonListener listener = new ButtonListener();
		storeButton.addActionListener(listener);
		calculateCostButton.addActionListener(listener);
		
		this.setVisible(true);
		this.setSize(200, 320);
		this.setLocation(200, 0);
		this.setTitle("Input");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String selectedHotelName = listView.getSelectedValue();
			Hotel selectedHotel = null;
			for(Hotel h : hotels)
			{
				if (h.getName().equals(selectedHotelName))
				{
					selectedHotel = h;
					break;
				}
			}
			
			if (selectedHotel!=null)
			{
				if (e.getSource().equals(storeButton))
				{
					String daysText = daysField.getText();
					int days = Integer.parseInt(daysText);
					Reservation r;
					if (mealsField.getText().equals(""))
					{
						r = new Reservation(days);
					}
					else
					{
						int meals = Integer.parseInt( mealsField.getText() );
						r = new AllInclusiveReservation(days,meals);
					}
					selectedHotel.addReservation(r);
				}
				else
				{
					int total = selectedHotel.calculateTotalCost();
					costField.setText( Integer.toString(total) );
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "No hotel selected!");
				System.out.println("No hotel selected");
			}			
		}
	}

}
