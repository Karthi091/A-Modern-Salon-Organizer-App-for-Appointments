package com.example.demo;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.Repository.AppointmentRepository;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.RewardRepository;
import com.example.demo.configuration.SwaggerConfig;
import com.example.demo.entity.Appointment;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Reward;
import com.example.demo.service.AppointmentService;
import com.example.demo.service.RewardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;


import io.swagger.v3.oas.models.OpenAPI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CustomerRepositoryTests {

    private static final String LOG_FOLDER_PATH = "logs";
    private static final String LOG_FILE_PATH = "logs/application.log";

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private AppointmentService appointmentService;

    
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private RewardRepository rewardRepository;

    @Autowired
    private RewardService rewardService;

    @Before(value = "")
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    


    @Test
    public void CRUD_testCreateCustomer() {
        Customer customer = new Customer();
        customer.setUsername("john_doe");
        customer.setEmail("john@example.com");
        customer.setPassword("password123");

        Customer savedCustomer = customerRepository.save(customer);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getId()).isNotNull();
        assertThat(savedCustomer.getUsername()).isEqualTo("john_doe");
    }

	@Test
    public void CRUD_testRetrieveCustomerById() {
        Customer customer = new Customer();
        customer.setUsername("jane_doe");
        customer.setEmail("jane@example.com");
        customer.setPassword("securepass");

        Customer savedCustomer = customerRepository.save(customer);

        Optional<Customer> retrievedCustomer = customerRepository.findById(savedCustomer.getId());
        assertThat(retrievedCustomer).isPresent();
        assertThat(retrievedCustomer.get().getUsername()).isEqualTo("jane_doe");
    }

    @Test
    public void CRUD_testUpdateCustomerEmail() {
        Customer customer = new Customer();
        customer.setUsername("mike_ross");
        customer.setEmail("mike@example.com");
        customer.setPassword("initialpass");

        Customer savedCustomer = customerRepository.save(customer);

        savedCustomer.setEmail("mike_updated@example.com");
        Customer updatedCustomer = customerRepository.save(savedCustomer);

        assertThat(updatedCustomer.getEmail()).isEqualTo("mike_updated@example.com");
    }
    @Test
    public void CRUD_testDeleteCustomer() {
        Customer customer = new Customer();
        customer.setUsername("sarah_connor");
        customer.setEmail("sarah@example.com");
        customer.setPassword("password456");

        Customer savedCustomer = customerRepository.save(customer);

        customerRepository.delete(savedCustomer);

        Optional<Customer> deletedCustomer = customerRepository.findById(savedCustomer.getId());
        assertThat(deletedCustomer).isNotPresent();
    }

    @Test
    public void CRUD_testFindCustomerByUsername() {
        Customer customer = new Customer();
        customer.setUsername("clark_kent");
        customer.setEmail("clark@example.com");
        customer.setPassword("supersecure");

        customerRepository.save(customer);

        Customer retrievedCustomer = customerRepository.findByUsername("clark_kent");
        assertThat(retrievedCustomer).isNotNull();
        assertThat(retrievedCustomer.getEmail()).isEqualTo("clark@example.com");
    }
    @Test
    public void CRUD_testCreateAppointment() {
        Appointment appointment = new Appointment();
        appointment.setServiceName("Haircut");
        appointment.setAppointmentTime(LocalDateTime.of(2025, 1, 15, 10, 0));
        appointment.setStatus("Pending");

        Appointment savedAppointment = appointmentRepository.save(appointment);

        assertThat(savedAppointment).isNotNull();
        assertThat(savedAppointment.getId()).isNotNull();
        assertThat(savedAppointment.getServiceName()).isEqualTo("Haircut");
        assertThat(savedAppointment.getStatus()).isEqualTo("Pending");
    }
    @Test
    public void CRUD_testRetrieveAppointmentById() {
        Appointment appointment = new Appointment();
        appointment.setServiceName("Dental Checkup");
        appointment.setAppointmentTime(LocalDateTime.of(2025, 1, 20, 15, 30));
        appointment.setStatus("Confirmed");

        Appointment savedAppointment = appointmentRepository.save(appointment);

        Optional<Appointment> retrievedAppointment = appointmentRepository.findById(savedAppointment.getId());
        assertThat(retrievedAppointment).isPresent();
        assertThat(retrievedAppointment.get().getServiceName()).isEqualTo("Dental Checkup");
        assertThat(retrievedAppointment.get().getStatus()).isEqualTo("Confirmed");
    }
    @Test
    public void CRUD_testUpdateAppointmentStatus() {
        Appointment appointment = new Appointment();
        appointment.setServiceName("Spa");
        appointment.setAppointmentTime(LocalDateTime.of(2025, 1, 25, 12, 0));
        appointment.setStatus("Pending");

        Appointment savedAppointment = appointmentRepository.save(appointment);

        savedAppointment.setStatus("Completed");
        Appointment updatedAppointment = appointmentRepository.save(savedAppointment);

        assertThat(updatedAppointment.getStatus()).isEqualTo("Completed");
    }
    @Test
    public void CRUD_testDeleteAppointment() {
        Appointment appointment = new Appointment();
        appointment.setServiceName("Massage");
        appointment.setAppointmentTime(LocalDateTime.of(2025, 1, 30, 14, 0));
        appointment.setStatus("Pending");

        Appointment savedAppointment = appointmentRepository.save(appointment);

        appointmentRepository.delete(savedAppointment);

        Optional<Appointment> deletedAppointment = appointmentRepository.findById(savedAppointment.getId());
        assertThat(deletedAppointment).isNotPresent();
    }

    @Test
    public void CRUD_testRetrieveAllAppointments() {
        Appointment appointment1 = new Appointment();
        appointment1.setServiceName("Facial");
        appointment1.setAppointmentTime(LocalDateTime.of(2025, 2, 5, 11, 0));
        appointment1.setStatus("Confirmed");

        Appointment appointment2 = new Appointment();
        appointment2.setServiceName("Manicure");
        appointment2.setAppointmentTime(LocalDateTime.of(2025, 2, 10, 16, 0));
        appointment2.setStatus("Pending");

        appointmentRepository.save(appointment1);
        appointmentRepository.save(appointment2);

        List<Appointment> appointments = appointmentRepository.findAll();

        assertThat(appointments).isNotEmpty();
        assertThat(appointments.size()).isGreaterThanOrEqualTo(2);
    }
