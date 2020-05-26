package springbatch.springbatch.config;



import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import springbatch.springbatch.model.User;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ItemReader;



@Configuration
@EnableBatchProcessing
public class SpringBatchConfiguration {




    ///////////////////// this is the job that will be executed
    //////////////////////////////////////////////////////////
    @Bean
    public Job job(JobBuilderFactory getJobBuilderFactory,
                   StepBuilderFactory getStepBuilderFactory,
                   ItemReader<User> getItemReader,
                   ItemProcessor<User, User> getItemProcessor,
                   ItemWriter<User> getItemWriter){

        // we create the step
        Step step = getStepBuilderFactory.get("step-1")
                // <User,User> is just casting the chunk
                .<User, User>chunk(100)
                .reader(getItemReader)
                .processor(getItemProcessor)
                .writer(getItemWriter)
                .build();


      // we create the job from the step
      Job myJob =  getJobBuilderFactory.get("job-1")
              .incrementer(new RunIdIncrementer())
              .start(step)
              .build();


    return myJob;
    };
    ///////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////




    ////////the line mapper is responsible for reading the lines from csv file
    ///////////////////////////////////////////////////////////////////////////
    @Bean
    public LineMapper<User> readLines(){

        // we create a line reader for csv file
        DefaultLineMapper<User> defaultLineMapper = new DefaultLineMapper<>();

        // we create a token which holds all the line
        DelimitedLineTokenizer delimitedLineTokenize = new DelimitedLineTokenizer();
        delimitedLineTokenize .setDelimiter(",");
        delimitedLineTokenize.setStrict(false);
        delimitedLineTokenize.setNames( new String[]{ "id", "name", "dep", "salary"});


        // we create  a field to hold data, and we give the model that we need to fill up
        BeanWrapperFieldSetMapper<User> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(User.class);


        // we supply the line reader with the token containing the data
        defaultLineMapper.setLineTokenizer(delimitedLineTokenize);
        // we supply the line reader with the right field model
        defaultLineMapper.setFieldSetMapper(fieldSetMapper);

        return defaultLineMapper;
    }

    @Bean
    public FlatFileItemReader<User> getFlatFileUsers(@Value("${input}") Resource  users){
         // create file reader
         FlatFileItemReader<User> flatFileItemReader = new FlatFileItemReader<>();
         // supply with the row data (cvs file)
         flatFileItemReader.setResource(users);
         // name it and fix lines
         flatFileItemReader.setName("CSV-USERS-READER");
         flatFileItemReader.setLinesToSkip(1);

         // read it
         flatFileItemReader.setLineMapper(readLines());

      return flatFileItemReader;
    }
     ///////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////
}
