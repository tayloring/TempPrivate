package com.whu.Gongyinchao.schoolservice.chatmodule.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.adapter.FaceAdapter;
import com.whu.Gongyinchao.schoolservice.chatmodule.adapter.FacePageAdeapter;
import com.whu.Gongyinchao.schoolservice.chatmodule.adapter.MessageAdapter;
import com.whu.Gongyinchao.schoolservice.chatmodule.baidupush.client.MyPushMessageReceiver;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.MessageItem;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.RecentItem;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.User;
import com.whu.Gongyinchao.schoolservice.chatmodule.database.MessageDB;
import com.whu.Gongyinchao.schoolservice.chatmodule.database.RecentDB;
import com.whu.Gongyinchao.schoolservice.chatmodule.swipeback.SwipeBackActivity;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.HomeWatcher;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.HomeWatcher.OnHomePressedListener;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.L;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SendMsgAsyncTask;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SharePreferenceUtil;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.T;
import com.whu.Gongyinchao.schoolservice.chatmodule.view.CirclePageIndicator;
import com.whu.Gongyinchao.schoolservice.chatmodule.view.JazzyViewPager;
import com.whu.Gongyinchao.schoolservice.chatmodule.view.JazzyViewPager.TransitionEffect;
import com.whu.Gongyinchao.schoolservice.chatmodule.xlistview.MsgListView;
import com.whu.Gongyinchao.schoolservice.chatmodule.xlistview.MsgListView.IXListViewListener;
import com.whu.Gongyinchao.schoolservice.common.app.SchoolServiceApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ChatActivity extends SwipeBackActivity implements
		MyPushMessageReceiver.EventHandler, OnTouchListener, OnClickListener,
		OnHomePressedListener, IXListViewListener {
	public static final int NEW_MESSAGE = 0x001;// 收到消息
	private TransitionEffect mEffects[] = { TransitionEffect.Standard,
			TransitionEffect.Tablet, TransitionEffect.CubeIn,
			TransitionEffect.CubeOut, TransitionEffect.FlipVertical,
			TransitionEffect.FlipHorizontal, TransitionEffect.Stack,
			TransitionEffect.ZoomIn, TransitionEffect.ZoomOut,
			TransitionEffect.RotateUp, TransitionEffect.RotateDown,
			TransitionEffect.Accordion, };// 表情翻页效果
	private SchoolServiceApplication mApplication;
	private static int MsgPagerNum;
	private MessageDB mMsgDB;
	private RecentDB mRecentDB;
	private int currentPage = 0;
	private boolean isFaceShow = false;
	private Button sendBtn;
	private ImageButton faceBtn;
	private EditText msgEt;
	private LinearLayout faceLinearLayout;
	private JazzyViewPager faceViewPager;
	private WindowManager.LayoutParams params;
	private InputMethodManager imm;
	private List<String> keys;
	private MessageAdapter adapter;
	private MsgListView mMsgListView;
	private SharePreferenceUtil mSpUtil;
	private User mFromUser;
	private TextView mTitle, mTitleLeftBtn, mTitleRightBtn;
	private HomeWatcher mHomeWatcher;
	private Gson mGson;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == NEW_MESSAGE) {
				// String message = (String) msg.obj;
				com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message msgItem = (com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message) msg.obj;
				String userId = msgItem.getUser_id();
				if (!userId.equals(mFromUser.getUserId()))// 如果不是当前正在聊天对象的消息，不处理
					return;

				int headId = msgItem.getHead_id();
				/*
				 * try { headId = Integer
				 * .parseInt(JsonUtil.getFromUserHead(message)); } catch
				 * (Exception e) { L.e("head is not integer  " + e); }
				 */
				// TODO Auto-generated method stub
				MessageItem item = new MessageItem(
						MessageItem.MESSAGE_TYPE_TEXT, msgItem.getNick(),
						System.currentTimeMillis(), msgItem.getMessage(),
						headId, true, 0);
				adapter.upDateMsg(item);
				mMsgDB.saveMsg(msgItem.getUser_id(), item);
				RecentItem recentItem = new RecentItem(userId, headId,
						msgItem.getNick(), msgItem.getMessage(), 0,
						System.currentTimeMillis());
				mRecentDB.saveRecent(recentItem);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat_main);
		initData();
		initView();
		initFacePage();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHomeWatcher = new HomeWatcher(this);
		mHomeWatcher.setOnHomePressedListener(this);
		mHomeWatcher.startWatch();
		MyPushMessageReceiver.ehList.add(this);// 监听推送的消息
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		imm.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
		faceLinearLayout.setVisibility(View.GONE);
		isFaceShow = false;
		super.onPause();
		mHomeWatcher.setOnHomePressedListener(null);
		mHomeWatcher.stopWatch();
		MyPushMessageReceiver.ehList.remove(this);// 移除监听
	}

	private void initData() {
		mFromUser = (User) getIntent().getSerializableExtra("user");
		if (mFromUser == null) {// 如果为空，直接关闭
			finish();
		}
		mApplication = SchoolServiceApplication.getInstance();
		mSpUtil = mApplication.getSpUtil();
		mGson = mApplication.getGson();
		mMsgDB = mApplication.getMessageDB();
		mRecentDB = mApplication.getRecentDB();
		Set<String> keySet = SchoolServiceApplication.getInstance().getFaceMap()
				.keySet();
		keys = new ArrayList<String>();
		keys.addAll(keySet);
		MsgPagerNum = 0;
		adapter = new MessageAdapter(this, initMsgData());
	}

	/**
	 * 加载消息历史，从数据库中读出
	 */
	private List<MessageItem> initMsgData() {
		List<MessageItem> list = mMsgDB.getMsg(mFromUser.getUserId(),
				MsgPagerNum);
		List<MessageItem> msgList = new ArrayList<MessageItem>();// 消息对象数组
		if (list.size() > 0) {
			for (MessageItem entity : list) {
				if (entity.getName().equals("")) {
					entity.setName(mFromUser.getNick());
				}
				if (entity.getHeadImg() < 0) {
					entity.setHeadImg(mFromUser.getHeadIcon());
				}
				msgList.add(entity);
			}
		}
		return msgList;

	}

	private void initFacePage() {
		// TODO Auto-generated method stub
		List<View> lv = new ArrayList<View>();
		for (int i = 0; i < SchoolServiceApplication.NUM_PAGE; ++i)
			lv.add(getGridView(i));
		FacePageAdeapter adapter = new FacePageAdeapter(lv, faceViewPager);
		faceViewPager.setAdapter(adapter);
		faceViewPager.setCurrentItem(currentPage);
		faceViewPager.setTransitionEffect(mEffects[mSpUtil.getFaceEffect()]);
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(faceViewPager);
		adapter.notifyDataSetChanged();
		faceLinearLayout.setVisibility(View.GONE);
		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				currentPage = arg0;
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// do nothing
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// do nothing
			}
		});

	}

	private GridView getGridView(int i) {
		// TODO Auto-generated method stub
		GridView gv = new GridView(this);
		gv.setNumColumns(7);
		gv.setSelector(new ColorDrawable(Color.TRANSPARENT));// 屏蔽GridView默认点击效果
		gv.setBackgroundColor(Color.TRANSPARENT);
		gv.setCacheColorHint(Color.TRANSPARENT);
		gv.setHorizontalSpacing(1);
		gv.setVerticalSpacing(1);
		gv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
		gv.setGravity(Gravity.CENTER);
		gv.setAdapter(new FaceAdapter(this, i));
		gv.setOnTouchListener(forbidenScroll());
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (arg2 == SchoolServiceApplication.NUM) {// 删除键的位置
					int selection = msgEt.getSelectionStart();
					String text = msgEt.getText().toString();
					if (selection > 0) {
						String text2 = text.substring(selection - 1);
						if ("]".equals(text2)) {
							int start = text.lastIndexOf("[");
							int end = selection;
							msgEt.getText().delete(start, end);
							return;
						}
						msgEt.getText().delete(selection - 1, selection);
					}
				} else {
					int count = currentPage * SchoolServiceApplication.NUM + arg2;
					// 注释的部分，在EditText中显示字符串
					// String ori = msgEt.getText().toString();
					// int index = msgEt.getSelectionStart();
					// StringBuilder stringBuilder = new StringBuilder(ori);
					// stringBuilder.insert(index, keys.get(count));
					// msgEt.setText(stringBuilder.toString());
					// msgEt.setSelection(index + keys.get(count).length());

					// 下面这部分，在EditText中显示表情
					Bitmap bitmap = BitmapFactory.decodeResource(
							getResources(), (Integer) SchoolServiceApplication
									.getInstance().getFaceMap().values()
									.toArray()[count]);
					if (bitmap != null) {
						int rawHeigh = bitmap.getHeight();
						int rawWidth = bitmap.getHeight();
						int newHeight = 40;
						int newWidth = 40;
						// 计算缩放因子
						float heightScale = ((float) newHeight) / rawHeigh;
						float widthScale = ((float) newWidth) / rawWidth;
						// 新建立矩阵
						Matrix matrix = new Matrix();
						matrix.postScale(heightScale, widthScale);
						// 设置图片的旋转角度
						// matrix.postRotate(-30);
						// 设置图片的倾斜
						// matrix.postSkew(0.1f, 0.1f);
						// 将图片大小压缩
						// 压缩后图片的宽和高以及kB大小均会变化
						Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0,
								rawWidth, rawHeigh, matrix, true);
						ImageSpan imageSpan = new ImageSpan(ChatActivity.this,
								newBitmap);
						String emojiStr = keys.get(count);
						SpannableString spannableString = new SpannableString(
								emojiStr);
						spannableString.setSpan(imageSpan,
								emojiStr.indexOf('['),
								emojiStr.indexOf(']') + 1,
								Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
						msgEt.append(spannableString);
					} else {
						String ori = msgEt.getText().toString();
						int index = msgEt.getSelectionStart();
						StringBuilder stringBuilder = new StringBuilder(ori);
						stringBuilder.insert(index, keys.get(count));
						msgEt.setText(stringBuilder.toString());
						msgEt.setSelection(index + keys.get(count).length());
					}
				}
			}
		});
		return gv;
	}

	private void initView() {
		// TODO Auto-generated method stub
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		params = getWindow().getAttributes();

		mMsgListView = (MsgListView) findViewById(R.id.msg_listView);
		// 触摸ListView隐藏表情和输入法
		mMsgListView.setOnTouchListener(this);
		mMsgListView.setPullLoadEnable(false);
		mMsgListView.setXListViewListener(this);
		mMsgListView.setAdapter(adapter);
		mMsgListView.setSelection(adapter.getCount() - 1);
		sendBtn = (Button) findViewById(R.id.send_btn);
		faceBtn = (ImageButton) findViewById(R.id.face_btn);
		msgEt = (EditText) findViewById(R.id.msg_et);
		faceLinearLayout = (LinearLayout) findViewById(R.id.face_ll);
		faceViewPager = (JazzyViewPager) findViewById(R.id.face_pager);
		msgEt.setOnTouchListener(this);
		mTitle = (TextView) findViewById(R.id.ivTitleName);
		mTitle.setText(mFromUser.getNick());
		mTitleLeftBtn = (TextView) findViewById(R.id.ivTitleBtnLeft);
		mTitleLeftBtn.setVisibility(View.VISIBLE);
		// mTitleRightBtn = (TextView) findViewById(R.id.ivTitleBtnRigh);
		mTitleLeftBtn.setOnClickListener(this);
		// mTitleRightBtn.setOnClickListener(this);
		msgEt.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_BACK) {
					if (params.softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
							|| isFaceShow) {
						faceLinearLayout.setVisibility(View.GONE);
						isFaceShow = false;
						// imm.showSoftInput(msgEt, 0);
						return true;
					}
				}
				return false;
			}
		});
		msgEt.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				if (s.length() > 0) {
					sendBtn.setEnabled(true);
				} else {
					sendBtn.setEnabled(false);
				}
			}
		});
		faceBtn.setOnClickListener(this);
		sendBtn.setOnClickListener(this);
	}

	// 防止乱pageview乱滚动
	private OnTouchListener forbidenScroll() {
		return new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_MOVE) {
					return true;
				}
				return false;
			}
		};
	}

	@Override
	public void onMessage(com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message message) {
		Message handlerMsg = handler.obtainMessage(NEW_MESSAGE);
		handlerMsg.obj = message;
		handler.sendMessage(handlerMsg);
	}


