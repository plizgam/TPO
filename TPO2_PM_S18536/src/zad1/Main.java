/**
 *
 *  @author Pliżga Miłosz S18536
 *
 */

package zad1;


public class Main {
  public static void main(String[] args) {
    Service s = new Service("Poland");
    String weatherJson = s.getWeather("Warsaw");
    Double rate1 = s.getRateFor("USD");
    Double rate2 = s.getNBPRate();

    WindowApp gui = new WindowApp(s);

  }
}