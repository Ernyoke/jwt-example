package esz.dev.jwtexample.security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtError {
    private String message;
}
