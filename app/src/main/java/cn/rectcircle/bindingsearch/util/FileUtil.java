package cn.rectcircle.bindingsearch.util;

import android.content.Context;

import java.io.*;

public class FileUtil {
	public static void saveString(Context context, String filename, String content){
		FileOutputStream outputStream;
		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(content.getBytes("utf8"));
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String readString(Context context, String filename){
		InputStream in = null;
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer("");
		try {
			in = context.openFileInput(filename);
			reader = new BufferedReader(new InputStreamReader(in));

			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try{
				if(reader!=null)
					reader.close();
				if(in!=null)
					in.close();
			}catch (IOException e){
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

}
