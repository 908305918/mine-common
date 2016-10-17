package com.lucy.common.aysnc;

public interface DataLoader {

	public String loadData(Object... params);

	public void handleData(String result);

}
