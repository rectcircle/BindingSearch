package cn.rectcircle.bindingsearch.service;

import cn.rectcircle.bindingsearch.model.RequireUrls;
import cn.rectcircle.bindingsearch.model.Version;
import io.reactivex.Observable;
import retrofit2.http.*;

import java.util.Map;

/**
 *
 */
public interface RequireUrlsService {
	@GET
	Observable<String> get(@Url String url);

	@POST
	Observable<String> post(@FieldMap Map<String, String> fieldMap);
}
