package com.game.helper.adapter.home;

import java.util.ArrayList;
import com.game.helper.BaseActivity;
import com.game.helper.R;
import com.game.helper.activity.ChoosePicActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.model.mine.PersonaImg;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.home.PhotoStrategyAdapter.java
 * @Author lbb
 * @Date 2016年9月6日 下午3:21:05
 * @Company 
 */
public class PhotoStrategyAdapter extends BaseAdapter{
	public interface DeleteListener1{
		void delete(int pos);
		void showpop();
	}
	DeleteListener1 mDeleteListener;
	ArrayList<PersonaImg> list=new ArrayList<PersonaImg>();
	ArrayList<PersonaImg> listTrue=new ArrayList<PersonaImg>();
	Activity activity;
	private LayoutInflater mInflater;
	int maxSize;
	public PhotoStrategyAdapter(Activity ac,ArrayList<PersonaImg> l,int maxSize)
	{
		activity=ac;
		list=l;
		mInflater = LayoutInflater.from(activity);
		this.maxSize=maxSize;
	}
	public void setList(ArrayList<PersonaImg> list,ArrayList<PersonaImg> list1) {
		this.list = list;
		listTrue=list1;
		notifyDataSetChanged();
	}
	public void setmDeleteListener(DeleteListener1 mDeleteListener) {
		this.mDeleteListener = mDeleteListener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@SuppressWarnings("deprecation")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_home_strategyimg, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}

		if(list!=null&&list.size()>0){
			if(position==list.size()-1){//最后一个为“加”标图
				holder.iv_delete.setVisibility(View.GONE);
				if(maxSize+1>list.size()){
					convertView.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							/*((BaseActivity)activity).startActivityForResult(ChoosePicActivity.class, null,
									MineDataEditingActivity.TO_SELECT_PHOTO);*/
							if(mDeleteListener !=null){
								mDeleteListener.showpop();
							}
						}
					});
				}
				holder.rRelativeLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_photo));

				if(maxSize==5){
					holder.iv_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.shouye_297));
				}else 	if(maxSize==8){
					holder.iv_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pic_08));
				}else 	if(maxSize==9){
					holder.iv_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pic_09));
				}


			}else{
				holder.rRelativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.white));
				holder.iv_icon.setImageBitmap(list.get(position).smallBitmap);
				holder.iv_delete.setVisibility(View.VISIBLE);
				holder.iv_delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						list.remove(position);
						notifyDataSetChanged();
						if(mDeleteListener !=null){
							mDeleteListener.delete(position);
						}
					}
				});

			}
		}
		return convertView;
	}
	class ViewHolder {
		public @BindView(R.id.iv_icon) ImageView iv_icon;
		public @BindView(R.id.iv_delete) ImageView iv_delete;
		public @BindView(R.id.rRelativeLayout) RelativeLayout rRelativeLayout;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
}
