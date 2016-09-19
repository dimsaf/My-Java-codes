
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ClientGUI extends JFrame {

	private static final long serialVersionUID = 553958465686826130L;
	private JPanel contentPane;
	private JTextField textField1, textField2, textField3;
	private String serverMessage;
	private File file;

	/**
	 * Create the frame.
	 */
	public ClientGUI() throws IOException {
		ClientEvents window = new ClientEvents();

		setTitle("Getting file from Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 502);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel label1 = new JLabel("Messages from server");
		label1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label1.setBounds(15, 20, 370, 30);
		contentPane.add(label1);

		JLabel label2 = new JLabel("My messages to server");
		label2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label2.setBounds(15, 120, 370, 30);
		contentPane.add(label2);

		JLabel label3 = new JLabel("");
		label3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		label3.setBounds(15, 218, 370, 30);
		contentPane.add(label3);

		textField1 = new JTextField();
		textField1.setText("First of all, you must push Connect button");
		textField1.setEnabled(false);
		textField1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		textField1.setBounds(15, 60, 370, 50);
		textField1.setColumns(10);
		contentPane.add(textField1);

		JButton btnConnect = new JButton("Connect");
		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnConnect.setBounds(440, 60, 230, 50);
		contentPane.add(btnConnect);

		textField2 = new JTextField();
		textField2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField2.setColumns(10);
		textField2.setBounds(15, 155, 370, 50);
		contentPane.add(textField2);

		JButton btnSend = new JButton("Send my message");
		btnSend.setEnabled(false);
		btnSend.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSend.setBounds(440, 155, 230, 50);
		contentPane.add(btnSend);

		textField3 = new JTextField();
		textField3.setEnabled(false);
		textField3.setFont(new Font("Tahoma", Font.PLAIN, 16));
		textField3.setColumns(10);
		textField3.setBounds(15, 255, 310, 50);
		contentPane.add(textField3);

		JButton btnBrowse = new JButton("...");
		btnBrowse.setEnabled(false);
		btnBrowse.setFont(new Font("Tahoma", Font.BOLD, 25));
		btnBrowse.setBounds(325, 255, 61, 50);
		contentPane.add(btnBrowse);

		JButton btnOK = new JButton("OK");
		btnOK.setEnabled(false);
		btnOK.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnOK.setBounds(440, 255, 230, 50);
		contentPane.add(btnOK);

		JButton btnCancel = new JButton("Cancel");
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnCancel.setBounds(234, 377, 230, 50);
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		contentPane.add(btnCancel);

		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField1.setText(window.connect());
				btnConnect.setEnabled(false);
				btnSend.setEnabled(true);
				textField2.requestFocus();
			}
		});

		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverMessage = window.sendMessage(textField2.getText());
				textField1.setText(serverMessage);
				if (serverMessage.equalsIgnoreCase("Password is correct! Connected")) {
					textField2.setEnabled(false);
					btnSend.setEnabled(false);
					label3.setText("Choose file from server");
					textField3.setEnabled(true);
					btnBrowse.setEnabled(true);
				} else {
					textField2.setText("");
					textField2.requestFocus();
				}
			}
		});

		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					JFileChooser dialog = new JFileChooser();
					dialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
					dialog.showOpenDialog(dialog);
					file = dialog.getSelectedFile();
					if (file != null) {
						textField3.setText(file.getPath());
						btnOK.setEnabled(true);
					}
				} catch (NullPointerException npe) {
				}

			}
		});
		
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				serverMessage = window.transferFile(file.getPath(), file.getName());
				textField1.setText(serverMessage);
				btnOK.setEnabled(false);
			}
		});

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGUI frame = new ClientGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
