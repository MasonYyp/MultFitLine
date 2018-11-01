package analysisData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.ejml.data.DenseMatrix64F;
import org.ejml.ops.CommonOps;

public class MultFitLine {
	
	public ArrayList<ArrayList<Double>> readFile(String fileName){
//		fileName = "E:\\multiple_line.csv";
		ArrayList<ArrayList<Double>> datas = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> Ydata = new ArrayList<Double>();
		ArrayList<Double> X1data = new ArrayList<Double>();
		ArrayList<Double> X2data = new ArrayList<Double>();
		ArrayList<Double> X3data = new ArrayList<Double>();
		// Read the data file of csv
        String line = null;
        try {
        	BufferedReader reader = new BufferedReader(new FileReader(fileName));
        	reader.readLine();
			while((line=reader.readLine())!=null){
			   line = line.replaceAll("\"", "").replaceAll("\\s*", "");
			   String[] dataStr = line.split(",");
			   X1data.add(Double.valueOf(dataStr[0]));
			   X2data.add(Double.valueOf(dataStr[1]));
			   X3data.add(Double.valueOf(dataStr[2]));
			   Ydata.add(Double.valueOf(dataStr[3]));
			}
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        datas.add(X1data);
        datas.add(X2data);
        datas.add(X3data);
        datas.add(Ydata);
        return datas;	
	}
	
	//Get the values of kB 
	public ArrayList<Double> valuesKB(ArrayList<ArrayList<Double>> datas){
		// Get the data of X and Y
		ArrayList<Double> X1data = datas.get(0);
		ArrayList<Double> X2data = datas.get(1);
		ArrayList<Double> X3data = datas.get(2);
        ArrayList<Double> Ydata = datas.get(3);
        // Initial matrix 3*3
        DenseMatrix64F Xmatrix = new DenseMatrix64F(3,3);
        DenseMatrix64F Ymatrix = new DenseMatrix64F(3,1);
        double l11 = this.calXY(X1data);
        double l22 = this.calXY(X2data);
        double l33 = this.calXY(X3data);
        double l12 = this.calXY(X1data, X2data);
        double l13 = this.calXY(X1data, X3data);
        double l23 = this.calXY(X2data, X3data);
        
        ArrayList<Double> lY = new ArrayList<Double>();
        double l1y = this.calXY(X1data, Ydata);lY.add(l1y);
        double l2y = this.calXY(X2data, Ydata);lY.add(l2y);
        double l3y = this.calXY(X3data, Ydata);lY.add(l3y);
        double lyy = this.calXY(Ydata);
        
  
        Xmatrix.set(0,0,l11);Xmatrix.set(0,1,l12);Xmatrix.set(0,2,l13);
        Xmatrix.set(1,0,l12);Xmatrix.set(1,1,l22);Xmatrix.set(1,2,l23);
        Xmatrix.set(2,0,l13);Xmatrix.set(2,1,l23);Xmatrix.set(2,2,l33);
        
        Ymatrix.set(0,0,l1y);
        Ymatrix.set(1,0,l2y);
        Ymatrix.set(2,0,l3y);
        
        // The inverse of Xmatrix 
        DenseMatrix64F Xinverse= new DenseMatrix64F(3,3);
        CommonOps.invert(Xmatrix, Xinverse);
        
        DenseMatrix64F valuesK = new DenseMatrix64F(3,1);
        // The Xinverse multiple Ymatrix
        CommonOps.mult(Xinverse, Ymatrix, valuesK);
        ArrayList<Double> K = new ArrayList<Double>();
        K.add(valuesK.get(0,0));K.add(valuesK.get(1,0));K.add(valuesK.get(2,0));
        double R = this.calXY(lyy, lY, K);
        double b = calXY(X1data, X2data, X3data, Ydata, K);
        K.add(b);
        K.add(R);
        // K=[k1,k2,k3,b,R] 
		return K;
	}
	
	public double calXY(ArrayList<Double> X1, ArrayList<Double> X2) {
		double X1sum = 0;
		double X2sum = 0;
		double X1X2sum = 0;
		int Xlen = X1.size();
		for(int i=0; i< Xlen;i++) {
			X1sum = X1sum +X1.get(i);
			X2sum = X2sum +X2.get(i);
			X1X2sum = X1X2sum + X1.get(i)*X2.get(i);
		}		
		return X1X2sum-X1sum*X2sum/Xlen;
	}
	
	public double calXY(double lyy, ArrayList<Double> lY,  ArrayList<Double> K) {
		double lKYsum = 0;
		int lYlen = lY.size();
		for(int i=0; i<lYlen;i++) {
			lKYsum = lKYsum + lY.get(i)*K.get(i);
		}	
		return lKYsum/lyy;
	}
	
	public double calXY(ArrayList<Double> X1, ArrayList<Double> X2, ArrayList<Double> X3, ArrayList<Double> Y, ArrayList<Double> K) {
		double X1sum = 0;
		double X2sum = 0;
		double X3sum = 0;
		double KXsum = 0;		
		double Ysum = 0;
		
		ArrayList<Double> Xave = new ArrayList<Double>();
		int Xlen = X1.size();
		for(int i=0; i< Xlen;i++) {
			X1sum = X1sum +X1.get(i);
			X2sum = X2sum +X2.get(i);
			X3sum = X3sum +X3.get(i);
			Ysum = Ysum + Y.get(i);
		}
		Xave.add(X1sum/Xlen);Xave.add(X2sum/Xlen);Xave.add(X3sum/Xlen);
		for(int i=0; i< 3; i++) {
			KXsum = KXsum + K.get(i)*Xave.get(i);
		}
		return Ysum/Xlen-KXsum;
	}
	
	public double calXY(ArrayList<Double> X) {
		double Xsum = 0;
		double XXsum = 0;
		int Xlen = X.size();
		for(int i=0; i< Xlen;i++) {
			Xsum = Xsum +X.get(i);
			XXsum = XXsum + X.get(i)*X.get(i);
		}		
		return XXsum-Xsum*Xsum/Xlen;
	}   

}
