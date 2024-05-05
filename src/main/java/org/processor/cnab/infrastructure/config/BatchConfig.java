package org.processor.cnab.infrastructure.config;

import org.processor.cnab.application.dto.TransactionCNAB;
import org.processor.cnab.domain.entity.TransactionEntity;
import org.processor.cnab.presentation.mapper.FromDto;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Configuration
public class BatchConfig {
    private final PlatformTransactionManager transactionManager;
    private final JobRepository jobRepository;

    public BatchConfig(final PlatformTransactionManager transactionManager,
                       final JobRepository jobRepository) {
        this.transactionManager = transactionManager;
        this.jobRepository = jobRepository;
    }

    @Bean
    Job job(Step step) {
        return new JobBuilder("job", jobRepository)
                .start(step)
                .incrementer(new RunIdIncrementer())
                .build();

    }

    @Bean
    Step step(
            ItemReader<TransactionCNAB> reader,
            ItemProcessor<TransactionCNAB, TransactionEntity> processor,
            ItemWriter<TransactionEntity> writer) {
        return new StepBuilder("step", jobRepository)
                .<TransactionCNAB, TransactionEntity>chunk(1000, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

    @StepScope
    @Bean
    FlatFileItemReader<TransactionCNAB> reader(
            @Value("#{jobParameters['cnabFile']}" )Resource resource
    ) {
        return new FlatFileItemReaderBuilder<TransactionCNAB>()
                .name("reader")
                .resource(resource)
                .fixedLength()
                .columns(
                        new Range(1, 1),
                        new Range(2, 9),
                        new Range(10, 19),
                        new Range(20, 30),
                        new Range(31, 42),
                        new Range(43, 48),
                        new Range(49, 62),
                        new Range(63, 80))
                .names("type",
                        "date",
                        "amount",
                        "cpf",
                        "card",
                        "hour",
                        "storeOwner",
                        "storeName")
                .targetType(TransactionCNAB.class)
                .build();
    }

    @Bean
    ItemProcessor<TransactionCNAB, TransactionEntity> processor() {
        return item -> new FromDto().toEntity(item);
    }

    @Bean
    public JdbcBatchItemWriter<TransactionEntity> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<TransactionEntity>()
                .dataSource(dataSource)
                .sql("INSERT INTO transaction_cnab (type, date, amount, cpf, card, hour, store_owner, store_name) " +
                        "VALUES (:type, :date, :amount, :cpf, :card, :hour, :storeOwner, :storeName)")
                .beanMapped()
                .build();
    }

    @Bean
    JobLauncher jobLauncherAsync(JobRepository jobRepository) throws Exception {
        var jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;



    }




}
