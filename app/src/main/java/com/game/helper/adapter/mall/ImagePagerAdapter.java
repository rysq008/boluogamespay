/*
 * Copyright 2014 trinea.cn All right reserved. This software is the confidential and proprietary information of
 * trinea.cn ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with trinea.cn.
 */
package com.game.helper.adapter.mall;

import java.util.EmptyStackException;
import java.util.List;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.game.helper.BaseActivity;
import com.game.helper.BaseApplication;
import com.game.helper.R;
import com.game.helper.activity.WebActivity;
import com.game.helper.activity.home.GameDetailActivity;
import com.game.helper.activity.home.InviteFriendsActivity;
import com.game.helper.download.bean.AppContent;
import com.game.helper.sdk.model.returns.GetAdList.AdData;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import butterknife.ButterKnife;
import butterknife.BindView;
import cn.trinea.android.common.util.ListUtils;

/**
 * ImagePagerAdapter
 *
 * @author
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    private Context context;

    public interface MsetV {
        void ref();
    }

    private MsetV liser;
    private List<AppContent> imageIdList;
    private boolean isInfiniteLoop;
    private int type;

    public void setLiser(MsetV liser) {
        this.liser = liser;
    }

    public ImagePagerAdapter(Context context, List<AppContent> imageIdList, int type) {
        this.context = context;
        this.imageIdList = imageIdList;
        isInfiniteLoop = false;
        this.type = type;
    }

    public List<AppContent> getImageIdList() {
        return imageIdList;
    }

    public void setImageIdList(List<AppContent> imageIdList) {
        this.imageIdList = imageIdList;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (ListUtils.getSize(imageIdList) <= 0) {
            return ListUtils.getSize(imageIdList);
        }
        return isInfiniteLoop ? Integer.MAX_VALUE : ListUtils.getSize(imageIdList);
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        if (position == 0) {
            return position;
        }
        return isInfiniteLoop ? position % ListUtils.getSize(imageIdList) : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_home_image_pager, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        // holder.setTag(R.id.glide_homeviewpage_image_tag, "http://i.imgur.com/idojSYm.png");

        if (position > 2) {
            position = position % 3;
        }


        holder.setData(position);
        final int finalPosition = position;
        holder.img.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Bundle bundle = new Bundle();
                if (type == 1) {
					try {
                        bundle.putString("Url", imageIdList.get(finalPosition).field1);
                        bundle.putString("TITLE", imageIdList.get(finalPosition).imgRemark);
                        if (!TextUtils.isEmpty(imageIdList.get(finalPosition).isRedirct) && imageIdList.get(finalPosition).isRedirct.equals("1")) {
                            ((BaseActivity) context).startActivity(InviteFriendsActivity.class);//调到原生页面分享
                        } else {
                            ((BaseActivity) context).startActivity(WebActivity.class, bundle);
                        }
                    }catch (EmptyStackException e){

                    }
                } else if (type == 2) {
					/*bundle.putString("Url",imageIdList.get(getPosition(position)).field1);
					bundle.putString("TITLE", "社区");
					((BaseActivity)context).startActivity(WebActivity.class,bundle);*/
                    bundle.putString("gameId", imageIdList.get(getPosition(finalPosition)).gameId);
                    bundle.putParcelable("appcontent", imageIdList.get(getPosition(finalPosition)));
                    ((BaseActivity) context).startActivity(GameDetailActivity.class, bundle);
                } else if (type == 3) {
					/*bundle.putString("Url",imageIdList.get(getPosition(position)).field1);
					bundle.putString("TITLE", "商城");
					((BaseActivity)context).startActivity(WebActivity.class,bundle);*/
                    bundle.putString("gameId", imageIdList.get(getPosition(finalPosition)).gameId);
                    if (null != imageIdList.get(getPosition(finalPosition)).detailInfo) {
                        bundle.putParcelable("appcontent", imageIdList.get(getPosition(finalPosition)).detailInfo);
                        ((BaseActivity) context).startActivity(GameDetailActivity.class, bundle);
                    }
                } else if (type == 4) {//游戏礼包
					/*bundle.putString("TITLE", "游戏礼包");
					bundle.putString("Url",imageIdList.get(getPosition(position)).field1);
					((BaseActivity)context).startActivity(WebActivity.class,bundle);*/

                    bundle.putString("gameId", imageIdList.get(getPosition(finalPosition)).gameId);
                    if (null != imageIdList.get(getPosition(finalPosition)).detailInfo) {
                        bundle.putParcelable("appcontent", imageIdList.get(getPosition(finalPosition)).detailInfo);
                        ((BaseActivity) context).startActivity(GameDetailActivity.class, bundle);
                    }
                }
            }
        });
        return convertView;
    }

    class ViewHolder {
        public
        @BindView(R.id.img)
        ImageView img;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }

        public void setData(int position) {
            if (type == 1) {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load(""+imageIdList.get(getPosition(position)).baseAcessPath + imageIdList.get(getPosition(position)).adImg)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        .placeholder(R.drawable.skin_loading_icon)//加载中显示的图片
                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                        //.crossFade()
                        .into(img);
            } else {
                Glide.with(BaseApplication.mInstance.context.getApplicationContext())
                        .load("" + imageIdList.get(getPosition(position)).baseAcessPath + imageIdList.get(getPosition(position)).adImg)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        //.centerCrop()// 长的一边撑满
                        .placeholder(R.drawable.skin_loading_icon)//加载中显示的图片
                        .error(R.drawable.picture_defeated)//加载失败时显示的图片
                        //.crossFade()
                        .into(img);
            }
			/*if(liser!=null){
				liser.ref();
			}*/
        }

    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }
}
