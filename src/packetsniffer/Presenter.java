package packetsniffer;


import net.sourceforge.jpcap.net.RawPacket;


/**
 * @author RichardFlanagan
 * @date 11 Nov 2015
 */
public interface Presenter {

	/**
	 * @return The MVP Model
	 */
	Model getModel();


	/**
	 * @param The MVP Model
	 */
	void setModel(Model model);


	/**
	 * @return The MVP View
	 */
	View getView();


	/**
	 * @param The MVP View
	 */
	void setView(View view);


	/**
	 * Start the application.
	 */
	void start();


	/**
	 * Click action for the getDevicesButton.
	 */
	void getDevicesButtonClickAction();


	/**
	 * Click action for the CapturePackets button.
	 * @param status of the button (on/off)
	 */
	void capturePacketsButtonClickAction(String status);


	/**
	 * Action event of the device selection button group.
	 * @param deviceID of the selected device.
	 * @param isSelected
	 */
	void deviceSelectedClickEvent(String deviceID, boolean isSelected);
	
	
	/**
	 * @param filter
	 * @param isSelected
	 */
	public void filterSelectedClickEvent(String filter, boolean isSelected);


	/**
	 * Action event for captured packets.
	 * @param The received packet
	 */
	void packetRecievedActionEvent(RawPacket packet);


	/**
	 * Display the text on the main View output.
	 * @param The text to display
	 */
	void displayText(String text);
}
