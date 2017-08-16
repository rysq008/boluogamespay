package com.game.helper.view.widget;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import butterknife.BindView;
import com.game.helper.R;
import com.game.helper.interfaces.comm.CommValues;
import com.yuan.leopardkit.download.DownLoadManager;
import com.yuan.leopardkit.download.model.DownloadInfo;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * @Description
 * @Path 
 * @Author lbb
 * @Date 2016年2月19日 上午10:40:33
 * @Company 
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyLinearLayout extends LinearLayout implements CommValues {
	ProgressBar pb_update_progress;
	TextView prgressTv;
	TextView progressShow;
	private Timer timer = new Timer();  
	private Context context;
	public static   boolean isTask=true;//外部锁

	int progress=0; 
	int total=100; 
	boolean done;
	DownloadInfo infos;
	private Handler handler = new Handler() {  
		@Override  
		public void handleMessage(Message msg) {  
			// TODO Auto-generated method stub  
			switch (msg.what) {
			case 1:
				if(total!=0){
					Log.e("lbb", "-----DWONLOAD--1--progress-----"+progress);
					Log.e("lbb", "-----DWONLOAD--1--total-----"+total);
					Log.e("lbb", "-----DWONLOAD--1--done-----"+done);
					pb_update_progress.setProgress((int) progress);
					pb_update_progress.setMax((int) total);
					progressShow.setText((int) ((float) progress / total * 100) + "%");
					double curP = 0;
					double curTotal = 0;
					if (progress / 1024L < 1024L) {
						curP = (double) progress / 1024;
						curTotal = (double) total / 1024L / 1024L;
						prgressTv.setText(Double.toString(getRealNum(curP)) + "KB/" + Double.toString(getRealNum(curTotal)) + "MB"
								);
					} else {
						curP = (double) progress / 1024L / 1024L;
						curTotal = (double) total / 1024L / 1024L;
						prgressTv.setText(Double.toString(getRealNum(curP)) + "MB/" + Double.toString(getRealNum(curTotal)) + "MB"
								);
					}
					if (done&&((int) ((float) progress / total * 100))==100) {
						//tv_download.setText("安装");
						infos.getDownLoadTask().setState(DownLoadManager.STATE_FINISH);
						prgressTv.setText(prgressTv.getText() + "");
					}
				}

				break;
			default:
				break;
			}
			super.handleMessage(msg);  
		}  
	};  
	private TimerTask task = new TimerTask() {  
		@Override  
		public void run() {  
			if(isTask){
				new GetOrderTask().start();
			}
		}  
	};  

	@SuppressLint("NewApi")
	public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		taskId = new Random().nextLong();
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_layout,this);
		pb_update_progress=(ProgressBar) findViewById(R.id.pb_update_progress);
		prgressTv=(TextView) findViewById(R.id.down_txt_pb);
		progressShow=(TextView) findViewById(R.id.down_progress);

		isTask=true;//外部锁
		handler.postDelayed(task, nextRefreshDelay);
		Log.e("lbb","------------MyLinearLayout-----------");
	}
	public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.context=context;
		taskId = new Random().nextLong();
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_layout,this);
		pb_update_progress=(ProgressBar) findViewById(R.id.pb_update_progress);
		prgressTv=(TextView) findViewById(R.id.down_txt_pb);
		progressShow=(TextView) findViewById(R.id.down_progress);

		isTask=true;//外部锁
		handler.postDelayed(task, nextRefreshDelay);
		Log.e("lbb","------------MyLinearLayout-----------");
	}
	public MyLinearLayout(Context context) {
		super(context);
		this.context=context;
		taskId = new Random().nextLong();
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_layout,this);
		pb_update_progress=(ProgressBar) findViewById(R.id.pb_update_progress);
		prgressTv=(TextView) findViewById(R.id.down_txt_pb);
		progressShow=(TextView) findViewById(R.id.down_progress);

		isTask=true;//外部锁
		handler.postDelayed(task, nextRefreshDelay);
		Log.e("lbb","------------MyLinearLayout-----------");
	}
	public MyLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		taskId = new Random().nextLong();
		this.context=context;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.my_layout,this);
		pb_update_progress=(ProgressBar) findViewById(R.id.pb_update_progress);
		prgressTv=(TextView) findViewById(R.id.down_txt_pb);
		progressShow=(TextView) findViewById(R.id.down_progress);

		isTask=true;//外部锁
		handler.postDelayed(task, nextRefreshDelay);
		Log.e("lbb","------------MyLinearLayout-----------");
	}
	public static final Object  objLock = new Object();
	private  long onlyTaskid=-1;//内部锁
	private long taskId=1;
	private long nextRefreshDelay = 5*100;
	public class GetOrderTask extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			super.run();
			try {
				if(onlyTaskid == taskId){
					Message message = new Message();  
					message.what = 1;  
					handler.sendMessage(message);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			handler.postDelayed(task, nextRefreshDelay);
		}
	}
	public void setDatas(long progress,  long total,  boolean done,DownloadInfo infos){
		MyLinearLayout.this.progress=(int) progress;
		MyLinearLayout.this.total=(int) total;
		MyLinearLayout.this.done=done;
		MyLinearLayout.this.infos=infos;
		Log.e("lbb", "-----DWONLOAD--02--progress-----"+MyLinearLayout.this.progress);
		Log.e("lbb", "-----DWONLOAD--02--total-----"+MyLinearLayout.this.total);
		Log.e("lbb", "-----DWONLOAD--02--done-----"+MyLinearLayout.this.done);
	}
	public void start(){

		synchronized (objLock) {
			nextRefreshDelay = 5* 100;
			onlyTaskid = taskId;
			if(!isTask){
				handler.postDelayed(task, 1* 100);
			}
		}

	}
	public void stop(){
		synchronized (objLock) {
			onlyTaskid=-1;
		}

	}
	//初始化数据
	public void setStartDatas(long progress,  long total,  boolean done,DownloadInfo infos){
		try {
			this.progress=(int) progress;
			this.total=(int) total;
			this.done=done;
			this.infos=infos;

			pb_update_progress.setMax((int) total);
			pb_update_progress.setProgress((int) progress);
			progressShow.setText((int) ((float) progress / total * 100) + "%");
			if (infos.getState() == DownLoadManager.STATE_FINISH) {
				prgressTv.setText(Double.toString(getRealNum(progress/1024/1024)) + "MB/"
						+ Double.toString(getRealNum( total/1024/1024)) + "MB"+"");
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}

	public void setStartDatas(){
		try {
			pb_update_progress.setMax(0);
			pb_update_progress.setProgress(100);
			prgressTv.setText("");
			progressShow.setText("0%");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

	}
	private double getRealNum(double num) {
		BigDecimal bg = new BigDecimal(num);
		return bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}
