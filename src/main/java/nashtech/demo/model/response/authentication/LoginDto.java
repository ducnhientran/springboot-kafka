package nashtech.demo.model.response.authentication;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {

    private String token;

}
