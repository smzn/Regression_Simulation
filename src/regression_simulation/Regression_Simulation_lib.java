package regression_simulation;

import java.util.Random;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mathworks.engine.EngineException;
import com.mathworks.engine.MatlabEngine;
import com.mathworks.engine.MatlabExecutionException;
import com.mathworks.engine.MatlabSyntaxException;

public class Regression_Simulation_lib {
	Future<MatlabEngine> eng;
	MatlabEngine ml;
	double data[][];
	
	public Regression_Simulation_lib(double[][] data) {
		this.data = data;
		this.eng = MatlabEngine.startMatlabAsync();
		try {
			ml = eng.get();
			ml.putVariableAsync("data", data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public double[] getMean() {
		double[] result = null;
		try {
			ml.eval("mean_value = mean(data);");
			Future<double[]> futureEval_value = ml.getVariableAsync("mean_value");
			result = futureEval_value.get();			
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	
	//https://jp.mathworks.com/help/matlab/ref/cov.html
	public double[][] getCov() {
		double[][] result = null;
		try {
			ml.eval("cov_value = cov(data);");
			Future<double[][]> futureEval_cov_value = ml.getVariableAsync("cov_value");
			result = futureEval_cov_value.get();
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	//https://jp.mathworks.com/help/stats/mvnrnd.html
	public double[][] getMvnrnd(double[] mu, double[][] sigma, int number) {
		double r[][] = new double[number][mu.length];
		try {
			ml.putVariableAsync("mu", mu);
			ml.putVariableAsync("sigma", sigma);
			ml.putVariableAsync("number", number);
			ml.eval("r = mvnrnd(mu,sigma,number)");
			
			Future<double[][]> futureEval_r = ml.getVariableAsync("r");
			r = futureEval_r.get();
		} catch (MatlabExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MatlabSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CancellationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EngineException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}
	
	public int[] getRand(int number) {
		int result[] = new int[number];
		Random rnd = new Random();
		for(int i = 0; i < number; i++) {
			if( rnd.nextDouble() < 0.3 ) result[i] = 0;
			else result[i] = 1;
		}
		return result;
	}

	public double[] getTimeRising(int number, double[][] r) {
		double result[] = new double[number];
		for(int i = 0; i < number; i++ ) {
			result[i] = 183.37 + -1.1897 * r[i][0] + 0.95187 * r[i][1] + 8.5664 * r[i][2] + 263.05 * r[i][3] + 0.80431 * r[i][4] + -0.01458 * r[i][0] * r[i][1] + 0.0043344 * r[i][1] * r[i][2] + 7.0798 * r[i][1] * r[i][3] + -5.7388 * r[i][2] * r[i][3] + -0.0094304 * r[i][2] * r[i][4];
		}
		return result;
	}
	
	public double getEvaluate(double[][] realvalue, double[] predict) {//比較は実際のデータ数のみ
		double result = 0;
		for(int i = 0; i < realvalue.length; i++) result += predict[i] - realvalue[i][0];
		result /= realvalue.length;
		return result;
	}
	
}
