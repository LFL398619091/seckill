package main.java.com.fang.web.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import main.java.com.fang.dto.Exposer;
import main.java.com.fang.dto.SeckillExecution;
import main.java.com.fang.dto.SeckillResult;
import main.java.com.fang.entity.Seckill;
import main.java.com.fang.enums.SeckillStateEnum;
import main.java.com.fang.exception.RepeatKillException;
import main.java.com.fang.exception.SeckillClosedException;
import main.java.com.fang.service.SeckillService;

@Controller
@RequestMapping("/seckill")
public class SeckillController {
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SeckillService seckillService;

	/**
	 * 获取秒杀列表页
	 *
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String getSeckillList(Model model) {
		List<Seckill> seckillList = seckillService.getSeckillList();
		model.addAttribute(seckillList);
		return "seckill/list";
	}

	/**
	 * 获取秒杀商品详情页
	 *
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/detail", method = RequestMethod.GET)
	public String getSeckillDetail(@PathVariable("seckillId") Long seckillId, Model model) {
		if (seckillId == null) {
			return "redirect:/seckill/list";
		}
		Seckill seckill = seckillService.getSeckillBySeckillId(seckillId);
		if (seckill == null) {
			return "redirect:/seckill/list";
		}
		model.addAttribute(seckill);
		return "seckill/detail";
	}

	/**
	 * 秒杀商品暴露接口
	 */
	@RequestMapping(value = "/{seckillId}/exposer", method = RequestMethod.POST, produces = {
			"application/json;charset=utf-8" })
	public @ResponseBody SeckillResult<Exposer> exposer(@PathVariable("seckillId") Long seckillId) {
		SeckillResult<Exposer> result = null;
		try {
			Exposer exposer = seckillService.exportSeckillUrl(seckillId);
			if (exposer != null && exposer.isExposed()) {
				result = new SeckillResult<Exposer>(true, exposer);
			} else {
				result = new SeckillResult<Exposer>(false, "秒杀未开启");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result = new SeckillResult<Exposer>(false, e.getMessage());
		}
		return result;
	}

	/**
	 * 执行秒杀
	 * 
	 * @param seckillId
	 * @param userPhone
	 * @param md5
	 * @return
	 */
	@RequestMapping(value = "/{seckillId}/{md5}/excute", method = RequestMethod.POST, produces = {
			"application/json;charset=utf-8" })
	public @ResponseBody SeckillResult<SeckillExecution> excuteKill(@PathVariable("seckillId") Long seckillId,
			@PathVariable("md5") String md5, @CookieValue(value = "killPhone", required = false) Long userPhone) {
		if (userPhone == null) {
			return new SeckillResult<SeckillExecution>(false, "未登录");
		}
		SeckillResult<SeckillExecution> result = null;
		if (seckillId == null || md5 == null) {
			result = new SeckillResult<SeckillExecution>(false, "参数不完整");
		}
		try {
			SeckillExecution seckillExecution = seckillService.excuteSeckill(seckillId, userPhone, md5);
			if (seckillExecution != null
					&& SeckillStateEnum.stateOf(seckillExecution.getState()) == SeckillStateEnum.SUCCESS) {
				result = new SeckillResult<SeckillExecution>(true, seckillExecution);
			} else {
				result = new SeckillResult<SeckillExecution>(false, seckillExecution.getStateInfo());
			}
		} catch (SeckillClosedException e1) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.END);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (RepeatKillException e2) {
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.REPEAT_KILL);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		} catch (Exception e3) {
			log.error(e3.getMessage(), e3);
			SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStateEnum.INNER_ERROR);
			result = new SeckillResult<SeckillExecution>(true, seckillExecution);
		}
		return result;
	}
	
	/**
	 * 获取系统时间
	 * @return
	 */
	@RequestMapping(value = "/time", method = RequestMethod.POST, produces = { "application/json;charset=utf-8" })
	@ResponseBody
	public SeckillResult<Long> getTime() {
		Date date = new Date();
		Long time = date.getTime();
		return new SeckillResult<Long>(true, time);	
	}
}
