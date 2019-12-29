package se.team2.views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.CompoundBorder;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class Testing
{

	private JFrame frame;
//	private final Action action = new SwingAction();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				try
				{
					Testing window = new Testing();
					window.frame.setVisible(true);
				} catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Testing()
	{
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	JTextArea txt;

	private void initialize()
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		TestRequestHandler t = new TestRequestHandler();

		//JTextArea textArea = new JTextArea();
		//textArea.setBounds(100, 100, 450, 300);
		//frame.getContentPane().add(textArea);
		
		txt = new JTextArea(5, 30);
		txt.setBounds(new Rectangle(0, 32, 450, 246));
		frame.getContentPane().add(txt);
		txt.setWrapStyleWord(true);
		txt.setLineWrap(true);
		txt.setEditable(false);
		txt.setText("Stock information for 3 main companies. Click on one to find out more information.");

		/////////////////////// THIRD BUTTON ///////////////////////

		JButton rfem = new JButton("RFEM");
		rfem.setBounds(170, 0, 117, 29);
		frame.getContentPane().add(rfem);
		rfem.setActionCommand("pushRFEM");
		rfem.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ("pushRFEM".equals(e.getActionCommand()))
				{
					t.makeRequest(TestRequestHandler.REQUEST_TYPE_TEST);
					String x = "\t\tRFEM\t\t\t\nDates\t\tOpens\tHighs\tLows\tVolumes\n";
					for (int i = 0; i < t.size(); i++)
					{
						x += t.getString("date", i) + "\t" + t.getString("open", i) + t.getString("close", i) + "\t"
								+ t.getString("high", i) + "\t" + t.getString("low", i) + "\t"
								+ t.getString("volume", i) + "\t\n";
					}
					txt.setText(x);
				}
			}
		});

		/////////////////////// SECOND BUTTON ///////////////////////

		JButton google = new JButton("Google");
		google.setBounds(0, 0, 117, 29);
		frame.getContentPane().add(google);
		google.setActionCommand("pushGoogle");
		google.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ("pushGoogle".equals(e.getActionCommand()))
				{
					t.makeRequest(TestRequestHandler.REQUEST_TYPE_TEST);
					String x = "\t\tGOOGLE\t\t\t\nDates\t\tOpens\tHighs\tLows\tVolumes\n";
					for (int i = 0; i < t.size(); i++)
					{
						x += t.getString("date", i) + "\t" + t.getString("open", i) + t.getString("close", i) + "\t"
								+ t.getString("high", i) + "\t" + t.getString("low", i) + "\t"
								+ t.getString("volume", i) + "\t\n";
					}
					txt.setText(x);
				}
			}
		});

		/////////////////////// FIRST BUTTON ///////////////////////

		JButton apple = new JButton("Apple");
		apple.setBounds(333, 0, 117, 29);
		frame.getContentPane().add(apple);
		apple.setActionCommand("pushApple");

		apple.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if ("pushApple".equals(e.getActionCommand()))
				{
					t.makeRequest(TestRequestHandler.REQUEST_TYPE_TEST);
					String x = "\t\tAPPLE\t\t\t\nDates\t\tOpens\tHighs\tLows\tVolumes\n";
					for (int i = 0; i < t.size(); i++)
					{
						x += t.getString("date", i) + "\t" + t.getString("open", i) + t.getString("close", i) + "\t"
								+ t.getString("high", i) + "\t" + t.getString("low", i) + "\t"
								+ t.getString("volume", i) + "\t\n";
					}
					txt.setText(x);
				}
			}
		});
	}
}
