package com.game.helper.adapter.community;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetFocusList.GetFocusListData;
import com.game.helper.view.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Path com.game.helper.adapter.community.FollowPeopleAdapter.java
 * @Author lbb
 * @Date 2016年8月26日 上午11:40:01
 * @Company
 */
public class FollowPeopleAdapter extends BaseAdapter {
    private Context mContext;
    protected LayoutInflater mInflater;
    List<GetFocusListData> data = new ArrayList<GetFocusListData>();
    int tag;

    public FollowPeopleAdapter(Context mContext, List<GetFocusListData> data, int tag) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.data = data;
        this.tag = tag;
    }

    public List<GetFocusListData> getData() {
        return data;
    }

    public void setData(List<GetFocusListData> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
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
            convertView = mInflater.inflate(R.layout.item_listview_community_follow_people, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GetFocusListData mGetFocusListData = (GetFocusListData) getItem(position);
        holder.setData(mGetFocusListData);

        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_icon)
        CircleImageView iv_icon;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.mView_last1)
        View mView_last1;
        @BindView(R.id.mView_last2)
        View mView_last2;
        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(GetFocusListData mGetFocusListData) {
            if (mGetFocusListData != null) {
                if (tag == 0) {
                    tv_name.setText(mGetFocusListData.nickName);
                    if (!TextUtils.isEmpty(mGetFocusListData.userIcon)) {
                        Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                .load("" + mGetFocusListData.fileAskPath + mGetFocusListData.userIcon)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                //.centerCrop()// 长的一边撑满
                                //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                .error(R.drawable.pic_moren)//加载失败时显示的图片
                                //.crossFade()
                                .into(iv_icon);
                    } else {
                        iv_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
                    }
                } else {
                    tv_name.setText(mGetFocusListData.operName);
                    if (!TextUtils.isEmpty(mGetFocusListData.operIcon)) {
                        Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                                .load("" + mGetFocusListData.fileAskPath + mGetFocusListData.operIcon)
                                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                                //.centerCrop()// 长的一边撑满
                                //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                                .error(R.drawable.pic_moren)//加载失败时显示的图片
                                //.crossFade()
                                .into(iv_icon);
                    } else {
                        iv_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
                    }
                }


            }
        }
    }
}
