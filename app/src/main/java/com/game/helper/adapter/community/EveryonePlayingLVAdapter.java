package com.game.helper.adapter.community;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.sdk.model.returns.GetGuildGameList.GuildGame;
import com.game.helper.view.widget.XCRoundImageViewByXfermode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description
 * @Path com.game.helper.adapter.community.EveryonePlayingLVAdapter.java
 * @Author lbb
 * @Date 2016年10月17日 下午5:25:51
 * @Company
 */
public class EveryonePlayingLVAdapter extends BaseAdapter {

    private List<GuildGame> mList = new ArrayList<GuildGame>();
    private Context mContext;
    protected LayoutInflater mInflater;

    public EveryonePlayingLVAdapter(Context mContext, List<GuildGame> mList) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mList = mList;
    }

    public List<GuildGame> getmList() {
        return mList;
    }

    public void setmList(List<GuildGame> mList) {
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
            convertView = mInflater.inflate(R.layout.item_listview_home_special_game, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final GuildGame mGuildGame = (GuildGame) getItem(position);
        holder.setData(mGuildGame);
        holder.tv_download.setVisibility(View.GONE);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_item)
        XCRoundImageViewByXfermode iv_item;
        @BindView(R.id.tv_dz)
        TextView tv_dz;
        @BindView(R.id.mLinear_dz)
        LinearLayout mLinear_dz;
        @BindView(R.id.tv_item)
        TextView tv_item;
        @BindView(R.id.tv_first)
        TextView tv_first;
        @BindView(R.id.tv_gift)
        TextView tv_gift;
        @BindView(R.id.tv_first3)
        TextView tvFirst3;
        @BindView(R.id.tv_type)
        TextView tv_type;
        @BindView(R.id.tv_datasize)
        TextView tv_datasize;
        @BindView(R.id.tv_msg)
        TextView tv_msg;
        @BindView(R.id.item_mLinearMsg)
        LinearLayout item_mLinearMsg;
        @BindView(R.id.pb_update_progress)
        ProgressBar pb_update_progress;
        @BindView(R.id.down_txt_pb)
        TextView downTxtPb;
        @BindView(R.id.down_progress)
        TextView downProgress;
        @BindView(R.id.mLinear_progress)
        LinearLayout mLinearProgress;
        @BindView(R.id.mLinearClick)
        LinearLayout mLinearClick;
        @BindView(R.id.tv_download)
        TextView tv_download;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(GuildGame mGuildGame) {
            if (mGuildGame != null) {
                iv_item.setType(XCRoundImageViewByXfermode.TYPE_ROUND);
                iv_item.setRoundBorderRadius(23);
                mLinear_dz.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(mGuildGame.tagName)) {
                    tv_gift.setText(mGuildGame.tagName);
                    tv_gift.setVisibility(View.VISIBLE);
                }
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + mGuildGame.fileAskPath + mGuildGame.logo)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                        .error(R.drawable.preview_card_pic_loading)//加载失败时显示的图片
                        //.crossFade()
                        .into(iv_item);
                tv_item.setText(mGuildGame.gameName);

                tv_type.setText(mGuildGame.platName);
                tv_datasize.setText("" + mGuildGame.fileSize + "M");
                tv_msg.setVisibility(View.INVISIBLE);

            }
        }

    }

}
