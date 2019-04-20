package com.listener.release.manga.output;

import com.listener.release.manga.commonmethods.InputChecking;

public class MangaChapterOutput {
	
	public enum MangaChapterStatus {
		SUCCESS, FAIL
	}
	
	private MangaChapterStatus m_status = null;
	private String m_details = null;
	
	public MangaChapterOutput(MangaChapterStatus i_status, String i_details) {
		InputChecking.checkObjectInput(i_status, true, null, true, "Status cannot be null.", null);
		InputChecking.checkStringInput(i_details, true, true, true, "Details cannot be null.",
				"Details cannot be empty");
		
		m_status = i_status;
		m_details = i_details;
	}
	
	public MangaChapterStatus getStatus() {
		return m_status;
	}
	
	public String getDetails() {
		return m_details;
	}
}
