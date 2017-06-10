package com.itheima.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RatioLayout extends FrameLayout {
	// ���տ�߱���ȥ��ʾ
	private float ratio = 2.43f; // ����ֵ

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public RatioLayout(Context context) {
		super(context);
	}

	public RatioLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// ����1 �����ؼ� ����2 ���Ե����� ����3 Ĭ�ϵ�ֵ
		float ratio = attrs.getAttributeFloatValue(
				"http://schemas.android.com/apk/res/com.itheima.googleplay",
				"ratio", 2.43f);
		setRatio(ratio);
	}

	public RatioLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	// ������ǰ����
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec ��ȵĹ��� ������������ ģʽ ֵ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec); // ģʽ
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);// ��ȴ�С
		int width = widthSize - getPaddingLeft() - getPaddingRight();// ȥ���������ߵ�padding

		int heightMode = MeasureSpec.getMode(heightMeasureSpec); // ģʽ
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);// �߶ȴ�С
		int height = heightSize - getPaddingTop() - getPaddingBottom();// ȥ���������ߵ�padding

		if (widthMode == MeasureSpec.EXACTLY
				&& heightMode != MeasureSpec.EXACTLY) {
			// ����һ�� �߶ȵ�ֵ �ø߶�=���/����
			height = (int) (width / ratio + 0.5f); // ��֤4������
		} else if (widthMode != MeasureSpec.EXACTLY
				&& heightMode == MeasureSpec.EXACTLY) {
			// ���ڸ߶��Ǿ�ȷ��ֵ ,������Ÿ߶ȵı仯���仯
			width = (int) ((height * ratio) + 0.5f);
		}
		// �����������µĹ���
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				width + getPaddingLeft() + getPaddingRight());
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				height + getPaddingTop() + getPaddingBottom());

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
