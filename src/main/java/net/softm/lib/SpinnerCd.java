package net.softm.lib;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ResourceCursorAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;

import net.softm.lib.adapter.CodeAdapter;
import net.softm.lib.common.AsyncHttp;
import net.softm.lib.common.AsyncHttpParam;
import net.softm.lib.common.CallBack;
import net.softm.lib.dto.BaseDataDTO;
import net.softm.lib.dto.CodeDTO;
import net.softm.lib.dto.in.CodeDTOIn;
import net.softm.lib.dto.out.CodeDTOOut;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
/**
 * SpinnerCd
 * @author softm 
 */
@SuppressLint("AppCompatCustomView")
public class SpinnerCd extends Spinner {
	public boolean loaded = false;
	public SpinnerCd(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialise();
	}
    private void initialise() {
    	this.setBackgroundResource(R.drawable.dropdown_img);
//    	setPopupBackgroundResource(R.drawable.dropdown_panel);
		setEnabled(true);
	}
    private boolean orderByAsc = true;
    public void setOrderByAsc(boolean v) {
    	orderByAsc = v;
    }
    
    private float fontSize = -1;
    public void setFontSize(float f) {
    	fontSize = f;
    }
    private boolean dialogVisible = true;  
    public boolean getDialogVisible() {
    	return this.dialogVisible;
    }
   	public SpinnerCd setDialogVisible(boolean dialogVisible) {
    	this.dialogVisible = dialogVisible; 
    	return this;
    }
   	
    @Override
    public boolean performClick() {
       if(!getDialogVisible()) {
           return false;
       }
       else {
           return super.performClick();
       }
    }
    
    public void setDropDownViewResource(int resource) {
    	((ResourceCursorAdapter) this.getAdapter()).setDropDownViewResource(resource);
    }
    public boolean setValue(String v) {
        boolean found = false;
    	int count = this.getAdapter().getCount();
    	for ( int i=0;i<count;i++) {
    		CodeDTO cd = (CodeDTO) this.getAdapter().getItem(i);
			if ( cd.getCode().equals(v) ) {
				this.setSelection(i);
                found = true;
				break;
			}
    	}
        return found;
    }
    
    public void setSelected(int position) {
        if (this.getCount() > position) {
			this.setSelection(position);
        }
    }
    
    public int getSelected() {
    	return this.getSelectedItemPosition();
    }
    
    public CodeDTO getSelectedValue() {
    	CodeDTO rtn = new CodeDTO();    	
        if (this.getCount() > 0) {
        	rtn = (CodeDTO) this.getAdapter().getItem(super.getSelectedItemPosition());        	
            return rtn;
        } else {
            return rtn;
        }
    }
    public String getValue(int position) {
        String rtn = null;    	
        if (this.getCount() > 0) {
        	CodeDTO cd = (CodeDTO) this.getAdapter().getItem(position);
    		rtn = cd.getCode();
            return rtn;
        } else {
            return rtn;
        }
    }
    
    public String getValue() {
        return getValue(super.getSelectedItemPosition()); 
    }

    public String getText(int position) {
        String rtn = null;    	
        if (this.getCount() > 0) {
        	CodeDTO cd = (CodeDTO) this.getAdapter().getItem(position);
    		rtn = cd.getValue();
            return rtn;
        } else {
            return rtn;
        }
    }
    public String getText() {
        return getText(super.getSelectedItemPosition()); 
    }

