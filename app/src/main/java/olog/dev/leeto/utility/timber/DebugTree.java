package olog.dev.leeto.utility.timber;

import timber.log.Timber;

public class DebugTree extends Timber.DebugTree {

    @Override
    protected String createStackElementTag(StackTraceElement element) {
        return super.createStackElementTag(element) + ":" +
                element.getLineNumber(); // e.g MainActivity:15
    }
}
