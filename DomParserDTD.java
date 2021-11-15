//package Assignment3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class DomParserDTD {
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		boolean flag = true;
		String error = "none";
		try {
			// Validates the XML Document against DTD
			validate("books.xml");
		}

		// Catches the validation error and stores in error variable
		catch (ParserConfigurationException e) {
			flag = false;
			error = e.getMessage();
		} catch (FileNotFoundException e) {
			flag = false;
			error = e.getMessage();
		} catch (SAXException e) {
			flag = false;
			error = e.getMessage();
		} catch (IOException e) {
			flag = false;
			error = e.getMessage();
		}

		if (flag == true) {
			System.out.println("SUCCESS: XML file is valid against DTD");

			// Parse XML Document
			parseXmlDom("books.xml");
		} else {
			// Validation Error Printing
			System.out.println("ERROR: " + error);
		}
	}

	// Validate XML Document against DTD
	public static void validate(String xmlFile)
			throws ParserConfigurationException, FileNotFoundException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		documentBuilderFactory.setValidating(true);
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		documentBuilder.setErrorHandler(new ErrorHandler() {
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}

			public void fatalError(SAXParseException exception) throws SAXException {
				throw exception;
			}

			public void warning(SAXParseException exception) throws SAXException {
				throw exception;
			}
		});
		documentBuilder.parse(new FileInputStream(xmlFile));

	}

	// Parses XML Document
	public static void parseXmlDom(String xml) throws SAXException, IOException, ParserConfigurationException {
		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Build Document
		Document document = builder.parse(new File(xml));

		// Normalize the XML Structure
		document.getDocumentElement().normalize();

		// Here comes the root node
		Element root = document.getDocumentElement();
		System.out.println("\nRoot Element: " + root.getNodeName());

		// Get all Books
		NodeList nList = document.getElementsByTagName("book");
		System.out.println("------------------");

		// Loop over Books
		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			System.out.println("");
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				// Print each book's detail
				Element eElement = (Element) node;
				System.out.println("Book id : " + eElement.getAttribute("id"));
				System.out.println("Author : " + eElement.getElementsByTagName("author").item(0).getTextContent());
				System.out.println("Title : " + eElement.getElementsByTagName("title").item(0).getTextContent());
				System.out.println("Genre : " + eElement.getElementsByTagName("genre").item(0).getTextContent());
				System.out.println("Price : $" + eElement.getElementsByTagName("price").item(0).getTextContent());
			}
		}
	}
}
