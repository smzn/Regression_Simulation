package regression_simulation;

import java.util.Arrays;

public class Regression_Simulation_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double data_tr[][] = new double[549][5];//[time] : rising sp,headwind,distance,mach,temp diff
		
		MySQL mysql = new MySQL(1); //csv取り込み
		
		//ここから時間-上昇
		data_tr = mysql.getCSV("csv/data_time_rising.csv", 549, 5); //時間、上昇
		System.out.println("Data_time_rising = "+Arrays.deepToString(data_tr));
		
		Regression_Simulation_lib rlib = new Regression_Simulation_lib(data_tr);
		int number = 549; //シミュレーション発生数
		double mean[] = rlib.getMean(); //元データの平均値取得
		double cov[][] = rlib.getCov(); //元データの分散共分散行列取得
		double r[][] = rlib.getMvnrnd(mean, cov, number); //多変量正規乱数取得
		//int tip[] = rlib.getRand(number); //WingTip用0-1変数(一様乱数0.3未満で未使用0)
		double result_tr[] = rlib.getTimeRising(number, r);
		System.out.println("Mean = "+Arrays.toString(mean));
		System.out.println("Covariance = "+Arrays.deepToString(cov));
		System.out.println("r = " +Arrays.deepToString(r));
		//System.out.println("tip = "+Arrays.toString(tip));
		System.out.println("Result_tr = "+Arrays.toString(result_tr));
		//シミュレーション結果(時間)の平均値
		double average = 0;
		for(int i = 0; i < number; i++) average += result_tr[i];
		average /= number;
		System.out.println("Result_tr:Average = " + average );
		//実際の値との差を求める
		double data_tr_time[][] = new double[549][1];
		data_tr_time = mysql.getCSV("csv/data_time_rising_timevalue.csv", 549, 1); //時間、上昇の時間のみ
		System.out.println("Data_time_rising_time = "+Arrays.deepToString(data_tr_time));
		double diff = rlib.getEvaluate(data_tr_time, result_tr);
		System.out.println("Difference : Simulation - RealData = " + diff);
		//ここまで時間-上昇
	}

}
