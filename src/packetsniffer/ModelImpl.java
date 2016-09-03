package packetsniffer;


import java.util.ArrayList;
import net.sourceforge.jpcap.capture.CaptureDeviceLookupException;
import net.sourceforge.jpcap.capture.CaptureDeviceOpenException;
import net.sourceforge.jpcap.capture.CapturePacketException;
import net.sourceforge.jpcap.capture.InvalidFilterException;
import net.sourceforge.jpcap.capture.PacketCapture;


public class ModelImpl implements Model {

	private Presenter presenter = null;
	private PacketCaptureThread captureThread = null;
	private String deviceID = "";
	private String filter = "";


	public ModelImpl() {}


	@Override
	public Presenter getPresenter() {
		return presenter;
	}


	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}


	@Override
	public ArrayList<NameValuePair<String, String>> scanForNetworkDevices() {
		String[] foundDevices = null;
		ArrayList<NameValuePair<String, String>> networkDevices = new ArrayList<NameValuePair<String, String>>();

		try {
			foundDevices = PacketCapture.lookupDevices();

			for (int i = 0; i < foundDevices.length; i++) {
				String[] list = foundDevices[i].split("\\r?\\n");
				String name = list[1].trim();
				String value = list[0].trim();

				networkDevices.add(new Pair<String, String>(name, value));
			}

		} catch (CaptureDeviceLookupException e) {
			System.err.println("Error reading interface devices!");
			e.printStackTrace();
		}

		return networkDevices;
	}


	@Override
	public void setOpenDevice(String deviceID) {
		stopPacketCapture();
		this.deviceID = deviceID;
	}

	
	@Override
	public void setFilter(String filter){
		stopPacketCapture();
		this.filter = filter;
	}
	

	@Override
	public String getOpenDevice() {
		return deviceID;
	}


	@Override
	public void startPacketCapture() {
		captureThread = new PacketCaptureThread(presenter, deviceID, filter);
		captureThread.start();
	}


	@Override
	public void stopPacketCapture() {
		if (captureThread != null) {
			captureThread.stopRunning();
		}
	}

}



class PacketCaptureThread extends Thread {

	private Presenter presenter = null;
	private PacketCapture packetCapture = new PacketCapture();
	private String deviceID = "";
	private boolean running = true;
	private String filter = "";


	PacketCaptureThread(Presenter presenter, String deviceID, String filter) {
		super(deviceID);
		this.presenter = presenter;
		this.deviceID = deviceID;
		this.filter = filter;
	}


	@Override
	public void run() {
		try {
			// True if you want to open the interface in promiscuous mode, and
			// otherwise false.
			// In promiscuous mode, you can capture packets every packet from
			// the wire, i.e., even if its source or destination MAC address is
			// not same as the MAC address of the interface you are opening.
			// In non-promiscuous mode, you can only capture packets send and
			// received by your host.
			packetCapture.open(deviceID, false);
			packetCapture.setFilter(filter, true);
			packetCapture.addRawPacketListener(packet -> {
				presenter.packetRecievedActionEvent(packet);
			});

			while (running) {
				packetCapture.capture(1);
			}

			packetCapture.close();
		} catch (CaptureDeviceOpenException | CapturePacketException | InvalidFilterException e) {
			e.printStackTrace();
		}
	}


	public void stopRunning() {
		running = false;
		interrupt();
	}

}
