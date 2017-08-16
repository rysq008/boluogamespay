package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;
import butterknife.ButterKnife;
import butterknife.BindView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.model.PushModel;
import com.game.helper.model.mine.SystemMsgModel;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.QueryUserIconTask;
import com.game.helper.sdk.model.returns.QueryUserIcon;
import com.game.helper.view.widget.CircleImageView;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SystemMsgAdapter extends BaseAdapter{
	private List<PushModel> mList=new ArrayList<PushModel>();
	private Context mContext;
	protected LayoutInflater mInflater;
	public SystemMsgAdapter(Context mContext,List<PushModel> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<PushModel> getmList() {
		return mList;
	}

	public void setmList(List<PushModel> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_mine_systemmsg, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		PushModel mMineCollect=(PushModel) getItem(position);
		holder.setData(mMineCollect);
		return convertView;
		
	}
	class ViewHolder {
		public @BindView(R.id.iv_icon) CircleImageView iv_icon;
		
		public @BindView(R.id.tv_title) TextView tv_title;
		public @BindView(R.id.tv_time) TextView tv_time;
		public @BindView(R.id.tv_people) TextView tv_people;
		public @BindView(R.id.mViewR) View mViewR;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(PushModel mMineCollect){
			if(mMineCollect!=null){
				if(mMineCollect.isRead==0){
					mViewR.setVisibility(View.VISIBLE);
				}else if(mMineCollect.isRead==1){
					mViewR.setVisibility(View.GONE);
				}
				if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("0")){
					/*new QueryUserIconTask(mContext, mMineCollect.id, new Back() {
						
						@Override
						public void success(Object object, String msg) {
							if(object!=null && object instanceof QueryUserIcon){
								QueryUserIcon mQueryUserIcon=(QueryUserIcon) object;
								if(mQueryUserIcon.data!=null){
									Glide.with(BaseApplication.mInstance.context.getApplicationContext())
									.load(""+mQueryUserIcon.data.fileAskPath+mQueryUserIcon.data.img)
									.diskCacheStrategy(DiskCacheStrategy.SOURCE)
									//.centerCrop()// 长的一边撑满
									//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
									.error(R.drawable.pic_moren)//加载失败时显示的图片
									//.crossFade()
									.into(iv_icon);
								}
							}
							
						}
						
						@Override
						public void fail(String status, String msg, Object object) {
							// TODO Auto-generated method stub
							
						}
					}).start();*/
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("1")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("2")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("3")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("4")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("5")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("6")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("7")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else if(!TextUtils.isEmpty(mMineCollect.tradetype)&&mMineCollect.tradetype.equals("8")){
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}else{
					iv_icon.setVisibility(View.GONE);
					tv_people.setVisibility(View.GONE);
					tv_title.setText(mMineCollect.content);
					tv_time.setText(mMineCollect.tradedate);
				}
			}
		}
		
	}

}
