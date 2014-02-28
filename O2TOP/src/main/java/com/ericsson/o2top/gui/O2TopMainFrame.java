package com.ericsson.o2top.gui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;

import com.ericsson.drawing.O2LayOut;
import com.ericsson.o2top.O2Builder;
import com.ericsson.o2top.command.BrctlShow;
import com.ericsson.o2top.command.Ifconfig;
import com.ericsson.o2top.command.NovaList;
import com.ericsson.o2top.command.OvsVsctlShow;
import com.ericsson.o2top.command.QuantumNetList;
import com.ericsson.o2top.command.QuantumPortList;
import com.ericsson.o2top.command.QuantumSubnetList;
import com.ericsson.o2top.session.SessionSsh;

public class O2TopMainFrame extends JFrame {
	private static final long serialVersionUID = -6930928744958941328L;
	JScrollPane mTopologyPanel;
	MyDrawPanel mTestPanel;

	private static final String HOST_NAME = "127.0.0.1";
	private static final String USER_NAME = "localadmin";
	private static final String PASS_WORD = "3r1cs50n";
	private static final String PORT = "6666";

	private String mHost;
	private String mUser;
	private String mPassword;
	private int mPort;
	private JDialog mDialog;
	private JTextField mHostTextField;
	private JTextField mUserTextField;
	private JPasswordField mPassField;
	private JTextField mPortTextField;

	public O2TopMainFrame() throws HeadlessException {
		mHost = HOST_NAME;
		mUser = USER_NAME;
		mPassword = PASS_WORD;
		mPort = Integer.parseInt(PORT);
		initComponents();
	}

	private void initComponents() {
		// Init menu bar
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu();
		JMenuItem itemConfig = new JMenuItem();
		JMenuItem itemDraw = new JMenuItem();
		JMenuItem itemZoomin = new JMenuItem();
		JMenuItem itemZoomout = new JMenuItem();
		JMenuItem itemExit = new JMenuItem();

		fileMenu.setText("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		itemConfig.setText("Config");
		itemConfig.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		itemConfig.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mDialog = new JDialog();
				mDialog.setTitle("Configuration");

				JPanel configPanel = new JPanel();
				configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.LINE_AXIS));

