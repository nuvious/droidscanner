package com.nuvsoft.android.scanner.tasks;

import android.content.Context;

import com.nuvsoft.android.scanner.db.DatabaseAssistant;
import com.nuvsoft.android.scanner.settings.EventTrigger;

public abstract class ScannerTask {

	private long eventTrigger;
	private long max_interval;
	private long last_interval = -1;
	private static volatile boolean running = false;

	public long getEventTrigger() {
		return eventTrigger;
	}

	public ScannerTask(long trigger, long max_interval) {
		this.eventTrigger = trigger;
		this.max_interval = max_interval;
		running = false;
	}

	public abstract String getLogTag();

	public int run(final Context c, EventTrigger t, int eventid) {
		// Log.v(getLogTag(), "RECEIVED TRIGGER: " + t.name());
		if (!running) {
			if (!t.isSet(getEventTrigger())) {
				// Log.d(getLogTag(), "Wrong Event Trigger");
				return eventid;
			}
			if (!checkInterval()) {
				// Log.d(getLogTag(), "Waiting for next poll event.");
				return eventid;
			}

			if (eventid == -1)
				eventid = DatabaseAssistant.registerEvent(c, t);

			running = true;
			if (run(c, eventid)) {
				updateInterval();
			}
			running = false;
		}
		return eventid;
	}

	/**
	 * @param c
	 * @return True if the logging task completes. The super class then updates
	 *         the logging interval.
	 */
	public abstract boolean run(Context c, int eventid);

	private void updateInterval() {
		last_interval = System.currentTimeMillis();
	}

	private boolean checkInterval() {
		return last_interval + max_interval < System.currentTimeMillis();
	}
}
