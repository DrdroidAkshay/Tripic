package com.tripic.tripic;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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
                final Intent intent;
                intent =  new Intent(context, NotificationDetailActivity.class);
                intent.putExtra("requeststatus",requeststatus);
                intent.putExtra("requestername",requestername);
                intent.putExtra("requesterphone",requestephone);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
        String phone = requestephone.replace(" ", "+");
        String loadimageurl = "http://fullmoonfilms.000webhostapp.com/Userprofilepics/" + phone + ".jpeg";
        loadimages(context,loadimageurl, viewHolder.profilepic);


    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView NotificationMsg;
        public Button viewdetailbtn;
        public LinearLayout notificationholder;
        public ImageView profilepic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            NotificationMsg=itemView.findViewById(R.id.notificationmsg_id);
            viewdetailbtn=itemView.findViewById(R.id.viewdetailbtn_id);
            notificationholder=itemView.findViewById(R.id.notificationholder_id);
            profilepic=itemView.findViewById(R.id.notificationpic_id);
        }
    }
    private void loadimages(Context context,String loadimageurl, ImageView profilepic) {

        Picasso.with(context).load(loadimageurl).error(R.drawable.profilepic).memoryPolicy(MemoryPolicy.NO_CACHE).networkPolicy(NetworkPolicy.NO_CACHE).into(profilepic, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {

            }
        });


    }

}
