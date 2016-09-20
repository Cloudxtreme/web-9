package cn.netkiller.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
	public final static long ONE_DAY = 24 * 60 * 60 * 1000;
	public final static long ONE_HOUR = 60 * 60 * 1000;

//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//	
	public ScheduledTasks() {
		// TODO Auto-generated constructor stub
	}
	@Scheduled(initialDelay = 1000, fixedRate = 60000*5)
	public void currentDate() {
//		System.out.println("^_^");
//		Date date = jdbcTemplate.queryForObject("select sysdate from dual", Date.class);
//		System.out.println(date);
//		log.info("The oracle sysdate is {}", dateFormat.format(date));
	}
/*
 * 
//	@Scheduled(fixedRate = 5000)
//	public void echoCurrentTime() {
//		log.info("The time is now {}", dateFormat.format(new Date()));
//	} 
	@Scheduled(fixedRate = ONE_DAY)
	public void scheduledTask() {
		System.out.println("");
	}*/

	@Scheduled(fixedDelay = ONE_HOUR)
	public void scheduleTask2() {
		log.info("==================== 分割线 ====================");
	}

/*

	@Scheduled(cron = "0 0/1 * * * ? ")
	public void ScheduledTask3() {
		System.out.println("");
	}*/

}
