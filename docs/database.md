# Database Design Document

## Tables

List of all tables:

- User
  - uid PK
  - username
  - password (Hashed + Salted)
  - Salt
  - created at
- Group
  - gid PK
  - name
- Messages
  - mid PK 
  - uid 
  - gid 
  - message (HTML removed)
  - date sent
- Joined Groups
  - uid FK PK
  - gid FK PK
- Session
  - uid FK PK
  - token
  - expire