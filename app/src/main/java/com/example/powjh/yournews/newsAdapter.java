package com.example.powjh.yournews;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class newsAdapter extends RecyclerView.Adapter<newsAdapter.ViewHolder>{
    public ArrayList<String> captions;
    public ArrayList<String> imageURL;
    private Listener listener;

    public static interface Listener {
        public void onClick(int position);
    }

    public newsAdapter(ArrayList<String> captions, ArrayList<String> imageURL){
        this.captions = captions;
        this.imageURL = imageURL;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public newsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_captioned_news,parent,false);
        return new ViewHolder(cv);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount(){
        return captions.size();
    }

    public void onBindViewHolder(ViewHolder holder, final int position){
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.news_image);
        TextView textView = (TextView) cardView.findViewById(R.id.new_captions);
        textView.setText(captions.get(position));
        try{Picasso.get().load(imageURL.get(position)).resize(2048, 1600)
                .onlyScaleDown().into(imageView);}catch (IllegalArgumentException e){
            Drawable drawable = cardView.getResources().getDrawable(R.drawable.imagenotfound);
            imageView.setImageDrawable(drawable);
        }

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }
}