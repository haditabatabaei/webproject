
package io.reactivex.disposables;

import io.reactivex.annotations.NonNull;


final class RunnableDisposable extends ReferenceDisposable<Runnable> {

    private static final long serialVersionUID = -8219729196779211169L;

    RunnableDisposable(Runnable value) {
        super(value);
    }

    @Override
    protected void onDisposed(@NonNull Runnable value) {
        value.run();
    }

    @Override
    public String toString() {
        return "RunnableDisposable(disposed=" + isDisposed() + ", " + get() + ")";
    }
}
