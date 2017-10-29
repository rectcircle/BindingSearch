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
		if(sb.length()==0){
			return null;
		} else {
			return sb.substring(0, sb.length()-2);
		}
	}
}
