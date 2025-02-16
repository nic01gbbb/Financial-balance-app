package ApplicationBalance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ApplicationBalance {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBalance.class, args);
		/*
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];  // 256-bit key
		secureRandom.nextBytes(key);
		String secretKey = Base64.getEncoder().encodeToString(key);
		System.out.println(secretKey);  // Print the key to use in your application
	   */

    }
}
