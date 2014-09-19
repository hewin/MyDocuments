
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
 * function:1.登录窗口
 * 2.登陆进度条；
 * 3.修改密码等按钮的变色；
 * 4.自定义的登陆界面
 * 5.8个方法，多个内部类，多线程
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
	/** 线程循环标志* */
	private boolean mIsRunning = false;
	private Thread login, loadThread;

	public Login() {

		this.windowWidth = 430;
		this.windowHeight = 320;

		// 创建参数对象

		userPictureUrl = getClass().getResource(
				"/configure/pictures/login/user.jpg");
		logoUrl = getClass().getResource("/configure/pictures/login/logo.png");

		// System.out.println(userPictureUrl);

		// 创建控件对象
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
		register = new JLabel("注册账号");
		fetchPassword = new JLabel("找回密码");
		model = new DefaultComboBoxModel<String>();
		usernametf = new JComboBox<String>(model);
		// usernametf.enableInputMethods(false);
		// windows中没有对应项
		// 什么作用？？
		// DefaultCellEditor dce=new DefaultCellEditor(usernametf);

		// usernametf.add
		passwordtf = new JPasswordField();
		passwordtf.setEchoChar('*');
		passwordtf.setText("密码");
		//passwordtf.setEchoChar('\0');
		// passwordtf.setEchoChar('*');
		rememberPassword = new JRadioButton("记住密码");
		autoLogin = new JRadioButton("自动登录");
		sure = new JButton("登录");
		// sure.setBorderPainted(false);

		// 初始化登陆信息
		// Thread.currentThread().getContextClassLoader().getResource("configure/initial/userInfo").getPath();
		// configUrl=getClass().getResource("/configure/initial");
		// System.out.println(userSetting);
		// userSetting=Thread.currentThread().getContextClassLoader().getResource("configure/initial/userSetting").getPath();
		initial();

		// UIManager.put("JButton.Font",new Font("楷体",Font.PLAIN,14));
		// UIManager.put("JLabel.font",new Font("楷体",Font.PLAIN,14));
		// UIManager.put("JPasswordField.font",new Font("楷体",Font.PLAIN,14));
		// UIManager.put("JTextField.font",new Font("楷体",Font.PLAIN,14));

		/*
		 * Component[] comp=loginFrame.getComponents(); for(Component cp:comp){
		 * cp.setFont(new Font("楷体",Font.PLAIN,14)); }
		 */

		loginFrame.setLayout(null);

		// 初始化参数
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
		// 初始化组件
		reference = new JWindow();
		refInfo = new JLabel();

		minx = new JLabel(new ImageIcon(minxUrl));
		close = new JLabel(new ImageIcon(closeUrl));

		// 最小化label设置
		minx.setOpaque(true);
		// minx.setBackground(colorBeforeClicked);
		minx.setBounds(windowWidth - 56, 0, 28, 28);

		// 关闭label设置
		close.setOpaque(true);
		// close.setBackground(colorBeforeClicked);
		close.setBounds(windowWidth - 28, 0, 28, 28);

		// label功能提示窗口
		refInfo.setBorder(BorderFactory.createLineBorder(Color.lightGray, 1));
		refInfo.setOpaque(true);
		refInfo.setBackground(Color.WHITE);
		// refInfo.setForeground(Color.BLACK);
		refInfo.setFont(new Font("仿宋", Font.PLAIN, 14));
		reference.setLayout(new BorderLayout());
		reference.add(refInfo, BorderLayout.CENTER);
		// reference.setUndecorated(true);
		reference.setVisible(false);

		// 组件属性
		// refInfo.BorderWidth="1px";
		// Border border=new B;//new Border("1px");

		// frame属性设置
		loginFrame.setBounds(screenWidth / 2 - this.windowWidth / 2,
				screenHeight / 2 - this.windowHeight / 2, this.windowWidth,
				this.windowHeight);
		loginFrame.setUndecorated(true);
		loginFrame.setIconImage((new ImageIcon(selogoUrl)).getImage());

		// 向logo的label中添加最小化和关闭按钮
		loginFrame.add(minx);
		loginFrame.add(close);
		// this.add(theme);

		// 最小化，关闭，主题设置监听
		close.addMouseListener(new Minimize_Close(close, loginFrame));
		minx.addMouseListener(new Minimize_Close(minx, loginFrame));

		// 一个对象，两个监听，窗口拖动监听
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

		// 设置字体样式
		// loginPanel.setFont(new Font("楷体",Font.PLAIN,14));
		register.setFont(new Font("楷体", Font.PLAIN, 14));
		fetchPassword.setFont(new Font("楷体", Font.PLAIN, 14));
		usernametf.setFont(new Font("楷体", Font.PLAIN, 14));
		passwordtf.setFont(new Font("楷体", Font.PLAIN, 14));
		rememberPassword.setFont(new Font("楷体", Font.PLAIN, 14));
		autoLogin.setFont(new Font("楷体", Font.PLAIN, 14));
		load[0].setFont(new Font("黑体", Font.HANGING_BASELINE, 16));
		sure.setFont(new Font("楷体", Font.PLAIN, 14));

		// 控件、frame、panel属性设置
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

		// 设置布局管理器
		loginPanel.setLayout(null);
		loading.setLayout(null);
		// loginFrame.setLayout(null);
		// loginPanel.setLayout(new GridLayout(3,2,50,40));

		// 向登录loginFrame窗口中添加控件

		// 向loginPanel面板中添加组件
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

		// loading panel组件
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

		// 设置frame、panel、控件位置

		// 主窗口430*320
		logo.setBounds(0, 0, 430, 160);
		loginPanel.setBounds(0, 160, 430, 120);

		// loginPanel组件
		loading.setBounds(0, 280, 430, 40);
		userPicture.setBounds(15, 10, 90, 90);
		usernametf.setBounds(120, 10, 200, 20);
		register.setBounds(330, 10, 60, 20);
		passwordtf.setBounds(120, 40, 200, 20);
		fetchPassword.setBounds(330, 40, 60, 20);
		rememberPassword.setBounds(120, 70, 100, 20);
		autoLogin.setBounds(220, 70, 100, 20);
		sure.setBounds(120, 100, 200, 20);

		// loading 面板组件
		load[0].setBounds(100, 5, 60, 30);
		load[1].setBounds(190, 5, 20, 30);
		load[2].setBounds(210, 5, 20, 30);
		load[3].setBounds(230, 5, 20, 30);
		load[4].setBounds(250, 5, 20, 30);
		loadingFlash.setBounds(170, 17, 150, 10);

		/*
		 * 添加监听
		 */
		passwordtf.addFocusListener(new ClearInfo(passwordtf));
		register.addMouseListener(new Register_Fetch(register));
		fetchPassword.addMouseListener(new Register_Fetch(fetchPassword));
		sure.addMouseListener(new Enter());
		// sure.addActionListener(new Enter());
		// sure.setFocusPainted(false);

		// usernametf.addFocusListener(new ClearInfo(usernametf));

		// 显示、关闭设置
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

	// 登陆加载动画
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
		// loading 面板组件
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

		// 初始化登录账户，密码等信息，是否自动登录

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

	// 保存用户状态,写文件
	public void save(String content) {

		// for(int i=0;i<;)

	}
	
	
	// 监听：登录逻辑
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
			 * 类似的 password=passwordtf.getText().trim();
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

	// 监听：清空和填充用户名密码提示

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
			if (jtf.getText().equals("密码")) {
				jtf.setText(null);
			}
		}

		@Override
		public void focusLost(FocusEvent arg0) {
			// TODO Auto-generated method stub
			if (jtf.getText().equals("") || jtf.getText() == null) {
				jtf.setText("密码");
				passwordtf.setEchoChar('\0');
			}
		}
	}

	// 监听：找回密码，注册账号
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
				refInfo.setText("最小化");
				reference.setBounds(point.x, point.y + 10, 45, 20);
			} else if (label == close) {
				refInfo.setText("关闭");
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
 * 使用DefaultComboBoxModel： DefaultComboBoxModel<String> model = new
 * DefaultComboBox<String>(); model.addElement("自动化"); JComboBox<String> combo =
 * new JComboBox<String>(model);
 */

/*
 * 鼠标监听 class ClearInfo implements MouseListener{ private JTextField textField ;
 * private String text; public ClearInfo(JTextField textf){
 * //if(jtf.getText().equals(null)) textField=textf; text=textField.getText(); }
 * 
 * public void mouseClicked(MouseEvent e) {
 * if(textField.getText().equals("用户名")||textField.getText().equals("密码"))
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
