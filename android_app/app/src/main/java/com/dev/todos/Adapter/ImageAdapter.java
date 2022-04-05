package com.dev.todos.Adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;


import com.dev.todos.R;
import com.dev.todos.Util.UseMe;

import java.util.ArrayList;

public class ImageAdapter extends PagerAdapter {
    Context context;

    ArrayList<Uri> uriArrayList = new ArrayList<>();

    LayoutInflater mLayoutInflater;

    public ImageAdapter(Context context) {
        this.context = context;
        this.uriArrayList.clear();
        this.uriArrayList = UseMe.uriArrayList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return uriArrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_image_item, container, false);

        Log.w("sizeofuriarr", "" + uriArrayList.size());
        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

            imageView.setImageURI(uriArrayList.get(position));



        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
