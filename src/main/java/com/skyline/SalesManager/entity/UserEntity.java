package com.skyline.SalesManager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "[User]")
public class UserEntity extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_user;

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "phone", unique = true)
    @Pattern(regexp = "^0[0-9]{9}$")
    private String phone;

    @Column(name = "[password]")
    private String password;

    @Column(name = "avatar")
    private String avatar;

    @Column(name = "status")
    private Integer status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    List<RoleEntity> roles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_voucher", joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_voucher"))
    List<VoucherEntity> vouchers = new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roles.stream().forEach(i -> authorities.add(new SimpleGrantedAuthority(i.getRole_name())));
        return List.of(new SimpleGrantedAuthority(authorities.toString())); // trả về danh sách all role của acc và được add vào trong authorities
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
