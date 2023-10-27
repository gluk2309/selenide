package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryOrderFormsTest {
    private String generateDate(int addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    } // генерация даты

    @Test
    void orderDeliveryCardsFullFormTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Воронеж");
        String planningDate = generateDate(7, "dd.MM.yyyy");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE); //очиста поля от предложенной даты
        $("[data-test-id='date'] input").setValue(planningDate); //вводим сгенерированую дату
        $("[data-test-id='name'] input").setValue("Надежда Ворон-Тарасова");
        $("[data-test-id='phone'] input").setValue("+71999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__content")
                //$(" .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));

    }

    @Test
    void interactionWithComplexElementsTest() {
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue("Во"); // Ввод первых двух букв нужного города
        $$(".menu-item__control").findBy(Condition.text("Воронеж")).click(); // Выбираем город из колекции и кликаем на него
        String planningDate = generateDate(3, "dd.MM.yyyy"); //Evaluate Expression
        if (!generateDate(0, "MM").equals(generateDate(10, "MM"))) {
            $(".input__icon").click();
            $("[data-step='1']").click();
            $$(".calendar__day").findBy(Condition.text(generateDate(10, "dd"))).click();

        } else {
            $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
            $("[data-test-id='date'] input").setValue(planningDate);
        }

        $("[data-test-id='name'] input").setValue("Надежда Ворон-Тарасова");
        $("[data-test-id='phone'] input").setValue("+71999999999");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("[data-test-id='notification'] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно забронирована на " + planningDate));

    }


}
