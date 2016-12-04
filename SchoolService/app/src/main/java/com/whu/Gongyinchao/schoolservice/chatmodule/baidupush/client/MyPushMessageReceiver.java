package com.whu.Gongyinchao.schoolservice.chatmodule.baidupush.client;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.baidu.android.pushservice.PushMessageReceiver;
import com.google.gson.Gson;
import com.whu.Gongyinchao.schoolservice.R;
import com.whu.Gongyinchao.schoolservice.chatmodule.activity.MainActivity;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.Message;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.MessageItem;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.RecentItem;
import com.whu.Gongyinchao.schoolservice.chatmodule.bean.User;
import com.whu.Gongyinchao.schoolservice.chatmodule.database.UserDB;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.L;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.NetUtil;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SendMsgAsyncTask;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.SharePreferenceUtil;
import com.whu.Gongyinchao.schoolservice.chatmodule.util.T;
import com.whu.Gongyinchao.schoolservice.common.app.SchoolServiceApplication;
import com.whu.Gongyinchao.schoolservice.indoormodule.ui.lecture.indoorLecture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("NewApi")
public  class MyPushMessageReceiver extends PushMessageReceiver {
	public static final String TAG = MyPushMessageReceiver.class.getSimpleName();
	public static final int NOTIFY_ID = 0x000;
	public static int mNewNum = 0;// 通知栏新消息条目，我只是用了一个全局变量，
	public static final String RESPONSE = "response";
	public static ArrayList<EventHandler> ehList = new ArrayList<EventHandler>();
	private SharePreferenceUtil mSpUtil;
	private Gson mGson;
	private UserDB mUserDB;
	private SchoolServiceApplication mApplication;
	private static final int LOGIN_OUT_TIME = 0;

	public static abstract interface EventHandler {
		public abstract void onMessage(Message message);

	//	public abstract void onBind(String method, int errorCode, String content);

	//	public abstract void onBind(final Context context, int errorCode, String appid,
						//   String userId, String channelId, String requestId);
		//void onBind(String method, int errorCode, String content);

		public abstract void onNotify(String title, String content);

		public abstract void onNetChange(boolean isNetConnected);

		public void onNewFriend(User u);
	}




	/*@Override
	public void onReceive(Context context, Intent intent) {

		// L.d(TAG, ">>> Receive intent: \r\n" + intent);
		L.i("listener num = " + ehList.size());
		if (intent.getAction().equals(PushConstants.ACTION_MESSAGE)) {
			// 获取消息内容
			String message = intent.getExtras().getString(
					PushConstants.EXTRA_PUSH_MESSAGE );
			// 消息的用户自定义内容读取方式
			L.i("onMessage: " + message);
			try {
				Message msgItem = SchoolServiceApplication.getInstance().getGson()
						.fromJson(message, Message.class);
				parseMessage(msgItem);// 预处理，过滤一些消息，比如说新人问候或自己发送的
			} catch (Exception e) {
				// TODO: handle exception
			}

		} else if (intent.getAction().equals(PushConstants.ACTION_RECEIVE)) {
			// 处理绑定等方法的返回数据
			// PushManager.startWork()的返回值通过PushConstants.METHOD_BIND得到
			// 获取方法
			final String method = intent
					.getStringExtra(PushConstants.EXTRA_METHOD);
			// 方法返回错误码。若绑定返回错误（非0），则应用将不能正常接收消息。
			// 绑定失败的原因有多种，如网络原因，或access token过期。
			// 请不要在出错时进行简单的startWork调用，这有可能导致死循环。
			// 可以通过限制重试次数，或者在其他时机重新调用来解决。
			final int errorCode = intent
					.getIntExtra(PushConstants.EXTRA_ERROR_CODE,
							PushConstants.ERROR_SUCCESS);
			// 返回内容
			final String content = new String(
					intent.getByteArrayExtra(PushConstants.EXTRA_CONTENT));

			// 用户在此自定义处理消息,以下代码为demo界面展示用
			L.i("onMessage: method : " + method + ", result : " + errorCode
					+ ", content : " + content);
			paraseContent(context, errorCode, content);// 处理消息

			// 回调函数
			for (int i = 0; i < ehList.size(); i++)
				((EventHandler) ehList.get(i)).onBind(method, errorCode,
						content);

			// 可选。通知用户点击事件处理
		} else if (intent.getAction().equals(
				PushConstants.ACTION_RECEIVE)) {
			L.d(TAG, "intent=" + intent.toUri(0));
			String title = intent
					.getStringExtra(PushConstants.ACTION_MESSAGE);
			String content = intent
					.getStringExtra(PushConstants.EXTRA_CONTENT);
			for (int i = 0; i < ehList.size(); i++)
				((EventHandler) ehList.get(i)).onNotify(title, content);
		} else if (intent.getAction().equals(
				"android.net.conn.CONNECTIVITY_CHANGE")) {
			boolean isNetConnected = NetUtil.isNetConnected(context);
			for (int i = 0; i < ehList.size(); i++)
				((EventHandler) ehList.get(i)).onNetChange(isNetConnected);
		}
	}*/

