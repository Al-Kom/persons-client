package persons_model;

import java.util.List;

public class PersonsBookModel {
	private final int ENTRY_PER_PAGE_MINIMAL_AMOUNT = 1;
	private final int PAGE_MINIMAL_AMOUNT = 0;
	private PersonList persons;
	private int pageNumber;
	private int entryPerPage;
	
	public PersonsBookModel() {
		persons = new PersonList();
		setEntryPerPage(0);
		setPageNumber(0);
	}
	
	public void addEntry(PersonModel personModel) {
		persons.add(personModel);
	}

	public void addEntry(List<PersonModel> entryList) {
		persons.add(entryList);
	}

	public int removeEntry(List<PersonModel> entryList) {
		int personsBeforeRemoving = persons.size();
		for(PersonModel p:entryList) {
			persons.remove(p);
		}
		//update page
		setEntryPerPage(getEntryPerPage());
		updatePageNumber();
		return (personsBeforeRemoving - persons.size());
	}

	public PersonModel getPageEntry(int entryNumber) {
		int index = entryNumber + getPageNumber()*getEntryPerPage();
		return persons.get(index);
	}

	public List<PersonModel> search(PersonModel personModel, List<Integer> searchParams) {
		return persons.search(personModel, searchParams);
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		if(pageNumber < PAGE_MINIMAL_AMOUNT
				|| getPagesAmount() < PAGE_MINIMAL_AMOUNT)
			this.pageNumber = PAGE_MINIMAL_AMOUNT;
		else if(pageNumber < getPagesAmount())
			this.pageNumber = pageNumber;
		else
			this.pageNumber = getPagesAmount();
	}

	public int getEntryPerPage() {
		return entryPerPage;
	}

	public void setEntryPerPage(int entryPerPage) {
		if(entryPerPage < ENTRY_PER_PAGE_MINIMAL_AMOUNT 
				|| persons.isEmpty())
			this.entryPerPage = ENTRY_PER_PAGE_MINIMAL_AMOUNT;
		else if(entryPerPage > persons.size())
			this.entryPerPage = persons.size();
		else
			this.entryPerPage = entryPerPage;
		updatePageNumber();
	}

	public int getEntryPerCurrentPage() {
		if(getPagesAmount() == PAGE_MINIMAL_AMOUNT
				|| getPageNumber() == getPagesAmount())
			return (persons.size() % getEntryPerPage());
		else
			return getEntryPerPage();
	}

	public int getPagesAmount() {
		if(getEntryPerPage() < ENTRY_PER_PAGE_MINIMAL_AMOUNT)
			return persons.size();
		else
			return (persons.size()/getEntryPerPage());
	}

	public PageModel getCurrentPage() {
		PersonList pageEntries =  new PersonList();
		int firstIndex = getPageNumber()*getEntryPerPage();
		int lastIndex = getEntryPerCurrentPage() + firstIndex;
		for(int i = firstIndex; i < lastIndex; i++) {
			pageEntries.add(persons.get(i));
		}
		return new PageModel(pageEntries, getEntryPerPage(), getPageNumber());
	}
//
//	public int getEntryAmount() {
//		return persons.size();
//	}
	
	private void updatePageNumber() {
		setPageNumber(getPageNumber());		
	}

	public void setPageFirstNumber() {
		setPageNumber(PAGE_MINIMAL_AMOUNT);		
	}

	public void setPageLastNumber() {
		setPageNumber(getPagesAmount());
	}

	public void increaseEntryPerPage() {
		setEntryPerPage(getEntryPerPage() + 1);
	}

	public void decreaseEntryPerPage() {
		setEntryPerPage(getEntryPerPage() - 1);
	}
}
