package com.itheima.googleplay.holder;

import java.sql.Types;

import com.itheima.googleplay.R;
import com.itheima.googleplay.domain.AppInfo;
import com.itheima.googleplay.tools.UiUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.Animator.AnimatorListener;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class DetailDesHolder extends BaseHolder<AppInfo> implements OnClickListener {
	@ViewInject(R.id.des_content)
	private TextView des_content;
	@ViewInject(R.id.des_author)
	private TextView des_author;
	@ViewInject(R.id.des_arrow)
	private ImageView des_arrow;
	@ViewInject(R.id.des_layout)
	private RelativeLayout des_layout;
	
	@Override
	public View initView() {
		View view=UiUtils.inflate(R.layout.detail_des);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void refreshView(AppInfo data) {
		des_content.setText(data.getDes());
		des_author.setText("����:"+data.getAuthor());
		des_layout.setOnClickListener(this);
		
		//des_content ��ʼ�߶�7�еĸ߶�
		LayoutParams layoutParams = des_content.getLayoutParams();
		layoutParams.height=getShortMeasureHeight();
		des_content.setLayoutParams(layoutParams);
		des_arrow.setImageResource(R.drawable.arrow_down);
	}
	/**
	 * ��ȡ7�еĸ߶�
	 * @return
	 */
	public int getShortMeasureHeight(){
		// ����һ���µ�TextView ��������,��ò�Ҫ��֮ǰ��TextView���� �п���Ӱ����������ִ��
		TextView textView=new TextView(UiUtils.getContext());
		textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);//���������С14dp
		textView.setMaxLines(7);
		textView.setLines(7);// ǿ����7��
		int width=des_content.getMeasuredWidth(); // ��ʼ���
		
		int widthMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);
		int heightMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);
		textView.measure(widthMeasureSpec, heightMeasureSpec);
		return textView.getMeasuredHeight();
	}
	/**
	 * ��ȡTextView �Լ�����ĸ߶�
	 * @return
	 */
	public int getLongMeasureHeight(){
		int width=des_content.getMeasuredWidth(); // ��ʼ���
		des_content.getLayoutParams().height= ViewGroup.LayoutParams.WRAP_CONTENT;// �߶Ȱ�������
		
		
		int widthMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY, width);
		int heightMeasureSpec=MeasureSpec.makeMeasureSpec(MeasureSpec.AT_MOST, 1000);
		des_content.measure(widthMeasureSpec,heightMeasureSpec);//
		return des_content.getMeasuredHeight();
	}
	boolean flag;// trueչ���� false û��չ��
	@Override
	public void onClick(View v) {
		expand();
	}
	ScrollView scrollView;
//	scrollView.scrollTo(0, scrollView.getMeasuredHeight())
	/**
	 * ��ȡ�������ScollView
	 */
	public ScrollView getScrollView(View view){
		ViewParent parent = view.getParent();
		if(parent instanceof ViewGroup){
			ViewGroup group=(ViewGroup) parent;
			if(group instanceof ScrollView){
				return (ScrollView)group;
			}else{
				return getScrollView(group);
			}
			
		}else{
			return null;
		}
	}
	
	private void expand() {
		scrollView=getScrollView(des_layout);
		int startHeight;
		int targetHeight;
		if(!flag){
			flag=true;
			startHeight=getShortMeasureHeight();
			targetHeight=getLongMeasureHeight();
		}else{
			flag=false;
			startHeight=getLongMeasureHeight();
			targetHeight=getShortMeasureHeight();
		}
		final LayoutParams layoutParams = des_content.getLayoutParams();
		ValueAnimator animator=ValueAnimator.ofInt(startHeight,targetHeight);
		animator.addUpdateListener(new AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				int value=(Integer) animation.getAnimatedValue();
				layoutParams.height=value;
				des_content.setLayoutParams(layoutParams);
				scrollView.scrollTo(0, scrollView.getMeasuredHeight());// ��scrollView �ƶ���������
			}
		});
		animator.addListener(new AnimatorListener() {  // ��������ִ��
			//��������ʼִ�е�ʱ�����
			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void onAnimationRepeat(Animator arg0) {
				
			}
			@Override
			public void onAnimationEnd(Animator arg0) {
				if(flag){
					des_arrow.setImageResource(R.drawable.arrow_up);
				}else{
					des_arrow.setImageResource(R.drawable.arrow_down);
				}
			}
			@Override
			public void onAnimationCancel(Animator arg0) {
				
			}
		});
		animator.setDuration(500);//���ö�������ʱ��
		animator.start();
	}
}	
