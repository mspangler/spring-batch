package mark;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class JobLauncherController {
	
	@Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job job;

    @RequestMapping("/jobLauncher.html")
    @ResponseBody
    public String handle() throws Exception{
        jobLauncher.run(job, new JobParametersBuilder().addLong("date", System.currentTimeMillis()).toJobParameters());
        return "Started the batch...";
    }

}
