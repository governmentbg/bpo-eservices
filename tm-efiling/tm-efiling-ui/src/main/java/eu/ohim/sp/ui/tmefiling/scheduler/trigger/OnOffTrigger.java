package eu.ohim.sp.ui.tmefiling.scheduler.trigger;

import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.PeriodicTrigger;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Extension of the PeriodicTrigger that could be disabled
 * during the startup
 * @author karalch
 *
 */
public class OnOffTrigger extends PeriodicTrigger {
	
	private boolean enabled = false;

	public OnOffTrigger(long period, TimeUnit timeUnit) {
		super(period, timeUnit);
	}
	
	public OnOffTrigger(long period) {
		this(period, null);
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