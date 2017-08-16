package com.game.helper.adapter.home;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.R;
import com.game.helper.sdk.model.returns.QueryMyAccount.Account;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.ToastUtil;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.SelectCollarDiscountNumberAdapter.java
 * @Author lbb
 * @Date 2016年11月21日 下午1:37:22
 * @Company 
 */
public class SelectCollarDiscountNumberAdapter extends BaseAdapter{
	private Context mContext;
	protected LayoutInflater mInflater;
	protected List<Account>  data=new ArrayList<Account>();
	
	public SelectCollarDiscountNumberAdapter(Context mContext,List<Account>  data) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.data=data;
	}

	public List<Account> getData() {
		return data;
	}

	public void setData(List<Account> data) {
		this.data = data;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Account getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
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
			convertView = mInflater.inflate(R.layout.item_listview_home_select_discount_number, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final Account mMineCollect=getItem(position);
		holder.setData(mMineCollect);
		
		return convertView;
	}

	class ViewHolder {
		
		public @BindView(R.id.iv_type) ImageView iv_type;
		public @BindView(R.id.tv_type) TextView tv_type;
		public @BindView(R.id.tv_item) TextView tv_item;
		public @BindView(R.id.tv_number) TextView tv_number;
		//public @BindView(R.id.tv_pwd) TextView tv_pwd;
		//public @BindView(R.id.tv_copy) TextView tv_copy;
 
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(final Account mMineCollect){
			if(mMineCollect!=null){
				tv_type.setText(mMineCollect.platName);
				tv_item.setText(mMineCollect.getTimeString);
				tv_number.setText(mMineCollect.gameAccount);
				/*tv_pwd.setText(mMineCollect.gamePwd);
				tv_copy.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if(SystemUtil.getSDKVersionNumber() >= 11){
							android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);  
							clipboardManager.setText(mMineCollect.gameAccount);  
						}else{
							// 得到剪贴板管理器
							android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);  
							clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mMineCollect.gameAccount));
						}
						ToastUtil.showToast(mContext, "成功复制到剪切板");
					}
				});*/
				
				
			}
		}
	}
	

}
