

/*
 * change ArcDialFrame labels so that they correspond to number of ticks 
 *	OR 
 * find out how to remove labes and make the inner background a transition of colors 
 * 	from green to orange then red (or something like that)
 *
 */

package mycharts;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.chart.plot.dial.DialBackground;
import org.jfree.chart.plot.dial.DialPlot;
import org.jfree.chart.plot.dial.DialPointer;
import org.jfree.chart.plot.dial.ArcDialFrame;
import org.jfree.chart.plot.dial.StandardDialScale;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;



public class UAVRadDial extends JFrame implements ChangeListener 
{

	// initialize components
	private JSlider slider;
	private DefaultValueDataset dataset;
	private DialPlot dialPlot = new DialPlot();
    	private ArcDialFrame dialFrame = new ArcDialFrame();
        private StandardDialScale scale = new StandardDialScale();
	private DialPointer needle;
	private JFreeChart dialChart;
	private ChartPanel chartPanel;
	private JPanel contentPanel;

	/*
	 * Creates a new instance.
	 *
	 * @param title the frame title.
	 */
	public UAVRadDial() 
	{

        
        	this.dataset = new DefaultValueDataset(50);
        	dialPlot.setView(0.0, 0.0, 1.0, 0.6);
        	dialPlot.setDataset(this.dataset);
        
        	dialFrame.setInnerRadius(0.40);
        	dialFrame.setOuterRadius(0.90);
        	dialFrame.setForegroundPaint(Color.darkGray);
        	dialFrame.setStroke(new BasicStroke(3.0f));
        	dialPlot.setDialFrame(dialFrame);
        
        	GradientPaint gp = new GradientPaint(new Point(),
        	        new Color(255, 255, 255), new Point(),
        	        new Color(240, 240, 240));
        	DialBackground sdb = new DialBackground(gp);
        	sdb.setGradientPaintTransformer(new StandardGradientPaintTransformer(
        	        GradientPaintTransformType.VERTICAL));
        	dialPlot.addLayer(sdb);
        	
        	scale.setTickRadius(0.88);
        	scale.setTickLabelOffset(0.07);
        	scale.setMajorTickIncrement(25.0);
        	dialPlot.addScale(0, scale);
       
        	needle = new DialPointer.Pin();
        	needle.setRadius(0.82);
        	dialPlot.addLayer(needle);
        	dialChart = new JFreeChart(dialPlot);
        	chartPanel = new ChartPanel(dialChart);
	
        	chartPanel.setPreferredSize(new Dimension(400, 250));
        	slider = new JSlider(0, 100);
        	slider.setMajorTickSpacing(10);
        	slider.setPaintLabels(true);
        	slider.addChangeListener(this);
        	contentPanel = new JPanel(new BorderLayout());
        	contentPanel.add(chartPanel);
        	contentPanel.add(slider, BorderLayout.SOUTH);
        	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	setContentPane(contentPanel);

	}


    
	/*
	 * Handle a change in the slider by updating the dataset value. This
	 * automatically triggers a chart repaint.
	 *
	 * @param e the event.
	 */
	public void stateChanged(ChangeEvent e) 
	{
	        this.dataset.setValue(new Integer(slider.getValue()));
	}

/*
     public static void main(String[] args) {

         UAVRadDial app = new UAVRadDial();
         app.pack();
         app.setVisible(true);

     }
*/


}
