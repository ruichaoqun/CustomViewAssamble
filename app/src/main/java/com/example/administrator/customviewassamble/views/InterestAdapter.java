package com.example.administrator.customviewassamble.views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InterestAdapter extends BaseAdapter {
    private List<TagInfo> list;
    private Context context;
    private boolean canSelect = true;
    private boolean isNoTips = false;
    private TipsClickListener tipsClickListener;

    public InterestAdapter(Context context) {
        this.context = context;
    }
    private HobbyItemView preSelectView;
    private int selectCount;


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final HobbyItemView itemView = new HobbyItemView(context);
        itemView.getHexagonView().setText(list.get(position).getTag());
        itemView.getImageView().setImageResource(list.get(position).getRes());
        if(list.get(position).getIsSelect() == 1){//选中
            itemView.getHexagonView().setVisibility(View.INVISIBLE);
            itemView.setStatus(1);
        }else{//未选中
            itemView.getHexagonView().setVisibility(View.VISIBLE);
            itemView.setStatus(0);
        }

        if(canSelect){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(itemView.getStatus() == 0){
                        if(selectCount == 10){
                            Toast.makeText(context,"最多选择10项兴趣",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        selectCount++;
                        itemView.setStatus(1);
                        itemView.getHexagonView().setVisibility(View.INVISIBLE);
                    }else{
                        selectCount--;
                        itemView.setStatus(0);
                        itemView.getHexagonView().setVisibility(View.VISIBLE);
                    }
                }
            });
        }else{
            if(!isNoTips){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(preSelectView!=itemView){
                            if(preSelectView != null){
                                preSelectView.getHexagonView().setVisibility(View.INVISIBLE);
                            }
                            preSelectView = itemView;
                            itemView.getHexagonView().setText("");
                            itemView.getHexagonView().setVisibility(View.VISIBLE);
                            if(tipsClickListener != null){
                                tipsClickListener.tipsClick(list.get(position).getTag(),itemView.getTop(),itemView.getLeft());
                            }
                        }else{
                            if(preSelectView != null&&preSelectView!=itemView){
                                preSelectView.getHexagonView().setVisibility(View.INVISIBLE);
                            }
                        }

                    }
                });
            }
        }


        return itemView;
    }

    public void setData(List<TagInfo> data) {
        if(list == null){
            list = new ArrayList<>();
        }else{
            list.clear();
        }
        if(!canSelect){
            for (int i = 0; i < data.size(); i++) {
                if(data.get(i).getIsSelect() == 0){//未选中
                    data.remove(i);
                    i--;
                }
            }
        }

        int count = 0;
        for (int i = 0; i < data.size(); i++) {
            if(data.get(i).getIsSelect() == 1){//未选中
                count++;
            }
        }
        selectCount = count;
        list.addAll(data);
    }

    public List<TagInfo> getList() {
        return list;
    }

    public void setChecked(boolean b){
        canSelect = b;
    }

    public void setNoTips(boolean b){
        isNoTips = b;
    }

    public void setTipsClickListener(TipsClickListener listener){
        tipsClickListener = listener;
    }

    public interface TipsClickListener{
        void tipsClick(String tag,int top,int left);
    }
}
