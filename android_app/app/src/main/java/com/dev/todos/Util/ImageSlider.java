package com.dev.todos.Util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.PagerAdapter;

import com.dev.todos.R;

import java.util.ArrayList;
import java.util.List;

public class ImageSlider extends PagerAdapter {
    Context context;
    FragmentManager fragmentManager;
    LayoutInflater layoutInflater;
    List<String> productLists;
    int [] images;

    public ImageSlider(Context context, List<String> sliderImage) {
        this.context=context;
        // this.fragmentManager=fragmentManager;
        layoutInflater=LayoutInflater.from(context);
        this.productLists=sliderImage;
        this.images=images;
    }

    @Override
    public int getCount() {
        return productLists.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;
        try {
            view = layoutInflater.inflate(R.layout.item_slider_image, container, false);
            ImageView imageView = view.findViewById(R.id.productImage);
            UseMe.setImage(context, productLists.get(position), imageView);
            //imageView.setImageResource(images[position]);

            container.addView(view, 0);
        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }
}
