package net.softm.lib;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * TitleView
 * @author softm 
 */ 
public class TitleView implements OnClickListener  {
	protected Activity activity;

	
	public TitleView(Activity activity, String title,boolean button1, boolean button2) {
		logContentView(activity.getWindow().getDecorView(), "");
		ViewGroup group = getContentView(activity.getWindow().getDecorView());
		initLayout(activity, title, group,button1,button2);
		this.activity = activity;
	}

	public TitleView(Activity activity, int resId,boolean button1, boolean button2) {
		logContentView(activity.getWindow().getDecorView(), "");		
		ViewGroup group = getContentView(activity.getWindow().getDecorView());
		initLayout(activity, activity.getString(resId), group,button1,button2);
		this.activity = activity;
	}

	public TitleView(Activity activity, int resId) {
		this(activity, activity.getString(resId), false,false);
	}
	
	public TitleView(Activity activity, int resId, boolean button1) {
		this(activity, activity.getString(resId), button1,false);
	}
	
	public TitleView(Activity activity, String title, boolean button1) {
		this(activity, title, button1,false);
	}
	public TitleView(Activity activity, String title) {
		this(activity, title, false,false);
	}
	protected void initLayout(Activity activity, String title, ViewGroup group,boolean button1, boolean button2) {
		if ( this.activity == null ) {
//			group.setId(R.id.body);
//			activity.body = group;
			LayoutInflater layout = activity.getLayoutInflater();
			View view = layout.inflate(R.layout.layout_title_basic, null);
			TextView textView = (TextView)view.findViewById(R.id.title_text);
			ImageButton imgBtnTop0 = (ImageButton)view.findViewById(R.id.btn_top0);
			ImageButton imgBtnTop1 = (ImageButton)view.findViewById(R.id.btn_top1);
			ImageButton imgBtnTop2 = (ImageButton)view.findViewById(R.id.btn_top2);
			textView.setText(title);

//	      String slideShow = AppContext.getValue("slideShow");
//	      if ("Y".equals(slideShow)) {
//	    	  CompoundButton btnSlide = (CompoundButton)view.findViewById(R.id.btn_slide);
//	    	  btnSlide.setChecked(true);
//	      }
			//textView.setPaintFlags(textView.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG) ;
//		imgBtnTop0.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				TitleView.this.activity.finish();
//			}
//		});
			imgBtnTop0.setOnClickListener(this);
			if ( !button1 ) {
				imgBtnTop1.setVisibility(View.GONE);
			} else {
				imgBtnTop1.setOnClickListener(this);
			}
			
			if ( !button2 ) {
				imgBtnTop2.setVisibility(View.GONE);
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)imgBtnTop1.getLayoutParams();
				params.addRule(RelativeLayout.CENTER_IN_PARENT);
				params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				params.addRule(RelativeLayout.LEFT_OF, R.id.btn_top2);
				imgBtnTop1.setLayoutParams(params);
			} else {
				imgBtnTop2.setOnClickListener(this);
			}
//		imgBtnTop1.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				try {
//					Method m = TitleView.this.activity.getClass().getMethod("btnTop1Click", View.class);
//		            try {
//		                m.invoke(TitleView.this.activity,v);
//		                //m.invoke(o, new Object[]{});// 파라미터가 없을 때
//		            } catch (IllegalArgumentException e)
//		            {
//		                e.printStackTrace();
//		            } catch (IllegalAccessException e)
//		            {
//		                e.printStackTrace();
//		            } catch (InvocationTargetException e)
//		            {
//		                e.printStackTrace();
//		            }					
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
//				} // 파라미터가 없으면 (Class<?>[])null 로 넣으면 된다.
//			}
//		});
//		imgBtnTop2.setOnClickListener(new View.OnClickListener() {
//			public void onClick(View v) {
//				try {
//					Method m = TitleView.this.activity.getClass().getMethod("btnTop2Click", View.class);
//					try {
//						m.invoke(TitleView.this.activity,v);
//						//m.invoke(o, new Object[]{});// 파라미터가 없을 때
//					} catch (IllegalArgumentException e)
//					{
//						e.printStackTrace();
//					} catch (IllegalAccessException e)
//					{
//						e.printStackTrace();
//					} catch (InvocationTargetException e)
//					{
//						e.printStackTrace();
//					}					
//				} catch (NoSuchMethodException e) {
//					e.printStackTrace();
//				} // 파라미터가 없으면 (Class<?>[])null 로 넣으면 된다.
//			}
//		});
			group.addView(view, 0);
			ViewGroup.LayoutParams params0 = (ViewGroup.LayoutParams)view.getLayoutParams();
			params0.height = 98;
			view.setLayoutParams(params0);			
		}
	}
	
	protected void initLayoutNoButton(Activity activity, String title, ViewGroup group) {
		if ( this.activity == null ) {		
			LayoutInflater layout = activity.getLayoutInflater();
			View view = layout.inflate(R.layout.layout_title_basic, null);
			TextView textView = (TextView)view.findViewById(R.id.title_text);
			ImageButton imageButton = (ImageButton)view.findViewById(R.id.btn_top0);
			imageButton.setVisibility(ImageButton.GONE);
			textView.setText(title);
			//textView.setPaintFlags(textView.getPaintFlags()|Paint.FAKE_BOLD_TEXT_FLAG) ;
			imageButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					TitleView.this.activity.finish();
				}
			});
