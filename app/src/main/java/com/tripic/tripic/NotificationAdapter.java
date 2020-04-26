package com.tripic.tripic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {


    private List<Notificationitem> listitems;
    private Context context;


    public NotificationAdapter(List<Notificationitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.notification_item,viewGroup,false);
        return new NotificationAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder viewHolder, int i) {
        Notificationitem listitem=listitems.get(i);
        String requestername=listitem.getRequestername();
        String requestephone = listitem.getRequesterphone();
        String requeststatus = listitem.getRequeststatus();
        if(requeststatus.equals("notaccepted")) {
            viewHolder.NotificationMsg.setText(requestername + " has requested for your ride.");
            viewHolder.NotificationMsg.setTextColor(R.color.colorPrimary);
        }
        else{
            viewHolder.NotificationMsg.setText(requestername + " has accepted your ride request.");

        }
        viewHolder.notificationholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Toast.makeText(context,listitem.getUsername(),
//                        Toast.LENGTH_SHORT).show();
//
//                final Intent intent;
//                intent =  new Intent(context, Riderdetails.class);
////                intent.putExtra("profilename",listitem.getUsername());
////                intent.putExtra("profilefromto",listitem.getRidefrom()+" To "+listitem.getRideto());
//                intent.putExtra("requestername",requestername);
//                intent.putExtra("requesterphone",requestephone);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView NotificationMsg;
        public Button viewdetailbtn;
        public LinearLayout notificationholder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NotificationMsg=itemView.findViewById(R.id.notificationmsg_id);
            viewdetailbtn=itemView.findViewById(R.id.viewdetailbtn_id);
            notificationholder=itemView.findViewById(R.id.notificationholder_id);

        }
    }

}
