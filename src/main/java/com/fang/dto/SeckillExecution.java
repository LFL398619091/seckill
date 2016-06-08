package main.java.com.fang.dto;

import main.java.com.fang.entity.SuccessKilled;
import main.java.com.fang.enums.SeckillStateEnum;

/**
 * 封装秒杀执行结果
 * Created by Jameslin on 16/6/7.
 */
public class SeckillExecution {
    //秒杀产品ID
    private Long seckillId;
    //秒杀执行结果状态
    private Integer state;
    //状态标示
    private String stateInfo;
    //秒杀成功对象
    private SuccessKilled successKilled;
    
    

    public SeckillExecution(Long seckillId, SeckillStateEnum stateEnum, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.successKilled = successKilled;
	}
    
    
	public SeckillExecution(Long seckillId, SeckillStateEnum stateEnum) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}


	public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckillId) {
        this.seckillId = seckillId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }


	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
    
}
