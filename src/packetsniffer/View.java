package packetsniffer;


import java.util.ArrayList;


public interface View {

	void createInterface();


	void open();


	void close();


	void redrawFrame();


	Presenter getPresenter();


	void setPresenter(Presenter presenter);


	String getTextAreaContent();


	void setTextAreaContent(String text);


	void addTextAreaContent(String text);


	void setDevicePanelContent(ArrayList<NameValuePair<String, String>> pairs);


	String getSelectedDeviceID();


	void toggleCaptureButtonStatus();


	void setCaptureButtonStatus(String status);


	String getCaptureButtonStatus();
}
