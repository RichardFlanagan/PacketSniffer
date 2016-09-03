package packetsniffer;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;


public class ViewImpl extends JFrame implements View {

	private static final long serialVersionUID = 1L;
	private Presenter presenter = null;
	private ButtonGroup DEVICES_BUTTON_GROUP;

	private JPanel MAIN_PANEL = new JPanel();
	private JPanel NORTH_PANEL = new JPanel();
	private JPanel SOUTH_PANEL = new JPanel();
	private JPanel EAST_PANEL = new JPanel();
	private JPanel WEST_PANEL = new JPanel();

	private JTextArea MAIN_TEXT_AREA = new JTextArea();
	private JScrollPane MAIN_TEXT_SCROLL = new JScrollPane(MAIN_TEXT_AREA);

	private JPanel DEVICES_PANEL = new JPanel();
	private JScrollPane DEVICES_PANEL_SCROLL = new JScrollPane(DEVICES_PANEL);

	private JPanel FILTER_PANEL = new JPanel();
	private JScrollPane FILTER_PANEL_SCROLL = new JScrollPane(FILTER_PANEL);

	private JButton BTN_CAPTURE_PACKETS = new JButton("Start Packet Capture");


	public ViewImpl() {}


	@Override
	public void createInterface() {
		initJFrame();
		buildMainPanels();
		buildTextArea();
		buildInterfacePanel();
		buildFilterPanel();
		buildButtonPanel();
		pack();
	}


	private void initJFrame() {
		setTitle("Packet Sniffer : Richard Flanagan : A00193644");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocation(100, 100);
		setResizable(false);
		setVisible(false);
	}


	private void buildMainPanels() {
		// MAIN_PANEL
		MAIN_PANEL.setLayout(new BorderLayout());
		this.add(MAIN_PANEL);

		// NORTH_PANEL
		MAIN_PANEL.add(NORTH_PANEL, BorderLayout.NORTH);

		// SOUTH_PANEL
		SOUTH_PANEL.setPreferredSize(new Dimension(0, 100));
		MAIN_PANEL.add(SOUTH_PANEL, BorderLayout.SOUTH);

		// EAST_PANEL
		EAST_PANEL.setLayout(new BoxLayout(EAST_PANEL, BoxLayout.Y_AXIS));
		MAIN_PANEL.add(EAST_PANEL, BorderLayout.EAST);

		// WEST_PANEL
		MAIN_PANEL.add(WEST_PANEL, BorderLayout.WEST);
	}


	private void buildTextArea() {
		// MAIN_TEXT_AREA
		MAIN_TEXT_AREA.setEditable(false);
		MAIN_TEXT_AREA.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
		        BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		MAIN_TEXT_AREA.setLineWrap(false);
		MAIN_TEXT_AREA.setAutoscrolls(true);
		// MAIN_TEXT_AREA.setPreferredSize(new Dimension(640, 480));
		MAIN_TEXT_AREA.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

		// MAIN_TEXT_SCROLL
		MAIN_TEXT_SCROLL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		MAIN_TEXT_SCROLL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		MAIN_TEXT_SCROLL.setAutoscrolls(true);
		MAIN_TEXT_SCROLL.setPreferredSize(new Dimension(640, 480));
		MAIN_PANEL.add(MAIN_TEXT_SCROLL, BorderLayout.CENTER);
	}


