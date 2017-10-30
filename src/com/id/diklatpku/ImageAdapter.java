package com.id.diklatpku;

import com.id.diklatpku.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	private Context mContext;

	// array of integers for images IDs
	private Integer[] mImageIds = { R.drawable.kegiatanpertama,
			R.drawable.kegiatankedua, R.drawable.kegiatanketiga,
			R.drawable.kegiatanempat
	};

	// constructor
	public ImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return mImageIds.length;
	}

	@Override
	public Object getItem(int i) {
		return i;
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		ImageView imageView = new ImageView(mContext);

		imageView.setImageResource(mImageIds[i]);
		imageView.setLayoutParams(new Gallery.LayoutParams(
				ViewGroup.LayoutParams.FILL_PARENT,
				ViewGroup.LayoutParams.FILL_PARENT));
		return imageView;
	}

}