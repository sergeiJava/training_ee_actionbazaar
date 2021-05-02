package com.actionbazaar.buslogic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

import com.actionbazaar.model.Email;

/**
 * Session Bean implementation class FlyerBean
 */
@Stateless
public class FlyerBean {

	
	private static final Logger logger = Logger.getLogger("FlyerBean");
	
	@Resource
	TimerService timeService;
    /**
     * Default constructor. 
     */
    public FlyerBean() {
        // TODO Auto-generated constructor stub
    }

    public List<Timer> getScheduledFlyers(){
    	Collection<Timer> timers = timeService.getAllTimers();
    	return new ArrayList<Timer>(timers);
    }
    
    /**
     * 
     * @param se
     * @param mail
     * 
     * to create ScheduleEXpression use this:
     * ScheduleExpression sce = new ScheduleExpression();
     * sce.month(2).dayOfMonth(14).year(2012).hour(11).minute(30); //14-feb-12 11:30
     */
    public void scheduleFlyer(ScheduleExpression se, Email mail) {
    	TimerConfig tc = new TimerConfig(mail, true);
    	Timer timer = timeService.createCalendarTimer(se, tc);
    	logger.log(Level.INFO, "Flyer will be sent at: {0}", timer.getNextTimeout());
    }
    
    
    @Timeout
    public void send(Timer timer) {
    	if(timer.getInfo() instanceof Email) {
    		Email mail = (Email) timer.getInfo();
    		// Retrieve bidders/sellers and email…
    	}
    
    }
}
