package Bank;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GradeShow extends JFrame {
	public GradeShow(JTable lblonmodel) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());
		JPanel gs = new JPanel();
		gs.setLayout(new BorderLayout());
		gs.add(new JScrollPane(lblonmodel), BorderLayout.CENTER);
		
		add(gs, BorderLayout.CENTER);
		setSize(300, 225);
		setVisible(true);
	}
	
}
