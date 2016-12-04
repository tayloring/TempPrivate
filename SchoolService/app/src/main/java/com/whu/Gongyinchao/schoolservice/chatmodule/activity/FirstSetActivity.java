package com.whu.Gongyinchao.schoolservice.chatmodule.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.Gson;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.adapter.HeadAdapter;
import com.whu.Gongyinchao.schoolservice.chatmodule.adapter.SexAdapter;
import com.whu.Gongyinchao.schoolservice.chatmodule.baidupush.client.MyPushMessageReceiver;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.User;
import com.whu.Gongyinchao.schoolservice.chatmodule.database.UserDB;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.DialogUtil;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.NetUtil;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SendMsgAsyncTask;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SharePreferenceUtil;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.T;
import com.whu.Gongyinchao.schoolservice.chatmodule.wheel.WheelView;
import com.whu.Gongyinchao.schoolservice.common.app.SchoolServiceApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.gujun.android.taggroup.TagGroup;

public class FirstSetActivity extends Activity implements OnClickListener,MyPushMessageReceiver.EventHandler{
	private static final int LOGIN_OUT_TIME = 0;
	private Button mFirstStartBtn;
	private WheelView mHeadWheel;
	private WheelView mSexWheel;
	private EditText mNickEt;
	private SchoolServiceApplication mApplication;
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private UserDB mUserDB;
	private View mNetErrorView;
	private TextView mTitle;
	public LoginOutTimeProcess mLoginOutTimeProcess;
	private Button local,takephoto;

	/* 头像文件 */
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

	/* 请求识别码 */
	private static final int CODE_GALLERY_REQUEST = 0xa0;//本地
	private static final int CODE_CAMERA_REQUEST = 0xa1;//拍照
	private static final int CODE_RESULT_REQUEST = 0xa2;//最终裁剪后的结果

	// 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
	private static int output_X = 600;
	private static int output_Y = 600;

	public ImageView headImage = null;


	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case LOGIN_OUT_TIME:
				if (mLoginOutTimeProcess != null
						&& mLoginOutTimeProcess.running)

					mLoginOutTimeProcess.stop();

				if (mConnectServerDialog != null
						&& mConnectServerDialog.isShowing())


					mConnectServerDialog.dismiss();

				if (task != null)

					task.stop();
				T.showShort(FirstSetActivity.this, "登录超时，请重试");
				break;

