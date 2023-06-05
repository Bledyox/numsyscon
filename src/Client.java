import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Client extends JFrame implements ActionListener, Runnable {

	private final String[] language_list = { "de", "en" };

	private boolean runflag;
	private String langflag = language_list[0];

	private JMenuBar menubar;
	private JMenu[] menu_list;
	private ButtonGroup submenu_list_1_group;
	private JRadioButtonMenuItem[] submenu_list_1;
	private JMenuItem submenu_2;
	private JPanel[] panel_list;
	private JTextField[] textfield_list;
	private JComboBox<String>[] combobox_list;
	private JButton button;

	@SuppressWarnings("unchecked")
	public Client() {
		this.setTitle(Messages.getString("title", ""));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(360, 180));
		menubar = new JMenuBar();
		menu_list = new JMenu[2];
		menu_list[0] = new JMenu(Messages.getString("ui.menu.language", langflag));
		menu_list[1] = new JMenu(Messages.getString("ui.menu.help", langflag));
		for (JMenu buffer : menu_list) {
			menubar.add(buffer);
		}
		submenu_list_1_group = new ButtonGroup();
		submenu_list_1 = new JRadioButtonMenuItem[language_list.length];
		for (int i = 0; i < language_list.length; i++) {
			submenu_list_1[i] = new JRadioButtonMenuItem(Messages.getString("messages.language", language_list[i]));
			submenu_list_1[i].addActionListener(this);
			submenu_list_1_group.add(submenu_list_1[i]);
			menu_list[0].add(submenu_list_1[i]);
		}
		submenu_list_1[0].setSelected(true);
		submenu_2 = new JMenuItem(Messages.getString("ui.submenu.about", langflag));
		submenu_2.addActionListener(this);
		menu_list[1].add(submenu_2);
		this.setJMenuBar(menubar);
		panel_list = new JPanel[3];
		panel_list[0] = new JPanel();
		panel_list[1] = new JPanel();
		panel_list[2] = new JPanel();
		this.add(panel_list[0]);
		panel_list[0].add(panel_list[1]);
		panel_list[0].add(panel_list[2]);
		textfield_list = new JTextField[2];
		for (int i = 0; i < textfield_list.length; i++) {
			textfield_list[i] = new JTextField(15);
			textfield_list[i].setFont(
					new Font(textfield_list[0].getFont().getFontName(), textfield_list[0].getFont().getStyle(), 15));
			textfield_list[i].setHorizontalAlignment(SwingConstants.CENTER);
		}
		textfield_list[1].setEditable(false);
		panel_list[1].add(textfield_list[0]);
		panel_list[2].add(textfield_list[1]);
		combobox_list = new JComboBox[2];
		for (int i = 0; i < combobox_list.length; i++) {
			combobox_list[i] = new JComboBox<>(Messages.getStringPack("numeralsystem.", 1, 36, langflag));
			combobox_list[i].setFont(
					new Font(combobox_list[i].getFont().getFontName(), combobox_list[i].getFont().getStyle(), 10));
		}
		combobox_list[0].setSelectedIndex(Integer.parseInt(Messages.getString("numeralsystem.input", "")) -1);
		combobox_list[1].setSelectedIndex(Integer.parseInt(Messages.getString("numeralsystem.output", "")) -1);
		panel_list[1].add(combobox_list[0]);
		panel_list[2].add(combobox_list[1]);
		button = new JButton(Messages.getString("ui.button.convert", langflag));
		button.addActionListener(this);
		panel_list[0].add(button);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		runflag = true;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == button) {
			String input = textfield_list[0].getText();
			int from = combobox_list[0].getSelectedIndex() + 1;
			int to = combobox_list[1].getSelectedIndex() + 1;
			try {
				textfield_list[1].setText(NumeralSystemConverter.parse(input, from, to));
			} catch (NumeralSystemConverterException ex) {
				JOptionPane.showMessageDialog(this, Messages.getString(ex.getMessage(), langflag), "",
						JOptionPane.ERROR_MESSAGE);
			}
		} else if (event.getSource() == submenu_2) {
			JOptionPane.showMessageDialog(this,
					"<html><body width='200'><h2>" + Messages.getString("title", "") + "</h2>"
							+ Messages.getString("subtitle", "") + "<br>" + Messages.getString("author", "") + "<br>"
							+ Messages.getString("version", "") + "</body></html>");
		}
		for (int i = 0; i < language_list.length; i++) {
			if (event.getSource() == submenu_list_1[i]) {
				if (langflag != language_list[i]) {
					langflag = language_list[i];
					switchLanguage();
				}
			}
		}
	}

	@Override
	public void run() {
		while (runflag) {}
	}

	private void switchLanguage() {
		menu_list[0].setText(Messages.getString("ui.menu.language", langflag));
		menu_list[1].setText(Messages.getString("ui.menu.help", langflag));
		for (int i = 0; i < language_list.length; i++) {
			submenu_list_1[i].setText(Messages.getString("messages.language", language_list[i]));
		}
		submenu_2.setText(Messages.getString("ui.submenu.about", langflag));
		for (int i = 0; i < combobox_list.length; i++) {
			int index = combobox_list[i].getSelectedIndex();
			combobox_list[i].removeAllItems();
			for (String buffer : Messages.getStringPack("numeralsystem.", 1, 36, langflag)) {
				combobox_list[i].addItem(buffer);
			}
			combobox_list[i].setSelectedIndex(index);
		}
		button.setText(Messages.getString("ui.button.convert", langflag));
	}

}
