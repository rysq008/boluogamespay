package com.game.helper.draggridview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.ChoosePicActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.adapter.mine.DragDropGridAdapter.DeleteListener;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.ChangeIconOrderbyTask;
import com.game.helper.net.task.RemoveUserIconTask;
import com.game.helper.sdk.model.returns.UserIcons;
import com.game.helper.util.ToastUtil;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DragAdapter extends BaseAdapter implements DragGridBaseAdapter{
	private List<UserIcons> mUserIconsList=new ArrayList<UserIcons>();
	
	private List<PersonaImg> list=new ArrayList<PersonaImg>();
	private LayoutInflater mInflater;
	private int mHidePosition = -1;
	private Context context;
	public DragAdapter(Context context, List<PersonaImg> list){
		this.context=context;
		this.list = list;
		mInflater = LayoutInflater.from(context);
	}

	public List<PersonaImg> getList() {
		return list;
	}

	public void setList(List<PersonaImg> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	/**
	 * 由于复用convertView导致某些item消失了，所以这里不复用item，
	 */
	@Override
	public View getView( int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(R.layout.item_listview_mine_person_img, null);
		ImageView iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
		ImageView iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete);
		RelativeLayout iRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.iRelativeLayout);
		convertView.setTag(position);
		iv_delete.setTag(position);
		if(position == mHidePosition){
			convertView.setVisibility(View.INVISIBLE);
		}
		if(list!=null&&list.size()>0){
			iRelativeLayout.setBackgroundColor(context.getResources().getColor(R.color.white));
			if(list.get(position).smallBitmap!=null){
				iv_icon.setImageBitmap(list.get(position).smallBitmap);
			}else{
				Glide.with(BaseApplication.mInstance.context.getApplicationContext())
				.load(""+list.get(position).fileAskPath+list.get(position).icon)
				.diskCacheStrategy(DiskCacheStrategy.SOURCE)
				//.centerCrop()// 长的一边撑满
				//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
				.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
				//.crossFade()
				.into(iv_icon);
			}
			iv_delete.setVisibility(View.VISIBLE);
			iv_delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final PersonaImg temp = list.get(Integer.parseInt(v.getTag().toString()));
                     new RemoveUserIconTask(context, ""+temp.id, new Back() {
						
						@Override
						public void success(Object object, String msg) {
							list.remove(temp);
							notifyDataSetChanged();
						}
						
						@Override
						public void fail(String status, String msg, Object object) {
							ToastUtil.showToast(context, "删除失败 "+msg);
							
						}
					}).start();
					/*if(mDeleteListener !=null){
						mDeleteListener.delete(position);
					}*/
					
				}
			});
			
		}
		return convertView;
	}
	

	@Override
	public void reorderItems(int oldPosition, int newPosition) {
		PersonaImg temp = list.get(oldPosition);
		PersonaImg tempTo = list.get(newPosition);
		if(oldPosition < newPosition){
			for(int i=oldPosition; i<newPosition; i++){
				Collections.swap(list, i, i+1);
			}
		}else if(oldPosition > newPosition){
			for(int i=oldPosition; i>newPosition; i--){
				Collections.swap(list, i, i-1);
			}
		}
		
		list.set(newPosition, temp);
		notifyDataSetChanged();
		
		mUserIconsList.clear();
		int i=0;
		for(PersonaImg p:list){
			++i;
			mUserIconsList.add(new UserIcons(""+p.id, ""+i));
			Log.e("lbb",p.id+":--id-----list------orderby---:"+i);
		}
		Log.e("lbb","----------------------------------list-end----------------------------------------");
		new ChangeIconOrderbyTask(context,mUserIconsList, new Back() {
			
			@Override
			public void success(Object object, String msg) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void fail(String status, String msg, Object object) {
				// TODO Auto-generated method stub
				
			}
		}).start();
	}

	@Override
	public void setHideItem(int hidePosition) {
		this.mHidePosition = hidePosition; 
		notifyDataSetChanged();
	}
	/*private OnClickListener onClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int index=Integer.parseInt(v.getTag().toString());
			deleteItem(index);
			if(mDeleteListener !=null){
				mDeleteListener.delete(index);
			}
		}
	};
	public void deleteItem( int itemIndex) {
		// TODO Auto-generated method stub
		list.remove(itemIndex);
		notifyDataSetChanged();
	}*/
	public interface DeleteListener{
		void delete(int pos);
	}
	DeleteListener mDeleteListener;
	public void setmDeleteListener(DeleteListener mDeleteListener) {
		this.mDeleteListener = mDeleteListener;
	}

}
