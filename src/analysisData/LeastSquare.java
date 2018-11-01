package analysisData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class LeastSquare {
	
	/**
	* The least square method
	* The line: y = k*x + b
	* k = sum((X-aveX)*(Y-aveY))/sum((X-aveX)^2)
	* b = aveY - k*aveX
	*/

	
	// Calculate the k and b of line
	public HashMap<String, Double> valuesKB(ArrayList<Double> X, ArrayList<Double> Y) {
		HashMap<String, Double> KB = new HashMap<String, Double>();
		
		// Calculate the sum
		int nX = 0;
		double sumX = 0.0, sumY = 0.0, sumXSquare = 0.0;
		while (nX < X.size()) {
			sumX = sumX + X.get(nX);
			sumXSquare =sumXSquare + X.get(nX) * X.get(nX);
			sumY = sumY + Y.get(nX);
			nX++;
		}
		
		// Calculate the average
		double aveX = sumX / nX;
		double aveY = sumY / nX;
		
		// 计算系数
		double XXAve = 0.0, XYAve = 0.0;
		for (int j = 0; j < nX; j++) {
			XXAve = XXAve + (X.get(j) - aveX) * (X.get(j) - aveX);
			XYAve = XYAve + (X.get(j) - aveX) * (Y.get(j) - aveY);
		}
		
		double k = XYAve/XXAve;
		double b = aveY - k * aveX;
		KB.put("K", k);
		KB.put("B", b);
		return KB;		
	}

	
	// Read the file of data
	public HashMap<String, ArrayList<Double>> readData(String fileName){
		 ArrayList<Double> X = new ArrayList<Double>();
         ArrayList<Double> Y = new ArrayList<Double>();
         HashMap<String, ArrayList<Double>> XY = new HashMap<String, ArrayList<Double>>();
         
		try {  
			// Read the data file of csv
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            // Read the first line of data
            // reader.readLine();
            
            //Read first line
            String xLine = reader.readLine();
            //Read second line
            String yLine = reader.readLine();
            
            xLine = xLine.replaceAll("\"", "").replaceAll("\\s*", "");
            yLine = yLine.replaceAll("\"", "").replaceAll("\\s*", "");
            
            String[] XStr = xLine.split(",");
            String[] YStr = yLine.split(",");
                       
            int i = 0;
            for(i=0 ; i<XStr.length ; i++) {
            	X.add(Double.valueOf(XStr[i]));
            	Y.add(Double.valueOf(YStr[i]));
            }
            
/*
            while((line=reader.readLine())!=null){
                //Remove the space at the beginning and end of the string 
                //System.out.println(line.str.trim()); 

                //Remove all the space in the string
            	//System.out.println(line.replaceAll(" ", ""));
            	
                //Remove all the space in the string
            	//System.out.println(line.replaceAll(" +", ""));   

               	// Remove all quotes in the sting 
            	line = line.replaceAll("\"", "");
            	// Remove all space and blank
            	line = line.replaceAll("\\s*", "");
            	// Split the line
                String[] items = line.split(",");
                
                System.out.println(line);  
            }
*/ 
        } catch (Exception e) {
        	e.printStackTrace();
        }
		XY.put("X", X);
		XY.put("Y", Y);
		return XY;
	}
}
