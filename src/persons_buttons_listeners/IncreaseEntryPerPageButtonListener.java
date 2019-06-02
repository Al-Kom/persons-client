package persons_buttons_listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controllers.PersonsMainController;

public class IncreaseEntryPerPageButtonListener implements ActionListener {
	private PersonsMainController controller;
	
	public IncreaseEntryPerPageButtonListener(PersonsMainController controller) {
		this.controller = controller;
	}

	public void actionPerformed(ActionEvent e) {
		controller.increaseEntryPerPage();
	}

}
