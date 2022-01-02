package game;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class SDgame extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	Container con;
	JPanel control;
	Toolkit toolkit = this.getToolkit();
	JMenuItem newGame = new JMenuItem("�½���Ϸ(N)");
	JMenuItem saveGame = new JMenuItem("������Ϸ(S)");
	JMenuItem exitGame = new JMenuItem("�˳���Ϸ(E)");
	JMenuItem showAllView = new JMenuItem("��ʾ���д���(S)");
	JMenuItem showInformationOfAuthor = new JMenuItem("��������(A)");
	JMenuItem showLicence = new JMenuItem("���Э��(L)");
	JMenuItem nextStep = new JMenuItem("ǰ��һ��(N)");
	JMenuItem lastStep = new JMenuItem("����һ��(L)");
	// /////////////////////////////////////////////////////////////////////////////////
	ArrayList<Step> stepArray = new ArrayList<Step>();
	int currentStep = stepArray.size() - 1;
	int forcusX = 0, forcusY = 0;
	int[][] itemTotal = new int[9][9];
	JButton[][] buttonNum = new JButton[9][9];
	Font font = new Font("", Font.BOLD, 35);
	Border black = BorderFactory.createLineBorder(Color.black);
	Border raisedBevel = BorderFactory.createRaisedBevelBorder();
	Border border = BorderFactory.createCompoundBorder(raisedBevel, black);
	long starttime = -1, overtime = -1; // ��Ϸ��ʼ�ͽ�����ʱ��
	int easyLevel = 0; // ������Ϸ�����׳̶�
	// ///////////////////////////////////////////////////////////////////////////////�����¼�����

	public void keyPressed(KeyEvent e) {
		if (starttime < 0) {// ��Ϸ��δ��ʼ,����
			return;
		}
		int keycode = e.getKeyCode();
		int num = -1;
		switch (keycode) {
		case KeyEvent.VK_DELETE:
			num = 0;
			break;
		case KeyEvent.VK_1:
			num = 1;
			break;
		case KeyEvent.VK_2:
			num = 2;
			break;
		case KeyEvent.VK_3:
			num = 3;
			break;
		case KeyEvent.VK_4:
			num = 4;
			break;
		case KeyEvent.VK_5:
			num = 5;
			break;
		case KeyEvent.VK_6:
			num = 6;
			break;
		case KeyEvent.VK_7:
			num = 7;
			break;
		case KeyEvent.VK_8:
			num = 8;
			break;
		case KeyEvent.VK_9:
			num = 9;
			break;
		case KeyEvent.VK_NUMPAD1:
			num = 1;
			break;
		case KeyEvent.VK_NUMPAD2:
			num = 2;
			break;
		case KeyEvent.VK_NUMPAD3:
			num = 3;
			break;
		case KeyEvent.VK_NUMPAD4:
			num = 4;
			break;
		case KeyEvent.VK_NUMPAD5:
			num = 5;
			break;
		case KeyEvent.VK_NUMPAD6:
			num = 6;
			break;
		case KeyEvent.VK_NUMPAD7:
			num = 7;
			break;
		case KeyEvent.VK_NUMPAD8:
			num = 8;
			break;
		case KeyEvent.VK_NUMPAD9:
			num = 9;
			break;
		case KeyEvent.VK_UP:
			forcusX--;
			toolkit.beep();
			break;
		case KeyEvent.VK_DOWN:
			forcusX++;
			toolkit.beep();
			break;
		case KeyEvent.VK_LEFT:
			forcusY--;
			toolkit.beep();
			break;
		case KeyEvent.VK_RIGHT:
			forcusY++;
			toolkit.beep();
			break;
		}
		if (num >= 0) {
			stepArray.add(new Step(forcusX, forcusY, num,
					itemTotal[forcusX][forcusY]));
			itemTotal[forcusX][forcusY] = num;
			currentStep++;
			showNumOnButtons(itemTotal);
			this.lastStep.setEnabled(true);
			// if(!SDgame.judge(itemTotal))
			showColorOnButtons();
			if (SDgame.judge(itemTotal))
				showWinGame();
		} else {
			if (forcusX < 0)
				forcusX = 0;
			if (forcusX > 8)
				forcusX = 8;
			if (forcusY < 0)
				forcusY = 0;
			if (forcusY > 8)
				forcusY = 8;
		}
		showColorOnButtons();
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	// ///////////////////////////////////////////////////////////////////////////////
	public void excuteStep(Step step, boolean flag) {
		if (flag) {
			itemTotal[step.x][step.y] = step.newnum;
		} else
			itemTotal[step.x][step.y] = step.orinum;
		this.showNumOnButtons(itemTotal);
	}

	public SDgame() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				buttonNum[i][j] = new JButton("");
				buttonNum[i][j].setFont(font);
				buttonNum[i][j].addActionListener(this);
				buttonNum[i][j].addKeyListener(this);
				// buttonNum[i][j].registerKeyboardAction(this,
				// KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true),
				// JComponent.WHEN_IN_FOCUSED_WINDOW); //��ӦEnter��
			}
		}
		// ////////////////////////////////////////////
		JMenu menuFile = new JMenu("��Ϸ(G)");
		menuFile.setMnemonic('G');
		menuFile.add(newGame);
		newGame.addActionListener(this);
		menuFile.add(saveGame);
		saveGame.addActionListener(this);
		menuFile.add(exitGame);
		exitGame.addActionListener(this);

		JMenu menuEdit = new JMenu("�༭(E)");
		menuEdit.setMnemonic('E');
		menuEdit.add(lastStep);
		lastStep.addActionListener(this);
		menuEdit.add(nextStep);
		nextStep.addActionListener(this);

		JMenu menuView = new JMenu("�鿴(V)");
		menuView.setMnemonic('V');
		menuView.add(showAllView);
		showAllView.addActionListener(this);

		JMenu menuTool = new JMenu("����(T)");
		menuTool.setMnemonic('T');

		JMenu menuHelp = new JMenu("����(H)");
		menuHelp.setMnemonic('H');
		menuHelp.add(showInformationOfAuthor);
		menuHelp.add(showLicence);
		showLicence.addActionListener(this);
		showInformationOfAuthor.addActionListener(this);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(menuFile);
		menuBar.add(menuEdit);
		menuBar.add(menuView);
		menuBar.add(menuTool);
		menuBar.add(menuHelp);
		this.setJMenuBar(menuBar);
		// ////////////////////////////////////////////
		con = this.getContentPane();
		control = new JPanel();
		control.setLayout(new GridLayout(3, 3));
		JPanel[] panelNum = new JPanel[9];
		for (int i = 0; i < 9; i++) {
			panelNum[i] = new JPanel();
			panelNum[i].setLayout(new GridLayout(3, 3));
			panelNum[i].add(buttonNum[(i / 3) * 3][(i % 3) * 3]);
			panelNum[i].add(buttonNum[(i / 3) * 3][(i % 3) * 3 + 1]);
			panelNum[i].add(buttonNum[(i / 3) * 3][(i % 3) * 3 + 2]);
			panelNum[i].add(buttonNum[(i / 3) * 3 + 1][(i % 3) * 3]);
			panelNum[i].add(buttonNum[(i / 3) * 3 + 1][(i % 3) * 3 + 1]);
			panelNum[i].add(buttonNum[(i / 3) * 3 + 1][(i % 3) * 3 + 2]);
			panelNum[i].add(buttonNum[(i / 3) * 3 + 2][(i % 3) * 3]);
			panelNum[i].add(buttonNum[(i / 3) * 3 + 2][(i % 3) * 3 + 1]);
			panelNum[i].add(buttonNum[(i / 3) * 3 + 2][(i % 3) * 3 + 2]);
			panelNum[i].setBorder(border);
			control.add(panelNum[i]);
		}
		showAllView.setEnabled(false);
		this.saveGame.setEnabled(false);
		this.lastStep.setEnabled(false);
		this.nextStep.setEnabled(false);
		con.add(new JScrollPane(control));
		// this.setTitle("����С��Ϸ����������ҵ��ѧ��Ŀ���");
		this.setTitle("����С��Ϸ");
		this.setLocation(200, 200);
		this.setSize(620, 620);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == lastStep) {
			if (currentStep >= 0)
				this.excuteStep(stepArray.get(currentStep), false);
			if (currentStep > 0) {
				currentStep--;
				this.nextStep.setEnabled(true);
			} else
				lastStep.setEnabled(false);
		} else if (ae.getSource() == nextStep) {
			if (currentStep < stepArray.size())
				this.excuteStep(stepArray.get(currentStep), true);
			if (currentStep < stepArray.size() - 1) {
				currentStep++;
				this.lastStep.setEnabled(true);
			} else
				nextStep.setEnabled(false);
		} else if (ae.getSource() == newGame) {
			try {
				String s = JOptionPane.showInputDialog(this, "�������Ѷ�ϵ��(0-81):");
				if (s == null)
					return;
				int snum = Integer.parseInt(s);
				if (snum >= 0 && snum <= 81) {
					easyLevel = snum;
				} else {
					JOptionPane.showMessageDialog(this, "�Ѷ�ϵ����Χ����ȷ�����������룡");
					return;
				}
			} catch (NumberFormatException nfe) {
				JOptionPane.showMessageDialog(this, "�����ʽ����ȷ�����������룡");
				return;
			}
			itemTotal = getNewItems();
			starttime = System.currentTimeMillis();
			this.saveGame.setEnabled(true);
			showNumOnButtons(itemTotal);
			showColorOnButtons();
		} else if (ae.getSource() == saveGame) {
		} else if (ae.getSource() == exitGame) {
			System.exit(0);
		} else if (ae.getSource() == showInformationOfAuthor) {
			String information = SDgame.getFileText("author.txt");
			JOptionPane.showMessageDialog(this, information);
		} else if (ae.getSource() == showLicence) {
			String licence = SDgame.getFileText("licence.txt");
			JOptionPane.showMessageDialog(this, licence);
		} else {
			if (starttime < 0) {// ��Ϸ��δ��ʼ,����
				return;
			}
			JButton button = (JButton) ae.getSource();
			// button.requestFocus();//����õ�����
			// button.geta.getActionForKeyStroke(KeyStroke.);
			int I = 0, J = 0;
			for (I = 0; I < 9; I++) {// �ҵ������¼��İ�ť������
				boolean ifFind = false;
				for (J = 0; J < 9; J++) {
					if (button == buttonNum[I][J]) {
						ifFind = true;
						break;
					}
				}
				if (ifFind)
					break;
			}
			forcusX = I;
			forcusY = J;
			showColorOnButtons();
		}
	}

	public static void main(String[] args) {
		new SDgame();
	}

	public static boolean judge(int[][] item) {// �����жϵ�ǰ�Ƿ��Ѿ���ȷ����
		for (int i = 0; i < 9; i++) {
			if (!SDgame.judgeLine(item[i]))
				return false;
		}
		for (int j = 0; j < 9; j++) {
			int[] temp = new int[9];
			for (int i = 0; i < 9; i++) {
				temp[i] = item[i][j];
			}
			if (!SDgame.judgeLine(temp))
				return false;
		}
		int[][] newitem = SDgame.transformOfItems(item);
		for (int i = 0; i < 9; i++) {
			if (!SDgame.judgeLine(newitem[i]))
				return false;
		}
		return true;
	}

	public static boolean judgeLine(int[] item) {// �ж�һ���Ƿ������������
		int[] temp = new int[9];
		for (int i = 0; i < 9; i++) {
			temp[i] = item[i];
		}
		Arrays.sort(temp);// ��С��������
		for (int i = 0; i < 9; i++) {
			if (temp[i] != i + 1)
				return false;
		}
		return true;
	}

	public static int[][] transformOfItems(int[][] oriItem) {// ת��������ʽ
		int[][] result = new int[9][9];
		for (int i = 0; i < 9; i++) {
			int index = 0;
			int n = (i / 3) * 3, m = (i % 3) * 3;
			for (int j = n; j < n + 3; j++) {
				for (int k = m; k < m + 3; k++) {
					result[j][k] = oriItem[i][index++];
				}
			}
		}
		return result;
	}

	public void showNumOnButtons(int[][] item) {// ��item�ﱣ���ֵ��ʾ����ť��
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (item[i][j] == 0)
					buttonNum[i][j].setText("  ");
				else
					buttonNum[i][j].setText("" + item[i][j]);
			}
		}
	}

	public void showColorOnButtons() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				if (i == forcusX && j == forcusY)
					buttonNum[i][j].setForeground(Color.BLUE);
				else
					buttonNum[i][j].setForeground(Color.BLACK);
			}
		}
		// this.getFocusOwner().setBackground(Color.BLACK);
	}

	public int[][] getNewItems() {// �õ�������ڸǴ���õ���������
		int[][] result = SDgame.getCompleteItems();
		for (int i = 0; i < easyLevel; i++) {
			int r = (int) (Math.random() * 81);
			if (r == 81)
				r -= 1;
			int indexi = r / 9;
			int indexj = r % 9;
			result[indexi][indexj] = 0;
		}
		return result;
	}

	public static int[][] getCompleteItems() {// �õ���õ���������
		int[][] temp = { { 7, 1, 6, 3, 5, 8, 4, 2, 9 },
				{ 8, 4, 9, 2, 6, 7, 3, 1, 5 }, { 3, 5, 2, 4, 1, 9, 6, 8, 7 },
				{ 5, 6, 7, 9, 4, 1, 8, 3, 2 }, { 4, 8, 1, 5, 3, 2, 7, 9, 6 },
				{ 9, 2, 3, 8, 7, 6, 5, 4, 1 }, { 2, 9, 4, 6, 8, 5, 1, 7, 3 },
				{ 1, 3, 5, 7, 2, 4, 9, 6, 8 }, { 6, 7, 8, 1, 9, 3, 2, 5, 4 } };
		int[][] result = new int[9][9];
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				result[i][j] = temp[i][j];
			}
		}
		return result;
	}

	public void showWinGame() {// ��Ϸ���غ�Ĵ�����
		if (starttime > 0) {
			String message = new String("��ϲ�㣬�ѹ��أ�");
			overtime = System.currentTimeMillis();
			message += "\n�����Ѷ�ϵ����";
			Integer cureasyLevel = easyLevel;
			message += cureasyLevel.toString();
			message += "\n���ع���ʱ�䣺";
			Integer usetime = (int) (overtime - starttime);
			usetime /= 1000;
			String time;
			if (usetime <= 60) {
				time = usetime.toString();
				time += " ��";
			} else if (usetime <= 3600) {
				Integer minute = usetime / 60;
				Integer second = usetime % 60;
				time = minute.toString() + " �� " + second.toString() + " ��";
			} else {
				Integer hour = usetime / 3600;
				Integer minute = usetime / 60;
				Integer second = usetime % 60;
				time = hour.toString() + " Сʱ " + minute.toString() + " �� "
						+ second.toString() + " ��";
			}
			message += time;
			JOptionPane.showMessageDialog(this, message);
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					itemTotal[i][j] = 0;
				}
			}
			showNumOnButtons(itemTotal);
			starttime = -1;
		}
	}

	public static String getFileText(String filePath) {
		File f = new File(filePath.trim());
		if (!f.exists())
			return null;
		try {
			int len = (int) f.length();
			char[] acContent = new char[len];
			FileReader fr = new FileReader(f);
			int textLen = fr.read(acContent);
			String strContent = String.valueOf(acContent, 0, textLen);
			return strContent;
		} catch (IOException ioe) {
			return null;
		}
	}
}

class Step {// ������
	int x, y;
	int newnum, orinum;

	Step(int x, int y, int newnum, int orinum) {
		this.x = x;
		this.y = y;
		this.newnum = newnum;
		this.orinum = orinum;
	}
}