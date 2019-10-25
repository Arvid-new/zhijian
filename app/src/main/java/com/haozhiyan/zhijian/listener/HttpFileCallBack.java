package com.haozhiyan.zhijian.listener;

/**
 * @author: Jason
 * @date: 2016/6/12.
 * @desc: 文件下载回调接口
 */
public interface HttpFileCallBack {

	/**
	 * 下载过程中的进度回调方法
	 * @param currentSize
	 * @param totalSize
	 * @param progress
	 * @param networkSpeed
	 */
	public void inProgress(long currentSize, long totalSize, float progress, long networkSpeed);

	/**
	 * 请求成功的回调
	 * @param result 返回结果
	 */
	public void onSuccess(Object result);

	/**
	 * 请求失败的回调
	 * @param code  错误标识码
	 * @param msg   错误描述
	 */
	public void onFailure(int code, String msg);

}
