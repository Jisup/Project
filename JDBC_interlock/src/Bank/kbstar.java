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
	private String kbcusname[] = { "��ȣ", "�̸�", "�ּ�", "��ȭ��ȣ", "����" }; // Table Data list (Header data, adding row)
	private DefaultTableModel kbcusmodel = new DefaultTableModel(kbcusname, 0);// Create Table
	private JTable kbcustable = new JTable(kbcusmodel);

	private String kbaccname[] = { "���±���", "������",  "�ݾ�" , "������"}; // Table Data list (Header data, adding row)
	private DefaultTableModel kbaccmodel = new DefaultTableModel(kbaccname, 0);// Create Table
	private JTable kbacctable = new JTable(kbaccmodel);
	
	private String kblonname[] = { "���ⱸ��", "������", "�����", "��û��" }; // Table Data list (Header data, adding row)
	private DefaultTableModel kblonmodel = new DefaultTableModel(kblonname, 0);// Create Table
	private JTable kblontable = new JTable(kblonmodel);
	
	private JLabel kbcustext;
	private JLabel kbacctext;
	private JLabel kblontext;
	private JLabel kbtitletext;
	public kbstar(Connection conn) { // JFrame ������
		setTitle("KB�����������ͺ��̽��ý���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // BorderLayout�� Ȱ���� �� �� �Ϸ� ������Ʈ ��ġ
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
		kbtitletext.setText("KB��������");
		kbtitletext.setFont(new Font("Serif", Font.BOLD, 30));
		kbtitletext.setHorizontalAlignment(JLabel.CENTER);
		
		kbtitle.add(kbtitleicon, BorderLayout.WEST);
		kbtitle.add(kbtitletext, BorderLayout.CENTER);
		
		kbcustomer.setLayout(new BorderLayout());
		kbcustext = new JLabel();
		kbcustext.setText("������");
		kbcustext.setFont(new Font("Serif", Font.BOLD, 20));
		kbcustext.setHorizontalAlignment(JLabel.CENTER);
		kbcustomer.add(kbcustext, BorderLayout.NORTH);
		
		JPanel cusbtn = new JPanel();
		cusbtn.setLayout(new FlowLayout());
		JButton cusse = new JButton("�˻�");
		cusbtn.add(cusse);
		JButton cusin = new JButton("����");
		cusbtn.add(cusin);
		JButton cusde = new JButton("Ż��");
		cusbtn.add(cusde);
		JButton cusup = new JButton("����");
		cusbtn.add(cusup);
		kbcustomer.add(new JScrollPane(kbcustable), BorderLayout.CENTER);
		kbcustomer.add(cusbtn, BorderLayout.SOUTH);
		
		kbaccount.setLayout(new BorderLayout());
		kbacctext = new JLabel();
		kbacctext.setText("���°���");
		kbacctext.setFont(new Font("Serif", Font.BOLD, 20));
		kbacctext.setHorizontalAlignment(JLabel.CENTER);
		kbaccount.add(kbacctext, BorderLayout.NORTH);
		
		JPanel accbtn = new JPanel();
		accbtn.setLayout(new FlowLayout());
		JButton accse = new JButton("�˻�");
		accbtn.add(accse);
		JButton accin = new JButton("����");
		accbtn.add(accin);
		JButton accde = new JButton("���");
		accbtn.add(accde);
		JButton accup = new JButton("����");
		accbtn.add(accup);

		kbaccount.add(new JScrollPane(kbacctable), BorderLayout.CENTER);
		kbaccount.add(accbtn, BorderLayout.SOUTH);
		
		kbloan.setLayout(new BorderLayout());
		kblontext = new JLabel();
		kblontext.setText("�������");
		kblontext.setFont(new Font("Serif", Font.BOLD, 20));
		kblontext.setHorizontalAlignment(JLabel.CENTER);
		kbloan.add(kblontext, BorderLayout.NORTH);
		
		JPanel lonbtn = new JPanel();
		lonbtn.setLayout(new FlowLayout());
		JButton lonse = new JButton("�˻�");
		lonbtn.add(lonse);
		JButton lonin = new JButton("��û");
		lonbtn.add(lonin);
		JButton londe = new JButton("���");
		lonbtn.add(londe);
		JButton lonup = new JButton("����");
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