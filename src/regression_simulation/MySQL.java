package regression_simulation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;

public class MySQL {
	String driver;// JDBCドライバの登録
    String server, dbname, url, user, password;// データベースの指定
    Connection con;
    Statement stmt;
    ResultSet rs;
    private double data[][];
    
	public MySQL( ) { //データベース接続時
		this.driver = "org.gjt.mm.mysql.Driver";
        this.server = "naisyo.sist.ac.jp";
        this.dbname = "naisyo";
        this.url = "jdbc:mysql://" + server + "/" + dbname + "?useUnicode=true&characterEncoding=UTF-8";
        this.user = "naisyo";
        this.password = "naisyo";
        this.data = new double[15][6];
        try {
            this.con = DriverManager.getConnection(url, user, password);
            this.stmt = con.createStatement ();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            Class.forName (driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
	}
	
	public MySQL(int flg) {} //csvで取り込む場合
	
	public double[][] selectData() {
		String sql = "SELECT (`age0to4`+`age5to9`+`age10to14`+`age15to19`) as `age0to19`, (`age20to24`+`age25to29`+`age30to34`+`age35to39`) as `age20to39`, (`age40to44`+`age45to49`+`age50to54`+`age55to59`) as `age40to59`, (`age60to64`+`age65to69`+`age70to74`+`age75to79`) as `age60to79`, (`age80to84`+`age85over`) as `age80over`, `house`FROM `populations` order by `total` desc limit 15";
		try {
			ResultSet rs = stmt.executeQuery(sql);
			int i = 0;
			while(rs.next()){
				data[i][0] = rs.getInt("age0to19");
				data[i][1] = rs.getInt("age20to39");
				data[i][2] = rs.getInt("age40to59");
				data[i][3] = rs.getInt("age60to79");
				data[i][4] = rs.getInt("age80over");
				data[i][5] = rs.getInt("house");
				i++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}
    
	public double[][] getCSV(String path, int row, int column) {
		//CSVから取り込み
		data = new double[row][column];
		try {
			File f = new File(path);
			BufferedReader br = new BufferedReader(new FileReader(f));
			String[][] tmp = new String[row][column]; 	 
			String line = br.readLine();
			for (int i = 0; line != null; i++) {
				tmp[i] = line.split(",", 0);
				line = br.readLine();
			}
			br.close();

			// CSVから読み込んだ配列の中身を処理
			for(int i = 0; i < tmp.length; i++) {
				for(int j = 0; j < tmp[0].length; j++) {
					data[i][j] = Double.parseDouble(tmp[i][j]);
				}
			} 

		} catch (IOException e) {
			System.out.println(e);
		}
		//CSVから取り込みここまで
		return data;
	}

}
