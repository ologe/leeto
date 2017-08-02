package olog.dev.leeto.base;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.transition.ArcMotion;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import olog.dev.leeto.R;
import olog.dev.leeto.utility.transition.fab_morph.MorphDialogToFab;
import olog.dev.leeto.utility.transition.fab_morph.MorphFabToDialog;

public abstract class AbsMorphActivity extends BaseActivity {

    protected @BindView(R.id.container) ConstraintLayout container;
    protected @BindView(R.id.root) FrameLayout root;
    protected @BindView(R.id.discard) Button discard;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        ButterKnife.bind(this);

        setupSharedElementTransitions();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        root.setOnClickListener(dismissListener);
        discard.setOnClickListener(dismissListener);
    }

    @Override
    @CallSuper
    protected void onPause() {
        super.onPause();
        root.setOnClickListener(null);
        discard.setOnClickListener(null);
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

        sharedEnter.addTarget(container);
        sharedReturn.addTarget(container);
        getWindow().setSharedElementEnterTransition(sharedEnter);
        getWindow().setSharedElementReturnTransition(sharedReturn);
    }

    @Override
    @CallSuper
    public void onBackPressed() {
        dismiss();
    }

    protected void dismiss() {
        finishAfterTransition();
    }

    private View.OnClickListener dismissListener = view -> dismiss();

    @LayoutRes
    protected abstract int getLayoutId();

}
