package net.softm.lib.adapter;

import java.util.ArrayList;

import net.softm.lib.R;
import net.softm.lib.dto.CodeDTO;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
/**
 * CodeAdapter
 * @author softm
 */
public class CodeAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mInflater;
	ArrayList<CodeDTO> mData;
    private float fontSize = -1;
	public CodeAdapter(Context context, LayoutInflater inflater,ArrayList<CodeDTO> data,float fontSize) {
		this.mContext = context;
		this.mInflater = inflater;
		this.mData = data;
		this.fontSize = fontSize;
	}
	
	@Override
	public int getCount() {
		return mData==null?0:mData.size();
	}

	@Override
	public CodeDTO getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
    /**
     * 기본 스피너 View 정의
     */
	@Override
	public View getView(int p, View v, ViewGroup parent) {
		CodeDTO data = getItem(p);
		
        if (v == null) {
            v = mInflater.inflate(android.R.layout.simple_spinner_item, parent, false);
//            v.setTag(android.R.id.text1,(TextView)v.findViewById(android.R.id.text1));  //        
        }
//        TextView v1 = (TextView)v.getTag(android.R.id.text1);
        TextView v1 = (TextView)v.findViewById(android.R.id.text1);
        
        String s1 = null;
        String s2 = null;
		try {
			s1 = StringUtils.defaultString(data.getCode());
			s2 = StringUtils.defaultString(data.getValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
        v1.setText( s1 );
        if ( p == 0 && "".equals(s1)  && "-선택-".equals(s2) ) {
        	v1.setText("");
        } else {
        	v1.setText(s2);
        }
        
    	v1.setTextColor(mContext.getResources().getColor(R.color.spnFontColor));
    	v1.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.fontSize==-1?mContext.getResources().getDimension(R.dimen.spnFontSize):this.fontSize);
        return v;        
	}

	
    /**
     * 스피너 클릭시 보여지는 View의 정의
     */
    @Override
    public View getDropDownView(int p, View convertView,
            ViewGroup parent) {
    	CodeDTO data = getItem(p);
        if (convertView == null) {
        	convertView = mInflater.inflate(android.R.layout.simple_spinner_dropdown_item, parent, false);            
        }
 
        TextView tv = (TextView) convertView
                .findViewById(android.R.id.text1);
        tv.setText(data.getValue());
        tv.setTextColor(mContext.getResources().getColor(R.color.spnFontColor));
//        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, this.fontSize==-1?getResources().getDimension(R.dimen.spnFontSize):this.fontSize);
    	tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimension(R.dimen.spnFontSize));	        
        return convertView;
    }
}


