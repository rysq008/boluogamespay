package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetInfoAct.InfoAct;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.ActivityAnnouncementAdapter.java
 * @Author lbb
 * @Date 2016年8月22日 上午10:55:14
 * @Company 
 */
public class ActivityAnnouncementAdapter extends BaseAdapter{

	private List<InfoAct> mList=new ArrayList<InfoAct>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public ActivityAnnouncementAdapter(Context mContext,List<InfoAct> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<InfoAct> getmList() {
		return mList;
	}

	public void setmList(List<InfoAct> mList) {
		this.mList = mList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_home_activity_announcement, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		InfoAct mInfoAct=(InfoAct) getItem(position);
		holder.setData(mInfoAct);
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.title) XCRoundImageViewByXfermode title;
		public @BindView(R.id.msg) TextView msg;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(InfoAct mInfoAct){
			if(mInfoAct!=null){
				if(TextUtils.isEmpty(mInfoAct.contentAbstract)){
					msg.setText(mInfoAct.contentTitle);
				}else{
					msg.setText(mInfoAct.contentTitle+":"+mInfoAct.contentAbstract);
				}
				
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mInfoAct.fileAskPath+mInfoAct.contentImage)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(title);
			}
		}
	}

}
