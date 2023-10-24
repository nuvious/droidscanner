import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.dyno.visual.swing.layouts.Bilateral;
import org.dyno.visual.swing.layouts.Constraints;
import org.dyno.visual.swing.layouts.GroupLayout;
import org.dyno.visual.swing.layouts.Leading;

import com.nuvsoft.android.scanner.settings.EventTrigger;
import com.nuvsoft.android.scanner.settings.LogAction;

public class SettingsGenerator extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lbl_logAction;
	private JButton b_addSettingsLine;
	private JList lst_eventTrigger;
	private JScrollPane sp_eventTrigger;
	private JList lst_logActions;
	private JScrollPane jsp_logAction;
	private JLabel lbl_eventTrigger;
	private JTextField tf_interval;
	private JLabel lbl_interval;
	private JLabel lbl_extraArgs;
	private JTextField jtf_extraArgs;
	private JTextArea output;
	private JScrollPane sp_output;
	private static final String PREFERRED_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";

	public SettingsGenerator() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GroupLayout());
		add(getLogActionLabel(), new Constraints(new Leading(12, 12, 12),
				new Leading(12, 12, 12)));
		add(getSettingsButton(), new Constraints(new Bilateral(13, 12, 81),
				new Leading(233, 12, 12)));
		add(getEventTriggerScrollPane(), new Constraints(new Leading(169, 142,
				12, 12), new Leading(33, 146, 10, 10)));
		add(getLogActionScrollPane(), new Constraints(new Leading(13, 144, 12,
				12), new Leading(33, 146, 12, 12)));
		add(getEventTriggerLabel(), new Constraints(new Leading(169, 12, 12),
				new Leading(12, 12, 12)));
		add(getInterval(), new Constraints(new Leading(13, 144, 12, 12),
				new Leading(207, 12, 12)));
		add(getIntervalLabel(), new Constraints(new Leading(13, 144, 12, 12),
				new Leading(185, 12, 12)));
		add(getExtraArgsLabel(), new Constraints(new Bilateral(169, 12, 41),
				new Leading(185, 12, 12)));
		add(getExtraArgs(), new Constraints(new Leading(169, 142, 12, 12),
				new Leading(207, 12, 12)));
		add(getOuputScrollPane(), new Constraints(new Bilateral(14, 10, 22),
				new Bilateral(266, 12, 22)));
		setSize(320, 362);
	}

	private JScrollPane getOuputScrollPane() {
		if (sp_output == null) {
			sp_output = new JScrollPane();
			sp_output.setViewportView(getOutput());
		}
		return sp_output;
	}

	private JTextArea getOutput() {
		if (output == null) {
			output = new JTextArea();
			output.setEditable(false);
			output.setText("");
		}
		return output;
	}

	private JTextField getExtraArgs() {
		if (jtf_extraArgs == null) {
			jtf_extraArgs = new JTextField();
			jtf_extraArgs.setText("");
		}
		return jtf_extraArgs;
	}

	private JLabel getExtraArgsLabel() {
		if (lbl_extraArgs == null) {
			lbl_extraArgs = new JLabel();
			lbl_extraArgs.setText("Extra Args");
		}
		return lbl_extraArgs;
	}

	private JLabel getIntervalLabel() {
		if (lbl_interval == null) {
			lbl_interval = new JLabel();
			lbl_interval.setText("Min Interval (ms)");
		}
		return lbl_interval;
	}

	private JTextField getInterval() {
		if (tf_interval == null) {
			tf_interval = new JTextField();
			tf_interval.setText("");
		}
		return tf_interval;
	}

	private JLabel getEventTriggerLabel() {
		if (lbl_eventTrigger == null) {
			lbl_eventTrigger = new JLabel();
			lbl_eventTrigger.setText("Event Trigger");
		}
		return lbl_eventTrigger;
	}

	private JScrollPane getLogActionScrollPane() {
		if (jsp_logAction == null) {
			jsp_logAction = new JScrollPane();
			jsp_logAction.setViewportView(getLogActions());
		}
		return jsp_logAction;
	}

	private JList getLogActions() {
		if (lst_logActions == null) {
			lst_logActions = new JList();
			DefaultListModel listModel = new DefaultListModel();
			for (LogAction a : LogAction.values()) {
				listModel.addElement(a);
			}
			lst_logActions.setModel(listModel);
		}
		return lst_logActions;
	}

	private JScrollPane getEventTriggerScrollPane() {
		if (sp_eventTrigger == null) {
			sp_eventTrigger = new JScrollPane();
			sp_eventTrigger.setViewportView(getEventTriggers());
		}
		return sp_eventTrigger;
	}

	private JList getEventTriggers() {
		if (lst_eventTrigger == null) {
			lst_eventTrigger = new JList();
			DefaultListModel listModel = new DefaultListModel();
			for (EventTrigger e : EventTrigger.values()) {
				listModel.addElement(e);
			}
			lst_eventTrigger.setModel(listModel);
		}
		return lst_eventTrigger;
	}

	private JButton getSettingsButton() {
		if (b_addSettingsLine == null) {
			b_addSettingsLine = new JButton();
			b_addSettingsLine.setText("Add Settings Line");
			b_addSettingsLine.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					try {
						long actionMask = 0;
						for (Object o : getLogActions().getSelectedValues()) {
							actionMask |= ((LogAction) o).getLogActionMask();
						}
						
						long eventMask = 0;
						for (Object o : getEventTriggers().getSelectedValues()) {
							eventMask |= ((EventTrigger) o)
									.getEventTriggerMask();
						}

						long interval = Long.parseLong(getInterval().getText());
						if (interval < 0)
							interval = 0;

						String extraArgs = getExtraArgs().getText();

						output.setText(String.format("%s%s,%s,%s,%s\n", output
								.getText(), actionMask, eventMask, interval,
								extraArgs));
					} catch (Exception e) {
						return;
					}
				}
			});
		}
		return b_addSettingsLine;
	}

	private JLabel getLogActionLabel() {
		if (lbl_logAction == null) {
			lbl_logAction = new JLabel();
			lbl_logAction.setText("Log Action(s)");
		}
		return lbl_logAction;
	}

	private static void installLnF() {
		try {
			String lnfClassname = PREFERRED_LOOK_AND_FEEL;
			if (lnfClassname == null)
				lnfClassname = UIManager.getCrossPlatformLookAndFeelClassName();
			UIManager.setLookAndFeel(lnfClassname);
		} catch (Exception e) {
			System.err.println("Cannot install " + PREFERRED_LOOK_AND_FEEL
					+ " on this platform:" + e.getMessage());
		}
	}

	/**
	 * Main entry of the class. Note: This class is only created so that you can
	 * easily preview the result at runtime. It is not expected to be managed by
	 * the designer. You can modify it as you like.
	 */
	public static void main(String[] args) {
		installLnF();
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				SettingsGenerator frame = new SettingsGenerator();
				frame.setDefaultCloseOperation(SettingsGenerator.EXIT_ON_CLOSE);
				frame.setTitle("SettingsGenerator");
				frame.getContentPane().setPreferredSize(frame.getSize());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}
}
