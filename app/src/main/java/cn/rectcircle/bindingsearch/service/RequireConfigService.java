package cn.rectcircle.bindingsearch.service;

import cn.rectcircle.bindingsearch.model.RequireUrl;
import cn.rectcircle.bindingsearch.model.RequireUrls;
import cn.rectcircle.bindingsearch.model.Version;
import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RequireConfigService {
	@GET("BindingSearch/raw/master/requireUrls.json")
	Observable<RequireUrls> getRequireUrls();

	@GET("BindingSearch/raw/master/version.json")
	Observable<Version> checkUrlsVersion();
}
