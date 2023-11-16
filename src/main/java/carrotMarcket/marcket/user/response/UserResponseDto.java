package carrotMarcket.marcket.user.response;

import carrotMarcket.marcket.user.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String loginId;

    public static UserResponseDto of(Users user) {
        return new UserResponseDto(user.getLoginId());
    }
}
