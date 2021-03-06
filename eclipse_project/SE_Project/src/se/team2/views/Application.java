package se.team2.views;

import java.awt.Component; 
import se.team2.views.charts.*;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.CompoundBorder;

import se.team2.controllers.FileHandler;
import se.team2.controllers.RequestHandler;
import se.team2.controllers.ServerRequestHandler;
import se.team2.utils.Table;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.Rectangle;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.beans.PropertyChangeEvent;

public class Application
{
	private int request_type = 0;
	private JFrame frame;
	private DateFrame aggregate_df;
	private String aggregate_datebefore = null;
	private String aggregate_dateafter = null;
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
					Application window = new Application();
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
	public Application()
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
		frame.setTitle("Team 2 REST GUI");
		frame.setBounds(100, 100, 1000, 500);
		//frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		ServerRequestHandler t = new ServerRequestHandler();
		aggregate_df = new DateFrame(this);
		//JTextArea textArea = new JTextArea();
		//textArea.setBounds(100, 100, 450, 300);
		//frame.getContentPane().add(textArea);
		
		txt = new JTextArea(5, 30);
		txt.setFont(new Font("monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(txt);
		int scroll_width = frame.getWidth()-20;
		int scroll_height = frame.getHeight()-85-100;
		int scroll_x = (frame.getWidth() - scroll_width)/2;
		int scroll_y = (frame.getHeight() - scroll_height)/2;
		scroll.setBounds(new Rectangle(scroll_x, scroll_y, scroll_width, scroll_height));    
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.getContentPane().add(scroll);
		txt.setWrapStyleWord(true);
		//txt.setLineWrap(true);
		txt.setEditable(false);
		txt.setText("Stock information for 3 main companies. Click on one to find out more information.");
		
		addChooseReportOptions(frame);
		
		/////////////////////// THIRD BUTTON ///////////////////////
		
		
		frame.addComponentListener(new ComponentAdapter() {
			
			@Override
			public void componentResized(ComponentEvent e) {
				int scroll_width = frame.getWidth()-20;
				int scroll_height = frame.getHeight()-85-100;
				int scroll_x = (frame.getWidth() - scroll_width)/2;
				int scroll_y = (frame.getHeight() - scroll_height)/2;
				scroll.setBounds(new Rectangle(scroll_x,scroll_y,scroll_width,scroll_height));
				scroll.repaint();
			}
			
		});
		Application app = this;
		
		JButton save_report = new JButton("Save Report");
		save_report.setBounds(0,50,117,29);
		frame.getContentPane().add(save_report);
		save_report.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showSaveDialog(app.frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    try {
						FileHandler.writeTo(selectedFile.getAbsolutePath(), (String) app.txt.getText());
						JOptionPane.showMessageDialog(app.frame, "The current displayed report was saved to the requested file!");
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(app.frame, "Uh Oh! Code Red! Code Red! Try Again. It didn't work.");
					}
				}
			}
			
		});
		JButton open_report = new JButton("Open Report");
		open_report.setBounds(170,50,117,29);
		frame.getContentPane().add(open_report);
		open_report.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(app.frame);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    try {
						String report = FileHandler.readFrom(selectedFile.getAbsolutePath());
						app.txt.setText(report);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(app.frame, "Uh Oh! Code Red! Code Red! Try Again. It didn't work.");
					}
				}
			}
			
		});
		
		JButton view_charts = new JButton("View Charts");
		view_charts.setBounds(333, 50, 117, 29);
		frame.getContentPane().add(view_charts);
		view_charts.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch(app.request_type) {
				case ServerRequestHandler.AGGREGATE_REQUEST:
					t.setParam("date_start", app.aggregate_datebefore);
					t.setParam("date_end", app.aggregate_dateafter);
				    BarChart_AWT chart = new BarChart_AWT("Aggregate Report", "Aggregate Report", t);
				    chart.pack( );          
				    chart.setVisible( true ); 
					break;
				case ServerRequestHandler.POP_SEARCH:
				    SwingUtilities.invokeLater(() -> {
				        PieChartExample example = new PieChartExample("Search Popularity Report",t);
				        example.setSize(800, 400);
				        example.setLocationRelativeTo(null);
				        example.setVisible(true);
				      });
				    break;
				case ServerRequestHandler.DAYS_30_REQUEST:
					JFrame frame = new ChartFrame(app,t);
					frame.setVisible(true);
					break;
				default:
					JOptionPane.showMessageDialog(app.frame, "There are no charts available for that report!");
				}
			}
			
		});

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

					String x = "";
					
					if(app.request_type == t.AGGREGATE_REQUEST) {
						if(app.aggregate_datebefore != null && app.aggregate_dateafter != null) {
							try {
								t.setParam("date_start",app.aggregate_datebefore);
								t.setParam("date_end",app.aggregate_dateafter);
								t.setParam("symbol", "rfem");
								t.makeRequest(app.request_type);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						try {
							t.setParam("symbol", "rfem");
							t.makeRequest(app.request_type);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(app.request_type == t.COMPANY_INFO) {
						x = companyInfo_output(t,"RFEM");
					}else {
						x = makeOutputTable(t,"RFEM");
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
				String x = "";
				if ("pushGoogle".equals(e.getActionCommand()))
				{
					if(app.request_type == t.AGGREGATE_REQUEST) {
						if(app.aggregate_datebefore != null && app.aggregate_dateafter != null) {
							try {
								t.setParam("date_start",app.aggregate_datebefore);
								t.setParam("date_end",app.aggregate_dateafter);
								t.setParam("symbol", "googl");
								t.makeRequest(app.request_type);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						try {
							t.setParam("symbol", "googl");
							t.makeRequest(app.request_type);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					if(app.request_type == t.COMPANY_INFO) {
						x = companyInfo_output(t,"GOOGLE");
					}else {
						x = makeOutputTable(t,"GOOGLE");
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
				String x = "";
				if ("pushApple".equals(e.getActionCommand()))
				{
					if(app.request_type == t.AGGREGATE_REQUEST) {
						if(app.aggregate_datebefore != null && app.aggregate_dateafter != null) {
							try {
								t.setParam("date_start",app.aggregate_datebefore);
								t.setParam("date_end",app.aggregate_dateafter);
								t.setParam("symbol", "aapl");
								t.makeRequest(app.request_type);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						try {
							t.setParam("symbol", "aapl");
							t.makeRequest(app.request_type);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					if(app.request_type == t.COMPANY_INFO) {
						x = companyInfo_output(t,"APPLE");
					}else {
						x = makeOutputTable(t,"APPLE");
					}
					txt.setText(x);
				}
			}
		});
	}
	
	
	private Component ActionListener(ActionListener actionListener) {
		// TODO Auto-generated method stub
		return null;
	}

	private static String formatNumber(double num) {
		DecimalFormat df = new DecimalFormat("#####.#####");
		return df.format(num);
	}
	/*private static String make30DaysOutputTable(RequestHandler t,String company) {
		String x = "\t\t"+company+"\t\t\t\nDates\tOpens\tClose\tHighs\tLows\tVolumes\tChange\tChange %\tChange Over Time\tVolume Change\t"
				  +"Volume Change %\tVolume Change Over Time\n";
		for (int i = 0; i < t.size(); i++)
		{
			x += t.getString("date", i) + "\t" + formatNumber(t.getDouble("open", i)) + "\t" +  formatNumber(t.getDouble("close", i)) + "\t"
					+ formatNumber(t.getDouble("high", i)) + "\t" + formatNumber(t.getDouble("low", i)) + "\t"
					+ formatNumber(t.getDouble("volume", i)) + "\t" + formatNumber(t.getDouble("change", i))+ "\t"
					+ formatNumber(t.getDouble("changePercent", i))+ "\t" + formatNumber(t.getDouble("changeOverTime", i))+ "\t\t"
					+ formatNumber(t.getDouble("volumeChange", i)) + "\t\t" + formatNumber(t.getDouble("volumeChangePercent", i))+ "\t\t"
					+ formatNumber(t.getDouble("volumeChangeOverTime", i)) + "\n";
		}
		return x;
	} */
//	private static String makeOutputTable(RequestHandler t, String company) {
//		String[] fields = t.keys();
//		String padding = "          ";
//		int[] column_lengths = new int[fields.length];
//		String extra_space = "     ";
//		String header = company+"\n";
//		for(int x = 0; x < fields.length; x++) {
//			header += fields[x]+extra_space+padding;
//			column_lengths[x] = fields[x].length() + padding.length();
//		}
//		header += "\n";
//		
//		String body = "";
//		for(int x = 0; x < t.size(); x++) {
//			for(int a = 0; a < fields.length; a++) {
//				String value = t.getString(fields[a], x);
//				if(isDouble(value)) value = formatNumber(t.getDouble(fields[a], x));
//				
//				int col_diff = (column_lengths[a] - value.length());
//				String col_diff_str = "";
//				for(int x1=0; x1 < col_diff; x1++) col_diff_str += " ";
//				body += value + col_diff_str + extra_space;
//			}
//			body += "\n";
//		}
//		
//		return header+body;
//	}
	public static String makeOutputTable(RequestHandler t, String company) {
		try {
			if(t.getString("error_msg", 0).equals("No Records Exist")) {
				return company+"\nNo Records Exist";
			}
		}catch(Exception e) {
			
		}
		
		String[] fields = t.keys();
		Table table = new Table();
		table.setHeaders(fields);
		table.setShowVerticalLines(true);
		
		for(int x = 0; x < t.size(); x++) {
			String[] array = new String[fields.length];
			for(int a = 0; a < fields.length; a++) {
				String value = t.getString(fields[a], x);
				if(isDouble(value)) value = formatNumber(t.getDouble(fields[a], x));
				
				array[a] = value;
			}
			table.addRow(array);
		}
		
		return company+"\n"+table.output();
	}
	public static boolean isDouble(String str) {
		try {
			Double d;
			d = Double.parseDouble(str);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	private void addChooseReportOptions(JFrame f) {
		Application app = this;
		app.request_type = ServerRequestHandler.DAYS_30_REQUEST;
		JRadioButton r1 = new JRadioButton("30-Day Report");
		JRadioButton r2 = new JRadioButton("Company Info");
		JRadioButton r3 = new JRadioButton("Aggregate Report");
		JRadioButton r4 = new JRadioButton("Search Popularity");
		
		r1.setBounds(500,0,120,30);
		r2.setBounds(620,0,120,30);
		r3.setBounds(740,0,150,30);
		r4.setBounds(500,45,150,30);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(r1);
		bg.add(r2);
		bg.add(r3);
		bg.add(r4);
		
		f.add(r1);
		f.add(r2);
		f.add(r3);
		f.add(r4);
		
		r4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(r4.isSelected()) {
					app.request_type = ServerRequestHandler.POP_SEARCH;
				}
			}
		});
		
		r3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(r3.isSelected()) {
					app.request_type = ServerRequestHandler.AGGREGATE_REQUEST;
					app.aggregate_df.setVisible(true);
				}
			}
		});
		
		r2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(r2.isSelected()) {
					app.request_type = ServerRequestHandler.COMPANY_INFO;
				}
			}
			
		});
		
		r1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(r1.isSelected()) {
					app.request_type = ServerRequestHandler.DAYS_30_REQUEST;
				}
			}
			
		});
	}
	
	public void setAggregateDateBefore(String d) { aggregate_datebefore = d; }
	public void setAggregateDateAfter(String d) { aggregate_dateafter = d; }
	
	private static String companyInfo_output(ServerRequestHandler srh, String symbol) {
		String str = symbol+"\n\n";
		String a = companyInfo_id_table(srh).output();
		if(!a.isBlank()) str += "IDENTIFICATION\n" + a +"\n";
		
		a =  companyInfo_descr_table(srh);
		if(!a.isBlank()) str += "DESCRIPTION\n" + a +"\n\n";
		
		a = companyInfo_siInfo(srh).output();
		if(!a.isBlank()) str += "STOCK AND INDUSTRY INFO\n" + a  + "\n";
		
		a = companyInfo_contactInfo(srh).output();
		if(!a.isBlank()) str += "CONTACT INFO\n" + a + "\n";
		
		return str;
	}
	
	private static Table companyInfo_contactInfo(ServerRequestHandler srh) {
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String[] fields = new String[] { "website" , "address" , "address2" , "city" , "state" , "zip" , "country" , "phone" };
		fields = companyInfo_dropNullFields(srh,fields);
		
		String[] row = new String[fields.length];
		for(int x = 0; x < row.length; x++) {
			row[x] = srh.getString(fields[x], 0);
		}
		t.setHeaders(fields);
		t.addRow(row);
		
		return t;
	}
	
	private static Table companyInfo_siInfo(ServerRequestHandler srh) {
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String[] fields = new String[] { "exchange" , "industry" , "issueType" , "sector" , "employees" };
		fields = companyInfo_dropNullFields(srh,fields);
		
		String[] row = new String[fields.length];
		for(int x = 0; x < row.length; x++) {
			row[x] = srh.getString(fields[x], 0);
		}
		t.setHeaders(fields);
		t.addRow(row);
		
		return t;
	}

	public static String companyInfo_descr_table(ServerRequestHandler srh) {
		int line_length = 100;
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String field = "description";
		String value = srh.getString(field, 0);
		
		String text = "";
		if(value.equalsIgnoreCase("null")) return "";
		for(int x = 0; x < value.length(); x+=line_length) {
			if(x+line_length > value.length()) text += value.substring(x,value.length());
			else text += value.substring(x,x+line_length) + "\n";
		}
		
		return text;
	}
	
	public static Table companyInfo_id_table(ServerRequestHandler srh) {
		Table t = new Table();
		t.setShowVerticalLines(true);
		
		String[] fields = companyInfo_dropNullFields(srh,new String[] { "companyName", "securityName", "primarySicCode", "ceo" });
		
		String[] row = new String[fields.length];
		for(int x = 0; x < row.length; x++) {
			row[x] = srh.getString(fields[x], 0);
		}
		t.setHeaders(fields);
		t.addRow(row);
		
		return t;
	}
	
	public static String[] companyInfo_dropNullFields(ServerRequestHandler srh,String[] fields) {
		boolean[] drop_column = new boolean[fields.length];
		int count = fields.length;
		
		for(int x = 0; x < fields.length; x++) {
			if (srh.getString(fields[x],0).equalsIgnoreCase("null")) {
				drop_column[x] = true;
				count = count - 1;
			} else {
				drop_column[x] = false;
			}
		}
		
		String[] fields2 = new String[count];
		int index = 0;
		for(int x = 0; x < fields.length; x++) {
			if(!drop_column[x]) {
				fields2[index] = fields[x];
				index++;
			}
		}
		
		return fields2;
		
	}
}
