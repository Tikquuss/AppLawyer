/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;


import appdatabase.bean.Operation;
import com.sun.javafx.geom.Rectangle;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

/**
 *
 * @author Utilisateur
 */
public class NotifExample {
    
    public static void showNotification(String title, String text, Duration d, String  t) {
    //Check if it is JavaFX Application Thread
    if (!Platform.isFxApplicationThread()) {    
        Platform.runLater(() -> showNotification(title, text, d, t));
        return;
    }
    Notifications notification1 = Notifications.create().title(title).text(text).position(Pos.CENTER);
    notification1.hideAfter(d);
    switch(t) {
        case "CONFIRM":
            notification1.showConfirm();
            break;
        case "ERROR":
            notification1.showError();
            break;
        case "INFORMATION":
            notification1.showInformation();
            break;
        case "SIMPLE":
            notification1.show();
            break;
        case "WARNING":
            notification1.showWarning();
            break;
        default:
            break;
    }
}
   public static void timerallfeaturesrepeating(int timeInterval, String task, LocalDateTime date){
        int Timeinterval = timeInterval;
        Timer timer = new Timer();
        timer.schedule(new TimerTask(){
            @Override
            public void run() {
                 showNotification(task+" (tâche APPLAWYER)","Vous avez une tâche prévue à "+date.toLocalTime(), Duration.seconds(100), "INFORMATION");
            }
        }, Timeinterval, Timeinterval);
      }
   
    public static void setAlarmTask(LocalDateTime datetime, Operation ope){
        Date desiredDate = new Date(datetime.getYear()-1900, datetime.getMonthValue()-1, datetime.getDayOfMonth(), datetime.getHour()-1, datetime.getMinute()+1);
        long delay = desiredDate.getTime() - System.currentTimeMillis();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(new Runnable(){ @Override
            public void run() {
                Operation op2 = Operation.getById(ope.getId());
                if(op2.getEtat().equals("En attente"))
                //timerallfeaturesrepeating(11000, task, datetime);                   
                  showNotification(ope.getTache()+" (tâche APPLAWYER)","Vous avez une tâche prévue à "+datetime.toLocalTime(), Duration.minutes(15), "INFORMATION");
                    
            }
                }, delay, TimeUnit.MILLISECONDS);
        }  
    
}
 