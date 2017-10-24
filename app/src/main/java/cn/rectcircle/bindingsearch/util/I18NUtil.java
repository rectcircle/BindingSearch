package cn.rectcircle.bindingsearch.util;

import android.content.Context;

import java.util.Locale;

public class I18NUtil {
	public static boolean isZh(Context context) {
		Locale locale = context.getResources().getConfiguration().locale;
		String language = locale.getLanguage();
		return language.endsWith("zh");
	}
}
