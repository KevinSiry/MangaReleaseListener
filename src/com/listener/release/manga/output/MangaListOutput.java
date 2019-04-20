package com.listener.release.manga.output;

import com.listener.release.manga.commonmethods.InputChecking;

public class MangaListOutput {

	public enum MangaListStatus {
		SUCCESS, FAIL
	}

	private MangaListStatus m_status = null;
	private String m_details = null;

	public MangaListOutput(MangaListStatus i_status, String i_details) {
		InputChecking.checkObjectInput(i_status, true, null, true, "Status cannot be null.", null);
		InputChecking.checkStringInput(i_details, true, true, true, "Details cannot be null.",
				"Details cannot be empty");
		
		m_status = i_status;
		m_details = i_details;
	}
	
	public MangaListStatus getStatus() {
		return m_status;
	}
	
	public String getDetails() {
		return m_details;
	}
}
