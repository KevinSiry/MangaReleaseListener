package com.listener.release.manga.commonmethods;

public class InputChecking {

	public static boolean checkStringInput(String i_input, boolean i_inputNull, boolean i_inputEmpty, boolean i_exception,
			String i_nullException, String i_emptyException) {

		if (i_inputNull && i_input == null) {
			if (i_exception) {
				throw new RuntimeException(i_nullException);
			}
		}

		if (i_inputEmpty && i_input.trim().isEmpty()) {
			if (i_exception) {
				throw new RuntimeException(i_emptyException);
			}
		}

		return true;
	}

	public static boolean checkObjectInput(Object i_input, boolean i_inputNull, Class<?> i_inputClass, boolean i_exception,
			String i_nullException, String i_classException) {
		
		if(i_inputNull && i_input == null) {
			if (i_exception) {
				throw new RuntimeException(i_nullException);
			}
		}
		
		if(i_inputClass != null && !(i_inputClass.isInstance(i_input))) {
			if (i_exception) {
				throw new RuntimeException(i_classException);
			}
		}
		
		return true;
	}
}