@Test
    public void CRUD_testCreateReward() {
        Reward reward = new Reward();
        reward.setPoints(100);

        Reward savedReward = rewardRepository.save(reward);

        assertThat(savedReward).isNotNull();
        assertThat(savedReward.getId()).isNotNull();
        assertThat(savedReward.getPoints()).isEqualTo(100);
    }

    @Test
    public void CRUD_testRetrieveRewardById() {
        Reward reward = new Reward();
        reward.setPoints(200);

        Reward savedReward = rewardRepository.save(reward);

        Reward retrievedReward = rewardRepository.findById(savedReward.getId()).orElse(null);

        assertThat(retrievedReward).isNotNull();
        assertThat(retrievedReward.getPoints()).isEqualTo(200);
    }

    @Test
    public void CRUD_testUpdateRewardPoints() {
        Reward reward = new Reward();
        reward.setPoints(150);

        Reward savedReward = rewardRepository.save(reward);

        savedReward.setPoints(250);
        Reward updatedReward = rewardRepository.save(savedReward);

        assertThat(updatedReward).isNotNull();
        assertThat(updatedReward.getPoints()).isEqualTo(250);
    }

    @Test
    public void CRUD_testDeleteReward() {
        Reward reward = new Reward();
        reward.setPoints(300);

        Reward savedReward = rewardRepository.save(reward);

        rewardRepository.delete(savedReward);

        Reward deletedReward = rewardRepository.findById(savedReward.getId()).orElse(null);

        assertThat(deletedReward).isNull();
    }

    @Test
    public void CRUD_testRetrieveAllRewards() {
        Reward reward1 = new Reward();
        reward1.setPoints(100);

        Reward reward2 = new Reward();
        reward2.setPoints(200);

        rewardRepository.save(reward1);
        rewardRepository.save(reward2);

        List<Reward> rewards = rewardRepository.findAll();

        assertThat(rewards).isNotEmpty();
        assertThat(rewards.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
public void Mapping_testCreateCustomerAndMapAppointments() {
    Customer customer = new Customer();
    customer.setUsername("JohnDoe");
    customer.setEmail("john.doe@example.com");
    customer.setPassword("password");

    Appointment appointment1 = new Appointment();
    appointment1.setServiceName("Consultation");
    appointment1.setAppointmentTime(LocalDateTime.now().plusDays(1));
    appointment1.setStatus("Pending");
    appointment1.setCustomer(customer);

    Appointment appointment2 = new Appointment();
    appointment2.setServiceName("Follow-up");
    appointment2.setAppointmentTime(LocalDateTime.now().plusDays(2));
    appointment2.setStatus("Pending");
    appointment2.setCustomer(customer);

    customer.setAppointments(Arrays.asList(appointment1, appointment2));

    customerRepository.save(customer);

    // Use the new method to fetch the customer with appointments eagerly loaded
    Customer savedCustomer = customerRepository.findByIdWithAppointments(customer.getId()).orElse(null);

    if (savedCustomer != null) {
        System.out.println("Customer retrieved: " + savedCustomer.getUsername());
        System.out.println("Appointments:");
        savedCustomer.getAppointments().forEach(appointment -> {
            System.out.println("Service Name: " + appointment.getServiceName());
            System.out.println("Status: " + appointment.getStatus());
            System.out.println("Appointment Time: " + appointment.getAppointmentTime());
        });
    } else {
        System.out.println("No customer found with the given ID.");
    }
}

    

    @Test
    public void Mapping_testCreateRewardAndMapToCustomer() {
        Customer customer = new Customer();
        customer.setUsername("JaneDoe");
        customer.setEmail("jane.doe@example.com");
        customer.setPassword("password");

        Reward reward = new Reward();
        reward.setPoints(500);
        reward.setCustomer(customer);

        customer.setReward(reward);

        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getReward()).isNotNull();
        assertThat(savedCustomer.getReward().getPoints()).isEqualTo(500);
    }

    @Test
    public void Mapping_testDeleteCustomerAndCascadeDeleteAppointments() {
        Customer customer = new Customer();
        customer.setUsername("MarkSmith");
        customer.setEmail("mark.smith@example.com");
        customer.setPassword("password");

        Appointment appointment = new Appointment();
        appointment.setServiceName("Routine Check");
        appointment.setAppointmentTime(LocalDateTime.now().plusDays(1));
        appointment.setStatus("Pending");
        appointment.setCustomer(customer);

        customer.setAppointments(Arrays.asList(appointment));

        customerRepository.save(customer);
        customerRepository.delete(customer);

        Appointment deletedAppointment = appointmentRepository.findById(appointment.getId()).orElse(null);

        assertThat(deletedAppointment).isNull();
    }

    @Test
    public void Mapping_testUpdateRewardPointsForCustomer() {
        Customer customer = new Customer();
        customer.setUsername("LucasBrown");
        customer.setEmail("lucas.brown@example.com");
        customer.setPassword("password");

        Reward reward = new Reward();
        reward.setPoints(100);
        reward.setCustomer(customer);

        customer.setReward(reward);
        customerRepository.save(customer);

        // Updating reward points
        reward.setPoints(200);
        rewardRepository.save(reward);

        Customer savedCustomer = customerRepository.findById(customer.getId()).orElse(null);

        assertThat(savedCustomer).isNotNull();
        assertThat(savedCustomer.getReward().getPoints()).isEqualTo(200);
    }

    @Test
    public void Repository_testCustomerRepositoryExists() {
        assertNotNull(customerRepository, "CustomerRepository should be loaded into the application context");
    }

    @Test
    public void Repository_testAppointmentRepositoryExists() {
        assertNotNull(appointmentRepository, "AppointmentRepository should be loaded into the application context");
    }

    @Test
    public void Repository_testRewardRepositoryExists() {
        assertNotNull(rewardRepository, "RewardRepository should be loaded into the application context");
    }

     @Test
    public void Annotation_testPasswordIsIgnored() throws Exception {
        // Create a customer
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setUsername("JohnDoe");
        customer.setEmail("john.doe@example.com");
        customer.setPassword("securepassword");

        // Serialize the customer to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResult = objectMapper.writeValueAsString(customer);

        // Print the serialized JSON for verification
        System.out.println("Serialized JSON: " + jsonResult);

        // Assert that the JSON contains the username and email
        assertTrue(jsonResult.contains("JohnDoe"), "JSON should contain the username");
        assertTrue(jsonResult.contains("john.doe@example.com"), "JSON should contain the email");

        // Assert that the JSON does NOT contain the password
        assertFalse(jsonResult.contains("securepassword"), "JSON should not contain the password field");
    }

    @Test
public void JPQL_testFindByIdWithAppointmentsEmpty() {
    // Test finding a customer by ID with appointments when no such customer exists
    Optional<Customer> result = customerRepository.findByIdWithAppointments(999L); // ID that doesn't exist
    
    // Assert the result is empty
    assertThat(result).isEmpty();
}

@Test
public void JPQL_testUpdateReward() {
    // Test updating a Reward object
    Reward reward = new Reward();
    reward.setName("Old Reward");
    reward.setPoints(100);
    rewardRepository.save(reward);  // Persist the initial reward

    // Update the reward
    reward.setName("Updated Reward");
    reward.setPoints(200);
    Reward updatedReward = rewardService.updateReward(reward);

    // Assert the updated reward has the correct values
    assertThat(updatedReward.getName()).isEqualTo("Updated Reward");
    assertThat(updatedReward.getPoints()).isEqualTo(200);
}

@Test
    public void SwaggerUI_testConfigurationFolder() {
        String directoryPath = "src/main/java/com/example/demo/configuration"; // Replace with the path to your directory
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory(), "The specified configuration folder should exist and be a directory.");
    }
 
    @Test
    public void SwaggerUI_testCustomOpenAPIBeanCreation() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SwaggerConfig.class);
        OpenAPI openAPI = context.getBean(OpenAPI.class);
 
        assertNotNull(openAPI, "OpenAPI bean should not be null.");
        Info info = openAPI.getInfo();
        assertNotNull(info, "OpenAPI Info should not be null.");
        assertEquals("My API", info.getTitle(), "API title should match the expected value.");
        assertEquals("1.0", info.getVersion(), "API version should match the expected value.");
        assertEquals("API documentation using Swagger", info.getDescription(), "API description should match the expected value.");
        context.close();
    }
 
    @Test
    public void SwaggerUI_testCustomOpenAPIMethodIsAnnotatedWithBean() throws NoSuchMethodException {
        Method method = SwaggerConfig.class.getDeclaredMethod("customOpenAPI");
        Bean beanAnnotation = method.getAnnotation(Bean.class);
        assertTrue(beanAnnotation != null, "customOpenAPI method should be annotated with @Bean.");
    }
 
    @Test
    public void SwaggerUI_testConfigurationAnnotation() {
        Configuration configurationAnnotation = SwaggerConfig.class.getAnnotation(Configuration.class);
        assertTrue(configurationAnnotation != null, "SwaggerConfig should be annotated with @Configuration.");
    }

    @Test
    void AOP_testAOPConfigFile() {
        // Path to the LoggingAspect class file
        String filePath = "src/main/java/com/example/demo/aspect/LoggingAspect.java";

        // Create a File object
        File file = new File(filePath);

        // Assert that the file exists and is a valid file
        assertTrue(file.exists() && file.isFile(), "LoggingAspect.java file should exist at the specified path.");
    }

    @Test
    void AOP_testAOPConfigFileAspect() throws Exception {
        // Path to the LoggingAspect class file
        Path aspectFilePath = Paths.get("src/main/java/com/example/demo/aspect/LoggingAspect.java");

        // Read the content of the aspect file
        String aspectFileContent = Files.readString(aspectFilePath);

        // Check if the file contains @Aspect annotation to ensure it's an Aspect class
        assertTrue(aspectFileContent.contains("@Aspect"), "The LoggingAspect.java should be annotated with @Aspect.");

        // Check if the file contains the logger definition to ensure logging functionality is implemented
        assertTrue(aspectFileContent.contains("private static final Logger logger"), "The LoggingAspect.java should define a logger for logging.");
    }

    @Test
    void AOP_testAspectMethods() throws Exception {
        // Path to the LoggingAspect class file
        Path aspectFilePath = Paths.get("src/main/java/com/example/demo/aspect/LoggingAspect.java");

        // Read the content of the aspect file
        String aspectFileContent = Files.readString(aspectFilePath);

        // Check for @Before and @AfterReturning annotations to ensure aspect methods are properly defined
        assertTrue(aspectFileContent.contains("@Before"), "@Before annotation should be present in the LoggingAspect.java");
        assertTrue(aspectFileContent.contains("@AfterReturning"), "@AfterReturning annotation should be present in the LoggingAspect.java");
    }

    @Test
    void AOP_testLoggingStatements() throws Exception {
        // Path to the LoggingAspect class file
        Path aspectFilePath = Paths.get("src/main/java/com/example/demo/aspect/LoggingAspect.java");

        // Read the content of the aspect file
        String aspectFileContent = Files.readString(aspectFilePath);

        // Check if logging statements are present in the aspect methods
        assertTrue(aspectFileContent.contains("logger.info"), "The LoggingAspect.java should contain logger.info statements for logging.");
    }

    @Test
    
    public void LOG_testLogFolderAndFileCreation() {
        // Check if the "logs" folder exists
        File logFolder = new File(LOG_FOLDER_PATH);
        assertTrue(logFolder.exists(), "Log folder should be created");
 
        // Check if the "application.log" file exists inside the "logs" folder
        File logFile = new File(LOG_FILE_PATH);
        assertTrue(logFile.exists(), "Log file should be created inside 'logs' folder");
    }

    @Test
    public void PaginateSorting_testGetAllAppointmentsPagination() throws Exception {
        // Prepare mock data (simple appointments for pagination testing)
        Appointment appointment1 = new Appointment();
        appointment1.setId(1L);
        appointment1.setServiceName("Service 1");
        
        Appointment appointment2 = new Appointment();
        appointment2.setId(2L);
        appointment2.setServiceName("Service 2");

        List<Appointment> appointments = Arrays.asList(appointment1, appointment2);

        // Mock the service layer

        
        
    }

    @Test
public void PaginateSorting_testGetAllAppointmentsSorting() throws Exception {
    // Prepare mock data
    Appointment appointment1 = new Appointment();
    appointment1.setId(1L);
    appointment1.setServiceName("Service B");

    Appointment appointment2 = new Appointment();
    appointment2.setId(2L);
    appointment2.setServiceName("Service A");

    // Sort the mock data by serviceName in ascending order
    List<Appointment> appointments = Arrays.asList(appointment1, appointment2);
    appointments.sort(Comparator.comparing(Appointment::getServiceName));

    // Create a Page object for sorted content
    Page<Appointment> appointmentPage = mock(Page.class);
    when(appointmentPage.getContent()).thenReturn(appointments);

    
}


   









}








