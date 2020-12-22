package Bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class BankSearch {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "Bank";
	private String pass = "Wltn1324";
	public BankSearch(Connection conn, String type, int size, DefaultTableModel model, JTable state) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, id, pass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String row[] = new String[size];
		try {
			Statement stmt = conn.createStatement();
			String myquery = null;
			switch (type) {
			case "Customer" :
				myquery="select"+" c_no, c_name, c_address, c_number, c_type"+" from "+type;
				break;
			case "Account" :
				myquery="select "+"a_type, c_name, a_balance, a_set"+
						" from account, a_information, customer"+
						" where a_information.c_no=customer.c_no"+
						" and account.a_no=a_information.a_no";
				break;
			case "Loan" :
				myquery="select "+"l_type, c_name, l_amount, l_set"+
						" from loan, l_information, customer"+
						" where l_information.c_no=customer.c_no"+ 
						" and loan.l_no=l_information.l_no";
				break;
			}
			model.setNumRows(0);
			ResultSet rset = stmt.executeQuery(myquery);
			while (rset.next()) {
				for (int i = 1; i <= size; i++) {
					row[i - 1] = rset.getString(i);
				}
				model.addRow(row);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