			default:
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_set_layout);

		//m.SetTags("数据挖掘","");

		final TagGroup mTagGroup1 = (TagGroup) findViewById(R.id.tag);
		String[] tags={"大数据", "网络", "信息安全","数据挖掘","云计算"};
		mTagGroup1.setTags(tags);
		final TagGroup mTagGroup2=(TagGroup)findViewById(R.id.tag2);
		mTagGroup2.setTags(new String[]{"金融","经济","营销","创业","管理"});

		mTagGroup1.submitTag();
		mTagGroup2.submitTag();
		int re =mTagGroup1.getChildCount();
		String[] rt =mTagGroup1.getTags();
	  T.showShort(this,"字数"+re+rt[1]);
		SharedPreferences sp=getSharedPreferences("tags",0);//存储文件名
		SharedPreferences.Editor se=sp.edit();
		se.putString("tags", rt[1]);
		se.commit();
		//startService(new Intent(FirstSetActivity.this, UploadPersonalityData.class));
        headImage=(ImageView)findViewById(R.id.headicon);
		local=(Button)findViewById(R.id.ipohoto);
		takephoto=(Button)findViewById(R.id.takephoto);
		local.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				choseHeadImageFromGallery();
			}
		});
         takephoto.setOnClickListener(new OnClickListener() {
			 @Override
			 public void onClick(View v) {
				 choseHeadImageFromCameraCapture();
			 }
		 });




		initData();
		initView();
	}



	// 从本地相册选取图片作为头像
	private void choseHeadImageFromGallery() {
		Intent intentFromGallery = new Intent();
		// 设置文件类型
		intentFromGallery.setType("image/*");//选择图片
		intentFromGallery.setAction(Intent.ACTION_GET_CONTENT);
		//如果你想在Activity中得到新打开Activity关闭后返回的数据，
		//你需要使用系统提供的startActivityForResult(Intent intent,int requestCode)方法打开新的Activity
		startActivityForResult(intentFromGallery, CODE_GALLERY_REQUEST);
	}

	// 启动手机相机拍摄照片作为头像
	private void choseHeadImageFromCameraCapture() {
		Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		// 判断存储卡是否可用，存储照片文件
		if (hasSdcard()) {
			intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
					.fromFile(new File(Environment
							.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		}

		startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
									Intent intent) {

		// 用户没有进行有效的设置操作，返回
		if (resultCode == RESULT_CANCELED) {//取消
			Toast.makeText(getApplication(), "取消", Toast.LENGTH_LONG).show();
			return;
		}

		switch (requestCode) {
			case CODE_GALLERY_REQUEST://如果是来自本地的
				cropRawPhoto(intent.getData());//直接裁剪图片
				break;

			case CODE_CAMERA_REQUEST:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);
					cropRawPhoto(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
							.show();
				}

				break;

			case CODE_RESULT_REQUEST:
				if (intent != null) {
					setImageToHeadView(intent);//设置图片框
				}

				break;
		}

		super.onActivityResult(requestCode, resultCode, intent);
	}

	/**
	 * 裁剪原始的图片
	 */
	public void cropRawPhoto(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");

		//把裁剪的数据填入里面

		// 设置裁剪
		intent.putExtra("crop", "true");

		// aspectX , aspectY :宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX , outputY : 裁剪图片宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);

		startActivityForResult(intent, CODE_RESULT_REQUEST);
	}

	/**
	 * 提取保存裁剪之后的图片数据，并设置头像部分的View
	 */
	private void setImageToHeadView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			headImage.setImageBitmap(photo);

			//新建文件夹 先选好路径 再调用mkdir函数 现在是根目录下面的Ask文件夹
			File nf = new File(Environment.getExternalStorageDirectory()+"/Ask");
			nf.mkdir();
			//在根目录下面的ASk文件夹下 创建okkk.jpg文件
			File f = new File(Environment.getExternalStorageDirectory()+"/Ask", "okkk.jpg");

			FileOutputStream out = null;
			try {

				//打开输出流 将图片数据填入文件中
				out = new FileOutputStream(f);
				photo.compress(Bitmap.CompressFormat.PNG, 90, out);

				try {
					out.flush();
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}


		}
	}

	/**
	 * 检查设备是否存在SDCard的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			// 有存储的SDCard
			return true;
		} else {
			return false;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		if (!NetUtil.isNetConnected(this))
			mNetErrorView.setVisibility(View.VISIBLE);
		else
			mNetErrorView.setVisibility(View.GONE);
	}

	private void initData() {
		// TODO Auto-generated method stub
		mApplication = SchoolServiceApplication.getInstance();
		mLoginOutTimeProcess = new LoginOutTimeProcess();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();
		mUserDB = mApplication.getUserDB();
		MyPushMessageReceiver.ehList.add(this);// 监听推送的消息
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if (task != null)
			task.stop();
		MyPushMessageReceiver.ehList.remove(this);// 注销推送的消息
	}

	private void initView() {
		mNetErrorView = findViewById(R.id.net_status_bar_top);
		mTitle = (TextView) findViewById(R.id.ivTitleName);
		mTitle.setVisibility(View.VISIBLE);
		mTitle.setText(R.string.first_start_title);
		mNetErrorView.setOnClickListener(this);
		mFirstStartBtn = (Button) findViewById(R.id.first_start_btn);
		mFirstStartBtn.setOnClickListener(this);
		mNickEt = (EditText) findViewById(R.id.nick_ed);
		mNickEt.setText(mSpUtil.getNick());


		mHeadWheel = (WheelView) findViewById(R.id.acount_head);
		mSexWheel = (WheelView) findViewById(R.id.acount_sex);
		mHeadWheel.setViewAdapter(new HeadAdapter(this));
		mSexWheel.setViewAdapter(new SexAdapter(this));
	}

	private Dialog mConnectServerDialog;

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.first_start_btn:
			if (!NetUtil.isNetConnected(this)) {
				T.showLong(this, R.string.net_error_tip);
				return;
			}

			SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
			 String nick=ps.getString("name", "");
			//mNickEt.setText(nick);

			//String nick = mNickEt.getText().toString();
			if (TextUtils.isEmpty(nick)) {
				T.showShort(getApplicationContext(), R.string.first_start_tips);
				return;
			}
			mSpUtil.setNick(nick);
			mSpUtil.setHeadIcon(mHeadWheel.getCurrentItem());
			mSpUtil.setTag(SexAdapter.SEXS[mSexWheel.getCurrentItem()]);

			PushManager.startWork(getApplicationContext(),
					PushConstants.LOGIN_TYPE_API_KEY,SchoolServiceApplication.API_KEY);
			List tags=new ArrayList();
               tags.add("数据挖掘");
			   tags.add("云计算");

             PushManager.setTags(getApplicationContext(),tags);

			onBind("",0,"");


			//PushManager.startWork(getApplicationContext(),
					///PushConstants.LOGIN_TYPE_API_KEY, SchoolServiceApplication.API_KEY);// 无baidu帐号登录,以apiKey随机获取一个id
			mConnectServerDialog = DialogUtil.getLoginDialog(this);
			mConnectServerDialog.show();
			mConnectServerDialog.setCancelable(false);// 返回键不能取消
			if (mLoginOutTimeProcess != null && !mLoginOutTimeProcess.running)
				mLoginOutTimeProcess.start();
			break;
		case R.id.net_status_bar_info_top:
			// 跳转到网络设置
			startActivity(new Intent(
					android.provider.Settings.ACTION_WIFI_SETTINGS));
			break;
		default:
			break;
		}
	}

	@Override
	public void onMessage(com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message message) {
		// TODO Auto-generated method stub

	}
	private SendMsgAsyncTask task;

	public void onBind(String method, int errorCode, String content) {
		if (errorCode == 0) {// 如果绑定账号成功，由于第一次运行，给同一tag的人推送一条新人消息
			User u = new User(mSpUtil.getUserId(), mSpUtil.getChannelId(),
					mSpUtil.getNick(), mSpUtil.getHeadIcon(), 0);
			mUserDB.addUser(u);// 把自己添加到数据库
			SharedPreferences sp=getSharedPreferences("token&channel",0);//token存储文件名
			SharedPreferences.Editor se=sp.edit();
			se.putString("channel_id",mSpUtil.getChannelId());
			se.putString("userid",mSpUtil.getUserId());
			se.putString("gender",mSpUtil.getTag());
			se.putString("nick",mSpUtil.getNick());
			se.commit();

			Toast.makeText(getApplicationContext(), mSpUtil.getChannelId(),
					Toast.LENGTH_SHORT).show();
			com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message msgItem = new com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message(
					System.currentTimeMillis(), "hi你好，欢迎来到武大", mSpUtil.getTag());
			task = new SendMsgAsyncTask(mGson.toJson(msgItem), "");


			task.setOnSendScuessListener(new SendMsgAsyncTask.OnSendScuessListener() {

				@Override
				public void sendScuess() {
					startActivity(new Intent(FirstSetActivity.this,
							MainActivity.class));
					if (mConnectServerDialog != null
							&& mConnectServerDialog.isShowing())
						mConnectServerDialog.dismiss();

					if (mLoginOutTimeProcess != null
							&& mLoginOutTimeProcess.running)
						mLoginOutTimeProcess.stop();
					T.showShort(mApplication, R.string.first_start_scuess);
					finish();
				}
			});
			task.send();
		}
		//startService(new Intent(FirstSetActivity.this, UploadProfile.class));


		//uploadprofile(mSpUtil.getChannelId(),mSpUtil.getUserId(),mSpUtil.getTag(),mSpUtil.getNick(),"123");
	}

	/*private void uploadprofile(final String channelid, final String userid, final String gender, final String nickname, final String headicon){
		SharedPreferences ps=getSharedPreferences("token&channel",0);//login是存储文件
		String token0=ps.getString("token", "");
		final String token = token0.substring(21, 61);
		//Log.d("TAG", token);
		String httpurl = "http://115.159.74.116:8000/user/upload_profile/";
		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		StringRequest stringRequest = new StringRequest(Request.Method.POST, httpurl,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						Log.d("TAG", "response -> " + response);
						//back.setText(response);
					}
				}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.e("TAG", error.getMessage(), error);
			}
		}) {

			@Override
			public Map<String, String> getHeaders() {
				HashMap<String, String> headers = new HashMap<String, String>();
				//headers.put("WWW-Authenticate","Basic realm=\"fake\"");
				headers.put("Authorization","Token "+token);
				return headers;
			}

			@Override
			protected Map<String, String> getParams() {
				//在这里设置需要post的参数
				Map<String, String> map = new HashMap<String, String>();
				map.put("channel_id", channelid);
				map.put("user_identity", userid);
				map.put("gender", gender);
				map.put("nick_name",nickname);
				map.put("head_icon",headicon);
				return map;
			}
		};


       requestQueue.add(stringRequest);
	}*/

	@Override
	public void onNotify(String title, String content) {



	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		if (!isNetConnected) {
			T.showShort(this, R.string.net_error_tip);
			mNetErrorView.setVisibility(View.VISIBLE);
		} else {
			mNetErrorView.setVisibility(View.GONE);
		}
	}

	@Override
	public void onNewFriend(User u) {
		// do nothing
	}

	// 登录超时处理线程
	class LoginOutTimeProcess implements Runnable {
		public boolean running = false;
		private long startTime = 0L;
		private Thread thread = null;

		LoginOutTimeProcess() {
		}

		public void run() {
			while (true) {
				if (!this.running)
					return;
				if (System.currentTimeMillis() - this.startTime > 20 * 1000L) {
					mHandler.sendEmptyMessage(LOGIN_OUT_TIME);
				}
				try {
					Thread.sleep(10L);
				} catch (Exception localException) {
				}
			}
		}

		public void start() {
			try {
				this.thread = new Thread(this);
				this.running = true;
				this.startTime = System.currentTimeMillis();
				this.thread.start();
			} finally {
			}
		}

		public void stop() {
			try {
				this.running = false;
				this.thread = null;
				this.startTime = 0L;
			} finally {
			}
		}
	}
}
