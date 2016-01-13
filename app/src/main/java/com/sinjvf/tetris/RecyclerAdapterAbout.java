package com.sinjvf.tetris;

import android.support.v7.widget.LinearLayoutManager;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by sinjvf on 28.12.15.
 */
public class RecyclerAdapterAbout extends RecyclerView.Adapter<RecyclerAdapterAbout.ViewHolder> {
    
    private ArrayList<Spanned> sText;
    private ArrayList<Spanned> sDescript;
    private ArrayList<Spanned> sSection;

    private final Spanned blank;
    private int size;
    private int activityType;
    private static final int CONTENT_SECT_VIEW =1;
    private static final int CONTENT_VIEW =2;
    private static final int TEXT_VIEW =3;
    private static final int TEXT_WITH_PICT =4;
    private  LinearLayoutManager llm;
    private final int withPict = 3;
    private float dim;


    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView sTextView;
        public TextView sDescriptView;
        public TextView sSectView;
        public TextView upView;
        public LinearLayout llview;
        public int viewtype;
        private ItemClickListener clickListener;

        public ViewHolder(View v, int viewType) {
            super(v);
            this.viewtype = viewType;

            sTextView = (TextView) v.findViewById(R.id.tv_recycler_text);
            sDescriptView = (TextView) v.findViewById(R.id.tv_recycler_descript);


            switch (viewType) {
                case TEXT_WITH_PICT:
                    llview = (LinearLayout)v.findViewById(R.id.tv_recycler_ll);
                    ImageView iw = new ImageView(llview.getContext());
                    iw.setImageResource(R.drawable.through);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
                            (LinearLayout.LayoutParams.WRAP_CONTENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT, 0);
                    iw.setLayoutParams(params);
                    llview.addView(iw);
                case TEXT_VIEW:
                    upView = (TextView) v.findViewById(R.id.tv_recycler_up);
                    if (activityType==Const.ABOUT_APP){
                        upView.setVisibility(View.INVISIBLE);
                    } else {
                        upView.setOnClickListener(this);
                        sTextView.setMovementMethod(LinkMovementMethod.getInstance());
                    }
                    sSectView = (TextView) v.findViewById(R.id.tv_recycler_sect);
                    break;
                case CONTENT_SECT_VIEW:
                    llview = (LinearLayout)v.findViewById(R.id.ll_for_recycler);
                    sSectView = new TextView(llview.getContext());//(TextView) v.findViewById(R.id.tv_recycler_sect);
                    sSectView.setTextSize(TypedValue.COMPLEX_UNIT_PX,dim);
                    Log.d(Const.LOG_TAG, "dim size=" + dim);
                    sSectView.setClickable(true);
                    llview.addView(sSectView);
                    sSectView.setOnClickListener(this);

                default:
                    sDescriptView.setOnClickListener(this);

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


    public RecyclerAdapterAbout(ArrayList<Spanned> sSection, ArrayList<Spanned> sDescript,
                                ArrayList<Spanned> sText, Spanned blank, int activityType,
                                LinearLayoutManager llm,  float dim) {
        this.sSection = sSection;
        this.sDescript = sDescript;
        this.sText = sText;
        size = sDescript.size();
        this.activityType = activityType;
        this.blank = blank;
        this.llm =llm;
        this.dim = dim;

    }


    @Override
    public int getItemViewType(int position) {
        if (position>=size| activityType==Const.ABOUT_APP){
            if(position==withPict+size)
                return TEXT_WITH_PICT;
            else
                return TEXT_VIEW;
        }
        else{
            if(!sSection.get(position).equals(blank))
                return CONTENT_SECT_VIEW;
            else
                return CONTENT_VIEW;
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
            case TEXT_WITH_PICT:
                v  = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_text_item, parent, false);
                return  new ViewHolder(v, TEXT_WITH_PICT);
            case CONTENT_VIEW:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_content_item, parent, false);
                return new ViewHolder(v, CONTENT_VIEW);
            default:
                v = LayoutInflater.from(parent.getContext())
                         .inflate(R.layout.recycler_content_item, parent, false);
                 return new ViewHolder(v, CONTENT_SECT_VIEW);

        }



    }

    // Заменяет контент отдельного view (вызывается layout manager-ом)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        switch (holder.viewtype) {
            case CONTENT_SECT_VIEW:
                if (!sSection.get(position).equals(blank)) {
                    holder.sSectView.setText(sSection.get(position));
                } else holder.sSectView.setText(null);

            case CONTENT_VIEW:
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
                int toPosition;
                if (position<size)
                    toPosition=position+size;
                else
                    toPosition=0;
                Log.d(Const.LOG_TAG, "click pos=" + position);
                llm.scrollToPositionWithOffset(toPosition, 0);
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
