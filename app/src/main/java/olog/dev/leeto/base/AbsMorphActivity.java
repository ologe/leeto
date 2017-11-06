package olog.dev.leeto.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.constraint.ConstraintLayout;
import android.transition.ArcMotion;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import olog.dev.leeto.R;
import olog.dev.leeto.utility.transition.fab_morph.MorphDialogToFab;
import olog.dev.leeto.utility.transition.fab_morph.MorphFabToDialog;

public abstract class AbsMorphActivity extends BaseActivity {

    private ConstraintLayout container;
    private FrameLayout root;
    private Button discard;

    protected ViewDataBinding viewDataBinding;

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        container = findViewById(R.id.container);
        root = findViewById(R.id.root);
        discard = findViewById(R.id.discard);

        setupSharedElementTransitions();
    }

    @Override
    @CallSuper
    protected void onResume() {
        super.onResume();
        root.setOnClickListener(view -> dismiss());
        discard.setOnClickListener(view -> dismiss());
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

    @LayoutRes
    protected abstract int getLayoutId();

}
