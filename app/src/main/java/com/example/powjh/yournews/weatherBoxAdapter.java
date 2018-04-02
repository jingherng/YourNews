package com.example.powjh.yournews;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.*;

class weatherBoxAdapter extends RecyclerView.Adapter<weatherBoxAdapter.ViewHolder>{

    private String[] iconIds;
    private String[] date;
    private String[] temp;

    public weatherBoxAdapter(String[] date, String[] iconIds, String[] temp){
        this.date = date; this.iconIds = iconIds; this.temp = temp;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;
        public ViewHolder(CardView v){
            super(v);
            cardView = v;
        }
    }

    @Override
    public weatherBoxAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.weatherbox, parent, false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        CardView cv = holder.cardView;
        ImageView imageView = cv.findViewById(R.id.weatherIcon);
        switch(iconIds[position]){
            case "01d":imageView.setImageResource(R.drawable.d01);break;
            case "02d":imageView.setImageResource(R.drawable.d02); break;
            case "03d":imageView.setImageResource(R.drawable.d03); break;
            case "04d":imageView.setImageResource(R.drawable.d04); break;
            case "09d":imageView.setImageResource(R.drawable.d09); break;
            case "10d":imageView.setImageResource(R.drawable.d10); break;
            case "11d":imageView.setImageResource(R.drawable.d11); break;
            case "13d":imageView.setImageResource(R.drawable.d13); break;
            case "50d":imageView.setImageResource(R.drawable.d50); break;
            case "01n":imageView.setImageResource(R.drawable.n01); break;
            case "02n":imageView.setImageResource(R.drawable.n02); break;
            case "03n":imageView.setImageResource(R.drawable.n03); break;
            case "04n":imageView.setImageResource(R.drawable.n04); break;
            case "09n":imageView.setImageResource(R.drawable.n09); break;
            case "10n":imageView.setImageResource(R.drawable.n10); break;
            case "11n":imageView.setImageResource(R.drawable.n11); break;
            case "13n":imageView.setImageResource(R.drawable.n13); break;
            case "50n":imageView.setImageResource(R.drawable.d50); break;
        }

        TextView dateText = cv.findViewById(R.id.date);
        TextView tempText = cv.findViewById(R.id.temp);
        dateText.setText(date[position]);
        tempText.setText(temp[position]);
    }

    @Override
    public int getItemCount(){
        return date.length;
    }

}
