package com.game.helper.adapter.mall;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.model.BaseViewHolder;
import com.game.helper.sdk.model.returns.GetGoodList.Good;
import com.game.helper.util.HtmlImageGetter;
import com.game.helper.util.URLImageParser;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description
 * @author 
 */
public class MallGridAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Good> mGoodList= new ArrayList<Good>();
	public MallGridAdapter(Context mContext,ArrayList<Good> mGoodList) {
		super();
		this.mContext = mContext;
		this.mGoodList=mGoodList;
	}

	public ArrayList<Good> getmGoodList() {
		return mGoodList;
	}

	public void setmGoodList(ArrayList<Good> mGoodList) {
		this.mGoodList = mGoodList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mGoodList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mGoodList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.item_gridview_mall_goods, parent, false);
		}
		TextView tv_item = BaseViewHolder.get(convertView, R.id.tv_item);
		//TextView tv_goodType = BaseViewHolder.get(convertView, R.id.tv_goodType);
		TextView tv_msg = BaseViewHolder.get(convertView, R.id.tv_msg);
		ImageView iv_item = BaseViewHolder.get(convertView, R.id.iv_item);
		TextView tv_money = BaseViewHolder.get(convertView, R.id.tv_money);
		TextView item_mall_goods_yuanjia_tv = BaseViewHolder.get(convertView, R.id.item_mall_goods_yuanjia_tv);
		item_mall_goods_yuanjia_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG ); //中间横线
		Good good=mGoodList.get(position);
		if(good!=null){
			tv_item.setText(good.goodName);
			item_mall_goods_yuanjia_tv.setText(good.price+"元");
			//tv_goodType.setText(good.goodType);
			try {
				//默认图片，无图片或没加载完显示此图片
				Drawable defaultDrawable = mContext.getResources().getDrawable(R.drawable.preview_card_pic_loading);
				//调用
				Spanned sp = Html.fromHtml(good.content, new URLImageParser(tv_msg), null);//, "/g9esun_msg", defaultDrawable
				
				tv_msg.setText(sp);
				
				//tv_msg.setText(""+Html.fromHtml(good.content));
			} catch (Exception e) {
				e.printStackTrace();
				tv_msg.setText(good.content);
			}
			
			if(!TextUtils.isEmpty(good.ptb)){
				tv_money.setText(good.ptb+"积分");
			}else{
				tv_money.setText("0");
			}
			
			Glide.with(BaseApplication.mInstance.context.getApplicationContext())
			.load(""+good.fileAskPath+good.img)
			.diskCacheStrategy(DiskCacheStrategy.SOURCE)
			//.centerCrop()// 长的一边撑满
			//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
			.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
			//.crossFade()
			.into(iv_item);
		}
		return convertView;
	}

}
