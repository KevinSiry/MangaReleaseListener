package com.listener.release.manga.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.listener.release.manga.commonmethods.InputChecking;

public class MangaListenerCore {

	public String getLastChapter(String i_mangaName) {
		InputChecking.checkStringInput(i_mangaName, true, true, true, "i_mangaName cannot be null.",
				"i_mangaName cannot be empty.");

		try {
			String m_title = null;
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(readWebPage(i_mangaName));
			Element racine = doc.getDocumentElement();
			Node racineNoeud = racine.getFirstChild();
			if (racineNoeud.getNodeType() == Node.ELEMENT_NODE) {
				Element firstNoeud = (Element) racineNoeud;
				Node nNode = firstNoeud.getElementsByTagName("item").item(0);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					m_title = eElement.getElementsByTagName("title").item(0).getTextContent();
				}
			}

			String[] testTable = m_title.split("\\.");

			return testTable[testTable.length - 1];
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	public InputStream readWebPage(String i_mangaName) {
		InputChecking.checkStringInput(i_mangaName, true, true, true, "i_mangaName cannot be null.",
				"i_mangaName cannot be empty.");
		try {
			URL url = new URL("http://fanfox.net/rss/" + i_mangaName + ".xml");
			URLConnection urlConnection = url.openConnection();
			urlConnection.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");

			return urlConnection.getInputStream();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	public JSONArray getUserMangaList() {
		try {
			File jsonMangaFile = new File("mangalist.json");
			BufferedReader bufferReader = new BufferedReader(new FileReader(jsonMangaFile));
			StringBuffer content = loadFile(bufferReader);

			JSONObject jsonData = new JSONObject(content.toString());
			JSONArray jsonArray = (JSONArray) jsonData.getJSONArray("manga_list");

			return jsonArray;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private StringBuffer loadFile(BufferedReader i_bufferReader) {
		try {
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = i_bufferReader.readLine()) != null) {
				content.append(inputLine);
			}
			i_bufferReader.close();

			return content;
		} catch (Exception e) {
			return null;
		}
	}
}
