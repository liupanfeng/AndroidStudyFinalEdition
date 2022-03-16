package com.example.libbase.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.libbase.FlowContent;
import com.example.libbase.R;
import com.example.libbase.inter.IFlowAdapter;
import com.example.libbase.view.FlowLayout;

import java.util.List;

/**
 * All rights Reserved, Designed By www.meishesdk.com
 *
 * @Author : LiuPanFeng
 * @CreateDate : 2021/12/21 16:36
 * @Description :
 * @Copyright : www.meishesdk.com Inc. All rights reserved.
 */
public class FlowAdapter implements IFlowAdapter {

    private OnFlowViewClickListener onFlowViewClickListener;
    private FlowLayout flowLayout;
    private Context mContext;

    public FlowAdapter(Context mContext,FlowLayout flowLayout) {
        this.flowLayout = flowLayout;
        this.mContext = mContext;
    }

    @Override
    public void setData(List<FlowContent> list, int background) {
        //往容器内添加TextView数据
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        if (flowLayout != null) {
            flowLayout.removeAllViews();
        }

        for (int i = 0; i < list.size(); i++) {
            TextView tv = new TextView(mContext);
            tv.setPadding(28, 10, 28, 10);
            tv.setText(list.get(i).getTitle());
            tv.setTag(R.id.tv_tag,list.get(i).getContent());
            tv.setMaxEms(10);
            tv.setSingleLine();
            tv.setBackgroundResource(background);
            tv.setLayoutParams(layoutParams);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onFlowViewClickListener!=null){
                        onFlowViewClickListener.onFlowClick(v, (String) v.getTag(R.id.tv_tag));
                    }
                }
            });
            flowLayout.addView(tv, layoutParams);
        }
    }



    public void setOnFlowViewClickListener(OnFlowViewClickListener onFlowViewClickListener) {
        this.onFlowViewClickListener = onFlowViewClickListener;
    }

    public interface OnFlowViewClickListener{
        void onFlowClick(View view,String content);
    }
}
