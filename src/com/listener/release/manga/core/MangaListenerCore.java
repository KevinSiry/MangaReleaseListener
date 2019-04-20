package com.listener.release.manga.core;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.listener.release.manga.output.MangaListOutput;
import com.listener.release.manga.output.MangaListOutput.MangaListStatus;

public class MangaListenerCore {

	public MangaListOutput getMangaList() {
		try {
			URL url = new URL("https://www.mangaeden.com/api/list/0/");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer content = new StringBuffer();
			while ((inputLine = bufferReader.readLine()) != null) {
				content.append(inputLine);
			}
			bufferReader.close();

			if(generateJsonMangaList(content)) {
				return new MangaListOutput(MangaListStatus.SUCCESS, "GetMangaList done successfully.");
			} else {
				return new MangaListOutput(MangaListStatus.FAIL, "GetMangaList failed : error in Manga list json generation.");
			}
		} catch (Exception e) {
			return new MangaListOutput(MangaListStatus.FAIL, "GetMangaList failed : " + e.getMessage());
		}
	}

	private boolean generateJsonMangaList(StringBuffer i_buffer) {
		try (FileWriter file = new FileWriter("mangalist.json")) {
			 
            file.write(i_buffer.toString());
            file.flush();
            
            return true;
 
        } catch (IOException e) {
            return false;
        }
	}
}
