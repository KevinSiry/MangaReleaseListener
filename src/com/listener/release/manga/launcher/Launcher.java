package com.listener.release.manga.launcher;

import org.json.JSONArray;

import com.listener.release.manga.core.MangaListenerCore;

public class Launcher {

	public static void main(String[] args) {
		MangaListenerCore listenerCore = new MangaListenerCore();
		JSONArray jsonArray = listenerCore.getUserMangaList();

		for (int i = 0; i < jsonArray.length(); i++) {
			String mangaTag = jsonArray.getJSONObject(i).getString("tag");
			System.out.println(listenerCore.getLastChapter(mangaTag));
		}
	}
}
