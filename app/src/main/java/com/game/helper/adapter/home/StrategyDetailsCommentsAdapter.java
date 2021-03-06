package com.game.helper.adapter.home;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.community.PersonalHomepageActivity;
import com.game.helper.sdk.model.returns.GetContenteOtherDetail.Comment;
import com.game.helper.view.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Description 评论列表
 * @Path com.game.helper.adapter.home.StrategyDetailsCommentsAdapter.java
 * @Author lbb
 * @Date 2016年8月24日 下午8:21:17
 * @Company
 */
public class StrategyDetailsCommentsAdapter extends BaseAdapter {
    private Context mContext;
    protected LayoutInflater mInflater;
    protected List<Comment> commentList = new ArrayList<Comment>();

    public StrategyDetailsCommentsAdapter(Context mContext, List<Comment> commentList) {
        super();
        mInflater = LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.commentList = commentList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return commentList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return commentList.get(position);
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
            convertView = mInflater.inflate(R.layout.item_listview_home_strategy_detail_comments, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Comment mComment = (Comment) getItem(position);
        holder.setData(mComment);
        holder.imageView_pic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                bundle.putString("userId", mComment.opUserId);
                ((BaseActivity) mContext).startActivity(PersonalHomepageActivity.class, bundle);
            }
        });
        return convertView;
    }

    class ViewHolder {
        @BindView(R.id.imageView_pic)
        CircleImageView imageView_pic;
        @BindView(R.id.tv_item)
        TextView tv_item;
        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_msg)
        TextView tv_msg;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(Comment mCommentData) {
            if (mCommentData != null) {
                if (!TextUtils.isEmpty(mCommentData.opImage)) {
                    Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                            .load("" + mCommentData.fileAskPath + mCommentData.opImage)
                            .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                            //.centerCrop()// 长的一边撑满
                            //.placeholder(R.drawable.preview_card_pic_loading)//加载中显示的图片
                            .error(R.drawable.pic_moren)//加载失败时显示的图片
                            //.crossFade()
                            .into(imageView_pic);
                } else {
                    imageView_pic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.pic_moren));
                }
                tv_item.setText(mCommentData.opName);
                tv_time.setText(mCommentData.plTimeString);
                tv_msg.setText(mCommentData.plContent);
            }
        }
    }
}
