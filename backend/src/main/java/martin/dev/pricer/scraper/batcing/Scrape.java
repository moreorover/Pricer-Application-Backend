package martin.dev.pricer.scraper.batcing;

import lombok.extern.slf4j.Slf4j;
import martin.dev.pricer.data.model.Url;
import martin.dev.pricer.data.repository.UrlRepository;
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
import org.springframework.batch.item.support.builder.CompositeItemProcessorBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

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
                .build();
    }

    @Bean
    public Step chunkBasedStep() {
        return this.stepBuilderFactory.get("chunkBasedStep")
                .<Url, String>chunk(1)
                .reader(urlReader())
                .processor(compositeItemProcessor())
                .faultTolerant()
                .retry(IOException.class)
                .writer(new ItemWriter<String>() {
                    @Override
                    public void write(List<? extends String> list) throws Exception {
//                        log.info("Size of list: " + list.size());
                        list.forEach(log::info);
                    }
                }).build();
    }

    private ItemProcessor<Url,String> compositeItemProcessor() {
        return new CompositeItemProcessorBuilder<Url, String>()
                .delegates(new UrlFilterProcessor(), new DocumentProcessor())
                .build();
    }

    @Bean
    public Step secondStep() {
        return  this.stepBuilderFactory.get("secondStep")
                .<Url, Url>chunk(3)
                .reader(new ItemReader<Url>() {
                    @Override
                    public Url read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                        return null;
                    }
                })
                .writer(new ItemWriter<Url>() {
                    @Override
                    public void write(List<? extends Url> list) throws Exception {
                        list.forEach(url -> System.out.println(url.toString()));
                    }
                }).build();
    }

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("job")
                .start(chunkBasedStep())
//                .next(secondStep())
                .build();
    }

}
