package org.uk.cyberbyte.armaworks.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.uk.cyberbyte.armaworks.Api.ApiClient;
import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player.Player;
import org.uk.cyberbyte.armaworks.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnPlayerListFragmentInteractionListener}
 * interface.
 */
public class PlayerListFragment extends Fragment {

    protected static final String TAG = "AW_PlayerFragment";

    private static final String ARG_PLAYERS = "players";
    private ArrayList<Player> players;
    private OnPlayerListFragmentInteractionListener mListener;
    private MyPlayerRecyclerViewAdapter recyclerAdapter;
    private RecyclerView recyclerView;

    public PlayerListFragment() {}

    @SuppressWarnings("unused")
    public static PlayerListFragment newInstance(ArrayList<Player> players) {
        PlayerListFragment fragment = new PlayerListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PLAYERS, players);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            players = getArguments().getParcelableArrayList(ARG_PLAYERS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getPlayers();

        View view = inflater.inflate(R.layout.fragment_player_list, container, false);
        if (view instanceof RecyclerView) {
            recyclerView = (RecyclerView) view;
            recyclerAdapter = new MyPlayerRecyclerViewAdapter(players, mListener);
            recyclerView.setAdapter(recyclerAdapter);
        }
        return view;
    }

    private void getPlayers() {
        Call<List<Player>> call = ApiClient.get().getPlayers();
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                Log.e(TAG, "Unable to get players: " + t.getMessage(), t);
            }

            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                Log.d(TAG, "Successfully got players");
                recyclerAdapter = new MyPlayerRecyclerViewAdapter(response.body(), mListener);
                recyclerView.setAdapter(recyclerAdapter);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPlayerListFragmentInteractionListener) {
            mListener = (OnPlayerListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnPlayerListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPlayerListFragmentInteractionListener {
        void onPlayerListFragmentInteraction(Player player);
    }
}
