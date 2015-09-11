package com.example.like_viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

public class MyScrollView extends ViewGroup {
	private GestureDetector gestureDetector;
	private Context ctx;
	public boolean isFling;

	public MyScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.ctx = context;
		init();

	}

	private void init() {
		// TODO Auto-generated method stub
		// mjMyScroll = new MJMyScroll(ctx);
		duration = 500;
		mjMyScroll = new Scroller(ctx);
		gestureDetector = new GestureDetector(ctx, new gestureListener());

	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			gestureDetector.onTouchEvent(ev);
			firstX = (int) ev.getX();
			firstY = (int) ev.getY();
			break;
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_MOVE:
			int distanceX = Math.abs((int) (ev.getX() - firstX));
			int distanceY = Math.abs((int) (ev.getY() - firstY));
			if (distanceX > distanceY && distanceX > 10) {
				return true;
			}else {
				return false;
			}

		default:
			break;
		}
		return false;
	}

	private class gestureListener extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			scrollBy((int) distanceX, 0);

			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			isFling = true;
			if (velocityX > 0 && currState > 0) {
				currState--;
			} else if (velocityX < 0 && currState < getChildCount() - 1) {
				currState++;
			}
			flushState();
			return false;
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int size = MeasureSpec.getSize(widthMeasureSpec);
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.measure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	private int firstX=0;
	private int firstY=0;
	private int lastX=0;
	private boolean isDrag = false;
	private float slideBtn_left;
	private int currState = 0;
	// private MJMyScroll mjMyScroll;
	private Scroller mjMyScroll;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		super.onTouchEvent(event);
		gestureDetector.onTouchEvent(event);
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			firstX = lastX = (int) event.getX();
			break;
		case MotionEvent.ACTION_MOVE:

			// 判断是否发生拖动
			break;
		case MotionEvent.ACTION_UP:

			// 在发生拖动的情况下，根据最后的位置，判断当前开关的状态
			if (!isFling) {

				int maxLeft = getWidth(); // slideBtn
				// 左边届最大值
				/*
				 * 根据 slideBtn_left 判断，当前应是什么状态
				 */
				if (event.getX() - firstX < -maxLeft / 2) {
					if (currState == getChildCount() - 1) {
						currState = currState;
					} else {
						currState = currState + 1;
					}
				} else if (event.getX() - firstX > maxLeft / 2) {
					if (currState == 0) {
						currState = currState;

					} else {
						currState = currState - 1;
					}
				} else {
					currState = currState;
				}
				flushState();
			}
			isFling = false;
			break;
		}
		return true;
	}

	private void flushState() {
		// if (currState) {
		// slideBtn_left = getWidth();
		// } else {
		// slideBtn_left = 0;
		// }

		flushView();
	}

	private int duration;

	public void flushView() {
		/*
		 * 对 slideBtn_left 的值进行判断 ，确保其在合理的位置 即 0<=slideBtn_left <= maxLeft
		 */
		if (pageChangeListener != null) {
			pageChangeListener.changeRadio(currState);
		}
		int maxLeft = getWidth(); // slideBtn
									// 左边届最大值

		// 确保 slideBtn_left >= 0
		// slideBtn_left = (slideBtn_left > 0) ? slideBtn_left : 0;

		// 确保 slideBtn_left <=maxLeft
		// slideBtn_left = (slideBtn_left < maxLeft) ? slideBtn_left : maxLeft;

		/*
		 * 刷新当前视图 导致 执行onDraw执行
		 */
		// scrollTo((int) (currState * maxLeft), 0);
		int distance = (int) (currState * getWidth() - getScrollX());
		int startX = getScrollX();
		mjMyScroll.startScroll(startX, 0, distance, 0, duration);
		invalidate();
	}

	public void radioFlushView(int currState) {
		int distance = (int) (currState * getWidth() - getScrollX());
		int startX = getScrollX();
		mjMyScroll.startScroll(startX, 0, distance, 0, duration);
		invalidate();
	}

	@Override
	public void computeScroll() {
		// TODO Auto-generated method stub
		if (mjMyScroll.computeScrollOffset()) {
			// int newX = (int) mjMyScroll.getCurrentX();
			int newX = (int) mjMyScroll.getCurrX();
			scrollTo(newX, 0);
			invalidate();
		}
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		for (int i = 0; i < getChildCount(); i++) {
			View view = getChildAt(i);
			view.layout(i * getWidth(), 0, getWidth() + i * getWidth(),
					getHeight());
		}
	}

	private PageChangeListener pageChangeListener;

	public PageChangeListener getPageChangeListener() {
		return pageChangeListener;
	}

	public void setPageChangeListener(PageChangeListener pageChangeListener) {
		this.pageChangeListener = pageChangeListener;
	}

	public interface PageChangeListener {
		void changeRadio(int count);
	}

}