	private Dialog mConnectServerDialog;
	private SendMsgAsyncTask task;
	//public LoginOutTimeProcess mLoginOutTimeProcess;
	@Override
	public void onBind(final Context context, int errorCode, String appid,
					   String userId, String channelId, String requestId) {
		String responseString = "onBind errorCode=" + errorCode + " appid="
				+ appid + " userId=" + userId + " channelId=" + channelId
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		if (errorCode == 0) {
			//paraseContent(context,errorCode,responseString);
			SharePreferenceUtil util = SchoolServiceApplication.getInstance()
					.getSpUtil();
			util.setAppId(appid);
			util.setChannelId(channelId);
			util.setUserId(userId);

			// 绑定成功
			Log.d(TAG, "绑定成功");
		}else {
			if (NetUtil.isNetConnected(context)) {
				if (errorCode == 30607) {
					T.showLong(context, "账号已过期，请重新登录");
					// 跳转到重新登录的界面
				} else {
					T.showLong(context, "启动失败，正在重试...");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							PushManager.startWork(context,
									PushConstants.LOGIN_TYPE_API_KEY,
									SchoolServiceApplication.API_KEY);
						}
					}, 2000);// 两秒后重新开始验证
				}
			} else {
				T.showLong(context, R.string.net_error_tip);
			}
		}


		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//updateContent(context, responseString);
	}


	/**
	 * 接收透传消息的函数。
	 *
	 * @param context
	 *            上下文
	 * @param message
	 *            推送的消息
	 * @param customContentString
	 *            自定义内容,为空或者json字符串
	 */
	@Override
	public void onMessage(Context context, String message,
						  String customContentString) {
		String messageString = "透传消息 onMessage=\"" + message
				+ "\" customContentString=" + customContentString;
		Log.d(TAG, messageString);

		try {
			Message msgItem = SchoolServiceApplication.getInstance().getGson()
					.fromJson(message, Message.class);
			parseMessage(msgItem);// 预处理，过滤一些消息，比如说新人问候或自己发送的
		} catch (Exception e) {
			// TODO: handle exception
		}

		// 自定义内容获取方式，mykey和myvalue对应透传消息推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (!customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//updateContent(context, messageString);
	}

	/**
	 * 接收通知到达的函数。
	 *
	 * @param context
	 *            上下文
	 * @param title
	 *            推送的通知的标题
	 * @param description
	 *            推送的通知的描述
	 * @param customContentString
	 *            自定义内容，为空或者json字符串
	 */

	@Override
	public void onNotificationArrived(Context context, String title,
									  String description, String customContentString) {

		String notifyString = "通知到达 onNotificationArrived  title=\"" + title
				+ "\" description=\"" + description + "\" customContent="
				+ customContentString;
		Log.d(TAG, notifyString);

		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (!customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		// 你可以參考 onNotificationClicked中的提示从自定义内容获取具体值
		//updateContent(context, notifyString);
	}

	/**
	 * 接收通知点击的函数。
	 *
	 * @param context
	 *            上下文
	 * @param title
	 *            推送的通知的标题
	 * @param description
	 *            推送的通知的描述
	 * @param customContentString
	 *            自定义内容，为空或者json字符串
	 */
	@Override
	public void onNotificationClicked(Context context, String title,
									  String description, String customContentString) {
		String notifyString = "通知点击 onNotificationClicked title=\"" + title + "\" description=\""
				+ description + "\" customContent=" + customContentString;
		Log.d(TAG, notifyString);
		Intent intent = new Intent();
		intent.setClass(context.getApplicationContext(), indoorLecture.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.getApplicationContext().startActivity(intent);


		// 自定义内容获取方式，mykey和myvalue对应通知推送时自定义内容中设置的键和值
		if (!TextUtils.isEmpty(customContentString)) {
			JSONObject customJson = null;
			try {
				customJson = new JSONObject(customContentString);
				String myvalue = null;
				if (!customJson.isNull("mykey")) {
					myvalue = customJson.getString("mykey");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


	}

	/**
	 * setTags() 的回调函数。
	 *
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示某些tag已经设置成功；非0表示所有tag的设置均失败。
	 * @param //successTags
	 *            设置成功的tag
	 * @param failTags
	 *            设置失败的tag
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onSetTags(Context context, int errorCode,
						  List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onSetTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);



		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
	//	updateContent(context, responseString);
	}

	/**
	 * delTags() 的回调函数。
	 *
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示某些tag已经删除成功；非0表示所有tag均删除失败。
	 * @param //successTags
	 *            成功删除的tag
	 * @param failTags
	 *            删除失败的tag
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onDelTags(Context context, int errorCode,
						  List<String> sucessTags, List<String> failTags, String requestId) {
		String responseString = "onDelTags errorCode=" + errorCode
				+ " sucessTags=" + sucessTags + " failTags=" + failTags
				+ " requestId=" + requestId;
		Log.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//updateContent(context, responseString);
	}

	/**
	 * listTags() 的回调函数。
	 *
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示列举tag成功；非0表示失败。
	 * @param tags
	 *            当前应用设置的所有tag。
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onListTags(Context context, int errorCode, List<String> tags,
						   String requestId) {
		String responseString = "onListTags errorCode=" + errorCode + " tags="
				+ tags;
		Log.d(TAG, responseString);

		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//updateContent(context, responseString);
	}

	/**
	 * PushManager.stopWork() 的回调函数。
	 *
	 * @param context
	 *            上下文
	 * @param errorCode
	 *            错误码。0表示从云推送解绑定成功；非0表示失败。
	 * @param requestId
	 *            分配给对云推送的请求的id
	 */
	@Override
	public void onUnbind(Context context, int errorCode, String requestId) {
		String responseString = "onUnbind errorCode=" + errorCode
				+ " requestId = " + requestId;
		Log.d(TAG, responseString);

		if (errorCode == 0) {
			// 解绑定成功
			Log.d(TAG, "解绑成功");
		}
		// Demo更新界面展示代码，应用请在这里加入自己的处理逻辑
		//updateContent(context, responseString);
	}

	/*private void updateContent(Context context, String content) {
		Log.d(TAG, "updateContent");
		//String logText = "" + Utils.logStringCache;

		if (!logText.equals("")) {
			logText += "\n";
		}

		SimpleDateFormat sDateFormat = new SimpleDateFormat("HH-mm-ss");
		logText += sDateFormat.format(new Date()) + ": ";
		logText += content;

		Utils.logStringCache = logText;

		Intent intent = new Intent();
		intent.setClass(context.getApplicationContext(), PushDemoActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.getApplicationContext().startActivity(intent);
	}*/


	private void parseMessage(Message msg) {
		Gson gson = SchoolServiceApplication.getInstance().getGson();
		// Message msg = gson.fromJson(message, Message.class);
		L.i("gson ====" + msg.toString());
		String tag = msg.getTag();
		String userId = msg.getUser_id();
		int headId = msg.getHead_id();
		// try {
		// headId = Integer.parseInt(JsonUtil.getFromUserHead(message));
		// } catch (Exception e) {
		// L.e("head is not a Integer....");
		// }
		if (!TextUtils.isEmpty(tag)) {// 如果是带有tag的消息
			if (userId.equals(SchoolServiceApplication.getInstance().getSpUtil()
					.getUserId()))
				return;
			User u = new User(userId, msg.getChannel_id(), msg.getNick(),
					headId, 0);
			SchoolServiceApplication.getInstance().getUserDB().addUser(u);// 存入或更新好友
			for (EventHandler handler : ehList)
				handler.onNewFriend(u);
			if (!tag.equals(RESPONSE)) {
				// Intent intenService = new
				// Intent(SchoolServiceApplication.getInstance(),
				// PreParseService.class);
				// intenService.putExtra("message", message);
				// SchoolServiceApplication.getInstance().startService(intenService);//
				// 启动服务去回消息
				// L.i("启动服务回复消息");
				L.i("response start");
				Message item = new Message(System.currentTimeMillis(), "hi",
						MyPushMessageReceiver.RESPONSE);
				new SendMsgAsyncTask(gson.toJson(item), userId).send();// 同时也回一条消息给对方1
				L.i("response end");
			}
		} else {// 普通消息，
			if (SchoolServiceApplication.getInstance().getSpUtil().getMsgSound())// 如果用户开启播放声音
				SchoolServiceApplication.getInstance().getMediaPlayer().start();
			if (ehList.size() > 0) {// 有监听的时候，传递下去
				for (int i = 0; i < ehList.size(); i++)
					((EventHandler) ehList.get(i)).onMessage(msg);
			} else {
				// 通知栏提醒，保存数据库
				// show notify
				showNotify(msg);
				MessageItem item = new MessageItem(
						MessageItem.MESSAGE_TYPE_TEXT, msg.getNick(),
						System.currentTimeMillis(), msg.getMessage(), headId,
						true, 1);
				RecentItem recentItem = new RecentItem(userId, headId,
						msg.getNick(), msg.getMessage(), 0,
						System.currentTimeMillis());
				SchoolServiceApplication.getInstance().getMessageDB()
						.saveMsg(userId, item);
				SchoolServiceApplication.getInstance().getRecentDB()
						.saveRecent(recentItem);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void showNotify(Message message) {
		// TODO Auto-generated method stub
		mNewNum++;
		// 更新通知栏
		SchoolServiceApplication application = SchoolServiceApplication.getInstance();

		int icon = R.drawable.notify_newmessage;
		CharSequence tickerText = message.getNick() + ":"
				+ message.getMessage();
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);

		notification.flags = Notification.FLAG_NO_CLEAR;
		// 设置默认声音
		 notification.defaults |= Notification.DEFAULT_SOUND;
		// 设定震动(需加VIBRATE权限)
		notification.defaults |= Notification.DEFAULT_VIBRATE;
		notification.contentView = null;

		Intent intent = new Intent(application, MainActivity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(application, 0,
				intent, 0);
		//notification.setLatestEventInfo(SchoolServiceApplication.getInstance(),
				//application.getSpUtil().getNick() + " (" + mNewNum + "条新消息)",
			//	tickerText, contentIntent);
		// 下面是4.0通知栏api
		// Bitmap headBm = BitmapFactory.decodeResource(
		// application.getResources(), SchoolServiceApplication.heads[Integer
		// .parseInt(JsonUtil.getFromUserHead(message))]);
		// Notification.Builder mNotificationBuilder = new
		// Notification.Builder(application)
		// .setTicker(tickerText)
		// .setContentTitle(JsonUtil.getFromUserNick(message))
		// .setContentText(JsonUtil.getMsgContent(message))
		// .setSmallIcon(R.drawable.notify_newmessage)
		// .setLargeIcon(headBm).setWhen(System.currentTimeMillis())
		// .setContentIntent(contentIntent);
		// Notification n = mNotificationBuilder.getNotification();
		// n.flags |= Notification.FLAG_NO_CLEAR;
		//
		// n.defaults |= Notification.DEFAULT_VIBRATE;

		application.getNotificationManager().notify(NOTIFY_ID, notification);// 通知一下才会生效哦
	}

	/**
	 * 处理登录结果
	 * 
	 * @param errorCode
	 * @param content
	 */
	private void paraseContent(final Context context, int errorCode,
			String content) {
		// TODO Auto-generated method stub
		if (errorCode == 0) {
			String appid = "";
			String channelid = "";
			String userid = "";

			try {
				JSONObject jsonContent = new JSONObject(content);
				JSONObject params = jsonContent
						.getJSONObject("response_params");
				appid = params.getString("appid");
				channelid = params.getString("channel_id");
				userid = params.getString("user_id");
			} catch (JSONException e) {
				L.e(TAG, "Parse bind json infos error: " + e);
			}
			SharePreferenceUtil util = SchoolServiceApplication.getInstance()
					.getSpUtil();
			util.setAppId(appid);
			util.setChannelId(channelid);
			util.setUserId(userid);
		} else {
			if (NetUtil.isNetConnected(context)) {
				if (errorCode == 30607) {
					T.showLong(context, "账号已过期，请重新登录");
					// 跳转到重新登录的界面
				} else {
					T.showLong(context, "启动失败，正在重试...");
					new Handler().postDelayed(new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							PushManager.startWork(context,
									PushConstants.LOGIN_TYPE_API_KEY,
									SchoolServiceApplication.API_KEY);
						}
					}, 2000);// 两秒后重新开始验证
				}
			} else {
				T.showLong(context, R.string.net_error_tip);
			}
		}
	}
}
