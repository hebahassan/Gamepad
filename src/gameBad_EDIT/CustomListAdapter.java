package gameBad_EDIT;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gamepad.R;
 
public class CustomListAdapter extends ArrayAdapter<String> {
 
 private final Activity context;
 private final String[] itemname;
 private final Integer[] imgid;
 
 public CustomListAdapter(Activity context, String[] itemname, Integer[] imgid) {
 super(context, R.layout.mylist, itemname);
 // TODO Auto-generated constructor stub
 
 this.context=context;
 this.itemname=itemname;
 this.imgid=imgid;
 }
 
 public View getView(int position,View view,ViewGroup parent) {
 LayoutInflater inflater=context.getLayoutInflater();
 View rowView=inflater.inflate(R.layout.mylist, null,true);
 
 TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
 ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);


 
 
 txtTitle.setText("Button: "+itemname[position]);
 imageView.setImageResource(imgid[position]);
 
 if(position==0){
	 txtTitle.setText("Select Button");
	 txtTitle.setTextColor(Color.RED);
	 imageView.setImageResource(0);
 }
 return rowView;
 
 };
}
