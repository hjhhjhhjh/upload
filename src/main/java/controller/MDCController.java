package controller;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysql.jdbc.StatementInterceptor;

/**
 * 研究MDC究竟是否
 * @author HJH-PC
 *
 */
@Controller
@RequestMapping(value = "/mdc")
// @Scope(value = "prototype")
public class MDCController {

	ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

	Logger logger = LoggerFactory.getLogger(MDCController.class);

	static String MDCKey = "hjh123";

	@ResponseBody
	@RequestMapping(value = "/index")
	public String index() throws InterruptedException {
		String uuidValue = MDC.get(MDCKey);

		int num = 0;
		String strNum = null;

		if (StringUtils.isBlank(uuidValue)) {
			uuidValue = UUID.randomUUID().toString();
			num = concurrentHashMap.size() + 1;
			// strNum = num+"";
			concurrentHashMap.put(uuidValue, num);
			MDC.put(MDCKey, uuidValue);

			uuidValue += " 新的";
		} else {
			// Thread.sleep(100);
			try {
				num = (int) concurrentHashMap.get(uuidValue);
			} catch (Exception e) {
				e.printStackTrace();
			}

			uuidValue += " 这是旧的";
		}
		System.out.println(uuidValue + ",concurrentHashMap有" + concurrentHashMap.size() + "个,这个是第" + num + "个");

		Thread.sleep(100);
		return uuidValue;
	}
}
