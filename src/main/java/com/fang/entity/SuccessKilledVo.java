package main.java.com.fang.entity;

public class SuccessKilledVo extends SuccessKilled {
	private Seckill seckill;

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}

	@Override
	public String toString() {
		return "SuccessKilledVo{" +
				"seckill=" + seckill +
				'}';
	}
}
