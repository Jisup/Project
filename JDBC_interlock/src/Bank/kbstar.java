package Bank;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class kbstar extends JFrame {
	private JLabel kbstarChoice; // Table Header Name
	private String kbcusname[] = { "번호", "이름", "주소", "전화번호", "구분" }; // Table Data list (Header data, adding row)
	private DefaultTableModel kbcusmodel = new DefaultTableModel(kbcusname, 0);// Create Table
	private JTable kbcustable = new JTable(kbcusmodel);

	private String kbaccname[] = { "계좌구분", "소유자",  "금액" , "생성일"}; // Table Data list (Header data, adding row)
	private DefaultTableModel kbaccmodel = new DefaultTableModel(kbaccname, 0);// Create Table
	private JTable kbacctable = new JTable(kbaccmodel);
	
	private String kblonname[] = { "대출구분", "대출자", "대출액", "신청일" }; // Table Data list (Header data, adding row)
	private DefaultTableModel kblonmodel = new DefaultTableModel(kblonname, 0);// Create Table
	private JTable kblontable = new JTable(kblonmodel);
	
	private JLabel kbcustext;
	private JLabel kbacctext;
	private JLabel kblontext;
	private JLabel kbtitletext;
	public kbstar(Connection conn) { // JFrame 생성자
		setTitle("KB국민은헹데이터베이스시스템");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // BorderLayout을 활용해 상 중 하로 컴포넌트 위치
		setLayout(new BorderLayout(25, 50));
		JPanel kbcustomer = new JPanel();
		JPanel kbaccount = new JPanel();
		JPanel kbloan = new JPanel();

		JPanel kbtitle = new JPanel();
		kbtitle.setLayout(new BorderLayout());
		
		ImageIcon kbicon = new ImageIcon("KBstar.jpg");
		Image originImg = kbicon.getImage();
		Image changedImg =originImg.getScaledInstance(115, 115, Image.SCALE_SMOOTH);
		ImageIcon kb_icon = new ImageIcon(changedImg);
		JLabel kbtitleicon = new JLabel(kb_icon);
		
		kbtitletext = new JLabel();
		kbtitletext.setText("KB국민은행");
		kbtitletext.setFont(new Font("Serif", Font.BOLD, 30));
		kbtitletext.setHorizontalAlignment(JLabel.CENTER);
		
		kbtitle.add(kbtitleicon, BorderLayout.WEST);
		kbtitle.add(kbtitletext, BorderLayout.CENTER);
		
		kbcustomer.setLayout(new BorderLayout());
		kbcustext = new JLabel();
		kbcustext.setText("고객관리");
		kbcustext.setFont(new Font("Serif", Font.BOLD, 20));
		kbcustext.setHorizontalAlignment(JLabel.CENTER);
		kbcustomer.add(kbcustext, BorderLayout.NORTH);
		
		JPanel cusbtn = new JPanel();
		cusbtn.setLayout(new FlowLayout());
		JButton cusse = new JButton("검색");
		cusbtn.add(cusse);
		JButton cusin = new JButton("가입");
		cusbtn.add(cusin);
		JButton cusde = new JButton("탈퇴");
		cusbtn.add(cusde);
		JButton cusup = new JButton("수정");
		cusbtn.add(cusup);
		kbcustomer.add(new JScrollPane(kbcustable), BorderLayout.CENTER);
		kbcustomer.add(cusbtn, BorderLayout.SOUTH);
		
		kbaccount.setLayout(new BorderLayout());
		kbacctext = new JLabel();
		kbacctext.setText("계좌관리");
		kbacctext.setFont(new Font("Serif", Font.BOLD, 20));
		kbacctext.setHorizontalAlignment(JLabel.CENTER);
		kbaccount.add(kbacctext, BorderLayout.NORTH);
		
		JPanel accbtn = new JPanel();
		accbtn.setLayout(new FlowLayout());
		JButton accse = new JButton("검색");
		accbtn.add(accse);
		JButton accin = new JButton("개설");
		accbtn.add(accin);
		JButton accde = new JButton("폐기");
		accbtn.add(accde);
		JButton accup = new JButton("수정");
		accbtn.add(accup);

		kbaccount.add(new JScrollPane(kbacctable), BorderLayout.CENTER);
		kbaccount.add(accbtn, BorderLayout.SOUTH);
		
		kbloan.setLayout(new BorderLayout());
		kblontext = new JLabel();
		kblontext.setText("대출관리");
		kblontext.setFont(new Font("Serif", Font.BOLD, 20));
		kblontext.setHorizontalAlignment(JLabel.CENTER);
		kbloan.add(kblontext, BorderLayout.NORTH);
		
		JPanel lonbtn = new JPanel();
		lonbtn.setLayout(new FlowLayout());
		JButton lonse = new JButton("검색");
		lonbtn.add(lonse);
		JButton lonin = new JButton("신청");
		lonbtn.add(lonin);
		JButton londe = new JButton("취소");
		lonbtn.add(londe);
		JButton lonup = new JButton("수정");
		lonbtn.add(lonup);

		kbloan.add(new JScrollPane(kblontable), BorderLayout.CENTER);
		kbloan.add(lonbtn, BorderLayout.SOUTH);
		
		add(kbtitle, BorderLayout.NORTH);
		add(kbcustomer, BorderLayout.WEST);
		add(kbaccount, BorderLayout.CENTER);
		add(kbloan, BorderLayout.EAST);
		cusse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankSearch cse = new BankSearch(conn, "Customer", 5, kbcusmodel, kbcustable);
			}
		});
		cusin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankInsert cin = new BankInsert("Customer");
			}
		});
		cusde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankDelete cde = new BankDelete("Customer");
			}
		});
		cusup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankUpdate cup = new BankUpdate("Customer");
			}
		});
		accse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankSearch ase = new BankSearch(conn, "Account", 4, kbaccmodel, kbacctable);
			}
		});
		accin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankInsert ain = new BankInsert("Account");
			}
		});
		accde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankDelete ade = new BankDelete("Account");
			}
		});
		accup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankUpdate aup = new BankUpdate("Account");
			}
		});
		lonse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankSearch lse = new BankSearch(conn, "Loan", 4, kblonmodel, kblontable);
			}
		});
		lonin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankInsert lin = new BankInsert("Loan");
			}
		});
		londe.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankDelete lde = new BankDelete("Loan");
			}
		});
		lonup.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BankUpdate lup = new BankUpdate("Loan");
			}
		});
		setSize(1340, 720);
		setVisible(true);
	}
}