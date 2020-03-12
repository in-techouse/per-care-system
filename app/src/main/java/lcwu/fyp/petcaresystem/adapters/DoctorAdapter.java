package lcwu.fyp.petcaresystem.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import lcwu.fyp.petcaresystem.R;
import lcwu.fyp.petcaresystem.activities.FixAppointment;
import lcwu.fyp.petcaresystem.model.User;


public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DocHolder> {
    private List<User> data;
    Context context;

    public DoctorAdapter(Context c) {
        data = new ArrayList<>();
        context = c;
    }

    public void setData(List<User> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public DoctorAdapter.DocHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_doctor, parent, false);
        return new DoctorAdapter.DocHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull DoctorAdapter.DocHolder holder, int position) {
        final User doc = data.get(position);

        if (doc != null) {
            holder.doc_name.setText(doc.getFirstName() + " " + doc.getLastName());
            Log.e("adapter", "doc q is" + doc.getQualification());
            holder.doc_detail.setText(doc.getQualification());
            holder.doc_contact.setText(doc.getPhNo());
            if (doc.getImage() != null && doc.getImage().length() > 0) {
                Log.e("adapter", "image added");
                Glide.with(context).load(doc.getImage()).into(holder.docImage);
            } else {
                Log.e("adapter", "Image not  found");
            }

            holder.mainCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bundle bundle = new Bundle();
                    Intent in = new Intent(context, FixAppointment.class);
                    bundle.putSerializable("doc", doc);
                    in.putExtras(bundle);
                    context.startActivity(in);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class DocHolder extends RecyclerView.ViewHolder {
        CircleImageView docImage;
        TextView doc_detail, doc_name, doc_contact;
        CardView mainCard;

        DocHolder(@NonNull View itemView) {
            super(itemView);
            doc_name = itemView.findViewById(R.id.doc_name);
            doc_detail = itemView.findViewById(R.id.doc_detail);
            doc_contact = itemView.findViewById(R.id.doc_contact);
            docImage = itemView.findViewById(R.id.docImage);
            mainCard = itemView.findViewById(R.id.mainCard);
        }
    }
}
