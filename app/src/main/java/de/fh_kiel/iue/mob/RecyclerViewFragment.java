package de.fh_kiel.iue.mob;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecyclerViewFragment extends Fragment {


    public RecyclerViewFragment() {
        // Required empty public constructor
    }

    private static class DataContainer{
        static ArrayList<String> initializeData(){
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i<99; i++){
                data.add("Stadt" + i);
            }
            return data;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootview = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = rootview.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(DataContainer.initializeData()));

        return rootview;
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
                    Intent intent = new Intent(getActivity(),DetailsActivity.class);
                    String input = mTextView.getText().toString();
                    intent.putExtra("Stadtname",input);
                    startActivity(intent);
                }
            });
        }

    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private ArrayList<String> mData;

        MyAdapter(ArrayList<String> data) {mData = data;}

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.list_item,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position){
            holder.mTextView.setText(mData.get(position));
        }

        @Override
        public int getItemCount() { return mData.size();}
    }

}
