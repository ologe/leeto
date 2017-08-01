package olog.dev.leeto.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;

import io.reactivex.annotations.Nullable;
import olog.dev.leeto.activity_detail.DetailActivity;
import olog.dev.leeto.model.pojo.Stop;
import olog.dev.leeto.utility.transition.fab_morph.MorphDialogToFab;
import olog.dev.leeto.utility.transition.fab_morph.MorphFabToDialog;

public abstract class AbsMorphActivity extends AppCompatActivity {

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        container = (ViewGroup) getContainerLayout();
        setupSharedElementTransitions();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getRootLayout().setOnClickListener(dismissListener);
        getDiscardButton().setOnClickListener(dismissListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getContainerLayout().setOnClickListener(null);
        getDiscardButton().setOnClickListener(null);
    }

    public void setupSharedElementTransitions() {
        ArcMotion arcMotion = new ArcMotion();
        arcMotion.setMinimumHorizontalAngle(50f);
        arcMotion.setMinimumVerticalAngle(50f);

        Interpolator easeInOut = AnimationUtils.loadInterpolator(this, android.R.interpolator.fast_out_slow_in);

        MorphFabToDialog sharedEnter = new MorphFabToDialog();
        sharedEnter.setPathMotion(arcMotion);
        sharedEnter.setInterpolator(easeInOut);

        MorphDialogToFab sharedReturn = new MorphDialogToFab();
        sharedReturn.setPathMotion(arcMotion);
        sharedReturn.setInterpolator(easeInOut);

        if (container != null) {
            sharedEnter.addTarget(container);
            sharedReturn.addTarget(container);
        }
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @Override
    public void onBackPressed() {
        dismiss();
    }

    protected void dismiss() {
        setResult(Activity.RESULT_CANCELED);
        finishAfterTransition();
    }

    protected void save(@Nullable Object object){
        Intent intent = null;
        if(object != null){
            intent = new Intent();
            if(object instanceof Stop){
                intent.putExtra(DetailActivity.EXTRA_STOP, (Stop)object);
            }
        }
        setResult(Activity.RESULT_OK, intent);
        finishAfterTransition();
    }

    private View.OnClickListener dismissListener = view -> dismiss();

    protected abstract View getContainerLayout();
    protected abstract View getRootLayout();

    protected abstract View getDiscardButton();
}
