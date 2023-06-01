package ucas.edu.android.mymail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.List;
import java.util.Random;

public class MailAdapter extends BaseAdapter {

    private List<EmailData> mEmailData;
    private Context mContext;

    public MailAdapter(Context mContext, List<EmailData> mEmailData) {
        this.mEmailData = mEmailData;
        this.mContext = mContext;
    }
    public void addItem(EmailData e){
        this.mEmailData.add(e);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEmailData.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmailData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MailViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.recyclerview_mail_item, parent, false);

            holder = new MailViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (MailViewHolder) convertView.getTag();
        }

        final EmailData email = mEmailData.get(position);

        holder.mIcon.setText(email.getmSender().substring(0, 1));
        holder.mSender.setText(email.getmSender());
        holder.mEmailTitle.setText(email.getmTitle());
        holder.mEmailDetails.setText(email.getmDetails());
        holder.mEmailTime.setText(email.getmTime());

        Random mRandom = new Random();
        final int color = Color.argb(255, mRandom.nextInt(256), mRandom.nextInt(256), mRandom.nextInt(256));
        ((GradientDrawable) holder.mIcon.getBackground()).setColor(color);

        holder.mFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageView favorite = (ImageView) view;
                if (favorite.getColorFilter() != null) {
                    favorite.clearColorFilter();
                } else {
                    favorite.setColorFilter(ContextCompat.getColor(mContext, R.color.colorOrange));
                }
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, DetailActivity.class);
                mIntent.putExtra("sender", email.getmSender());
                mIntent.putExtra("title", email.getmTitle());
                mIntent.putExtra("details", email.getmDetails());
                mIntent.putExtra("time", email.getmTime());
                mIntent.putExtra("icon", email.getmSender().substring(0, 1));
                mIntent.putExtra("colorIcon", color);
                mContext.startActivity(mIntent);
            }
        });

        return convertView;
    }
}
class MailViewHolder {
    TextView mIcon;
    TextView mSender;
    TextView mEmailTitle;
    TextView mEmailDetails;
    TextView mEmailTime;
    ImageView mFavorite;
    RelativeLayout mLayout;

    MailViewHolder(View itemView) {
        mIcon = itemView.findViewById(R.id.tvIcon);
        mSender = itemView.findViewById(R.id.tvEmailSender);
        mEmailTitle = itemView.findViewById(R.id.tvEmailTitle);
        mEmailDetails = itemView.findViewById(R.id.tvEmailDetails);
        mEmailTime = itemView.findViewById(R.id.tvEmailTime);
        mFavorite = itemView.findViewById(R.id.ivFavorite);
        mLayout = itemView.findViewById(R.id.layout);
    }
}

