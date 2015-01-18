package com.ciandt.cursoandroid.worldwondersapp.listener;

public interface IntegratorOperatorCallback<TResult> {

    /**
     * Called when an integrator call against API is completed and with success.
     *
     * @param result
     */
    public void onOperationCompleteSuccess(TResult result);

    /**
     * Called when an integrator call against API is completed and with error.
     */
    public void onOperationCompleteError();
}
