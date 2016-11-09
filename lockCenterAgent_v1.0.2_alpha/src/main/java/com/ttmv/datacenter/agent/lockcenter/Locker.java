package com.ttmv.datacenter.agent.lockcenter;

public interface Locker {

	public boolean lockUniqueFE(String key);

	public boolean unlockUniqueFE(String key);

	public boolean lockUnique(String key);

	public void unlockUnique(String key);

	public boolean lockUntilFE(String key, int untilNum);

	public boolean releaseOneFE(String key);
}
