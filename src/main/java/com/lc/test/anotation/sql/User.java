package com.lc.test.anotation.sql;

/**
 * user实体-使用自定义注解实现映射表和字段
 * @author wlc
 */
@Table("t_user")
public class User {
	@Column("id")
	private Integer id;
	@Column("name")
	private String name;
	@Column("age")
	private Integer age;
	@Column("email")
	private String email;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
