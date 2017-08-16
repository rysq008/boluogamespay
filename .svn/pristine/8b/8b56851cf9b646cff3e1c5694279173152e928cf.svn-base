package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;

import com.game.helper.R;
import com.game.helper.model.mine.PersonaImg;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import butterknife.ButterKnife;
import butterknife.BindView;

/**
 * @Description
 * @Path com.game.helper.adapter.mine.SelectPersonaImgAdapter.java
 * @Author lbb
 * @Date 2016年9月1日 下午1:36:00
 * @Company 
 */
public class SelectPersonaImgAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<PersonaImg> mList=new ArrayList<PersonaImg>();
	public SelectPersonaImgAdapter(Context mContext,List<PersonaImg> mList) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.mList=mList;
	}
	
	public List<PersonaImg> getmList() {
		return mList;
	}


	public void setmList(List<PersonaImg> mList) {
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
			convertView = mInflater.inflate(R.layout.item_listview_mine_person_img, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		if(position!=mList.size()-1){
			holder.setData(mList.get(position));
		}
		return convertView;
	}

	class ViewHolder {
		public @BindView(R.id.iv_icon) ImageView iv_icon;
		public @BindView(R.id.iv_delete) ImageView iv_delete;
		
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void setData(PersonaImg mPersonaImg){
			if(mPersonaImg!=null){
				iv_icon.setImageBitmap(mPersonaImg.smallBitmap);
				if(mPersonaImg.isSel==1){
					iv_delete.setVisibility(View.VISIBLE);
				}else if(mPersonaImg.isSel==0){
					iv_delete.setVisibility(View.GONE);
				}
			}
		}
	}
	

}
