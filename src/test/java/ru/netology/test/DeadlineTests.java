package ru.netology.test;

import org.junit.jupiter.api.*;

import ru.netology.data.DataHelper;
import ru.netology.data.SqlHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SqlHelper.cleanAuthCodes;
import static ru.netology.data.SqlHelper.cleanDB;

public class DeadlineTests {
    LoginPage loginPage;

    @AfterEach
    void tearDown(){
        cleanAuthCodes();
    }

    @AfterAll
    static void cleaner() {
        cleanDB();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }


    @Test
    @DisplayName("Should successfully login to dashboard with exist login and password from sut test data")
    void shouldSuccessFullLogin(){
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyPageVisibility();
        var verificationCode = SqlHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }
    @Test
    @DisplayName("Should get error notification if user is not exist in base")
    void shouldGetErrorNotificationIfLoginWithRandomUser(){
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyError();
    }
    @Test
    @DisplayName("Should get error notification if user is not exist in base and active user and " +
            "random verification code")
    void shouldGetErrorNotificationIfLoginWithRandomUserVerificationCode(){
        var authInfo = DataHelper.getAuthInfoWithTestData();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode().getCode();
        verificationPage.verify(verificationCode);
        verificationPage.getError();
    }
}
