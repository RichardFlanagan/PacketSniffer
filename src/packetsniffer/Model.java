package packetsniffer;


import java.util.ArrayList;


/**
 * @author RichardFlanagan
 * @date 11 Nov 2015
 */
public interface Model {

	/**
	 * @return The MVP Presenter
	 */
	Presenter getPresenter();


	/**
	 * @param The MVP Presenter
	 */
	void setPresenter(Presenter presenter);


	/**
	 * @return The list of all found networks on the machine.
	 */
	ArrayList<NameValuePair<String, String>> scanForNetworkDevices();


	/**
	 * @param deviceID
	 */
	void setOpenDevice(String deviceID);


	/**
	 * @return The id of the currently opened device.
	 */
	String getOpenDevice();
	
	
	/**
	 * @param filter
	 */
	void setFilter(String filter);

	
	/**
	 * Start capturing packets.
	 */
	void startPacketCapture();


	/**
	 * Stop capturing packets.
	 */
	void stopPacketCapture();
}
