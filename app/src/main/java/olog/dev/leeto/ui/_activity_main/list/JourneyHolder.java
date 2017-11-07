package olog.dev.leeto.ui._activity_main.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import dev.olog.domain.model.Journey;
import olog.dev.leeto.utility.AppConstants;

public class JourneyHolder extends RecyclerView.ViewHolder {

    private static final int OVERRIDE_WIDTH = 1000;

    JourneyHolder(View view) {
        super(view);

//        constraintLayout = ((ConstraintLayout) itemView);
//        constraintSet = new ConstraintSet();
//        constraintSet.clone(constraintLayout);
    }

    void bind(Journey journey, int position){
        if(position == RecyclerView.NO_POSITION) return; // is animating
//
//        String ratio = AppConstants.getRatio(position);
//
//        constraintSet.setDimensionRatio(image.getId(), ratio);
//        constraintSet.applyTo(constraintLayout);
    }

    private int getAspectRatioHeight(String ratio){
        if(ratio.equals(AppConstants.RATIO_2_1)) return OVERRIDE_WIDTH / 2;
        if(ratio.equals(AppConstants.RATIO_16_9)) return OVERRIDE_WIDTH / 16 * 9;
        if(ratio.equals(AppConstants.RATIO_ONE_HALF)) return (int) (OVERRIDE_WIDTH / 1.5);
        if(ratio.equals(AppConstants.RATIO_SQUARE)) return OVERRIDE_WIDTH;
        return 0;
    }


}
