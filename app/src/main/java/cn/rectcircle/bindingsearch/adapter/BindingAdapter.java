package cn.rectcircle.bindingsearch.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rectcircle.bindingsearch.R;
import cn.rectcircle.bindingsearch.model.BindingState;
import cn.rectcircle.bindingsearch.model.RequireUrl;
import cn.rectcircle.bindingsearch.service.DownloadService;
import cn.rectcircle.bindingsearch.service.RequireUrlsService;
import cn.rectcircle.bindingsearch.util.StringUtil;
import com.google.gson.internal.LinkedHashTreeMap;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 绑定信息适配器
 * @author Rectcircle
 * @Date 2017/10/27
 */
public class BindingAdapter extends RecyclerView.Adapter<BindingAdapter.ViewHolder> {

	private List<BindingState> mBindingStateList;

	private RequireUrlsService mRequireUrlsService;

	private DownloadService mDownloadService;

	public BindingAdapter(List<BindingState> bindingStateList,
	                      RequireUrlsService requireUrlsService,
	                      DownloadService downloadService) {
		mBindingStateList = bindingStateList;
		mRequireUrlsService = requireUrlsService;
		mDownloadService = downloadService;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		final Context parentContext = parent.getContext();
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.binding_info_item, parent, false);
		final ViewHolder viewHolder = new ViewHolder(view);
		//绑定打开按钮，负责打开该网站的登录页面
		viewHolder.openButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int postion = viewHolder.getAdapterPosition();
				Intent intent = new Intent();
				intent.setAction("android.intent.action.VIEW");
				Uri content_url = Uri.parse(mBindingStateList.get(postion).getRequireUrl().getLoginUrl());
				intent.setData(content_url);

