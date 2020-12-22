package Bank;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BankDelete extends JFrame {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "Bank";
	private String pass = "Wltn1324";
	Connection conn = null;

	public BankDelete(String type) {
		setTitle(type + "데이터삭제");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setLayout(new BorderLayout());
		
		JPanel dnext = new JPanel();
		dnext.setLayout(new FlowLayout());
		switch (type) {
		case "Customer" :			
			
			JLabel cno = new JLabel("삭제하고싶은 고객 (번호) : ");
			JTextField c_no = new JTextField(20);
			JButton c_btn = new JButton("삭제하기");
			dnext.add(cno); dnext.add(c_no);
			add(dnext, BorderLayout.CENTER);
			add(c_btn, BorderLayout.SOUTH);
			
			c_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String numtext=c_no.getText();
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn = DriverManager.getConnection(url, id, pass);
					} catch (ClassNotFoundException p) {
						p.printStackTrace();
					} catch (SQLException p) {
						p.printStackTrace();
					}
					try {
						Calendar cal = Calendar.getInstance();
						String myquery = "Delete Customer where c_no="+numtext;
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new dewindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
				}
			});
			break;
		case "Account" :
			JLabel ano = new JLabel("삭제하고싶은 계좌 (번호) : ");
			JTextField a_no = new JTextField(20);
			JButton a_btn = new JButton("삭제하기");
			dnext.add(ano); dnext.add(a_no);
			add(dnext, BorderLayout.CENTER);
			add(a_btn, BorderLayout.SOUTH);
			
			a_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String numtext=a_no.getText();
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn = DriverManager.getConnection(url, id, pass);
					} catch (ClassNotFoundException p) {
						p.printStackTrace();
					} catch (SQLException p) {
						p.printStackTrace();
					}
					try {
						Calendar cal = Calendar.getInstance();
						String myquery = "Delete Account where a_no="+numtext;
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new dewindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
				}
			});
			break;
		case "Loan" :
			JLabel lno = new JLabel("삭제하고싶은 대출 (번호) : ");
			JTextField l_no = new JTextField(20);
			JButton l_btn = new JButton("삭제하기");
			dnext.add(lno); dnext.add(l_no);
			add(dnext, BorderLayout.CENTER);
			add(l_btn, BorderLayout.SOUTH);
			l_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String numtext=l_no.getText();
					try {
						Class.forName("oracle.jdbc.driver.OracleDriver");
						conn = DriverManager.getConnection(url, id, pass);
					} catch (ClassNotFoundException p) {
						p.printStackTrace();
					} catch (SQLException p) {
						p.printStackTrace();
					}
					try {
						Calendar cal = Calendar.getInstance();
						String myquery = "Delete Loan where l_no="+numtext;
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new dewindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
				}
			});
			break;
		}
		setSize(640,360);
		setVisible(true);
	}
}
class dewindow extends JFrame {
	public dewindow(int type) {
		if (type == 0) {
			setTitle("성공");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
			JPanel newwindowContainer = new JPanel();
			setContentPane(newwindowContainer);
			
			JLabel newlabel = new JLabel("삭제되었습니다.!");
			newwindowContainer.add(newlabel);
			
			setSize(200,100);
			setResizable(false);
			setVisible(true);
		}
	}
}