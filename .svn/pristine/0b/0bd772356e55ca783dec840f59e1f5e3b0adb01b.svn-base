package com.game.helper.adapter.community;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.download.bean.AppContent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Path com.game.helper.adapter.community.TAPlayingAdapter.java
 * @Author lbb
 * @Date 2016年10月18日 上午10:39:46
 * @Company
 */
public class TAPlayingAdapter extends BaseAdapter {

    private Context mContext;
    protected LayoutInflater mInflater;
    public List<AppContent> gameList = new ArrayList<AppContent>();

    public TAPlayingAdapter(Context mContext, List<AppContent> gameList) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.gameList = gameList;
    }

    public List<AppContent> getGameList() {
        return gameList;
    }


    public void setGameList(List<AppContent> gameList) {
        this.gameList = gameList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return gameList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_listview_community_everyoneplaying, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setData(gameList.get(position));
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_title)
        TextView tv_title;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(AppContent mAppContent) {
            if (mAppContent != null) {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + mAppContent.fileAskPath + mAppContent.logo)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                        //.crossFade()
                        .into(iv_icon);
                tv_title.setText(mAppContent.gameName);
            }
        }
    }
}
