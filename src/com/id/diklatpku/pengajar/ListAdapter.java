package com.id.diklatpku.pengajar;

import com.id.diklatpku.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

//class ini digunakan untuk mengubah tampilan listview tidak seperti bawaan Android pada umumnya
public class ListAdapter extends ArrayAdapter<String> {
	private final Context context;
	private final String[] valuestitle;
	private final String[] valuessubtitle;
	private final String[] valuesimage;
	private final String[] valuesemail;
	private final String[] valuehp;
	private final String[] valuelink;

	public ListAdapter(Context context, String[] valuestitle,
			String[] valuessubtitle, String[] valuesemail, String[] valuehp,
			String[] valuesimage, String[] valuelink) {
		super(context, R.layout.list_info, valuestitle);
		this.context = context;
		this.valuestitle = valuestitle;
		this.valuessubtitle = valuessubtitle;
		this.valuesimage = valuesimage;
		this.valuesemail = valuesemail;
		this.valuehp = valuehp;
		this.valuelink = valuelink;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// tampilan listview di atur dalam listview_layout.xml yang berada dalam
		// res/layout
		View rowView = inflater.inflate(R.layout.list_info, parent, false);
		TextView title = (TextView) rowView.findViewById(R.id.id);
		TextView subtitle = (TextView) rowView.findViewById(R.id.tipe);
		// ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
		TextView email = (TextView) rowView.findViewById(R.id.email);
		TextView hp = (TextView) rowView.findViewById(R.id.hp);
		TextView link = (TextView) rowView.findViewById(R.id.link);

		title.setText(valuestitle[position]);
		subtitle.setText(valuessubtitle[position]);
		new DownloadImageTask((ImageView) rowView.findViewById(R.id.image_user))
				.execute(valuesimage[position]);
		email.setText(valuesemail[position]);
		hp.setText(valuehp[position]);
		link.setText(valuelink[position]);

		// imageView.setImageResource(valuesimage[position]);

		return rowView;
	}
}