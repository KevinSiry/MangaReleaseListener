package com.listener.release.manga.launcher;

import com.listener.release.manga.core.MangaListenerCore;

public class Launcher {

	public static void main(String[] args) {
		MangaListenerCore mangaListenerCore = new MangaListenerCore();
		String test = mangaListenerCore.getMangaId("One Piece");
		System.out.println(test);
	}
}
