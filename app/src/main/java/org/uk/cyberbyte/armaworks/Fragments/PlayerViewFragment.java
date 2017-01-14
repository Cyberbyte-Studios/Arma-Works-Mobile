package org.uk.cyberbyte.armaworks.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.uk.cyberbyte.armaworks.Api.ApiClient;
import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player.Player;
import org.uk.cyberbyte.armaworks.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerViewFragment extends Fragment {

    protected static final String TAG = "AW_PlayerViewFragment";
    public static final String ARG_PLAYER = "player";
    private Player player;

    public PlayerViewFragment() {}

    public static PlayerViewFragment newInstance(Player player) {
        PlayerViewFragment fragment = new PlayerViewFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PLAYER, player);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            player = getArguments().getParcelable(ARG_PLAYER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player_view, container, false);
    }

    private void updatePlayer(Player player) {
        Call<Player> call = ApiClient.get().updatePlayer(player.getUid(), player);
        call.enqueue(new Callback<Player>() {
            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                Log.e(TAG, "Unable to update player: " + t.getMessage(), t);
            }

            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                Log.d(TAG, "Successfully updated player");

            }
        });
    }
}
