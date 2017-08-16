package com.game.helper.adapter.home;

import com.game.helper.R;
import com.game.helper.model.BaseViewHolder;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class HomeGridAdapter extends BaseAdapter {
	private Context mContext;

	public String[] img_text = { "我的游戏", "网游", "单机", "主题", "快捷充值", "领折扣号",
			"礼包", "排行榜" };
	public int[] imgs = { R.drawable.shouye_156, R.drawable.shouye_158,
			R.drawable.shouye_161, R.drawable.shouye_163,
			R.drawable.shouye_165, R.drawable.shouye_167,
			R.drawable.shouye_169, R.drawable.shouye_171};

	public HomeGridAdapter(Context mContext) {
		super();
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_text.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
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
					R.layout.item_gridview_home, parent, false);
		}
		TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
		ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
		iv.setBackgroundResource(imgs[position]);

		tv.setText(img_text[position]);
		return convertView;
	}

}
