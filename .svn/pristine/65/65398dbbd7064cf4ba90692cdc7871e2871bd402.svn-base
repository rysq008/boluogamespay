package com.game.helper.adapter.community;

import java.util.ArrayList;
import java.util.List;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.DynamicDetailsActivity;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DzDynamicTask;
import com.game.helper.sdk.model.returns.DzDynamic;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.Dynamic_1Info;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.ImageData;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.MyScrollviewGridView;

import android.content.Context;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description 公会圈
 * @Path com.game.helper.adapter.community.SociatyCircleAdapter.java
 * @Author lbb
 * @Date 2016年8月25日 下午2:18:03
 * @Company 
 */
public class SociatyCircleAdapter extends BaseAdapter {

	private Context mContext;
	protected LayoutInflater mInflater;
	private List<Dynamic_1Info> list=new ArrayList<Dynamic_1Info>();
	public SociatyCircleAdapter(Context mContext,List<Dynamic_1Info> list) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.list=list;
	}

	public List<Dynamic_1Info> getList() {
		return list;
	}

	public void setList(List<Dynamic_1Info> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();//mList.size()
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_community_sociaty_circle, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.init();
		holder.setData(list.get(position),position);
		holder.tv_cleans.setVisibility(View.GONE);
		holder.iv_icon.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Bundle bundle=new Bundle();
				bundle.putString("userId", list.get(position).userId);
				((BaseActivity)mContext).startActivity(PersonalHomepageActivity.class,bundle);
			}
		});
		holder.mLinearItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Bundle bundle=new Bundle();
				bundle.putString("dynamicId", list.get(position).dynamicId);
				((BaseActivity)mContext).startActivity(DynamicDetailsActivity.class,bundle);
			}
		});
		holder.dyImg_gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Bundle bundle=new Bundle();
				bundle.putString("dynamicId", list.get(position).dynamicId);
				((BaseActivity)mContext).startActivity(DynamicDetailsActivity.class,bundle);
				
			}
		});
		return convertView;
	}

	class ViewHolder {
		
		public @BindView(R.id.mLinearItem) LinearLayout mLinearItem;
		
		public @BindView(R.id.iv_icon) CircleImageView iv_icon;
		public @BindView(R.id.tv_name) TextView tv_name;
		public @BindView(R.id.tv_cleans) ImageView tv_cleans;//删除
		
		public @BindView(R.id.iv_zan) ImageView iv_zan;
		
		public @BindView(R.id.tv_msg) TextView tv_msg;

		public @BindView(R.id.mLinearImg) LinearLayout mLinearImg;
		public @BindView(R.id.dyImg_gridView) MyScrollviewGridView dyImg_gridView;
		DyImgAdapter mDyImgAdapter;
		List<ImageData> imageList=new ArrayList<ImageData>();
		
		public @BindView(R.id.tv_address) TextView tv_address;
		public @BindView(R.id.tv_time) TextView tv_time;
		public @BindView(R.id.tv_zan) TextView tv_zan;
		public @BindView(R.id.tv_comment) TextView tv_comment;

		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
		public void init(){
			 imageList=new ArrayList<ImageData>();
			mDyImgAdapter=new DyImgAdapter(mContext, imageList);
			dyImg_gridView.setAdapter(mDyImgAdapter);
			dyImg_gridView.setClickable(false);
			dyImg_gridView.setPressed(false);
             dyImg_gridView.setEnabled(false);
		}
		public void setData(Dynamic_1Info mDynamic_1Info,final int position){
			if(mDynamic_1Info!=null){
				tv_msg.setFilters(new InputFilter[] { new InputFilter.LengthFilter(500) });
				
				if(!TextUtils.isEmpty(mDynamic_1Info.field1)){
					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mDynamic_1Info.fileAskPath+mDynamic_1Info.field1)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片  
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(iv_icon);
				}else{
					iv_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				tv_name.setText(mDynamic_1Info.userName);
				tv_msg.setText(mDynamic_1Info.content);
				
				tv_address.setText(mDynamic_1Info.field2);
				tv_time.setText(mDynamic_1Info.publishDateString);
				tv_zan.setText("赞("+mDynamic_1Info.dzNum+")");
				tv_comment.setText("评论("+mDynamic_1Info.plNum+")");
				//
				if(mDynamic_1Info.imageList!=null&&mDynamic_1Info.imageList.size()>0){
					mDyImgAdapter.setData(mDynamic_1Info.imageList);
					mLinearImg.setVisibility(View.VISIBLE);
				}else{
					mLinearImg.setVisibility(View.GONE);
				}
				iv_zan.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						LoginData user=DBManager.getInstance(mContext).getUserMessage();
						new DzDynamicTask(mContext, user.userId,  list.get(position).dynamicId, new Back() {

							@Override
							public void success(Object object, String msg) {
								ToastUtil.showToast(mContext, msg);
								//刷新点赞数
		                       if(object!=null && object instanceof DzDynamic){
		                    	   DzDynamic mDzDynamic=(DzDynamic) object;
		                    	   if(mDzDynamic.data!=null){
		                    		   list.get(position).dzNum=mDzDynamic.data.dzNum;
		                    		   notifyDataSetChanged();
		                    	   }
		                       }
							}

							@Override
							public void fail(String status, String msg, Object object) {
								ToastUtil.showToast(mContext, msg);

							}
						}).start();
					}
				});
			}
		}
	}
	

}