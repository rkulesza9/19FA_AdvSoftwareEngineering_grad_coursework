package se.team2.views.charts;

import java.text.DecimalFormat;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import se.team2.controllers.ServerRequestHandler;


public class PieChartExample extends JFrame {
  private static final long serialVersionUID = 6294689542092367723L;

  public PieChartExample(String title, ServerRequestHandler srh) {
    super(title);

    // Create dataset
    PieDataset dataset = createDataset(srh);

    // Create chart
    JFreeChart chart = ChartFactory.createPieChart(
        "Search Popularity Report",
        dataset,
        true, 
        true,
        false); 

    //Format Label
    PieSectionLabelGenerator labelGenerator = new StandardPieSectionLabelGenerator(
        "{0} : {1}/{3} ({2})", new DecimalFormat("0"), new DecimalFormat("0%")) ;
    ((PiePlot) chart.getPlot()).setLabelGenerator(labelGenerator);
    
    // Create Panel
    ChartPanel panel = new ChartPanel(chart);
    setContentPane(panel);
  }

  private PieDataset createDataset(ServerRequestHandler srh) {
	  try {
		  	ServerRequestHandler srh_rfem = new ServerRequestHandler();
		  	ServerRequestHandler srh_aapl = new ServerRequestHandler();
		  	ServerRequestHandler srh_googl = new ServerRequestHandler();
		  	
		  	CountDownLatch latch = new CountDownLatch(3);
		  	
		  	Thread thread_rfem = new Thread() {
		  		@Override
		  		public void run() {
		  			try {
		  			    srh_rfem.setParam("symbol", "rfem");
						srh_rfem.makeRequest(ServerRequestHandler.POP_SEARCH);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		  			latch.countDown();
		  		}
		  	};
		  	Thread thread_aapl = new Thread() {
		  		@Override
		  		public void run() {
		  			try {
		  				srh_aapl.setParam("symbol", "aapl");
						srh_aapl.makeRequest(ServerRequestHandler.POP_SEARCH);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		  			latch.countDown();
		  		}
		  	};
		  	Thread thread_googl = new Thread() {
		  		@Override
		  		public void run() {
		  			try {
		  				srh_googl.setParam("symbol", "googl");
						srh_googl.makeRequest(ServerRequestHandler.POP_SEARCH);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		  			latch.countDown();
		  		}
		  	};
		  	
		  	thread_rfem.start();
		  	thread_aapl.start();
		  	thread_googl.start();
		  	
		  	while(latch.getCount() > 0) {	}
		  	
		    DefaultPieDataset dataset=new DefaultPieDataset();
		    dataset.setValue("RFEM", srh_rfem.getDouble("NumSearches", 0));		    
		    dataset.setValue("AAPL", srh_aapl.getDouble("NumSearches", 0));	    
		    dataset.setValue("GOOGL", srh_googl.getDouble("NumSearches", 0));
		    return dataset;
	  }catch(Exception e) {
		  e.printStackTrace();
		  return null;
	  }
  }
}