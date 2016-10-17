package com.lucy.picture.selector;

/**
 * 图片文件夹
 * 
 * @author Administrator
 * 
 */
public class ImageFloder {
	/*
	 * 文件夹路径
	 */
	private String dirPath;
	/*
	 * 第一张图片路径
	 */
	private String firstImagePath;
	/*
	 * 文件夹名称
	 */
	private String name;
	/*
	 * 文件夹图片数量
	 */
	private int count;

	public String getDirPath() {
		return dirPath;
	}

	public void setDirPath(String dirPath) {
		this.dirPath = dirPath;
		this.name = dirPath.substring(dirPath.lastIndexOf("/"));
	}

	public String getFirstImagePath() {
		return firstImagePath;
	}

	public void setFirstImagePath(String firstImagePath) {
		this.firstImagePath = firstImagePath;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