	private void buildInterfacePanel() {
		// DEVICES_PANEL
		DEVICES_PANEL.setBorder(
		        new TitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
		                BorderFactory.createEmptyBorder(5, 5, 5, 5)), "Network Interface Devices"));
		DEVICES_PANEL.setLayout(new BoxLayout(DEVICES_PANEL, BoxLayout.Y_AXIS));

		// DEVICES_PANEL_SCROLL
		DEVICES_PANEL_SCROLL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		DEVICES_PANEL_SCROLL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		DEVICES_PANEL_SCROLL.setAutoscrolls(true);
		DEVICES_PANEL_SCROLL.setPreferredSize(new Dimension(320, 0));

		// Add to panel
		EAST_PANEL.add(DEVICES_PANEL_SCROLL);
	}


	private void buildFilterPanel() {
		// FILTER_PANEL
		FILTER_PANEL.setBorder(
		        new TitledBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK),
		                BorderFactory.createEmptyBorder(5, 5, 5, 5)), "Packet Filters"));
		FILTER_PANEL.setLayout(new BoxLayout(FILTER_PANEL, BoxLayout.Y_AXIS));
		
		ButtonGroup bg = new ButtonGroup();
		
		JRadioButton all = new JRadioButton("All", true);
		all.setActionCommand("");
		all.addItemListener(actionEvent -> {
			JRadioButton button = (JRadioButton) actionEvent.getItem();
			presenter.filterSelectedClickEvent(button.getActionCommand(), button.isSelected());
		});
		
		JRadioButton tcp = new JRadioButton("TCP", true);
		tcp.setActionCommand("proto TCP");
		tcp.addItemListener(actionEvent -> {
			JRadioButton button = (JRadioButton) actionEvent.getItem();
			presenter.filterSelectedClickEvent(button.getActionCommand(), button.isSelected());
		});
		
		JRadioButton udp = new JRadioButton("UDP", true);
		udp.setActionCommand("proto UDP");
		udp.addItemListener(actionEvent -> {
			JRadioButton button = (JRadioButton) actionEvent.getItem();
			presenter.filterSelectedClickEvent(button.getActionCommand(), button.isSelected());
		});
		
		JRadioButton icmp = new JRadioButton("ICMP", true);
		icmp.setActionCommand("proto ICMP");
		icmp.addItemListener(actionEvent -> {
			JRadioButton button = (JRadioButton) actionEvent.getItem();
			presenter.filterSelectedClickEvent(button.getActionCommand(), button.isSelected());
		});
		
		FILTER_PANEL.add(all); bg.add(all);
		FILTER_PANEL.add(tcp); bg.add(tcp);
		FILTER_PANEL.add(udp); bg.add(udp);
		FILTER_PANEL.add(icmp); bg.add(icmp);
		
		// FILTER_PANEL_SCROLL
		FILTER_PANEL_SCROLL.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		FILTER_PANEL_SCROLL.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		FILTER_PANEL_SCROLL.setAutoscrolls(true);
		FILTER_PANEL_SCROLL.setPreferredSize(new Dimension(320, 0));

		EAST_PANEL.add(FILTER_PANEL_SCROLL);
	}


	private void buildButtonPanel() {
		// BTN_CAPTURE_PACKETS
		BTN_CAPTURE_PACKETS.addActionListener(actionEvent -> {
			presenter.capturePacketsButtonClickAction(BTN_CAPTURE_PACKETS.getActionCommand());
		});
		BTN_CAPTURE_PACKETS.setBackground(Color.GREEN);
		BTN_CAPTURE_PACKETS.setActionCommand("start");
		SOUTH_PANEL.add(BTN_CAPTURE_PACKETS);
	}


	@Override
	public void open() {
		setVisible(true);
	}


	@Override
	public void close() {
		dispose();
	}


	@Override
	public void redrawFrame() {
		revalidate();
		this.repaint();
	}


	@Override
	public Presenter getPresenter() {
		return presenter;
	}


	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public String getTextAreaContent() {
		return MAIN_TEXT_AREA.getText();
	}


	@Override
	public void setTextAreaContent(String text) {
		MAIN_TEXT_AREA.setText(text);
	}


	@Override
	public void addTextAreaContent(String text) {
		MAIN_TEXT_AREA.append(text);
	}


	@Override
	public void setDevicePanelContent(ArrayList<NameValuePair<String, String>> pairs) {
		DEVICES_PANEL.removeAll();
		DEVICES_BUTTON_GROUP = new ButtonGroup();

		for (NameValuePair<String, String> pair : pairs) {
			JRadioButton radioButton = new JRadioButton(pair.getName());
			radioButton.setActionCommand(pair.getValue());
			radioButton.setSelected(true);
			radioButton.addItemListener(actionEvent -> {
				JRadioButton button = (JRadioButton) actionEvent.getItem();
				presenter.deviceSelectedClickEvent(button.getActionCommand(), button.isSelected());
			});
			DEVICES_BUTTON_GROUP.add(radioButton);
			DEVICES_PANEL.add(radioButton);
		}
		redrawFrame();
	}


	@Override
	public String getSelectedDeviceID() {
		return DEVICES_BUTTON_GROUP.getSelection().getActionCommand();
	}


	@Override
	public void toggleCaptureButtonStatus() {
		if (BTN_CAPTURE_PACKETS.getActionCommand() == "start") {
			setCaptureButtonStatus("stop");
		} else if (BTN_CAPTURE_PACKETS.getActionCommand() == "stop") {
			setCaptureButtonStatus("start");
		}
	}


	@Override
	public void setCaptureButtonStatus(String status) {
		if (status == "stop") {
			BTN_CAPTURE_PACKETS.setText("Stop Packet Capture");
			BTN_CAPTURE_PACKETS.setBackground(Color.RED);
			BTN_CAPTURE_PACKETS.setActionCommand("stop");
		} else if (status == "start") {
			BTN_CAPTURE_PACKETS.setText("Start Packet Capture");
			BTN_CAPTURE_PACKETS.setBackground(Color.GREEN);
			BTN_CAPTURE_PACKETS.setActionCommand("start");
		}
	}


	@Override
	public String getCaptureButtonStatus() {
		return BTN_CAPTURE_PACKETS.getActionCommand();
	}

}
