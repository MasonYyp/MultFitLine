package analysisData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javax.swing.JFrame;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;

public class LeastSquareDrawLineScatter extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName = null;
	private Double k = 0.0;
	private Double b = 0.0;
	
	public Double getK() {
		return k;
	}
	public void setK(Double k) {
		this.k = k;
	}
	public Double getB() {
		return b;
	}
	public void setB(Double b) {
		this.b = b;
	}

	public LeastSquareDrawLineScatter(String title, String fileName) {
		super(title);
		this.fileName = fileName;
		JFreeChart chart = createOverlaidChart();
        ChartPanel panel = new ChartPanel(chart, true, true, true, true, true);
        panel.setPreferredSize(new java.awt.Dimension(800, 500));
        setContentPane(panel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

    /**
     * Creates an overlaid chart.
     *
     * @return The chart.
     */
    private JFreeChart createOverlaidChart() {
    	// Get the data
    	LeastSquare ls = new LeastSquare();
		HashMap<String, ArrayList<Double>> XY = ls.readData(fileName);
		ArrayList<Double> X = XY.get("X");
		ArrayList<Double> Y = XY.get("Y");
		HashMap<String, Double> KB= ls.valuesKB(X, Y);
		this.setK(KB.get("K"));;
		this.setB(KB.get("B")); 
    	
    	
    	// Add first data set and rendererLine
        DefaultXYDataset dataLine = createDatasetLine(X, this.getK(), this.getB());
        XYItemRenderer rendererLine = new XYLineAndShapeRenderer();        
        NumberAxis XAxis = new NumberAxis("y:log(N)");
		NumberTickUnit Xunit=new NumberTickUnit(0.2);
		XAxis.setTickUnit(Xunit);
		
		NumberAxis YAxis = new NumberAxis("x:log(1/σ)");
		NumberTickUnit Yunit=new NumberTickUnit(0.5);
		YAxis.setTickUnit(Yunit);
        
        // create plot
        XYPlot plot = new XYPlot(dataLine, YAxis, XAxis, rendererLine);

        // Add second data set and rendererScatter
        DefaultXYDataset dataScatter = createDatasetScatter(X, Y);
        XYItemRenderer rendererScatter = new XYShapeRenderer();
        plot.setDataset(1, dataScatter);
        plot.setRenderer(1, rendererScatter);

        // return a new chart containing the overlaid plot
        return new JFreeChart("分形维数及拟合直线", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

    }

    /**
     * Generate the dataset
     * @return The dataset.
     */
    private DefaultXYDataset createDatasetLine(ArrayList<Double> X,Double k, Double b) {
    	// Get the min of X
    	Double maxX = Collections.max(X);
		Double minX = Collections.min(X);		
		Double maxY = k*maxX+b;
		Double minY = k*minX+b;
		
    	DefaultXYDataset lineDataset = new DefaultXYDataset();
    	double[][] data= {{minX,maxX},{minY,maxY}};
    	lineDataset.addSeries("拟合数据", data);
        return lineDataset;
    }
    
    private DefaultXYDataset createDatasetScatter(ArrayList<Double> X, ArrayList<Double> Y) {
    	//The list of one-dimensional convert to array of two-dimensional 
    	int size = X.size();
    	double[][] data= new double[2][size];
    	
    	for(int i=0; i<size; i++) {
    		data[0][i] = X.get(i);
    		data[1][i] = Y.get(i);
    	}
    	
    	DefaultXYDataset scatterDataset = new DefaultXYDataset();
		scatterDataset.addSeries("原始数据", data);
        return scatterDataset;
    }
}