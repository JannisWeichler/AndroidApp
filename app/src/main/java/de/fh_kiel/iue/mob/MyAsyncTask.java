package de.fh_kiel.iue.mob;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.widget.ProgressBar;


class MyAsyncTask extends AsyncTask<Integer,Integer,Boolean> {

    public interface Listener{
        void datachanged();
        void progress(int percent);
        void toastausgeben(Boolean result);
    }

    private Listener mListener;
    private ProgressBar mprogressbar = null;
    private MyAdapter mAdapter = null;


    MyAsyncTask(Listener listener){ mListener = listener; }


    @Override
    protected Boolean doInBackground(Integer... integers) {


        for(int i=0; i<= integers[0]; i+=integers[1]) {
            //mListener.fill(i,daten);
            Stadt stadt = new Stadt("stadt " + i, new Stadt.Weather[]{new Stadt.Weather("noch nie geladen")}, new Stadt.Main(i, i, i, i, i), new Stadt.Wind(i, i), new Stadt.Sys(i, i), new Stadt.Cloud(i));
            ActivityMain.DataContainer.adddata(stadt);
            SystemClock.sleep(integers[2]);
            publishProgress(i);
        }

        return null;
    }


    @Override
    protected void onProgressUpdate(Integer...progress){
        mListener.datachanged();
        mListener.progress(progress[0]);
    }

    @Override
    protected void onPostExecute(Boolean result){
        mListener.toastausgeben(result);
    }
}
