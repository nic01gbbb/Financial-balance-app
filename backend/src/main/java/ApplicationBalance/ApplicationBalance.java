package ApplicationBalance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;


@SpringBootApplication
public class ApplicationBalance {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationBalance.class, args);
        BigDecimal num1 = new BigDecimal("10785.06");
        BigDecimal num2 = new BigDecimal("14598.04");

        // Multiply num1 by num2
        BigDecimal resultMultiply = num1.subtract(num2);
        System.out.println("Multiplication result: " + resultMultiply);



        /*
		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[32];  // 256-bit key
		secureRandom.nextBytes(key);
		String secretKey = Base64.getEncoder().encodeToString(key);
		System.out.println(secretKey);  // Print the key to use in your application
	   */

    }
}
