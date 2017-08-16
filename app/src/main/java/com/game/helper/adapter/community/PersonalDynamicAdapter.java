package com.game.helper.adapter.community;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.DynamicDetailsActivity;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.db.manager.DBManager;
import com.game.helper.net.base.BaseBBXTask.Back;
import com.game.helper.net.task.DeleteDynamicTask;
import com.game.helper.net.task.DzDynamicTask;
import com.game.helper.sdk.model.returns.DzDynamic;
import com.game.helper.sdk.model.returns.LoginData;
import com.game.helper.sdk.model.returns.GetDynamic_1Info.ImageData;
import com.game.helper.sdk.model.returns.GetSelfDynamic.Dynamic;
import com.game.helper.util.ToastUtil;
import com.game.helper.view.dialog.MyAlertDailog;
import com.game.helper.view.dialog.MyAlertDailog.onClickLeftListener;
import com.game.helper.view.dialog.MyAlertDailog.onClickRightListener;
import com.game.helper.view.widget.CircleImageView;
import com.game.helper.view.widget.MyScrollviewGridView;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class PersonalDynamicAdapter extends BaseAdapter {
	private Map<Integer, ViewHolder> mCachedViews = new HashMap<Integer, ViewHolder>();
	private List<Dynamic> list=new ArrayList<Dynamic>();
	private Context mContext;
	protected LayoutInflater mInflater;
	LoginData user;
	public PersonalDynamicAdapter(Context mContext,List<Dynamic> list) {
		super();
		mInflater = LayoutInflater.from(mContext);
		this.mContext=mContext;
		this.list=list;
		 user=DBManager.getInstance(mContext).getUserMessage();
	}

	public List<Dynamic> getList() {
		return list;
	}

	public void setList(List<Dynamic> list) {
		user=DBManager.getInstance(mContext).getUserMessage();
		this.list = list;
		notifyDataSetChanged();
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_listview_community_personal_dynamic, null);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		else {
			holder = (ViewHolder) convertView.getTag();
		}
		if (holder instanceof ViewHolder) {
			holder.init();
            ((ViewHolder) holder).setData(list.get(position),position);
            if(!TextUtils.isEmpty(list.get(position).userId)&&user!=null&&!TextUtils.isEmpty(user.userId)&&user.userId.equals(list.get(position).userId)){
            	 holder.tv_cleans.setVisibility(View.VISIBLE);
            }else{
            	 holder.tv_cleans.setVisibility(View.GONE);
            }
            holder.tv_cleans.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				// TODO Auto-generated method stub
    				final MyAlertDailog dialog = new MyAlertDailog(
    						mContext);
    				Resources res = mContext.getResources();
    				dialog.setTitle("提醒",Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
    				dialog.setContent1("是否删除发布的动态？"
    						,Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL);
    				dialog.setLeftButtonText("取消");
    				dialog.setRightButtonText("确定");
    				if (dialog != null && !dialog.isShowing()) {
    					dialog.show();
    				}
    				dialog.setOnClickLeftListener(new onClickLeftListener() {
    					@Override
    					public void onClickLeft() {
    						dialog.dismiss();

    					}
    				});
    				dialog.setOnClickRightListener(new onClickRightListener() {

    					@Override
    					public void onClickRight() {
    						dialog.dismiss();

    						if(!TextUtils.isEmpty(list.get(position).userId)&&user!=null&&!TextUtils.isEmpty(user.userId)&&user.userId.equals(list.get(position).userId)){
    							 new DeleteDynamicTask(mContext, list.get(position).dynamicId, new Back() {

    		    						@Override
    		    						public void success(Object object, String msg) {
    		    							ToastUtil.showToast(mContext, "动态删除成功");
    		    							 list.remove(position);
    		    							 notifyDataSetChanged();
    		    						}

    		    						@Override
    		    						public void fail(String status, String msg, Object object) {
    		    							ToastUtil.showToast(mContext, msg);

    		    						}
    		    					}).start();
    						}else{
    							ToastUtil.showToast(mContext, "非本人发布的动态，无权删除");
    						}

    					}
    				});
    			}
    		});
    		/*holder.tv_comment.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				if(mSquareDynamicListener!=null){
    					mSquareDynamicListener.setVisibility(View.VISIBLE);
    				}
    			}
    		});*/
    		holder.iv_icon.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View arg0) {
    				//
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
            cacheView(position, (ViewHolder) holder);
        }
		return convertView;
	}
	private void cacheView(int position, ViewHolder view) {
        Iterator<Map.Entry<Integer, ViewHolder>> entries = mCachedViews.entrySet().iterator();

        while (entries.hasNext()) {

            Map.Entry<Integer, ViewHolder> entry = entries.next();
            if (entry.getValue() == view && entry.getKey() != position) {
                mCachedViews.remove(entry.getKey());
                break;
            }
        }

        mCachedViews.put(position, view);

    }
    class ViewHolder {
        public @BindView(R.id.iv_icon)
        CircleImageView iv_icon;
        public @BindView(R.id.tv_name)
        TextView tv_name;
        public @BindView(R.id.tv_cleans)
        ImageView tv_cleans;
        public @BindView(R.id.tv_msg)
        TextView tv_msg;
        public @BindView(R.id.tv_open_unfold)
        ImageView tv_open_unfold;
        public @BindView(R.id.mLinearOpen_unfold)
        LinearLayout mLinearOpen_unfold;
        public @BindView(R.id.dyImg_gridView)
        MyScrollviewGridView dyImg_gridView;
        public @BindView(R.id.mLinearImg)
        LinearLayout mLinearImg;
        public @BindView(R.id.tv_address)
        TextView tv_address;
        public @BindView(R.id.tv_time)
        TextView tv_time;
        public @BindView(R.id.iv_zan)
        ImageView iv_zan;
        public @BindView(R.id.tv_zan)
        TextView tv_zan;
        public @BindView(R.id.tv_comment)
        TextView tv_comment;
        public @BindView(R.id.comment_layout)
        LinearLayout comment_layout;
        public @BindView(R.id.mLinearItem)
        LinearLayout mLinearItem;

        DyImgAdapter mDyImgAdapter;
        List<ImageData> imageList = new ArrayList<ImageData>();


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

		public void setData(Dynamic mDynamic,final int position){
			if(mDynamic!=null){
				tv_msg.setFilters(new InputFilter[] { new InputFilter.LengthFilter(52) });

				if(!TextUtils.isEmpty(mDynamic.field1)){

					Glide.with(BaseApplication.mInstance.context.getApplicationContext())
					.load(""+mDynamic.fileAskPath+mDynamic.field1)
					.diskCacheStrategy(DiskCacheStrategy.SOURCE)
					//.centerCrop()// 长的一边撑满
					//.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
					.error(R.drawable.pic_moren)//加载失败时显示的图片
					//.crossFade()
					.into(iv_icon);
				}else{
					iv_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
				}
				tv_name.setText(mDynamic.userName);

				if(!TextUtils.isEmpty(mDynamic.content)&&mDynamic.content.length()>52){
					if(mDynamic.isOPen==0){
						tv_msg.setFilters(new InputFilter[] { new InputFilter.LengthFilter(52) });
						mLinearOpen_unfold.setVisibility(View.VISIBLE);
					}else{
						tv_msg.setFilters(new InputFilter[] { new InputFilter.LengthFilter(500) });
						mLinearOpen_unfold.setVisibility(View.GONE);
					}
				}else{
					tv_msg.setFilters(new InputFilter[] { new InputFilter.LengthFilter(52) });
					mLinearOpen_unfold.setVisibility(View.GONE);
				}

				tv_msg.setText(mDynamic.content);

				tv_address.setText(mDynamic.field2);
				tv_time.setText(mDynamic.publishDateString);
				tv_zan.setText("赞("+mDynamic.dzNum+")");
				tv_comment.setText("评论("+mDynamic.plNum+")");
				//
				if(mDynamic.imageList!=null&&mDynamic.imageList.size()>0){
					mDyImgAdapter.setData(mDynamic.imageList);
					mLinearImg.setVisibility(View.VISIBLE);
				}else{
					mLinearImg.setVisibility(View.GONE);
				}
				//500
				tv_open_unfold.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						list.get(position).content=list.get(position).content+" ";
						list.get(position).isOPen=1;
						notifyDataSetChanged();
					}
				});
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


	
	/*
    public Comment(String comment) {
        this.comment = Html.fromHtml("<font color='#4A766E'>zhaizu: </font>" + comment);
    }
    */
}
