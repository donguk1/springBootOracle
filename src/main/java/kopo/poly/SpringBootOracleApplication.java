package kopo.poly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 간단한 배치(일괄처리)
 * <p>
 * 스프링부트 실행시 배치를 혀옹하도록 설정하는 어노테이션
 * <p>
 */
@EnableScheduling
@SpringBootApplication
public class SpringBootOracleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootOracleApplication.class, args);
    }

}
