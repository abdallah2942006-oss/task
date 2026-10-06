-- Make the Manger table read-only for a specific user.
-- Replace 'username' with the actual MySQL username.

REVOKE INSERT, UPDATE, DELETE
ON Manger
FROM 'username'@'localhost';