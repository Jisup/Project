package Bank;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
class takewindow extends JFrame {
	public takewindow(int type) {
		if (type == 0) {
			setTitle("성공");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
			JPanel newwindowContainer = new JPanel();
			setContentPane(newwindowContainer);
			
			JLabel newlabel = new JLabel("제출되었습니다.!");
			newwindowContainer.add(newlabel);
			
			setSize(200,100);
			setResizable(false);
			setVisible(true);
		}
	}
}
class BankInsert extends JFrame implements ActionListener {
//	public BankInsert(Connection conn, String type) {
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "Bank";
	private String pass = "Wltn1324";
	Connection conn = null;
	public BankInsert(String type) {
		setTitle(type + "데이터 입력");

		switch (type) {
		case "Customer":
			setLayout(new GridLayout(6, 1));

			JLabel cno, cname, caddress, cphone, ctype;
			JTextField c_no, c_name, c_address, c_phone;
			JRadioButton ctype1, ctype2;
			JButton c_btn, csubmit;

			cno = new JLabel("고객 (번호) : ");
			c_no = new JTextField(20);
			c_btn = new JButton("아이디확인");
			
			c_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String number = c_no.getText();
					new CustomerSearch(number);
				}
			});
			JPanel cnext = new JPanel();
			cnext.setLayout(new FlowLayout());
			cnext.add(cno);
			cnext.add(c_no);
			cnext.add(c_btn);
			add(cnext);

			cname = new JLabel("고객  이름 : ");
			c_name = new JTextField(30);
			JPanel cnext1 = new JPanel();
			cnext1.setLayout(new FlowLayout());
			cnext1.add(cname);
			cnext1.add(c_name);
			add(cnext1);

			caddress = new JLabel("주소 (간결) : ");
			c_address = new JTextField(30);
			JPanel cnext2 = new JPanel();
			cnext2.setLayout(new FlowLayout());
			cnext2.add(caddress);
			cnext2.add(c_address);
			add(cnext2);

			cphone = new JLabel("번호(-표기) : ");
			c_phone = new JTextField(30);
			JPanel cnext3 = new JPanel();
			cnext3.setLayout(new FlowLayout());
			cnext3.add(cphone);
			cnext3.add(c_phone);
			add(cnext3);

			ctype = new JLabel("구분 : ");
			ctype1 = new JRadioButton("내국인");
			ctype2 = new JRadioButton("외국인");
			
			ButtonGroup ctypegroup = new ButtonGroup();
			ctypegroup.add(ctype1);
			ctypegroup.add(ctype2);
			
			JPanel cnext4 = new JPanel();
			cnext4.setLayout(new FlowLayout());
			cnext4.add(ctype);
			cnext4.add(ctype1);
			cnext4.add(ctype2);
			add(cnext4);

			csubmit = new JButton("제출");
			
			csubmit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String customernumber = c_no.getText();
					String customername = c_name.getText();
					String customeradd = c_address.getText();
					String customerphone = c_phone.getText();
					String customertype = null;
					if (ctype1.isSelected()) {
						customertype = "내국인";
					}
					else if (ctype2.isSelected()) {
						customertype = "외국인";
					}
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
						String myquery = "Insert Into Customer(c_no, c_name, c_type, c_address, c_number)"+
									" values( '"+customernumber+"','"+customername+
									"','"+customertype+"','"+customeradd+"','"+customerphone+"')";
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new takewindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
				}
			});
			add(csubmit);
			break;
		case "Account":
			setLayout(new GridLayout(5, 1));

			JLabel acno,aano, pno, alimit;
			JTextField ac_no, a_limit;
			JRadioButton atype1, atype2, atype3, ptype1, ptype2, ptype3;
			JButton ac_btn, asubmit;

			acno = new JLabel("고객 (번호) : ");
			ac_no = new JTextField(20);
			ac_btn = new JButton("아이디확인");
			
			ac_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String cusid = ac_no.getText();
					new CustomerSearch(cusid);
				}
			});
			
			JPanel anext = new JPanel();
			anext.setLayout(new FlowLayout());
			anext.add(acno);
			anext.add(ac_no);
			anext.add(ac_btn);
			add(anext);
			
			aano=new JLabel("계좌 선택 : ");
			atype1 = new JRadioButton("예금");
			atype2 = new JRadioButton("적금");
			atype3 = new JRadioButton("입출금");
			
			ButtonGroup atypegroup = new ButtonGroup();
			atypegroup.add(atype1);
			atypegroup.add(atype2);
			atypegroup.add(atype3);
			
			JPanel anext1 = new JPanel();
			anext1.setLayout(new FlowLayout());
			anext1.add(aano);
			anext1.add(atype1);
			anext1.add(atype2);
			anext1.add(atype3);
			add(anext1);

			pno = new JLabel("카드  선택 : ");
			ptype1 = new JRadioButton("체크카드");
			ptype2 = new JRadioButton("신용카드");
			ptype3 = new JRadioButton("마일리지카드");
			
			ButtonGroup ptypegroup = new ButtonGroup();
			ptypegroup.add(ptype1);
			ptypegroup.add(ptype2);
			ptypegroup.add(ptype3);
			
			JPanel pnext1 = new JPanel();
			pnext1.setLayout(new FlowLayout());
			pnext1.add(pno);
			pnext1.add(ptype1);
			pnext1.add(ptype2);
			pnext1.add(ptype3);
			add(pnext1);

			alimit = new JLabel("한도 지정 : ");
			a_limit = new JTextField(30);
			JPanel alnext = new JPanel();
			alnext.setLayout(new FlowLayout());
			alnext.add(alimit);alnext.add(a_limit);
			add(alnext);
			
			asubmit = new JButton("제출");
			
			asubmit.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String customernumber =ac_no.getText();
					String cardtext = null;
					String accounttext;
					String limittext = a_limit.getText();

					LocalDate now = LocalDate.now();
					try {
					}catch(NumberFormatException n) {
						
					}
					if (ptype1.isSelected())
						cardtext = "4331";
					else if (ptype2.isSelected())
						cardtext = "212131";
					else if (ptype3.isSelected())
						cardtext = "42115131";
					if (atype1.isSelected())
						accounttext = "14214";
					else if (atype2.isSelected())
						accounttext = "122214";
					else if (atype3.isSelected())
						accounttext = "1144257";
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
						String myquery = "Insert Into P_information"+
									" values( '"+cardtext+"','"+customernumber+
									"','"+now+"','"+now+"')";
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new takewindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
				}
			});
			add(asubmit);
			break;
			
		case "Loan":
			setLayout(new GridLayout(4,1));
		
			JLabel lview, lno, llimit, lcno;
			JTextField l_limit, lc_no;
			JRadioButton ltype1, ltype2, ltype3, ltype4, ltype5;
			JButton lc_btn, l_btn, lsubmit;
		
			lcno = new JLabel("고객 (번호) : ");
			lc_no = new JTextField(20);
			lc_btn = new JButton("아이디확인");
			
			lc_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String number = lc_no.getText();
					new CustomerSearch(number);
				}
			});
			JPanel lnext = new JPanel();
			lnext.setLayout(new FlowLayout());
			lnext.add(lcno);
			lnext.add(lc_no);
			lnext.add(lc_btn);
			add(lnext);
			
			lno = new JLabel("대출 선택 : ");
			ltype1 = new JRadioButton("장기대출");
			ltype2 = new JRadioButton("전세대출");
			ltype3 = new JRadioButton("담보대출");
			ltype4 = new JRadioButton("신용대출");
			ltype5 = new JRadioButton("자동차대출");
			
			ButtonGroup ltypegroup = new ButtonGroup();
			ltypegroup.add(ltype1);
			ltypegroup.add(ltype2);
			ltypegroup.add(ltype3);
			ltypegroup.add(ltype4);
			ltypegroup.add(ltype5);
			
			JPanel lnext2 = new JPanel();
			lnext2.setLayout(new FlowLayout());
			lnext2.add(lno);
			lnext2.add(ltype1);
			lnext2.add(ltype2);
			lnext2.add(ltype3);
			lnext2.add(ltype4);
			lnext2.add(ltype5);
			add(lnext2);
			
			llimit= new JLabel("대출신청금액  : ");
			l_limit = new JTextField(30);
			l_btn = new JButton("등급별 대출금액보기");
			
			l_btn.addActionListener(new ActionListener() {
				private String lblonname[] = { "신용등급", "대출한도"}; // Table Data list (Header data, adding row)
				private DefaultTableModel lblonmodel = new DefaultTableModel(lblonname, 0);// Create Table
				private JTable lblontable = new JTable(lblonmodel);
				private String row[] = new String[2];
				@Override
				public void actionPerformed(ActionEvent e) {
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
						String myquery = "select * from Grade";
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						while (rset.next()) {
							// row 값 읽기
							for (int i = 1; i < 3; i++) {
								System.out.print(rset.getString(i) + "\t");
								row[i - 1] = rset.getString(i);
							}
							System.out.println();
							// JTable에 출력
							lblonmodel.addRow(row);
						}
					} catch (SQLException d) {
						d.printStackTrace();
					}
					new GradeShow(lblontable);
				}
			});
			JPanel lnext1 = new JPanel();
			lnext1.setLayout(new FlowLayout());
			lnext1.add(llimit);
			lnext1.add(l_limit);
			lnext1.add(l_btn);
			add(lnext1);

			lsubmit = new JButton("제출");
		
			lsubmit.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String customernumber =lc_no.getText();
					String cardtext = null;
					LocalDate now = LocalDate.now();
					if (ltype1.isSelected()) {
						cardtext="32121";
					}
					else if (ltype2.isSelected()) {
						cardtext="32223";
					}
					else if (ltype3.isSelected()) {
						cardtext="32442";
					}
					else if (ltype4.isSelected()) {
						cardtext="212131";
					}
					else if (ltype5.isSelected()) {
						cardtext="3232142";
					}
					String limittext = l_limit.getText();
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
						String myquery = "Insert Into L_information"+
									" values( '"+cardtext+"','"+customernumber+
									"','"+now+"','"+limittext+"')";
						System.out.println(myquery);
						Statement stmt = conn.createStatement();
						ResultSet rset = stmt.executeQuery(myquery);
						new takewindow(0);
					} catch (SQLException d) {
						d.printStackTrace();
					}
					new takewindow(0);
				}
			});
			add(lsubmit);
			break;
		}

		setSize(640, 240);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
/**
 * 값가져올때 TextField 값 가져올때 getText();
 * 
 */
