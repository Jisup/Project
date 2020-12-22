package Bank;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BankMain extends JFrame {
	public Connection conn = null;
	private JLabel BankChoice; // Table Header Name

	public BankMain() { // JFrame 생성자
		setTitle("은행데이터베이스관리자시스템");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // BorderLayout을 활용해 상 중 하로 컴포넌트 위치
		setLayout(new BorderLayout(50, 75));
		JPanel Btn_panel = new JPanel();
		
		Btn_panel.setLayout(new GridLayout(1,3,10,0));
		ImageIcon image1 = new ImageIcon("KBstar.jpg");
		ImageIcon image2 = new ImageIcon("Woori.jpg");
		ImageIcon image3 = new ImageIcon("Kwangju.jpg");
		JButton kbstar_btn = new JButton(image1);
		Btn_panel.add(kbstar_btn);
		JButton woori_btn = new JButton(image2);
		Btn_panel.add(woori_btn);
		JButton Kwangju_btn = new JButton(image3);
		Btn_panel.add(Kwangju_btn);
		
		// DB 상태 출력용 라벨
		
		BankChoice = new JLabel();
		BankChoice.setText("Bank Manager System");
		BankChoice.setFont(new Font("Serif", Font.BOLD, 60));
		BankChoice.setHorizontalAlignment(JLabel.CENTER);
		add(BankChoice, BorderLayout.NORTH);
		add(Btn_panel, BorderLayout.CENTER);
		
		kbstar_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new kbstar(conn);			
			}
		});
		woori_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new woori(conn);
			}
		});
		Kwangju_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new kwangju(conn);
			}
		});
		
		setSize(1340, 720);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BankMain();
	}
}