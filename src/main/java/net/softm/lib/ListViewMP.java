package net.softm.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
/**
 * ListViewMP
 * @author softm 
 */
public class ListViewMP extends ListView {
    public ListViewMP(Context context) {
        super(context);
        initialise();
    }

    public ListViewMP(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialise();
    }

    public ListViewMP(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialise();
    }

    private void initialise() {
//        this.setBackgroundResource(com.entropykorea.gas.lib.R.drawable.top_bg);
//    	this.setSelector(R.drawable.listview_item_selector);
        setEnabled(true);
    }
}
