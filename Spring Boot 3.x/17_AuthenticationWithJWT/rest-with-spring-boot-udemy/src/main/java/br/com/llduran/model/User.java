package br.com.llduran.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails, Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "password")
	private String password;

	@Column(name = "account_non_expired")
	private Boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private Boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private Boolean credentialsNonExpired;

	@Column(name = "enabled")
	private Boolean enabled;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_permission", joinColumns = {@JoinColumn (name = "id_user")},
			inverseJoinColumns = {@JoinColumn (name = "id_permission")}
	)
	private List<Permission> permissions;

	public List<String> getRoles()
	{
		List<String> roles = new ArrayList<>();

		permissions.forEach(p -> roles.add(p.getDescription()));

		return	roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities()
	{
		return this.permissions;
	}

	@Override
	public String getPassword()
	{
		return this.password;
	}

	@Override
	public String getUsername()
	{
		return this.userName;
	}

	@Override
	public boolean isAccountNonExpired()
	{
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked()
	{
		return this.accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired()
	{
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isEnabled()
	{
		return this.enabled;
	}
}
