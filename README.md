## Spring Verify

Spring Verify is a robust API built on Java Spring framework, designed to streamline user verification processes. Upon signup, it seamlessly triggers an email confirmation to the user. By clicking on the link provided in the email, the user's account is instantly activated, ensuring a secure and efficient onboarding experience.

### Features
- **Email Verification**: Automatically sends an email to the user upon signup for account activation.
- **Secure Activation**: Users can activate their accounts securely by clicking on the verification link in the email.
- **Easy Integration**: Built on Java Spring framework.

### Installation
To set up Spring Verify in your project, follow these steps:
1. **Clone the Repository**: Clone this repository to your local machine using:
    ```
    git clone https://github.com/priyanshu16095/spring-verify.git
    ```
2. **Configure Email Service**: Configure the email service settings in the `application.properties` file to match your SMTP server credentials.
3. **Build and Run**: Build and run the application using Maven:
    ```
    mvn clean install
    mvn spring-boot:run
    ```
