package net.softm.lib.adapter;

import net.softm.lib.R;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
/**
 * SampleAdapter
 * @author softm
 *
 */
public class SampleAdapter extends CursorAdapter {
	public SampleAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        parent.setPadding(0, 0, 0, 0);
        View retView = inflater.inflate(R.layout.list_item_sample, parent, false);
        return retView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		int p = cursor.getPosition();			
        TextView tvCd = (TextView) view.findViewById(R.id.tv_cd);
        tvCd.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1))));
        TextView tvCdNm = (TextView) view.findViewById(R.id.tv_cd_nm);
        tvCdNm.setText(cursor.getString(cursor.getColumnIndex(cursor.getColumnName(2))));
        view.setBackgroundColor(context.getResources().getColor(p%2==0?R.color.listEvenRow:R.color.listOddRow));	        
	}
}