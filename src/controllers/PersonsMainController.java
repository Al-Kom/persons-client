package controllers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import persons_gui.PersonVariableInputPanel;
import persons_gui.PersonsBookPanel;
import persons_gui.PersonsGUI;
import persons_gui.PersonsRemoveDialog;
import persons_gui.PersonsSearchDialog;
import persons_model.PersonModel;
import persons_model.PersonsBookModel;
import persons_model.PageModel;
import persons_model.PersonList;

public class PersonsMainController {
	private PersonsGUI gui;
	private PersonsSearchDialog searchDialog;
	private PersonsBookModel server;
	private PersonsBookPanel bookPanel;

	public PersonsMainController() {
		server = new PersonsBookModel();
	}
	
	public void showGUI() {
		gui = new PersonsGUI(this);
		bookPanel = gui.getBookPanel();
		updateBookPanel();
	}
	
	public void setBookPanel(PersonsBookPanel bookPanel) {
		this.bookPanel = bookPanel;
		updateBookPanel();
	}

	public void showSearchDialog() {
		searchDialog = new PersonsSearchDialog(this);
		searchDialog.showDialog();
	}

	public void showRemoveDialog() {
		new PersonsRemoveDialog(this);
	}

	public void searchEntry(PersonModel personModel,
			List<Integer> variableParametres) {
		List<PersonModel> founded = search(personModel, variableParametres);
		PersonsMainController searchDialogController =
				new PersonsMainController();
		//give book controller to search dialog for it's book's buttons
		searchDialog.addBookController(searchDialogController);
		//for book updating
		searchDialogController.setBookPanel(searchDialog.getBookPanel());
		//fill the book
		searchDialogController.addEntry(founded);
	}

	public void removeEntry(PersonModel personModel, List<Integer> variableParametres) {
		List<PersonModel> founded = search(personModel, variableParametres);
		String removeInfo;
		int foundedEntryAmount = founded.size();
		if(foundedEntryAmount == 0) {
			removeInfo = "Ничего не найдено";
		} else {
			int removedAmount = this.removeEntry(founded);
			removeInfo = "Найдено " + foundedEntryAmount + " записей\n" +
					"Удалено " + removedAmount + " записей";
		}
		JOptionPane.showMessageDialog(null,
				removeInfo,
				"Отчет об удалении",
				JOptionPane.PLAIN_MESSAGE);
	}

	public List<PersonModel> search(PersonModel personModel, List<Integer> inputSearchParams) {
		List<Integer> searchParams = new ArrayList<Integer>();
		if(inputSearchParams.contains(PersonVariableInputPanel.FIRST_NAME_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_FIRST_NAME);
		if(inputSearchParams.contains(PersonVariableInputPanel.SECOND_NAME_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_SECOND_NAME);
		if(inputSearchParams.contains(PersonVariableInputPanel.THIRD_NAME_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_THIRD_NAME);
		if(inputSearchParams.contains(PersonVariableInputPanel.CITY_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_CITY);
		if(inputSearchParams.contains(PersonVariableInputPanel.STREET_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_STREET);
		if(inputSearchParams.contains(PersonVariableInputPanel.HOUSE_NUMBER_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_HOUSE_NUMBER);
		if(inputSearchParams.contains(PersonVariableInputPanel.MOBILE_PHONE_NUMBER_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_MOBILE_PHONE_NUMBER);
		if(inputSearchParams.contains(PersonVariableInputPanel.HOME_PHONE_NUMBER_SELECTED))
			searchParams.add(PersonList.SEARCH_BY_HOME_PHONE_NUMBER);
		
		return server.search(personModel, searchParams);
	}

	public void addEntry(PersonModel personModel) {
		server.addEntry(personModel);
		updateBookPanel();
	}

	public void addEntry(List<PersonModel> entryList) {
		server.addEntry(entryList);
		updateBookPanel();
	}

	public int removeEntry(List<PersonModel> entryList) {
		int removedAmount = server.removeEntry(entryList);
		updateBookPanel();
		return removedAmount;
	}

	public void clearBook() {
		server = new PersonsBookModel();
		updateBookPanel();
	}

	public void saveBook() {
		JFileChooser fileSaveDialog = new JFileChooser();
		fileSaveDialog.showOpenDialog(null);
		File file = fileSaveDialog.getSelectedFile();
		if(file != null) {
			new PersonsSaver(this, file);
		}
	}

	public void loadBook() {
		JFileChooser fileLoadDialog = new JFileChooser();
		fileLoadDialog.showOpenDialog(null);
		File file = fileLoadDialog.getSelectedFile();
		if(file != null) {
			clearBook();
			new PersonsLoader(this ,file);			
		}
	}
	


	public void increasePageNumber() {
		server.setPageNumber(server.getPageNumber() + 1);
		updateBookPanel();
	}

	public void decreasePageNumber() {
		server.setPageNumber(server.getPageNumber() - 1);
		updateBookPanel();
	}

	public void setPageFirstNumber() {
		server.setPageFirstNumber();
		updateBookPanel();
	}

	public void setPageLastNumber() {
		server.setPageLastNumber();
		updateBookPanel();
	}

	public void increaseEntryPerPage() {
		server.increaseEntryPerPage();
		updateBookPanel();
	}

	public void decreaseEntryPerPage() {
		server.decreaseEntryPerPage();
		updateBookPanel();
	}

	public void updateBookPanel() {
		
		PageModel page = server.getCurrentPage();
		bookPanel.updateEntryAmount(page.getEntryPerPage(),
				page.getEntryPerCurrentPage());
		bookPanel.updatePageNumber(page.getPageNumber());
		bookPanel.setPage(page);
	}
	
	public PageModel getCurrentPage() {
		return server.getCurrentPage();
	}
	
}
