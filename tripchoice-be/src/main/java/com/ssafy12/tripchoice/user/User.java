package com.ssafy12.tripchoice.user;

import com.ssafy12.tripchoice.auth.UserRole;
import com.ssafy12.tripchoice.common.validation.AssertionMessage;
import com.ssafy12.tripchoice.common.auditing.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.Where;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.apache.commons.lang3.EnumUtils.isValidEnum;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at is null")
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false)
    private String name;

    @Setter
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    private LocalDateTime deletedAt;

    @Builder
    private User(String email, String password, String name, byte[] image) {
        checkArgument(isNotEmpty(email));
        checkArgument(isNotEmpty(password));
        checkArgument(isNotEmpty(name));

        this.email = email;
        this.password = password;
        this.name = name;
        this.role = UserRole.USER;
        this.image = image;

        Assert.isTrue(isNotEmpty(this.email), AssertionMessage.NOT_EMPTY);
        Assert.isTrue(isNotEmpty(this.password), AssertionMessage.NOT_EMPTY);
        Assert.isTrue(isNotEmpty(this.name), AssertionMessage.NOT_EMPTY);
    }

    public void changeRole(UserRole role) {
        checkNotNull(role);
        this.role = role;
        assert (isValidEnum(UserRole.class, role.name()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;

        if (!Objects.equals(id, user.id)) return false;
        if (!Objects.equals(email, user.email)) return false;
        if (!Objects.equals(password, user.password)) return false;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", id)
                .append("email", email)
                .append("password", "[PROTECTED]")
                .append("name", name)
                .append("createAt", getCreatedAt())
                .append("lastModifiedAt", getLastModifiedAt())
                .build();
    }
}
