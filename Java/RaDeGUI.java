


import mycharts.UAVCompass;
import mycharts.UAVRadDial;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CompassPlot;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.Spinner;
import java.awt.GridBagLayout;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JFrame;
import javax.swing.JPanel;




public class RaDeGUI
{

	private static final int WINDOW_WIDTH = 1200;
	private static final int WINDOW_HEIGHT = 700;



	public static void main(String[] args)
	{


		// initialize chart components
        	UAVCompass compass = new UAVCompass();
		UAVRadDial radiationDial = new UAVRadDial();
		radiationDial.setVisible(true);
		JPanel radDialPanel = new JPanel();

		// initialize frame
        	JFrame guiFrame = new JFrame();
		guiFrame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        	guiFrame.getContentPane().setLayout(new GridBagLayout());
        	guiFrame.setDefaultCloseOperation(3);
        	guiFrame.setTitle("RaDe GUI");
        	Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        	guiFrame.setLocation((d.width - guiFrame.getSize().width) / 2, (d.height - guiFrame.getSize().height) / 2);
        	guiFrame.setVisible(true);


		// combine components together
		//guiFrame.getContentPane().add(compass, BorderLayout.CENTER);
		guiFrame.getContentPane().add(radiationDial, BorderLayout.CENTER);

	        
	}

}
