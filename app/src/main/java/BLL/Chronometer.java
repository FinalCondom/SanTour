package BLL;

/**
 * Created by lucien on 24.11.2017.
 */
//chronometer class
public final class Chronometer {
    private long begin, end, pause, duration;
    private boolean isRunning = false;
    //Start the chronometer
    public void start(){
        pause+=end-begin;
        begin = System.currentTimeMillis();
        isRunning = true;
    }

    //Stop the chronometer
    public void stop(){
        end = System.currentTimeMillis();
        isRunning = false;
    }

    //return the time once
    public long getTime() {
        //if the chronometer is still running, we return the current time
        if(isRunning) {
            long currentTime = System.currentTimeMillis();
            return pause + currentTime - begin;
        }
        //if the chornometer is stopped, we return the value at the end of the function
        return pause+end-begin;
    }
}
