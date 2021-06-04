package Game;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.*;

public class OriginalIm extends JFrame implements ActionListener{
	private Container cn;
	private JPanel pn;
	private JButton bt;
	int rd = 1;
	
	public OriginalIm(int rd) {
		super("Original Image");
		this.rd = rd;
		cn = this.getContentPane();
		
		bt = new JButton();
		bt.setIcon(getIcon(0));
		
		pn = new JPanel();
		pn.add(bt);
		
		cn.add(pn);
		this.setVisible(true);
		this.setResizable(false);
		this.setSize(600, 650);
		this.setLocation(0, 27);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
//		this.setLocationRelativeTo(null);
		
	}
	
	private Icon getIcon(int index) {
		int width = 600, height = 600;
		Image image = new ImageIcon(getClass().getResource("/Game/Image/" + rd + "/" + index + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;
	}
	
	public String next(JLabel lb) {
		String str[] = lb.getText().split(":");
		int tt = Integer.parseInt(str[3]);
		int s = Integer.parseInt(str[2]);
		int m = Integer.parseInt(str[1]);
		int h = Integer.parseInt(str[0]);
		String kq = "";
		int sum = tt + s * 100 + m * 60 * 100 + h * 60 * 60 * 100 + 1;
		if (sum % 100 > 9)
			kq = ":" + sum % 100 + kq;
		else
			kq = ":0" + sum % 100 + kq;
		sum /= 100;
		
		if (sum % 60 > 9)
			kq = ":" + sum % 60 + kq;
		else
			kq = ":0" + sum % 60 + kq;
		sum /= 60;
		
		if (sum % 60 > 9)
			kq = ":" + sum % 60 + kq;
		else
			kq = ":0" + sum % 60 + kq;
		sum /= 60;
		if (sum > 9)
			kq = sum + kq;
		else
			kq = "0" + sum +kq;
		return kq;
	}
	
	public static void main(String[] args) {
		new OriginalIm(1);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}

