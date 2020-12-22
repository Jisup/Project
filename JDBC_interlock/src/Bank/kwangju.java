package Bank;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
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

public class kwangju extends JFrame {
	private JLabel kjstarChoice; // Table Header Name
	private String kjcusname[] = { "��ȣ", "�̸�", "�ּ�", "��ȭ��ȣ", "����" }; // Table Data list (Header data, adding row)
	private DefaultTableModel kjcusmodel = new DefaultTableModel(kjcusname, 0);// Create Table
	private JTable kjcustable = new JTable(kjcusmodel);
	private String kjcusrow[] = new String[5];

	private String kjaccname[] = { "���¹�ȣ", "������",  "�ݾ�" , "������"}; // Table Data list (Header data, adding row)
	private DefaultTableModel kjaccmodel = new DefaultTableModel(kjaccname, 0);// Create Table
	private JTable kjacctable = new JTable(kjaccmodel);
	private String kjaccrow[] = new String[4];
	
	private String kjlonname[] = { "�����ȣ", "������", "�����", "��û��" }; // Table Data list (Header data, adding row)
	private DefaultTableModel kjlonmodel = new DefaultTableModel(kjlonname, 0);// Create Table
	private JTable kjlontable = new JTable(kjlonmodel);
	private String kjlonrow[] = new String[4];
	
	private JLabel kjcustext;
	private JLabel kjacctext;
	private JLabel kjlontext;
	private JLabel kjtitletext;
	public kwangju(Connection conn) { // JFrame ������
		setTitle("�������൥���ͺ��̽��ý���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // BorderLayout�� Ȱ���� �� �� �Ϸ� ������Ʈ ��ġ
		setLayout(new BorderLayout(25, 50));
		JPanel kjcustomer = new JPanel();
		JPanel kjaccount = new JPanel();
		JPanel kjloan = new JPanel();
		
		JPanel kjtitle = new JPanel();
		kjtitle.setLayout(new BorderLayout());
		
		ImageIcon kjicon = new ImageIcon("Kwangju.jpg");
		Image originImg = kjicon.getImage();
		Image changedImg =originImg.getScaledInstance(115, 115, Image.SCALE_SMOOTH);
		ImageIcon kj_icon = new ImageIcon(changedImg);
		JLabel kjtitleicon = new JLabel(kj_icon);
		
		kjtitletext = new JLabel();
		kjtitletext.setText("��������");
		kjtitletext.setFont(new Font("Serif", Font.BOLD, 30));
		kjtitletext.setHorizontalAlignment(JLabel.CENTER);
		
		kjtitle.add(kjtitleicon, BorderLayout.WEST);
		kjtitle.add(kjtitletext, BorderLayout.CENTER);
		
		kjcustomer.setLayout(new BorderLayout());
		kjcustext = new JLabel();
		kjcustext.setText("������");
		kjcustext.setFont(new Font("Serif", Font.BOLD, 20));
		kjcustext.setHorizontalAlignment(JLabel.CENTER);
		kjcustomer.add(kjcustext, BorderLayout.NORTH);
		
		JPanel cusbtn = new JPanel();
		cusbtn.setLayout(new FlowLayout());
		JButton cusin = new JButton("����");
		cusbtn.add(cusin);
		JButton cusde = new JButton("Ż��");
		cusbtn.add(cusde);
		JButton cusup = new JButton("����");
		cusbtn.add(cusup);
		kjcustomer.add(new JScrollPane(kjcustable), BorderLayout.CENTER);
		kjcustomer.add(cusbtn, BorderLayout.SOUTH);
		
		kjaccount.setLayout(new BorderLayout());
		kjacctext = new JLabel();
		kjacctext.setText("���°���");
		kjacctext.setFont(new Font("Serif", Font.BOLD, 20));
		kjacctext.setHorizontalAlignment(JLabel.CENTER);
		kjaccount.add(kjacctext, BorderLayout.NORTH);
		
		JPanel accbtn = new JPanel();
		accbtn.setLayout(new FlowLayout());
		JButton accin = new JButton("����");
		accbtn.add(accin);
		JButton accde = new JButton("���");
		accbtn.add(accde);
		JButton accup = new JButton("����");
		accbtn.add(accup);

		kjaccount.add(new JScrollPane(kjacctable), BorderLayout.CENTER);
		kjaccount.add(accbtn, BorderLayout.SOUTH);
		
		kjloan.setLayout(new BorderLayout());
		kjlontext = new JLabel();
		kjlontext.setText("�������");
		kjlontext.setFont(new Font("Serif", Font.BOLD, 20));
		kjlontext.setHorizontalAlignment(JLabel.CENTER);
		kjloan.add(kjlontext, BorderLayout.NORTH);
		
		JPanel lonbtn = new JPanel();
		lonbtn.setLayout(new FlowLayout());
		JButton lonin = new JButton("��û");
		lonbtn.add(lonin);
		JButton londe = new JButton("���");
		lonbtn.add(londe);
		JButton lonup = new JButton("����");
		lonbtn.add(lonup);

		kjloan.add(new JScrollPane(kjlontable), BorderLayout.CENTER);
		kjloan.add(lonbtn, BorderLayout.SOUTH);
		
		add(kjtitle, BorderLayout.NORTH);
		add(kjcustomer, BorderLayout.WEST);
		add(kjaccount, BorderLayout.CENTER);
		add(kjloan, BorderLayout.EAST);
		
		setSize(1340, 720);
		setVisible(true);
	}
}