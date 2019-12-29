package se.team2.test.request_speed;

import se.team2.views.charts.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import se.team2.controllers.ServerRequestHandler;
import se.team2.views.Application;

public class ChartFrame extends JFrame {
	private Application frame = null;
	private ServerRequestHandler srh;
	
	public ChartFrame(Application frm,ServerRequestHandler srh) {
		super("Choose Chart Type");
		frame = frm;
		this.srh = srh;
		init();
	}
	
	public ChartFrame() {
		super("Choose Chart Type");
		init();
	}
	
	public void setParent(Application frame) {
		this.frame = frame;
	}
	
	private void init() {
		ChartFrame app = this;
		setBounds(100, 100, 250, 250);
		setResizable(false);
		getContentPane().setLayout(null);
		//setVisible(true);
		
//		JLabel start_date_label = new JLabel("Start Date (YYYY-MM-DD): ");
//		start_date_label.setBounds(0,0,300,10);
//		getContentPane().add(start_date_label);
		
		String[] types = {
				"open",
				"close",
				"high",
				"low",
				"volume",
				"change",
				"changePercent",
				"changeOverTime",
				"volumeChange",
				"volumeChangePercent",
				"volumeChangeOverTime"
		};
		JComboBox select_types = new JComboBox(types);
		select_types.setSelectedIndex(1);
		select_types.setBounds(0,0,200,25);
		getContentPane().add(select_types);
		
		
//		JTextField start_date_text = new JTextField(100);
//		start_date_text.setBounds(0,20,100,25);
//		start_date_text.setText("2019-09-11");
//		getContentPane().add(start_date_text);
//		
//		JLabel end_date_label = new JLabel("Start Date (YYYY-MM-DD): ");
//		end_date_label.setBounds(0,80,300,10);
//		getContentPane().add(end_date_label);
//		
//		JTextField end_date_text = new JTextField(100);
//		end_date_text.setBounds(0,100,100,25);
//		end_date_text.setText("2019-11-11");
//		getContentPane().add(end_date_text);
		
		JButton submit = new JButton("submit");
		submit.setBounds(0,150,75,25);
		getContentPane().add(submit);
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String type = (String) select_types.getSelectedItem();
		        SwingUtilities.invokeLater(() -> {
		            LineChartEx ex = new LineChartEx(srh,type);
		            ex.setVisible(true);
		        });
			}
		});
	}
	
	private boolean isDate(String str,String fmt) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
			dateFormat.parse(str);
			return true;
		}catch(Exception e) {
			return false;
		}
	}
	private String cleanDate(String str,String fmt) {
		try{
			SimpleDateFormat dateFormat = new SimpleDateFormat(fmt);
			return dateFormat.format(dateFormat.parse(str));
		}catch(Exception e) {
			return null;
		}
	}


}