package eu.ohim.sp.common.ui.scheduler.trigger;

import java.util.Date;

import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * Extention of the PeriodicTrigger that could be disabled 
 * during the startup
 * @author karalch
 *
 */
public class OnOffTrigger extends PeriodicTrigger {
	
	private boolean enabled = false;

	public OnOffTrigger(long period) {
		super(period);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Date nextExecutionTime(TriggerContext triggerContext) {
		if (enabled) {
			return super.nextExecutionTime(triggerContext);
		} else {
			return null;
		}
	}
	
}