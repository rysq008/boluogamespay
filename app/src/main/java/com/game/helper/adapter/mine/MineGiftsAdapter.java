package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.QueryMyGift.MyGift;
import com.game.helper.util.SystemUtil;
import com.game.helper.util.TimeUtil;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.MineGiftsAdapter.java
 * @Author lbb
 * @Date 2016年8月19日 下午4:01:20
 * @Company 
 */
public class MineGiftsAdapter extends BaseAdapter{

	private List<MyGift> mList=new ArrayList<MyGift>();
	private Context mContext;
	protected LayoutInflater mInflater;
	
	public MineGiftsAdapter(Context mContext,List<MyGift> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}

	public List<MyGift> getmList() {
		return mList;
	}

	public void setmList(List<MyGift> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_mine_gifts, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		final MyGift mMineCollect=(MyGift) getItem(position);
		holder.setData(mMineCollect);
		holder.tv_Copy.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(SystemUtil.getSDKVersionNumber() >= 11){
                    android.text.ClipboardManager clipboardManager = (android.text.ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);  
                    clipboardManager.setText(mMineCollect.getCode);  
                }else{
                    // 得到剪贴板管理器
                    android.content.ClipboardManager clipboardManager = (android.content.ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);  
                    clipboardManager.setPrimaryClip(ClipData.newPlainText(null, mMineCollect.getCode));
                }
				ToastUtil.showToast(mContext, "已复制");
			}
		});
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.title) TextView title;
		public @BindView(R.id.time) TextView time;
		public @BindView(R.id.no) TextView no;
		public @BindView(R.id.icon) XCRoundImageViewByXfermode icon;
		public @BindView(R.id.tv_Copy) TextView tv_Copy;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(MyGift mMineCollect){
			if(mMineCollect!=null){
				icon.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
				icon.setRoundBorderRadius(23);
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+mMineCollect.fileAskPath+mMineCollect.field4)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(icon);
				
				title.setText(mMineCollect.field1);
				time.setText("有效期："+TimeUtil.changeTimeFormat(mMineCollect.field2, "yyyy-MM-dd")
						    +"\n"
						    +"         至   "+ TimeUtil.changeTimeFormat(mMineCollect.field3, "yyyy-MM-dd"));
				no.setText("卡号："+mMineCollect.getCode);
			}
		}
		
	}
}
