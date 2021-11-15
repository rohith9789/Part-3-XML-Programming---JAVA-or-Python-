//package Assignment3;

import java.io.File;
import java.io.IOException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

public class DomParserSchema {
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException {
		boolean flag = true;
		String error = "none";
		try {
			// Validate XML Document against Schema
			validate("books.xml", "books.xsd");
		}

		// Catches the validation error and stores in error variable
		catch (SAXException e) {
			flag = false;
			error = e.getMessage();
		} catch (IOException e) {
			flag = false;
			error = e.getMessage();
		}

		if (flag == true) {
			System.out.println("SUCCESS: XML file is valid against Schema");

			// Parse XML Document
			parseXmlDom("books.xml");
		} else {
			
			// Validation Error printing
			System.out.println("ERROR: " + error);
		}

	}

	// Validate XML Document against Schema
	public static void validate(String xmlFile, String validationFile) throws SAXException, IOException {
		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		((schemaFactory.newSchema(new File(validationFile))).newValidator())
				.validate(new StreamSource(new File(xmlFile)));
	}

	// Parse XML Document
	public static void parseXmlDom(String xml) throws SAXException, IOException, ParserConfigurationException {
		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Build Document
		Document document = builder.parse(new File(xml));

		// Normalize the XML Structure
		document.getDocumentElement().normalize();

		// Root node
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
