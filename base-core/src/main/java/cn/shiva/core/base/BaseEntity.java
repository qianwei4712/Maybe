package cn.shiva.core.base;

import cn.shiva.core.utils.SysUtils;
import cn.shiva.system.entity.User;

import java.io.Serializable;
import java.util.Date;

public class BaseEntity<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 删除标志，正常
	 */
	public static final int DELFLAG_NORMAL = 0 ;

	/**
	 * 删除标志，已删除
	 */
	public static final int DELFLAG_DELETED = 1 ;

	/**
	 * id<br>
	 * 指定自增策略
	 */
	protected Long id;

	/**
	 * 逻辑删除，0-正常，1-删除
	 */
	protected int delFlag;

	/**
	 * 生成时间<br>
	 * 字段自动填充策略，新增时自动填充
	 */
	protected Date createDate;

	/**
	 * 创建对象
	 */
	protected Long createBy;

	/**
	 * 备注
	 */
	protected String remarks;



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(int delFlag) {
		this.delFlag = delFlag;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Long getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void preInsert(){
		User user = SysUtils.getUser();
		if (user.getId() != null){
			this.createBy = user.getId();
		}
		this.createDate = new Date();
	}

}
