package test;

import java.awt.EventQueue;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import com.toedter.calendar.JDateChooser;

import beans.Smartphone;
import beans.User;
import smartphoneEJB.SmartphoneRemote;
import userEJB.UserRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.JComboBox;

public class myGUIS {

	private JFrame frame;
	private JTextField nameField;
	private JTextField prenomField;
	private JTextField telField;
	private JTextField emailField;
	private static UserRemote userRemote;
	private static SmartphoneRemote phoneRemote;
	private JTable table;
	private User selectedUser = null;
	private Smartphone selectedPhone = null;
	private JTextField refField;
	private JTextField snameField;
	private JTextField marqueField;
	private JTable table_1;
	private String users;

	public static void UserLookup() throws NamingException {
		Hashtable<Object, Object> conf = new Hashtable<Object, Object>();
		conf.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		conf.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(conf);
		userRemote = (UserRemote) context.lookup("ejb:/GpsProjectServer/UserSession!userEJB.UserRemote");
	}

	public static void PhoneLookup() throws NamingException {
		Hashtable<Object, Object> conf = new Hashtable<Object, Object>();
		conf.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
		conf.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
		final Context context = new InitialContext(conf);
		phoneRemote = (SmartphoneRemote) context
				.lookup("ejb:/GpsProjectServer/SmartphoneSession!smartphoneEJB.SmartphoneRemote");
	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					myGUIS window = new myGUIS();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public myGUIS() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		try {
			UserLookup();
			PhoneLookup();
		} catch (NamingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		frame.setBounds(100, 100, 742, 376);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 11, 716, 315);
		frame.getContentPane().add(tabbedPane);

		JPanel userPanel = new JPanel();
		tabbedPane.addTab("User", null, userPanel, null);
		userPanel.setLayout(null);

		JLabel lblNewLabel = new JLabel("nom");
		lblNewLabel.setBounds(23, 36, 48, 14);
		userPanel.add(lblNewLabel);

		nameField = new JTextField();
		nameField.setBounds(96, 33, 96, 20);
		userPanel.add(nameField);
		nameField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("prenom");
		lblNewLabel_1.setBounds(23, 80, 48, 14);
		userPanel.add(lblNewLabel_1);

		prenomField = new JTextField();
		prenomField.setBounds(96, 77, 96, 20);
		userPanel.add(prenomField);
		prenomField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("telephone");
		lblNewLabel_2.setBounds(23, 131, 64, 14);
		userPanel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("email");
		lblNewLabel_3.setBounds(23, 174, 48, 14);
		userPanel.add(lblNewLabel_3);

		telField = new JTextField();
		telField.setBounds(96, 128, 96, 20);
		userPanel.add(telField);
		telField.setColumns(10);

		emailField = new JTextField();
		emailField.setBounds(96, 171, 96, 20);
		userPanel.add(emailField);
		emailField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Date");
		lblNewLabel_4.setBounds(23, 218, 48, 14);
		userPanel.add(lblNewLabel_4);

		JDateChooser dateField = new JDateChooser();
		dateField.setDateFormatString("yyyy-MM-dd");
		dateField.setBounds(96, 212, 96, 20);
		userPanel.add(dateField);

		JButton btnSave = new JButton("save");
		btnSave.addActionListener(e -> {
			userRemote.save(new User(nameField.getText(), prenomField.getText(), telField.getText(),
					emailField.getText(), dateField.getDate()));
		});
		btnSave.setBounds(118, 241, 89, 23);
		userPanel.add(btnSave);

		JButton btnDelete = new JButton("delete");
		btnDelete.addActionListener(e -> {
			if (selectedUser != null)
				userRemote.deleteById(selectedUser.getId());

		});
		btnDelete.setBounds(231, 241, 89, 23);
		userPanel.add(btnDelete);

		JButton btnUpdate = new JButton("update");
		btnUpdate.addActionListener(e -> {
			userRemote.update(new User(nameField.getText(), prenomField.getText(), telField.getText(),
					emailField.getText(), dateField.getDate()));
		});
		btnUpdate.setBounds(349, 241, 89, 23);
		userPanel.add(btnUpdate);
		String[] columns = new String[] { "id", "name", "prenom", "email", "telephone", "date" };

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(303, 33, 376, 169);
		userPanel.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "id", "nom", "prenom", "email", "telephone", "date" }));
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Long selectedId = (Long) table.getModel().getValueAt(table.getSelectedRow(), 0);
				selectedUser = userRemote.findById(selectedId);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		updateUserTableData(table);

		JPanel smartphonePanel = new JPanel();
		tabbedPane.addTab("smartphone", null, smartphonePanel, null);
		smartphonePanel.setLayout(null);

		JLabel lblNewLabel_5 = new JLabel("ref");
		lblNewLabel_5.setBounds(28, 31, 48, 14);
		smartphonePanel.add(lblNewLabel_5);

		refField = new JTextField();
		refField.setBounds(99, 28, 96, 20);
		smartphonePanel.add(refField);
		refField.setColumns(10);

		JLabel lblNewLabel_6 = new JLabel("name");
		lblNewLabel_6.setBounds(28, 78, 48, 14);
		smartphonePanel.add(lblNewLabel_6);

		snameField = new JTextField();
		snameField.setBounds(99, 75, 96, 20);
		smartphonePanel.add(snameField);
		snameField.setColumns(10);

		JLabel lblNewLabel_7 = new JLabel("marque");
		lblNewLabel_7.setBounds(28, 131, 48, 14);
		smartphonePanel.add(lblNewLabel_7);

		marqueField = new JTextField();
		marqueField.setBounds(99, 128, 96, 20);
		smartphonePanel.add(marqueField);
		marqueField.setColumns(10);

		JComboBox userComboBox = new JComboBox();
		for (User u : userRemote.findAll()) {
			userComboBox.addItem(u.getEmail());
		}
		userComboBox.addItem(users);
		userComboBox.setBounds(99, 164, 96, 22);
		smartphonePanel.add(userComboBox);

		JButton btnSaveSbutton = new JButton("save");
		btnSaveSbutton.addActionListener(e -> {
			phoneRemote.save(new Smartphone(refField.getText(), snameField.getText(), marqueField.getText(),
					userRemote.findByEmail(userComboBox.getModel().getSelectedItem().toString())));
		});
		btnSaveSbutton.setBounds(28, 232, 89, 23);
		smartphonePanel.add(btnSaveSbutton);

		JButton btnDeleteSbutton = new JButton("delete");
		btnDeleteSbutton.setBounds(160, 232, 89, 23);
		smartphonePanel.add(btnDeleteSbutton);

		JButton btnUpdateSbutton = new JButton("update");
		btnUpdateSbutton.setBounds(290, 232, 89, 23);
		smartphonePanel.add(btnUpdateSbutton);

		JLabel lblNewLabel_8 = new JLabel("User");
		lblNewLabel_8.setBounds(28, 168, 48, 14);
		smartphonePanel.add(lblNewLabel_8);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(269, 31, 384, 157);
		smartphonePanel.add(scrollPane_1);

		table_1 = new JTable();
		scrollPane_1.setViewportView(table_1);
		table_1.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "id", "ref", "name", "marque", "user" }));
		table_1.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				String selectedRef = table_1.getModel().getValueAt(table_1.getSelectedRow(), 0).toString();
				Smartphone sp = phoneRemote.findByRef(selectedRef);
				if (sp != null) {
					refField.setText(sp.getRef());
					snameField.setText(sp.getName());
					marqueField.setText(sp.getMarque());
					if (sp.getUser() == null) {
						userComboBox.getModel().setSelectedItem(userComboBox.getItemAt(0));
					} else {
						for (int i = 0; i < userComboBox.getItemCount(); i++) {
							if (userComboBox.getItemAt(i) != null) {
								if (userComboBox.getItemAt(i).toString().equals(sp.getUser().getEmail())) {
									userComboBox.getModel().setSelectedItem(userComboBox.getItemAt(i));
								}
							}
						}
					}
					selectedPhone = sp;
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		updatePhoneTableData(table_1);
	}

	private void updateUserTableData(JTable table) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.fireTableDataChanged();
		dtm.setRowCount(0);
		for (User u : userRemote.findAll()) {
			dtm.addRow(new Object[] { u.getId(), u.getNom(), u.getPrenom(), u.getTelephone(), u.getEmail(),
					u.getdateN() });
		}
	}

	private void updatePhoneTableData(JTable table) {
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setRowCount(0);
		for (Smartphone p : phoneRemote.findAll()) {
			dtm.addRow(new Object[] { p.getRef(), p.getName(), p.getMarque(),
					 });
		}
	}
}
