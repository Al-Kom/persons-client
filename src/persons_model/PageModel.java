package persons_model;

public class PageModel {
	private PersonList persons;
	private int pageNumber;
	public int entryPerPage;

	public PageModel() {
		persons = new PersonList();
		entryPerPage = 0;
		pageNumber = 0;
	}
	
	public PageModel(PersonList persons, int entryPerPage, int pageNumber) {
		this.persons = persons;
		this.entryPerPage = entryPerPage;
		this.pageNumber = pageNumber;
	}

	public PersonModel getEntry(int index) {
		return persons.get(index);
	}

	public int getEntryAmount() {
		return persons.size();
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	
	public int getEntryPerPage() {
		return entryPerPage;
	}
	
	public int getEntryPerCurrentPage() {
		return persons.size();
	}
}
