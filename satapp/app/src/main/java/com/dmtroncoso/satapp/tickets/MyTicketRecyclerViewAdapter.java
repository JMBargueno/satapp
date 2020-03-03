package com.dmtroncoso.satapp.tickets;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.tickets.TicketFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyTicketRecyclerViewAdapter extends RecyclerView.Adapter<MyTicketRecyclerViewAdapter.ViewHolder> {

    private List<Ticket> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTicketRecyclerViewAdapter(List<Ticket> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_ticket, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(mValues != null){
            holder.mItem = mValues.get(position);

            holder.txtTitle.setText(holder.mItem.getTitulo());
            holder.txtDescription.setText(holder.mItem.getDescripcion());

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    Toast.makeText(MyApp.getContext(), holder.mItem.getId(), Toast.LENGTH_SHORT).show();
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void setData(List<Ticket> resultList){
        this.mValues = resultList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(mValues != null){
            return mValues.size();
        }else{
            return 0;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView txtTitle;
        public final TextView txtDescription;

        public Ticket mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            txtTitle = view.findViewById(R.id.textViewTitleTicket);
            txtDescription = view.findViewById(R.id.textViewDescriptionTicket);

        }

    }
}
