package com.dmtroncoso.satapp;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.retrofit.model.User;
import com.dmtroncoso.satapp.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import jp.wasabeef.glide.transformations.BitmapTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;
import okhttp3.ResponseBody;


public class MyUserResponseRecyclerViewAdapter extends RecyclerView.Adapter<MyUserResponseRecyclerViewAdapter.ViewHolder> {

    private List<User> mValues;
    private UserViewModel userViewModel;
    Context context;
    RecyclerView recyclerView;

    public MyUserResponseRecyclerViewAdapter(List<User> mValues, UserViewModel userViewModel, Context context) {
        this.mValues = mValues;
        this.userViewModel = userViewModel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_user_no_val_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.name.setText(holder.mItem.getName());
        holder.email.setText(holder.mItem.getEmail());
        holder.role.setText(holder.mItem.getRole());


        holder.bottomActionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if (holder.mItem.getPicture() != null) {
                userViewModel.getImgUsr(holder.mItem.getId()).observeForever(new Observer<ResponseBody>() {
                    @Override
                    public void onChanged(ResponseBody responseBody) {
                        Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                        Glide.with(context)
                                .load(bmp)
                                .circleCrop()
                                .into(holder.fotoPerfil);
                    }
                });

        }
        Glide .with(MyApp.getContext()).load(holder.mItem.getAvatar()).apply(RequestOptions.bitmapTransform(new CropCircleTransformation())).into(holder.fotoPerfil);



    }

    public void setData(List<User> list){
        if(this.mValues != null) {
            this.mValues.clear();
        } else {
            this.mValues =  new ArrayList<>();
        }
        this.mValues.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView fotoPerfil;
        public final TextView name;
        public final TextView email;
        public final TextView role;
        public final ImageView bottomActionMenu;

        public User mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            name = (TextView) view.findViewById(R.id.name);
            email = (TextView) view.findViewById(R.id.email);
            role = (TextView) view.findViewById(R.id.rol);
            fotoPerfil = (ImageView) view.findViewById(R.id.avatar);
            bottomActionMenu = (ImageView) view.findViewById(R.id.imageViewBottomActionMenu);
        }

    }
}
