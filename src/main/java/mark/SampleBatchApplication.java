package mark;

import java.sql.Date;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class SampleBatchApplication {
	
	@Autowired
	private JobBuilderFactory jobs;

	@Autowired
	private StepBuilderFactory steps;

	@Bean
	protected Tasklet tasklet() {
		return new Tasklet() {
			@Override
			public RepeatStatus execute(StepContribution contribution,
					ChunkContext context) {
				return RepeatStatus.FINISHED;
			}
		};
	}

	@Bean
	public Job job() throws Exception {
		return this.jobs.get("job")
				.incrementer(new JobParametersIncrementer() {

					@Override
					public JobParameters getNext(JobParameters parameters) {
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						long id=0;
				        if (parameters==null || parameters.isEmpty()) {
				            id=1;
				        }else{
				            id = parameters.getLong("run.id",1L) + 1;
				        }
				        JobParametersBuilder builder = new JobParametersBuilder();
				 
				        builder.addLong("run.id", id).toJobParameters();
				        builder.addLong("EXECUTION_DATE", new Date(System.currentTimeMillis()).getTime());
				 
				        return builder.toJobParameters();
					}
					
				})
				.start(step1())
				.build();
	}

	@Bean
	protected Step step1() throws Exception {
		return this.steps.get("step1").tasklet(tasklet()).build();
	}
	
	@Bean
	public DataSource dataSource() {

	    BasicDataSource ds = new BasicDataSource();

	    try {
	        ds.setDriverClassName("com.mysql.jdbc.Driver");
	        ds.setUsername("root");
	        ds.setPassword("");
	        ds.setUrl("jdbc:mysql://127.0.0.1/spring-batch");

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return ds;
	}

}
