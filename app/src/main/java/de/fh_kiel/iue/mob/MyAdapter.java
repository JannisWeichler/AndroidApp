package de.fh_kiel.iue.mob;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    public interface Listener{
        void itemClicked(int position);
    }

    private Listener mListener;
    private List<Stadt> mData;

    MyAdapter(List<Stadt> data, Listener aListener) {
        mData = data;
        mListener = aListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        TextView mTextView;

        MyViewHolder(@NonNull View itemView){
            super(itemView);

            mTextView = itemView.findViewById(R.id.list_item);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    mListener.itemClicked(pos);
                    ActivityMain.del = false;

                }
            });

        }

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item,parent,false);
        return new MyAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.MyViewHolder holder, int position){
        holder.mTextView.setText(mData.get(position).getStadtName());
    }

    @Override
    public int getItemCount() { return mData.size();}
}
