package com.game.helper.adapter.mine;

import java.util.ArrayList;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.ChoosePicActivity;
import com.game.helper.activity.mine.MineDataEditingActivity;
import com.game.helper.model.mine.PersonaImg;
import com.game.helper.view.pdgrid.PagedDragDropGrid;
import com.game.helper.view.pdgrid.PagedDragDropGridAdapter;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class DragDropGridAdapter implements  PagedDragDropGridAdapter{
	public interface DeleteListener{
		void delete(int pos);
		void showpop();
	}
	DeleteListener mDeleteListener;
	PagedDragDropGrid gridview;
	ArrayList<PersonaImg> list=new ArrayList<PersonaImg>();
	ArrayList<PersonaImg> listTrue=new ArrayList<PersonaImg>();
	Activity activity;
	private LayoutInflater mInflater;
	int rowCount,columnCount;
	int maxSize;
	public DragDropGridAdapter(Activity ac,PagedDragDropGrid gv,ArrayList<PersonaImg> l,int rowCount,int columnCount,int maxSize)
	{
		activity=ac;
		gridview=gv;
		list=l;
		this.rowCount=rowCount;
		this.columnCount=columnCount;
		this.maxSize=maxSize;
		mInflater = LayoutInflater.from(activity);
	}

	public void setmDeleteListener(DeleteListener mDeleteListener) {
		this.mDeleteListener = mDeleteListener;
	}

	public List<PersonaImg> getList() {
		return list;
	}

	public void setList(ArrayList<PersonaImg> list,ArrayList<PersonaImg> list1) {
		this.list = list;
		listTrue=list1;
		gridview.notifyDataSetChanged();
	}

	@Override
	public int pageCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int itemCountInPage(int page) {
		// TODO Auto-generated method stub
		return list.size();
	}

	@SuppressWarnings("deprecation")
	@Override
	public View view(int page, int index) {
		// TODO Auto-generated method stub
        Log.e("lbb", "----view---page----"+page);
		View convertView = mInflater.inflate(R.layout.item_listview_mine_person_img, null);
		ImageView iv_icon=(ImageView)convertView.findViewById(R.id.iv_icon);
		ImageView iv_delete=(ImageView)convertView.findViewById(R.id.iv_delete);
		RelativeLayout iRelativeLayout=(RelativeLayout)convertView.findViewById(R.id.iRelativeLayout);
		convertView.setTag(index);
		iv_delete.setTag(index);
		if(list.size()>1&&index==0 )
		{//第一个为头像
			//tvTitle.setTextColor(activity.getResources().getColor(R.color.black));
		}
		if(list!=null&&list.size()>0){
			if(index==list.size()-1){//最后一个为“加”标图
				iv_delete.setVisibility(View.GONE);
				if(maxSize+1>list.size()){
					convertView.setOnClickListener(new OnClickListener() {
						
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
						/*	((BaseActivity)activity).startActivityForResult(ChoosePicActivity.class, null,
									MineDataEditingActivity.TO_SELECT_PHOTO);*/
							if(mDeleteListener !=null){
								mDeleteListener.showpop();
							}
						}
					});
				}
				iRelativeLayout.setBackgroundDrawable(activity.getResources().getDrawable(R.drawable.shape_photo));
				if(maxSize==5){
					iv_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.shouye_297));
				}else 	if(maxSize==8){
					iv_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pic_08));
					convertView.setOnLongClickListener(onLongClickListener);
				}else 	if(maxSize==9){
					iv_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.pic_09));
				}
			}else{
				iRelativeLayout.setBackgroundColor(activity.getResources().getColor(R.color.white));
				if(list.get(index).smallBitmap!=null){
					
					iv_icon.setImageBitmap(list.get(index).smallBitmap);
				}else{
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+list.get(index).fileAskPath+list.get(index).icon)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
					//.crossFade()
					.into(iv_icon);
				}
				iv_delete.setVisibility(View.VISIBLE);
				iv_delete.setOnClickListener(onClickListener);
				convertView.setOnLongClickListener(onLongClickListener);
			}
		}

		return convertView;
	}

	private OnClickListener onClickListener=new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			int index=Integer.parseInt(v.getTag().toString());
			deleteItem(0,index);
			if(mDeleteListener !=null){
				mDeleteListener.delete(index);
			}
		}
	};

	private OnLongClickListener onLongClickListener=new OnLongClickListener() {

		@Override
		public boolean onLongClick(View v) {
			// TODO Auto-generated method stub
			return gridview.onLongClick(v);
		}
	};

	/*	private void showDelColumnAnim(View v)
	{
		int index=Integer.parseInt(v.getTag().toString());
		if(index==0||index==1)return ;

		adapter.addItems(list.get(index));
		this.deleteItem(0,index);

	}*/


	@Override
	public int rowCount() {
		// TODO Auto-generated method stub
		return rowCount;//2
	}

	@Override
	public int columnCount() {
		// TODO Auto-generated method stub
		return columnCount;//4
	}

	@Override
	public void printLayout() {
		// TODO Auto-generated method stub

	}

	@Override
	public void swapItems(int pageIndex, int itemIndexA, int itemIndexB) {
		// TODO Auto-generated method stub
       /* for(PersonaImg m:list){
        	Log.e("lbb", "---swapItems---"+m.id);
        }*/
       
		//B起始位置 
		//A目标位置
		//Toast.makeText(activity, "select" + itemIndexA+" ---select "+itemIndexB, Toast.LENGTH_SHORT).show();  
	}

	@Override
	public void moveItemToPreviousPage(int pageIndex, int itemIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void moveItemToNextPage(int pageIndex, int itemIndex) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteItem(int pageIndex, int itemIndex) {
		// TODO Auto-generated method stub
		list.remove(itemIndex);
		gridview.notifyDataSetChanged();
	}

	@Override
	public int deleteDropZoneLocation() {
		// TODO Auto-generated method stub
		return BOTTOM;
	}

	@Override
	public boolean showRemoveDropZone() {
		// TODO Auto-generated method stub
		return false;
	}


}
