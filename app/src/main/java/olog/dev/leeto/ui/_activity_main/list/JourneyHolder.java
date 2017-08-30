package olog.dev.leeto.ui._activity_main.list;

import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import olog.dev.leeto.R;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Location;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.ui.activity_detail.DetailActivity;
import olog.dev.leeto.utility.AppConstants;
import olog.dev.leeto.utility.DateUtils;

public class JourneyHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.journeyName) TextView journeyName;
    @BindView(R.id.journeyDate) TextView journeyDate;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.journeyDescription) TextView journeyDescription;
    @BindView(R.id.img) ImageView image;

    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;

    JourneyHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);

        constraintLayout = ((ConstraintLayout) itemView);
        constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
    }

    void bind(Journey journey, int position){
        if(position == RecyclerView.NO_POSITION) return;

        Stop firstStop = journey.getFirstStop();
        String date = DateUtils.toString(firstStop.getDate());

        journeyName.setText(journey.getName());
        journeyDate.setText(date);
        location.setText(firstStop.getLocation().getName());
        journeyDescription.setText(journey.getShortDescription());

        scrim.setTransitionName(DetailActivity.SHARED_ROOT + position);
        journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + position);

        constraintSet.setDimensionRatio(image.getId(), AppConstants.getRatio(position));
        constraintSet.applyTo(constraintLayout);

        Glide.with(itemView.getContext())
                .load(getCityImage(firstStop.getLocation()))
                .centerCrop()
                .error(R.color.dark_grey)
                .into(image);
    }

    @DrawableRes
    private int getCityImage(Location location){
        String city = location.getName();
        if(city.equals("Mosca")) return R.drawable.mosca;
        if(city.equals("Parma")) return R.drawable.parma;
        if(city.equals("Parigi")) return R.drawable.parigi;
        if(city.equals("New York")) return R.drawable.new_york;
        if(city.equals("Barcellona")) return R.drawable.barcellona;
        return -1;
    }

}
