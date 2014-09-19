
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

//import java.util.ArrayList;

/**
 * function:1.��¼����
 * 2.��½��������
 * 3.�޸�����Ȱ�ť�ı�ɫ��
 * 4.�Զ���ĵ�½����
 * 5.8������������ڲ��࣬���߳�
 * @author sun
 * @version 0.0.4 8/8/2014
 * */

// import com.wenxin.ui.tools.MoveWindow;

public final class 121250010_Login {

	private final int screenWidth, screenHeight, windowWidth, windowHeight;

	public JLabel minx;
	public JLabel close;
	private final URL minxUrl, closeUrl, selogoUrl;

	public JWindow reference;
	public JLabel refInfo;

	public static Color colorBeforeClicked = new Color(176, 150, 134);
	public static Color colorAfterClicked = new Color(176, 170, 134);
	public static Color inUse = new Color(176, 140, 100);
	// Color(176,243,134) 132,209,177

	private final JFrame loginFrame;
	private final JPanel loginPanel, loading;
	private final JLabel logo, userPicture;
	private final JLabel[] load = new JLabel[5];
	private final JLabel register, fetchPassword;
	private final DefaultComboBoxModel<String> model;
	private final JComboBox<String> usernametf;
	private final JPasswordField passwordtf;
	private final JRadioButton rememberPassword, autoLogin;
	private final JButton sure;
	private JProgressBar loadingFlash;
	private final URL logoUrl, userPictureUrl;

	
	// public static String name;
	/** �߳�ѭ����־* */
	private boolean mIsRunning = false;
	private Thread login, loadThread;

