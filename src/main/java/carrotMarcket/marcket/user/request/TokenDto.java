package carrotMarcket.marcket.user.request;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
public class TokenDto {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
