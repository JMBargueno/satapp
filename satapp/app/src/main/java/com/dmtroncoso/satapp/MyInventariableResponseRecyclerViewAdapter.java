/*
package com.dmtroncoso.satapp;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.dmtroncoso.satapp.retrofit.model.InventariableResponse;
import com.dmtroncoso.satapp.viewmodel.InventariableViewModel;

import java.util.ArrayList;
import java.util.List;

<<<<<<< HEAD
=======
*/
/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 *//*
>>>>>>> master

public class MyInventariableResponseRecyclerViewAdapter extends RecyclerView.Adapter<MyInventariableResponseRecyclerViewAdapter.ViewHolder> {

    private List<InventariableResponse> mValues;
    InventariableViewModel inventoriableViewModel;
    Context context;
    RecyclerView recyclerView;


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

            holder.inventariableName.setText(holder.mItem.getNombre());
            //holder.textViewInventariableListLocation.setText(holder.mItem);
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

        public ViewHolder(View view) {
            super(view);
            mView = view;
            inventariableName = view.findViewById(R.id.textViewInventariableOnListName);
            textViewInventariableListLocation = view.findViewById(R.id.textViewInventariableListLocation);

        }





    }
}
*/
