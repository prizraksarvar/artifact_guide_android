package com.sarvarcorp.artifact.listadapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sarvarcorp.artifact.R;
import com.sarvarcorp.artifact.base.BaseAppCompatActivity;
import com.sarvarcorp.artifact.entities.GuideType;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

public class GuideTypesRecyclerViewAdapter extends RecyclerView.Adapter<GuideTypesRecyclerViewAdapter.GuideTypeListViewHolder> implements Observer<List<GuideType>> {
    private LiveData<List<GuideType>> mValues;

    private GuideTypesListListener listener;
    private BaseAppCompatActivity activity;

    public GuideTypesRecyclerViewAdapter(GuideTypesListListener listener, BaseAppCompatActivity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GuideTypeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_guide_type_list_item, parent, false);
        return new GuideTypeListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideTypeListViewHolder holder, int position) {
        if (mValues==null || mValues.getValue()==null)
            return;
        holder.setGuideType(mValues.getValue().get(position),position);

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

    public void setValues(LiveData<List<GuideType>> values) {
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
    public void onChanged(@Nullable List<GuideType> products) {
        notifyDataSetChanged();
    }

    public class GuideTypeListViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final ConstraintLayout buttonView;
        private final TextView nameView;
        private GuideType guideType;
        private int position;

        public GuideTypeListViewHolder(View itemView, final GuideTypesListListener listener) {
            super(itemView);
            position=-1;
            view = itemView;
            buttonView = (ConstraintLayout) itemView.findViewById(R.id.guideTypeButton);
            nameView = (TextView) itemView.findViewById(R.id.guideTypeNameTextView);

            buttonView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListItem(view, guideType);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + guideType.id + "'";
        }

        public void update() {
            nameView.setText(guideType.name);
            ViewCompat.setTransitionName(nameView,"quideTypeName"+guideType.id);
            ViewCompat.setTransitionName(buttonView,"quideTypeLayout"+guideType.id);
        }

        public void setGuideType(GuideType guideType, int position) {
            this.position = position;
            this.guideType = guideType;
            update();
        }
    }

    public interface GuideTypesListListener {
        public void onClickListItem(View view, GuideType guideType);
    }
}
