package com.whu.Gongyinchao.schoolservice.indoormodule.ui.expresspart.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.whu.Gongyinchao.schoolservice.R;

import java.util.ArrayList;

public class MyListView extends ListView implements OnScrollListener {
	private View headView;
	private int headViewHeight;
	int currentScrollState;
	private float lastDownY;
	private int deltaCount;
	private ArrayList<String> data;
	private ArrayAdapter<String> adapter;
	private int currentState;
	private final int DECREASE_HEADVIEW_PADDING = 100;
	private final int LOAD_DATA = 101;
	private final int DISMISS_CIRCLE = 102;
	private ImageView circle;

	private int CircleMarginTop;

	private int firstVisibleItem;
	
	// -- footer view
	private XListViewFooter mFooterView;
//	private boolean mEnablePullLoad;
	private boolean mPullLoading;
//	private boolean mIsFooterReady = false;
	private IXListViewListener mListViewListener;
	private final static int PULL_LOAD_MORE_DELTA = 50;
//	private final static int SCROLLBACK_FOOTER = 1;
//	private final static int SCROLL_DURATION = 400;
//	private int mScrollBack;
	private int mTotalItemCount;
	private final static float OFFSET_RADIO = 1.0f;
	private int direction;//1代表向上2代表向下
//	private Scroller mScroller;
	
	private float mLastY = -1;
	
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DECREASE_HEADVIEW_PADDING:
				System.out.println("lln---111");//松手开始刷新
				setHeadViewPaddingTop(deltaCount);
				setCircleMargin();
				break;
			case LOAD_DATA:
				// clearCircleViewMarginTop();
				System.out.println("lln---222");
				Thread thread = new Thread(new DismissCircleThread());
				thread.start();
				currentState = LoadState.LOADSTATE_IDLE;
				if (adapter != null) {
					adapter.notifyDataSetChanged();
				}
				mListViewListener.onRefresh();
				break;
			case DISMISS_CIRCLE:
				System.out.println("lln---333");//刷新结束
				int margin = msg.arg1;
				setCircleMargin(margin);
				if (margin == 0) {
					CircleAnimation.stopRotateAnmiation(circle);
				}
				break;

			}
		}

	};

	//给刷新圆圈添加margintop的值
	protected void setCircleMargin(int margin) {
		MarginLayoutParams lp = (MarginLayoutParams) circle.getLayoutParams();
		lp.topMargin = margin;
		circle.setLayoutParams(lp);
	}

	protected void setCircleMargin() {
		// TODO Auto-generated method stub
		MarginLayoutParams lp = (MarginLayoutParams) circle.getLayoutParams();
		lp.topMargin = CircleMarginTop - headView.getPaddingTop();
		circle.setLayoutParams(lp);
	}

	private class DecreaseThread implements Runnable {
		private final static int TIME = 25;
		private int decrease_length;

		public DecreaseThread(int count) {
			decrease_length = count / TIME;
		}

		@Override
		public void run() {
			for (int i = 0; i < TIME; i++) {
				if (i == 24) {
					deltaCount = 0;
				} else {
					deltaCount = deltaCount - decrease_length;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Message msg = Message.obtain();
				msg.what = DECREASE_HEADVIEW_PADDING;
				handler.sendMessage(msg);
			}
		}
	}

	public MyListView(Context context, ArrayList<String> dataList) {
		this(context);
		this.data = dataList;
	}

	public MyListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView(context);
	}

	public MyListView(Context context) {
		super(context);
		initHeadView(context);
	}

	public MyListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeadView(context);
	}

	private void initHeadView(Context context) {
		super.setOnScrollListener(this);
		headView = LayoutInflater.from(context).inflate(R.layout.header, null);
		addHeaderView(headView);//初始化headerview并把headerview添加到listview里
		circle = (ImageView) headView.findViewById(R.id.circleprogress);
		//当一个视图树将要被绘制时，调用的回调类,主要是获取header的高度
		headView.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					@Override
					public boolean onPreDraw() {
						if (headView.getMeasuredHeight() > 0) {
							headViewHeight = headView.getMeasuredHeight();
							headView.getViewTreeObserver()
									.removeOnPreDrawListener(this);
						}
						return true;
					}
				});
		
		// init footer view
		mFooterView = new XListViewFooter(context);
		addFooterView(mFooterView);
