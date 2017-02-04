package org.uk.cyberbyte.armaworks.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.uk.cyberbyte.armaworks.Api.ApiClient;
import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player.Player;
import org.uk.cyberbyte.armaworks.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayerFragment extends Fragment implements Validator.ValidationListener {

    protected static final String TAG = "AW_PlayerViewFragment";
    public static final String ARG_PLAYER = "player";
    private Player player;

    Validator validator;

    @NotEmpty
    private EditText bankEditText;

    @NotEmpty
    private EditText cashEditText;

    private Button saveButton;

    public PlayerFragment() {}

    public static PlayerFragment newInstance(Player player) {
        PlayerFragment fragment = new PlayerFragment();
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
        Log.d(TAG, "Created player fragment for " + player.getName());

        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_player_view, container, false);
        bankEditText = (EditText) view.findViewById(R.id.player_bank);
        cashEditText = (EditText) view.findViewById(R.id.player_cash);

        bankEditText.setText(player.getBankacc());
        cashEditText.setText(player.getCash());
        fillPlayerInfo(view);
        saveButton = (Button) view.findViewById(R.id.player_save);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validator.validate();
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bankEditText = null;
        cashEditText = null;
        saveButton = null;
    }

    @Override
    public void onValidationSucceeded() {
        Log.d(TAG, "Validation sucsessful");
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else {
                Log.e(TAG, "Validation failed"); //handle better
            }
        }
    }

    private void fillPlayerInfo(View view) {
        bankEditText = (EditText) view.findViewById(R.id.player_bank);
        cashEditText = (EditText) view.findViewById(R.id.player_cash);

        bankEditText.setText(player.getBankacc());
        cashEditText.setText(player.getCash());
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
