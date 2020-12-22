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

public class woori extends JFrame {
	private JLabel wrstarChoice; // Table Header Name
	private String wrcusname[] = { "��ȣ", "�̸�", "�ּ�", "��ȭ��ȣ", "����" }; // Table Data list (Header data, adding row)
	private DefaultTableModel wrcusmodel = new DefaultTableModel(wrcusname, 0);// Create Table
	private JTable wrcustable = new JTable(wrcusmodel);
	private String wrcusrow[] = new String[5];

	private String wraccname[] = { "���¹�ȣ", "������",  "�ݾ�" , "������"}; // Table Data list (Header data, adding row)
	private DefaultTableModel wraccmodel = new DefaultTableModel(wraccname, 0);// Create Table
	private JTable wracctable = new JTable(wraccmodel);
	private String wraccrow[] = new String[4];
	
	private String wrlonname[] = { "�����ȣ", "������", "�����", "��û��" }; // Table Data list (Header data, adding row)
	private DefaultTableModel wrlonmodel = new DefaultTableModel(wrlonname, 0);// Create Table
	private JTable wrlontable = new JTable(wrlonmodel);
	private String wrlonrow[] = new String[4];
	
	private JLabel wrcustext;
	private JLabel wracctext;
	private JLabel wrlontext;
	private JLabel wrtitletext;
	public woori(Connection conn) { // JFrame ������
		setTitle("�츮���൥���ͺ��̽��ý���");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // BorderLayout�� Ȱ���� �� �� �Ϸ� ������Ʈ ��ġ
		setLayout(new BorderLayout(25, 50));
		JPanel wrcustomer = new JPanel();
		JPanel wraccount = new JPanel();
		JPanel wrloan = new JPanel();
		
		JPanel wrtitle = new JPanel();
		wrtitle.setLayout(new BorderLayout());
		
		ImageIcon wricon = new ImageIcon("Woori.jpg");
		Image originImg = wricon.getImage();
		Image changedImg =originImg.getScaledInstance(115, 115, Image.SCALE_SMOOTH);
		ImageIcon wr_icon = new ImageIcon(changedImg);
		JLabel wrtitleicon = new JLabel(wr_icon);
		
		wrtitletext = new JLabel();
		wrtitletext.setText("�츮����");
		wrtitletext.setFont(new Font("Serif", Font.BOLD, 30));
		wrtitletext.setHorizontalAlignment(JLabel.CENTER);
		
		wrtitle.add(wrtitleicon, BorderLayout.WEST);
		wrtitle.add(wrtitletext, BorderLayout.CENTER);
		
		wrcustomer.setLayout(new BorderLayout());
		wrcustext = new JLabel();
		wrcustext.setText("������");
		wrcustext.setFont(new Font("Serif", Font.BOLD, 30));
		wrcustext.setHorizontalAlignment(JLabel.CENTER);
		wrcustomer.add(wrcustext, BorderLayout.NORTH);
		
		JPanel cusbtn = new JPanel();
		cusbtn.setLayout(new FlowLayout());
		JButton cusin = new JButton("����");
		cusbtn.add(cusin);
		JButton cusde = new JButton("Ż��");
		cusbtn.add(cusde);
		JButton cusup = new JButton("����");
		cusbtn.add(cusup);
		wrcustomer.add(new JScrollPane(wrcustable), BorderLayout.CENTER);
		wrcustomer.add(cusbtn, BorderLayout.SOUTH);
		
		wraccount.setLayout(new BorderLayout());
		wracctext = new JLabel();
		wracctext.setText("���°���");
		wracctext.setFont(new Font("Serif", Font.BOLD, 30));
		wracctext.setHorizontalAlignment(JLabel.CENTER);
		wraccount.add(wracctext, BorderLayout.NORTH);
		
		JPanel accbtn = new JPanel();
		accbtn.setLayout(new FlowLayout());
		JButton accin = new JButton("����");
		accbtn.add(accin);
		JButton accde = new JButton("���");
		accbtn.add(accde);
		JButton accup = new JButton("����");
		accbtn.add(accup);

		wraccount.add(new JScrollPane(wracctable), BorderLayout.CENTER);
		wraccount.add(accbtn, BorderLayout.SOUTH);
		
		wrloan.setLayout(new BorderLayout());
		wrlontext = new JLabel();
		wrlontext.setText("�������");
		wrlontext.setFont(new Font("Serif", Font.BOLD, 30));
		wrlontext.setHorizontalAlignment(JLabel.CENTER);
		wrloan.add(wrlontext, BorderLayout.NORTH);
		
		JPanel lonbtn = new JPanel();
		lonbtn.setLayout(new FlowLayout());
		JButton lonin = new JButton("��û");
		lonbtn.add(lonin);
		JButton londe = new JButton("���");
		lonbtn.add(londe);
		JButton lonup = new JButton("����");
		lonbtn.add(lonup);

		wrloan.add(new JScrollPane(wrlontable), BorderLayout.CENTER);
		wrloan.add(lonbtn, BorderLayout.SOUTH);
		
		add(wrtitle, BorderLayout.NORTH);
		add(wrcustomer, BorderLayout.WEST);
		add(wraccount, BorderLayout.CENTER);
		add(wrloan, BorderLayout.EAST);
		
		setSize(1340, 720);
		setVisible(true);
	}
}