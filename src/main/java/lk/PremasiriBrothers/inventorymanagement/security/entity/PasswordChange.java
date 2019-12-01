package lk.PremasiriBrothers.inventorymanagement.security.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class PasswordChange {

    private String username;

    @Size(min = 4, message = "password should include four characters or symbols")
    private String opsw;

    @Size(min = 4, message = "password should include four characters or symbols")
    private String npsw;

    @Size(min = 4, message = "password should include four characters or symbols")
    private String nrepsw;

}