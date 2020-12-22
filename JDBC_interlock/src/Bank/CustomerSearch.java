package Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CustomerSearch {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "Bank";
	private String pass = "Wltn1324";
	
	Connection conn = null;
	public CustomerSearch(String type) {
//		try {
//			int numtype = Integer.parseInt(type);
//		}catch (NumberFormatException e) {
//			
//		}
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			String myquery = "select Count (c_no) from Customer"+" where c_no="+type;
			System.out.println(myquery);
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery(myquery);
			String result=null;
			while (rset.next()) {
					 result = rset.getString(1);
			}
			System.out.println(result);
			new newwindow(Integer.parseInt(result));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
class newwindow extends JFrame {
	public newwindow(int type) {
		if (type == 0) {
			setTitle("성공");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
			JPanel newwindowContainer = new JPanel();
			setContentPane(newwindowContainer);
			
			JLabel newlabel = new JLabel("사용가능한 아이디 입니다.!");
			newwindowContainer.add(newlabel);
			
			setSize(200,100);
			setResizable(false);
			setVisible(true);
		}
		else if (type == 1){
			setTitle("실패");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
			JPanel newwindowContainer = new JPanel();
			setContentPane(newwindowContainer);
			
			JLabel newlabel = new JLabel("존재하는아이디입니다.!");
			newwindowContainer.add(newlabel);
			
			setSize(200,100);
			setResizable(false);
			setVisible(true);
		}
	}
}