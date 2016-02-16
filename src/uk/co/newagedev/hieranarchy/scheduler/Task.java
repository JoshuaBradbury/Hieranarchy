package uk.co.newagedev.hieranarchy.scheduler;

public abstract class Task implements Runnable {

	protected int upsCount, upsEnd, id, offset, currOffset;

	public Task(int upsEnd) {
		this(upsEnd, 0);
	}

	public Task(int upsEnd, int offset) {
		upsCount = 0;
		currOffset = 0;
		this.offset = offset;
		this.upsEnd = upsEnd;
	}
	
	public int getId() {
		return id;
	}

	public boolean shouldRepeat() {
		return false;
	}

	protected void update() {
		if (currOffset == offset) {
			upsCount++;
			if (upsCount == upsEnd) {
				run();
				if (shouldRepeat()) {
					upsCount = 0;
				} else {
					TaskScheduler.removeTask(id);
				}
			}
		} else {
			currOffset++;
		}
	}
}
