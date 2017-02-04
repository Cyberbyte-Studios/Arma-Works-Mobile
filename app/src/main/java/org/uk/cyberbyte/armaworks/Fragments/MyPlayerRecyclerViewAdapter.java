package org.uk.cyberbyte.armaworks.Fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.uk.cyberbyte.armaworks.Api.Models.ArmaLife.Player.Player;
import org.uk.cyberbyte.armaworks.Fragments.PlayerListFragment.OnPlayerListFragmentInteractionListener;
import org.uk.cyberbyte.armaworks.R;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Player} and makes a call to the
 * specified {@link PlayerListFragment.OnPlayerListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyPlayerRecyclerViewAdapter extends RecyclerView.Adapter<MyPlayerRecyclerViewAdapter.ViewHolder> {

    private final List<Player> mValues;
    private final OnPlayerListFragmentInteractionListener mListener;

    public MyPlayerRecyclerViewAdapter(List<Player> items, PlayerListFragment.OnPlayerListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_player, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(mValues.get(position).getUid()));
        holder.mContentView.setText(mValues.get(position).getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onPlayerListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mValues == null) {
            return 0;
        }
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public Player mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