/*	@Override
	public void onBind(String method, int errorCode, String content) {
		// TODO Auto-generated method stub

	}*/

	@Override
	public void onNotify(String title, String content) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onNetChange(boolean isNetConnected) {
		// TODO Auto-generated method stub
		if (!isNetConnected)
			T.showShort(this, "网络连接已断开");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.face_btn:
			if (!isFaceShow) {
				imm.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
				try {
					Thread.sleep(80);// 解决此时会黑一下屏幕的问题
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				faceLinearLayout.setVisibility(View.VISIBLE);
				isFaceShow = true;
			} else {
				faceLinearLayout.setVisibility(View.GONE);
				isFaceShow = false;
			}
			break;
		case R.id.send_btn:// 发送消息
			String msg = msgEt.getText().toString();
			MessageItem item = new MessageItem(MessageItem.MESSAGE_TYPE_TEXT,
					mSpUtil.getNick(), System.currentTimeMillis(), msg,
					mSpUtil.getHeadIcon(), false, 0);
			adapter.upDateMsg(item);
			// if (adapter.getCount() - 10 > 10) {
			// L.i("begin to remove...");
			// adapter.removeHeadMsg();
			// MsgPagerNum--;
			// }
			mMsgListView.setSelection(adapter.getCount() - 1);
			mMsgDB.saveMsg(mFromUser.getUserId(), item);
			msgEt.setText("");
			com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message msgItem = new com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message(
					System.currentTimeMillis(), msg, "");
			new SendMsgAsyncTask(mGson.toJson(msgItem), mFromUser.getUserId())
					.send();
			RecentItem recentItem = new RecentItem(mFromUser.getUserId(),
					mFromUser.getHeadIcon(), mFromUser.getNick(), msg, 0,
					System.currentTimeMillis());
			mRecentDB.saveRecent(recentItem);
			break;
		case R.id.ivTitleBtnLeft:
			finish();
			break;
		case R.id.ivTitleBtnRigh:
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.msg_listView:
			imm.hideSoftInputFromWindow(msgEt.getWindowToken(), 0);
			faceLinearLayout.setVisibility(View.GONE);
			isFaceShow = false;
			break;
		case R.id.msg_et:
			imm.showSoftInput(msgEt, 0);
			faceLinearLayout.setVisibility(View.GONE);
			isFaceShow = false;
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public void onNewFriend(User u) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHomePressed() {
		// TODO Auto-generated method stub
		mApplication.showNotification();
	}

	@Override
	public void onHomeLongPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		MsgPagerNum++;
		List<MessageItem> msgList = initMsgData();
		int position = adapter.getCount();
		adapter.setMessageList(msgList);
		mMsgListView.stopRefresh();
		mMsgListView.setSelection(adapter.getCount() - position - 1);
		L.i("MsgPagerNum = " + MsgPagerNum + ", adapter.getCount() = "
				+ adapter.getCount());
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub

	}

}