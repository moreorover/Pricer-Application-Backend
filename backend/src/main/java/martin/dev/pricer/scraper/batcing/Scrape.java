package martin.dev.pricer.scraper.batcing;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.repository.UrlRepository;
import org.jsoup.nodes.Document;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.*;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
@EnableScheduling
public class Scrape {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public JobLauncher jobLauncher;

    @Autowired
    private UrlRepository urlRepository;

    @Scheduled(cron = "0/15 * * * * *")
    public void runJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();
        jobParametersBuilder.addDate("runTime", new Date());
        this.jobLauncher.run(job(), jobParametersBuilder.toJobParameters());
    }

    @Bean
    public ItemReader<Url> urlReader() {
        Map<String, Sort.Direction> map = new HashMap<>();
        map.put("checkedAt", Sort.Direction.ASC);

        return new RepositoryItemReaderBuilder<Url>()
                .repository(this.urlRepository)
                .pageSize(99)
                .methodName("findAll")
                .name("urlReader")
                .sorts(map)
                .saveState(false)
                .build();
    }

    @Bean
    public TaskExecutor taskExecutor2() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        return executor;
    }

    @Bean
    public Step chunkBasedStep() {
        return this.stepBuilderFactory.get("chunkBasedStep")
                .<Url, String>chunk(1)
                .reader(urlReader())
                .processor(compositeItemProcessor())
                .faultTolerant()
                .retry(IOException.class)
                .retryLimit(99)
                .listener(new UrlRetryListener())
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {

                    }
                })
                .taskExecutor(taskExecutor2())
                .build();
    }

    @Bean
    public Step chunkBasedStep2() {
        return this.stepBuilderFactory.get("chunkBasedStep2")
                .<Url, String>chunk(1)
                .reader(new ItemReader<Url>() {
                    @Autowired
                    UrlRepository urlRepository;

                    @Override
                    public Url read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        return this.urlRepository.findById(2L).get();
                    }
                })
                .processor(compositeItemProcessor())
                .faultTolerant()
                .retry(IOException.class)
                .retryLimit(99)
                .listener(new UrlRetryListener())
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {

                    }
                })
                .taskExecutor(taskExecutor2())
                .build();
    }

    private ItemProcessor<Url,String> compositeItemProcessor() {
        return new CompositeItemProcessorBuilder<Url, String>()
                .delegates(new UrlFilterProcessor(), new DocumentProcessor())
                .build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(chunkBasedStep())
                .build();
    }

}
