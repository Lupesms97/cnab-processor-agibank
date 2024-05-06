package org.processor.cnab.application.service;



import org.processor.cnab.presentation.exception.CouldNotStoreException;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ProcessorServicer {

    private final Path fileStorageLocation;
    private final Job job;
    private final JobLauncher jobLauncher;

    public ProcessorServicer(
            @Value("${file.upload-dir}")String fileUploadDir,
            final Job job,
            @Qualifier("jobLauncherAsync") final JobLauncher jobLauncher) {
        this.fileStorageLocation = Paths.get(fileUploadDir);
        this.job = job;
        this.jobLauncher = jobLauncher;
    }


    public void uploadFile(MultipartFile file) throws Exception {

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path targetLocation = fileStorageLocation.resolve(fileName);

        file.transferTo(targetLocation);


        var jobParameters = new JobParametersBuilder()
                .addJobParameter("cnab",
                        file.getOriginalFilename(),
                        String.class,
                        true)
                .addJobParameter("cnabFile",
                        "file:"+targetLocation.toString(),
                        String.class, false)
                .toJobParameters();

        jobLauncher.run(job, jobParameters) ;

    }
}
