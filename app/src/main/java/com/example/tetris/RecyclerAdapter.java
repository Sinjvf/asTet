package com.example.tetris;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sinjvf on 28.12.15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    
    private ArrayList<Spanned> sText;
    private ArrayList<Spanned> sDescript;
    private ArrayList<Spanned> sSection;

    private final Spanned blank;
    private int size;
    private int activityType;
    private static final int CONTENT_SECT_VIEW =1;
    private static final int CONTENT_VIEW =2;
    private static final int TEXT_VIEW =3;


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView sTextView;
        public TextView sDescriptView;
        public TextView sSectView;
        public TextView upView;
        public int viewtype;
        private ItemClickListener clickListener;

        public ViewHolder(View v, int viewType) {
            super(v);
            this.viewtype = viewType;
            switch (viewType) {
                case TEXT_VIEW:
                    sTextView = (TextView) v.findViewById(R.id.tv_recycler_text);
                    sDescriptView = (TextView) v.findViewById(R.id.tv_recycler_descript);
                    sSectView = (TextView) v.findViewById(R.id.tv_recycler_sect);
                    upView = (TextView) v.findViewById(R.id.tv_recycler_up);
                    if (activityType==Const.ABOUT_APP){
                        upView.setVisibility(View.INVISIBLE);
                    } else {
                        upView.setOnClickListener(this);
                        sTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    break;
                case CONTENT_SECT_VIEW:
                    sDescriptView = (TextView) v.findViewById(R.id.tv_recycler_descript1);
                    sSectView = (TextView) v.findViewById(R.id.tv_recycler_sect1);
                    sDescriptView.setOnClickListener(this);
                    sSectView.setOnClickListener(this);


                    break;
                default:
                    sDescriptView = (TextView) v.findViewById(R.id.tv_recycler_descript2);
                    sDescriptView.setOnClickListener(this);
                    break;
                }
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

        public void setViewtype(int viewtype) {
            this.viewtype = viewtype;
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

    }

    public interface ItemClickListener {
        public void onClick(View view, int position);
    }




    // Конструктор
    public RecyclerAdapter( ArrayList<Spanned> sSection,ArrayList<Spanned> sDescript, ArrayList<Spanned> sText,Spanned blank, int activityType) {
        this.sSection = sSection;
        this.sDescript = sDescript;
        this.sText = sText;
        size = sDescript.size();
        this.activityType = activityType;
        this.blank = blank;


    }


    @Override
    public int getItemViewType(int position) {
        if (position>=size| activityType==Const.ABOUT_APP){
            return TEXT_VIEW;
        }
        else{
            if(!sSection.get(position).equals(blank))
                return CONTENT_SECT_VIEW;
            else return CONTENT_VIEW;
        }

    }
    // Создает новые views (вызывается layout manager-ом)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View v;
        switch (viewType) {
            case TEXT_VIEW:
                v  = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_text_item, parent, false);
                return  new ViewHolder(v, TEXT_VIEW);
             case CONTENT_VIEW:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_content_item, parent, false);
                return new ViewHolder(v, CONTENT_VIEW);
             default:
                 v = LayoutInflater.from(parent.getContext())
                         .inflate(R.layout.recycler_content_sect_item, parent, false);
                 return new ViewHolder(v, CONTENT_SECT_VIEW);
        }

    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        switch (holder.viewtype) {
            case CONTENT_VIEW:
                holder.sDescriptView.setText("   "+sDescript.get(position));
                break;
            case CONTENT_SECT_VIEW:
                if (!sSection.get(position).equals(blank)) {
                    holder.sSectView.setText(sSection.get(position));
                } else holder.sSectView.setText(null);
                holder.sDescriptView.setText("   "+sDescript.get(position));
                break;
            default:
                if (activityType ==Const.ABOUT_RULE)
                    position = position-size;
                if (!sSection.get(position).equals(blank)) {
                    holder.sSectView.setText(sSection.get(position));
                } else holder.sSectView.setText(null);
                holder.sDescriptView.setText(sDescript.get(position));
                holder.sTextView.setText(sText.get(position));
                break;
        }

        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d(Const.LOG_TAG, "click pos=" + position);
            //    linearLayoutManager.scrollToPositionWithOffset(2, 20);



                //onBindViewHolder(new ViewHolder(view, TEXT_VIEW), position + size);
            }
        });

    }

    // Возвращает размер данных (вызывается layout manager-ом)
    @Override
    public int getItemCount() {
        switch (activityType) {
            case Const.ABOUT_APP:
                return size;
            default:
                return size * 2;
        }
    }



}
