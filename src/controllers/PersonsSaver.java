package controllers;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import persons_model.PageModel;
import persons_model.PersonModel;

public class PersonsSaver {
	
	public PersonsSaver(PersonsMainController personsMainController, File file) {
		file = new File(file.toString() + ".xml");
		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			Document doc = docBuilder.newDocument();
			
			//root
			Element personsElement = doc.createElement("persons");
			doc.appendChild(personsElement);
			//save page
			int curPageNumber = personsMainController.getCurrentPage().getPageNumber();
			
			personsMainController.setPageLastNumber();
			int pagesAmount = personsMainController.getCurrentPage().getPageNumber();
			
			personsMainController.setPageFirstNumber();
			for(int pageIndex = 0; pageIndex < pagesAmount; pageIndex++) {
				PageModel page = personsMainController.getCurrentPage();
				int curPageEntryAmount = page.getEntryPerCurrentPage();
				for(int entry = 0; entry < curPageEntryAmount; entry++) {
					
					PersonModel person = page.getEntry(entry);
					
					//root's child
					Element personEl = doc.createElement("person");
					personsElement.appendChild(personEl);
					
					//root's child's child
					Element fName = doc.createElement("firstName");
					fName.setAttribute("value", person.getFirstName());
					personEl.appendChild(fName);
					
					//root's child's child
					Element sName = doc.createElement("secondName");
					sName.setAttribute("value", person.getSecondName());
					personEl.appendChild(sName);
					
					//root's child's child
					Element tName = doc.createElement("thirdName");
					tName.setAttribute("value", person.getThirdName());
					personEl.appendChild(tName);
					
					//root's child's child
					Element city = doc.createElement("city");
					city.setAttribute("value", person.getCity());
					personEl.appendChild(city);
					
					//root's child's child
					Element street = doc.createElement("street");
					street.setAttribute("value", person.getStreet());
					personEl.appendChild(street);
					
					//root's child's child
					Element houseN = doc.createElement("houseN");
					houseN.setAttribute("value", 
							Long.toString(person.getHouseNumber()));
					personEl.appendChild(houseN);
					
					//root's child's child
					Element mobilePHN = doc.createElement("mobilePHN");
					mobilePHN.setAttribute("value", 
							Long.toString(person.getMobilePhoneNumber()));
					personEl.appendChild(mobilePHN);
					
					//root's child's child
					Element homePHN = doc.createElement("homePHN");
					homePHN.setAttribute("value", 
							Long.toString(person.getHomePhoneNumber()));
					personEl.appendChild(homePHN);
				}
				personsMainController.increasePageNumber();
			}
			//restore page
			while(personsMainController.getCurrentPage().getPageNumber() != curPageNumber) {
				personsMainController.decreasePageNumber();
			}
			//save data to XML-file
			TransformerFactory transfFactory = TransformerFactory.newInstance();
			Transformer transformerToXML = transfFactory.newTransformer();
			
			DOMSource domSource = new DOMSource(doc);
			StreamResult fileStream = new StreamResult(file);
			transformerToXML.transform(domSource, fileStream);

		} catch (TransformerConfigurationException ex) {
			ex.printStackTrace();
		} catch (TransformerException ex) {
			ex.printStackTrace();
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
		} catch (Exception ex) {
			System.out.println("Неизвестная ошибка!: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
