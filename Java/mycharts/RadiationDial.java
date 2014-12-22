
package mycharts;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.dial.*;
import org.jfree.chart.plot.dial.DialPointer.*;
import org.jfree.chart.plot.dial.StandardDialFrame;
import org.jfree.data.general.DefaultValueDataset;
import org.jfree.ui.GradientPaintTransformType;
import org.jfree.ui.StandardGradientPaintTransformer;

public class RadiationDial extends JFrame
{
        static class RadPanel extends JPanel
                implements ChangeListener
        {


		private DialPlot dialplot = new DialPlot();
                private ArcDialFrame arcdialframe = new ArcDialFrame();
                private Pin pin = new Pin();
                private JFreeChart jfreechart = new JFreeChart(dialplot);
                private ChartPanel chartpanel = new ChartPanel(jfreechart);
                private JSlider slider = new JSlider(0, 100);

                DefaultValueDataset dataset;

                public void stateChanged(ChangeEvent changeevent)
                {
                        dataset.setValue(new Integer(slider.getValue()));
                }

                public RadPanel()
                {
                        super(new BorderLayout());
                        dataset = new DefaultValueDataset(50);
                        dialplot.setView(0.0, 0.0, 1.0, 0.6);
                        dialplot.setDataset(dataset);
                        arcdialframe.setInnerRadius(0.4);
                        arcdialframe.setOuterRadius(0.9);
                        arcdialframe.setForegroundPaint(Color.DARK_GRAY);
                        arcdialframe.setStroke(new BasicStroke(3));
                        dialplot.setDialFrame(arcdialframe);


                       

                        StandardDialScale standarddialscale = new StandardDialScale();
                        standarddialscale.setTickRadius(0.88);
                        standarddialscale.setTickLabelOffset(0.07);
                        standarddialscale.setMajorTickIncrement(25);
                        dialplot.addScale(0, standarddialscale);
                        pin.setRadius(0.82);
                        dialplot.addLayer(pin);
                        chartpanel.setPreferredSize(new Dimension(400, 250));
                        slider.setMajorTickSpacing(10);
                        slider.setPaintLabels(true);
                        slider.addChangeListener(this);
                        add(chartpanel);
                        add(slider, "South");
                }
        }


        public static JPanel createRadPanel()
        {
                return new RadPanel();
        }

        public RadiationDial()
        {
                setDefaultCloseOperation(3);
                setContentPane(createRadPanel());
        }


}

