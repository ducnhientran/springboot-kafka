package nashtech.demo.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum Messages {

    SUCCESSFUL("SUCCESSFUL"),
    UNAUTHORIZED("UNAUTHORIZED"),
    USER_NOTFOUND("USER_NOTFOUND");

    private String message;

}
