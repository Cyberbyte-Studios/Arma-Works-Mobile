package org.uk.cyberbyte.armaworks.Activities.ArmaLife;

import android.os.Bundle;
import android.util.Log;

import org.uk.cyberbyte.armaworks.Activities.MenuActivity;
import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player.Player;
import org.uk.cyberbyte.armaworks.Fragments.PlayerListFragment;
import org.uk.cyberbyte.armaworks.Fragments.PlayerFragment;
import org.uk.cyberbyte.armaworks.R;

public class ArmaLifeActivity extends MenuActivity implements PlayerListFragment.OnPlayerListFragmentInteractionListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_arma_life);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onPlayerListFragmentInteraction(Player player) {
        Log.d(TAG, "Clicked on player " + player.getName());

        Bundle args = new Bundle();
        args.putParcelable(PlayerFragment.ARG_PLAYER, player);
        switchFragment(new PlayerFragment(), R.id.content_arma_life, args);
    }
}
