package matheusrrt.encurtadorurl.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration //Informa que essa classe tem configurações da aplicação
@EnableAsync
public class AsyncConfig {

    @Bean(name = "clickExecutor")
    public Executor clickExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5); //No mínimo 5 threads vivas
        executor.setMaxPoolSize(20); //Máximo de 20 threads
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("click-async-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

}
