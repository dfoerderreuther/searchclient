package com.decisionem.util;

import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class XmlUtil {
	
	public static String printXml(String requestUrl) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder();
		Document document = builder.build(requestUrl);
		XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
		return outputter.outputString(document);
	}
}
