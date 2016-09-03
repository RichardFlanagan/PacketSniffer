package packetsniffer;


import javax.swing.SwingUtilities;



/**
 * @author RichardFlanagan
 * @date 11 Nov 2015
 */
public class Main {


	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

			Model model = new ModelImpl();
			View view = new ViewImpl();

			Presenter presenter = new PresenterImpl(model, view);
			model.setPresenter(presenter);
			view.setPresenter(presenter);

			presenter.start();

		});

	}

}
