package olog.dev.leeto.ui._activity_main.list;

import android.support.annotation.DrawableRes;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import butterknife.BindView;
import butterknife.ButterKnife;
import olog.dev.leeto.R;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Location;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.ui.activity_detail.DetailActivity;
import olog.dev.leeto.utility.AppConstants;

public class JourneyHolder extends RecyclerView.ViewHolder {

    private static final int OVERRIDE_WIDTH = 1000;

    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.journeyName) TextView journeyName;
    @BindView(R.id.journeyDate) TextView journeyDate;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.journeyDescription) TextView journeyDescription;
    @BindView(R.id.img) ImageView image;

    private ConstraintLayout constraintLayout;
    private ConstraintSet constraintSet;
    private RequestManager glide;

    JourneyHolder(View view) {
        super(view);
        this.glide = Glide.with(itemView.getContext());
        ButterKnife.bind(this, view);

        constraintLayout = ((ConstraintLayout) itemView);
        constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
    }

    void bind(Journey journey, int position){
        if(position == RecyclerView.NO_POSITION) return; // is animating

        Stop firstStop = journey.getFirstStop();
        String date = firstStop.getFormattedDate();

        journeyName.setText(journey.getName());
        journeyDate.setText(date);
        location.setText(firstStop.getLocation().getName());
        journeyDescription.setText(journey.getShortDescription());

        scrim.setTransitionName(DetailActivity.SHARED_ROOT + position);
        journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + position);

        String ratio = AppConstants.getRatio(position);

        constraintSet.setDimensionRatio(image.getId(), ratio);
        constraintSet.applyTo(constraintLayout);

        glide.load(getCityImage(firstStop.getLocation()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .override(OVERRIDE_WIDTH, getAspectRatioHeight(ratio))
                .error(R.color.dark_grey)
                .into(image);
    }

    @DrawableRes
    private int getCityImage(Location location){
        String city = location.getName();
        if(city.equals("Moscow")) return R.drawable.mosca;
        if(city.equals("Parma")) return R.drawable.parma;
        if(city.equals("Paris")) return R.drawable.parigi;
        if(city.equals("New York")) return R.drawable.new_york;
        if(city.equals("Barcellona")) return R.drawable.barcellona;
        return -1;
    }

    private int getAspectRatioHeight(String ratio){
        if(ratio.equals(AppConstants.RATIO_2_1)) return OVERRIDE_WIDTH / 2;
        if(ratio.equals(AppConstants.RATIO_16_9)) return OVERRIDE_WIDTH / 16 * 9;
        if(ratio.equals(AppConstants.RATIO_ONE_HALF)) return (int) (OVERRIDE_WIDTH / 1.5);
        if(ratio.equals(AppConstants.RATIO_SQUARE)) return OVERRIDE_WIDTH;
        return 0;
    }

}
