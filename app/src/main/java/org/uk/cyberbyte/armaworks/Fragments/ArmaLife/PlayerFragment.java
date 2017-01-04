package org.uk.cyberbyte.armaworks.Fragments.ArmaLife;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player;
import org.uk.cyberbyte.armaworks.Fragments.BaseFragment;
import org.uk.cyberbyte.armaworks.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PlayerFragment extends BaseFragment {

    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
//            recyclerView.setAdapter(new MyPlayerRecyclerViewAdapter(DummyContent.ITEMS, mListener));
        }
        return view;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Player player);
    }
}