	public void setGravity(int i) {
		this.setGravity(i);
	}
	public SpinnerCd getCode(JSONArray code) {
		return getCode(code,"");
	}
	public SpinnerCd getCode(JSONArray code,int resourceId) {
		return getCode(code,getResources().getString(resourceId));		
	}
	private Cursor c = null;
	public SpinnerCd getCode(JSONArray code,String prompt) {
		JSONObject resultObject;
		BaseDataDTO<CodeDTO> dto = new BaseDataDTO<CodeDTO>();
		ArrayList<CodeDTO> data = new ArrayList<CodeDTO>();
		try {
			resultObject = code.getJSONObject(0);
			if ( !"".equals(prompt) ) {
				data.add(new CodeDTO("",prompt));				
			}
			for (int idx = 0; idx < resultObject.length(); idx++) {
				data.add(new CodeDTO(resultObject.names().getString(idx),resultObject.getString(resultObject.names().getString(idx))));
			}
			dto.setData(data);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		DefaultApplication mApp = (DefaultApplication) this.getContext().getApplicationContext();
		Util.i("result data : " + dto.toString());
//		CodeAdapter adapter = new CodeAdapter(this.getContext(), LayoutInflater.from(this.getContext()),data,this.fontSize);
		CodeAdapter adapter = new CodeAdapter(this.getContext(), LayoutInflater.from(this.getContext()),data,this.fontSize);
		SpinnerCd.this.setAdapter(adapter);		
		return this;
	}

	public SpinnerCd getCode(LinkedHashMap<String, String> map) {
		return getCode(map,"");
	}
	public SpinnerCd getCode(LinkedHashMap<String, String> map,int resourceId) {
		return getCode(map,getResources().getString(resourceId));		
	}
	public SpinnerCd getCode(LinkedHashMap<String, String> map,String prompt) {
		String sql = "";
		int idx = -1;
		if ( !"".equals(prompt) ) {
			sql += "select "
				    + " 1 as _id ,"
				    + "'' as CD   ,"
				    + "'" + prompt + "' as CD_NM "
            ;
			idx = 1;
		}

		for(Entry<String, String> item:map.entrySet()) {
			if ( idx > -1 )
				sql += " UNION ";
			
			idx++;
			sql += "select "
			    + " " + (idx )+ " as _id ,"
			    + "'" + item.getKey()   + "' as CD   ,"
			    + "'" + item.getValue() + "' as CD_NM "
			;
		}
		if ( orderByAsc ) {
			sql += " ORDER BY CD ASC";
		}
		Log.d("MPGAS",sql);
		DefaultApplication mApp = (DefaultApplication) this.getContext().getApplicationContext();
//		SQLiteDatabase db = mApp.getDatabase();
//		if ( c!= null ) c.close();
//        c = db.rawQuery(sql, null);		
//		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
		String[] from = new String[] { "CD_NM" }; // 가져올 DB의 필드 이름
		int[] to = new int[] { android.R.id.text1 }; // 각각 대응되는 xml의 TextView의
//		SpinnerAdapter adapter = new SpinnerAdapter(
//				this.getContext(), // ListView의 context
//				android.R.layout.simple_list_item_1, // ListView의 Custom layout
//				c, // Item으로 사용할 DB의 Cursor
//				from, // DB 필드 이름
//				to // DB필드에 대응되는 xml TextView의 id
//				   , 0
//				   ,this.fontSize);
//		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
//		setAdapter(adapter);
		return this;
	}
	public SpinnerCd getCode(String code) {
		return getCode(code,"",null);
	}
	
	public SpinnerCd getCode(String code,int resourceId) {
		return getCode(code,getResources().getString(resourceId),null);
	}
	
	public SpinnerCd getCode(String code,int resourceId, CallBack cb) {
		return getCode(code,getResources().getString(resourceId),cb);
	}
	public SpinnerCd getCode(String code,String prompt,int resourceId, CallBack cb) {
		return getCode(code,"codeCommon",getResources().getString(resourceId),null,cb);		
	}
	public SpinnerCd getCode(String code,String prompt, CallBack cb) {
		return getCode(code,"codeCommon",prompt,null,cb);
	}
	
	public SpinnerCd getSysCode(String service,String p,String prompt, CallBack cb) {
		return getCode(p,service,prompt,null, cb);		
	}
	public SpinnerCd getSysCode(String service,String p,int resourceId, CallBack cb) {
		return getCode(p,service,getResources().getString(resourceId),null, cb);		
	}
	
	public SpinnerCd getDataCode(String service,String p,String prompt,String url, CallBack cb) {
		return getCode(p,service,prompt,url, cb);		
	}
	public SpinnerCd getDataCode(String service,String p,int resourceId, String url, CallBack cb) {
		return getCode(p,service,getResources().getString(resourceId),url, cb);		
	}
	
	public SpinnerCd getCode(String code,String service,final String prompt,String url, final CallBack cb) {
		DefaultApplication mApp = (DefaultApplication) this.getContext().getApplicationContext();
    	CodeDTOIn in = new CodeDTOIn();
    	CodeDTO data = new CodeDTO();
    		data.setCode(code);
		in.setData(data);
		Gson gson = new Gson();
		String inParams = ""; 
		inParams = gson.toJson(in);
		Util.i("in json : " + inParams);
		String serviceUrl = StringUtils.isEmpty(url)?Constant.SERVER_COMMON_URL:url;
		new AsyncHttp<CodeDTOOut>(this.getContext()) {
			@Override
			public void complete(CodeDTOOut result) {
				Util.i(result.getClass().getName() +" result : " + result);						
		        ArrayList<CodeDTO> data = result.getData();				
		        if (data != null && !"".equals(prompt) ) {
	        		data.add(0, new CodeDTO("", prompt));
		        }
	        	CodeAdapter adapter = new CodeAdapter(SpinnerCd.this.getContext(), LayoutInflater.from(this.getContext()),data,SpinnerCd.this.fontSize);
	        	SpinnerCd.this.setAdapter(adapter);
			}
			@Override
			public void callback(CodeDTOOut result) {
		    	final BaseActivity activity = (BaseActivity)SpinnerCd.this.getContext();												
//				activity.alert("끝나면 실행한다.");
		    	if ( cb != null ) {
		    		cb.complete(SpinnerCd.this);
		    	}
			}
//	 	}.execute(new AsyncHttpParam(Constant.SERVER_COMMON_URL,
		}.sync(new AsyncHttpParam(serviceUrl,
				new FormEncodingBuilder().add("serverKey", "data")
										 .add("p", code)
										 ,StringUtils.defaultString(service).equals("")?"codeCommon":service
										 ));
		return this;
	}
	
	public SpinnerCd getArrange(int fromV,int toV,String prompt) {
			String sql = "";
			int idx = -1;
			if ( !"".equals(prompt) ) {
				sql += "select "
					    + " 1 as _id ,"
					    + "'' as CD   ,"
					    + "'" + prompt + "' as CD_NM "
	            ;
				idx = 1;
			}
	
			for(int seq=fromV;seq<=toV; seq++) {
				if ( idx > -1 )
					sql += " UNION ";
				
				idx++;
				sql += "select "
				    + " " + (idx )+ " as _id ,"
				    + "'" + seq   + "' as CD   ,"
				    + "'" + seq + "' as CD_NM "
				;
			}
			Log.d("MPGAS",sql);
			DefaultApplication mApp = (DefaultApplication) this.getContext().getApplicationContext();
//			SQLiteDatabase db = mApp.getDatabase();
			if ( c!= null ) c.close();
//	        c = db.rawQuery(sql, null);		
	//		adapter.setDropDownViewResource(android.R.layout.select_dialog_item);
			String[] from = new String[] { "CD_NM" }; // 가져올 DB의 필드 이름
			int[] to = new int[] { android.R.id.text1 }; // 각각 대응되는 xml의 TextView의
//			SpinnerAdapter adapter = new SpinnerAdapter(
//					this.getContext(), // ListView의 context
//					android.R.layout.simple_list_item_1, // ListView의 Custom layout
//					c, // Item으로 사용할 DB의 Cursor
//					from, // DB 필드 이름
//					to // DB필드에 대응되는 xml TextView의 id
//					   , 0
//					   ,this.fontSize);
//			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);		
//			setAdapter(adapter);
			return this;
		}
	public SpinnerCd getArrange(int fromV,int toV) {
		return getArrange(fromV,toV,"");
	}
	public SpinnerCd getArrange(int fromV,int toV,int resourceId) {
		return getArrange(fromV,toV,getResources().getString(resourceId));		
	}
	
//	public class SpinnerAdapter extends SimpleCursorAdapter {
//	    private float fontSize = -1;
//	    public SpinnerAdapter(Context context, int layout, Cursor c,
//				String[] from, int[] to, int flags,float fontSize) {
//			super(context, layout, c, from, to, flags);
//			this.fontSize = fontSize;
//		}
//
//	    /**
//	     * 기본 스피너 View 정의
//	     */
//	    @Override
//	    public View getView(int position, View convertView, ViewGroup parent) {
//	        if (convertView == null) {
//	            LayoutInflater inflater = LayoutInflater.from(getContext());
//	            convertView = inflater.inflate(
//	                    android.R.layout.simple_spinner_item, parent, false);
//	        }
//            Cursor c = (Cursor)this.getItem(position);
//
//            	TextView tv = (TextView) convertView
//            			.findViewById(android.R.id.text1);
//                if ( position == 0 && "".equals(c.getString(1))  && "-선택-".equals(c.getString(2)) ) {
//                	tv.setText("");
//                } else {
//                	tv.setText(c.getString(2));
//                }
//            	tv.setTextColor(getResources().getColor(R.color.spnFontColor));
//            	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.fontSize==-1?getResources().getDimension(R.dimen.spnFontSize):this.fontSize);
//            	return convertView;
//	        
//	    }
//
//	    /**
//	     * 스피너 클릭시 보여지는 View의 정의
//	     */
//	    @Override
//	    public View getDropDownView(int position, View convertView,
//	            ViewGroup parent) {
//	 
//	        if (convertView == null) {
//	            LayoutInflater inflater = LayoutInflater.from(getContext());
//	            convertView = inflater.inflate(
//	                    android.R.layout.simple_spinner_dropdown_item, parent, false);
//	        }
//	 
//	        TextView tv = (TextView) convertView
//	                .findViewById(android.R.id.text1);
//            Cursor c = (Cursor)this.getItem(position);
//	        tv.setText(c.getString(2));
//	        tv.setTextColor(getResources().getColor(R.color.spnFontColor));
////	        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.fontSize==-1?getResources().getDimension(R.dimen.spnFontSize):this.fontSize);
//        	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.spnFontSize));	        
//	        return convertView;
//	    }
//	}
}
