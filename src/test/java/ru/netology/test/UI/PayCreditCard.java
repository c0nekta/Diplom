package ru.netology.test.UI;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.CreditPage;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DataHelper.*;
import static ru.netology.data.SQLHelper.*;

public class PayCreditCard {



    @BeforeAll
    public static void setUpAll() {

        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void openPage() {

        open("http://localhost:8080");
    }

    CreditPage creditPage = new CreditPage();
    MainPage mainPage = new MainPage();

    @AfterEach
    void cleanDB() {

        SQLHelper.databaseCleanUp();
    }

    @AfterAll
    public static void tearDownAll() {

        SelenideLogger.removeListener("allure");
    }

    @Test
    @SneakyThrows
    @DisplayName("Покупка кредитной картой")
    void shouldApproveCreditCard() {
        mainPage.buyCreditCard();
        var info = getApprovedCard();
        creditPage.sendingValidData(info);
        creditPage.bankApproved();
        var expected = DataHelper.getStatusFirstCard();
        var creditRequest = getCreditRequestInfo();
        var orderInfo = getOrderInfo();
        assertEquals(expected, getCreditRequestInfo().getStatus());
        assertEquals(orderInfo.getPayment_id(), creditRequest.getBank_id());
    }


    @Test
    @DisplayName("Покупка кредитной картой: пустое поле")
    void shouldEmptyFormWithCredit() {
        mainPage.buyCreditCard();
        creditPage.pressButtonForContinue();
        creditPage.emptyForm();

    }

    @Test
    @DisplayName("Покупка кредитной картой без заполнения поля карты, остальные поля - валидные данные")
    public void shouldEmptyFieldCardWithCredit() {
        mainPage.buyCreditCard();
        var info = getEmptyCardNumber();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поля карты одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldCardNumberWithCredit() {
        mainPage.buyCreditCard();
        var info = getOneNumberCardNumber();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поля карты 15 цифрами, остальные поля - валидные данные")
    public void shouldFifteenNumberInFieldCardNumberWithCredit() {
        mainPage.buyCreditCard();
        var info = getFifteenNumberCardNumber();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldCardNumberError();
    }

    @Test
    @DisplayName("Покупка кредитной картой неизвестной картой при заполнения поля карты, остальные поля - валидные данные")
    public void shouldFakerCardInFieldCardNumberWithCredit() {
        mainPage.buyCreditCard();
        var info = getFakerNumberCardNumber();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFakerCardNumber();
    }

    @Test
    @DisplayName("Покупка кредитной картой без заполнения поля месяц, остальные поля - валидные данные")
    public void shouldEmptyFieldMonthWithCredit() {
        mainPage.buyCreditCard();
        var info = getEmptyMonth();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: поле месяц одной цифрой, остальные поля - валидные данные")
    public void shouldOneNumberInFieldMonthWithCredit() {
        mainPage.buyCreditCard();
        var info = getOneNumberMonth();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: в поле месяц предыдущий от текущего, остальные поля -валидные данные")
    public void shouldFieldWithPreviousMonthWithCredit() {
        mainPage.buyCreditCard();
        var info = getPreviousMonthInField();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: в поле месяц нулевой (не существующий) месяц" +
            " остальные поля -валидные данные")
    public void shouldFieldWithZeroMonthWithCredit() {
        mainPage.buyCreditCard();
        var info = getZeroMonthInField();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка кредитной картой:  в поле месяц в верном формате тринадцатый (не существующий) месяц" +
            " остальные поля -валидные данные")
    public void shouldFieldWithThirteenMonthWithCredit() {
        mainPage.buyCreditCard();
        var info = getThirteenMonthInField();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldMonthError();
    }

    @Test
    @DisplayName("Покупка кредитной картой без заполнения поля год, остальные поля -валидные данные")
    public void shouldEmptyFieldYearWithCredit() {
        mainPage.buyCreditCard();
        var info = getEmptyYear();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поля год, предыдущим годом от текущего" +
            " остальные поля -валидные данные")
    public void shouldPreviousYearFieldYearWithCredit() {
        mainPage.buyCreditCard();
        var info = getPreviousYearInField();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поля год, на шесть лет больше чем текущий" +
            " остальные поля -валидные данные")
    public void shouldPlusSixYearFieldYearWithCredit() {
        mainPage.buyCreditCard();
        var info = getPlusSixYearInField();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldYearError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: поле владелец пустое, остальные поля -валидные данные")
    public void shouldEmptyFieldNameWithCredit() {
        mainPage.buyCreditCard();
        var info = getApprovedCard();
        creditPage.sendingEmptyNameValidData(info);
        creditPage.sendingValidDataWithFieldNameError();
    }


    @Test
    @DisplayName("Покупка кредитной картой: заполнение поля владелец спец. символами" +
            " остальные поля -валидные данные")
    public void shouldSpecialSymbolInFieldNameWithCredit() {
        mainPage.buyCreditCard();
        var info = getSpecialSymbolInFieldName();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поля владелец цифрами" +
            " остальные поля -валидные данные")
    public void shouldNumberInFieldNameWithCredit() {
        mainPage.buyCreditCard();
        var info = getNumberInFieldName();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поле владелец русскими буквами" +
            " остальные поля -валидные данные")
    public void shouldRussianNameInFieldNameWithCredit() {
        mainPage.buyCreditCard();
        var info = getRusName();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldNameError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: заполнение поле владелец только фамилией" +
            " остальные поля -валидные данные")
    public void shouldOnlySurnameInFieldNameWithCredit() {
        mainPage.buyCreditCard();
        var info = getOnlySurnameInFieldName();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldNameError();
    }


    @Test
    @DisplayName("Покупка кредитной картой: поле CVV пустое" +
            " остальные поля -валидные данные")
    public void shouldEmptyCVVInFieldCVVWithCredit() {
        mainPage.buyCreditCard();
        var info = getEmptyCVVInFieldCVV();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: поле CVV одним числом" +
            " остальные поля -валидные данные")
    public void shouldOneNumberInFieldCVVWithCredit() {
        mainPage.buyCreditCard();
        var info = getOneNumberInFieldCVV();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldCVVError();
    }

    @Test
    @DisplayName("Покупка кредитной картой: поле CVV двумя числами" +
            " остальные поля -валидные данные")
    public void shouldTwoNumberInFieldCVVWithCredit() {
        mainPage.buyCreditCard();
        var info = getOTwoNumberInFieldCVV();
        creditPage.sendingValidData(info);
        creditPage.sendingValidDataWithFieldCVVError();
    }

}





