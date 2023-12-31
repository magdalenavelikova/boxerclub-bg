package bg.boxerclub.boxerclubbgrestserver.service.impl;

import bg.boxerclub.boxerclubbgrestserver.repository.DogRepository;
import bg.boxerclub.boxerclubbgrestserver.repository.LinkRepository;
import bg.boxerclub.boxerclubbgrestserver.service.JobService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {
    private final JobLauncher jobLauncher;
    private final Job job;
    private final LinkRepository linksRepository;
    private final DogRepository dogRepository;

    public JobServiceImpl(JobLauncher jobLauncher, Job job, LinkRepository linkRepository, DogRepository dogRepository) {
        this.jobLauncher = jobLauncher;
        this.job = job;
        this.linksRepository = linkRepository;
        this.dogRepository = dogRepository;
    }

    @Override
    public void init() {
        if (linksRepository.count() == 0 && dogRepository.count() == 0) {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt", System.currentTimeMillis())
                    .toJobParameters();
            try {
                jobLauncher.run(job, jobParameters);
            } catch (JobExecutionAlreadyRunningException | JobParametersInvalidException |
                     JobInstanceAlreadyCompleteException | JobRestartException e) {
                throw new RuntimeException(e);
            }
        }
    }
}