package org.uk.cyberbyte.armaworks.Util;

import android.os.AsyncTask;

public abstract class ExceptionAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {

    private Exception exception = null;
    private Params[] params;

    @Override
    final protected Result doInBackground(Params... params) {
        try {
            this.params = params;
            return doInBackground();
        } catch (Exception e) {
            exception = e;
            return null;
        }
    }

    abstract protected Result doInBackground() throws Exception;

    @Override
    final protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        onPostExecute(exception, result);
    }

    abstract protected void onPostExecute(Exception exception, Result result);

    public Params[] getParams() {
        return params;
    }

}