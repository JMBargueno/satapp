package com.dmtroncoso.satapp.inventariables;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.Constantes;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.common.SharedPreferencesManager;
import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;

import java.util.ArrayList;
import java.util.List;


import static com.dmtroncoso.satapp.common.Constants.UBICACION_DESCONOCIDA;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class MyInventariableResponseRecyclerViewAdapter extends RecyclerView.Adapter<MyInventariableResponseRecyclerViewAdapter.ViewHolder> {

    private List<InventariableResponse> mValues;
    InventariableViewModel inventoriableViewModel;
    Context context;
    RecyclerView recyclerView;
    SharedPreferencesManager sharedPreferencesManager;


    public MyInventariableResponseRecyclerViewAdapter(Context ctx, List<InventariableResponse> items, InventariableViewModel inventariableViewModel) {
       this.context = ctx;
       this.mValues = items;
       this.inventoriableViewModel = inventariableViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_inventariable, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null){
            holder.mItem = mValues.get(position);
            // TODO: hacemos uso del ViewModel


            holder.textViewInventariableListLocation.setText((holder.mItem.getUbicacion() != null)? holder.mItem.getUbicacion():UBICACION_DESCONOCIDA);

            holder.inventariableName.setText(holder.mItem.getNombre());

            if(holder.mItem.getImagen()!=null) {
             GlideUrl glideUrl = new GlideUrl(Constantes.URL_BASE + holder.mItem.getImagen()
                            ,new LazyHeaders.Builder()
                                    .addHeader("Authorization", "Bearer " + sharedPreferencesManager.getSomeStringValue("token"))
                                    .build());
                Glide
                        .with(MyApp.getContext())
                        .load(glideUrl)
                        .into(holder.ivFoto);
            }
            holder.ivMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inventoriableViewModel.openDialogInventariableMenu(context,holder.mItem.getId());
                }
            });

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != inventoriableViewModel) {
                        inventoriableViewModel.setIdInventoriableSeleccionado(holder.mItem.getId());
                    }
                }
            });
        }
    }

    public void setData(List<InventariableResponse> list){
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
        public InventariableResponse mItem;
        public TextView inventariableName, textViewInventariableListLocation;
        public ImageView ivFoto, ivMenu;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            inventariableName = view.findViewById(R.id.textViewInventariableOnListName);
            textViewInventariableListLocation = view.findViewById(R.id.textViewInventariableListLocation);
            ivFoto = view.findViewById(R.id.imageViewInventariableFoto);
            ivMenu = view.findViewById(R.id.imageViewInventariableMenu);

        }
    }

    public void setList(List<InventariableResponse> list) {
        this.mValues = list;
        notifyDataSetChanged();
    }

    public void addAll(List<InventariableResponse> newList) {
        int lastIndex = mValues.size() - 1;
        mValues.addAll(newList);
        notifyItemRangeInserted(lastIndex, newList.size());
    }
}

