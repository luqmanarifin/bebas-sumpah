# READ ME

A secure mobile email client, embedded with digital signature using Elliptic Curve Digital Signature Algorithm and SHA-1. Encrypted with Bonek Encryption Algorithm (BEA), symmetric block cipher encryption.

What user can do with this mobile email client :

1. Get public key and private key for Elliptic Curve Digital Signature

2. Write email, enter recipient email address, write email subject

3. View main inbox, sent email

4. Attach a file to email

5. Choose whether the mail will be encrypted or not
  * If it is encrypted, the mail will be encrypted with Bonek Encryption Algorithm
  * If not, so it just a plain text sent
  * User will enter the symmetric key if the receive or send encrypted mail

6. Choose whether the mail will be signatured
  * If it is signatured, the mail will be encrypted with 521-bit Elliptic Curve Digital Signature. This is as safety as 1024-bit key RSA asymmetric encryption
  * User will enter the public key of sender if they receive the email
  * User will enter the private key of their own if they send email
  * The signature is located at the end of the mail
  * If not, so it just a plain text sent without signature

7. Supported only on Android