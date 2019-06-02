package persons_launcher;
/**
 * TODO:
 * remove all magical numbers
 * ArrayList->List (not in new) [programming at interfaces]
 */
import controllers.PersonsMainController;

public class PersonsLauncher {
	public static void main(String[] args) {
		new PersonsMainController().showGUI();
	}
}
