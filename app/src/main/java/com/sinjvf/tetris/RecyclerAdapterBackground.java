package com.sinjvf.tetris;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by sinjvf on 28.12.15.
 */
public class RecyclerAdapterBackground extends RecyclerView.Adapter<RecyclerAdapterBackground.ViewHolder> {

    private  int[] resourses;
    private ItemClickListener clickListener;

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public ImageButton imageButton1;
        public ImageButton imageButton2;
        public ImageButton imageButton3;

        public ViewHolder(View v) {
            super(v);
            imageButton1 = (ImageButton) v.findViewById(R.id.ib_recycler_1);
            imageButton2 = (ImageButton) v.findViewById(R.id.ib_recycler_2);
            imageButton3 = (ImageButton) v.findViewById(R.id.ib_recycler_3);
            imageButton1.setOnClickListener(this);
            imageButton2.setOnClickListener(this);
            imageButton3.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            clickListener.onImageClick(v, getAdapterPosition());
        }

    }


    public RecyclerAdapterBackground(int[]resourses ) {
        this.resourses = resourses;
    }


    public void setMyClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface ItemClickListener {
        public void onImageClick(View view, int position);
    }


    // Создает новые views (вызывается layout manager-ом)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v;
        v  = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_background_item, parent, false);
        return  new ViewHolder(v);
    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.imageButton1.setImageResource(resourses[position*3]);
        holder.imageButton2.setImageResource(resourses[position*3+1]);
        holder.imageButton3.setImageResource(resourses[position * 3 + 2]);
     /**   holder.setClickListener(new ItemClickListener() {
            @Override
            public void onImageClick(View view, int position) {
                int pos = position*3;
                switch (view.getId()) {
                    case R.id.ib_recycler_1:
                        pos =position * 3;
                        break;
                    case R.id.ib_recycler_2:
                        pos = position * 3 + 1;
                        break;
                    case R.id.ib_recycler_3:
                        pos = position * 3 + 2;
                        break;
                }
               // return pos;
            }
        });
**/
    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        return resourses.length/3;
    }



}