//			group.addView(view, 0);
			LinearLayout.LayoutParams params = (LinearLayout.LayoutParams)view.getLayoutParams();
			group.addView(view, 0,params);
		}
	}

	private void logContentView(View parent, String indent) {
//		Log.d(Constant.LOG_TAG, indent + parent.getClass().getName());
		if (parent instanceof ViewGroup) {
			ViewGroup group = (ViewGroup)parent;
			for (int i = 0; i < group.getChildCount(); i++)
				logContentView(group.getChildAt(i), indent + "  ");
		}
	}

	private ViewGroup getContentView(View parent) {
		ViewGroup view = null;
		if (parent instanceof ViewGroup) {
			ViewGroup group = (ViewGroup)parent;
			for (int i = 0; i < group.getChildCount(); i++) {
				if (group.getChildAt(i).getClass().equals(FrameLayout.class)) {
//					Log.d(Constant.LOG_TAG, "여기까지 왓따 : " + group.getChildAt(i).getClass().getName());					
					view = (ViewGroup) ((ViewGroup) group.getChildAt(i)).getChildAt(0);
					Log.d(Constant.LOG_TAG, "Find it : " + view.getClass().getName());					
					break;
				} else {
//					Log.d(Constant.LOG_TAG, "여기까지 왓!따 : " + group.getChildAt(i).getClass().getName());
					view = getContentView(group.getChildAt(i));
				}
			}
		}
//		
//		if (parent instanceof ViewGroup) {
//			ViewGroup group = (ViewGroup)parent;
////			if (group.getClass().equals(FrameLayout.class)) {
//			view = (ViewGroup)group.getChildAt(0);
////			}
////			else {
////				for (int i = 0; i < group.getChildCount(); i++) {
////					view = getContentView(group.getChildAt(i));
////				}
////			}
//		}
		return view;
	}

	public void setImageResource0(int resid) {
		ImageButton imgBtn = (ImageButton)activity.findViewById(R.id.btn_top0);
//		imgBtn.setBackgroundDrawable(activity.getResources().getDrawable(id));
		imgBtn.setBackgroundResource(resid);		
	}
	public void setImageResource1(int resid) {
		ImageButton imgBtn = (ImageButton)activity.findViewById(R.id.btn_top1);
//		imgBtn.setBackgroundDrawable(activity.getResources().getDrawable(id));
		imgBtn.setBackgroundResource(resid);		
	}
	
	public void setImageResource2(int resid) {
		ImageButton imgBtn = (ImageButton)activity.findViewById(R.id.btn_top2);
//		imgBtn.setImageDrawable(activity.getResources().getDrawable(id));
//		imgBtn.setBackgroundDrawable(activity.getResources().getDrawable(id));		
		imgBtn.setBackgroundResource(resid);
	}
	
	private OnTopClickListner topListner;
	public interface OnTopClickListner{
		public void onClickMainButton(View v);
		public void onClickOneButton(View v);
		public void onClickTwoButton(View v);
	}
	
    // interface
	public interface OnClickTitleText {
		void OnClickTitleText(View v);
	}

	private OnClickTitleText callbackOnClickTitleText = null;

	public void setOnClickTitleText(OnClickTitleText callback) {
		TextView textView = (TextView)activity.findViewById(R.id.title_text);
		textView.setOnClickListener(this);
		callbackOnClickTitleText = callback;
	}
    
    @Override
	public void onClick(View v) {
		int viewID = v.getId();
		if (topListner == null) return;
		if ( R.id.btn_top0 == viewID  ) {
			topListner.onClickMainButton(v);			
		} else if ( R.id.btn_top1 == viewID  ) {
			topListner.onClickOneButton(v);			
		} else if ( R.id.btn_top2 == viewID  ) {
			topListner.onClickTwoButton(v);
		} else if ( R.id.title_text == viewID ) {
			if( callbackOnClickTitleText != null ) {
				callbackOnClickTitleText.OnClickTitleText(v);
			}
		}
	}
	
	public void setOnTopClickListner(OnTopClickListner l){
		this.topListner = l;
	}
	
	public void setTitle( String title ) {
		TextView textView = (TextView)activity.findViewById(R.id.title_text);
		textView.setText( title );
	}
	
	public void setTitle( int id ) {
		String title = activity.getString(id);
		setTitle(title);
	}	
}


