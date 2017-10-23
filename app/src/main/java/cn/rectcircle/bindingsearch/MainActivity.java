package cn.rectcircle.bindingsearch;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import cn.rectcircle.bindingsearch.model.RequireUrls;
import cn.rectcircle.bindingsearch.model.Version;
import cn.rectcircle.bindingsearch.service.RequireConfigService;
import cn.rectcircle.bindingsearch.service.RequireUrlsService;
import cn.rectcircle.bindingsearch.util.AssetUtil;
import cn.rectcircle.bindingsearch.util.FileUtil;
import cn.rectcircle.bindingsearch.util.NetWorkStateUtil;
import com.google.gson.Gson;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

//控制器
public class MainActivity extends AppCompatActivity {


	//常量
	private static final String GITHUB_USER="rectcircle"; //github 仓库用户
	private static final String GITHUB_CONTENT_URL="https://raw.githubusercontent.com/"; //读取github上内容的地址
	private static final String VERSION_FILE_NAME = "version.json"; //配置版本的位置
	private static final String REQUIREURLS_FILE_NAME = "requireUrls.json"; //配置文件
	private static final String CONFIG_VERSION = "cn.rectcircle.bindingsearch.CONFIG_VERSION";


	//Android内置对象
	private TextView textViewTest;
	private ProgressBar progressBar;
	private SharedPreferences sharedPref;
	//ProgressBar
	//private ProgressDialog dialog;

	//工具对象
	private Gson gson = new Gson();
	private Retrofit retrofitConfig; //http请求器，负责请求、获取配置
	private Retrofit retrofitNumberApi;

	//服务对象
	private RequireConfigService requireConfigService; //请求配置
	private RequireUrlsService requireUrlsService; //请求

	//数据缓存对象
	private Version version;
	private RequireUrls requireUrls;

	private boolean finishUpdateConfig = false;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textViewTest = (TextView) findViewById(R.id.textViewTest);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);

		retrofitConfig = new Retrofit.Builder()
				.baseUrl("https://www.rectcircle.cn/article/")
				.addConverterFactory(ScalarsConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();


		retrofitNumberApi = new Retrofit.Builder()
				.baseUrl(GITHUB_CONTENT_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.build();

		requireConfigService = retrofitConfig.create(RequireConfigService.class);
		requireUrlsService = retrofitNumberApi.create(RequireUrlsService.class);

		//从apk包中读取配置文件
		version = gson.fromJson(AssetUtil.readString(this, VERSION_FILE_NAME),
								Version.class) ;
		requireUrls = gson.fromJson(AssetUtil.readString(this, REQUIREURLS_FILE_NAME),
				RequireUrls.class);

		sharedPref = getPreferences(Context.MODE_PRIVATE);


	}


	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if(!finishUpdateConfig){
			updateConfig();
			finishUpdateConfig = true;
		}
	}

	/**
	 * 更新配置文件
	 */
	private void updateConfig(){
		//读取内置储存器中的配置文件，更新配置
		if(!sharedPref.contains(CONFIG_VERSION)){
			//不存在，保存
			SharedPreferences.Editor editor = sharedPref.edit();
			editor.putString(CONFIG_VERSION, version.getVersion());
			editor.apply();
			//保存配置文件到内部存储
			FileUtil.saveString(this, REQUIREURLS_FILE_NAME, gson.toJson(requireUrls));
		} else {
			String nowVersion = sharedPref.getString(CONFIG_VERSION,"");
			//存在
			if(!nowVersion.equals(version.getVersion())){ //版本号不相等
				//更新环境中的缓存对象
				version = new Version(nowVersion);
				requireUrls = gson.fromJson(FileUtil.readString(this, REQUIREURLS_FILE_NAME),
						RequireUrls.class);
			}
		}

		if(NetWorkStateUtil.isNetworkConnected(this)){
			//从网络中检查配置
			changeConfig();
		} else {
			showNetworkAlertDialog();
		}
	}

	/**
	 * 显示网络状态提示框
	 */
	private void showNetworkAlertDialog(){
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Tips");
		dialog.setMessage("The app need network!");
		dialog.setCancelable(false); //禁止通过返回键取消
		dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
			}
		});
		dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				MainActivity.this.finish();
			}
		});
		dialog.show();
	}

	private void changeConfig(){
		showProgressBar();
		requireConfigService
				.checkUrlsVersion(GITHUB_USER) //首先请求获取Api的版本信息
				.flatMap(new Function<Version, ObservableSource<RequireUrls>>() {
					@Override
					public ObservableSource<RequireUrls> apply(Version version) throws Exception {
						if(version.equals(MainActivity.this.version)){ //版本没有变
							MainActivity.this.version = version;
							return Observable.just(MainActivity.this.requireUrls); //直接返回内置配置
						} else {
							return requireConfigService.getRequireUrls(GITHUB_USER); //再执行一次请求
						}
					}
				})
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<RequireUrls>() {
					@Override
					public void accept(RequireUrls requireUrls) throws Exception {
						MainActivity.this.requireUrls = requireUrls;
						//更新配置文件
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.putString(CONFIG_VERSION, version.getVersion());
						editor.apply();
						//保存配置文件到内部存储
						FileUtil.saveString(MainActivity.this, REQUIREURLS_FILE_NAME, gson.toJson(requireUrls));
						hideProgressBar();
					}
				});
	}

	private void showProgressBar(){
		if(progressBar!=null){
			progressBar.setVisibility(View.VISIBLE);
		}
	}

	private void hideProgressBar(){
		if(progressBar!=null){
			progressBar.setVisibility(View.GONE);
		}
	}

	public void getRequiresUrls(View view){
		requireUrlsService
				.get("/")
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Consumer<String>() {
					@Override
					public void accept(String s) throws Exception {
						textViewTest.setText(s);
					}
				});
	}
}
