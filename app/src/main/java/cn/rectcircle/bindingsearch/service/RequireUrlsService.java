package cn.rectcircle.bindingsearch.service;

import cn.rectcircle.bindingsearch.model.RequireUrls;
import cn.rectcircle.bindingsearch.model.Version;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.Map;

/**
 *
 */
public interface RequireUrlsService {
	//@Header("Cookie") String cookies
	@GET
	Observable<String> get(@Url String url, @HeaderMap Map<String, String> headers );

	@GET
	Observable<Response<String>> getCookies(@Url String url);

	@POST
	@FormUrlEncoded
	Observable<String> post(@Url String url, @HeaderMap Map<String, String> headers, @FieldMap Map<String, String> fieldMap);
}