	public Login() {

		this.windowWidth = 430;
		this.windowHeight = 320;

		// ������������

		userPictureUrl = getClass().getResource(
				"/configure/pictures/login/user.jpg");
		logoUrl = getClass().getResource("/configure/pictures/login/logo.png");

		// System.out.println(userPictureUrl);

		// �����ؼ�����
		loginFrame = new JFrame();
		loginPanel = new JPanel();
		loading = new JPanel();
		load[0] = new JLabel("loading");
		load[1] = new JLabel(".");
		load[2] = new JLabel(".");
		load[3] = new JLabel(".");
		load[4] = new JLabel(".");
		logo = new JLabel(new ImageIcon(logoUrl));
		userPicture = new JLabel(new ImageIcon(userPictureUrl));
		register = new JLabel("ע���˺�");
		fetchPassword = new JLabel("�һ�����");
		model = new DefaultComboBoxModel<String>();
		usernametf = new JComboBox<String>(model);
		// usernametf.enableInputMethods(false);
		// windows��û�ж�Ӧ��
		// ʲô���ã���
		// DefaultCellEditor dce=new DefaultCellEditor(usernametf);

		// usernametf.add
		passwordtf = new JPasswordField();
		passwordtf.setEchoChar('*');
		passwordtf.setText("����");
		//passwordtf.setEchoChar('\0');
		// passwordtf.setEchoChar('*');
		rememberPassword = new JRadioButton("��ס����");
		autoLogin = new JRadioButton("�Զ���¼");
		sure = new JButton("��¼");
		// sure.setBorderPainted(false);

		// ��ʼ����½��Ϣ
		// Thread.currentThread().getContextClassLoader().getResource("configure/initial/userInfo").getPath();
		// configUrl=getClass().getResource("/configure/initial");
		// System.out.println(userSetting);
		// userSetting=Thread.currentThread().getContextClassLoader().getResource("configure/initial/userSetting").getPath();
		initial();

		// UIManager.put("JButton.Font",new Font("����",Font.PLAIN,14));
		// UIManager.put("JLabel.font",new Font("����",Font.PLAIN,14));
		// UIManager.put("JPasswordField.font",new Font("����",Font.PLAIN,14));
		// UIManager.put("JTextField.font",new Font("����",Font.PLAIN,14));

		/*
		 * Component[] comp=loginFrame.getComponents(); for(Component cp:comp){
		 * cp.setFont(new Font("����",Font.PLAIN,14)); }
		 */

		loginFrame.setLayout(null);

		// ��ʼ������
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		selogoUrl = getClass().getResource("/configure/pictures/hewin.png");
		minxUrl = getClass().getResource("/configure/pictures/login/minx.jpg");
		closeUrl = getClass()
				.getResource("/configure/pictures/login/close.jpg");
		/*
		 * themeURL = getClass()
		 * .getResource("/configure/pictures/login/theme.png");
		 */
		// ��ʼ�����
		reference = new JWindow();
		refInfo = new JLabel();

		minx = new JLabel(new ImageIcon(minxUrl));
		close = new JLabel(new ImageIcon(closeUrl));

		// ��С��label����
		minx.setOpaque(true);
		// minx.setBackground(colorBeforeClicked);
		minx.setBounds(windowWidth - 56, 0, 28, 28);

		// �ر�label����
		close.setOpaque(true);
		// close.setBackground(colorBeforeClicked);
		close.setBounds(windowWidth - 28, 0, 28, 28);

		// label������ʾ����
		refInfo.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		refInfo.setOpaque(true);
		refInfo.setBackground(Color.WHITE);
		// refInfo.setForeground(Color.BLACK);
		refInfo.setFont(new Font("����", Font.PLAIN, 14));
		reference.setLayout(new BorderLayout());
		reference.add(refInfo, BorderLayout.CENTER);
		// reference.setUndecorated(true);
		reference.setVisible(false);

		// �������
		// refInfo.BorderWidth="1px";
		// Border border=new B;//new Border("1px");

		// frame��������
		loginFrame.setBounds(screenWidth / 2 - this.windowWidth / 2,
				screenHeight / 2 - this.windowHeight / 2, this.windowWidth,
				this.windowHeight);
		loginFrame.setUndecorated(true);
		loginFrame.setIconImage((new ImageIcon(selogoUrl)).getImage());

		// ��logo��label�������С���͹رհ�ť
		loginFrame.add(minx);
		loginFrame.add(close);
		// this.add(theme);

		// ��С�����رգ��������ü���
		close.addMouseListener(new Minimize_Close(close, loginFrame));
		minx.addMouseListener(new Minimize_Close(minx, loginFrame));

		// һ���������������������϶�����
		MoveWindow frameListener = new MoveWindow(loginFrame);
		loginFrame.addMouseListener(frameListener);
		loginFrame.addMouseMotionListener(frameListener);
		/*
		 * MyFrame.colorBeforeClicked=new Color(127,180,241);
		 * MyFrame.colorAfterClicked=new Color(127,180,180); MyFrame.inUse=new
		 * Color(127,180,170); minx.setBackground(colorBeforeClicked);
		 * close.setBackground(colorBeforeClicked);
		 * theme.setBackground(colorBeforeClicked);
		 */
		/*
		 * MyFrame.colorBeforeClicked=new Color(24,101,198);
		 * MyFrame.colorBeforeClicked=new Color(24,101,160); MyFrame.inUse=new
		 * Color(24,101,150);
		 */

		// ����������ʽ
		// loginPanel.setFont(new Font("����",Font.PLAIN,14));
		register.setFont(new Font("����", Font.PLAIN, 14));
		fetchPassword.setFont(new Font("����", Font.PLAIN, 14));
		usernametf.setFont(new Font("����", Font.PLAIN, 14));
		passwordtf.setFont(new Font("����", Font.PLAIN, 14));
		rememberPassword.setFont(new Font("����", Font.PLAIN, 14));
		autoLogin.setFont(new Font("����", Font.PLAIN, 14));
		load[0].setFont(new Font("����", Font.HANGING_BASELINE, 16));
		sure.setFont(new Font("����", Font.PLAIN, 14));

		// �ؼ���frame��panel��������
		loading.setVisible(false);
		loading.setOpaque(true);
		usernametf.setEditable(true);
		for (int i = 0; i <= 4; i++) {
			load[i].setVisible(false);
		}
		logo.setBackground(colorBeforeClicked);
		logo.setOpaque(true);
		// loginFrame.setUndecorated(true);

		// sure.setIcon(new ImageIcon(dir));
		// sure.setForeground(Color.BLACK);
		// sure.setBackground(new Color(10,50,255));

		// ���ò��ֹ�����
		loginPanel.setLayout(null);
		loading.setLayout(null);
		// loginFrame.setLayout(null);
		// loginPanel.setLayout(new GridLayout(3,2,50,40));

		// ���¼loginFrame��������ӿؼ�

		// ��loginPanel�����������
		// loginPanel.setOpaque(false);
		loginPanel.add(userPicture);
		// loginPanel.add(usernameChoice);
		loginPanel.add(usernametf);
		loginPanel.add(register);
		loginPanel.add(passwordtf);
		loginPanel.add(fetchPassword);
		loginPanel.add(rememberPassword);
		loginPanel.add(autoLogin);
		loginPanel.add(sure);

		// loading panel���
		loading.add(load[0]);
		loadingFlash=new JProgressBar();
		loading.add(loadingFlash);
		
		/*loading.add(load[1]);
		loading.add(load[2]);
		loading.add(load[3]);
		loading.add(load[4]);*/

		loginFrame.add(loginPanel);// ,BorderLayout.CENTER);
		loginFrame.add(logo);// ,BorderLayout.NORTH);
		loginFrame.add(loading);

		// ����frame��panel���ؼ�λ��

		// ������430*320
		logo.setBounds(0, 0, 430, 160);
		loginPanel.setBounds(0, 160, 430, 120);

		// loginPanel���
		loading.setBounds(0, 280, 430, 40);
		userPicture.setBounds(15, 10, 90, 90);
		usernametf.setBounds(120, 10, 200, 20);
		register.setBounds(330, 10, 60, 20);
		passwordtf.setBounds(120, 40, 200, 20);
		fetchPassword.setBounds(330, 40, 60, 20);
		rememberPassword.setBounds(120, 70, 100, 20);
		autoLogin.setBounds(220, 70, 100, 20);
		sure.setBounds(120, 100, 200, 20);

		// loading ������
		load[0].setBounds(100, 5, 60, 30);
		load[1].setBounds(190, 5, 20, 30);
		load[2].setBounds(210, 5, 20, 30);
		load[3].setBounds(230, 5, 20, 30);
		load[4].setBounds(250, 5, 20, 30);
		loadingFlash.setBounds(170, 17, 150, 10);

		/*
		 * ��Ӽ���
		 */
		passwordtf.addFocusListener(new ClearInfo(passwordtf));
		register.addMouseListener(new Register_Fetch(register));
		fetchPassword.addMouseListener(new Register_Fetch(fetchPassword));
		sure.addMouseListener(new Enter());
		// sure.addActionListener(new Enter());
		// sure.setFocusPainted(false);

		// usernametf.addFocusListener(new ClearInfo(usernametf));

		// ��ʾ���ر�����
		// loginFrame.theme.setVisible(false);
		
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.setResizable(false);
		loginFrame.setVisible(true);

		// start the thread
		initial();
		automaticLogin();
		mIsRunning=true;
		/*login = new Thread();
		login.start();
		loginFrame.setVisible(true);*/

	}

