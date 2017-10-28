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
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

import java.util.List;

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
				.subscribe(new Consumer<ResponseBody>() {
					@Override
					public void accept(ResponseBody responseBody) throws Exception {
						Bitmap bm = BitmapFactory.decodeStream(responseBody.byteStream());
						fholder.logoImageView.setImageBitmap(bm);
					}
				});
	}

	@Override
	public int getItemCount() {
		return mBindingStateList.size();
	}

	public void search(String number) {
		for(BindingState state:mBindingStateList){
			final BindingState fstate = state;
			final RequireUrl requireUrl = state.getRequireUrl();
			if(requireUrl.getMethod().toUpperCase().equals("GET")){
				String target = requireUrl.getPrefix()
						+ RequireUrl.PHONE_PARAM
						+ requireUrl.getSuffix();

				final String url = requireUrl.getUrl().replace(target, number);
				mRequireUrlsService
						.getCookies(url)
						.flatMap(new Function<Response<String>, ObservableSource<String>>() {
							@Override
							public ObservableSource<String> apply(Response<String> stringResponse) throws Exception {
								Headers headers = stringResponse.headers();
								return mRequireUrlsService.get(url, headers.get("Set-Cookie"));
							}
						})
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(new Observer<String>() {
							@Override
							public void onSubscribe(Disposable d) {
								fstate.setState(BindingState.StateEnum.RUNNING);
								notifyDataSetChanged();
							}

							@Override
							public void onNext(String s) {
								if(s.contains(requireUrl.getIsBind())){
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
						});


			} else if(requireUrl.getMethod().toUpperCase().equals("POST")){

			} else {

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
