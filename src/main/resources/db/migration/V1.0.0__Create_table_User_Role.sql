CREATE TABLE user (
      id bigint AUTO_INCREMENT,
      username NVARCHAR(255),
      email NVARCHAR(255) NOT NULL,
      password NVARCHAR(255),
      status NVARCHAR(50),
      deleted bit DEFAULT 0,
      created_by NVARCHAR(255) DEFAULT 'system',
      updated_by NVARCHAR(255),
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      PRIMARY KEY(id)
);

CREATE TABLE role(
     id bigint AUTO_INCREMENT,
     name nvarchar(255) NOT NULL,
     deleted bit DEFAULT 0,
     created_by nvarchar(255) DEFAULT 'system',
     updated_by nvarchar(255),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY(id)
);


CREATE TABLE user_role(
      id bigint(19) AUTO_INCREMENT,
      user_id bigint(19) NOT NULL,
      role_id bigint(19) NOT NULL,
      deleted bit DEFAULT 0,
      created_by NVARCHAR(255) DEFAULT 'system',
      updated_by NVARCHAR(255),
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      PRIMARY KEY(id)
);

ALTER TABLE user_role ADD CONSTRAINT FK_User_Role FOREIGN KEY (user_id) REFERENCES user(id);
ALTER TABLE user_role ADD CONSTRAINT FK_Role_User FOREIGN KEY (role_id) REFERENCES role(id);