	public void run() {
		// TODO Auto-generated method stub
		initial();
		automaticLogin();
	}

	// ��½���ض���
	public void loading() {
		

		//loginFrame.repaint();
	

		
			System.out.println("test");
			//loadingFlash.setStringPainted(true);
			new Thread(new Runnable(){
				public void run(){
				for (int i = 0; i <= 100; i++) {
					loading.setVisible(true);
					loadingFlash.setValue(i);
					load[0].setVisible(true);
					loading.repaint();
					loginFrame.repaint();
					try {
							Thread.sleep(50);		
					} catch (InterruptedException ie) {
					}
				}	}	
			}).start();
			
	}
	
	public void xix(int i){
		loading.setVisible(true);
		// loading ������
		if(i==0)
			load[i].setBounds(120, 5, 60, 30);
		else	
			load[i].setBounds(170+i*20, 5, 20, 30);
		load[i].setVisible(true);
		loading.repaint();
		loginFrame.repaint();
		System.out.println("running");
	
			
	}
	
	// login automatically
	public void automaticLogin() {
		
		if (autoLogin.isSelected()) {

			sure.doClick();
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {

			}
		}
	}

	// initialization
	public void initial() {

		// ��ʼ����¼�˻����������Ϣ���Ƿ��Զ���¼

		rememberPassword.setSelected(true);
		autoLogin.setSelected(true);

		// .setFocusPainted(true);
		// .setContentAreaFilled(true);
		// .setBorderPainted(true);

		BufferedReader readUserInfo, readUserSetting;
		String users;
		readUserInfo = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream("/configure/initial/userInfo")));
		readUserSetting = new BufferedReader(new InputStreamReader(getClass()
				.getResourceAsStream("/configure/initial/userSetting")));

		ArrayList<String> username = new ArrayList<String>();
		ArrayList<String> password = new ArrayList<String>();
		String[] string;
		try {
			readUserSetting.readLine();
			while ((users = readUserInfo.readLine()) != null) {
				string = users.split(";");
				System.out.println(users);
				model.addElement(string[0]);
				if (string.length == 2) {
					passwordtf.setText(string[1]);
					password.add(string[1]);
				}
				username.add(string[0]);

			}
			readUserInfo.close();
			readUserSetting.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop() {
		for (int i = 1; i <= 4; i++) {
			load[i].setVisible(false);
			loginFrame.repaint();
		}
	}

	// �����û�״̬,д�ļ�
	public void save(String content) {

		// for(int i=0;i<;)

	}
	
	
	// ��������¼�߼�
	class Enter implements MouseListener {

		@SuppressWarnings("unused")
		private String userName;
		@SuppressWarnings("unused")
		private String password;

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			sure.setEnabled(true);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			userName = model.getSelectedItem().toString();// usernametf.se;
			password = String.valueOf(passwordtf.getPassword());
			/*
			 * ���Ƶ� password=passwordtf.getText().trim();
			 */

			loginFrame.repaint();
			sure.setSelected(false);
			sure.setFocusPainted(false);
			sure.setOpaque(false);
			sure.setVerifyInputWhenFocusTarget(false);
			loading();

			// com.wenxin.bl.Login login=new com.wenxin.bl.Login();
			// Toolkit.getDefaultToolkit().beep();

			/*
			 * if(login.login(userName, password)){ System.out.println("xixi");
			 * if(rememberPassword.isSelected())
			 * save(userName+";"+password+"\n"); else save(userName+";\n");
			 * 
			 * @SuppressWarnings("unused") MainMenu mainUI=new MainMenu();
			 * loginFrame.dispose(); // name=userName; } else{
			 * JOptionPane.showMessageDialog(null,login.loginState ,
			 * "login Erro", JOptionPane.WARNING_MESSAGE);
			 * //JOptionPane.showMessageDialog
			 * (null,login.loginState,"login Erro",JOptionPane.ERROR_MESSAGE); }
			 */
		}
	}

	// ��������պ�����û���������ʾ

	class ClearInfo implements FocusListener {
		private JTextField jtf;

		// private String text;
		public ClearInfo(JTextField jtf) {
			this.jtf = jtf;
			// text=jtf.getText();
		}

		@Override
		public void focusGained(FocusEvent arg0) {
			// TODO Auto-generated method stub
			if (jtf.getText().equals("����")) {
				jtf.setText(null);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			if (jtf.getText().equals("") || jtf.getText() == null) {
				jtf.setText("����");
				passwordtf.setEchoChar('\0');
			}
		}
	}

	// �������һ����룬ע���˺�
	class Register_Fetch implements MouseListener {
		JLabel jlabel;
		String text;

		public Register_Fetch(JLabel label) {
			jlabel = label;

			text = label.getText();
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

			loginFrame.dispose();
			// RegisterFetchPassword signIn=new RegisterFetchPassword();

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			jlabel.setOpaque(true);
			jlabel.setForeground(new Color(10, 100, 225));
			// jlabel.setBackground(Color.WHITE);
			jlabel.setText(text);
			// repaint();
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			jlabel.setForeground(null);
			jlabel.setText(text);
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	

	private class Minimize_Close implements MouseListener {

		private JLabel label;
		private JFrame window;
		Point point;

		public Minimize_Close(JLabel label, JFrame window) {
			label.setBackground(colorBeforeClicked);
			this.label = label;
			this.window = window;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			reference.setVisible(false);
			if (label == close) {
				window.setVisible(false);
			} else if (label == minx) {
				// window.setMaximumSize();
				window.setState(JFrame.ICONIFIED);
				// window.setVisible(false);
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			point = MouseInfo.getPointerInfo().getLocation();

			// System.out.println(label.getBounds());

			if (label == minx) {
				refInfo.setText("��С��");
				reference.setBounds(point.x, point.y + 10, 45, 20);
			} else if (label == close) {
				refInfo.setText("�ر�");
				reference.setBounds(point.x, point.y + 10, 30, 20);
			}
			;// window.setBounds(x, y, width, height);
				// window.setBounds();
			reference.setVisible(true);
			label.setBackground(colorAfterClicked);

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			reference.setVisible(false);
			// theme.set;
			// theme=new JLabel(new ImageIcon(themeBefore));
			// window.repaint();
			label.setBackground(colorBeforeClicked);
			// themeWindow.setVisible(false);
			window.repaint();

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

			reference.setVisible(false);
			if (label == close)
				label.setBounds(windowWidth - 28, 0, 28, 30);
			else if (label == minx)
				label.setBounds(windowWidth - 56, 0, 28, 30);

			// else(operation.equals("minx"))
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			if (label == close)
				label.setBounds(windowWidth - 28, 0, 28, 28);
			else if (label == minx)
				label.setBounds(windowWidth - 56, 0, 28, 28);
			/*
			 * else {
			 * 
			 * MyFrame.colorBeforeClicked = new Color(127, 180, 241);
			 * MyFrame.colorAfterClicked = new Color(127, 180, 180);
			 * MyFrame.inUse = new Color(127, 180, 170);
			 * minx.setBackground(colorBeforeClicked);
			 * close.setBackground(colorBeforeClicked);
			 * 
			 * }
			 */

		}

	}

	public class MoveWindow extends MouseAdapter {

		private Point lastPoint = null;
		private JFrame window = null;

		public MoveWindow(JFrame window) {
			this.window = window;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			lastPoint = e.getLocationOnScreen();
		}

		@Override
		public void mouseDragged(MouseEvent e) {

			Point point = e.getLocationOnScreen();
			double offsetX = point.x - lastPoint.x;
			double offsetY = point.y - lastPoint.y;
			Rectangle bounds = window.getBounds();
			bounds.x += offsetX;
			bounds.y += offsetY;
			window.setBounds(bounds);
			lastPoint = point;
			window.repaint();
		}
	}
}

/*
 * ʹ��DefaultComboBoxModel�� DefaultComboBoxModel<String> model = new
 * DefaultComboBox<String>(); model.addElement("�Զ���"); JComboBox<String> combo =
 * new JComboBox<String>(model);
 */

/*
 * ������ class ClearInfo implements MouseListener{ private JTextField textField ;
 * private String text; public ClearInfo(JTextField textf){
 * //if(jtf.getText().equals(null)) textField=textf; text=textField.getText(); }
 * 
 * public void mouseClicked(MouseEvent e) {
 * if(textField.getText().equals("�û���")||textField.getText().equals("����"))
 * textField.setText(null); //adaptee.txt1_keyReleased(e); }
 * 
 * @Override public void mouseEntered(MouseEvent e) { // TODO Auto-generated
 * method stub //textField.setText(text); }
 * 
 * @Override public void mouseExited(MouseEvent e) { // TODO Auto-generated
 * method stub if(textField.getText()==null||textField.getText().equals(""))
 * textField.setText(text); }
 * 
 * @Override public void mousePressed(MouseEvent e) { // TODO Auto-generated
 * method stub //textField.setText(text); }
 * 
 * @Override public void mouseReleased(MouseEvent e) { // TODO Auto-generated
 * method stub //textField.setText(text); } }
 */
