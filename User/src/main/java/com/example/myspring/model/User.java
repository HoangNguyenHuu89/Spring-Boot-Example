package com.example.myspring.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private long id;

	@Size(min = 5, message = "Username phải lớn hơn 4 ký tự")
	@Column(name = "name")
	private String name;

	@NotEmpty(message = "Thiếu birthday")
	@Pattern(regexp = "\\d{1,2}/\\d{1,2}/\\d{4}", message = "Không đúng định dạng ngày tháng")
	@Column(name = "birthday")
	private String birthday;

	@NotEmpty(message = "Thiếu email")
	@Email(message = "Email không hợp lệ")
	@Column(name = "email")
	private String email;

	public User() {
		super();
	}

	public User(long id, String name, String birthday, String email) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
		this.email = email;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
