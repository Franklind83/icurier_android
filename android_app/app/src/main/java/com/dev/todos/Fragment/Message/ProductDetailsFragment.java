package com.dev.todos.Fragment.Message;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.todos.Adapter.ImageAdapter;
import com.dev.todos.Adapter.ImageAdapter2;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;
import com.dev.todos.Util.UseMe;
import com.dev.todos.databinding.FragmentProductDetailsBinding;
import com.ravindu1024.indicatorlib.ViewPagerIndicator;

import java.text.ParseException;

import static com.dev.todos.Util.UseMe.getDateFormat;


public class ProductDetailsFragment extends Fragment {

    FragmentProductDetailsBinding binding;
    double Total =0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_details, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle bundle = getArguments();

        if (UseMe.stringArrayList.size() > 0) {
            try {
                ViewPager viewPager = view.findViewById(R.id.viewPager);
                ViewPagerIndicator pagerIndicator = view.findViewById(R.id.pager_indicator);
                ImageAdapter2 adapter = new ImageAdapter2(getContext());
                viewPager.setAdapter(adapter);
                pagerIndicator.setPager(viewPager);
            }catch (Exception e){}

        }

        binding.txtOrigin.setText(bundle.getString(Keys.location_from));
        binding.txtDestination.setText(bundle.getString(Keys.location_to));
        binding.txtProductPrice.setText("$ "+bundle.getString(Keys.item_price));
        binding.txtReward.setText("$ "+bundle.getString(Keys.reward));
        binding.txtQunt.setText(bundle.getString(Keys.qunt));

        SpannableString content = new SpannableString(bundle.getString(Keys.productLink));
        content.setSpan(new UnderlineSpan(), 0, content.length(),0);
        binding.txtProductLink.setText(content);

        try {
            binding.txtDelvDate.setText(getDateFormat(bundle.getString(Keys.delivery_deadline)));
        } catch (ParseException e) {
            e.printStackTrace();
        }


        binding.txtDescription.setText(bundle.getString(Keys.article_comment));
        binding.txtProductName.setText(bundle.getString(Keys.article_name));
        try {
            binding.orderdateTxt.setText(getString(R.string.order_on_new)+getDateFormat(bundle.getString(Keys.date)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        binding.userNameTxt.setText(bundle.getString(Keys.name));

        UseMe.setImage(getActivity(),bundle.getString(Keys.profileImage),binding.imgProfile);

        setTotal(bundle.getString(Keys.reward),bundle.getString(Keys.item_price),bundle.getString(Keys.qunt));


        binding.txtProductLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = "https://" + binding.txtProductLink.getText().toString();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }

        });

        binding.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
    }

    private void setTotal(String string, String bundleString, String s) {
        double sum = 0;
        sum =  Double.parseDouble(bundleString) * Double.parseDouble(s);;
        Total = sum + Double.parseDouble(string);
        binding.txtOrder.setText("$ " +Total);

    }
}