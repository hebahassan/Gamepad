
package gameBad_EDIT;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.gamepad.R;

public class SwipeAdapter extends BaseAdapter {

	// ListView list;
	public ArrayList<String> itemname = new ArrayList<String>();
    private Context mContext = null;
    public static View delete_left;

    /**
     * 
     */
    private int mRightWidth = 0;
    

    private IOnItemRightClickListener mListener = null;

    public interface IOnItemRightClickListener {
        void onRightClick(View v, int position);
    }

    /**
     * @param mainActivity
     */
    public SwipeAdapter(Context ctx, ArrayList<String> item,int rightWidth,IOnItemRightClickListener l) {
        mContext = ctx;
        mRightWidth = rightWidth;
        mListener = l;
        itemname = item;
    //    itemname = i;
    }

    @Override
    public int getCount() {
        return itemname.size() ;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }
    
    public void remove(int position) {

    	itemname.remove(position);
    }
    
    public void Hide(View position, Context context) {
        SwipeListView ob = new SwipeListView(context);
        ob.hiddenRight(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder item;
        final int thisPosition = position;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.layout_icontable, parent, false);
            item = new ViewHolder();
            item.item_left = (View)convertView.findViewById(R.id.item_left);
            item.item_right = (View)convertView.findViewById(R.id.item_right);
            item.item_left_txt = (TextView)convertView.findViewById(R.id.item_left_txt);
            item.item_right_txt = (TextView)convertView.findViewById(R.id.item_right_txt);
            convertView.setTag(item);
        } else {
            item = (ViewHolder)convertView.getTag();
        }
        LinearLayout.LayoutParams lp1 = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        item.item_left.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LayoutParams(mRightWidth, LayoutParams.MATCH_PARENT);
        item.item_right.setLayoutParams(lp2);
     //   Log.i("g "+itemname, "");
        item.item_left_txt.setText(""+itemname.get(thisPosition));
    //    item.item_right_txt.setText("delete " + thisPosition);
        item.item_right.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightClick(v, thisPosition);
                }
            }
        });
        return convertView;
    }

    private class ViewHolder {
        View item_left;

        View item_right;

        TextView item_left_txt;

        @SuppressWarnings("unused")
		TextView item_right_txt;
    }
}
