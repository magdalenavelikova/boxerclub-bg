package bg.boxerclub.boxerclubbgrestserver.config;

import bg.boxerclub.boxerclubbgrestserver.model.entity.DogEntity;
import bg.boxerclub.boxerclubbgrestserver.model.entity.LinkEntity;
import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import bg.boxerclub.boxerclubbgrestserver.util.DogFieldSetMapper;
import bg.boxerclub.boxerclubbgrestserver.util.ParentFieldSetMapper;
import bg.boxerclub.boxerclubbgrestserver.validation.ValidationProcessorDogs;
import bg.boxerclub.boxerclubbgrestserver.validation.ValidationProcessorLinks;
import org.jetbrains.annotations.NotNull;
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

    private final Resource INPUT_CSV_LINKS = new FileSystemResource("src/main/resources/links.csv");
    private final Resource INPUT_CSV_DOGS = new FileSystemResource("src/main/resources/dogs.csv");
    private final JobRepository jobRepository;

    private final LinkRepository linkRepository;
    private final DogRepository dogRepository;
    private final PlatformTransactionManager transactionManager;
    private final DogFieldSetMapper dogFieldSetMapper;
    private final ParentFieldSetMapper parentFieldSetMapper;

    public BatchConfiguration(JobRepository jobRepository,
                              PlatformTransactionManager transactionManager,
                              LinkRepository linkRepository,
                              DogRepository dogRepository,
                              DogFieldSetMapper dogFieldSetMapper,
                              ParentFieldSetMapper parentFieldSetMapper) {

        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.linkRepository = linkRepository;
        this.dogRepository = dogRepository;
        this.dogFieldSetMapper = dogFieldSetMapper;
        this.parentFieldSetMapper = parentFieldSetMapper;
    }


    @Bean
    public Job job() {
        JobBuilder jobBuilderFactory = new JobBuilder("import", jobRepository);
        return jobBuilderFactory.flow(stepLinks())
                .next(stepDogs())
                .next(stepParents())
                .end()
                .build();
    }


    @Bean
    public ItemReader<LinkEntity> readerLinks() {
        FlatFileItemReader<LinkEntity> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapperLinks());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(INPUT_CSV_LINKS);
        return itemReader;

    }

    @Bean
    public LineMapper<LinkEntity> lineMapperLinks() {
        DefaultLineMapper<LinkEntity> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("type", "description", "title", "urlLink");
        lineTokenizer.setIncludedFields(0, 1, 2, 3);
        BeanWrapperFieldSetMapper<LinkEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(LinkEntity.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        return lineMapper;
    }


    @Bean
    public ItemProcessor<LinkEntity, LinkEntity> processorLinks() {
        return new ValidationProcessorLinks();
    }


    @Bean
    public RepositoryItemWriter<LinkEntity> writerLinks() {
        RepositoryItemWriter<LinkEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(linkRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public TaskExecutor taskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(1);
        return asyncTaskExecutor;
    }


    @Bean
    public Step stepLinks() {
        return new StepBuilder("stepLinks", jobRepository)
                .<LinkEntity, LinkEntity>chunk(1, transactionManager)
                .reader(readerLinks())
                .processor(processorLinks())
                .writer(writerLinks())
                .taskExecutor(taskExecutor()).build();
    }


    @Bean
    public ItemReader<DogEntity> readerDogs() {
        FlatFileItemReader<DogEntity> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapperDogs());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(INPUT_CSV_DOGS);
        return itemReader;

    }

    @Bean
    public LineMapper<DogEntity> lineMapperDogs() {
        DefaultLineMapper<DogEntity> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = getDelimitedLineTokenizerDogs();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(dogFieldSetMapper);
        return lineMapper;
    }

    @NotNull
    private static DelimitedLineTokenizer getDelimitedLineTokenizerDogs() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "birthday", "color", "date_of_decease",
                "health_status", "is_approved", "kennel", "micro_chip", "name", "picture_url", "registration_num", "sex",
                "owner_id");
        lineTokenizer.setIncludedFields(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 14);
        return lineTokenizer;
    }

    @Bean
    public ItemReader<DogEntity> readerParents() {
        FlatFileItemReader<DogEntity> itemReader = new FlatFileItemReader<>();
        itemReader.setLineMapper(lineMapperParents());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(INPUT_CSV_DOGS);
        return itemReader;

    }

    @Bean
    public LineMapper<DogEntity> lineMapperParents() {
        DefaultLineMapper<DogEntity> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer lineTokenizer = getDelimitedLineTokenizerParents();
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(parentFieldSetMapper);
        return lineMapper;
    }

    @NotNull
    private static DelimitedLineTokenizer getDelimitedLineTokenizerParents() {
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(";");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("registration_num", "father_rn", "mother_rn");
        lineTokenizer.setIncludedFields(10, 12, 13);
        return lineTokenizer;
    }

    @Bean
    public ItemProcessor<DogEntity, DogEntity> processorDogs() {
        return new ValidationProcessorDogs();
    }


    @Bean
    public RepositoryItemWriter<DogEntity> writerDogs() {
        RepositoryItemWriter<DogEntity> writer = new RepositoryItemWriter<>();
        writer.setRepository(dogRepository);
        writer.setMethodName("save");
        return writer;
    }


    @Bean
    public Step stepDogs() {
        return new StepBuilder("stepDogs", jobRepository)
                .<DogEntity, DogEntity>chunk(1, transactionManager)
                .reader(readerDogs())
                .processor(processorDogs())
                .writer(writerDogs())
                .taskExecutor(taskExecutor()).build();
    }

    @Bean
    public Step stepParents() {
        return new StepBuilder("stepParents", jobRepository)
                .<DogEntity, DogEntity>chunk(1, transactionManager)
                .reader(readerParents())
                .processor(processorDogs())
                .writer(writerDogs())
                .taskExecutor(taskExecutor()).build();
    }
}