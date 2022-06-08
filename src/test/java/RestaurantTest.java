import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;
    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setupRestaurant(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        // Edge case for exactly when time is exactly at opening time
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(spiedRestaurant.openingTime);
        boolean openAtOpeningTime = spiedRestaurant.isRestaurantOpen();
        assertTrue(openAtOpeningTime);

        // Edge case for exactly when time is exactly at closing time
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(spiedRestaurant.openingTime);
        boolean openAtClosingTime = spiedRestaurant.isRestaurantOpen();
        assertTrue(openAtClosingTime);

        // when time is between opening and closing time
        LocalTime betweenTime = spiedRestaurant.openingTime.plusMinutes((spiedRestaurant.openingTime.until(spiedRestaurant.openingTime, ChronoUnit.MINUTES)) /2);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(betweenTime);
        boolean openBetween = spiedRestaurant.isRestaurantOpen();
        assertTrue(openBetween);


    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant spiedRestaurant = Mockito.spy(restaurant);

        LocalTime closeTime = spiedRestaurant.closingTime.plusMinutes(1);
        Mockito.when(spiedRestaurant.getCurrentTime()).thenReturn(closeTime);
        boolean closeBetween = spiedRestaurant.isRestaurantOpen();
        assertFalse(closeBetween);


    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}