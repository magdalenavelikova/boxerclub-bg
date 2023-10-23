package bg.boxerclub.boxerclubbgrestserver.core.config;

import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration

public class BatchConfiguration {
    //    @Value("${file.input}")
    private Resource inputCsv = new FileSystemResource("src/main/resources/links.csv");
    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final LinkRepository linkRepository;

    public BatchConfiguration(JobRepository jobRepository, PlatformTransactionManager transactionManager, LinkRepository linkRepository) {

        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;

        this.linkRepository = linkRepository;
    }


//    @Bean
//    public PlatformTransactionManager transactionManager() {
//        return new JpaTransactionManager();
//    }

    @Bean
    public Job job() {
        //  return new JobBuilder("importLinks", jobRepository).incrementer(new RunIdIncrementer()).start(step1()).build();
        JobBuilder jobBuilderFactory = new JobBuilder("importLinks", jobRepository);
        return jobBuilderFactory.flow(step1()).end()
                .build();

    }


    @Bean
    public ItemReader<LinkEntity> reader() {

        FlatFileItemReader<LinkEntity> itemReader = new FlatFileItemReader<LinkEntity>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputCsv);
        return itemReader;

    }

    @Bean
    public LineMapper<LinkEntity> lineMapper() {
        DefaultLineMapper<LinkEntity> lineMapper = new DefaultLineMapper<LinkEntity>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"type", "description", "title", "urlLink"});
        lineTokenizer.setIncludedFields(new int[]{0, 1, 2, 3});
        BeanWrapperFieldSetMapper<LinkEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<LinkEntity>();
        fieldSetMapper.setTargetType(LinkEntity.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    @Bean
    public ItemProcessor<LinkEntity, LinkEntity> processor() {
        return new ValidationProcessor();
    }


    @Bean
    public RepositoryItemWriter<LinkEntity> writer() {
        RepositoryItemWriter<LinkEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(linkRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(10);
        return asyncTaskExecutor;
    }

    //
    @Bean
    public Step step1() {
        return new StepBuilder("step1", jobRepository)
                .<LinkEntity, LinkEntity>chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .taskExecutor(taskExecutor()).build();
    }
//
//    @Bean
//    public LinkEntityItemProcessor processor() {
//        return new LinkEntityItemProcessor();
//    }
}
