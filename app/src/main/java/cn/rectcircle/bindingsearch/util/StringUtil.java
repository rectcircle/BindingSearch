package cn.rectcircle.bindingsearch.util;

import java.util.List;

/**
 * @author Rectcircle
 * @date 2017/10/28
 */
public class StringUtil {
	public static String getCookieFromSetCookie(List<String> setCookieList){
		StringBuffer sb = new StringBuffer();
		for (String setCookie:setCookieList) {
			sb.append(setCookie.split(";")[0]);
			sb.append("; ");
		}
		return sb.substring(0, sb.length()-2);
	}
}
