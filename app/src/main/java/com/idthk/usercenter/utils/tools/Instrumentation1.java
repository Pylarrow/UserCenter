package com.idthk.usercenter.utils.tools;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import com.facebook.common.internal.Preconditions;


public class Instrumentation1 {
	private static final String TAG = "Instrumentation1";

	private static enum ImageRequestState {
		NOT_STARTED, STARTED, SUCCESS, FAILURE, CANCELLATION,
	}

	private final Paint mPaint;
	private final Rect mTextRect;
	private final View mView;

	private PerfListener mPerfListener;
	private long mStartTime;
	private String mTag;
	private long mFinishTime;
	private ImageRequestState mState;

	public Instrumentation1(View view) {
		mPaint = new Paint();
		mTextRect = new Rect();
		mView = view;
		mState = ImageRequestState.NOT_STARTED;
	}

	public void init(final String tag, final PerfListener perfListener) {
		mTag = Preconditions.checkNotNull(tag);
		mPerfListener = Preconditions.checkNotNull(perfListener);
	}

	public void onStart() {
		Preconditions.checkNotNull(mTag);
		Preconditions.checkNotNull(mPerfListener);
		if (mState == ImageRequestState.STARTED) {
			onCancellation();
		}
		mStartTime = System.currentTimeMillis();
		mFinishTime = 0;
		mPerfListener.reportStart();
		mState = ImageRequestState.STARTED;

	}

	public void onSuccess() {
		Preconditions.checkState(mState == ImageRequestState.STARTED);
		mState = ImageRequestState.SUCCESS;
		mFinishTime = System.currentTimeMillis();
		final long elapsedTime = mFinishTime - mStartTime;
		mPerfListener.reportSuccess(elapsedTime);

	}

	public void onFailure() {
		Preconditions.checkState(mState == ImageRequestState.STARTED);
		mState = ImageRequestState.FAILURE;
		mFinishTime = System.currentTimeMillis();
		final long elapsedTime = mFinishTime - mStartTime;
		mPerfListener.reportFailure(elapsedTime);

	}

	public void onCancellation() {
		if (mState != ImageRequestState.STARTED) {
			return;
		}
		mState = ImageRequestState.CANCELLATION;
		mFinishTime = System.currentTimeMillis();
		final long elapsedTime = mFinishTime - mStartTime;
		mPerfListener.reportCancellation(elapsedTime);

	}



	public void onDraw(final Canvas canvas) {
		mPaint.setColor(0xC0000000);
		mTextRect.set(0, 0, mView.getWidth(), 35);
		canvas.drawRect(mTextRect, mPaint);

		mPaint.setColor(Color.WHITE);
		canvas.drawText("[" + mTag + "]", 10, 15, mPaint);

		String message = "Not started";
		switch (mState) {
		case STARTED:
			message = "Loading...";
			break;
		case SUCCESS:
			message = "Loaded after " + (mFinishTime - mStartTime) + "ms";
			break;
		case FAILURE:
			message = "Failed after " + (mFinishTime - mStartTime) + "ms";
			break;
		case CANCELLATION:
			message = "Cancelled after " + (mFinishTime - mStartTime) + "ms";
			break;
		default:
			break;
		}
		canvas.drawText(message, 10, 30, mPaint);
	}
}
