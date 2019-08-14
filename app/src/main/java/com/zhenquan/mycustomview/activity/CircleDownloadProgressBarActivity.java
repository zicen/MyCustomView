package com.zhenquan.mycustomview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.zhenquan.mycustomview.R;
import com.zhenquan.mycustomview.customview.CircleDownloadProgressBar;
import com.zhenquan.mycustomview.utils.Enums;

import java.util.ArrayList;
import java.util.List;

public class CircleDownloadProgressBarActivity extends AppCompatActivity {
    private RecyclerView mRecyclerCircleProgressBar;
    List<CircleDownloadProgressBar.Status> mShowData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_download_progress_bar);

        mRecyclerCircleProgressBar = (RecyclerView) findViewById(R.id.recycler_circle_progress_bar);
        mRecyclerCircleProgressBar.setLayoutManager(new LinearLayoutManager(this));
        for (int i = 0; i < 50; i++) {
            CircleDownloadProgressBar.Status random = Enums.random(CircleDownloadProgressBar.Status.class);
            mShowData.add(random);
        }
        mRecyclerCircleProgressBar.setAdapter(new DataAdapter());
    }

    final private class DataAdapter extends RecyclerView.Adapter<DataVH> {

        @Override
        public DataVH onCreateViewHolder(ViewGroup parent, int viewType) {
            return new DataVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_circle_progress_bar, parent, false));
        }

        @Override
        public void onBindViewHolder(DataVH holder, int position) {
            holder.title.setText(String.valueOf(position));
            holder.circleDownloadProgressBar.setStatus(mShowData.get(position));
        }

        @Override
        public int getItemCount() {
            return mShowData.size();
        }
    }

    final private class DataVH extends RecyclerView.ViewHolder {
        TextView title;
        CircleDownloadProgressBar circleDownloadProgressBar;

        DataVH(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt);
            circleDownloadProgressBar = itemView.findViewById(R.id.cdpb_status);
        }
    }
}
