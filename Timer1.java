import java.util.Timer;
import java.util.TimerTask;

class Timer1 {

    Timer timer;

    public Timer1(int seconds) {
        timer = new Timer();
        timer.schedule(new RemindTask(), seconds * 1000);
    }

    class RemindTask extends TimerTask {
        public void run() {
            System.out.println("Your time is up!");
            //exit program
            System.exit(0);
            //terminate timer thread
            timer.cancel();
        }
    }

    public static void main(String args[]) {
        //number in bracket = number of seconds
        new Timer1(20);
        System.out.format("Task scheduled.%n");
    }
}