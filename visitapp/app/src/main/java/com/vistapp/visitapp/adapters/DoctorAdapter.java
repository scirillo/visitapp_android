package com.vistapp.visitapp.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vistapp.visitapp.R;
import com.vistapp.visitapp.listeners.RecyclerOnClickListener;
import com.vistapp.visitapp.listeners.UpdateFragmentListener;
import com.vistapp.visitapp.model.DoctorModel;

import java.util.List;

/**
 * Created by Santiago Cirillo on 14/05/2017.
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {

    private final boolean showCheck;
    private List<DoctorModel> mValues;
    private RecyclerOnClickListener mListener;
    private boolean showButton;
    private UpdateFragmentListener fragmentListener;
    private Context context;

    public DoctorAdapter(List<DoctorModel> items, boolean showButton, boolean showCheck) {
        mValues = items;
        this.showButton = showButton;
        this.showCheck = showCheck;
    }

    public void setFragmentListener(UpdateFragmentListener fragmentListener) {
        this.fragmentListener = fragmentListener;
    }

    public List<DoctorModel> getValues() {
        return mValues;
    }

    public void remove(DoctorModel item) {
        if (mValues.contains(item)) {
            mValues.remove(item);
            this.notifyDataSetChanged();
        }
    }

    public void setOnClickListener(RecyclerOnClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.dashbard_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        DoctorModel mItem = mValues.get(position);
        holder.item = mItem;
        holder.mTitle.setText(mItem.getName());
        holder.mSubTitle1.setText(mItem.getDirection());
        holder.mSubTitle2.setText(mItem.getClinic());
        holder.mSubTitle3.setText(mItem.getNote());
        holder.mSubTitle4.setText(mItem.getTime());
        holder.mCheckbox.setChecked(false);
        holder.cv.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void add(DoctorModel doctorModel) {
        mValues.add(doctorModel);
        this.notifyDataSetChanged();
    }

    public void addAll(List<DoctorModel> doctorList) {
        if(doctorList!=null){
            mValues = doctorList;
            this.notifyDataSetChanged();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mTitle;
        private final TextView mSubTitle1;
        private final TextView mSubTitle2;
        private final TextView mSubTitle3;
        private final TextView mSubTitle4;
        private final Button mButton;
        private final CheckBox mCheckbox;
        private DoctorModel item;
        private final CardView cv;

        public ViewHolder(View view) {
            super(view);
            mTitle = (TextView) view.findViewById(R.id.item_title);
            mSubTitle1 = (TextView) view.findViewById(R.id.item_subtitle1);
            mSubTitle2 = (TextView) view.findViewById(R.id.item_subtitle2);
            mSubTitle3 = (TextView) view.findViewById(R.id.item_subtitle3);
            mSubTitle4 = (TextView) view.findViewById(R.id.item_subtitle4);
            mCheckbox = (CheckBox) view.findViewById(R.id.checkBox);
            cv = (CardView) view.findViewById(R.id.cv);
            mSubTitle1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fragmentListener.navigateToMaps(item);
                }
            });
            if(showCheck){
                mCheckbox.setVisibility(View.VISIBLE);
            }else mCheckbox.setVisibility(View.GONE);
            mCheckbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mCheckbox.isChecked()){
                        cv.setBackgroundColor(ContextCompat.getColor(context, R.color.card_selected));
                    }else{
                        cv.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    }
                    if (fragmentListener != null) fragmentListener.itemChecked(item, mCheckbox.isChecked());
                }
            });
            mButton = (Button) view.findViewById(R.id.btn_more_info);
            if (showButton) {
                mButton.setVisibility(View.VISIBLE);
            } else mButton.setVisibility(View.GONE);
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (fragmentListener != null) fragmentListener.updateAdapter(item);
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) mListener.onClick(v, getLayoutPosition());
                }
            });
        }
    }
}
