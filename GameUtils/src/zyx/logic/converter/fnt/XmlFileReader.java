package zyx.logic.converter.fnt;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

class XmlFileReader
{

	private final Document doc;

	XmlFileReader(File file) throws ParserConfigurationException, SAXException, IOException
	{
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		doc = dBuilder.parse(file);
	}

	void convertTo(File output) throws IOException
	{
		ArrayList<FontCharacter> characters = new ArrayList<>();
		ArrayList<FontKerning> kernings = new ArrayList<>();
		
		NodeList characterList = doc.getElementsByTagName("char");
		NodeList kerningList = doc.getElementsByTagName("kerning");
		short lineHeight = Short.parseShort(doc.getElementsByTagName("common").item(0).getAttributes().getNamedItem("lineHeight").getNodeValue());
		
		for (int i = 0; i < characterList.getLength(); i++)
		{
			Node item = characterList.item(i);
			NamedNodeMap attributes = item.getAttributes();
			
			characters.add(new FontCharacter(attributes));
		}
		
		for (int i = 0; i < kerningList.getLength(); i++)
		{
			Node item = kerningList.item(i);
			NamedNodeMap attributes = item.getAttributes();
			
			kernings.add(new FontKerning(attributes));
		}
		
		System.out.println(characters);
		System.out.println(kernings);

		try (DataOutputStream out = new DataOutputStream(new FileOutputStream(output)))
		{
			out.writeShort(lineHeight);
			out.writeShort(characterList.getLength());
			out.writeShort(kerningList.getLength());
			
			for (FontCharacter character : characters)
			{
				character.save(out);
			}
			for (FontKerning kerning : kernings)
			{
				kerning.save(out);
			}
			
			out.flush();
		}
	}

}