				if (intent.resolveActivity(parentContext.getPackageManager()) != null) {
					parentContext.startActivity(intent);
				}
			}
		});
		//分享按钮
		viewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int postion = viewHolder.getAdapterPosition();
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT,
						mBindingStateList.get(postion).getRequireUrl().getLoginUrl());

				if (intent.resolveActivity(parentContext.getPackageManager()) != null) {
					parentContext.startActivity(intent);
				}
			}
		});

		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		final ViewHolder fholder = holder;
		BindingState bindingState = mBindingStateList.get(position);
		holder.websiteNameTextView.setText(bindingState.getRequireUrl().getName());
		holder.stateTextView.setText(bindingState.getFinalState());
		//请求图片
		mDownloadService.download(bindingState.getRequireUrl().getLogoUrl())
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new ImageObserver(fholder));
	}

	@Override
	public int getItemCount() {
		return mBindingStateList.size();
	}

	public void search(String number) {
		for(BindingState state:mBindingStateList){
			final BindingState fstate = state;
			final RequireUrl requireUrl = state.getRequireUrl();

			final String url = requireUrl.getUrl();
			final Map<String, String> params = new LinkedHashTreeMap<>();
			params.putAll(requireUrl.getParams());
			//配置手机号参数
			params.put(requireUrl.getPhoneKey(),
					requireUrl.getPhoneParamPrefix()
							+number
							+requireUrl.getPhoneParamSuffix());

			if(requireUrl.getMethod().toUpperCase().equals("GET")){
				final String urlByGet = StringUtil.createGetUrl(url, params);
				//获取cookies的url
				mRequireUrlsService
						.getCookies(requireUrl.getRegisterUrl())
						.flatMap(new Function<Response<String>, ObservableSource<String>>() {
							@Override
							public ObservableSource<String> apply(Response<String> stringResponse) throws Exception {
								Map<String, String> headers = new HashMap<>(requireUrl.getHeaders());
								List<String> cookieList = stringResponse.headers().values("Set-Cookie");
								String cookie = StringUtil.getCookieFromSetCookie(cookieList);
								//配置cookie
								if(cookie!=null){
									headers.put("Cookie", cookie);
								}
								//配置Referer，解决可能出现的跨域问题
								headers.put("Referer", requireUrl.getRegisterUrl());

								//配置时间戳参数
								headleTimestampParam(requireUrl, params);
								return mRequireUrlsService.get(
										urlByGet,
										headers);
							}
						})
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new ResponseObserver(fstate, requireUrl));

			} else if(requireUrl.getMethod().toUpperCase().equals("POST")){
				mRequireUrlsService
						.getCookies(requireUrl.getRegisterUrl())
						.flatMap(new Function<Response<String>, ObservableSource<String>>() {
							@Override
							public ObservableSource<String> apply(Response<String> stringResponse) throws Exception {
								Map<String, String> headers = new HashMap<>(requireUrl.getHeaders());
								List<String> cookieList = stringResponse.headers().values("Set-Cookie");
								String cookie = StringUtil.getCookieFromSetCookie(cookieList);
								if(cookie!=null){
									headers.put("Cookie", cookie);
								}
								//配置Referer，解决可能出现的跨域问题
								headers.put("Referer", requireUrl.getRegisterUrl());

								//配置时间戳参数
								headleTimestampParam(requireUrl, params);

								return mRequireUrlsService.post(
										url,
										headers,
										params
								);
							}
						})
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new ResponseObserver(fstate, requireUrl));
			} else {

			}

		}
	}


	class ImageObserver implements Observer<ResponseBody>{

		private final ViewHolder holder;

		public ImageObserver(ViewHolder holder) {
			this.holder = holder;
		}

		@Override
		public void onSubscribe(Disposable d) {

		}

		@Override
		public void onNext(ResponseBody responseBody) {
			Bitmap bm = BitmapFactory.decodeStream(responseBody.byteStream());
			holder.logoImageView.setImageBitmap(bm);
		}

		@Override
		public void onError(Throwable e) {

		}

		@Override
		public void onComplete() {

		}
	}


	class ResponseObserver implements Observer<String>{

		private final BindingState fstate;
		private final RequireUrl requireUrl;

		public ResponseObserver(BindingState fstate, RequireUrl requireUrl) {
			this.fstate = fstate;
			this.requireUrl = requireUrl;
		}

		@Override
		public void onSubscribe(Disposable d) {
			fstate.setState(BindingState.StateEnum.RUNNING);
			notifyDataSetChanged();
		}

		@Override
		public void onNext(String s) {
			if(s.contains(requireUrl.getBound())){
				fstate.setResult(BindingState.ResultEnum.BINDED);
			} else if(s.contains(requireUrl.getNoBind())){
				fstate.setResult(BindingState.ResultEnum.NOBIND);
			} else {
				fstate.setResult(BindingState.ResultEnum.UNKNOWN);
			}
		}

		@Override
		public void onError(Throwable e) {
			fstate.setState(BindingState.StateEnum.ERROR);
			notifyDataSetChanged();
		}

		@Override
		public void onComplete() {
			fstate.setState(BindingState.StateEnum.FINISHED);
			notifyDataSetChanged();
		}
	}

	private void headleTimestampParam(RequireUrl requireUrl, Map<String, String> params){
		if(requireUrl.getTimestampKey() != null &&
				(!"".equals(requireUrl.getTimestampKey()))){
			if(requireUrl.getTimestampUnit().equals(RequireUrl.TIMESTAMP_UNIT_S)){
				params.put(requireUrl.getTimestampKey(), ""+System.currentTimeMillis()/1000);
			} else if(requireUrl.getTimestampUnit().equals(RequireUrl.TIMESTAMP_UNIT_MS)){
				params.put(requireUrl.getTimestampKey(), ""+System.currentTimeMillis());
			}
		}
	}



	static class ViewHolder extends RecyclerView.ViewHolder{
		ImageView logoImageView;
		TextView websiteNameTextView;
		TextView stateTextView;
		Button shareButton;
		Button openButton;


		public ViewHolder(View itemView) {
			super(itemView);
			logoImageView = (ImageView) itemView.findViewById(R.id.logo_img);
			websiteNameTextView = (TextView) itemView.findViewById(R.id.website_name);
			stateTextView = (TextView) itemView.findViewById(R.id.state);
			shareButton = (Button) itemView.findViewById(R.id.share);
			openButton = (Button) itemView.findViewById(R.id.open);
		}
	}

}