//		mScroller = new Scroller(context, new DecelerateInterpolator());
		
		currentScrollState = OnScrollListener.SCROLL_STATE_IDLE;
		currentState = LoadState.LOADSTATE_IDLE;
		firstVisibleItem = 0;
		CircleMarginTop = 76;
		setSelector(new ColorDrawable(Color.TRANSPARENT));
	    setItemsCanFocus(true);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		float downY = ev.getY();
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastDownY = downY;
			break;
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float downY = event.getY();
		
		if (mLastY == -1) {
			mLastY = event.getRawY();
		}
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			mLastY = event.getRawY();
			break;
		case MotionEvent.ACTION_UP:
			if (deltaCount > 0 && currentState != LoadState.LOADSTATE_LOADING
					&& firstVisibleItem == 0
					&& headView.getBottom() >= headViewHeight) {
				decreasePadding(deltaCount);
				loadDataForThreeSecond();
				startCircleAnimation();
			}
			if(direction == 1){
				if(getLastVisiblePosition() == mTotalItemCount - 1){
					if (mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA){
						startLoadMore();
					}
				}
				
				mFooterView.setBottomMargin(0);
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
			
			break;
		case MotionEvent.ACTION_MOVE:
			final float deltaY = event.getRawY() - mLastY;
			mLastY = event.getRawY();
			
			int nowDeltaCount = (int) ((downY - lastDownY) / 3.0);
			int grepDegree = nowDeltaCount - deltaCount;
			deltaCount = nowDeltaCount;
			
			if (getFirstVisiblePosition() == 0 && currentState != LoadState.LOADSTATE_LOADING
					&& (headView.getBottom() >= headViewHeight || deltaY > 0)){
			
//			if (deltaCount > 0 && currentState != LoadState.LOADSTATE_LOADING
//					&& firstVisibleItem == 0
//					&& headView.getBottom() >= headViewHeight) {
				direction = 2;
				setHeadViewPaddingTop(deltaCount);
				setCircleViewStay();
				CircleAnimation.startCWAnimation(circle,
						5 * (deltaCount - grepDegree), 5 * deltaCount);
			}else if(getLastVisiblePosition() == mTotalItemCount - 1 && mFooterView.getBottomMargin() > 0 || deltaY < 0){
				System.out.println("lln---向下滑");
				direction = 1;//1代表向上2代表向下
				updateFooterHeight(-deltaY / OFFSET_RADIO);
				
			}
			break;
//			default:
//				mLastY = -1;
//				if(getLastVisiblePosition() == mTotalItemCount - 1){
//					if (mFooterView.getBottomMargin() > PULL_LOAD_MORE_DELTA){
//						System.out.println("lln---111");
//						startLoadMore();
//					}
////					resetFooterHeight();
//				}
//				
//				break;
		}

		return super.onTouchEvent(event);
	}
	
	private void updateFooterHeight(float delta) {
		int height = mFooterView.getBottomMargin() + (int) delta;
		if (!mPullLoading) {
			if (height > PULL_LOAD_MORE_DELTA) { // height enough to invoke load
				mFooterView.setState(XListViewFooter.STATE_READY);
			} else {
				mFooterView.setState(XListViewFooter.STATE_NORMAL);
			}
		}
		mFooterView.setBottomMargin(height);
	}

	private void startCircleAnimation() {
		CircleAnimation.startRotateAnimation(circle);

	}

	private class DismissCircleThread implements Runnable {
		private final int COUNT = 100;
		private final int deltaMargin;

		public DismissCircleThread() {
			deltaMargin = CircleMarginTop / COUNT;
		}

		@Override
		public void run() {
			int temp = 0;
			for (int i = 0; i <= COUNT; i++) {
				if (i == 100) {
					temp = 0;
				} else {
					temp = CircleMarginTop - deltaMargin * i;
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
				Message msg = Message.obtain();
				msg.what = DISMISS_CIRCLE;
				msg.arg1 = temp;
				handler.sendMessage(msg);
			}

		}
	}

	private void setCircleViewStay() {
		if (headView.getPaddingTop() > (CircleMarginTop)) {
			MarginLayoutParams lp = (MarginLayoutParams) circle
					.getLayoutParams();
			lp.topMargin = CircleMarginTop - headView.getPaddingTop();
			circle.setLayoutParams(lp);
		}
	}

	private void loadDataForThreeSecond() {
		currentState = LoadState.LOADSTATE_LOADING;
		data.add("New Data");
		Message msg = Message.obtain();
		msg.what = LOAD_DATA;
		handler.sendMessageDelayed(msg, 3000);
	}

	public void setAdapter(ArrayAdapter<String> adapter) {
		super.setAdapter(adapter);
		this.adapter = adapter;
	}
	
	private void startLoadMore() {
		mPullLoading = true;
		mFooterView.setState(XListViewFooter.STATE_LOADING);
		if (mListViewListener != null) {
			mListViewListener.onLoadMore();
		}
	}

	private void setHeadViewPaddingTop(int deltaY) {
		headView.setPadding(0, deltaY, 0, 0);
	}

	private void decreasePadding(int count) {
		Thread thread = new Thread(new DecreaseThread(count));
		thread.start();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		mTotalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		switch (scrollState) {
		case SCROLL_STATE_FLING:
			currentScrollState = SCROLL_STATE_FLING;
			break;
		case SCROLL_STATE_IDLE:
			currentScrollState = SCROLL_STATE_IDLE;
			break;
		case SCROLL_STATE_TOUCH_SCROLL:
			currentScrollState = SCROLL_STATE_TOUCH_SCROLL;
			break;
		}
	}
	public void setXListViewListener(IXListViewListener l) {
		mListViewListener = l;
	}
	public void stopLoadMore() {
		if (mPullLoading == true) {
			mPullLoading = false;
			mFooterView.setState(XListViewFooter.STATE_NORMAL);
		}
	}
	public interface IXListViewListener {
		public void onRefresh();
		public void onLoadMore();
	}
}
