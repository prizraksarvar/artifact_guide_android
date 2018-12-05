package com.sarvarcorp.artifact.listadapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sarvarcorp.artifact.App;
import com.sarvarcorp.artifact.R;
import com.sarvarcorp.artifact.base.BaseAppCompatActivity;
import com.sarvarcorp.artifact.entities.Guide;

import java.io.IOException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GuidesRecyclerViewAdapter extends RecyclerView.Adapter<GuidesRecyclerViewAdapter.GuideTypeListViewHolder> implements Observer<List<Guide>> {
    private LiveData<List<Guide>> mValues;

    private GuidesListListener listener;
    private BaseAppCompatActivity activity;

    public GuidesRecyclerViewAdapter(GuidesListListener listener, BaseAppCompatActivity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @NonNull
    @Override
    public GuideTypeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_guide_list_item, parent, false);
        return new GuideTypeListViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideTypeListViewHolder holder, int position) {
        if (mValues==null || mValues.getValue()==null)
            return;
        holder.setGuide(mValues.getValue().get(position),position);

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

    public void setValues(LiveData<List<Guide>> values) {
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
    public void onChanged(@Nullable List<Guide> products) {
        notifyDataSetChanged();
    }

    public class GuideTypeListViewHolder extends RecyclerView.ViewHolder {
        private final View view;
        private final TextView nameTextView;
        private final ImageView imageView;
        private Guide guide;
        private int position;
        okhttp3.Call c;

        public GuideTypeListViewHolder(View itemView, final GuidesListListener listener) {
            super(itemView);
            position=-1;
            view = itemView;
            nameTextView = (TextView) itemView.findViewById(R.id.guideNameTextView);
            imageView = (ImageView) itemView.findViewById(R.id.guideListImageView);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickListItem(view, guide);
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + guide.id + "'";
        }

        public void update() {
            nameTextView.setText(guide.name);

            ViewCompat.setTransitionName(nameTextView,"guideName"+guide.id);
            ViewCompat.setTransitionName(imageView, "guideImage"+guide.id);

            imageView.setImageResource(R.drawable.placeholder);

            if (!guide.image.equals("")) {
                Glide.with(App.getComponent().provideStaticData().getMainActivity())
                        .load(guide.image)
                        .into(imageView);
                //setImage(guide.image, imageView);
            }
        }

        private void setImage(String image, ImageView imageView) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(image)
                    .build();

            c = client.newCall(request);

            c.enqueue(new Callback() {
                @Override
                public void onFailure(okhttp3.Call call, IOException e) {

                }

                @Override
                public void onResponse(okhttp3.Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        Bitmap photoBitmap = BitmapFactory.decodeStream(response.body().byteStream());
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(photoBitmap);
                            }
                        });
                    }
                }
            });
        }

        public void setGuide(Guide guide, int position) {
            this.position = position;
            this.guide = guide;
            update();
        }
    }

    public interface GuidesListListener {
        public void onClickListItem(View view, Guide guide);
    }
}
