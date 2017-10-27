package cn.rectcircle.bindingsearch.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.rectcircle.bindingsearch.R;
import cn.rectcircle.bindingsearch.model.RequireUrl;

import java.util.List;

public class BindingAdapter extends RecyclerView.Adapter<BindingAdapter.ViewHolder> {

	private List<RequireUrl> mRequireUrlList;

	public BindingAdapter(List<RequireUrl> requireUrlList) {
		mRequireUrlList = requireUrlList;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.binding_info_item, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);



		return viewHolder;
	}

	@Override
	public void onBindViewHolder(ViewHolder holder, int position) {
		RequireUrl requireUrl = mRequireUrlList.get(position);
		holder.websiteNameTextView.setText(requireUrl.getName());
		holder.stateTextView.setText("测试中");
	}

	@Override
	public int getItemCount() {
		return mRequireUrlList.size();
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
