package com.dmtroncoso.satapp.anotaciones;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.dmtroncoso.satapp.CalendarActivity;
import com.dmtroncoso.satapp.R;
import com.dmtroncoso.satapp.common.MyApp;
import com.dmtroncoso.satapp.data.anotaciones.AnotacionViewModel;
import com.dmtroncoso.satapp.retrofit.model.anotaciones.Notas;
import com.dmtroncoso.satapp.tickets.Anotaciones;

import java.util.Calendar;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class anotacionesFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    AnotacionViewModel anotacionViewModel;
    List<Anotaciones> listAnotaciones;
    MyanotacionesRecyclerViewAdapter adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public anotacionesFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static anotacionesFragment newInstance(int columnCount) {
        anotacionesFragment fragment = new anotacionesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        anotacionViewModel = new ViewModelProvider(getActivity()).get(AnotacionViewModel.class);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anotaciones_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new MyanotacionesRecyclerViewAdapter(listAnotaciones, mListener);
            recyclerView.setAdapter(adapter);

            loadAnotaciones();
        }
        return view;
    }

    public void loadAnotaciones(){
        anotacionViewModel.getAnotaciones().observe(getActivity(), new Observer<List<Anotaciones>>() {
            @Override
            public void onChanged(List<Anotaciones> anotaciones) {
                listAnotaciones = anotaciones;
                adapter.setData(listAnotaciones);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Anotaciones item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.ticket_detail_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Test ticket value");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
            case R.id.calendarEvent:
                /*Calendar beginTime = Calendar.getInstance();
                beginTime.set(2020, 2, 01, 18, 54);
                Calendar endTime = Calendar.getInstance();
                endTime.set(2020, 2, 01, 21, 01);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(CalendarContract.Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(CalendarContract.Events.TITLE, "Test")
                        .putExtra(CalendarContract.Events.DESCRIPTION, "Test class")
                        .putExtra(CalendarContract.Events.EVENT_LOCATION, "The Test")
                        .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, "myrows.contactme@gmail.com, jallamasalvarez@gmail.com, jmbarguenolopez@gmail.com, pablorodriguezr2000@gmail.com")
                        .putExtra(CalendarContract.Events.HAS_ALARM, true)
                        .putExtra(CalendarContract.Reminders.EVENT_ID, CalendarContract.Events._ID)
                        .putExtra(CalendarContract.Events.ALLOWED_REMINDERS, "METHOD_DEFAULT")
                        .putExtra(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
                        .putExtra(CalendarContract.Reminders.MINUTES,5);
                startActivity(intent);*/
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
