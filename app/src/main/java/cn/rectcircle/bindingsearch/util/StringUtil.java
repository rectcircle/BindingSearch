package cn.rectcircle.bindingsearch.util;

import com.google.gson.internal.LinkedTreeMap;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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

	public static String createGetUrl(String url, Map<String, String> params) {
		StringBuffer sb = new StringBuffer(url);
		if(!url.contains("?")){
			sb.append('?');
		}
		if (params.size() != 0) {
			StringBuffer paramStringBuffer = new StringBuffer();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				if (null == entry.getValue() || "".equals(entry.getValue())) {
					paramStringBuffer.append(entry.getKey());
					paramStringBuffer.append("&");
				} else {
					paramStringBuffer.append(entry.getKey());
					paramStringBuffer.append("=");
					try {
						paramStringBuffer.append(URLEncoder.encode(entry.getValue(),"utf8"));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					paramStringBuffer.append("&");
				}
			}
			sb.append(paramStringBuffer.substring(0, paramStringBuffer.length() - 1));
		}
		return sb.toString();
	}

	public static String urlEncode(String src){
		try {
			return URLEncoder.encode(src, "utf8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
