package com.sarvarcorp.artifactguidedemo.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarvarcorp.artifactguidedemo.R;
import com.sarvarcorp.artifactguidedemo.base.BaseAppCompatActivity;
import com.sarvarcorp.artifactguidedemo.entities.GuideType;
import com.sarvarcorp.artifactguidedemo.entities.UniversalItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class UniversalItemRecyclerViewAdapter
        extends RecyclerView.Adapter<UniversalItemRecyclerViewAdapter.UniversalItemListViewHolder>
        implements Observer<List<UniversalItem>> {
    private LiveData<List<UniversalItem>> mValues;

    private UniversalItemListListener listener;
    private BaseAppCompatActivity activity;

    public UniversalItemRecyclerViewAdapter(UniversalItemListListener listener, BaseAppCompatActivity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public UniversalItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_guide_type_list_item, parent, false);
        return new UniversalItemListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversalItemListViewHolder holder, int position) {
        if (mValues==null || mValues.getValue()==null)
            return;
        holder.setUniversalItem(mValues.getValue().get(position),position);

        /*holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });*/
    }

    public void setValues(LiveData<List<UniversalItem>> values) {
        mValues = values;
        mValues.observeForever(this);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValues!=null && mValues.getValue()!=null)
            return mValues.getValue().size();
        return 0;
    }

    @Override
    public void onChanged(@Nullable List<UniversalItem> products) {
        notifyDataSetChanged();
    }

    public class UniversalItemListViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ConstraintLayout buttonView;
        private final TextView nameView;
        private UniversalItem universalItem;
        private int position;

        public UniversalItemListViewHolder(View itemView, final UniversalItemListListener listener) {
            super(itemView);
            position=-1;
            view = itemView;
            buttonView = (ConstraintLayout) itemView.findViewById(R.id.guideTypeButton);
            nameView = (TextView) itemView.findViewById(R.id.guideTypeNameTextView);

            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListItem(view, universalItem);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + universalItem.id + "'";
        }

        public void update() {
            nameView.setText(universalItem.name);
            ViewCompat.setTransitionName(nameView,"quideTypeName"+ universalItem.id);
            ViewCompat.setTransitionName(buttonView,"quideTypeLayout"+ universalItem.id);
        }

        public void setUniversalItem(UniversalItem universalItem, int position) {
            this.position = position;
            this.universalItem = universalItem;
            update();
        }
    }

    public interface UniversalItemListListener {
        void onClickListItem(View view, UniversalItem universalItem);
    }
}
