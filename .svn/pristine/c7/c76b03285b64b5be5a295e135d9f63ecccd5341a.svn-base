package com.game.helper.muldownload.download;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.game.helper.download.bean.AppContent;
import com.game.helper.muldownload.bean.DownloadInfo;
import com.game.helper.muldownload.utils.AppUtil;
import com.game.helper.muldownload.utils.FileUtil;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @Description
 * @Path com.game.helper.muldownload.download.DownBaseAdapter.java
 * @Author lbb
 * @Date 2016年10月25日 上午10:54:23
 * @Company 
 */
public  class DownBaseAdapter extends BaseAdapter implements DownloadManager.DownloadObserver {

	private List<AppContent> list=new ArrayList<AppContent>();
	private List<ViewHolder> mDisplayedHolders= new ArrayList<ViewHolder>();
	private Context context;

	public DownBaseAdapter(Context context,List<AppContent> list ) {
		this.context = context;
		this.list = list;
	}


	public void addItems(List<AppContent> infos) {
		list.clear();
		list.addAll(infos);
		notifyDataSetChanged();
	}

	public void startObserver() {
		DownloadManager.getInstance().registerObserver(this);
	}

	public void stopObserver() {
		DownloadManager.getInstance().unRegisterObserver(this);
	}
	public List<ViewHolder> getDisplayedHolders() {
		synchronized (mDisplayedHolders) {
			return new ArrayList<ViewHolder>(mDisplayedHolders);
		}
	}
	public void clearAllItem() {
		if (list != null){
			list.clear();
		}
		if (mDisplayedHolders != null) {
			mDisplayedHolders.clear();
		}
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

	/*@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final AppInfo appInfo = list.get(position);
		final ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder(context);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.setData(appInfo);
		mDisplayedHolders.add(holder);
		return holder.getRootView();
	}*/

	@Override
	public void onDownloadStateChanged(DownloadInfo info) {
		//refreshHolder(info);
	}

	@Override
	public void onDownloadProgressed(DownloadInfo info) {
		//refreshHolder(info);

	}

	

	/*private void refreshHolder(final DownloadInfo info) {
		List<ViewHolder> displayedHolders = getDisplayedHolders();
		for (int i = 0; i < displayedHolders.size(); i++) {
			final ViewHolder holder = displayedHolders.get(i);
			AppContent appInfo = holder.getData();
			if (appInfo.getId() == info.getId()) {
				AppUtil.post(new Runnable() {
					@Override
					public void run() {
						holder.refreshState(info.getDownloadState(),
								info.getProgress());
					}
				});
			}
		}

	}*/

	public class ViewHolder {/*
		public TextView textView01;
		public TextView textView02;
		public TextView textView03;
		public TextView textView04;
		public ImageView imageView_icon;
		public Button button;
		public LinearLayout linearLayout;

		public AppContent mData;
		private DownloadManager mDownloadManager;
		private int mState;
		private float mProgress;
		protected View mRootView;
		private Context context;
		private boolean hasAttached;

		public ViewHolder(Context context) {
			mRootView = initView();
			mRootView.setTag(this);
			this.context = context;


		}

		public View getRootView() {
			return mRootView;
		}

		public View initView() {
			View view = AppUtil.inflate(R.layout.item_recommend_award);

			imageView_icon = (ImageView) view
					.findViewById(R.id.imageview_task_app_cion);

			textView01 = (TextView) view
					.findViewById(R.id.textview_task_app_name);
			textView02 = (TextView) view
					.findViewById(R.id.textview_task_app_size);
			textView03 = (TextView) view
					.findViewById(R.id.textview_task_app_desc);
			textView04 = (TextView) view
					.findViewById(R.id.textview_task_app_love);
			button = (Button) view.findViewById(R.id.button_task_download);
			linearLayout = (LinearLayout) view
					.findViewById(R.id.linearlayout_task);

			button.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					System.out.println("mState:173    "+mState);
					if (mState == DownloadManager.STATE_NONE
							|| mState == DownloadManager.STATE_PAUSED
							|| mState == DownloadManager.STATE_ERROR) {

						mDownloadManager.download(mData);
					} else if (mState == DownloadManager.STATE_WAITING
							|| mState == DownloadManager.STATE_DOWNLOADING) {
						mDownloadManager.pause(mData);
					} else if (mState == DownloadManager.STATE_DOWNLOADED) {
						//				tell2Server();
						mDownloadManager.install(mData);
					} 
				}
			});
			return view;
		}


		public void setData(AppInfo data) {

			if (mDownloadManager == null) {
				mDownloadManager = DownloadManager.getInstance();

			}
			String filepath= FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + data.getName() + ".apk";

			boolean existsFile = FileUtil.isExistsFile(filepath);
			if(existsFile){
				int fileSize = FileUtil.getFileSize(filepath);

				if(data.getSize()==fileSize){
					DownloadInfo downloadInfo = DownloadInfo.clone(data);
					downloadInfo.setCurrentSize(data.getSize());
					downloadInfo.setHasFinished(true);
					mDownloadManager.setDownloadInfo(data.getId(),downloadInfo );
				}
				//			else if(fileSize>0){
				//				DownloadInfo downloadInfo = DownloadInfo.clone(data);
				//				downloadInfo.setCurrentSize(data.getSize());
				//				downloadInfo.setHasFinished(false);
				//				mDownloadManager.setDownloadInfo(data.getId(),downloadInfo );
				//			}

			}

			DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(data
					.getId());
			if (downloadInfo != null) {

				mState = downloadInfo.getDownloadState();
				mProgress = downloadInfo.getProgress();
			} else {

				mState = DownloadManager.STATE_NONE;
				mProgress = 0;
			}		
			this.mData = data;
			refreshView();
		}

		public AppContent getData() {
			return mData;
		}

		public void refreshView() {
			linearLayout.removeAllViews();
			AppContent info = getData();
			textView01.setText(info.getName());
			textView02.setText(FileUtil.FormetFileSize(info.getSize()));
			textView03.setText(info.getDes());
			textView04.setText("已有" + info.getDownloadNum() + "人喜欢");
			finalBitmap.display(imageView_icon, info.getIconUrl());


			if (info.getType().equals("0")) {
				//		mState = DownloadManager.STATE_READ;
				textView02.setVisibility(View.GONE);
			}else{
				String  path=FileUtil.getDownloadDir(AppUtil.getContext()) + File.separator + info.getName() + ".apk";
				hasAttached = FileUtil.isValidAttach(path, false);

				DownloadInfo downloadInfo = mDownloadManager.getDownloadInfo(info
						.getId());
				if (downloadInfo != null && hasAttached) {
					if(downloadInfo.isHasFinished()){

						mState = DownloadManager.STATE_DOWNLOADED;
					}else{
						mState = DownloadManager.STATE_PAUSED;

					}

				} else {
					mState = DownloadManager.STATE_NONE;
					if(downloadInfo !=null){
						downloadInfo.setDownloadState(mState);
					}
				}
			}

			refreshState(mState, mProgress);
		}

		public void refreshState(int state, float progress) {
			mState = state;
			mProgress = progress;
			switch (mState) {
			case DownloadManager.STATE_NONE:
				button.setText(R.string.app_state_download);
				break;
			case DownloadManager.STATE_PAUSED:
				button.setText(R.string.app_state_paused);
				break;
			case DownloadManager.STATE_ERROR:
				button.setText(R.string.app_state_error);
				break;
			case DownloadManager.STATE_WAITING:
				button.setText(R.string.app_state_waiting);
				break;
			case DownloadManager.STATE_DOWNLOADING:
				button.setText((int) (mProgress * 100) + "%");
				break;
			case DownloadManager.STATE_DOWNLOADED:
				button.setText(R.string.app_state_downloaded);
				break;
				//	case DownloadManager.STATE_READ:
				//		button.setText(R.string.app_state_read);
				//		break;
			default:
				break;
			}
		}
	*/}



	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
}
