package com.dev.todos.Fragment.ShopperNewOrder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.todos.Activity.BottomnavigationActivity;
import com.dev.todos.Adapter.ProductListAdapter;
import com.dev.todos.Fragment.ChangeFragment;
import com.dev.todos.Fragment.Home.TravellerFragment;
import com.dev.todos.R;
import com.dev.todos.Util.Keys;

import java.util.ArrayList;

import static com.dev.todos.Fragment.ShopperNewOrder.AddShopperFragment.checkorder;


public class ShopperProductListFragment extends Fragment {

    RecyclerView recyclerView;
    ImageView imgBack,btnDiscard,imgAdd;
          TextView txtTotal;
    Button btnFollow;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopper_product_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.rv);
        imgBack = view.findViewById(R.id.imgBack);
        imgAdd = view.findViewById(R.id.imgAdd);
        txtTotal = view.findViewById(R.id.txtTotal);
        btnFollow = view.findViewById(R.id.btnFollow);
        btnDiscard = view.findViewById(R.id.btnDiscard);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ProductListAdapter(getActivity(), BottomnavigationActivity.productDetailArrayList));

        Bundle bundle = new Bundle();
        bundle = getArguments();
        txtTotal.setText("TOTAL: "+bundle.getString("Total"));

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkorder = true;
                new ChangeFragment(getActivity()).changeFragmentWithBackStack(getFragmentManager(), null, R.id.frameLayoout,
                        new AddShopperFragment());
            }
        });

        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });



        btnDiscard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDiscardDialog();
            }
        });


    }

    public void showDiscardDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getString(R.string.do_you_want_to_discart));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BottomnavigationActivity.productDetailArrayList = new ArrayList<>();
                new ChangeFragment(getActivity()).changeFragmentWithoutBackStack(getFragmentManager(),
                        null, R.id.frameLayoout, new TravellerFragment());
            }
        });
        builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void  onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();


    }


}