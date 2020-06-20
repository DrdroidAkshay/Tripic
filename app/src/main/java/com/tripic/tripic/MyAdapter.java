package com.tripic.tripic;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {


    private List<Listitem> listitems;
    private Context context;


    public MyAdapter(List<Listitem> listitems, Context context) {
        this.listitems = listitems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override


    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            Listitem listitem=listitems.get(i);

            viewHolder.listitemusername.setText(listitem.getUsername());
            viewHolder.listitemdate.setText(listitem.getRidedate());
            viewHolder.listitemtime.setText(listitem.getRidetime());
            viewHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context,listitem.getUsername(),
                            Toast.LENGTH_SHORT).show();

                    final Intent intent;
                    intent =  new Intent(context, Riderdetails.class);
                    intent.putExtra("profilename",listitem.getUsername());
                    intent.putExtra("profilefromto",listitem.getRidefrom()+" To "+listitem.getRideto());
                    intent.putExtra("profiledate",listitem.getRidedate());
                    intent.putExtra("profiletime",listitem.getRidetime());
                    intent.putExtra("profilephone",listitem.getUserphone());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        String phone = listitem.getUserphone().replace(" ", "+");
        String loadimageurl = "http://fullmoonfilms.000webhostapp.com/Userprofilepics/" + phone + ".jpeg";
        loadimages(context,loadimageurl, viewHolder.listitemimage);

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{
        public TextView listitemusername,listitemdate,listitemtime,listitemprice;
        public LinearLayout linearLayout;
        public ImageView listitemimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            listitemusername=(TextView) itemView.findViewById(R.id.name_id);
            listitemdate=(TextView) itemView.findViewById(R.id.date_id);
            listitemtime=(TextView) itemView.findViewById(R.id.time_id);
            linearLayout=(LinearLayout) itemView.findViewById(R.id.cardviewlinearlayout_id);
            listitemimage=itemView.findViewById(R.id.pic_id);
            listitemprice=itemView.findViewById(R.id.price_id);

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
