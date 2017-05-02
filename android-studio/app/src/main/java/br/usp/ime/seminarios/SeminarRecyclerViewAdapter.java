package br.usp.ime.seminarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class SeminarRecyclerViewAdapter extends RecyclerView.Adapter<SeminarRecyclerViewAdapter.CustomViewHolder> {
    private List<Seminar> seminarList;
    private Context mContext;

    public SeminarRecyclerViewAdapter(Context context, List<Seminar> seminarList) {
        this.seminarList = seminarList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seminar_row, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        Seminar seminar = seminarList.get(i);

        customViewHolder.title.setText(seminar.getTitle());
        customViewHolder.presenter.setText(seminar.getPresenter());
        customViewHolder.short_description.setText(seminar.getShort_description());
    }

    @Override
    public int getItemCount() {
        return (null != seminarList ? seminarList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView presenter;
        protected TextView short_description;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.seminar_title);
            this.presenter = (TextView) view.findViewById(R.id.presenter);
            this.short_description = (TextView) view.findViewById(R.id.short_description);
        }
    }
}