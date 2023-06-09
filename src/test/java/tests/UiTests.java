package tests;

import api.ApiData;
import api.BookAttributes;
import api.BookStoreModel;
import api.RequestToApi;
import helpers.AttachOnFailedTest;
import helpers.TestNGRetry;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import dataGenerator.Generator;
import org.testng.annotations.Test;


@Listeners(AttachOnFailedTest.class)
public class UiTests extends BaseUITests {

    private BookStoreModel apiAuth;
    private BookStoreModel apiRegister;
    private String userName;
    private String password;


    @Test
    public void textBoxForm() {
        mainPage.clickToElements();
        elementsPage.textBoxClick();
        elementsPage.fillFullName(Generator.generateString());
        elementsPage.fillEmail(Generator.generateEmail());
        elementsPage.fillCurrentAddress(Generator.generateString());
        elementsPage.fillPermanentAddress(Generator.generateString());
        elementsPage.clickSubmitButton();
    }

    @Test
    public void checkBoxFolders() {
        mainPage.clickToElements();
        elementsPage.checkBoxClick();
        elementsPage.clickPathCheckmark();
    }

    @Test
    public void radioButton() {
        mainPage.clickToElements();
        elementsPage.radioButtonClick();
        elementsPage.clickRadioButtonElement();
    }

    @Test
    public void addUserToWebTable() {
        mainPage.clickToElements();
        elementsPage.webTablesClick();
        elementsPage.registrationClick();
        elementsPage.fillFirstName(Generator.generateString());
        elementsPage.fillLastName(Generator.generateString());
        elementsPage.fillEmail(Generator.generateEmail());
        elementsPage.fillAge(Generator.generateInteger());
        elementsPage.fillSalary(Generator.generateInteger());
        elementsPage.fillDepartment(Generator.generateString());
        elementsPage.submitButtonClick();
    }

    @Test
    public void deleteAllUsers() {
        mainPage.clickToElements();
        elementsPage.webTablesClick();
        elementsPage.deleteUsers();
    }

    @Test
    public void menuButtons() {
        mainPage.clickToElements();
        elementsPage.buttonsClick();
        elementsPage.buttonClickMe();
    }

    @Test
    public void links() {
        mainPage.clickToElements();
        elementsPage.linksClick();
        elementsPage.homeLinkClick();
    }

    @Test(retryAnalyzer = TestNGRetry.class)
    public void brokenLinks() {
        mainPage.clickToElements();
        elementsPage.brokenLinksClick();
        elementsPage.validLinkClick();
    }

    @Test
    public void downloadFile() throws InterruptedException {
        mainPage.clickToElements();
        elementsPage.uploadAndDownloadClick();
        elementsPage.downloadClick();
    }

    @Test
    public void dynamicProperties() {
        mainPage.clickToElements();
        elementsPage.dynamicPropertiesClick();
        elementsPage.colorChangeClick();
    }

    @Test
    public void fillForm() {
        mainPage.clickToForms();
        formsPage.practiseFormClick();
        formsPage.fillFirstName(Generator.generateString());
        formsPage.fillLastName(Generator.generateString());
        formsPage.fillUserEmail(Generator.generateEmail());
        formsPage.maleGenderClick();
        formsPage.fillNumber(Generator.generatePhoneNumber());
        formsPage.calendarFill();
        formsPage.fillSubjects(Generator.generateString());
        formsPage.hobbySportClick();
        formsPage.hobbyReadingClick();
        formsPage.hobbyMusicClick();
        formsPage.fillCurrentAddress(Generator.generateString());
    }

    @Test
    public void uiAndApiBooks() { //Сравнение книг на бэке и UI
        mainPage.clickToBookStore();
        bookStorePage.scrollToAllBooksTitle();
        Assert.assertEquals(bookStorePage.getBooksTitleUI(), BookAttributes.getBooksTitleApi(), "Названия книг на api и ui не совпадают!");
        Assert.assertEquals(bookStorePage.getBooksAuthorUI(), BookAttributes.getBooksAuthorApi(), "Авторы книг на api и ui не совпадают!");
        Assert.assertEquals(bookStorePage.getBooksPublisherUI(), BookAttributes.getBooksPublisherApi(), "Писатели книг на api и ui не совпадают!");
    }

    @Test
    public void uiAuthorizationWithApiRegister() { //создание пользователя на бэке и авторизация на UI
        //Создание нового польхователя на бэке
        apiRegister = BookStoreModel.builder()
                .userName(ApiData.UserData.newUser)
                .password(ApiData.UserData.newPassword)
                .build();

        Assert.assertEquals(RequestToApi.methodPOST(apiRegister, ApiData.Endpoints.NEW_USER_ENDPOINT).statusCode(), 201, "Ошибка,пользователь не был зарегистрирован на бэке!");
        userName = apiRegister.getUserName();
        password = apiRegister.getPassword();
        mainPage.clickToBookStore();
        bookStorePage.spanLoginClick();
        bookStorePage.fillLogin(userName);
        bookStorePage.fillPassword(password);
        bookStorePage.loginClick();
    }

    @Test(retryAnalyzer = TestNGRetry.class)
    public void uiRegistrationWithApiAuth() { //создание пользователя на UI и авторизация на бэке
        //Авторизация на бэке
        apiAuth=BookStoreModel.builder()
                .userName(ApiData.UserData.USER_NAME)
                .password(ApiData.UserData.USER_PASSWORD)
                .build();

        userName = Generator.generateString();
        password = Generator.generatePassword();
        mainPage.clickToBookStore();
        bookStorePage.spanLoginClick();
        bookStorePage.newUserButtonClick();
        bookStorePage.fillFirstName(Generator.generateString());
        bookStorePage.fillLastName(Generator.generateString());
        bookStorePage.fillLogin(userName);
        bookStorePage.fillPassword(password);
        bookStorePage.captchaClick();
        bookStorePage.registerClick();
        Assert.assertEquals(RequestToApi.methodPOST(apiAuth, ApiData.Endpoints.AUTHORIZATION_ENDPOINT), 201, "Ошибка статус-код не 201!");
    }

    @Test
    public void newTabAlert() {
        mainPage.clickToAlerts();
        alertPage.browserWindowsClick();
        alertPage.newTabClick();
    }

    @Test
    public void clickToAllTypeAlerts() {
        mainPage.clickToAlerts();
        alertPage.alertsClick();
        alertPage.firstAlertClick();
        alertPage.secondAlertClick();
        alertPage.thirdAlertClick();
        alertPage.fourthAlertClick();
    }

    @Test
    public void getFrameText() {
        mainPage.clickToAlerts();
        alertPage.framesClick();
        alertPage.getFrameText();
    }

    @Test
    public void widgetTest() {
        mainPage.clickToWidgets();
        widgetsPage.accordianClick();
        widgetsPage.clickToAllAccordianElements();
    }

    @Test
    public void moveSlider() {
        mainPage.clickToWidgets();
        widgetsPage.clickToSlider();
    }

    @Test
    public void progressBar(){
        mainPage.clickToWidgets();
        widgetsPage.clickToProgressBar();
        widgetsPage.clickToButtonStart();
        widgetsPage.checkThatElementChangedText();
    }

}
