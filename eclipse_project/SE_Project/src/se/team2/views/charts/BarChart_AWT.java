package se.team2.views.charts;

import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset;

import se.team2.controllers.ServerRequestHandler; 

public class BarChart_AWT extends JFrame {
	private ServerRequestHandler srh;
   
   public BarChart_AWT( String applicationTitle , String chartTitle, ServerRequestHandler srh) {
      super( applicationTitle );        
      this.srh = srh;
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "Company",            
         "Value",            
         createDataset(),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 560 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   
   private CategoryDataset createDataset( ) {
	   try {
		      final String rfem = "RFEM";        
		      final String googl = "GOOGL";        
		      final String aapl = "AAPL";        
		      final String min = "min";        
		      final String max = "max";        
		      final String avg = "avg";        
		      final String median = "median";        
		      final DefaultCategoryDataset dataset = 
		      new DefaultCategoryDataset( );
		      
			  	ServerRequestHandler srh_rfem = new ServerRequestHandler();
			  	ServerRequestHandler srh_aapl = new ServerRequestHandler();
			  	ServerRequestHandler srh_googl = new ServerRequestHandler();
			  	
			  	CountDownLatch latch = new CountDownLatch(3);
			  	
			  	Thread thread_rfem = new Thread() {
			  		@Override
			  		public void run() {
			  			try {
			  			    srh_rfem.setParam("symbol", "rfem");
			  			    srh_rfem.setParam("date_start", srh.getParam("date_start"));
			  			    srh_rfem.setParam("date_end", srh.getParam("date_end"));
							srh_rfem.makeRequest(ServerRequestHandler.AGGREGATE_REQUEST);
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
			  			    srh_aapl.setParam("date_start", srh.getParam("date_start"));
			  			    srh_aapl.setParam("date_end", srh.getParam("date_end"));
							srh_aapl.makeRequest(ServerRequestHandler.AGGREGATE_REQUEST);
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
			  			    srh_googl.setParam("date_start", srh.getParam("date_start"));
			  			    srh_googl.setParam("date_end", srh.getParam("date_end"));
							srh_googl.makeRequest(ServerRequestHandler.AGGREGATE_REQUEST);
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

		      dataset.addValue( srh_rfem.getDouble(min, 0), rfem , min );     
		      dataset.addValue( srh_rfem.getDouble(max, 0), rfem , max ); 
		      dataset.addValue( srh_rfem.getDouble(avg, 0) , rfem , avg ); 
		      dataset.addValue( srh_rfem.getDouble(median, 0) , rfem , median );   

		      dataset.addValue( srh_googl.getDouble(min, 0), googl , min );     
		      dataset.addValue( srh_googl.getDouble(max, 0), googl , max ); 
		      dataset.addValue( srh_googl.getDouble(avg, 0) , googl , avg ); 
		      dataset.addValue( srh_googl.getDouble(median, 0) , googl , median ); 
		      
		      dataset.addValue( srh_aapl.getDouble(min, 0), aapl , min );     
		      dataset.addValue( srh_aapl.getDouble(max, 0), aapl , max ); 
		      dataset.addValue( srh_aapl.getDouble(avg, 0) , aapl , avg ); 
		      dataset.addValue( srh_aapl.getDouble(median, 0) , aapl , median );                

		      return dataset; 
	   }catch(Exception e) {
		   e.printStackTrace();
		   return null;
	   }
   }
}