package com.game.helper.adapter.home;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetPtbRule.PtbRule;
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
 * @Path com.game.helper.adapter.home.TaskCenterAwardAdapter.java
 * @Author lbb
 * @Date 2016年8月25日 上午9:45:51
 * @Company 
 */
public class TaskCenterAwardAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<PtbRule> mList=new ArrayList<PtbRule>();
	
	public TaskCenterAwardAdapter(Context mContext,List<PtbRule> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}
	public List<PtbRule> getmList() {
		return mList;
	}

	public void setmList(List<PtbRule> mList) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_home_task_center_award, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final PtbRule mMineCollect=(PtbRule) getItem(position);
		holder.setData(mMineCollect);
		
		return convertView;
	}

	class ViewHolder {
		
		public @BindView(R.id.iv_logo) ImageView iv_logo;
		public @BindView(R.id.tv_title) TextView tv_title;
		public @BindView(R.id.tv_g) TextView tv_g;
		public @BindView(R.id.tv_msg) TextView tv_msg;
		

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(PtbRule m){
			if(m!=null){
				tv_title.setText(m.resource);
				
				if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("register")){//
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_245));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					
					tv_g.setText("每日最多可获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("invite")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wode_55));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日邀请他人下载可获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("pSign")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_127));
					tv_g.setText("每日平台签到"+m.maxTime+"次");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("gSign")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wode_53));
					tv_g.setText("每日公会签到"+m.maxTime+"次");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("share")){//
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_129));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日分享资讯最多可获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("publish")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_134));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日发布攻略最多可获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("dz")){//
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_132));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("award")){//
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wode_57));
					tv_g.setText("每日最多可免费摇"+m.maxTime+"次");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("pl")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wode_92));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("friendCircle")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_203));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("qq")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_209));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("weibo")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.logo_sinaweibo));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("qqFriend")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_207));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("weixinFriend")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shouye_205));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}else if(!TextUtils.isEmpty(m.ruleCode)&&m.ruleCode.equals("libao")){
					iv_logo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wode_113));
					BigDecimal bd = new BigDecimal(m.maxTime*m.ptb);
					bd = bd.setScale(2,BigDecimal.ROUND_HALF_UP);
					tv_g.setText("每日最多可以获得"+bd.toString()+"金币");
				}
				
				tv_msg.setText(""+m.ptb);
			}
		}
	}
	

}
