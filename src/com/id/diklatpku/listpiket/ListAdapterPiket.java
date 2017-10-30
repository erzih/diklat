package com.id.diklatpku.listpiket;
import com.id.diklatpku.R;
import com.id.diklatpku.pengajar.DownloadImageTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

//class ini digunakan untuk mengubah tampilan listview tidak seperti bawaan Android pada umumnya
public class ListAdapterPiket extends ArrayAdapter<String> {
	private final Context context;
	private final String[] valuestitle;
	private final String[] valuessubtitle;
	private final String[] valuesimage;

	public ListAdapterPiket(Context context, String[] valuestitle,
			String[] valuessubtitle, String[] valuesimage) {
		super(context, R.layout.petugas_piket, valuestitle);
		this.context = context;
		this.valuestitle = valuestitle;
		this.valuessubtitle = valuessubtitle;
		this.valuesimage = valuesimage;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// tampilan listview di atur dalam listview_layout.xml yang berada dalam
		// res/layout
		View rowView = inflater.inflate(R.layout.petugas_piket, parent, false);
		TextView title = (TextView) rowView
				.findViewById(R.id.txt_nama_pikt_satu);
		TextView subtitle = (TextView) rowView
				.findViewById(R.id.txt_no_pkt_satu);

		title.setText(valuestitle[position]);
		subtitle.setText(valuessubtitle[position]);
		new DownloadImageTask(
				(ImageView) rowView.findViewById(R.id.imgae_pkt_satu))
				.execute(valuesimage[position]);

		Button button = (Button) rowView.findViewById(R.id.btn_cal_satu);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast.makeText(getContext(),
						"testing" + valuessubtitle[position],
						Toast.LENGTH_SHORT).show();

				// // Uri uri = Uri.parse("tel:" + valuessubtitle[position]);
				// // Intent i= new Intent(Intent.ACTION_DIAL, uri);
				//
				Intent phoneDialerIntent = new Intent(Intent.ACTION_DIAL);
				//
				phoneDialerIntent.setData(Uri.parse("tel:"
						+ valuessubtitle[position]));
				phoneDialerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(phoneDialerIntent);
				//
				// // Intent callIntent = new Intent(Intent.ACTION_CALL);
				// //
				// callIntent.setData(Uri.parse("tel:"+valuessubtitle[position]));
				// // context.startActivity(callIntent);
			}
		});

		return rowView;
	}
}