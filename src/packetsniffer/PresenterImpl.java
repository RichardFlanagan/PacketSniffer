package packetsniffer;


import java.util.ArrayList;
import java.util.Formatter;
import java.util.Locale;
import net.sourceforge.jpcap.net.EthernetPacket;
import net.sourceforge.jpcap.net.ICMPPacket;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.IPProtocol;
import net.sourceforge.jpcap.net.PacketEncoding;
import net.sourceforge.jpcap.net.RawPacket;
import net.sourceforge.jpcap.net.TCPPacket;
import net.sourceforge.jpcap.net.UDPPacket;


public class PresenterImpl implements Presenter {

	Model model;
	View view;


	public PresenterImpl() {}


	public PresenterImpl(Model model, View view) {
		this.model = model;
		this.view = view;
	}


	@Override
	public Model getModel() {
		return model;
	}


	@Override
	public void setModel(Model model) {
		this.model = model;
	}


	@Override
	public View getView() {
		return view;
	}


	@Override
	public void setView(View view) {
		this.view = view;
	}


	@Override
	public void start() {
		view.createInterface();
		getDevicesButtonClickAction();

		StringBuilder str = new StringBuilder();
		str.append("+------------------------------------------+\n");
		str.append("|                                          |\n");
		str.append("| Networks 4 Assignment : Packet Viewer    |\n");
		str.append("| By Richard Flanagan : A00193644          |\n");
		str.append("|                                          |\n");
		str.append("+------------------------------------------+\n");

		view.setTextAreaContent(str.toString());
		view.open();
	}


	@Override
	public void getDevicesButtonClickAction() {
		// Get network interface devices
		ArrayList<NameValuePair<String, String>> devices = model.scanForNetworkDevices();

		// Display results
		if (devices.size() > 0) {
			displayText("\n==========( NETWORK INTERFACE DEVICES )==========\n");
			for (NameValuePair<String, String> dev : devices) {
				displayText(dev.toString() + "\n");
			}
			model.setOpenDevice(devices.get(0).getValue());

			view.setDevicePanelContent(devices);
		} else {
			StringBuilder str = new StringBuilder();
			str.append("\nNO NETWORK INTERFACE DEVICES FOUND!\n");
			str.append("\n - Do you have administrator priviliges?\n");
			str.append("\n - Is WinPcap installed and active on your machine?\n");
			str.append("\n - Do you have network drivers installed?\n");
			displayText(str.toString());
		}
	}


	@Override
	public void capturePacketsButtonClickAction(String status) {
		if (status == "start") {
			model.startPacketCapture();
			view.setCaptureButtonStatus("stop");
		} else if (status == "stop") {
			model.stopPacketCapture();
			view.setCaptureButtonStatus("start");
		}
	}


	@Override
	public void deviceSelectedClickEvent(String deviceID, boolean isSelected) {
		if (isSelected) {
			model.setOpenDevice(deviceID);
			view.setCaptureButtonStatus("start");
		}
	}
	
	
	@Override	
	public void filterSelectedClickEvent(String filter, boolean isSelected){
		if (isSelected) {
			model.setFilter(filter);
			view.setCaptureButtonStatus("start");
		}
	}

	
	@Override
	public void packetRecievedActionEvent(RawPacket packet) {
		StringBuilder str = new StringBuilder();
		Formatter formatter = new Formatter(str, Locale.UK);
		String formatterTemplate = "\n%1$-24s: %2$-2s";
		str.append("\n==========( PACKET RECIEVED )==========");

		
		// Get Link Layer Packet
		EthernetPacket ethernetPacket = new EthernetPacket(EthernetPacket.ETH_HEADER_LEN, PacketEncoding.extractData(0, 0, packet.getData()));
		
		str.append("\n-----( LINK LAYER FRAME )-----");
		formatter.format(formatterTemplate, "Packet", ethernetPacket);
		formatter.format(formatterTemplate, "Source MAC", ethernetPacket.getSourceHwAddress());
		formatter.format(formatterTemplate, "Destination MAC", ethernetPacket.getDestinationHwAddress());
		formatter.format(formatterTemplate, "Protocol", ethernetPacket.getEthernetProtocol());

		
		// Get Network Layer Packet
		IPPacket ipPacket = new IPPacket(EthernetPacket.ETH_HEADER_LEN, PacketEncoding.extractData(0, 0, packet.getData()));
		
		str.append("\n-----( NETWORK LAYER PACKET )-----");
		formatter.format(formatterTemplate, "Packet", ipPacket);
		formatter.format(formatterTemplate, "IP Version", "IPv" + ipPacket.getVersion());
		if(ipPacket.getVersion() == 6){
			str.append("\nIPv6 display not supported");
			
			// Display
			displayText(str.toString());
			formatter.close();
			return;
		}
		formatter.format(formatterTemplate, "Source IP", ipPacket.getSourceAddress());
		formatter.format(formatterTemplate, "Destination IP", ipPacket.getDestinationAddress());
		formatter.format(formatterTemplate, "Type of Service", ipPacket.getTypeOfService());
		formatter.format(formatterTemplate, "Packet ID", ipPacket.getId());

		
		// Get Transport Layer Packet
		str.append("\n-----( TRANSPORT LAYER SEGMENT )-----");
		formatter.format(formatterTemplate, "Protocol", IPProtocol.getDescription(ipPacket.getIPProtocol()));
		
		if (ipPacket.getIPProtocol() == IPProtocol.TCP) {
			TCPPacket tcpPacket = new TCPPacket(EthernetPacket.ETH_HEADER_LEN, PacketEncoding.extractData(0, 0, packet.getData()));
			
			formatter.format(formatterTemplate, "Packet", tcpPacket);
			formatter.format(formatterTemplate, "Source Port", tcpPacket.getSourcePort());
			formatter.format(formatterTemplate, "Destination Port", tcpPacket.getDestinationPort());
			
		} else if (ipPacket.getIPProtocol() == IPProtocol.UDP) {
			UDPPacket udpPacket = new UDPPacket(EthernetPacket.ETH_HEADER_LEN, PacketEncoding.extractData(0, 0, packet.getData()));			
			
			formatter.format(formatterTemplate, "Packet", udpPacket);
			formatter.format(formatterTemplate, "Source Port", udpPacket.getSourcePort());
			formatter.format(formatterTemplate, "Destination Port", udpPacket.getDestinationPort());
			
		} else if (ipPacket.getIPProtocol() == IPProtocol.ICMP) {
			ICMPPacket icmpPacket = new ICMPPacket(EthernetPacket.ETH_HEADER_LEN, PacketEncoding.extractData(0, 0, packet.getData()));
			
			formatter.format(formatterTemplate, "Packet", icmpPacket);
		} 
		
		// Display
		displayText(str.toString());
		formatter.close();
	}
	

	@Override
	public void displayText(String text) {
		view.addTextAreaContent(text + "\n");
	}

}
