package marketplace.entity;

import jakarta.persistence.*;
import lombok.*;
import marketplace.entity.enums.Role;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
}
