package main.java.com.fang.entity;

import java.util.Date;

public class Seckill {
    private Long seckillId;
    private String name;
    private Integer number;
    private Date startTime;
    private Date endTime;
    private Date createTime;

    public Long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(Long seckill_id) {
        this.seckillId = seckill_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date start_time) {
        this.startTime = start_time;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date end_time) {
        this.endTime = end_time;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date create_time) {
        this.createTime = create_time;
    }

    @Override
    public String toString() {
        return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", startTime="
                + startTime + ", endTime=" + endTime + ", createTime=" + createTime + "]";
    }

}
