package cn.rectcircle.bindingsearch.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rectcircle
 * @date 2017/10/28
 */
public class BindingState {

	private RequireUrl requireUrl;
	/**
	 * 记录请求的状态
	 */
	private StateEnum state;
	/**
	 * 记录结果
	 */
	private ResultEnum result;

	public BindingState() {

	}

	/**
	 * 创建一个请求状态列表
	 * @param requireUrlList 请求Url信息列表
	 * @return 请求状态列表
	 */
	public static List<BindingState> create(List<RequireUrl> requireUrlList){
		List<BindingState> bindingStateList= new ArrayList<>();
		for (RequireUrl requireUrl: requireUrlList) {
			bindingStateList.add(new BindingState(requireUrl));
		}
		return bindingStateList;
	}

	public BindingState(RequireUrl requireUrl) {
		this.requireUrl = requireUrl;
		this.state = StateEnum.WAITING;
		this.result = ResultEnum.UNKNOWN;
	}

	public RequireUrl getRequireUrl() {
		return requireUrl;
	}

	public void setRequireUrl(RequireUrl requireUrl) {
		this.requireUrl = requireUrl;
	}

	public StateEnum getState() {
		return state;
	}

	public void setState(StateEnum state) {
		this.state = state;
	}

	public ResultEnum getResult() {
		return result;
	}

	public void setResult(ResultEnum result) {
		this.result = result;
	}

	public String getFinalState(){
		String res;
		switch (state){
			case WAITING: res = "等待中...";break;
			case RUNNING: res = "查询中...";break;
			case ERROR: res = "网络出错";break;
			case FINISHED:
				switch (result){
					case UNKNOWN: res = "未知状态";break;
					case BINDED: res = "该号码已绑定";break;
					case NOBIND: res = "该号码未被绑定";break;
					default: res="错误"; break;
				}
				break;
			default: res="错误"; break;
		}
		return res;
	}

	/**
	 * 请求状态
	 */
	public enum StateEnum{
		/**
		 * 等待状态
		 */
		WAITING,
		/**
		 * 执行请求中
		 */
		RUNNING,
		/**
		 * 请求已完成
		 */
		FINISHED,
		/**
		 * 错误
		 */
		ERROR
	}

	/**
	 * 结果状态
	 */
	public enum ResultEnum{
		/**
		 * 位置状态
		 */
		UNKNOWN,
		/**
		 * 已绑定
		 */
		BINDED,
		/**
		 * 未绑定
		 */
		NOBIND
	}

}
