package com.listener.release.manga.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import com.listener.release.manga.commonmethods.InputChecking;
import com.listener.release.manga.output.MangaChapterOutput;
import com.listener.release.manga.output.MangaChapterOutput.MangaChapterStatus;
import com.listener.release.manga.output.MangaListOutput;
import com.listener.release.manga.output.MangaListOutput.MangaListStatus;

public class MangaListenerCore {

	public MangaListOutput getMangaList() {
		try {
			URL url = new URL("https://www.mangaeden.com/api/list/0/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer content = loadFile(bufferReader);

			if (generateJsonMangaList(content)) {
				return new MangaListOutput(MangaListStatus.SUCCESS, "GetMangaList done successfully.");
			} else {
				return new MangaListOutput(MangaListStatus.FAIL,
						"GetMangaList failed : error in Manga list json generation.");
			}
		} catch (Exception e) {
			return new MangaListOutput(MangaListStatus.FAIL, "GetMangaList failed : " + e.getMessage());
		}
	}

	public MangaChapterOutput getMangaChapter(String i_idManga) {
		InputChecking.checkStringInput(i_idManga, true, true, true, "i_idManga cannot be null.",
				"i_idManga cannot be empty.");

		try {
			URL url = new URL("https://www.mangaeden.com/api/manga/" + i_idManga + "/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			StringBuffer content = loadFile(bufferReader);

			getLastChapter(content);

			return new MangaChapterOutput(MangaChapterStatus.SUCCESS, "getMangaChapter done successfully.");
		} catch (Exception e) {
			return new MangaChapterOutput(MangaChapterStatus.FAIL, "getMangaChapter failed : " + e.getMessage());
		}
	}

	public String getMangaId(String i_mangaName) {
		try {
			File jsonMangaFile = new File("mangalist.json");
			BufferedReader bufferReader = new BufferedReader(new FileReader(jsonMangaFile));
			StringBuffer content = loadFile(bufferReader);

			JSONObject jsonData = new JSONObject(content.toString());
			JSONArray jsonArray = (JSONArray) jsonData.getJSONArray("manga");

			for (int i = 0; i < jsonArray.length(); i++) {
				if (jsonArray.getJSONObject(i).getString("t").equals(i_mangaName)) {
					return jsonArray.getJSONObject(i).getString("i");
				}
			}
		} catch (Exception e) {
			return null;
		}
		return null;
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

	private String getLastChapter(StringBuffer i_buffer) {
		JSONObject jsonData = new JSONObject(i_buffer.toString());
		JSONArray jsonArray = (JSONArray) jsonData.getJSONArray("chapters").get(0);

		return jsonArray.get(0).toString();
	}

	private boolean generateJsonMangaList(StringBuffer i_buffer) {
		InputChecking.checkObjectInput(i_buffer, true, null, true, "i_buffer cannot be null.", null);

		try (FileWriter file = new FileWriter("mangalist.json")) {

			file.write(i_buffer.toString());
			file.flush();

			return true;

		} catch (IOException e) {
			return false;
		}
	}
}
