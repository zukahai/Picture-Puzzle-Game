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

public class PuzzleGame extends JFrame implements KeyListener, ActionListener{
	Timer timer;
	int indexI, indexJ;
	int n = 3;
	int rd = 1;
	private Container cn;
	private JPanel pn, pn2, pn3; 
	private JButton b[][] = new JButton[n + 1][n + 1];
	private int[][]v = new int[n + 1][n + 1];
	private JButton originalIm, newGame_bt;
	private JLabel time_lb;
	JButton size;
	public PuzzleGame(int rd) {
		super("Image puzzle game - HaiZuka");
		this.rd = rd;
		cn = this.getContentPane();
		cn.setBackground(Color.white);
		time_lb = new JLabel("00:00:00:00");
		time_lb.setFont(new Font("Arial", 1, 20));
		time_lb.addKeyListener(this);
		pn2 = new JPanel();
		pn2.setLayout(new FlowLayout());
		pn2.add(time_lb);
		
		originalIm = new JButton("");
		originalIm.setActionCommand("Original Image");
		originalIm.setIcon(getIconButton("original", 200, 50));
		originalIm.addActionListener(this);
		originalIm.addKeyListener(this);
		originalIm.setFont(new Font("UTM Nokia", 1, 15));
		originalIm.setBackground(Color.white);
		originalIm.setBorder(null);
		
		newGame_bt = new JButton("");
		newGame_bt.setActionCommand("newGame");
		newGame_bt.setIcon(getIconButton("newgame", 200, 50));
		newGame_bt.addActionListener(this);
		newGame_bt.addKeyListener(this);
		newGame_bt.setFont(new Font("UTM Nokia", 1, 15));
		newGame_bt.setBackground(Color.white);
		newGame_bt.setBorder(null);
		
		pn3 = new JPanel();
		pn3.setLayout(new FlowLayout());
		pn3.add(newGame_bt);
		pn3.add(originalIm);
		
		pn = new JPanel();
		pn.setLayout(new GridLayout(n,n));
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++){
				b[i][j] = new JButton("");
				b[i][j].addActionListener(this);
				b[i][j].setActionCommand(n * (i - 1) + j + "");
				v[i][j] = n * (i - 1) + j;
				b[i][j].addKeyListener(this);
				b[i][j].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
//				b[i][j].setBorder(null);
				pn.add(b[i][j]);
			}
		}
		int i1, j1, i2, j2;
		for (int k = 1; k <= 2*n*n; k++) {
			do {
				i1 = (int) (Math.round((n-1)*Math.random()+1));;
				j1 = (int) (Math.round((n-1)*Math.random()+1));
			} while (i1 == n && j1 == n);
			do {
				i2 = (int) (Math.round((n-1)*Math.random()+1));
				j2 = (int) (Math.round((n-1)*Math.random()+1));
			} while ((i2 == n && j2 == n) ||(i2 == i1&&j2 == j1));
			int p = v[i1][j1];
			v[i1][j1] = v[i2][j2];
			v[i2][j2] = p;
		}
		updateImages();
		pn3.setSize(100, 100);
		cn.add(pn2, "North");
		cn.add(pn);
		cn.add(pn3, "South");
		this.setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(630, 730);
		this.setLocation(650, 0);
		indexI = n; indexJ = n;
		timer = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				time_lb.setText(next(time_lb));
			}
		});
	}
	
	private Icon getIcon(int index) {
		int width = 200, height = 200;
		Image image = new ImageIcon(getClass().getResource("/Game/Image/" + rd + "/" + index + ".jpg")).getImage();
		Icon icon = new ImageIcon(image.getScaledInstance(width, height, image.SCALE_SMOOTH));
		return icon;
	}
	
	private Icon getIconButton(String nameIM, int w, int h) {
		int width = w, height = h;
		Image image = new ImageIcon(getClass().getResource("/Game/Button/" + nameIM + ".jpg")).getImage();
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
	
	public void updateImages(){
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				b[i][j].setIcon(getIcon(v[i][j]));
			}	
		}
	}
	public void checkWin() {
		boolean kt = true;
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				if (v[i][j] != n*(i-1)+j) kt = false;					
			}
		}
		if (kt) {
			new newGame();
			this.dispose();
		} else {
			b[n][n].setText(String.valueOf(""));
		}
	}
	
	// Xử lý khi gõ phím
	public void keyPressed(KeyEvent e) {
		timer.start();
		if (e.getKeyCode()==KeyEvent.VK_ESCAPE) System.exit(0); // thoát chương trình
		if (e.getKeyCode()==KeyEvent.VK_DOWN) { // khi bấm phím xuống: Hoán đổi vị trị của ôn trống với ô phím trên nó.
			if (indexI > 1) {
				int s = v[indexI][indexJ];
				v[indexI][indexJ] = v[indexI-1][indexJ];
				v[indexI-1][indexJ] = s;
				indexI--;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_UP) { //khi bấm phím lên: Hoán đổi vị trị của ôn trống với ô phím dưới nó.
			if (indexI < n) {
				int s = v[indexI][indexJ];
				v[indexI][indexJ] = v[indexI+1][indexJ];
				v[indexI+1][indexJ] = s;
				indexI++;
			}
		}
		
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {//khi bấm phím sang phải: Hoán đổi vị trị của ôn trống với ô bên trái nó.
			if (indexJ > 1) {
				int s = v[indexI][indexJ];
				v[indexI][indexJ] = v[indexI][indexJ-1];
				v[indexI][indexJ-1] = s;
				indexJ--;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) { //khi bấm phím sang trái: Hoán đổi vị trị của ôn trống với ô bên phải nó.
			if (indexJ < n) {
				int s = v[indexI][indexJ];
				v[indexI][indexJ] = v[indexI][indexJ+1];
				v[indexI][indexJ+1] = s;
				indexJ++;
			}
		}
		updateImages();
		checkWin();
	}
	public void keyReleased(KeyEvent e) {
		updateImages();
	}
	public void keyTyped(KeyEvent e) {
		
	}
	public static void main(String[] args) {
		new newGame();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals(originalIm.getActionCommand())) {
			new OriginalIm(rd);
		}
		else if (e.getActionCommand().equals(newGame_bt.getActionCommand())) {
			new newGame();
			this.dispose();
		} else {
			int K = Integer.parseInt(e.getActionCommand());
			int ii = (K - 1) / 3 + 1;
			int jj = (K - 1) % 3 + 1;
			if ((ii == indexI) ^ (jj == indexJ)) {
				System.out.println("Ok");
			}
		}
	}
}

