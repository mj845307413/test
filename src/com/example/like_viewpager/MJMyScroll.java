package com.example.like_viewpager;

import android.content.Context;
import android.os.SystemClock;

public class MJMyScroll {

	private int startX;
	private int startY;
	private int distanceX;
	private int distanceY;
	private long startTime;
	private boolean isFinish;

	public MJMyScroll(Context context) {
	}

	public void startScroll(int startX, int startY, int disX, int disY) {
		// TODO Auto-generated method stub
		this.startX = startX;
		this.startY = startY;
		this.distanceX = disX;
		this.distanceY = disY;
		this.startTime = SystemClock.uptimeMillis();

		this.isFinish = false;
	}

	private int duration = 500;
	private long currentX;
	private long currentY;

	public long getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public long getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public boolean computeScrollOffset() {
		if (isFinish) {
			return false;
		}
		long passTime = SystemClock.uptimeMillis() - startTime;
		if (passTime < duration) {
			currentX = startX + distanceX * passTime / duration;
			currentY = startY + distanceY * passTime / duration;
		} else {
			currentX = startX + distanceX;
			currentY = startY + distanceY;
			isFinish=true;
		}
		return true;
	}
}
