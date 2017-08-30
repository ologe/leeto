package olog.dev.leeto.ui._activity_main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import olog.dev.leeto.R;
import olog.dev.leeto.data.model.Journey;
import olog.dev.leeto.data.model.Stop;
import olog.dev.leeto.utility.DateUtils;

public class JourneyHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.scrim) View scrim;
    @BindView(R.id.journeyName) TextView journeyName;
    @BindView(R.id.journeyDate) TextView journeyDate;
    @BindView(R.id.location) TextView location;
    @BindView(R.id.journeyDescription) TextView journeyDescription;

    JourneyHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    void bind(Journey journey, int position){
            Stop firstStop = journey.getFirstStop();
            String date = DateUtils.toString(firstStop.getDate());

            journeyName.setText(journey.getName());
            journeyDate.setText(date);
            location.setText(firstStop.getLocation().getName());
            journeyDescription.setText(journey.getShortDescription());

//            scrim.setTransitionName(DetailActivity.SHARED_ROOT + position);
//            journeyName.setTransitionName(DetailActivity.SHARED_JOURNEY_NAME + position);
    }

}
