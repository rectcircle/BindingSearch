package cn.rectcircle.bindingsearch.service;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author Rectcircle
 * @date 2017/10/28
 */
public interface DownloadService {
	@GET
	Observable<ResponseBody> download(@Url String url);
}
