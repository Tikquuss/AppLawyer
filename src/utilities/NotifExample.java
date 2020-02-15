/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


import java.awt.AWTException;
import java.awt.SystemTray;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Utilisateur
 */
public class NotifExample {
   /* public static void timerallfeaturesrepeating(int timeInterval, String task, LocalDateTime date){
        int Timeinterval = timeInterval;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                if (SystemTray.isSupported()) {
                            TrayIconDemo td = new TrayIconDemo();
                try {
                        td.displayTray(task, date);
                  } catch (AWTException ex) {
                        Logger.getLogger(NotifExample.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  } else {
                     System.err.println("System tray not supported!");
                  }             
            }
        }, Timeinterval);
}*/
    public static void setAlarmTask(LocalDateTime datetime, String task){
        Date desiredDate = new Date(datetime.getYear()-1900, datetime.getMonthValue()-1, datetime.getDayOfMonth(), datetime.getHour()-1, datetime.getMinute()+1);
        long delay = desiredDate.getTime() - System.currentTimeMillis();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(new Runnable(){ @Override
            public void run() {
                if (SystemTray.isSupported()) {
                            TrayIconDemo td = new TrayIconDemo();
                try {
                        td.displayTray(task, datetime);
                  } catch (AWTException ex) {
                        Logger.getLogger(NotifExample.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  } else {
                     System.err.println("System tray not supported!");
                  }  
            }
                }, delay, TimeUnit.MILLISECONDS);
        }  
    
}
 