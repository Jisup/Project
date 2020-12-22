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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class BankUpdate extends JFrame {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "Bank";
	private String pass = "Wltn1324";
	Connection conn = null;

	public BankUpdate(String type) {
		setTitle(type + "데이터수정");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
		setLayout(new BorderLayout());
		
		JPanel dnext = new JPanel();
		dnext.setLayout(new FlowLayout());
		switch (type) {
		case "Customer" :
			JLabel cno = new JLabel("업데이트할 고객 (번호) : ");
			JTextField c_no = new JTextField(20);
			JRadioButton ct1=new JRadioButton("이름"),ct2=new JRadioButton("주소"),
					ct3=new JRadioButton("번호"),ct4=new JRadioButton("구분");
			JLabel cm = new JLabel("변경할 내용 : ");
			JTextField c_m = new JTextField(30);
			JButton c_btn = new JButton("업데이트하기");
			
			dnext.add(cno); dnext.add(c_no);
			add(dnext, BorderLayout.NORTH);
			
			JPanel bnext = new JPanel();
			bnext.setLayout(new FlowLayout());
			bnext.add(ct1);
			bnext.add(ct2);
			bnext.add(ct3);
			bnext.add(ct4);
			
			JPanel tnext = new JPanel();
			tnext.setLayout(new BorderLayout());
			tnext.add(bnext, BorderLayout.CENTER);
			JPanel cnext = new JPanel();
			cnext.setLayout(new FlowLayout());
			cnext.add(cm); cnext.add(c_m);
			tnext.add(cnext, BorderLayout.SOUTH);
			add(tnext, BorderLayout.CENTER);
			add(c_btn, BorderLayout.SOUTH);
			
			c_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String ctype=null;
					if (ct1.isSelected()) {
						ctype="c_name";
					}
					else if (ct2.isSelected()) {
						ctype="c_address";
					}
					else if (ct3.isSelected()) {
						ctype="c_number";
					}
					else if (ct4.isSelected()) {
						ctype="c_type";
					}
					String numtext=c_no.getText();
					String mestext=c_m.getText();
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
						String myquery = "update Customer set "+ctype+"= '"+mestext+"' where c_no="+numtext;
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new upwindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
				}
			});
			break;
		case "Account" :
			break;
		case "Loan" :
			break;
		}
		setSize(640, 360);
		setVisible(true);
	}
}
class upwindow extends JFrame {
	public upwindow(int type) {
		if (type == 0) {
			setTitle("성공");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
			JPanel newwindowContainer = new JPanel();
			setContentPane(newwindowContainer);
			
			JLabel newlabel = new JLabel("업데이트완료.!");
			newwindowContainer.add(newlabel);
			
			setSize(200,100);
			setResizable(false);
			setVisible(true);
		}
	}
}