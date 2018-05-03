package com.abss.mommy_radio.version2.other;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.abss.mommy_radio.R;
import com.abss.mommy_radio.version2.login.WXLoginActivity;

import java.util.ArrayList;
import java.util.List;

public class PopupWindowController{
    
    //this is a click event method 
    public void justSampleInActivity(){
        
                new PopupWindowController(this).ShowPopWindow(view, new ArrayList<String>(sublevelsMap.keySet()), new WXLoginActivity.VSMethod() {
                    @Override
                    public void Method(String s) { // param: button text 
                        //如果与当前页面状态相同则直接返回
                        if(currentSubLevelId.equals(sublevelsMap.get(s)))return;
                        //否则 依据按钮文本做对应的更改
                        
                        
                    }
                });   
        
    }
    
    List<String>list;

    private Context context;
    public PopupWindowController(Context context){
        this.context=context;
    }

    public void ShowPopWindow(View anchor, List<String> list, WXLoginActivity.VSMethod clickCallBack){
        final PopupWindow window=new PopupWindow(context);
        window.setBackgroundDrawable(new ColorDrawable(0));
        View v=LayoutInflater.from(context).inflate(R.layout.popwindow1,null);
        HandleView(v,list,window,clickCallBack);
        window.setContentView(v);
        window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setOutsideTouchable(true);
        window.update();
        //Toast.makeText(context,"size:"+list.size(),Toast.LENGTH_SHORT).show();
        window.showAsDropDown(anchor,anchor.getWidth()/2-100,-list.size()*60-40-anchor.getHeight());
    }
    public void HandleView(final View view, List<String> list, final PopupWindow window, final WXLoginActivity.VSMethod clickCallBack){
        final RecyclerView recyclerView=(RecyclerView) (view.findViewById(R.id.resource_types));
        ResourceTypeAdapter adapter=new ResourceTypeAdapter(context,list);
        adapter.setCallBack(new IClickCallBack() {
            @Override
            public void onClick(Button resource_type_button) {

                int count=recyclerView.getAdapter().getItemCount();
                if(((ResourceTypeViewHolder)recyclerView.findViewHolderForAdapterPosition(count-1)).resource_type_button==resource_type_button){
                    view.setActivated(true);
                }else{
                    view.setActivated(false);
                }
                for(int i=0;i<count;i++) {
                    ResourceTypeViewHolder holder=(ResourceTypeViewHolder)recyclerView.findViewHolderForAdapterPosition(i);
                    holder.resource_type_button.setActivated(false);

                }
                resource_type_button.setActivated(true);

                clickCallBack.Method(resource_type_button.getText().toString());
                //window.dismiss();
            }

            @Override
            public void onLongClick(Button resource_type_button) {

            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));


    }


    public class ResourceTypeAdapter extends RecyclerView.Adapter<ResourceTypeViewHolder>{
        private Context context;
        private List<String> list=new ArrayList<String>();

        private IClickCallBack callBack;
        public void setCallBack(IClickCallBack callBack){
            this.callBack=callBack;
        }
        public ResourceTypeAdapter(Context context){
            this.context=context;

        }
        public ResourceTypeAdapter(Context context,List<String> list){
            this.context=context;
            Refresh(list);

        }
        public void Refresh(List<String> list){
            this.list.clear();
            this.list.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public ResourceTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ResourceTypeViewHolder(LayoutInflater.from(context).inflate(R.layout.resource_type_item,parent,false));
        }

        @Override
        public void onBindViewHolder(ResourceTypeViewHolder holder, final int position) {

            holder.resource_type_button.setText(list.get(position));
            holder.resource_type_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(callBack!=null){
                        callBack.onClick((Button)v);
                    }

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class ResourceTypeViewHolder extends RecyclerView.ViewHolder{
        public Button resource_type_button;
        public ResourceTypeViewHolder(View itemView) {
            super(itemView);
            resource_type_button=(Button)(itemView.findViewById(R.id.resource_type_button));
        }
    }

    public interface IClickCallBack{
        public void onClick(Button btn);
        public void onLongClick(Button btn);
    }
}