				configPanel.setBorder(BorderFactory.createEtchedBorder());
				configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.LINE_AXIS));

				JLabel hostLabel = new JLabel();
				hostLabel.setText("Host: ");
				configPanel.add(hostLabel);

				mHostTextField = new JTextField();
				mHostTextField.setText(HOST_NAME);
				configPanel.add(mHostTextField);

				JLabel userLabel = new JLabel();
				userLabel.setText("User: ");
				configPanel.add(userLabel);

				mUserTextField = new JTextField();
				mUserTextField.setText(USER_NAME);
				mUserTextField.setMaximumSize(new Dimension(200, 2147483647));
				mUserTextField.setPreferredSize(new Dimension(100, 21));
				configPanel.add(mUserTextField);

				JLabel passLabel = new JLabel();
				passLabel.setText("Password: ");
				configPanel.add(passLabel);

				mPassField = new JPasswordField();
				mPassField.setText(PASS_WORD);
				mPassField.setMaximumSize(new Dimension(200, 2147483647));
				mPassField.setPreferredSize(new Dimension(100, 21));
				configPanel.add(mPassField);

				JLabel portLabel = new JLabel();
				portLabel.setText("Port: ");
				configPanel.add(portLabel);

				mPortTextField = new JTextField();
				mPortTextField.setText(PORT);
				mPortTextField.setMaximumSize(new Dimension(100, 2147483647));
				mPortTextField.setPreferredSize(new Dimension(50, 21));
				configPanel.add(mPortTextField);

				JButton confirmButton = new JButton();
				confirmButton.setText("OK");
				confirmButton.setFocusable(false);
				confirmButton.setHorizontalTextPosition(SwingConstants.CENTER);
				confirmButton.setVerticalTextPosition(SwingConstants.BOTTOM);
				confirmButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent evt) {
						mHost = mHostTextField.getText();
						mUser = mUserTextField.getText();
						mPassword = mPassField.getText();
						mPort = Integer.parseInt(mPortTextField.getText());
						mDialog.dispose();
					}
				});
				configPanel.add(confirmButton);

				mDialog.add(configPanel);

				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				mDialog.setBounds((screenSize.width - 600) / 2, (screenSize.height - 70) / 2, 600, 70);
				mDialog.setVisible(true);
			}
		});
		fileMenu.add(itemConfig);

		itemDraw.setText("Draw");
		itemDraw.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_MASK));
		itemDraw.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					SessionSsh mSession = new SessionSsh(mUser, mPassword, mHost, mPort, 300);

					O2Builder builder = new O2Builder();

					NovaList novaList = new NovaList(mSession);
					novaList.exec();
					builder.setVms(novaList.getVmVector());

					QuantumPortList portlist = new QuantumPortList(mSession);
					portlist.exec();
					builder.setPorts(portlist.getPortVector());

					QuantumSubnetList subnetlist = new QuantumSubnetList(mSession);
					subnetlist.exec();
					builder.setSubnets(subnetlist.getSubnetVector());

					QuantumNetList netlist = new QuantumNetList(mSession);
					netlist.exec();
					builder.setNets(netlist.getNetVector());

					OvsVsctlShow vsctlshow = new OvsVsctlShow(mSession, mPassword);
					vsctlshow.exec();
					builder.setBridges_vsctl(vsctlshow.getBridgeVector());

					Ifconfig ifconfig = new Ifconfig(mSession);
					ifconfig.exec();
					builder.setInterfaces(ifconfig.getNetInterfaceVector());

					BrctlShow brctlshow = new BrctlShow(mSession);
					brctlshow.exec();
					builder.setBridgesBrctl(brctlshow.getBridgeVector());
					builder.buildVMs();
					mSession.close();
					mTestPanel.set_builder(builder);
				} catch (Exception ee) {
					ee.printStackTrace();
					return;
				}

				repaint();
			}
		});
		fileMenu.add(itemDraw);

		itemZoomin.setText("Zoomin");
		itemZoomin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, InputEvent.CTRL_MASK));
		itemZoomin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				O2LayOut.factor = O2LayOut.factor + 1;
				repaint();
			}
		});
		fileMenu.add(itemZoomin);

		itemZoomout.setText("Zoomout");
		itemZoomout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, InputEvent.CTRL_MASK));
		itemZoomout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				O2LayOut.factor = O2LayOut.factor - 1;
				repaint();
			}
		});
		fileMenu.add(itemZoomout);

		itemExit.setText("Exit");
		itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.CTRL_MASK));
		itemExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
			}
		});
		fileMenu.add(itemExit);

		JMenu helpMenu = new JMenu();
		JMenuItem itemAbout = new JMenuItem();

		helpMenu.setText("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);

		itemAbout.setText("About");
		itemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialog = new JDialog();
				dialog.setTitle("About");

				JTextArea text = new JTextArea();
				text.append("Copyright: Copyright (c) 2014\n");
				text.append("Company: Ericsson\n");
				text.append("PDU EI Created");

				dialog.add(text);

				Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
				dialog.setBounds((screenSize.width - 320) / 2, (screenSize.height - 240) / 2, 320, 240);
				dialog.setVisible(true);
			}
		});
		helpMenu.add(itemAbout);

		menuBar.add(fileMenu);
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);

		// Init main windows
		mTopologyPanel = new JScrollPane();
		mTopologyPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		mTopologyPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		mTestPanel = new MyDrawPanel();
		// mTopologyPanel.add(mTestPanel);

		getContentPane().add(mTestPanel);
		super.setTitle("O2Top");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setBounds((screenSize.width - 800) / 2, (screenSize.height - 600) / 2, 800, 600);
	}

	public JScrollPane getTopologyPanel() {
		return mTopologyPanel;
	}
}
